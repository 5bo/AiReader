package com.hdmb.ireader.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.tb.hdmb.R;
import com.hdmb.ireader.mvp.presenter.ArticleDirectoryPresenter;
import com.hdmb.ireader.mvp.presenter.impl.ArticleDirectoryPresenterImpl;
import com.hdmb.ireader.mvp.view.ArticleDirectoryView;
import com.hdmb.ireader.resp.Article;
import com.hdmb.ireader.resp.ArticleDirectory;
import com.hdmb.ireader.ui.Navigator;
import com.hdmb.ireader.ui.base.RecyclerViewItemAdapter;
import com.hdmb.ireader.ui.base.StatusBarActivity;
import com.hdmb.ireader.ui.fragment.MyBookshelfFragment;
import com.hdmb.ireader.ui.listener.NavigationFinishClickListener;
import com.hdmb.ireader.utils.ThemeUtils;
import com.hdmb.ireader.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 作者    武博
 * 时间    2016/7/14 0014 18:03
 * 文件    TbReader
 * 描述
 */
public class ArticleDirectoryActivity extends StatusBarActivity<ArticleDirectoryPresenter> implements Toolbar.OnMenuItemClickListener, ArticleDirectoryView {

    private static final String EXTRA_ARTICLE = "article";
    private static final String EXTRA_ARTICLE_IMG = "articlePic";
    private static final String NAME_IMG_AVATAR = "imgAvatar";

    @BindView(R.id.toolbar)
    protected Toolbar toolbar;
    @BindView(R.id.articlePic)
    protected ImageView articlePic;
    @BindView(R.id.articleName)
    protected TextView articleName;
    @BindView(R.id.articleAuthor)
    protected TextView articleAuthor;
    @BindView(R.id.articleStatus)
    protected TextView articleStatus;
    @BindView(R.id.articleCategory)
    protected TextView articleCategory;
    @BindView(R.id.articleReader)
    protected Button articleReader;
    @BindView(R.id.addBookshelf)
    protected Button addBookshelf;

    @BindView(R.id.recyclerView)
    protected RecyclerView recyclerView;

    private Article mArticle;
    private List<Integer> mParts = new ArrayList<>();

    private RecyclerViewItemAdapter<Integer> mPartsAdapter;

    public static void openArticleDirectory(@NonNull Activity activity, @NonNull Article article, @NonNull ImageView imgAvatar) {
        Intent intent = new Intent(activity, ArticleDirectoryActivity.class);
        intent.putExtra(EXTRA_ARTICLE, article);
        intent.putExtra(EXTRA_ARTICLE_IMG, R.drawable.g8);
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, imgAvatar, NAME_IMG_AVATAR);
        ActivityCompat.startActivity(activity, intent, options.toBundle());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ThemeUtils.configThemeBeforeOnCreate(this, R.style.AppThemeLight_FitsStatusBar, R.style.AppThemeDark_FitsStatusBar);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_directory);
        ButterKnife.bind(this);

        ViewCompat.setTransitionName(articlePic, NAME_IMG_AVATAR);

        mArticle = getIntent().getParcelableExtra(EXTRA_ARTICLE);
        articleName.setText(mArticle.getName());
        articleStatus.setText("状态:" + mArticle.getStatus());
        articlePic.setImageResource(getIntent().getIntExtra(EXTRA_ARTICLE_IMG, R.drawable.g8));

        toolbar.setNavigationOnClickListener(new NavigationFinishClickListener(this));
        toolbar.inflateMenu(R.menu.article_directory);
        toolbar.setOnMenuItemClickListener(this);

        mPartsAdapter = new RecyclerViewItemAdapter<Integer>(mContext, mParts, R.layout.item_article_part) {
            @Override
            public void convert(ViewHolder holder, Integer partIndex, int position) {
                holder.setText(R.id.part, "第" + partIndex + "节");
                holder.setOnClickListener(R.id.part, (view) -> {
                    mArticle.setPartId(partIndex);
                    BookReaderActivity.openBookReader(ArticleDirectoryActivity.this, mArticle);
                });
            }
        };
        recyclerView.setLayoutManager(new GridLayoutManager(mContext, 4));
        recyclerView.setAdapter(mPartsAdapter);

        mPresenter.queryArticleDirectory(mArticle.getId());
    }

    @Override
    protected ArticleDirectoryPresenter createPresenter() {
        return new ArticleDirectoryPresenterImpl(this);
    }

    /**
     * 免费阅读
     *
     * @param v
     */
    @OnClick(R.id.articleReader)
    void articleReader(View v) {
        BookReaderActivity.openBookReader(this, mArticle);
    }

    @OnClick(R.id.addBookshelf)
    void addBookshelf(View v) {
        mArticle.setPartCount(mParts.size());
        mPresenter.addBookshelf(mContext, mArticle);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.openInBrowser:// 在浏览器中打开
                Navigator.openInBrowser(this, "");
        }
        return false;
    }

    @Override
    public void showDirectory(ArticleDirectory articleDirectory) {
        articleAuthor.setText("作者:" + articleDirectory.getAuthor());
        articleCategory.setText("分类:" + articleDirectory.getCategory());
        mPartsAdapter.setData(articleDirectory.getParts());
    }

    @Override
    public void startArticleLook() {

    }

    @Override
    public void showAddBookshelfStatus(Boolean status) {
        if (status)
            sendBroadcast(new Intent(MyBookshelfFragment.ACTION_REFRESH_BOOKSHELF));
        ToastUtils.with(mContext).show(status ? "已成功加入书架" : "加入失败");
    }
}
