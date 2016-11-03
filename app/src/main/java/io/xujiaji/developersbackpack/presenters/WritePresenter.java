package io.xujiaji.developersbackpack.presenters;

import io.xujiaji.developersbackpack.contracts.WriteContract;

/**
 * Created by jiana on 16-7-27.
 */
public class WritePresenter implements WriteContract.Presenter{
    private WriteContract.View view;

    public WritePresenter(WriteContract.View view) {
        this.view = view;
    }

    @Override
    public void start() {
    }


}
