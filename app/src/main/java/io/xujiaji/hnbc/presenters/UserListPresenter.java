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

package io.xujiaji.hnbc.presenters;

import java.util.List;

import io.xujiaji.hnbc.R;
import io.xujiaji.hnbc.contracts.UserListContract;
import io.xujiaji.hnbc.fragments.base.BaseMainFragment;
import io.xujiaji.hnbc.model.entity.User;
import io.xujiaji.hnbc.model.net.NetRequest;
import io.xujiaji.xmvp.presenters.XBasePresenter;

/**
 * Created by jiana on 16-11-24.
 * 用户列表
 */

public class UserListPresenter extends XBasePresenter<UserListContract.View> implements UserListContract.Presenter {

    private User nowUser;
    /**
     * 该页面是什么
     */
    private int pageType = 0;

    public UserListPresenter(UserListContract.View view) {
        super(view);
    }

    @Override
    public void start() {
        super.start();
        judgmentPage();
        requestUserList();
    }

    @Override
    public void requestUserList() {
        if (pageType == UserListContract.FANS)
            NetRequest.Instance().requestFans(nowUser, new NetRequest.RequestListener<List<User>>() {
                @Override
                public void success(List<User> users) {
                    view.showList(users);
                }

                @Override
                public void error(String err) {
                    view.requestUserListFail(err);
                }
            });

        if (pageType == UserListContract.FOCUS)
            NetRequest.Instance().requestFocus(nowUser, new NetRequest.RequestListener<List<User>>() {
                @Override
                public void success(List<User> users) {
                    view.showList(users);
                }

                @Override
                public void error(String err) {
                    view.requestUserListFail(err);
                }
            });
    }

    @Override
    public void judgmentPage() {
        Integer value = BaseMainFragment.getData(UserListContract.class.getSimpleName());
        if (value == null) return;
        if (value == UserListContract.FANS) {
            view.showTitle(R.string.fans);
        } else if (value == UserListContract.FOCUS) {
            view.showTitle(R.string.follow);
        }
        pageType = value;
        nowUser = BaseMainFragment.getData(User.class.getSimpleName());
        BaseMainFragment.clearData();
    }

    @Override
    public void end() {
        super.end();
        nowUser = null;
    }
}
