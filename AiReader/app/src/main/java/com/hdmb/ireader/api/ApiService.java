package com.hdmb.ireader.api;

import com.hdmb.ireader.resp.Article;
import com.hdmb.ireader.resp.ArticleDirectory;
import com.hdmb.ireader.resp.ArticlePartContent;
import com.hdmb.ireader.resp.Category;
import com.hdmb.ireader.resp.CategoryView;
import com.hdmb.ireader.resp.Patch;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * 作者    武博
 * 时间    2016/7/11 0011 14:43
 * 文件    TbReader
 * 描述
 */
public interface ApiService {

    @GET("ireader/api/app/patch/{appVersionName}/{patchVersionName}")
    Observable<List<Patch>> checkPatchs(@Path("appVersionName") String versionName, @Path("patchVersionName") String patchName);

    /**
     * 检索图书
     *
     * @param keyword
     * @return
     */
    @GET("ireader/api/article/query")
    Observable<List<Article>> queryArticles(@Query("kw") String keyword);

    /**
     * 图书目录
     *
     * @param articleId
     * @return
     */
    @GET("ireader/api/article/directory/{articleId}")
    Observable<ArticleDirectory> queryArticleDirectory(@Path("articleId") String articleId);

    /**
     * 图书小节内容
     *
     * @param articleId
     * @param partIndex
     * @return
     */
    @GET("ireader/api/article/content/{articleId}/{partIndex}")
    Observable<ArticlePartContent> queryArticlePartContent(@Path("articleId") String articleId, @Path("partIndex") int partIndex);

    /**
     * 分类列表
     *
     * @return
     */
    @GET("ireader/api/category/list")
    Observable<List<Category>> getCategoryList();

    /**
     * 分类列表
     *
     * @return
     */
    @GET("ireader/api/category/{categoryId}/{pageNumber}")
    Observable<CategoryView> getCategoryView(@Path("categoryId") String categoryId, @Path("pageNumber") int pageNumber);
}
