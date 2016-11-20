package io.xujiaji.hnbc.presenters;

import java.util.List;

import io.xujiaji.hnbc.contracts.CollectContract;
import io.xujiaji.hnbc.fragments.base.BaseRefreshFragment;
import io.xujiaji.hnbc.model.entity.Post;
import io.xujiaji.hnbc.model.net.NetRequest;

/**
 * Created by jiana on 16-11-20.
 * 收藏
 */

public class CollectPresenter extends BasePresenter<CollectContract.View> implements CollectContract.Presenter{

    public CollectPresenter(CollectContract.View view) {
        super(view);
    }

    @Override
    public void start() {
        super.start();
        requestUpdateListData();
    }

    @Override
    public void requestLoadListData(int nowSize) {
        NetRequest.Instance().pullCollectPost(nowSize, BaseRefreshFragment.PAGE_SIZE, new NetRequest.RequestListener<List<Post>>() {
            @Override
            public void success(List<Post> posts) {
                view.loadListDataSuccess(posts);
            }

            @Override
            public void error(String err) {
                view.loadListDataFail(err);
            }
        });
    }

    @Override
    public void requestUpdateListData() {
        NetRequest.Instance().pullCollectPost(0, BaseRefreshFragment.PAGE_SIZE, new NetRequest.RequestListener<List<Post>>() {
            @Override
            public void success(List<Post> posts) {
                view.updateListSuccess(posts);
            }

            @Override
            public void error(String err) {
                view.loadListDataFail(err);
            }
        });

    }
}
