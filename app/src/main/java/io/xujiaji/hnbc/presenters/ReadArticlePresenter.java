package io.xujiaji.hnbc.presenters;

import io.xujiaji.hnbc.config.C;
import io.xujiaji.hnbc.contracts.ReadActicleContract;
import io.xujiaji.hnbc.fragments.BaseMainFragment;
import io.xujiaji.hnbc.model.entity.Post;

/**
 * Created by jiana on 16-11-14.
 */

public class ReadArticlePresenter extends BasePresenter<ReadActicleContract.View> implements ReadActicleContract.Presenter {

    public ReadArticlePresenter(ReadActicleContract.View view) {
        super(view);
    }

    @Override
    public void requestCommentsData(String postId) {

    }

    @Override
    public void requestPostData() {
        Post post = BaseMainFragment.getData(C.data.KEY_POST);
        if (post != null) {
            view.showArticle(post);
            BaseMainFragment.clearData();
        }
    }
}
