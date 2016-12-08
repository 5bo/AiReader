package com.hdmb.ireader.txtreader;

/**
 * 作者    武博
 * 时间    2016/8/8 0008 15:42
 * 文件    TbReader
 * 描述
 */
public interface ManagerTransformer extends Transformer {

    void loadTxtFile(Transformer transformer);

    void jumpToPage(int pageNumber);

    void separatePage();

    void refreshBitmapText();

    void refreshBitmapBackground();
}
