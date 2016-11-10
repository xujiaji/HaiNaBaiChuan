package io.xujiaji.hnbc.presenters;

import io.xujiaji.hnbc.contracts.Contract;

/**
 * Created by jiana on 16-11-4.
 */

public class BasePresenter<T extends Contract.BaseView> {
    protected T view;
    public BasePresenter(T view) {
        this.view = view;
    }

    public void start() {}

    public void end() {
        view = null;
    }
}
