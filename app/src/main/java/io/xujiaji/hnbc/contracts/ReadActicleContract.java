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

import java.util.List;

import io.xujiaji.hnbc.model.entity.Comment;
import io.xujiaji.hnbc.model.entity.Post;
import io.xujiaji.hnbc.model.entity.Reply;
import io.xujiaji.hnbc.model.entity.User;
import io.xujiaji.xmvp.contracts.XContract;

/**
 * Created by jiana on 16-11-14.
 * 阅读文章协约
 */

public interface ReadActicleContract {
    interface Presenter extends XContract.Presenter {

        /**
         * 通过文章的id查询所有评论
         * @param postId
         */
        void requestCommentsData(String postId);

        /**
         * 获取所有评论的回复
         * @param comments
         */
        void requestCommentsReply(List<Comment> comments);

        /**
         * 请求文章数据
         * @param
         */
        void requestPostData();

        /**
         * 请求喜欢文章
         */
        void requestLike(Post post);
        /**
         * 请求跟随
         */
        void requestFollow(User user);

        /**
         * 添加评论
         * @param comment
         */
        void addComment(String comment);

        void addReply(User replyUser, Comment comment, String content);
    }

    interface View extends XContract.View {
        /**
         * 显示文章
         * @param post
         */
        void showArticle(Post post);

        /**
         * 显示评论
         */
        void showComment(List<Comment> comments, List<Reply> replyList);


        /**
         * 添加评论成功
         */
        void addCommentSuccess();

        /**
         * 添加评论失败
         * @param err
         */
        void addCommentFail(String err);

        /**
         * 回复评论成功
         */
        void replyCommentSuccess();

        /**
         * 回复评论失败
         * @param err
         */
        void replyCommentFail(String err);

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
