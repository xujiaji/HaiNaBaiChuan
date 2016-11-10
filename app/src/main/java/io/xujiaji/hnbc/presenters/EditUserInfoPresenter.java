package io.xujiaji.hnbc.presenters;

import java.io.File;
import java.util.Date;

import cn.bmob.v3.exception.BmobException;
import io.xujiaji.hnbc.contracts.EditUserInfoContract;
import io.xujiaji.hnbc.factory.ErrMsgFactory;
import io.xujiaji.hnbc.model.check.LoginCheck;
import io.xujiaji.hnbc.model.entity.User;
import io.xujiaji.hnbc.model.net.NetRequest;

/**
 * Created by jiana on 16-11-7.
 */

public class EditUserInfoPresenter extends BasePresenter <EditUserInfoContract.View> implements EditUserInfoContract.Presenter {

    public EditUserInfoPresenter(EditUserInfoContract.View view) {
        super(view);
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
                view.changeHeadPicFail(ErrMsgFactory.errMSG(err.getErrorCode()));
            }
        });
    }

    @Override
    public void requestChangeNickname(String nickname) {
        String err = LoginCheck.checkNickname(nickname);
        if (err != null) {
            view.changeNicknameFail(err);
            return;
        }
        NetRequest.Instance().updateInfo(NetRequest.UpdateType.NICKNAME,
                nickname,
                new NetRequest.RequestListener<User>() {
                    @Override
                    public void success(User user) {
                        view.changeNicknameSuccess();
                    }

                    @Override
                    public void error(BmobException err) {
                        view.changeNicknameFail(ErrMsgFactory.errMSG(err.getErrorCode()));
                    }
                });
    }

    @Override
    public void requestChangePhone(String phoneNumber) {
        String err = LoginCheck.checkPhone(phoneNumber);
        if (err != null) {
            view.changePhoneFail(err);
            return;
        }
        NetRequest.Instance().updateInfo(NetRequest.UpdateType.PHONE,
                phoneNumber,
                new NetRequest.RequestListener<User>() {
                    @Override
                    public void success(User user) {
                        view.changePhoneSuccess();
                    }

                    @Override
                    public void error(BmobException err) {
                        view.changePhoneFail(ErrMsgFactory.errMSG(err.getErrorCode()));
                    }
                });
    }

    @Override
    public void requestChangeSign(String sign) {
        String err = LoginCheck.checkSign(sign);
        if (err != null) {
            view.changeSignFail(err);
            return;
        }
        NetRequest.Instance().updateInfo(NetRequest.UpdateType.SIGN,
                sign,
                new NetRequest.RequestListener<User>() {
                    @Override
                    public void success(User user) {
                        view.changeSignSuccess();
                    }

                    @Override
                    public void error(BmobException err) {
                        view.changeSignFail(ErrMsgFactory.errMSG(err.getErrorCode()));
                    }
                });
    }

    @Override
    public void requestChangeCity(String city) {
        NetRequest.Instance().updateInfo(NetRequest.UpdateType.CITY,
                city,
                new NetRequest.RequestListener<User>() {
                    @Override
                    public void success(User user) {
                        view.changeCitySuccess();
                    }

                    @Override
                    public void error(BmobException err) {
                        view.changeCityFail(ErrMsgFactory.errMSG(err.getErrorCode()));
                    }
                });
    }

    @Override
    public void requestChangeSex(int sex) {
        NetRequest.Instance().updateInfo(NetRequest.UpdateType.SEX,
                sex,
                new NetRequest.RequestListener<User>() {
                    @Override
                    public void success(User user) {
                        view.changeSexSuccess();
                    }

                    @Override
                    public void error(BmobException err) {
                        view.changeSexFail(ErrMsgFactory.errMSG(err.getErrorCode()));
                    }
                });
    }

    @Override
    public void requestBirthday(Date birthday) {
        NetRequest.Instance().updateInfo(NetRequest.UpdateType.BIRTHDAY,
                birthday,
                new NetRequest.RequestListener<User>() {
                    @Override
                    public void success(User user) {
                        view.changeBirthdaySuccess();
                    }

                    @Override
                    public void error(BmobException err) {
                        view.changeBirthdayFail(ErrMsgFactory.errMSG(err.getErrorCode()));
                    }
                });
    }

    @Override
    public void requestChangeEmail(String email) {
        String err = LoginCheck.checkEmail(email);
        if (err != null) {
            view.changeEmailFail(err);
            return;
        }
        NetRequest.Instance().updateInfo(NetRequest.UpdateType.EMAIL,
                email,
                new NetRequest.RequestListener<User>() {
                    @Override
                    public void success(User user) {
                        view.changeEmailSuccess();
                    }

                    @Override
                    public void error(BmobException err) {
                        view.changeEmailFail(ErrMsgFactory.errMSG(err.getErrorCode()));
                    }
                });
    }

    @Override
    public void requestChangePassword(String oldPwd, String newPwd) {
        String err = LoginCheck.checkChangePassword(oldPwd, newPwd);
        if (err != null) {
            view.changePasswordFail(err);
            return;
        }
        NetRequest.Instance().updatePassword(oldPwd, newPwd, new NetRequest.RequestListener<User>() {
            @Override
            public void success(User user) {
                view.changePasswordSuccess();
            }

            @Override
            public void error(BmobException err) {
                view.changePasswordFail(ErrMsgFactory.errMSG(err.getErrorCode()));
            }
        });
    }
}
