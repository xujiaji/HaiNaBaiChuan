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

package io.xujiaji.hnbc.contract;

import io.xujiaji.xmvp.contracts.XContract;

/**
 * Created by jiana on 16-11-20.
 */

public interface SetContract {
    interface Presenter extends XContract.Presenter {
        /**
         * 扫描缓存大小
         */
        void scanCacheSize();

        /**
         * 清理缓存
         */
        void cleanCache();
    }

    interface View extends XContract.View {
        /**
         * 显示缓存大小
         * @param size
         */
        void showCacheSize(String size);

        /**
         * 提示开始清理缓存
         */
        void showStartCleanCache();
        /**
         * 显示清理缓存成功
         */
        void showCleanCacheSuccess();

        void showCleanCacheFail(String err);
    }
}
