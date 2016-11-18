/*
 * Copyright 2016 XuJiaji
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

package io.xujiaji.hnbc.service;

import android.app.IntentService;
import android.content.Intent;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import io.xujiaji.hnbc.utils.FileUtils;
import io.xujiaji.hnbc.utils.OtherUtils;
import io.xujiaji.hnbc.utils.SharedPreferencesUtil;

/**
 * 下载欢迎页面的图片
 */
public class DownLoadWelPicService extends IntentService {
    public DownLoadWelPicService() {
        super("DownLoadWelPicService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String imgUrl = intent.getExtras().getString("imgPath");
        if (imgUrl == null) {
            return;
        }
        File cache = new File(FileUtils.getAppCacheDir() + "/" + getImgName(imgUrl));
        if (cache.exists()) {
            SharedPreferencesUtil.saveWelPicPath(cache.getPath());
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
