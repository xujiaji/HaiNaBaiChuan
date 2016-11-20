package io.xujiaji.hnbc.presenters;

import io.xujiaji.hnbc.contracts.CollectContract;

/**
 * Created by jiana on 16-11-20.
 * 收藏
 */

public class CollectPresenter extends BasePresenter<CollectContract.View> implements CollectContract.Presenter{

    public CollectPresenter(CollectContract.View view) {
        super(view);
    }

    @Override
    public void requestLoadListData(int nowSize) {

    }

    @Override
    public void requestUpdateListData() {

    }
}
