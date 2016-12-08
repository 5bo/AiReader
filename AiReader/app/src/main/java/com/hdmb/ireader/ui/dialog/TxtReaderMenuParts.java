package com.hdmb.ireader.ui.dialog;


import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.tb.hdmb.R;
import com.hdmb.ireader.common.adapter.recyclerview.DividerGridItemDecoration;
import com.hdmb.ireader.common.adapter.recyclerview.RecyclerAdapter;
import com.hdmb.ireader.common.adapter.recyclerview.RecyclerViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 作者    武博
 * 时间    2016/8/3 0003 15:12
 * 文件    TbReader
 * 描述
 */
public class TxtReaderMenuParts extends PopupWindow {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    RecyclerAdapter recyclerAdapter;
    private Context context;
    private int mWindow_Width;
    private int mWindow_Height;
    OnSelectPartListener listener;
    List<String> mParts = new ArrayList<>();

    public TxtReaderMenuParts(Context context, int partSize, OnSelectPartListener listener) {
        this.context = context;
        this.listener = listener;
        for (int i = 1; i <= partSize; i++)
            mParts.add("第" + i + "节");
        init();
    }

    private void init() {
        WindowManager m = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metrics = new DisplayMetrics();
        m.getDefaultDisplay().getMetrics(metrics);

        mWindow_Width = metrics.widthPixels;
        mWindow_Height = metrics.heightPixels;

        int rootwith = mWindow_Width;
        int rootheigh = mWindow_Height / 4 * 3;
        LinearLayout layout = (LinearLayout) LinearLayout.inflate(context, R.layout.poputwindow_txtreader_menu_parts, null);
        ButterKnife.bind(this, layout);
        this.setWidth(rootwith);
        this.setHeight(rootheigh);
        this.setFocusable(false);
        this.setOutsideTouchable(false);
        this.setContentView(layout);
        ColorDrawable dw = new ColorDrawable(Color.parseColor("#88000000"));
        this.setBackgroundDrawable(dw);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.addItemDecoration(new DividerGridItemDecoration(context));
        recyclerAdapter = new RecyclerAdapter(context, mParts) {
            @Override
            protected int getItemLayoutId(int viewType) {
                return R.layout.item_article_part;
            }

            @Override
            protected void bindData(RecyclerViewHolder holder, int position, Object item) {
                holder.setText(R.id.part, mParts.get(position));
            }
        };
        recyclerView.setAdapter(recyclerAdapter);
        recyclerAdapter.setOnItemClickListener((View itemView, int pos) -> {
            if (listener != null)
                listener.onSelectPart(pos + 1);
            dismiss();
        });
    }

    public void setPartSize(int partSize) {
        mParts.clear();
        for (int i = 1; i <= partSize; i++)
            mParts.add("第" + i + "节");
        recyclerAdapter.setData(mParts);
    }

    public void setScrollPosition(int position) {
        recyclerView.scrollToPosition(position);
    }

    public interface OnSelectPartListener {
        void onSelectPart(int part);
    }
}