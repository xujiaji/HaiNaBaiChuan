package io.xujiaji.hnbc.contract;

import io.xujiaji.xmvp.contracts.XContract;

/**
 * Created by jiana on 16-11-20.
 */

public interface SetContract {
    interface Presenter extends XContract.Presenter {
        /**
         * 扫描缓存大小
         */
        void scanCacheSize();

        /**
         * 清理缓存
         */
        void cleanCache();
    }

    interface View extends XContract.View {
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
