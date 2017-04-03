package com.alless.googleplay.manager;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import com.alless.googleplay.app.Constant;
import com.alless.googleplay.bean.DownloadInfoBean;

import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Observer;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/4/1.
 */

public class DownloadManager {
    private static final String TAG = "DownloadManager";
    public static final int STATE_UN_DOWNLOAD = 0;//未下载
    public static final int STATE_DOWNLOADING = 1;//下载中
    public static final int STATE_PAUSE = 2;//暂停下载
    public static final int STATE_WAITING = 3;//等待下载
    public static final int STATE_FAILED = 4;//下载失败
    public static final int STATE_DOWNLOADED = 5;//下载完成
    public static final int STATE_INSTALLED = 6;//已安装
    private static final String DOWNLOAD_DIRECTORY = Environment.getExternalStorageDirectory() + "/Android/data/com.alless.googleplay/apk/";
    private static DownloadManager sDownloadManager;
    //作为被观察者类
    //维护一个观察者集合,对应包名
    private HashMap<String, Observer> mObserverHashMap = new HashMap<String, Observer>();

    //添加观察者
    public void addObserver(String packageName, Observer observer) {
        mObserverHashMap.put(packageName, observer);
    }

    //删除观察者
    public void removeObserver(String packageName) {
        mObserverHashMap.remove(packageName);
    }

    //通知观察者
    public void notifyObserver(String packageName, DownloadInfoBean downloadInfoBean) {
        Observer observer = mObserverHashMap.get(packageName);
        if (observer != null) {
            observer.update(null,downloadInfoBean);
        }
    }

    //保存对应包名的app的下载信息
    private HashMap<String, DownloadInfoBean> mDownHashMap = new HashMap<String, DownloadInfoBean>();
    private final OkHttpClient mOkHttpClient;

    private DownloadManager() {
        mOkHttpClient = new OkHttpClient();

    }

    public static DownloadManager getInstance() {
        if (sDownloadManager == null) {
            synchronized (DownloadManager.class) {
                if (sDownloadManager == null) {
                    sDownloadManager = new DownloadManager();
                }
            }
        }
        return sDownloadManager;
    }

    /**
     * 创建存放下载的apk文件夹
     */
    public void createDownloadDir() {
        File file = new File(DOWNLOAD_DIRECTORY);
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    /**
     * 初始化apk的下载安装情况,返回状态结果
     */
    public DownloadInfoBean initDownloadInfo(Context context, String packageName, int size, String downloadUrl) {
        //检查缓存
        if (mDownHashMap.get(packageName) != null) {
            return mDownHashMap.get(packageName);
        }
        //保存数据
        DownloadInfoBean downloadInfoBean = new DownloadInfoBean();
        downloadInfoBean.setPackageName(packageName);
        downloadInfoBean.setSize(size);
        downloadInfoBean.setDownloadUrl(downloadUrl);

        //检查安装状态
        if (isInstalled(context, packageName)) {
            //保存已安装状态
            downloadInfoBean.setStatus(STATE_INSTALLED);
            //检查是否下载完成
        } else if (isDownloaded(downloadInfoBean)) {
            //保存已下载完成状态
            downloadInfoBean.setStatus(STATE_DOWNLOADED);
        }else{
            //其他未下载完成状态
            downloadInfoBean.setStatus(STATE_UN_DOWNLOAD);
        }
        mDownHashMap.put(packageName, downloadInfoBean);
        return downloadInfoBean;

    }

    /**
     * 判断apk是否已安装
     */
    public boolean isInstalled(Context context, String packageName) {
        //通过包管理器如果能获取到包名的信息,说明存在apk.否则会抛出异常.
        try {
            context.getPackageManager().getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);
            return true;//没有异常就是存在
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 判断一个apk是否完全下载
     */
    private boolean isDownloaded(DownloadInfoBean downloadInfoBean) {
        //看本地存储文件,并且核实大小
        String path = DOWNLOAD_DIRECTORY + downloadInfoBean.getPackageName()+".apk";
        downloadInfoBean.setFilePath(path);
        File file = new File(path);
        if (file.exists()) {
            //判断大小
            if (file.length() == downloadInfoBean.getSize()) {
                return true;
            }else {
                //保存下载的进度
                downloadInfoBean.setProgress((int) file.length());
                return false;
            }
        }
        return false;
    }

    /**
     * 打开指定包名的apk
     */
    public void openApp(Context context, String packageName) {
        Intent launchIntentForPackage = context.getPackageManager().getLaunchIntentForPackage(packageName);
        context.startActivity(launchIntentForPackage);
    }

    /**
     *点击按钮 处理相应状态
     */
    public void handleDownloadAction(Context context, String packageName) {
        //从集合中获取信息
        DownloadInfoBean downloadInfoBean = mDownHashMap.get(packageName);
        switch (downloadInfoBean.getStatus()) {
            //已安装---->打开应用
            case STATE_INSTALLED:
                openApp(context,packageName);
                break;
            //已下载---->安装应用
            case STATE_DOWNLOADED:
                installApk(context, downloadInfoBean);
                break;
            //未下载--->下载应用
            case STATE_UN_DOWNLOAD:
                Log.d(TAG, "handleDownloadAction: "+"未下载完成");
                downloadApk(downloadInfoBean);
                break;
            //下载中---->暂停
            case STATE_DOWNLOADING:
                pauseDownload(downloadInfoBean);
                break;
            //暂停---->下载
            case STATE_PAUSE:
                downloadApk(downloadInfoBean);
                break;
            //失败---->下载
            case STATE_FAILED:
                downloadApk(downloadInfoBean);
                break;
            //等待---->取消
            case STATE_WAITING:
                cancelDownload(downloadInfoBean);
                break;

        }
    }

    private void cancelDownload(DownloadInfoBean downloadInfoBean) {
        //移除完更新状态
        downloadInfoBean.setStatus(STATE_UN_DOWNLOAD);
        notifyObserver(downloadInfoBean.getPackageName(), downloadInfoBean);

    }

    private void pauseDownload(DownloadInfoBean downloadInfoBean) {
        //把状态更新成暂停,然后通知UI刷新
        downloadInfoBean.setStatus(STATE_PAUSE);
        notifyObserver(downloadInfoBean.getPackageName(),downloadInfoBean);

    }

    /**
     * 下载apk 并保存进度
     * @param downloadInfoBean
     */
    private void downloadApk(final DownloadInfoBean downloadInfoBean) {
        //进入等待状态
        downloadInfoBean.setStatus(STATE_WAITING);
        //通知ui刷新状态 显示“等待”
        notifyObserver(downloadInfoBean.getPackageName(), downloadInfoBean);//通知观察者更新

        //创建一个子线程下载
        new Thread(){
            @Override
            public void run() {
                //range断点续传的起始位置
                String url = Constant.URL_DOWNLOAD + downloadInfoBean.getDownloadUrl() + "&range=" + downloadInfoBean.getProgress();
                FileOutputStream fileOutputStream = null;
                InputStream inputStream = null;
                File file = new File(downloadInfoBean.getFilePath());
                Log.d(TAG, "downloadInfoBean.getFilePath(): "+downloadInfoBean.getFilePath());
                    try {
                        if (!file.exists()){
                            file.createNewFile();
                        }
                        Request request = new Request.Builder().get().url(url).build();
                        Response response = mOkHttpClient.newCall(request).execute();
                        if (response.isSuccessful()) {
                            inputStream = response.body().byteStream();
                            fileOutputStream = new FileOutputStream(file, true);
                            byte[] buffer = new byte[1024*8];
                            int len = -1;
                            while ((len = inputStream.read(buffer)) != -1) {
                                Log.d(TAG, "len:"+len);
                                //检查状态如果是暂停，跳出循环
                                if (downloadInfoBean.getStatus() == STATE_PAUSE) {
                                    return;//直接返回
                                }

                                fileOutputStream.write(buffer, 0, len);
                                //保存进度
                                downloadInfoBean.setProgress( downloadInfoBean.getProgress() +len);
                                //保存状态
                                downloadInfoBean.setStatus(STATE_DOWNLOADING);
                                //通知观察者
                                notifyObserver(downloadInfoBean.getPackageName(),downloadInfoBean);
                            }
                            //下载完成设置状态
                            downloadInfoBean.setStatus(STATE_DOWNLOADED);
                                //通知观察者
                            notifyObserver(downloadInfoBean.getPackageName(),downloadInfoBean);
                        }

                    } catch (IOException e) {
                        //下载失败,设置状态
                        downloadInfoBean.setStatus(STATE_FAILED);
                        //通知观察者
                        notifyObserver(downloadInfoBean.getPackageName(),downloadInfoBean);

                        e.printStackTrace();
                    } finally {
                        closeStream(inputStream);
                        closeStream(fileOutputStream);
                    }

            }
        }.start();
    }

    private void closeStream(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 安装apk
     */
    private void installApk(Context context, DownloadInfoBean downloadInfoBean) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        File file = new File(downloadInfoBean.getFilePath());
        if (file.exists()) {
            intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
            context.startActivity(intent);
        }
    }

}
