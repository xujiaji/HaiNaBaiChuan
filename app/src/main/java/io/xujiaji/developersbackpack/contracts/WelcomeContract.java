package io.xujiaji.developersbackpack.contracts;


import android.widget.ImageView;

/**
 * 管理welcome的view和presenter的契约
 */
public interface WelcomeContract {
    interface Presenter extends Contract.BasePresenter {
        void setWelPic(ImageView pic);
    }

    interface View extends Contract.BaseView {
        void startAnim();
        void showHello();
    }
}
