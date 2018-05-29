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

import io.xujiaji.hnbc.R;
import io.xujiaji.hnbc.app.App;
import io.xujiaji.hnbc.config.C;
import io.xujiaji.hnbc.contract.ReadActicleContract;
import io.xujiaji.hnbc.fragment.base.BaseMainFragment;
import io.xujiaji.hnbc.model.entity.Comment;
import io.xujiaji.hnbc.model.entity.Post;
import io.xujiaji.hnbc.model.entity.Reply;
import io.xujiaji.hnbc.model.entity.User;
import io.xujiaji.hnbc.model.net.NetRequest;
import io.xujiaji.hnbc.utils.LogUtil;
import io.xujiaji.xmvp.presenters.XBasePresenter;

/**
 * Created by jiana on 16-11-14.
 */

public class ReadArticlePresenter extends XBasePresenter<ReadActicleContract.View> implements ReadActicleContract.Presenter {
    private Post post;

    public ReadArticlePresenter(ReadActicleContract.View view) {
        super(view);
    }

    @Override
    public void end() {
        super.end();
        post = null;
    }

    @Override
    public void requestCommentsData(String postId) {
        NetRequest.Instance().getPostComment(postId, new NetRequest.RequestListener<List<Comment>>() {

            @Override
            public void success(List<Comment> comments) {
                requestCommentsReply(comments);
            }

            @Override
            public void error(String err) {
                LogUtil.e3(err);
            }
        });
    }

    @Override
    public void requestCommentsReply(final List<Comment> comments) {
        NetRequest.Instance().getCommentReply(comments, new NetRequest.RequestListener<List<Reply>>() {

            @Override
            public void success(List<Reply> lists) {
                view.showComment(comments, lists);
            }

            @Override
            public void error(String err) {

            }
        });
    }

    @Override
    public void requestPostData() {
        post = BaseMainFragment.getData(C.data.KEY_POST);
        if (post != null) {
            view.showArticle(post);
            requestCommentsData(post.getObjectId());
            BaseMainFragment.clearData();
        }
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

    @Override
    public void addComment(String comment) {
        if (post == null) {
            view.addCommentFail(App.getAppContext().getString(R.string.current_not_article));
        }
        NetRequest.Instance().addComment(post, comment, new NetRequest.RequestListener<String>() {
            @Override
            public void success(String s) {
                view.addCommentSuccess();
                requestCommentsData(post.getObjectId());
            }

            @Override
            public void error(String err) {
                view.addCommentFail(err);
            }
        });
    }

    @Override
    public void addReply(User replyUser, Comment comment, String content) {
        NetRequest.Instance().replyComment(replyUser, comment, content, new NetRequest.RequestListener() {
            @Override
            public void success(Object o) {
                requestCommentsData(post.getObjectId());
                view.replyCommentSuccess();
            }

            @Override
            public void error(String err) {
                view.replyCommentFail(err);
            }
        });
    }
}
