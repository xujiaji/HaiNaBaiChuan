package io.xujiaji.hnbc.presenters;

import android.content.Context;
import android.widget.ImageView;

import io.xujiaji.hnbc.config.C;
import io.xujiaji.hnbc.contracts.UserInfoContract;
import io.xujiaji.hnbc.fragments.BaseMainFragment;
import io.xujiaji.hnbc.fragments.UserInfoFragment;
import io.xujiaji.hnbc.model.data.DataFiller;
import io.xujiaji.hnbc.model.entity.User;
import io.xujiaji.hnbc.model.net.NetRequest;
import io.xujiaji.hnbc.utils.ImgLoadUtil;

/**
 * Created by jiana on 16-11-6.
 *
 */

public class UserInfoPresenter extends BasePresenter <UserInfoContract.View> implements UserInfoContract.Presenter {

    public UserInfoPresenter(UserInfoContract.View view) {
        super(view);
    }

    @Override
    public void requestExitLogin() {
        NetRequest.Instance().exitLogin();
        view.exitLoginSuccess();
    }


    @Override
    public void requestUserInfo() {
        User user = null;
        if (UserInfoFragment.SelfSwitch) {
            user = DataFiller.getLocalUser();
            view.displayUser(user);
        } else {
            user = BaseMainFragment.getData(C.data.KEY_USER);
            if (user != null) {
                if (user.getObjectId().equals(DataFiller.getLocalUser().getObjectId())) {
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

    }
}
