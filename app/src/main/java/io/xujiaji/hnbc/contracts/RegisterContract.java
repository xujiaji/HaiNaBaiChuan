package io.xujiaji.hnbc.contracts;

/**
 * Created by jiana on 16-11-4.
 */

public interface RegisterContract {
    interface Presenter extends Contract.BasePresenter{
        void requestRegister();
        void checkUsername(String username);
        void checkNickname(String nickname);
        void checkPhone(String phone);
        void checkEmail(String email);
        void checkPassword(String password);
        void checkPasswordConfirm(String passwordConfirm);
        void sex(int type);
    }

    interface View extends Contract.BaseView {
        void callRegisterSuccess(String username);
        void callRegisterFail(String err);
        void errUsername(String err);
        void errNickName(String err);
        void errPhone(String err);
        void errEmail(String err);
        void errPassword(String err);
        void errPasswordConfirm(String err);
    }
}
