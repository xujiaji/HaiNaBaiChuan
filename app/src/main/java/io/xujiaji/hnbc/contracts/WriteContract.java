package io.xujiaji.hnbc.contracts;

/**
 * Created by jiana on 16-7-27.
 */
public interface WriteContract {
    interface Presenter extends Contract.BasePresenter {

    }

    interface View extends Contract.BaseView {
        void openImgSelect();
    }
}
