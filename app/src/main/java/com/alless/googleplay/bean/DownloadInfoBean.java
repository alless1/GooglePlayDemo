package com.alless.googleplay.bean;

/**
 * Created by Administrator on 2017/4/1.
 */

public class DownloadInfoBean {
    private int status; //下载状态
    private int progress;//下载进度
    private String packageName;//下载apk的包名
    private int size;//下载apk的大小
    private String filePath;//apk 的文件路径

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
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

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
