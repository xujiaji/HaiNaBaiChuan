package io.xujiaji.hnbc.contracts;

import io.xujiaji.hnbc.contracts.base.Contract;

/**
 * Created by jiana on 16-11-20.
 */

public interface SetContract {
    interface Presenter extends Contract.BasePresenter {
        /**
         * 扫描缓存大小
         */
        void scanCacheSize();

        /**
         * 清理缓存
         */
        void cleanCache();
    }

    interface View extends Contract.BaseView {
        /**
         * 显示缓存大小
         * @param size
         */
        void showCacheSize(String size);

        /**
         * 提示开始清理缓存
         */
        void showStartCleanCache();
        /**
         * 显示清理缓存成功
         */
        void showCleanCacheSuccess();

        void showCleanCacheFail(String err);
    }
}
