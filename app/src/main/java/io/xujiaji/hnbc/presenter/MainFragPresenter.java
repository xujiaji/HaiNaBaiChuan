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
import android.content.Intent;
import android.net.Uri;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobUser;
import io.xujiaji.hnbc.R;
import io.xujiaji.hnbc.contract.MainContract;
import io.xujiaji.hnbc.fragment.MainFragment;
import io.xujiaji.hnbc.model.data.DataFiller;
import io.xujiaji.hnbc.model.entity.BannerData;
import io.xujiaji.hnbc.model.entity.MainTag;
import io.xujiaji.hnbc.model.entity.Post;
import io.xujiaji.hnbc.model.entity.User;
import io.xujiaji.hnbc.model.net.NetRequest;
import io.xujiaji.hnbc.utils.ImgLoadUtil;
import io.xujiaji.xmvp.presenters.XBasePresenter;

/**
 * Created by jiana on 16-7-22.
 */
public class MainFragPresenter extends XBasePresenter<MainContract.MainFragView> implements MainContract.MainFragPersenter {
    private List<BannerData> bannerDataList = null;

    public MainFragPresenter(MainContract.MainFragView view) {
        super(view);
    }

    @Override
    public void start() {
        super.start();
        requestUpdateListData();
        requestBannerData();
    }

    @Override
    public void end() {
        super.end();
        bannerDataList = null;
    }

    @Override
    public List<MainTag> getTags() {
        return DataFiller.getTagsData();
    }

    @Override
    public void requestBannerData() {
        NetRequest.Instance().pullBannerData(new NetRequest.RequestListener<List<BannerData>>() {
            @Override
            public void success(List<BannerData> bannerDatas) {
                bannerDataList = bannerDatas;
                List<String> titles = new ArrayList<String>();
                List<String> images = new ArrayList<String>();
                for (BannerData bd : bannerDatas) {
                    titles.add(bd.getTitle());
                    images.add(bd.getPicUrl());
                }
                view.pullBannerDataSuccess(titles, images);
            }

            @Override
            public void error(String err) {
                view.pullBannerDataFail(err);
            }
        });
    }

    @Override
    public void requestOpenBannerLink(Context context, int position) {
        Uri uri = Uri.parse(bannerDataList.get(position).getLinkTo());
        Intent it = new Intent(Intent.ACTION_VIEW, uri);
        context.startActivity(it);
    }

    @Override
    public void requestLoadHead(final ImageView head) {
        User user = BmobUser.getCurrentUser(User.class);
        if (user == null) {
            head.setImageResource(R.drawable.head);
            return;
        }
        ImgLoadUtil.loadHead(head.getContext(), head, user.getHeadPic());
    }

    @Override
    public void requestUpdateListData() {
        NetRequest.Instance().pullPostList(0, MainFragment.PAGE_SIZE, new NetRequest.RequestListener<List<Post>>() {
            @Override
            public void success(List<Post> posts) {
                view.updateListSuccess(posts);
            }

            @Override
            public void error(String err) {
                view.updateListFail(err);
            }
        });
    }

    @Override
    public void requestLoadListData(int currentNum) {
        NetRequest.Instance().pullPostList(currentNum, MainFragment.PAGE_SIZE, new NetRequest.RequestListener<List<Post>>() {
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
    public void requestLike(Post post) {
        NetRequest.Instance().likeComment(post, new NetRequest.RequestListener<String>() {
            @Override
            public void success(String s) {
                view.likePostSuccess();
            }

            @Override
            public void error(String err) {
                view.likePostFail(err);
            }
        });
    }

    @Override
    public void requestFollow(User user) {
        NetRequest.Instance().followUser(user, new NetRequest.RequestListener<String>() {
            @Override
            public void success(String s) {
                view.followUserSuccess();
            }

            @Override
            public void error(String err) {
                view.followUserFail(err);
            }
        });
    }


}
