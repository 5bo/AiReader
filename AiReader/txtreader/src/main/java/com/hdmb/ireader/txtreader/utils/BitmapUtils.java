package com.hdmb.ireader.txtreader.utils;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.hdmb.ireader.txtreader.TxtManager;
import com.hdmb.ireader.txtreader.bean.LineChar;

import java.util.List;

/**
 * 作者    武博
 * 时间    2016/8/10 0010 14:30
 * 文件    TbReader
 * 描述
 */
public class BitmapUtils {

    /**
     * @param pageIndex
     * @param pageNums
     * @param linesData
     * @param manager
     * @param pageBitmap
     * @return
     */
    public static Bitmap getPageBitmapWidthLines(int pageIndex, int pageNums, List<LineChar> linesData,
                                                 TxtManager manager, Bitmap pageBitmap) {
        if (linesData == null || linesData.size() == 0 || manager == null || pageBitmap == null)
            return null;
        int viewwith = manager.getWidth();
        int viewheigh = manager.getHeight();
        Rect srcRect = new Rect(0, 0, viewwith, viewheigh);// 截取bmp1中的矩形区域
        Rect dstRect = new Rect(0, 0, viewwith, viewheigh);// bmp1在目标画布中的位置
        Bitmap mThispage = pageBitmap.copy(Bitmap.Config.RGB_565, true);
        Canvas nextcavan = new Canvas(mThispage);
        int titleY = manager.getConfig().getMarginTop() + manager.getPageNumberOrTitleSize();
        // 画出标题
        nextcavan.drawText(manager.getTitle(), manager.getConfig().getMarginLeft(),
                titleY, manager.getPageTitlePaint());

        int contentY = titleY + manager.getTextSize() + manager.getConfig().getLineSpacing();
        // 画出内容
        int nums = manager.getLineSize() > linesData.size() ? linesData.size() : manager.getLineSize();
        int lineHeight = manager.getTextSize() + manager.getConfig().getLineSpacing();
        for (int i = 0; i < nums; i++) {
            nextcavan.drawText(linesData.get(i).getLineString(), manager.getConfig().getMarginLeft(),
                    contentY + i * lineHeight, manager.getTextPaint());
        }
        String indexmsg = "";
        if (pageNums == -1) {// 页数为-1时说明还没有完成分页
            indexmsg = "-" + pageIndex + "-";
        } else {
            indexmsg = pageIndex + "/" + pageNums;
        }
        // 画出页码
        nextcavan.drawText(indexmsg, manager.getConfig().getMarginLeft(), manager.getHeight() - 10,
                manager.getPageNumberPaint());
        nextcavan.drawBitmap(mThispage, srcRect, dstRect, manager.getTextPaint());
        return mThispage;
    }

    /**
     * 背景图片资源平铺
     *
     * @param res
     * @param resId
     * @param bitmapWidth
     * @param bitmapHeight
     * @return
     */
    public static Bitmap createBitmapTiledBackground(Resources res, int resId, int bitmapWidth, int bitmapHeight) {
        Bitmap bitmap = BitmapFactory.decodeResource(res, resId);
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int[] color = new int[width * height];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                color[y * width + x] = bitmap.getPixel(x, y);
            }
        }
        int[] colors = new int[bitmapWidth * bitmapHeight];
        for (int y = 0, size = bitmapWidth * bitmapHeight, border = width * height, index = 0; y < size; y++) {
            if (index == border) {
                index = 0;
            }
            colors[y] = color[index];
            index++;
        }

        Bitmap pageBitmap = Bitmap.createBitmap(colors, bitmapWidth, bitmapHeight, Bitmap.Config.RGB_565);
        return pageBitmap;
    }

}
