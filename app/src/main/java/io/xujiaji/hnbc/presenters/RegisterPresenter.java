package io.xujiaji.hnbc.presenters;

import io.xujiaji.hnbc.R;
import io.xujiaji.hnbc.app.App;
import io.xujiaji.hnbc.config.C;
import io.xujiaji.hnbc.contracts.RegisterContract;
import io.xujiaji.hnbc.utils.check.LoginCheck;
import io.xujiaji.hnbc.model.entity.User;
import io.xujiaji.hnbc.model.net.NetRequest;
import io.xujiaji.hnbc.utils.LogUtil;
import io.xujiaji.hnbc.utils.MD5Util;

/**
 * Created by jiana on 16-11-5.
 */

public class RegisterPresenter extends BasePresenter <RegisterContract.View> implements RegisterContract.Presenter{
    private String passwordSave;
    private User user = null;
    //密码确认
    private boolean pwdConfirm = false;
    public RegisterPresenter(RegisterContract.View view) {
        super(view);
    }


    @Override
    public void start() {
        super.start();
        user = new User();
        user.setSex(C.SEX_SECRET);
    }

    @Override
    public void end() {
        super.end();
        passwordSave = null;
        user = null;
    }

    @Override
    public void requestRegister() {
        if (
//                user.getEmail() != null &&
//                user.getNickname() != null &&
                user.getUsername() != null &&
//                user.getMobilePhoneNumber() != null &&
//                user.getEmail() != null &&
                passwordSave != null &&
                pwdConfirm) {
            user.setNickname(App.getAppContext().getString(R.string.secret_man));
            NetRequest.Instance().userRegister(user, new NetRequest.RequestListener<User>() {
                @Override
                public void success(User userGet) {
                    view.callRegisterSuccess(user.getUsername());
                    LogUtil.e3("user = " + userGet.toString());
                }

                @Override
                public void error(String err) {
                    LogUtil.e3("err = " + err);
                    view.callRegisterFail(err);
                }
            });
        } else {
            view.callRegisterFail("请检查您的输入信息！");
        }
    }

    @Override
    public void checkUsername(String username) {
        String value = LoginCheck.checkAccount(username);
        if (value == null) {
            user.setUsername(username);
            return;
        }
        view.errUsername(value);
    }

    @Override
    public void checkNickname(String nickname) {
        String value = LoginCheck.checkNickname(nickname);
        if (value == null) {
            user.setNickname(nickname);
            return;
        }
        view.errNickName(value);
    }

    @Override
    public void checkPhone(String phone) {
        String value = LoginCheck.checkPhone(phone);
        if (value == null) {
            user.setMobilePhoneNumber(phone);
            return;
        }
        view.errPhone(value);
    }

    @Override
    public void checkEmail(String email) {
        String value = LoginCheck.checkEmail(email);
        if (value == null) {
            user.setEmail(email);
            return;
        }
        view.errEmail(value);
    }

    @Override
    public void checkPassword(String password) {
        String value = LoginCheck.checkPassword(password);
        if (value == null) {
            passwordSave = password;
            user.setPassword(MD5Util.getMD5(password));
            return;
        }
        view.errPassword(value);
    }

    @Override
    public void checkPasswordConfirm(String passwordConfirm) {
        String value = LoginCheck.checkConfirmPassword(passwordSave, passwordConfirm);
        if (value == null) {
            pwdConfirm = true;
            return;
        }
        pwdConfirm = false;
        view.errPasswordConfirm(value);
    }

    @Override
    public void sex(int type) {
        user.setSex(type);
    }

}
