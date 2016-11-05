package io.xujiaji.hnbc.contracts;

import java.util.List;

import io.xujiaji.hnbc.model.entity.MainPersonMsg;
import io.xujiaji.hnbc.model.entity.MainTag;

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
        void clickHome();
        void clickUserInfo();
        void clickCollect();
        void clickRelease();
        void clickSet();
    }

    interface MainBaseFragView extends Contract.BaseView {

    }



    interface MainFragPersenter extends Contract.BasePresenter {
        void autoScrollPager();
        List<MainTag> getTags();
        List<MainPersonMsg> getPersonMsgs();
    }

    interface MainFragView extends MainBaseFragView {
        void contentLayoutToTop();
        void contentLayoutToTopListener(boolean start);
        void contentLayoutToDown();
        void contentLayoutToDownListener(boolean start);
        void currentPager(int position);
        int getPagerSize();
        int getPagerNowPosition();
    }
}
