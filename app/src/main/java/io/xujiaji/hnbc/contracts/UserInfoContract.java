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
package io.xujiaji.hnbc.contracts;

import android.content.Context;
import android.widget.ImageView;

import io.xujiaji.hnbc.model.entity.User;
import io.xujiaji.xmvp.contracts.XContract;

/**
 * Created by jiana on 16-11-6.
 */

public interface UserInfoContract {

    interface Presenter extends XContract.Presenter {
        void requestExitLogin();
        void requestUserInfo();
        void requestEdit(Context context);
        void requestDisplayHeadPic(ImageView imgHead, String url);
        void requestDisplayUserInfoBg(ImageView imgUserInfoBg, String url);
        void requestBack();

        void requestFansNum(User user);
        void requestFocusNum(User user);
        void requestCollectNum(User user);
    }

    interface View extends XContract.View {
        void displayUser(User user);
        void exitLoginSuccess();

        /**
         * 显示粉丝数量
         * @param num
         */
        void showFansNum(String num);

        /**
         * 显示关注当前用户的人
         * @param num
         */
        void showFocusNum(String num);

        /**
         * 显示喜欢的文章数
         * @param num
         */
        void showCollectNum(String num);
    }

}
