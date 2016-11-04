package io.xujiaji.hnbc.presenters;

import io.xujiaji.hnbc.contracts.WriteContract;

/**
 * Created by jiana on 16-7-27.
 */
public class WritePresenter extends BasePresenter implements WriteContract.Presenter{
    private WriteContract.View view;

    public WritePresenter(WriteContract.View view) {
        super(view);
        this.view = view;
    }

    @Override
    public void start() {
    }


}
