package io.xujiaji.hnbc.activities;

import android.animation.ObjectAnimator;
import android.app.Fragment;
import android.content.res.Configuration;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.xujiaji.hnbc.R;
import io.xujiaji.hnbc.contracts.MainContract;
import io.xujiaji.hnbc.fragments.BaseMainFragment;
import io.xujiaji.hnbc.fragments.LoginFragment;
import io.xujiaji.hnbc.fragments.MainFragment;
import io.xujiaji.hnbc.presenters.MainPresenter;
import io.xujiaji.hnbc.utils.ActivityUtils;
import io.xujiaji.hnbc.utils.LogUtil;
import io.xujiaji.hnbc.utils.ToastUtil;
import no.agens.depth.lib.tween.interpolators.ExpoIn;
import no.agens.depth.lib.tween.interpolators.QuintOut;

public class MainActivity extends BaseActivity<MainPresenter> implements MainContract.View {
    /**
     * Fragment数量
     */
    private static final int FRAGMENT_NUM = 5;
    /**
     * 首页
     */
    public static final int MENU_HOME = 0;
    /**
     * 用户信息
     */
    public static final int MENU_USER_INFO = 1;
    /**
     * 收藏
     */
    public static final int MENU_COLLECT = 2;
    /**
     * 发布
     */
    public static final int MENU_RELEASE = 3;
    /**
     * 设置
     */
    public static final int MENU_SET = 4;
    private BaseMainFragment[] baseFragments;
    //当前Fragment
    private BaseMainFragment currentFragment;
    private static boolean isMenuVisible = false;
    @BindView(R.id.menu_container)
    ViewGroup menu;
    private int curretMenuIndex = 0;

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onInit() {
        super.onInit();
        baseFragments = new BaseMainFragment[FRAGMENT_NUM];
        MainFragment fragment = MainFragment.newInstance();
        currentFragment = fragment;
        baseFragments[MENU_HOME] = fragment;
        addFragment(fragment, R.id.fragment_container);
        setupMenu();
    }


    @Override
    protected int getContentId() {
        return R.layout.activity_main;
    }


    @Override
    public void setupMenu() {
        List<String> list = presenter.getMenuData();
        for (int i = 0, len = list.size(); i < len; i++) {
            addMenuItem(menu, list.get(i), i);
        }
        menu.setTranslationY(20000);
    }

    @Override
    public void menuOpen() {
        if (isMenuVisible) {
            return;
        }
        isMenuVisible = true;
        ObjectAnimator translationY = ObjectAnimator.ofFloat(menu, View.TRANSLATION_Y, menu.getHeight(), 0);
        translationY.setDuration(1000);
        translationY.setInterpolator(new QuintOut());
        translationY.setStartDelay(150);
        translationY.start();
//        selectMenuItem(curretMenuIndex, ((TextView) menu.getChildAt(curretMenuIndex).findViewById(R.id.item_text)).getCurrentTextColor());
//        ((MenuAnimation) currentFragment).animateTOMenu();
        currentFragment.animateTOMenu();
    }

    @Override
    public void menuClose() {
        if (!isMenuVisible) {
            return;
        }
        isMenuVisible = false;
        ObjectAnimator translationY = ObjectAnimator.ofFloat(menu, View.TRANSLATION_Y, menu.getHeight());
        translationY.setDuration(750);
        translationY.setInterpolator(new ExpoIn());
        translationY.start();
    }

    @Override
    public void clickHome() {
        LogUtil.e3("clickHome()");
        currentFragment.exitFromMenu();
        MainFragment mainFragment = (MainFragment) baseFragments[MENU_HOME];
        goToFragment(mainFragment);
        menuClose();
    }

    @Override
    public void clickUserInfo() {
        LogUtil.e3("clickUserInfo()");
        currentFragment.exitFromMenu();
        LoginFragment loginFragment;
        if (baseFragments[MENU_USER_INFO] == null) {
            loginFragment = LoginFragment.newInstance();
            loginFragment.setIntroAnimate(true);
        } else {
            loginFragment = (LoginFragment) baseFragments[MENU_USER_INFO];
            loginFragment.setIntroAnimate(true);
            loginFragment.introAnimate();
        }
        goToFragment(loginFragment);
        baseFragments[MENU_USER_INFO] = loginFragment;
        menuClose();
    }

    @Override
    public void clickCollect() {

    }

    @Override
    public void clickRelease() {

    }

    @Override
    public void clickSet() {

    }


    private void addMenuItem(ViewGroup menu, String text, final int menuIndex) {
        ViewGroup item = (ViewGroup) LayoutInflater.from(this).inflate(R.layout.menu_item, menu, false);
        item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                menuItemStatus(menuIndex);
            }
        });
        TextView itemText = ButterKnife.findById(item, R.id.item_text);
        ImageView imgMark = ButterKnife.findById(item, R.id.imgMark);

        itemText.setText(text);
//        item.setOnClickListener(getMenuItemCLick(menuIndex, splashColor));
        if (menuIndex == 0) {
            int padding = (int) getResources().getDimension(R.dimen.menu_item_height_padding);
            menu.addView(item, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) getResources().getDimension(R.dimen.menu_item_height) + padding));
            item.setPadding(0, padding, 0, 0);
            imgMark.setVisibility(View.VISIBLE);
        } else if (menuIndex == 3) {
            int padding = (int) getResources().getDimension(R.dimen.menu_item_height_padding);
            menu.addView(item, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) getResources().getDimension(R.dimen.menu_item_height) + padding));
            item.setPadding(0, 0, 0, padding);
        } else
            menu.addView(item, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) getResources().getDimension(R.dimen.menu_item_height)));

    }

    /**
     * 更新menu的item标记状态
     */
    public void menuItemStatus(int position) {
        for (int i = 0; i < menu.getChildCount(); i++) {
            if (i == position) {
                ButterKnife.findById(menu.getChildAt(i), R.id.imgMark).setVisibility(View.VISIBLE);
            } else {
                ButterKnife.findById(menu.getChildAt(i), R.id.imgMark).setVisibility(View.GONE);
            }

        }

        LogUtil.e3("curretMenuIndex = " + curretMenuIndex);
        LogUtil.e3("position = " + position);

        if (position == curretMenuIndex) {
            onBackPressed();
            return;
        }
        switch (position) {
            case MENU_HOME:
                clickHome();
                break;
            case MENU_USER_INFO:
                clickUserInfo();
                break;
            case MENU_COLLECT:
                clickCollect();
                break;
            case MENU_RELEASE:
                clickRelease();
                break;
            case MENU_SET:
                clickSet();
                break;
        }
        curretMenuIndex = position;
    }

    @Override
    public void onBackPressed() {
        if (isMenuVisible) {
            menuClose();
            currentFragment.revertFromMenu();
        } else if (currentFragment.clickBack()) {

        } else if (!ActivityUtils.exitBy2Click()) {
            ToastUtil.getInstance().showShortT("马上再点一次,退出！");
        } else {
            super.onBackPressed();
        }
    }

    public BaseMainFragment[] getBaseMainFragments() {
        return baseFragments;
    }

    public static boolean isMenuVisible() {
        return isMenuVisible;
    }

    /**
     * 跳转到另一个fragment
     *
     * @param newFragment
     */
    public void goToFragment(final BaseMainFragment newFragment) {
        final Fragment oldFragment = currentFragment;
        currentFragment = newFragment;
        if (newFragment.isAdded()) {
            LogUtil.e3("已经添加newFragment，显示");
            getFragmentManager().beginTransaction().show(newFragment).commit();
            newFragment.setIntroAnimate(true);
            newFragment.introAnimate();
        } else {
            LogUtil.e3("没有添加newFragment，添加显示");
            getFragmentManager().beginTransaction().add(R.id.fragment_container, newFragment).commit();
        }
        getWindow().getDecorView().postDelayed(new Runnable() {
            @Override
            public void run() {
                LogUtil.e3("隐藏oldFragment");
                getFragmentManager().beginTransaction().hide(oldFragment).commit();
            }
        }, 2000);
    }

    /**
     * Fragment跳转Fragment
     * @param newFragment 要跳转的Fragment
     * @param oldFragment 当前的Fragment
     * @param delOldFrag 是否把当前的Fragment删除
     */
    public void fragGoToFrag(BaseMainFragment newFragment, final BaseMainFragment oldFragment, final boolean delOldFrag) {
        currentFragment = newFragment;
        oldFragment.exitFromMenu();
        newFragment.setIntroAnimate(true);
        if (newFragment.isAdded()) {
            getFragmentManager().beginTransaction().show(newFragment).commit();
            newFragment.introAnimate();
        } else {
            getFragmentManager().beginTransaction().add(R.id.fragment_container, newFragment).commit();
        }
        getWindow().getDecorView().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (delOldFrag) {
                    getFragmentManager().beginTransaction().remove(oldFragment).commit();
                } else {
                    getFragmentManager().beginTransaction().hide(oldFragment).commit();
                }
            }
        }, 2000);
    }
}
