package com.hdmb.ireader.ui.dialog;


import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;

import com.tb.hdmb.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 作者    武博
 * 时间    2016/8/3 0003 15:12
 * 文件    TbReader
 * 描述
 */
public class TxtReaderMenuTypeface extends PopupWindow {

    @BindView(R.id.seekBar)
    SeekBar seekBar;
    @BindView(R.id.typeface1)
    RadioButton typeface1;
    @BindView(R.id.typeface2)
    RadioButton typeface2;
    @BindView(R.id.typeface3)
    RadioButton typeface3;
    @BindView(R.id.typefaces)
    RadioGroup typefaces;

    private Context context;
    private OnTxtReaderMenuTypefaceClickListener mListener;

    private int mWindow_Width;
    private int mWindow_Height;

    public TxtReaderMenuTypeface(Context context, OnTxtReaderMenuTypefaceClickListener mListener) {
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
        int rootheigh = mWindow_Height / 4;

        LinearLayout layout = (LinearLayout) LinearLayout.inflate(context, R.layout.poputwindow_txtreader_menu_typeface, null);
        ButterKnife.bind(this, layout);
        this.setWidth(rootwith);
        this.setHeight(rootheigh);
        this.setFocusable(false);
        this.setOutsideTouchable(false);
        this.setContentView(layout);
        ColorDrawable dw = new ColorDrawable(Color.parseColor("#88000000"));
        this.setBackgroundDrawable(dw);

        seekBar.setMax(30);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                mListener.onTextSizeChange(i + 30);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        typefaces.setOnCheckedChangeListener((RadioGroup radioGroup, int i) -> {
            if (mListener != null)
                switch (i) {
                    case R.id.typeface0:
                        mListener.onTypefaceChange("");
                        break;
                    case R.id.typeface1:
                        mListener.onTypefaceChange("fonts/font2.ttf");
                        break;
                    case R.id.typeface2:
                        mListener.onTypefaceChange("fonts/font1.ttf");
                        break;
                    case R.id.typeface3:
                        mListener.onTypefaceChange("fonts/font3.ttf");
                        break;
                }
        });
    }

    public void setTextSize(int textSize) {
        seekBar.setProgress(textSize - 30);
    }

    public void setTypeface(String typeface) {
        if (typeface.equals("fonts/font2.ttf")) {
            typeface1.setChecked(true);
        } else if (typeface.equals("fonts/font1.ttf")) {
            typeface2.setChecked(true);
        } else {
            typeface3.setChecked(true);
        }
    }

    public interface OnTxtReaderMenuTypefaceClickListener {
        void onTextSizeChange(int textSize);

        void onTypefaceChange(String textsort);
    }
}
