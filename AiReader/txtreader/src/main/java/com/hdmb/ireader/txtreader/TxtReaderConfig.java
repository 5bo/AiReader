package com.hdmb.ireader.txtreader;

import android.graphics.Color;

/**
 * 作者    武博
 * 时间    2016/8/8 0008 14:54
 * 文件    TbReader
 * 描述
 */
public class TxtReaderConfig {
    private int defaultTextColor = Color.BLACK;
    private int defaultBackgroundColor = com.hdmb.ireader.R.drawable.ic_default_bg;
    // 字体类型
    private String fontType = null;
    // 字体是否加粗
    private boolean fontWeight = false;
    // 字体大小
    private int textSize = 36;
    // 字体颜色
    private int textColor = defaultTextColor;
    // 背景
    private int backgroundColor = Color.WHITE;
    // 行间距
    private int lineSpacing = 15;
    // 距离顶部距离
    private int marginTop = 15;
    // 距离底部的距离
    private int marginBottom = 15;
    // 距离左边的距离
    private int marginLeft = 20;
    // 距离右边的距离
    private int marginRight = 20;
    // 页码字体颜色
    private int pageNumberTextColor = Color.parseColor("#999999");
    // 页码字体大小
    private int pageNumberTextSize = 28;
    private int pageTitleBarTextSize = 28;
    // 是否隐藏状态栏
    private boolean hideStateBar = false;

    public int getDefaultTextColor() {
        return defaultTextColor;
    }

    public void setDefaultTextColor(int defaultTextColor) {
        this.defaultTextColor = defaultTextColor;
    }

    public int getDefaultBackgroundColor() {
        return defaultBackgroundColor;
    }

    public void setDefaultBackgroundColor(int defaultBackgroundColor) {
        this.defaultBackgroundColor = defaultBackgroundColor;
    }

    public String getFontType() {
        return fontType;
    }

    public void setFontType(String fontType) {
        this.fontType = fontType;
    }

    public boolean isFontWeight() {
        return fontWeight;
    }

    public void setFontWeight(boolean fontWeight) {
        this.fontWeight = fontWeight;
    }

    public int getTextSize() {
        return textSize;
    }

    public void setTextSize(int textSize) {
        this.textSize = textSize;
    }

    public int getTextColor() {
        return textColor;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    public int getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public int getLineSpacing() {
        return lineSpacing;
    }

    public void setLineSpacing(int lineSpacing) {
        this.lineSpacing = lineSpacing;
    }

    public int getMarginTop() {
        return marginTop;
    }

    public void setMarginTop(int marginTop) {
        this.marginTop = marginTop;
    }

    public int getMarginBottom() {
        return marginBottom;
    }

    public void setMarginBottom(int marginBottom) {
        this.marginBottom = marginBottom;
    }

    public int getMarginLeft() {
        return marginLeft;
    }

    public void setMarginLeft(int marginLeft) {
        this.marginLeft = marginLeft;
    }

    public int getMarginRight() {
        return marginRight;
    }

    public void setMarginRight(int marginRight) {
        this.marginRight = marginRight;
    }

    public int getPageNumberTextColor() {
        return pageNumberTextColor;
    }

    public void setPageNumberTextColor(int pageNumberTextColor) {
        this.pageNumberTextColor = pageNumberTextColor;
    }

    public int getPageNumberTextSize() {
        return pageNumberTextSize;
    }

    public void setPageNumberTextSize(int pageNumberTextSize) {
        this.pageNumberTextSize = pageNumberTextSize;
    }

    public int getPageTitleBarTextSize() {
        return pageTitleBarTextSize;
    }

    public void setPageTitleBarTextSize(int pageTitleBarTextSize) {
        this.pageTitleBarTextSize = pageTitleBarTextSize;
    }

    public boolean isHideStateBar() {
        return hideStateBar;
    }

    public void setHideStateBar(boolean hideStateBar) {
        this.hideStateBar = hideStateBar;
    }
}
