package com.hdmb.ireader.ui.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tb.hdmb.R;
import com.hdmb.ireader.mvp.presenter.MyBookshelfPresenter;
import com.hdmb.ireader.mvp.presenter.impl.MyBookshelfPresenterImpl;
import com.hdmb.ireader.mvp.view.MyBookshelfView;
import com.hdmb.ireader.resp.Article;
import com.hdmb.ireader.ui.activity.BookReaderActivity;
import com.hdmb.ireader.ui.base.BaseFragment;
import com.hdmb.ireader.ui.base.RecyclerViewItemAdapter;
import com.hdmb.ireader.utils.ImageUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 作者    武博
 * 时间    2016/7/13 0013 17:32
 * 文件    TbReader
 * 描述    我的书架
 */
public class MyBookshelfFragment extends BaseFragment<MyBookshelfPresenter> implements MyBookshelfView {

    public static final String ACTION_REFRESH_BOOKSHELF = "com.tb.reader.refreshMyBookshelf";

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private List<Article> mArticles = new ArrayList<>();

    RecyclerViewItemAdapter<Article> mMyBookshelfAdapter;

    public static int width, height;
    public static Bitmap itemBookshelfBackground;
    public static Bitmap itemBookshelfGapBackground;
    private static int bookshelfRowCount = 3;

    private BroadcastReceiver refreshReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            mPresenter.loadMyBooks();
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mybookshelf, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    protected MyBookshelfPresenter createPresenter() {
        return new MyBookshelfPresenterImpl(mContext, this);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Display defaultDisplay = getActivity().getWindowManager().getDefaultDisplay();
        width = defaultDisplay.getWidth();
        height = defaultDisplay.getHeight();
        itemBookshelfBackground = ImageUtils.getBitmap(mContext, R.drawable.l4, width / bookshelfRowCount, height / bookshelfRowCount);
        itemBookshelfGapBackground = ImageUtils.getBitmap(mContext, R.drawable.k9, width / bookshelfRowCount, height / bookshelfRowCount);
        mMyBookshelfAdapter = new RecyclerViewItemAdapter<Article>(mContext, mArticles, R.layout.item_bookshelf) {
            @Override
            public void convert(ViewHolder holder, Article article, int position) {
                holder.setVisible(R.id.articleName, false);
                if (article != null) {
                    holder.setVisible(R.id.articleName, true);
                    holder.setText(R.id.articleName, article.getName());
                }
                holder.setBackgroundBitmap(R.id.bookshelf, itemBookshelfBackground);
                holder.setImageBitmap(R.id.bookshelfGap, itemBookshelfGapBackground);
                holder.setOnClickListener(R.id.articleName, v -> {
                            BookReaderActivity.openBookReader(getActivity(), article);
                        }
                );
            }

            @Override
            public int getItemCount() {
                if (mArticles.size() > bookshelfRowCount * 5)
                    return ((mArticles.size() / bookshelfRowCount) + 1) * bookshelfRowCount;
                else
                    return bookshelfRowCount * 5;
            }
        };
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), bookshelfRowCount));
        recyclerView.setAdapter(mMyBookshelfAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.loadMyBooks();
        getActivity().registerReceiver(refreshReceiver, new IntentFilter(ACTION_REFRESH_BOOKSHELF));
    }

    @Override
    public void showMyBookshelfArticles(List<Article> articles) {
        mMyBookshelfAdapter.setData(articles);
    }
}
