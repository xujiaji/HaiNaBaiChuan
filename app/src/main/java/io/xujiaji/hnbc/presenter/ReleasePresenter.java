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

package io.xujiaji.hnbc.presenter;

import java.util.List;

import io.xujiaji.hnbc.contract.ReleaseContract;
import io.xujiaji.hnbc.fragment.base.BaseRefreshFragment;
import io.xujiaji.hnbc.model.entity.Post;
import io.xujiaji.hnbc.model.net.NetRequest;
import io.xujiaji.xmvp.presenters.XBasePresenter;

/**
 * Created by jiana on 16-11-20.
 * 收藏
 */

public class ReleasePresenter extends XBasePresenter<ReleaseContract.View> implements ReleaseContract.Presenter{

    public ReleasePresenter(ReleaseContract.View view) {
        super(view);
    }

    @Override
    public void start() {
        super.start();
        requestUpdateListData();
    }

    @Override
    public void requestLoadListData(int nowSize) {
        NetRequest.Instance().pullReleasePost(nowSize, BaseRefreshFragment.PAGE_SIZE, new NetRequest.RequestListener<List<Post>>() {
            @Override
            public void success(List<Post> posts) {
                if (posts == null || posts.size() == 0) {
                    view.loadListDateOver();
                } else {
                    view.loadListDataSuccess(posts);
                }
            }

            @Override
            public void error(String err) {
                view.loadListDataFail(err);
            }
        });
    }

    @Override
    public void requestUpdateListData() {
        NetRequest.Instance().pullReleasePost(0, BaseRefreshFragment.PAGE_SIZE, new NetRequest.RequestListener<List<Post>>() {
            @Override
            public void success(List<Post> posts) {
                view.updateListSuccess(posts);
            }

            @Override
            public void error(String err) {
                view.loadListDataFail(err);
            }
        });

    }
}
