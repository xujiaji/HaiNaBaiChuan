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
    }

    interface View extends Contract.BaseView {
        void displayUser(User user);
        void exitLoginSuccess();
    }

}
