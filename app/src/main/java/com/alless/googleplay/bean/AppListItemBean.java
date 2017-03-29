package com.alless.googleplay.bean;

/**
 * Created by Administrator on 2017/3/29.
 */

public class AppListItemBean {

    /**
     * des : 搜狐视频-免费高清美剧电影视频播放器随时随地，作球迷领地——6月12日起，一手
     * downloadUrl : app/com.sohu.sohuvideo/com.sohu.sohuvideo.apk
     * iconUrl : app/com.sohu.sohuvideo/icon.jpg
     * id : 1619825
     * name : 搜狐视频
     * packageName : com.sohu.sohuvideo
     * size : 10024247
     * stars : 2.5
     */

    private String des;
    private String downloadUrl;
    private String iconUrl;
    private int id;
    private String name;
    private String packageName;
    private int size;
    private float stars;

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public float getStars() {
        return stars;
    }

    public void setStars(float stars) {
        this.stars = stars;
    }
}
