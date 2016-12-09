package io.xujiaji.hnbc.contracts;


import io.xujiaji.xmvp.contracts.XContract;

/**
 * Created by jiana on 01/12/16.
 */

public interface AboutContract {
    interface View extends XContract.View {
        void showNowVersion(String version);
    }

    interface Presenter extends XContract.Presenter {
        void getNowVersion();
    }
}
