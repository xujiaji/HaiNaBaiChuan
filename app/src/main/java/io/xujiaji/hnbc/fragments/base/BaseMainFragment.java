/*
 * Copyright 2016 XuJiaji
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.xujiaji.hnbc.fragments.base;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.view.ViewTreeObserver;

import java.util.HashMap;
import java.util.Map;

import io.xujiaji.hnbc.presenters.BasePresenter;
import io.xujiaji.hnbc.utils.LogUtil;
import io.xujiaji.hnbc.utils.TransitionHelper;

/**
 * Created by jiana on 16-11-5.
 */

public abstract class BaseMainFragment<T extends BasePresenter> extends BaseFragment<T> {
    //是否是打开LoginFragment
    private boolean introAnimate = false;
    /**
     * 是否将Fragment删除<br>
     * 代表着这个Fragment在跳转到另一个Fragment时会被删除掉<br>
     * 处理这个逻辑的方法在MainActivity的goToFragment()方法中
     */
    private boolean isDeleted = false;
    //是否同意返回
    private boolean isAgreeBlack = true;

    //是否更新过用户信息
    private boolean updatedUser = false;

    //保存一些数据
    private static Map<String, Object> dataSave = new HashMap<>(1);

    /**
     * 获取数据
     * @param key
     * @param <D>
     * @return
     */
    public static <D> D getData(String key) {
        Object obj = dataSave.get(key);
        if (obj == null) {
            return null;
        } else {
            return (D) obj;
        }
    }

    public static void clearData() {
        dataSave.clear();
    }

    /**
     * 保存数据
     * @param key
     * @param obj
     */
    public static void saveData(String key, Object obj) {
        dataSave.put(key, obj);
    }

    AnimatorListenerAdapter showShadowListener = new AnimatorListenerAdapter() {
        @Override
        public void onAnimationEnd(Animator animation) {
            super.onAnimationEnd(animation);
            isAgreeBlack = true;
            LogUtil.e2("Fragment Animate end");
        }
    };

    @Override
    protected void onInit() {
        super.onInit();
        introAnimate();
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (updatedUser && !hidden) {
            updateUserInfo();
            updatedUser = false;
        }
    }

    /**
     * 更新显示新的头像
     */
    protected void updateUserInfo() {

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
        TransitionHelper.animateToMenuState(getView(), showShadowListener);
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
        LogUtil.e2("Fragment Animate introAnimate start");
        rootView.setTranslationY(0);
        isAgreeBlack = false;
        rootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                introAnimate = false;
                rootView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                TransitionHelper.startIntroAnim(rootView, showShadowListener);
            }
        });

    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    /**
     * 处理点击返回，处理点击返回键时，动画没有执行完，则不执行操作
     * @return
     */
    public boolean clickBack() {
        return !isAgreeBlack;
    }

    public void setUpdatedUser(boolean updatedUser) {
        this.updatedUser = updatedUser;
    }
}
