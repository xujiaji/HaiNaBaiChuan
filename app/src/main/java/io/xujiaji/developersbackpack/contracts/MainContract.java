package io.xujiaji.developersbackpack.contracts;

import java.util.List;

/**
 * Created by jiana on 16-7-22.
 */
public interface MainContract {
    interface Presenter extends Contract.BasePresenter {
        List<String> getMenuData();
    }

    interface View extends Contract.BaseView {
        void setupMenu();
        void menuOpen();
        void menuClose();
    }

    interface MainFragPersenter extends Contract.BasePresenter {
        void autoScrollPager();
    }

    interface MainFragView extends Contract.BaseView {
        void contentLayoutToTop();
        void contentLayoutToTopListener(boolean start);
        void contentLayoutToDown();
        void contentLayoutToDownListener(boolean start);
        void animateTOMenu();
        void revertFromMenu();
        void exitFromMenu();
        void currentPager(int position);
        int getPagerSize();
        int getPagerNowPosition();
        boolean clickBack();
    }
}
