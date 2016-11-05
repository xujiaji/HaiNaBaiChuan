package io.xujiaji.hnbc.presenters;

import android.content.Context;

import io.xujiaji.hnbc.contracts.LoginContract;
import io.xujiaji.hnbc.model.check.LoginCheck;
import io.xujiaji.hnbc.utils.ToastUtil;

/**
 * Created by jiana on 16-11-4.
 */

public class LoginPresenter extends BasePresenter implements LoginContract.Presenter {
    private LoginContract.View view;
    public LoginPresenter(LoginContract.View view) {
        super(view);
        this.view = view;
    }

    @Override
    public void start() {

    }

    @Override
    public void end() {
        view = null;
    }

    @Override
    public void requestLogin(String name, String password) {
        String nameErr = LoginCheck.checkAccount(name);
        String passwordErr = LoginCheck.checkPassword(password);
        if (nameErr != null) {
            view.nameFormatError(nameErr);
            return;
        }
        if (passwordErr != null) {
            view.passwordFormatError(passwordErr);
            return;
        }
        ToastUtil.getInstance().showShortT("未知区域：登录功能");
        view.callLoginFail("登录失败，未开发区域！");
    }

    @Override
    public void requestSina() {
        ToastUtil.getInstance().showShortT("未知区域：新浪登录");
    }

    @Override
    public void requestQQ() {
        ToastUtil.getInstance().showShortT("未知区域：QQ登录");
    }

    @Override
    public void requestWeiXin() {
        ToastUtil.getInstance().showShortT("未知区域：微信登录");
    }

    @Override
    public void requestRegistered(Context context) {
        ToastUtil.getInstance().showShortT("未知区域：注册功能");
    }
}
