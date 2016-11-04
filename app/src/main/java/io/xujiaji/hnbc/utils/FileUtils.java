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
}
