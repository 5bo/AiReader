package com.hdmb.ireader.txtreader.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者    武博
 * 时间    2016/8/8 0008 16:59
 * 文件    TbReader
 * 描述
 */
public class ParagraphCache {
    List<Paragraph> mParagraphs;

    public ParagraphCache() {
        mParagraphs = new ArrayList<>();
    }

    public void addParagraph(Paragraph paragraph) {
        mParagraphs.add(paragraph);
    }

    public void clear() {
        mParagraphs.clear();
        System.gc();
    }

    public int getParagraphSize(){
        return mParagraphs.size();
    }

    public Paragraph getParagraphByIndex(int index) {
        if (index >= 0 && index < mParagraphs.size()) {
            return mParagraphs.get(index);
        }
        System.out.println("Paragraphindex:" + index);
        System.out.println("paragraphs.size():" + mParagraphs.size());
        return null;
    }

    public boolean hasParagraphCache() {
        return mParagraphs.size() != 0;
    }
}
