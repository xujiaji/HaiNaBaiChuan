/*
 * Copyright 2018 XuJiaji
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.xujiaji.hnbc.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;

import java.io.File;
import java.text.DecimalFormat;

import io.xujiaji.hnbc.app.App;
import rx.Observable;
import rx.functions.Func1;

/**
 * 文件管理工具类
 */
public class FileUtils {
    private FileUtils() {
    }

    public static String getAppCacheDir() {
        String cachePath = null;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            cachePath = App.getAppContext().getExternalCacheDir().getPath();
        } else {
            cachePath = App.getAppContext().getCacheDir().getPath();
        }
        return cachePath;
    }

    /**
     * 判断是否是gif图片
     * @return
     */
    public static boolean isGif(String path) {
        if (path == null) return false;
        int pathLen = path.length();
        try{
            return (".GIF".equals(path.substring(pathLen - 4, pathLen).toUpperCase()));
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 根据Uri获取图片绝对路径，解决Android4.4以上版本Uri转换
     * @param imageUri
     * @author yaoxing
     * @date 2014-10-12
     */
    @TargetApi(19)
    public static String getImageAbsolutePath(Activity context, Uri imageUri) {
        if (context == null || imageUri == null)
            return null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT && DocumentsContract.isDocumentUri(context, imageUri)) {
            if (isExternalStorageDocument(imageUri)) {
                String docId = DocumentsContract.getDocumentId(imageUri);
                String[] split = docId.split(":");
                String type = split[0];
                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            } else if (isDownloadsDocument(imageUri)) {
                String id = DocumentsContract.getDocumentId(imageUri);
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));
                return getDataColumn(context, contentUri, null, null);
            } else if (isMediaDocument(imageUri)) {
                String docId = DocumentsContract.getDocumentId(imageUri);
                String[] split = docId.split(":");
                String type = split[0];
                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }
                String selection = MediaStore.Images.Media._ID + "=?";
                String[] selectionArgs = new String[] { split[1] };
                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        } // MediaStore (and general)
        else if ("content".equalsIgnoreCase(imageUri.getScheme())) {
            // Return the remote address
            if (isGooglePhotosUri(imageUri))
                return imageUri.getLastPathSegment();
            return getDataColumn(context, imageUri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(imageUri.getScheme())) {
            return imageUri.getPath();
        }
        return null;
    }

    public static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
        Cursor cursor = null;
        String column = MediaStore.Images.Media.DATA;
        String[] projection = { column };
        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }

    enum SizeType {
        B, KB, MB, GB, TB
    }

    /**
     * 转换获取的文件单位
     *
     * @param size 获取的文件大小，单位（byte）
     * @param type 需要转换到哪个大小单位
     * @return 返回转换后的值
     */
    public static double formatSize(long size, SizeType type) {
        double formatSized = 0;
        switch (type) {
            case B:
                formatSized = size;
                break;
            case KB:
                formatSized = size / 1024.0;
                break;
            case MB:
                formatSized = size / 1024.0 / 1024.0;
                break;
            case GB:
                formatSized = size / 1024.0 / 1024.0 / 1024.0;
                break;
            case TB:
                formatSized = size / 1024.0 / 1024.0 / 1024.0 / 1024.0;
                break;
        }
        return formatSized;
    }

    /**
     * 自动判断因该转换的单位
     *
     * @param size 大小
     * @return 转换后的大小
     */
    public static String formatSize(long size) {
        if (size == 0L) {
            return "0B";
        }
        DecimalFormat df = new DecimalFormat("#.00");
        if (size < 1024) {
            return df.format(formatSize(size, SizeType.B)) + "B";
        }
        if (size < 1024 * 1024) {
            return df.format(formatSize(size, SizeType.KB)) + "KB";
        }
        if (size < 1024 * 1024 * 1024) {
            return df.format(formatSize(size, SizeType.MB)) + "MB";
        }

        if (size >= 1024 * 1024 * 1024) {
            double formatSized = formatSize(size, SizeType.GB);
            if (formatSized >= 1024) {
                return df.format(formatSized / 1024) + "TB";
            }
            return df.format(formatSized) + "GB";
        }
        return null;
    }


    public static File[] getAllCacheFile() {
        String packagePath = "/data/data/" + App.getAppContext().getPackageName();
        File[] files = new File[5];
        files[0] = App.getAppContext().getExternalCacheDir();
        files[1] = new File(packagePath + "/app_webview");
        files[2] = new File(packagePath + "/cache");
        files[3] = new File(packagePath + "/code_cache");
        files[4] = new File(packagePath + "/files");
        return files;
    }


    /**
     * rxjava递归查询
     * @param f
     * @return
     */
    public static Observable<File> listFiles(File f){
        if(f.isDirectory()){
            return Observable.from(f.listFiles()).flatMap(new Func1<File, Observable<File>>() {
                @Override
                public Observable<File> call(File file) {
                    return listFiles(file);
                }
            });
        } else {
            return Observable.just(f).filter(new Func1<File, Boolean>() {
                @Override
                public Boolean call(File file) {
                    return file.exists() && file.canRead();
                }
            });
        }
    }
}
