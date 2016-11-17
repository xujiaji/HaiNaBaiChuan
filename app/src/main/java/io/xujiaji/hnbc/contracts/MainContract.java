package io.xujiaji.hnbc.contracts;

import android.content.Context;
import android.widget.ImageView;

import java.util.List;

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

    interface MainBaseFragView extends Contract.BaseView {

    }



    interface MainFragPersenter extends Contract.BasePresenter {
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
         * 请求加载数据
         */
        void requestUpdateListData();

        /**
         * 请求加载更多数据
         */
        void requestLoadListData(int current);

        /**
         * 请求喜欢文章
         */
        void requestLike(Post post);
        /**
         * 请求跟随
         */
        void requestFollow(User user);
    }

    interface MainFragView extends MainBaseFragView {
        void contentLayoutToTop();
        void contentLayoutToTopListener(boolean start);
        void contentLayoutToDown();
        void contentLayoutToDownListener(boolean start);

        void pullBannerDataSuccess(List<String> titles, List<String> images);

        void pullBannerDataFail(String err);
        /**
         * 刷新列表数据成功
         */
        void updateListDateSuccess(List<Post> posts);

        /**
         * 刷新列表数据失败
         * @param err
         */
        void updateListDateFail(String err);

        /**
         * 加载列表数据成功
         * @param posts
         */
        void loadListDateSuccess(List<Post> posts);

        /**
         * 已经把数据加载完了
         */
        void loadListDateOver();

        /**
         * 加载数据失败了
         * @param err
         */
        void loadListFail(String err);


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
