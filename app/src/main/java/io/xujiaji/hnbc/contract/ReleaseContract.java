package io.xujiaji.hnbc.contract;

import io.xujiaji.hnbc.contract.base.RefreshContract;
import io.xujiaji.hnbc.model.entity.Post;

/**
 * Created by jiana on 16-11-20.
 * 发布
 */

public interface ReleaseContract {
    interface Presenter extends RefreshContract.Presenter {

    }

    interface View extends RefreshContract.View<Post> {

    }
}
