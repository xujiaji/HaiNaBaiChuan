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

import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import io.xujiaji.hnbc.app.App;

/**
 * Toast工具类
 */
public final class ToastUtil {
    private static volatile ToastUtil mToastUtil = null;
    private Toast mToast = null;
    protected Handler handler = new Handler(Looper.getMainLooper());

    /**
     * 获取实例
     */
    public static ToastUtil getInstance() {
        if (mToastUtil == null) {
            synchronized (ToastUtil.class) {
                mToastUtil = new ToastUtil();
            }
        }
        return mToastUtil;
    }

    /**
     * 显示Toast，多次调用此函数时，Toast显示的时间不会累计，并且显示内容为最后一次调用时传入的内容
     * 持续时间默认为short
     * @param tips 要显示的内容
     *            {@link Toast#LENGTH_LONG}
     */
    public void showShortT(final String tips){
        showToast(tips, -1, Toast.LENGTH_SHORT);
    }

    public void showShortT(final int tips){
        showToast(null, tips, Toast.LENGTH_SHORT);
    }

    public void showLongT(final String tips){
        showToast(tips, -1, Toast.LENGTH_LONG);
    }

    public void showLongT(final int tips){
        showToast(null, tips, Toast.LENGTH_LONG);
    }

    /**
     * 显示Toast，多次调用此函数时，Toast显示的时间不会累计，并且显示内容为最后一次调用时传入的内容
     *
     * @param tips     要显示的内容
     * @param duration 持续时间，参见{@link Toast#LENGTH_SHORT}和
     *                 {@link Toast#LENGTH_LONG}
     */
    public void showToast(final String tips, final int tipi, final int duration) {
        if (android.text.TextUtils.isEmpty(tips) && tipi <= 0) {
            return;
        }
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (mToast == null) {
                    if (android.text.TextUtils.isEmpty(tips)) {
                        mToast = Toast.makeText(App.getAppContext(), tipi, duration);
                    } else {
                        mToast = Toast.makeText(App.getAppContext(), tips, duration);
                    }
                    mToast.show();
                } else {
                    if (android.text.TextUtils.isEmpty(tips)) {
                        mToast.setText(tipi);
                    } else {
                        mToast.setText(tips);
                    }
                    mToast.setDuration(duration);
                    mToast.show();
                }
            }
        });
    }
}
