package com.hdmb.ireader.resp;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 作者    武博
 * 时间    2016/7/15 0015 10:02
 * 文件    TbReader
 * 描述
 */
public class ArticleDirectory {

    String id;
    String name;
    String author;
    String status;
    String category;
    @SerializedName("partIndexs")
    List<Integer> parts;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<Integer> getParts() {
        return parts;
    }

    public void setParts(List<Integer> parts) {
        this.parts = parts;
    }

    @Override
    public String toString() {
        return "ArticleDirectory{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", author='" + author + '\'' +
                ", status='" + status + '\'' +
                ", category='" + category + '\'' +
                ", partCount=" + parts +
                '}';
    }
}
