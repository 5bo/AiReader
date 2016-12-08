package com.hdmb.ireader.ui;

import android.graphics.Color;
import android.graphics.Paint;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * 作者    武博
 * 时间    2016/7/16 0016 17:26
 * 文件    TbReader
 * 描述
 */
public class BookPagesFactory {

    private int mWidth;
    private int mHeight;
    private int mFontSize = 50;
    private int mTextColor = Color.BLACK;
    private int mMarginWidth = 15; // 左右与边缘的距离
    private int mMarginTop = 0; // 上边缘的距离
    private int mMarginBottom = 0; // 下边缘的距离
    private int mLineCount; // 每页可以显示的行数
    private float mVisibleWidth; // 绘制内容的宽
    private float mVisibleHeight; // 绘制内容的高
    private int mLineSpaceing = 2;
    private int mLineHeight;
    private Paint mPaint;
    private File bookFile;

    public BookPagesFactory(int w, int h) {
        this.mWidth = w;
        this.mHeight = h;
    }

    public void setFontSize(int fontSize) {
        this.mFontSize = fontSize;
        mPaint.setTextSize(mFontSize);
    }

    public void setTextColor(int textColor) {
        this.mTextColor = textColor;
        mPaint.setColor(mTextColor);
    }

    public void setMarginTop(int top) {
        mMarginTop = top;
    }

    public void setMarginBottom(int bottom) {
        mMarginBottom = bottom;
    }

    public void openbook(String filepath) {
        init();
        bookFile = new File(filepath);
    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setTextAlign(Paint.Align.LEFT);//设置绘制文字的对齐方向
        mPaint.setTextSize(mFontSize);
        mPaint.setColor(mTextColor);
        mVisibleWidth = mWidth - mMarginWidth * 2;
        mVisibleHeight = mHeight - mMarginTop + mMarginBottom;
        mLineHeight = mFontSize + mLineSpaceing * 2;
        mLineCount = (int) (mVisibleHeight / mLineHeight); // 可显示的行数
    }

    private String readParagraph(BufferedReader reader) throws IOException {
        String str = "";
        if ((str = reader.readLine()) != null) {
            return str;
        }
        return null;
    }

    private String listToString(List<String> strings) {
        StringBuilder sb = new StringBuilder();
        for (String s : strings) {
            sb.append(s).append("\r\n");
        }
        return sb.toString();
    }

    public List<String> getPages() {
        List<String> pages = new ArrayList<>();
        List<String> page = new ArrayList<>();
        BufferedReader reader = null;
        String paragraph;
        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(bookFile), "utf-8"));
            while (page.size() < mLineCount && (paragraph = readParagraph(reader)) != null) {
                while (paragraph != null && paragraph.length() > 0) {
                    int nSize = mPaint.breakText(paragraph, true, mVisibleWidth, null);
                    page.add(paragraph.substring(0, nSize));
                    paragraph = paragraph.substring(nSize);//截取从nSize开始的字符串
                    if (page.size() >= mLineCount) {
                        pages.add(listToString(page));
                        page.clear();
                    }
                }
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null)
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
        return pages;
    }
}