package com.hdmb.ireader.resp;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 作者    武博
 * 时间    2016/7/14 0014 14:53
 * 文件    TbReader
 * 描述    图书
 */
public class Article implements Parcelable {
    String id;
    String name;
    String author;
    String status;
    String category;
    int readPartId;
    int partCount;
    int offlineId;
    public Article() {
    }

    protected Article(Parcel in) {
        id = in.readString();
        name = in.readString();
        author = in.readString();
        status = in.readString();
        category = in.readString();
        readPartId = in.readInt();
        partCount = in.readInt();
        offlineId = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(author);
        dest.writeString(status);
        dest.writeString(category);
        dest.writeInt(readPartId);
        dest.writeInt(partCount);
        dest.writeInt(offlineId);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Article> CREATOR = new Creator<Article>() {
        @Override
        public Article createFromParcel(Parcel in) {
            return new Article(in);
        }

        @Override
        public Article[] newArray(int size) {
            return new Article[size];
        }
    };

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

    public int getPartId() {
        if (readPartId == 0)
            readPartId = 1;
        return readPartId;
    }

    public void setPartId(int partId) {
        this.readPartId = partId;
    }

    public static Creator<Article> getCREATOR() {
        return CREATOR;
    }

    public int getPartCount() {
        return partCount;
    }

    public void setPartCount(int partCount) {
        this.partCount = partCount;
    }

    public int getOfflineId() {
        return offlineId;
    }

    public void setOfflineId(int offlineId) {
        this.offlineId = offlineId;
    }

    @Override
    public String toString() {
        return "Article{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", author='" + author + '\'' +
                ", status='" + status + '\'' +
                ", category='" + category + '\'' +
                ", readPartId=" + readPartId +
                ", partCount='" + partCount + '\'' +
                ", offlineId='" + offlineId + '\'' +
                '}';
    }
}
