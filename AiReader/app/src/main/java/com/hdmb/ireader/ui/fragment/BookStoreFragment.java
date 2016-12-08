package com.hdmb.ireader.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tb.hdmb.R;
import com.hdmb.ireader.mvp.presenter.BookStorePresenter;
import com.hdmb.ireader.mvp.presenter.impl.BookStorePresenterImpl;
import com.hdmb.ireader.mvp.view.BookStoreView;
import com.hdmb.ireader.resp.Category;
import com.hdmb.ireader.ui.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 作者    武博
 * 时间    2016/7/14 0014 14:37
 * 文件    TbReader
 * 描述
 */
public class BookStoreFragment extends BaseFragment<BookStorePresenter> implements BookStoreView {

    @BindView(R.id.noData)
    protected TextView noData;
    @BindView(R.id.dataLayout)
    protected LinearLayout dataLayout;
    @BindView(R.id.tabLayout)
    protected TabLayout tabLayout;
    @BindView(R.id.viewPager)
    protected ViewPager viewPager;

    List<Category> mCategorys = new ArrayList<>();
    FragmentPagerAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bookstore, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViewPager();
    }

    private void initViewPager() {
        mAdapter = new FragmentPagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                Fragment fragment = new CategoryViewFragment();
                Bundle bundle = new Bundle();
                bundle.putParcelable(CategoryViewFragment.EXTRA_CATEGORY, mCategorys.get(position));
                fragment.setArguments(bundle);
                return fragment;
            }

            @Override
            public int getCount() {
                return mCategorys.size();
            }
        };
        viewPager.setAdapter(mAdapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        mPresenter.getCategoryList();
    }

    @Override
    protected BookStorePresenter createPresenter() {
        return new BookStorePresenterImpl(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.getCategoryList();
    }

    @Override
    public void noData() {
        noData.setVisibility(View.VISIBLE);
        dataLayout.setVisibility(View.GONE);
    }

    @Override
    public void showCategoryTab(List<Category> categories) {
        this.mCategorys = categories;
        noData.setVisibility(View.GONE);
        dataLayout.setVisibility(View.VISIBLE);
        mAdapter.notifyDataSetChanged();
        viewPager.setOffscreenPageLimit(2);
        for (int i = 0; i < mCategorys.size(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            tab.setText(mCategorys.get(i).getName());
        }
    }
}
