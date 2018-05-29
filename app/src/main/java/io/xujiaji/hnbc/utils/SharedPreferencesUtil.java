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

import android.content.Context;
import android.content.SharedPreferences;

import io.xujiaji.hnbc.app.App;
import io.xujiaji.hnbc.config.C;

/**
 * Created by jiana on 16-7-25.
 */
public class SharedPreferencesUtil {
    /**
     * 欢迎页面图片位置
     */
    public static final String WELCOME_PIC = "welcome_pic";
    private SharedPreferencesUtil() {}

    public static void saveWelPicPath(String imgPath) {
        getEdit().putString(WELCOME_PIC, imgPath).commit();
    }

    public static String getWelPicPath() {
        return get().getString(WELCOME_PIC, null);
    }

    public static SharedPreferences get() {
        return App.getAppContext().getSharedPreferences(C.CFile.XML_PREFERENCE_NAME, Context.MODE_PRIVATE);
    }

    public static SharedPreferences.Editor getEdit() {
        return get().edit();
    }
}
