package io.xujiaji.hnbc.contracts;

import io.xujiaji.hnbc.contracts.base.Contract;

/**
 * Created by jiana on 01/12/16.
 */

public interface AboutContract {
    interface View extends Contract.BaseView {
        void showNowVersion(String version);
    }

    interface Presenter extends Contract.BasePresenter {
        void getNowVersion();
    }
}
