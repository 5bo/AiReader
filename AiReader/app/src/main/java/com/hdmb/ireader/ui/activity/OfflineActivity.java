package com.hdmb.ireader.ui.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.CheckBox;

import com.tb.hdmb.R;
import com.hdmb.ireader.db.BookshelfManager;
import com.hdmb.ireader.mvp.presenter.OfflinePersenter;
import com.hdmb.ireader.mvp.presenter.impl.OfflinePersenterImpl;
import com.hdmb.ireader.mvp.view.OfflineView;
import com.hdmb.ireader.resp.Article;
import com.hdmb.ireader.service.OfflineService;
import com.hdmb.ireader.ui.base.RecyclerViewItemAdapter;
import com.hdmb.ireader.ui.base.StatusBarActivity;
import com.hdmb.ireader.ui.listener.NavigationFinishClickListener;
import com.hdmb.ireader.utils.ThemeUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 作者    武博
 * 时间    2016/8/22 0022 15:52
 * 文件    TbReader
 * 描述
 */
public class OfflineActivity extends StatusBarActivity<OfflinePersenter> implements Toolbar.OnMenuItemClickListener, OfflineView {

    public static String ACTION_REFRESH_OFFLINELIST = "com.tb.reader.refresh.OfflineList";

    @BindView(R.id.toolbar)
    protected Toolbar toolbar;
    @BindView(R.id.recyclerView)
    protected RecyclerView recyclerView;
    private RecyclerViewItemAdapter<Article> recyclerViewItemAdapter;
    private List<Article> mArticles = new ArrayList<>();

    private BroadcastReceiver refreshOfflineList = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            mArticles = BookshelfManager.getBookshelfBooks(mContext);
            String articleId = intent.getStringExtra("articleId");
            for (int i = 0; i < mArticles.size(); i++) {
                if (articleId.equals(mArticles.get(i).getId()))
                    recyclerViewItemAdapter.notifyItemChanged(i);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ThemeUtils.configThemeBeforeOnCreate(this, R.style.AppThemeLight, R.style.AppThemeDark);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offline);
        ButterKnife.bind(this);

        toolbar.setNavigationOnClickListener(new NavigationFinishClickListener(this));
        recyclerViewItemAdapter = new RecyclerViewItemAdapter<Article>(mContext, mArticles, R.layout.item_offline) {
            @Override
            public void convert(ViewHolder holder, Article article, int position) {
                holder.setText(R.id.articleName, article.getName());
                holder.setText(R.id.articleOffline, "已缓存" + article.getOfflineId() + "节");
                if (article.getStatus().contains("连载")) {
                    holder.setText(R.id.articleStatus, "已更新" + article.getPartCount() + "节");
                } else {
                    holder.setText(R.id.articleStatus, "已完结");
                }
                holder.setChecked(R.id.offline, false);
                for (Article a : OfflineService.mArtricles) {
                    if (a.getId().equals(article.getId()))
                        holder.setChecked(R.id.offline, true);
                }
                holder.setOnClickListener(R.id.offline, v -> {
                    Intent intent = new Intent(OfflineActivity.this, OfflineService.class);
                    intent.putExtra("article", article);
                    if (((CheckBox) holder.getView(R.id.offline)).isChecked()) {
                        intent.putExtra("status", true);
                    } else {
                        intent.putExtra("status", false);
                    }
                    startService(intent);
                });

            }
        };
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setAdapter(recyclerViewItemAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.loadArticles();
        registerReceiver(refreshOfflineList, new IntentFilter(ACTION_REFRESH_OFFLINELIST));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(refreshOfflineList);
    }

    @Override
    protected OfflinePersenter createPresenter() {
        return new OfflinePersenterImpl(this, this);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        return false;
    }

    @Override
    public void showArticles(List<Article> articles) {
        mArticles = articles;
        recyclerViewItemAdapter.setData(mArticles);
    }
}
