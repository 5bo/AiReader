package com.hdmb.ireader.ui.dialog;


import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.SeekBar;
import android.widget.TextView;

import com.tb.hdmb.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 作者    武博
 * 时间    2016/8/3 0003 15:12
 * 文件    TbReader
 * 描述
 */
public class TxtReaderMenuLight extends PopupWindow {

    @BindView(R.id.progress)
    TextView progress;
    @BindView(R.id.seekBar)
    SeekBar seekBar;
    private Context context;
    private int mWindow_Width;
    private int mWindow_Height;


    public TxtReaderMenuLight(Context context) {
        this.context = context;
        init();
    }

    private void init() {
        SaveSystemdefaultBrignhtness(context);

        WindowManager m = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metrics = new DisplayMetrics();
        m.getDefaultDisplay().getMetrics(metrics);

        mWindow_Width = metrics.widthPixels;
        mWindow_Height = metrics.heightPixels;

        int rootwith = mWindow_Width;
        int rootheigh = mWindow_Height / 7;
        LinearLayout layout = (LinearLayout) LinearLayout.inflate(context, R.layout.poputwindow_txtreader_menu_light, null);
        ButterKnife.bind(this, layout);
        this.setWidth(rootwith);
        this.setHeight(rootheigh);
        this.setFocusable(false);
        this.setOutsideTouchable(false);
        this.setContentView(layout);
        ColorDrawable dw = new ColorDrawable(Color.parseColor("#88000000"));
        this.setBackgroundDrawable(dw);

        int systembrightness = getSystemdefaultBrignhtness();
        int p = systembrightness * 100 / 255;

        System.out.println("systembrightness+++++3---/:" + p);

        seekBar.setProgress(p);
        progress.setText(p + "%");
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                // 取得当前进度
                int tmpInt = i;
                progress.setText(tmpInt + "%");

                // 根据当前进度改变亮度
                Settings.System.putInt(context.getContentResolver(),
                        Settings.System.SCREEN_BRIGHTNESS, tmpInt);
                tmpInt = Settings.System.getInt(context.getContentResolver(),
                        Settings.System.SCREEN_BRIGHTNESS, -1);

                WindowManager.LayoutParams wl = ((Activity) context).getWindow().getAttributes();

                float tmpFloat = (float) tmpInt / 100;
                if (tmpFloat > 0 && tmpFloat <= 1) {
                    wl.screenBrightness = tmpFloat;
                }

                ((Activity) context).getWindow().setAttributes(wl);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void SaveSystemdefaultBrignhtness(Context mContext) {

        SharedPreferences sharedPreferences = mContext.getSharedPreferences("SYSTEM_BRIGHTNESS", Context.MODE_PRIVATE);
        sharedPreferences.edit().putInt("brightness_value", getScreenBrightness(mContext)).commit();

    }

    public int getSystemdefaultBrignhtness() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("SYSTEM_BRIGHTNESS", Context.MODE_PRIVATE);
        return sharedPreferences.getInt("brightness_value", 30);
    }

    public int getScreenBrightness(Context activity) {
        int value = 0;
        ContentResolver cr = activity.getContentResolver();
        try {
            value = Settings.System.getInt(cr, Settings.System.SCREEN_BRIGHTNESS);
        } catch (Settings.SettingNotFoundException e) {

        }

        return value;
    }
}