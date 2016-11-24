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

import java.util.List;

import io.xujiaji.hnbc.contracts.base.Contract;
import io.xujiaji.hnbc.contracts.base.RefreshContract;
import io.xujiaji.hnbc.model.entity.MainTag;
import io.xujiaji.hnbc.model.entity.Post;
import io.xujiaji.hnbc.model.entity.User;

/**
 * Created by jiana on 16-7-22.
 */
public interface MainContract {
    interface Presenter extends Contract.BasePresenter {
        List<String> getMenuData();
        boolean checkLocalUser();
        User getUser();
    }

    interface View extends Contract.BaseView {
        void menuOpen();
        void menuClose();
        void menuToggle();
        void clickHome();
        void clickUserInfo();
        void clickCollect();
        void clickRelease();
        void clickSet();
    }



    interface MainFragPersenter extends RefreshContract.Presenter {
        List<MainTag> getTags();

        /**
         * 请求加载广告数据
         */
        void requestBannerData();

        /**
         * 请求打开链接
         */
        void requestOpenBannerLink(Context context, int position);

        void requestLoadHead(ImageView head);

        /**
         * 请求喜欢文章
         */
        void requestLike(Post post);
        /**
         * 请求跟随
         */
        void requestFollow(User user);
    }

    interface MainFragView extends RefreshContract.View<Post> {
        void contentLayoutToTop();
        void contentLayoutToTopListener(boolean start);
        void contentLayoutToDown();
        void contentLayoutToDownListener(boolean start);

        void pullBannerDataSuccess(List<String> titles, List<String> images);

        void pullBannerDataFail(String err);

        /**
         * 喜欢文章成功
         */
        void likePostSuccess();
        void likePostFail(String err);

        /**
         * 跟随某人成功
         */
        void followUserSuccess();

        /**
         * 跟随某人失败
         * @param err
         */
        void followUserFail(String err);

    }
}
