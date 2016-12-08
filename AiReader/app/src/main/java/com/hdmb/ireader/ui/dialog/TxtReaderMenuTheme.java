package com.hdmb.ireader.ui.dialog;


import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.tb.hdmb.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 作者    武博
 * 时间    2016/8/3 0003 15:12
 * 文件    TbReader
 * 描述
 */
public class TxtReaderMenuTheme extends PopupWindow {

    public static final int STYLE_DEFOULT = R.drawable.reading__reading_themes_vine_paper;
    public static final int STYLE_BLACK = R.drawable.reading__reading_themes_vine_dark;
    public static final int STYLE_GRAY = R.drawable.reading__reading_themes_vine_green;
    public static final int STYLE_BULE = R.drawable.reading__reading_themes_vine_white;
    public static final int STYLE_YELLOW = R.drawable.reading__reading_themes_vine_yellow1;

    @BindView(R.id.themeBg1)
    ImageView themeBg1;
    @BindView(R.id.themeTag1)
    View themeTag1;
    @BindView(R.id.theme1)
    RelativeLayout theme1;
    @BindView(R.id.themeBg2)
    ImageView themeBg2;
    @BindView(R.id.themeTag2)
    View themeTag2;
    @BindView(R.id.theme2)
    RelativeLayout theme2;
    @BindView(R.id.themeBg3)
    ImageView themeBg3;
    @BindView(R.id.themeTag3)
    View themeTag3;
    @BindView(R.id.theme3)
    RelativeLayout theme3;
    @BindView(R.id.themeBg4)
    ImageView themeBg4;
    @BindView(R.id.themeTag4)
    View themeTag4;
    @BindView(R.id.theme4)
    RelativeLayout theme4;
    @BindView(R.id.themeBg5)
    ImageView themeBg5;
    @BindView(R.id.themeTag5)
    View themeTag5;
    @BindView(R.id.theme5)
    RelativeLayout theme5;

    private Context context;

    private int mWindow_Width;
    private int mWindow_Height;
    private int mSelectedposition;
    private View SelectedTag;
    private OnTxtThemeChangeListener mListener;


    public TxtReaderMenuTheme(Context context, OnTxtThemeChangeListener mListener) {
        this.context = context;
        this.mListener = mListener;
        init();
    }

    private void init() {
        WindowManager m = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metrics = new DisplayMetrics();
        m.getDefaultDisplay().getMetrics(metrics);

        mWindow_Width = metrics.widthPixels;
        mWindow_Height = metrics.heightPixels;

        int rootwith = mWindow_Width;
        int rootheigh = mWindow_Height / 7;
        LinearLayout layout = (LinearLayout) LinearLayout.inflate(context, R.layout.poputwindow_txtreader_menu_theme, null);
        ButterKnife.bind(this, layout);
        this.setWidth(rootwith);
        this.setHeight(rootheigh);
        this.setFocusable(false);
        this.setOutsideTouchable(false);
        this.setContentView(layout);
        ColorDrawable dw = new ColorDrawable(Color.parseColor("#88000000"));
        this.setBackgroundDrawable(dw);

        themeBg1.setBackgroundResource(STYLE_DEFOULT);
        themeBg2.setBackgroundResource(STYLE_BLACK);
        themeBg3.setBackgroundResource(STYLE_GRAY);
        themeBg4.setBackgroundResource(STYLE_BULE);
        themeBg5.setBackgroundResource(STYLE_YELLOW);

        mSelectedposition = 1;
        SelectedTag = themeTag1;
    }

    @OnClick(R.id.theme1)
    protected void theme1(View v) {
        if (mSelectedposition != 1) {
            mListener.onThemeChange(STYLE_DEFOULT);
            hideSlidtag(SelectedTag);
            SelectedTag = themeTag1;
            showSlidTag(SelectedTag);
        }
    }

    @OnClick(R.id.theme2)
    protected void theme2(View v) {
        if (mSelectedposition != 2) {
            mListener.onThemeChange(STYLE_BLACK);
            hideSlidtag(SelectedTag);
            SelectedTag = themeTag2;
            showSlidTag(SelectedTag);
        }
    }

    @OnClick(R.id.theme3)
    protected void theme3(View v) {
        if (mSelectedposition != 3) {
            mListener.onThemeChange(STYLE_GRAY);
            hideSlidtag(SelectedTag);
            SelectedTag = themeTag3;
            showSlidTag(SelectedTag);
        }
    }

    @OnClick(R.id.theme4)
    protected void theme4(View v) {
        if (mSelectedposition != 4) {
            mListener.onThemeChange(STYLE_BULE);
            hideSlidtag(SelectedTag);
            SelectedTag = themeTag4;
            showSlidTag(SelectedTag);
        }
    }

    @OnClick(R.id.theme5)
    protected void theme5(View v) {
        if (mSelectedposition != 5) {
            mListener.onThemeChange(STYLE_YELLOW);
            hideSlidtag(SelectedTag);
            SelectedTag = themeTag5;
            showSlidTag(SelectedTag);
        }
    }

    private void hideSlidtag(View mSelectedTag) {
        mSelectedTag.setVisibility(View.INVISIBLE);
    }

    private void showSlidTag(View mSelectedTag) {
        mSelectedTag.setVisibility(View.VISIBLE);

    }

    public interface OnTxtThemeChangeListener {
        void onThemeChange(int theme);
    }
}
