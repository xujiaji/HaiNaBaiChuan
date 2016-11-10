package io.xujiaji.hnbc.presenters;

import io.xujiaji.hnbc.contracts.WriteContract;

/**
 * Created by jiana on 16-7-27.
 */
public class WritePresenter extends BasePresenter <WriteContract.View> implements WriteContract.Presenter{

    public WritePresenter(WriteContract.View view) {
        super(view);
    }
}
