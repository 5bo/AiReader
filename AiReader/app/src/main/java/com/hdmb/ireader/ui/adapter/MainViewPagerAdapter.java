package com.hdmb.ireader.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.hdmb.ireader.ui.fragment.BookStoreFragment;
import com.hdmb.ireader.ui.fragment.MyBookshelfFragment;

/**
 * 作者    武博
 * 时间    2016/7/14 0014 14:35
 * 文件    TbReader
 * 描述
 */
public class MainViewPagerAdapter extends FragmentPagerAdapter {

    public MainViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new MyBookshelfFragment();
            case 1:
                return new BookStoreFragment();
        }
        return new MyBookshelfFragment();
    }

    @Override
    public int getCount() {
        return 2;
    }
}
