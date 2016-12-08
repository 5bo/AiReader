package com.hdmb.ireader.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.NotificationCompat;
import android.text.TextUtils;
import android.util.Log;

import com.tb.hdmb.R;
import com.hdmb.ireader.api.HttpManager;
import com.hdmb.ireader.db.BookshelfManager;
import com.hdmb.ireader.manager.ArticleManager;
import com.hdmb.ireader.resp.Article;
import com.hdmb.ireader.resp.ArticleDirectory;
import com.hdmb.ireader.ui.activity.OfflineActivity;
import com.hdmb.ireader.utils.RxUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * 作者    武博
 * 时间    2016/8/23 0023 15:05
 * 文件    TbReader
 * 描述
 */
public class OfflineService extends Service {

    private Executor executor = Executors.newSingleThreadExecutor();
    public static List<Article> mArtricles = new ArrayList<>();
    private CompositeSubscription mSubscriptions = null;
    private Subscription mSubscription = null;
    // 通知栏
    private NotificationManager manager;

    @Override
    public void onCreate() {
        super.onCreate();
        manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        mSubscriptions = RxUtils.getNewCompositeSubIfUnsubscribed(new CompositeSubscription());
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);

        Article article = intent.getParcelableExtra("article");
        boolean status = intent.getBooleanExtra("status", true);
        if (status) {
            mArtricles.add(article);
            if (mSubscription == null) {
                addSubscribe();
            }
        } else {
            for (int i = 0; i < mArtricles.size(); i++) {
                if (mArtricles.get(i).getId().equals(article.getId())) {
                    mArtricles.remove(i);
                    if (i == 0) {
                        unsubscribe();
                        addSubscribe();
                    }
                }
            }
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void unsubscribe() {
        RxUtils.unsubscribeIfNotNull(mSubscriptions);
    }

    private void addSubscribe() {
        mSubscription = offlineArticle();
        if (mSubscription != null) {
            mSubscriptions.add(mSubscription);
        }
    }

    private Subscription offlineArticle() {
        if (mArtricles != null && mArtricles.size() > 0) {
            Article article = mArtricles.get(0);
            return HttpManager.getInstance().getApiService().queryArticleDirectory(article.getId())
                    .flatMap(new Func1<ArticleDirectory, Observable<Integer>>() {
                        @Override
                        public Observable<Integer> call(ArticleDirectory articleDirectory) {
                            article.setAuthor(articleDirectory.getAuthor());
                            article.setCategory(articleDirectory.getCategory());
                            article.setPartCount(articleDirectory.getParts().size());
                            article.setStatus(articleDirectory.getStatus());
                            BookshelfManager.updateBookshelfForArticle(OfflineService.this, article);
//                            return Observable.from(articleDirectory.getParts());
                            return Observable.from(articleDirectory.getParts().subList(0, 100));
                        }
                    }).filter(new Func1<Integer, Boolean>() {
                        @Override
                        public Boolean call(Integer partId) {
                            String filePath = ArticleManager.getInstance().getPartFilePath(article.getId(), partId);
                            return TextUtils.isEmpty(filePath);
                        }
                    }).flatMap(new Func1<Integer, Observable<Integer>>() {
                        @Override
                        public Observable<Integer> call(Integer partId) {
                            return HttpManager.getInstance().getApiService().queryArticlePartContent(article.getId(), partId)
                                    .map(content -> {
                                        ArticleManager.getInstance().cachePartContent(article.getId(), content.getPartId(), content.getContent());
                                        BookshelfManager.updateOfflineIdByArticleId(OfflineService.this, article.getId(), content.getPartId());
                                        return content.getPartId();
                                    });
                        }
                    })
                    .subscribeOn(Schedulers.from(executor))
                    .subscribe(new Subscriber<Integer>() {
                        @Override
                        public void onCompleted() {
                            manager.cancel(1);
                            sendBoradcast(article.getId());
                            Log.e("OfflineService", "onCompleted");
                            mArtricles.remove(0);
                            unsubscribe();
                            addSubscribe();
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.e("OfflineService", e.getMessage());
                        }

                        @Override
                        public void onNext(Integer offlineId) {
                            Log.e("OfflineService", "第" + offlineId + "节");
                            updateNotification(offlineId);
                        }
                    });
        }
        return null;
    }

    private void sendBoradcast(String articleId) {
        Intent intent = new Intent(OfflineActivity.ACTION_REFRESH_OFFLINELIST);
        intent.putExtra("articleId", articleId);
        sendBroadcast(intent);
    }

    private void updateNotification(int offlineId) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        //系统状态栏显示的小图标
        builder.setSmallIcon(R.drawable.ic_launcher);
        //下拉显示的大图标
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher));
        //禁止用户点击删除按钮删除
        builder.setAutoCancel(false);
        //禁止滑动删除
        builder.setOngoing(true);
        //取消右上角的时间显示
        builder.setShowWhen(false);
        builder.setContentTitle("正在下载第" + offlineId + "节");
        builder.setProgress(mArtricles.get(0).getPartCount(), offlineId, false);
        //builder.setContentInfo(progress+"%");
        builder.setOngoing(true);
        builder.setShowWhen(false);
        Intent intent = new Intent(this, OfflineActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(this, 1, intent, 0);
        builder.setContentIntent(pIntent);
        Notification notification = builder.build();
        manager.notify(1, notification);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mSubscriptions != null || !mSubscriptions.isUnsubscribed()) {
            mSubscriptions.unsubscribe();
        }
    }
}
