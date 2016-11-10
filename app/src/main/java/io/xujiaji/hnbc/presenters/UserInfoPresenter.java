package io.xujiaji.hnbc.presenters;

import android.content.Context;
import android.widget.ImageView;

import io.xujiaji.hnbc.contracts.UserInfoContract;
import io.xujiaji.hnbc.model.data.DataFiller;
import io.xujiaji.hnbc.model.net.NetRequest;
import io.xujiaji.hnbc.utils.ImgLoadUtil;

/**
 * Created by jiana on 16-11-6.
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
        view.displayUser(DataFiller.getLocalUser());
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
    public void start() {
        super.start();
        requestUserInfo();
    }
}
