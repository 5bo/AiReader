package com.hdmb.ireader.manager;

import android.os.Environment;

import com.hdmb.ireader.utils.Storeage;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * 作者    武博
 * 时间    2016/7/15 0015 15:39
 * 文件    TbReader
 * 描述
 */
public class ArticleManager {

    private static final String charsetCode = "utf-8";
    private static final String TAG = "ArticleManager";
    private static ArticleManager instance;
    private static String ARTICLE_PATH = Environment.getExternalStorageDirectory().getPath() + Storeage.CACHE_PATH + "/article/";

    private ArticleManager() {
        File file = new File(ARTICLE_PATH);
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    public static ArticleManager getInstance() {
        if (instance == null)
            instance = new ArticleManager();
        return instance;
    }

    public String getPartFilePath(String articleId, int partId) {
        String fileName = partId + ".txt";
        File file = new File(ARTICLE_PATH + articleId + "/", fileName);
        if (file.exists())
            return file.getAbsolutePath();
        return "";
    }

    public String cachePartContent(String articleId, int partId, String content) {
        String fileName = partId + ".txt";
        File dir = new File(ARTICLE_PATH + articleId + "/");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File file = new File(dir, fileName);
        try {
            RandomAccessFile raf = new RandomAccessFile(file, "rw");
            raf.write(content.getBytes(charsetCode));
            raf.close();
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
        return file.getAbsolutePath();
    }
}
