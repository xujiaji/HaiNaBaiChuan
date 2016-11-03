package io.xujiaji.developersbackpack.service;

import android.app.IntentService;
import android.content.Intent;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import io.xujiaji.developersbackpack.app.App;
import io.xujiaji.developersbackpack.utils.FileUtils;
import io.xujiaji.developersbackpack.utils.LogHelper;
import io.xujiaji.developersbackpack.utils.OtherUtils;
import io.xujiaji.developersbackpack.utils.SharedPreferencesUtil;

/**
 * 下载欢迎页面的图片
 */
public class DownLoadWelPicService extends IntentService {
    public DownLoadWelPicService() {
        super("DownLoadWelPicService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        LogHelper.E("onHandleIntent");
        String imgUrl = intent.getExtras().getString("imgPath");
        if (imgUrl == null) {
            return;
        }
        File cache = new File(FileUtils.getAppCacheDir(App.getAppContext()) + "/" + getImgName(imgUrl));
        if (cache.exists()) {
            SharedPreferencesUtil.saveWelPicPath(cache.getPath());
            LogHelper.E("Welcome图片已存在");
            return;
        }
        try {
            InputStream input = getImageStream(imgUrl);
            if (input != null) {
                OutputStream output = new FileOutputStream(cache);
                int temp;
                while ((temp = input.read()) != -1) {
                    output.write(temp);
                }
                input.close();
                output.close();
                SharedPreferencesUtil.saveWelPicPath(cache.getPath());
                LogHelper.E("成功下载Welcome图片");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getImgName(String imgUrl) {
//        return imgUrl.substring(imgUrl.lastIndexOf('/'), imgUrl.length());
        return OtherUtils.currDay() + imgUrl.substring(imgUrl.lastIndexOf('.'), imgUrl.length());
    }

    private InputStream getImageStream(String path) throws Exception{
        URL url = new URL(path);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setConnectTimeout(5 * 1000);
        conn.setRequestMethod("GET");
        if(conn.getResponseCode() == HttpURLConnection.HTTP_OK){
            return conn.getInputStream();
        }
        return null;
    }
}
