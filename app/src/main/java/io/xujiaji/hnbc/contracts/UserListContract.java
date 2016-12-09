/*
 * Copyright 2016 XuJiaji
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.xujiaji.hnbc.contracts;

import android.support.annotation.StringRes;

import java.util.List;

import io.xujiaji.hnbc.model.entity.User;
import io.xujiaji.xmvp.contracts.XContract;

/**
 * Created by jiana on 16-11-24.
 *
 * 用户列表
 */

public interface UserListContract {

    /**
     * 粉丝列表
     */
    int FANS = 124;

    /**
     * 关注列表
     */
    int FOCUS = 125;


    interface Presenter extends XContract.Presenter {
        /**
         * 请求用户列表
         */
        void requestUserList();

        void judgmentPage();
    }

    interface View extends XContract.View {
        /**
         * 显示标题
         * @param title
         */
        void showTitle(@StringRes int title);

        /**
         * 显示错误信息
         * @param err
         */
        void requestUserListFail(String err);

        /**
         * 显示用户列表
         * @param users
         */
        void showList(List<User> users);
    }
}
