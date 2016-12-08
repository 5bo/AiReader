package com.hdmb.ireader.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tb.hdmb.R;
import com.hdmb.ireader.mvp.presenter.CategoryViewPresenter;
import com.hdmb.ireader.mvp.presenter.impl.CategoryViewPresenterImpl;
import com.hdmb.ireader.mvp.view.CategoryViewView;
import com.hdmb.ireader.resp.Article;
import com.hdmb.ireader.resp.Category;
import com.hdmb.ireader.resp.CategoryView;
import com.hdmb.ireader.resp.Pagenation;
import com.hdmb.ireader.ui.activity.ArticleDirectoryActivity;
import com.hdmb.ireader.ui.base.BaseFragment;
import com.hdmb.ireader.ui.base.RecyclerViewItemAdapter;
import com.hdmb.ireader.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 作者    武博
 * 时间    2016/7/25 0025 9:38
 * 文件    TbReader
 * 描述
 */
public class CategoryViewFragment extends BaseFragment<CategoryViewPresenter> implements SwipeRefreshLayout.OnRefreshListener, CategoryViewView {

    public static String EXTRA_CATEGORY = "category";

    @BindView(R.id.noData)
    protected TextView noData;
    @BindView(R.id.swipeRefreshLayout)
    protected SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.recyclerView)
    protected RecyclerView recyclerView;

    protected RecyclerViewItemAdapter<Article> mAdapter;
    protected List<Article> mArticles = new ArrayList<>();
    protected Pagenation mPagenation = new Pagenation();
    Category mCategory;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Bundle args = getArguments();
        if (null != args) {
            mCategory = args.getParcelable(EXTRA_CATEGORY);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_category, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        swipeRefreshLayout.setRefreshing(true);
        swipeRefreshLayout.setOnRefreshListener(this);
        mAdapter = new RecyclerViewItemAdapter<Article>(getActivity(), mArticles, R.layout.item_article) {
            @Override
            public void convert(ViewHolder holder, Article article, int position) {
                holder.setText(R.id.articleName, article.getName());
                holder.setText(R.id.articleStatus, article.getStatus());
                holder.setOnClickListener(R.id.article, (v) -> {
                    ArticleDirectoryActivity.openArticleDirectory(getActivity(), article, holder.getView(R.id.articlePic));
                });
                holder.setOnClickListener(R.id.addBookshelf, (v) -> {
                    mPresenter.addBookshelf(mContext, article);
                });
            }

            @Override
            protected void loadMoreData() {
                mPresenter.getCategoryArticleList(mCategory.getId(), 1);
            }
        };
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(mAdapter);
        mPresenter.getCategoryArticleList(mCategory.getId(), mPagenation.getNext());
    }

    @Override
    protected CategoryViewPresenter createPresenter() {
        return new CategoryViewPresenterImpl(this);
    }

    @OnClick(R.id.noData)
    public void noData(View view) {
        onRefresh();
    }

    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(true);
        mPresenter.getCategoryArticleList(mCategory.getId(), 1);
    }

    @Override
    public void noDate() {
        swipeRefreshLayout.setRefreshing(false);
        noData.setVisibility(View.VISIBLE);
        swipeRefreshLayout.setVisibility(View.GONE);
    }

    @Override
    public void showArticleView(CategoryView categoryView) {
        mPagenation = categoryView.getPagenation();
        swipeRefreshLayout.setRefreshing(false);
        noData.setVisibility(View.GONE);
        swipeRefreshLayout.setVisibility(View.VISIBLE);
        if (mPagenation.getCurrent() == 1) {
            mArticles = categoryView.getArticles();
        } else {
            mArticles.addAll(categoryView.getArticles());
        }
        mAdapter.setData(mArticles);
    }

    @Override
    public void showAddBookshelfStatus(boolean status) {
        if (status)
            getActivity().sendBroadcast(new Intent(MyBookshelfFragment.ACTION_REFRESH_BOOKSHELF));
        ToastUtils.with(mContext).show(status ? "加入成功" : "已加入书架");
    }
}
