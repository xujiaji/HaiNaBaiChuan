package io.xujiaji.hnbc.contracts;

import java.util.Date;

/**
 * Created by jiana on 16-11-7.
 */

public interface EditUserInfoContract {
    interface Presenter extends Contract.BasePresenter {
        void requestChangeHeadPic(String picPath);
        void requestChangeNickname(String nickname);
        void requestChangePhone(String phoneNumber);
        void requestChangeSign(String sign);
        void requestChangeCity(String city);
        void requestChangeSex(int sex);
        void requestBirthday(Date birthday);
        void requestChangeEmail(String email);
        void requestChangePassword(String password);
    }

    interface View extends Contract.BaseView {
        void changeHeadPicSuccess();
        void changeHeadPicFail(String err);
        void changeNicknameSuccess();
        void changeNicknameFail(String err);
        void changePhoneSuccess();
        void changePhoneFail(String err);
        void changeSignSuccess();
        void changeSignFail(String err);
        void changeCitySuccess();
        void changeCityFail(String err);
        void changeSexSuccess();
        void changeSexFail(String err);
        void changeBirthdaySuccess();
        void changeBirthdayFail(String err);
        void changeEmailSuccess();
        void changeEmailFail(String err);
        void changePasswordSuccess();
        void changePasswordFail(String err);
    }
}
