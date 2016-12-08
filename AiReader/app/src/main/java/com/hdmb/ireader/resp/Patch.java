package com.hdmb.ireader.resp;

/**
 * 作者    武博
 * 时间    2016/7/11 0011 17:03
 * 文件    TbReader
 * 描述
 */
public class Patch {
    int id;
    String versionName;
    String patchName;
    String patchUrl;
    String patchMd5;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public String getPatchName() {
        return patchName;
    }

    public void setPatchName(String patchName) {
        this.patchName = patchName;
    }

    public String getPatchUrl() {
        return patchUrl;
    }

    public void setPatchUrl(String patchUrl) {
        this.patchUrl = patchUrl;
    }

    public String getPatchMd5() {
        return patchMd5;
    }

    public void setPatchMd5(String patchMd5) {
        this.patchMd5 = patchMd5;
    }

    @Override
    public String toString() {
        return "Patch{" +
                "id=" + id +
                ", versionName='" + versionName + '\'' +
                ", patchName='" + patchName + '\'' +
                ", patchUrl='" + patchUrl + '\'' +
                ", patchMd5='" + patchMd5 + '\'' +
                '}';
    }
}
