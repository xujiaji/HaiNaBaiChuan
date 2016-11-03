package io.xujiaji.hnbc.utils;

import android.content.Context;
import android.os.Environment;

/**
 * 文件管理工具类
 */
public class FileUtils {
    private FileUtils() {
    }

    public static String getAppCacheDir(Context context) {
        String cachePath = null;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            cachePath = context.getExternalCacheDir().getPath();
        } else {
            cachePath = context.getCacheDir().getPath();
        }
        return cachePath;
    }

//    LogHelper.E("Environment.getDataDirectory() = " + Environment.getDataDirectory());
//    LogHelper.E("Environment.getExternalStorageState() = " + Environment.getExternalStorageState());
//    LogHelper.E("Environment.getRootDirectory()" + Environment.getRootDirectory());
//    LogHelper.E("Environment.getDownloadCacheDirectory() = " + Environment.getDownloadCacheDirectory());
//    LogHelper.E("Environment.getExternalStorageDirectory() = " + Environment.getExternalStorageDirectory());
//
//    LogHelper.E("getExternalFilesDir(Environment.DIRECTORY_PICTURES) = " + getExternalFilesDir(Environment.DIRECTORY_PICTURES));
//    LogHelper.E("getFilesDir() = " + getFilesDir());
//    LogHelper.E("getCacheDir() = " + getCacheDir());
//    LogHelper.E("getExternalCacheDir() = " + getExternalCacheDir());
}
