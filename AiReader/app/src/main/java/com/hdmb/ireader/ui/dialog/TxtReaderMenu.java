package com.hdmb.ireader.ui.dialog;


import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.tb.hdmb.R;

/**
 * 作者    武博
 * 时间    2016/8/3 0003 15:12
 * 文件    TbReader
 * 描述
 */
public class TxtReaderMenu extends PopupWindow {

    private Context context;
    private OnTxtReaderMenuClickListener mListener;

    private int mWindow_With;
    private int mWindow_Heigh;

    public TxtReaderMenu(Context context, OnTxtReaderMenuClickListener mListener) {
        this.context = context;
        this.mListener = mListener;
        init();
    }

    private void init() {
        WindowManager m = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metrics = new DisplayMetrics();
        m.getDefaultDisplay().getMetrics(metrics);

        mWindow_With = metrics.widthPixels;
        mWindow_Heigh = metrics.heightPixels;

        int rootwith = mWindow_With;
        int rootheigh = mWindow_Heigh / 7;

        LinearLayout layout = (LinearLayout) LinearLayout.inflate(context, R.layout.poputwindow_txtreader_menu, null);

        this.setWidth(rootwith);
        this.setHeight(rootheigh);
        this.setFocusable(false);
        this.setOutsideTouchable(false);
        this.setContentView(layout);
        ColorDrawable dw = new ColorDrawable(Color.parseColor("#88000000"));
        this.setBackgroundDrawable(dw);

        TextView parts = (TextView) layout.findViewById(R.id.menuParts);
        TextView typeface = (TextView) layout.findViewById(R.id.menuTypeface);
        TextView theme = (TextView) layout.findViewById(R.id.menuTheme);
        TextView light = (TextView) layout.findViewById(R.id.menuLight);

        parts.setOnClickListener(v->{
            if (mListener != null)
                mListener.onPartsMenu();
        });

        typeface.setOnClickListener(v -> {
            if (mListener != null)
                mListener.onTypefaceMenu();
        });

        theme.setOnClickListener(v -> {
            if (mListener != null)
                mListener.onThemeMenu();
        });

        light.setOnClickListener(v -> {
            if (mListener != null)
                mListener.onLightMenu();
        });

    }

    public interface OnTxtReaderMenuClickListener {
        void onPartsMenu();

        void onTypefaceMenu();

        void onThemeMenu();

        void onLightMenu();
    }
}
