package io.xujiaji.hnbc.contracts;

import io.xujiaji.hnbc.model.entity.Post;

/**
 * Created by jiana on 16-11-20.
 *
 */

public interface CollectContract {
    interface Presenter extends RefreshContract.Presenter {

    }

    interface View extends RefreshContract.View<Post> {

    }
}
