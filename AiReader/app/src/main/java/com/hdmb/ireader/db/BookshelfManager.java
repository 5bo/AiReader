package com.hdmb.ireader.db;

import android.content.Context;
import android.text.TextUtils;

import com.litesuits.orm.db.assit.QueryBuilder;
import com.litesuits.orm.db.assit.WhereBuilder;
import com.litesuits.orm.db.model.ColumnsValue;
import com.litesuits.orm.db.model.ConflictAlgorithm;
import com.hdmb.ireader.resp.Article;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 作者    武博
 * 时间    2016/7/16 0016 16:05
 * 文件    TbReader
 * 描述
 */
public class BookshelfManager {

    /**
     * 获取书架所有图书
     *
     * @return
     */
    public static List<Article> getBookshelfBooks(Context context) {
        List<Article> articles = new ArrayList<>();
        List<BookshelfModel> list = DBManager.getInstance(context).getLiteOrm().query(BookshelfModel.class);
        if (list != null && list.size() > 0) {
            for (BookshelfModel model : list) {
                Article article = new Article();
                article.setId(model.getArticleId());
                article.setName(model.getArticleName());
                article.setAuthor(model.getArticleAuthor());
                article.setStatus(model.getArticleStatus());
                article.setCategory(model.getArticleCategory());
                article.setPartId(model.getPartId());
                article.setPartCount(model.getPartCount());
                article.setOfflineId(model.getOfflineId());
                articles.add(article);
            }
        }
        return articles;
    }

    /**
     * 根据图书Id更新已更新到的小节id
     *
     * @param context
     * @param articleId
     * @param partId
     * @return
     */
    public static long updatePartIdByArticleId(Context context, String articleId, int partId) {
        WhereBuilder wb = new WhereBuilder(BookshelfModel.class, "articleId = ?", new String[]{articleId});
        Map<String, Object> map = new HashMap<>();
        map.put("partId", partId);
        ColumnsValue cv = new ColumnsValue(map);
        int row = DBManager.getInstance(context).getLiteOrm().update(wb, cv, ConflictAlgorithm.None);
        return row;
    }

    /**
     * 查询书架是否有该书
     *
     * @param context
     * @param articleId
     * @return
     */
    public static boolean existsArticle(Context context, String articleId) {
        QueryBuilder<BookshelfModel> qb = new QueryBuilder<>(BookshelfModel.class).whereEquals("articleId", articleId);
        List<BookshelfModel> list = DBManager.getInstance(context).getLiteOrm().query(qb);
        if (list != null && list.size() > 0)
            return true;
        else
            return false;
    }

    /**
     * 加入书架
     */
    public static long addBookshelf(Context context, Article article) {
        BookshelfModel model = new BookshelfModel();
        model.setArticleId(article.getId());
        model.setArticleName(article.getName());
        model.setArticleAuthor(article.getAuthor());
        model.setArticleStatus(article.getStatus());
        model.setArticleCategory(article.getCategory());
        model.setPartId(article.getPartId());
        model.setPartCount(article.getPartCount());
        return DBManager.getInstance(context).getLiteOrm().insert(model);
    }

    /**
     * 更新书架上的某个图书
     *
     * @param context
     * @param article
     * @return
     */
    public static int updateBookshelfForArticle(Context context, Article article) {
        WhereBuilder wb = new WhereBuilder(BookshelfModel.class, "articleId = ?", new String[]{article.getId()});
        Map<String, Object> map = new HashMap<>();
        if (!TextUtils.isEmpty(article.getAuthor()))
            map.put("articleAuthor", article.getAuthor());
        if (!TextUtils.isEmpty(article.getCategory()))
            map.put("articleCategory", article.getCategory());
        if (!TextUtils.isEmpty(article.getStatus()))
            map.put("articleStatus", article.getStatus());
        if (article.getPartId() != 0)
            map.put("partId", article.getPartId());
        if (article.getPartCount() != 0)
            map.put("partCount", article.getPartCount());
        if (article.getOfflineId() != 0)
            map.put("offlineId", article.getOfflineId());
        ColumnsValue cv = new ColumnsValue(map);
        int row = DBManager.getInstance(context).getLiteOrm().update(wb, cv, ConflictAlgorithm.None);
        return row;
    }

    /**
     * 根据图书Id更新已更新多少小节
     *
     * @param context
     * @param articleId
     * @param partCount
     * @return
     */
    public static long updateArticlePartsByArticleId(Context context, String articleId, int partCount) {
        WhereBuilder wb = new WhereBuilder(BookshelfModel.class, "articleId = ?", new String[]{articleId});
        Map<String, Object> map = new HashMap<>();
        map.put("partCount", partCount);
        ColumnsValue cv = new ColumnsValue(map);
        int row = DBManager.getInstance(context).getLiteOrm().update(wb, cv, ConflictAlgorithm.None);
        return row;
    }

    /**
     * 根据图书Id更新已离线多少小节
     *
     * @param context
     * @param articleId
     * @param offlineId
     * @return
     */
    public static long updateOfflineIdByArticleId(Context context, String articleId, int offlineId) {
        WhereBuilder wb = new WhereBuilder(BookshelfModel.class, "articleId = ?", new String[]{articleId});
        Map<String, Object> map = new HashMap<>();
        map.put("offlineId", offlineId);
        ColumnsValue cv = new ColumnsValue(map);
        int row = DBManager.getInstance(context).getLiteOrm().update(wb, cv, ConflictAlgorithm.None);
        return row;
    }

}
