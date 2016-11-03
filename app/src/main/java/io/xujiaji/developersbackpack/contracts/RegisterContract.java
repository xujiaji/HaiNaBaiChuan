package io.xujiaji.developersbackpack.contracts;

/**
 * Created by jiana on 16-8-2.
 */
public interface RegisterContract {

    interface Presenter extends Contract.BasePresenter {
        void requestEmail();
        void requestPhone();
        void requestSina();
        void requestQQ();
        void requestWeiXin();

    }

    interface View extends Contract.BaseView {
        void showSendEmailSuccess();
        void showSendSMSScuccess();
        void showWait();
        void registerSuccess();

    }
}
