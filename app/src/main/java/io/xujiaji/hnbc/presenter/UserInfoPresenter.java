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


package io.xujiaji.hnbc.presenter;

import android.content.Context;
import android.widget.ImageView;

import io.xujiaji.hnbc.config.C;
import io.xujiaji.hnbc.contract.UserInfoContract;
import io.xujiaji.hnbc.fragment.UserInfoFragment;
import io.xujiaji.hnbc.fragment.base.BaseMainFragment;
import io.xujiaji.hnbc.model.data.DataFiller;
import io.xujiaji.hnbc.model.entity.User;
import io.xujiaji.hnbc.model.net.NetRequest;
import io.xujiaji.hnbc.utils.ImgLoadUtil;
import io.xujiaji.xmvp.presenters.XBasePresenter;

/**
 * Created by jiana on 16-11-6.
 *
 */

public class UserInfoPresenter extends XBasePresenter<UserInfoContract.View> implements UserInfoContract.Presenter {

    public UserInfoPresenter(UserInfoContract.View view) {
        super(view);
    }
    private User user;

    @Override
    public void requestExitLogin() {
        NetRequest.Instance().exitLogin();
        view.exitLoginSuccess();
    }


    @Override
    public void requestUserInfo() {
        if (UserInfoFragment.SelfSwitch) {
            user = DataFiller.getLocalUser();
            view.displayUser(user);
        } else {
            user = BaseMainFragment.getData(C.data.KEY_USER);
            if (user != null) {
                if (DataFiller.getLocalUser() != null && user.getObjectId().equals(DataFiller.getLocalUser().getObjectId())) {
                    UserInfoFragment.SelfSwitch = true;
                }
                view.displayUser(user);
                BaseMainFragment.clearData();
            }
        }

        requestFansNum(user);
        requestFocusNum(user);
        requestCollectNum(user);
    }

    @Override
    public void requestEdit(Context context) {

    }

    @Override
    public void requestDisplayHeadPic(ImageView imgHead, String url) {
        ImgLoadUtil.loadHead(imgHead.getContext(), imgHead, url);
    }

    @Override
    public void requestDisplayUserInfoBg(ImageView imgUserInfoBg, String url) {
        ImgLoadUtil.loadBitmap(imgUserInfoBg.getContext(), imgUserInfoBg, url);
    }

    @Override
    public void requestBack() {

    }

    @Override
    public void requestFansNum(User user) {
        NetRequest.Instance().requestFansNum(user, new NetRequest.RequestListener<String>() {
            @Override
            public void success(String s) {
                view.showFansNum(s);
            }

            @Override
            public void error(String err) {

            }
        });
    }

    @Override
    public void requestFocusNum(User user) {
        NetRequest.Instance().requestFocusNum(user, new NetRequest.RequestListener<String>() {
            @Override
            public void success(String s) {
                view.showFocusNum(s);
            }

            @Override
            public void error(String err) {

            }
        });
    }

    @Override
    public void requestCollectNum(User user) {
        NetRequest.Instance().requestCollectNum(user, new NetRequest.RequestListener<String>() {
            @Override
            public void success(String s) {
                view.showCollectNum(s);
            }

            @Override
            public void error(String err) {

            }
        });
    }

    public User getUser() {
        return user;
    }

    @Override
    public void end() {
        super.end();
        user = null;
    }
}
