package io.xujiaji.hnbc.presenters;

import java.io.File;
import java.util.Date;

import cn.bmob.v3.exception.BmobException;
import io.xujiaji.hnbc.contracts.EditUserInfoContract;
import io.xujiaji.hnbc.model.net.NetRequest;

/**
 * Created by jiana on 16-11-7.
 */

public class EditUserInfoPresenter extends BasePresenter implements EditUserInfoContract.Presenter {
    private EditUserInfoContract.View view;

    public EditUserInfoPresenter(EditUserInfoContract.View view) {
        super(view);
        this.view = view;
    }

    @Override
    public void requestChangeHeadPic(String picPath) {
        NetRequest.Instance().uploadHeadPic(new File(picPath), new NetRequest.RequestListener<String>() {
            @Override
            public void success(String s) {
                view.changeHeadPicSuccess();
            }

            @Override
            public void error(BmobException err) {

            }
        });
    }

    @Override
    public void requestChangeNickname(String nickname) {

    }

    @Override
    public void requestChangePhone(String phoneNumber) {

    }

    @Override
    public void requestChangeSign(String sign) {

    }

    @Override
    public void requestChangeCity(String city) {

    }

    @Override
    public void requestChangeSex(int sex) {

    }

    @Override
    public void requestBirthday(Date birthday) {

    }

    @Override
    public void requestChangeEmail(String email) {

    }

    @Override
    public void requestChangePassword(String password) {

    }

    @Override
    public void start() {

    }

    @Override
    public void end() {
        view = null;
    }
}
