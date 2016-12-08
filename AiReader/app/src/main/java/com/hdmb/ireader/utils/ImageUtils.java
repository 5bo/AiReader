package com.hdmb.ireader.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;

/**
 * 作者    武博
 * 时间    2016/7/15 0015 13:55
 * 文件    TbReader
 * 描述
 */
public class ImageUtils {
    /**
     * 获得相应大小的Bitmap
     **/
    public static Bitmap getBitmap(Context context, int id, int width, int height) {
        // 生成一张白纸
        Bitmap paper = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        // 生成一块画板
        Canvas board = new Canvas(paper); // 把白纸贴到画板上
        // 要画的图片
        Drawable image = context.getResources().getDrawable(id);
        // 设置所画图片的宽高
        image.setBounds(0, 0, width, height);
        // 将图片交给画板，画板会将图片贴在白纸上
        image.draw(board);
        // 图片已经贴在白纸上了，我们需要的是纸，而不是画板
        return paper;
    }
}
