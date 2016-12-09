package io.xujiaji.hnbc.presenters;

import java.util.List;

import io.xujiaji.hnbc.contracts.ReleaseContract;
import io.xujiaji.hnbc.fragments.base.BaseRefreshFragment;
import io.xujiaji.hnbc.model.entity.Post;
import io.xujiaji.hnbc.model.net.NetRequest;
import io.xujiaji.xmvp.presenters.XBasePresenter;

/**
 * Created by jiana on 16-11-20.
 * 收藏
 */

public class ReleasePresenter extends XBasePresenter<ReleaseContract.View> implements ReleaseContract.Presenter{

    public ReleasePresenter(ReleaseContract.View view) {
        super(view);
    }

    @Override
    public void start() {
        super.start();
        requestUpdateListData();
    }

    @Override
    public void requestLoadListData(int nowSize) {
        NetRequest.Instance().pullReleasePost(nowSize, BaseRefreshFragment.PAGE_SIZE, new NetRequest.RequestListener<List<Post>>() {
            @Override
            public void success(List<Post> posts) {
                if (posts == null || posts.size() == 0) {
                    view.loadListDateOver();
                } else {
                    view.loadListDataSuccess(posts);
                }
            }

            @Override
            public void error(String err) {
                view.loadListDataFail(err);
            }
        });
    }

    @Override
    public void requestUpdateListData() {
        NetRequest.Instance().pullReleasePost(0, BaseRefreshFragment.PAGE_SIZE, new NetRequest.RequestListener<List<Post>>() {
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
