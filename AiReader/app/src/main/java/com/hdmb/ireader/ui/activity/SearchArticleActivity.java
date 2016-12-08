package com.hdmb.ireader.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.widget.TextView;

import com.tb.hdmb.R;
import com.hdmb.ireader.mvp.presenter.SearchArticlePresenter;
import com.hdmb.ireader.mvp.presenter.impl.SearchArticlePresenterImpl;
import com.hdmb.ireader.mvp.view.SearchArticleView;
import com.hdmb.ireader.resp.Article;
import com.hdmb.ireader.ui.base.RecyclerViewItemAdapter;
import com.hdmb.ireader.ui.base.StatusBarActivity;
import com.hdmb.ireader.utils.ThemeUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 作者    武博
 * 时间    2016/7/14 0014 15:14
 * 文件    TbReader
 * 描述
 */
public class SearchArticleActivity extends StatusBarActivity<SearchArticlePresenter> implements SearchArticleView, SearchView.OnQueryTextListener {

    @BindView(R.id.searchView)
    SearchView searchView;
    @BindView(R.id.noData)
    TextView noData;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    List<Article> mArticles = new ArrayList<>();
    RecyclerViewItemAdapter<Article> mArticlesAdapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        ThemeUtils.configThemeBeforeOnCreate(this, R.style.AppThemeLight_FitsStatusBar, R.style.AppThemeDark_FitsStatusBar);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_article);
        ButterKnife.bind(this);
        // 设置该SearchView默认是否自动缩小为图标
        searchView.setIconifiedByDefault(false);
        // 为该SearchView组件设置事件监听器
        searchView.setOnQueryTextListener(this);
        // 设置该SearchView显示搜索按钮
        searchView.setSubmitButtonEnabled(true);
        // 设置该SearchView内默认显示的提示文本
        searchView.setQueryHint("查找图书");
        mArticlesAdapter = new RecyclerViewItemAdapter<Article>(mContext, mArticles, R.layout.item_article) {
            @Override
            public void convert(ViewHolder holder, Article article, int position) {
                holder.setText(R.id.articleName, article.getName());
                holder.setText(R.id.articleAuthor, article.getAuthor());
                holder.setText(R.id.articleCategory, article.getCategory());
                holder.setText(R.id.articleStatus, article.getStatus());
                holder.setOnClickListener(R.id.article, (v) -> {
                    ArticleDirectoryActivity.openArticleDirectory(SearchArticleActivity.this, article, holder.getView(R.id.articlePic));
                });
                holder.setOnClickListener(R.id.addBookshelf, (v) -> {

                });
            }
        };
        //设置布局管理器
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mArticlesAdapter);
    }

    @Override
    protected SearchArticlePresenter createPresenter() {
        return new SearchArticlePresenterImpl(this);
    }

    @Override
    public void showArticles(List<Article> articles) {
        if (articles != null && articles.size() > 0) {
            noData.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            mArticlesAdapter.setData(articles);
        } else {
            noData.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        }
    }

    // 用户输入字符时激发该方法
    @Override
    public boolean onQueryTextSubmit(String query) {
        mPresenter.query(query);
        return false;
    }

    // 单击搜索按钮时激发该方法
    @Override
    public boolean onQueryTextChange(String newText) {
        mArticlesAdapter.setData(new ArrayList<>());
        return true;
    }
}
