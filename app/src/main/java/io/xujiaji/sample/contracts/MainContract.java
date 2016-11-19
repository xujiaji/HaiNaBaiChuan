package io.xujiaji.sample.contracts;

import io.xujiaji.xmvp.contracts.Contract;

/**
 * Created by jiana on 16-11-19.
 */

public interface MainContract {
    interface Presenter extends Contract.BasePresenter{
        void requestTextData();
    }

    interface View extends Contract.BaseView{
        void showText(String text);
    }
}
