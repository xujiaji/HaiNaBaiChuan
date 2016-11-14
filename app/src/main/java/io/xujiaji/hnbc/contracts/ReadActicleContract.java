package io.xujiaji.hnbc.contracts;

import java.util.List;

import io.xujiaji.hnbc.model.entity.Comment;
import io.xujiaji.hnbc.model.entity.Post;

/**
 * Created by jiana on 16-11-14.
 * 阅读文章协约
 */

public interface ReadActicleContract {
    interface Presenter extends Contract.BasePresenter {

        /**
         * 通过文章的id查询所有评论
         * @param postId
         */
        void requestCommentsData(String postId);

        /**
         * 请求文章数据
         * @param
         */
        void requestPostData();
    }

    interface View extends Contract.BaseView {
        /**
         * 显示文章
         * @param post
         */
        void showArticle(Post post);

        /**
         * 显示评论
         * @param comments
         */
        void showComment(List<Comment> comments);

    }
}
