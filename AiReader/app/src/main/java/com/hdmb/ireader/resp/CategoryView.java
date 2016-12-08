package com.hdmb.ireader.resp;

import java.util.List;

/**
 * 作者    武博
 * 时间    2016/7/25 0025 10:00
 * 文件    TbReader
 * 描述
 */
public class CategoryView {

    String category;// 分类名称，如“鬼话”
    List<Article> articles;// 文章列表
    Pagenation pagenation;// 导航信息

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }

    public Pagenation getPagenation() {
        return pagenation;
    }

    public void setPagenation(Pagenation pagenation) {
        this.pagenation = pagenation;
    }

    @Override
    public String toString() {
        return "CategoryView{" +
                "category='" + category + '\'' +
                ", articles=" + articles +
                ", pagenation=" + pagenation +
                '}';
    }
}
