package io.xujiaji.hnbc.contracts;

/**
 * Created by jiana on 16-8-2.
 */
public interface RegisterContract {

    interface Presenter extends Contract.BasePresenter {
        void requestRegister();
    }

    interface View extends Contract.BaseView {
        void showSendEmailSuccess();
        void showSendSMSScuccess();
        void showWait();
        void registerSuccess();

    }
}
