package io.xujiaji.hnbc.contracts;

/**
 * Created by jiana on 16-11-4.
 */

public interface LoginContract {
    interface Presenter extends Contract.BasePresenter{
        void requestLogin(String name, String password);
        void requestSina();
        void requestQQ();
        void requestWeiXin();
    }

    interface View extends MainContract.MainBaseFragView {
        void nameFormatError(String err);
        void passwordFormatError(String err);
        void callLoginSuccess();
        void callLoginFail(String failMassage);
    }
}
