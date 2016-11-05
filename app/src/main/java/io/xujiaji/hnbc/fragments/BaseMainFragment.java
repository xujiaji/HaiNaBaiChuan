package io.xujiaji.hnbc.fragments;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.view.ViewTreeObserver;

import io.xujiaji.hnbc.contracts.Contract;
import io.xujiaji.hnbc.utils.TransitionHelper;

/**
 * Created by jiana on 16-11-5.
 */

public abstract class BaseMainFragment<T extends Contract.BasePresenter> extends BaseFragment<T> {
    //是否是打开LoginFragment
    private boolean introAnimate = false;
    //是否同意返回
    private static boolean isAgreeBlack = false;
    AnimatorListenerAdapter showShadowListener = new AnimatorListenerAdapter() {
        @Override
        public void onAnimationEnd(Animator animation) {
            super.onAnimationEnd(animation);
            isAgreeBlack = true;
        }
    };

    @Override
    protected void onInit() {
        super.onInit();
        introAnimate();
    }

    /**
     * 右上退出这个Fragment
     */
    public void exitFromMenu() {
        TransitionHelper.animateMenuOut(getRootView());
    }

    /**
     * 打开菜单时Fragment向上侧身移动
     */
    public void animateTOMenu() {
        TransitionHelper.animateToMenuState(getView(), new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
            }
        });
    }

    /**
     * 关闭菜单时，Fragment恢复
     */
    public void revertFromMenu() {
        TransitionHelper.startRevertFromMenu(getRootView(), showShadowListener);
    }

    /**
     * 是否是打开Fragment的动画
     * @return
     */
    public boolean getIntroAnimate() {
        return introAnimate;
    }

    /**
     * 如果设置为true则显示Fragment有从左下角显示出来的动画
     * @param introAnimate
     */
    public void setIntroAnimate(boolean introAnimate) {
        this.introAnimate = introAnimate;
    }


    /**
     * 左下角显示出Fragment的动画
     */
    public void introAnimate() {
        if (!introAnimate) {
            return;
        }
        rootView.setTranslationY(0);
        isAgreeBlack = false;
        rootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                rootView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                TransitionHelper.startIntroAnim(rootView, showShadowListener);
            }
        });

    }

    /**
     * 处理点击返回，处理点击返回键时，动画没有执行完，则不执行操作
     * @return
     */
    public boolean clickBack() {
        return !isAgreeBlack;
    }
//    void exitFromMenu();
//    void animateTOMenu();
//    void revertFromMenu();
//    void introAnimate();
//    boolean getIntroAnimate();
//    void setIntroAnimate(boolean introAnimate);
}
