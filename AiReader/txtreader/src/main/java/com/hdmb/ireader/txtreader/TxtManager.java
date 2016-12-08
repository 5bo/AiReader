package com.hdmb.ireader.txtreader;

import android.graphics.Paint;

import com.hdmb.ireader.txtreader.bean.TxtError;
import com.hdmb.ireader.txtreader.bean.TxtFile;

/**
 * 作者    武博
 * 时间    2016/8/5 0005 22:05
 * 文件    TbReader
 * 描述
 */
public interface TxtManager {
    // 初始化设置
    void initSettings();

    // 获取当前章节
    int getPart();

    // 是否有上一章节
    boolean hasPrevPart();

    // 是否有下一章节
    boolean hasNextPart();

    // 获取标题
    String getTitle();

    // 获取配置信息
    TxtReaderConfig getConfig();

    // 获取文字画笔
    Paint getTextPaint();

    // 获取页码画笔
    Paint getPageNumberPaint();

    // 获取标题画笔
    Paint getPageTitlePaint();

    int getPageNumberOrTitleSize();

    // 获取View的宽度
    int getWidth();

    // 获取View的高度
    int getHeight();

    // 获取总行数
    int getLineSize();

    // 获取字体大小
    int getTextSize();

    void setTransformer(Transformer transformer);

    // 加载txt文件
    void loadTxtFile();

    TxtFile getTxtFile();

    void setTxtFile(TxtFile txtFile);

    // 跳转至pageNumber页
    void jumpToPage(int pageNumber);

    void setTxtLoadListener(TxtLoadListener listener);

    void onTxtLoadError(TxtError txterror);

    void separatePage();

    void refreshBitmapText();

    void refreshBitmapBackground();
}
