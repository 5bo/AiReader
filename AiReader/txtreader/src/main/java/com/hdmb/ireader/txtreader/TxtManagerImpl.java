package com.hdmb.ireader.txtreader;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.hdmb.ireader.txtreader.bean.TxtError;
import com.hdmb.ireader.txtreader.bean.TxtFile;

import java.lang.reflect.Field;

/**
 * 作者    武博
 * 时间    2016/8/8 0008 15:21
 * 文件    TbReader
 * 描述
 */
public class TxtManagerImpl implements TxtManager {

    private Context context;
    private TxtReaderConfig config;
    private Paint txtPaint;
    private Paint pageNumberPaint;
    private Paint pageTitlePaint;
    private int width;
    private int height;
    // 每页显示的行数
    private int lineSize;
    private TxtFile txtFile;

    private ManagerTransformer transformer;
    private TxtLoadListener txtLoadListener;

    public TxtManagerImpl(Context context, TxtFile txtFile) {
        this.context = context;
        this.txtFile = txtFile;
        config = new TxtReaderConfig();
        txtPaint = new Paint();
        pageNumberPaint = new Paint();
        pageTitlePaint = new Paint();
        initWH();
        initSettings();
    }

    private void initWH() {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        width = dm.widthPixels;
        height = dm.heightPixels;
        if (getConfig().isHideStateBar()) {// 减去状态栏的高度
            int statsBarHeight = getStateBarHeight();
            height = height - statsBarHeight;
        }
    }

    /**
     * 获取状态栏高度
     *
     * @return
     */
    public int getStateBarHeight() {
        Class<?> c = null;
        Object obj = null;
        Field field = null;
        int x = 0;
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            return context.getResources().getDimensionPixelSize(x);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return 0;
    }

    @Override
    public void initSettings() {
        getTextPaint().setAntiAlias(true);// 防止锯齿
        getTextPaint().setFakeBoldText(getConfig().isFontWeight());// 是否加粗
        getTextPaint().setTextSize(getConfig().getTextSize());
        getTextPaint().setColor(getConfig().getTextColor());
        getPageNumberPaint().setColor(getConfig().getPageNumberTextColor());
        getPageNumberPaint().setTextSize(getConfig().getPageNumberTextSize());
        getPageTitlePaint().setColor(getConfig().getPageNumberTextColor());
        getPageTitlePaint().setTextSize(getConfig().getPageTitleBarTextSize());
        getPageTitlePaint().setFakeBoldText(getConfig().isFontWeight());// 是否加粗

        if (getConfig().getFontType() != null) {
            Typeface typeface = null;
            if (getConfig().getFontType().equals(""))
                typeface = Typeface.DEFAULT;
            else
                typeface = Typeface.createFromAsset(context.getAssets(), getConfig().getFontType());
            getTextPaint().setTypeface(typeface);
            getPageNumberPaint().setTypeface(typeface);
            getPageTitlePaint().setTypeface(typeface);
        }
        if (transformer != null) {
            transformer.postResult(true);
        }
        int lineHeight = getConfig().getTextSize() + getConfig().getLineSpacing();
        int linesHeight = getHeight() - (getConfig().getMarginTop() + getConfig().getMarginBottom())
                - (getConfig().getPageTitleBarTextSize() + getConfig().getPageNumberTextSize());
        lineSize = linesHeight / lineHeight;
    }

    @Override
    public int getPart() {
        return txtFile.getPart();
    }

    @Override
    public boolean hasPrevPart() {
        if (txtFile.getPart() > 1)
            return true;
        return false;
    }

    @Override
    public boolean hasNextPart() {
        return true;
    }

    @Override
    public String getTitle() {
        return txtFile.getBookName();
    }

    @Override
    public TxtReaderConfig getConfig() {
        return config;
    }

    @Override
    public Paint getTextPaint() {
        return txtPaint;
    }

    @Override
    public Paint getPageNumberPaint() {
        return pageNumberPaint;
    }

    @Override
    public Paint getPageTitlePaint() {
        return pageTitlePaint;
    }

    @Override
    public int getPageNumberOrTitleSize() {
        return getConfig().getPageNumberTextSize();
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public int getLineSize() {
        return lineSize;
    }

    @Override
    public int getTextSize() {
        return config.getTextSize();
    }

    @Override
    public void setTransformer(Transformer transformer) {
        this.transformer = (ManagerTransformer) transformer;
    }

    @Override
    public void loadTxtFile() {
        transformer.loadTxtFile(new Transformer() {
            @Override
            public void postResult(Boolean t) {
                if (t) {
                    System.out.println("加载文件成功");
                    if (txtLoadListener != null) {
                        txtLoadListener.onLoadSuccess();
                    }
                }
            }

            @Override
            public void postError(TxtError txterror) {
                if (txtLoadListener != null) {
                    txtLoadListener.onError(txterror);
                }
            }
        });
    }

    @Override
    public TxtFile getTxtFile() {
        return txtFile;
    }

    @Override
    public void setTxtFile(TxtFile txtFile) {
        this.txtFile = txtFile;
    }

    @Override
    public void jumpToPage(int pageNumber) {
        transformer.jumpToPage(pageNumber);
    }

    @Override
    public void setTxtLoadListener(TxtLoadListener listener) {
        txtLoadListener = listener;
    }

    @Override
    public void onTxtLoadError(TxtError txterror) {
        if (txtLoadListener != null) {
            txtLoadListener.onError(txterror);
        }
    }

    @Override
    public void separatePage() {
        if (transformer != null) {
            transformer.separatePage();
        }
    }

    @Override
    public void refreshBitmapText() {
        if (transformer != null) {
            initSettings();
            transformer.refreshBitmapText();
        }
    }

    @Override
    public void refreshBitmapBackground() {
        if (transformer != null) {
            initSettings();
            transformer.refreshBitmapBackground();
        }
    }
}
