package com.hdmb.ireader.resp;

import com.google.gson.annotations.SerializedName;

/**
 * 作者    武博
 * 时间    2016/7/15 0015 15:22
 * 文件    TbReader
 * 描述
 */
public class ArticlePartContent {

    // 图书索引
    String articleId = "";
    // 图书名称
    String articleName = "";
    // 小节索引
    @SerializedName("partIndex")
    int partId = 0;
    // 小节内容
    String content = "";

    Navigation navigation;

    public String getArticleId() {
        return articleId;
    }

    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }

    public String getArticleName() {
        return articleName;
    }

    public void setArticleName(String articleName) {
        this.articleName = articleName;
    }

    public int getPartId() {
        return partId;
    }

    public void setPartId(int partId) {
        this.partId = partId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


    public Navigation getNavigation() {
        return navigation;
    }

    public void setNavigation(Navigation navigation) {
        this.navigation = navigation;
    }

    @Override
    public String toString() {
        return "ArticlePartContent{" +
                "articleId='" + articleId + '\'' +
                ", articleName='" + articleName + '\'' +
                ", readPartId=" + partId +
                ", content='" + content + '\'' +
                ", navigation=" + navigation +
                '}';
    }

    public static class Navigation {
        // 小节总数，如15
        int total;
        // 当前小节索引，如10
        int current;
        // 前一小节索引，如9
        int prev;
        // 后一小节索引，如11
        int next;

        public Navigation(int total, int current, int prev, int next) {
            this.total = total;
            this.current = current;
            this.prev = prev;
            this.next = next;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public int getCurrent() {
            return current;
        }

        public void setCurrent(int current) {
            this.current = current;
        }

        public int getPrev() {
            return prev;
        }

        public void setPrev(int prev) {
            this.prev = prev;
        }

        public int getNext() {
            return next;
        }

        public void setNext(int next) {
            this.next = next;
        }

        @Override
        public String toString() {
            return "Navigation{" +
                    "total=" + total +
                    ", current=" + current +
                    ", prev=" + prev +
                    ", next=" + next +
                    '}';
        }
    }
}
