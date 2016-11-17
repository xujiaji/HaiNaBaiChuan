package io.xujiaji.hnbc.contracts;

import android.content.Context;
import android.widget.ImageView;

import io.xujiaji.hnbc.model.entity.User;

/**
 * Created by jiana on 16-11-6.
 */

public interface UserInfoContract {

    interface Presenter extends Contract.BasePresenter {
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

    interface View extends Contract.BaseView {
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
