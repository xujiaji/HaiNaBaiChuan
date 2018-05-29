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

package io.xujiaji.hnbc.service;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import io.xujiaji.hnbc.app.App;
import io.xujiaji.hnbc.utils.FileUtils;
import io.xujiaji.hnbc.utils.FirHelper;

/**
 * 更新服务
 */
public class UpdateService extends IntentService {
    public static final String UPDATE_URL = "update_url";
    public static final String APK_LOCAL = "apk_local";
    private String appName = "hnbc.apk";

    public UpdateService() {
        super(UpdateService.class.getSimpleName());
    }

    public static void start(String url) {
        Intent intent = new Intent(App.getAppContext(), UpdateService.class);
        intent.putExtra(UPDATE_URL, url);
        App.getAppContext().startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        try {
            URL url = new URL(intent.getStringExtra(UPDATE_URL));
            // 创建连接
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.connect();
            // 获取文件大小
            int length = conn.getContentLength();
            // 创建输入流
            InputStream is = conn.getInputStream();
            File file = new File(FileUtils.getAppCacheDir());
            File apkFile = new File(file, appName);
            FileOutputStream fos = new FileOutputStream(apkFile);
            int count = 0;
            int progress;
            // 缓存
            byte buf[] = new byte[1024];
            while (true) {
                int numread = is.read(buf);
                count += numread;
                // 计算进度条位置
                progress = (int) (((float) count / length) * 100);
                Intent intent1 = new Intent("io.xujiaji.hnbc.update_progress");
                if (numread <= 0) {
                    // 下载完成
                    FirHelper.ProgressDialogUtil.dismiss();
                    Log.e("UpdateService", "update apk = " + apkFile.getAbsolutePath());
                    intent1.putExtra(APK_LOCAL, apkFile.getAbsolutePath());
                    sendBroadcast(intent1);
                    break;
                }

                // 写入文件
                fos.write(buf, 0, numread);
                // 更新进度
                FirHelper.ProgressDialogUtil.setProgress(progress);
            }
            fos.close();
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
