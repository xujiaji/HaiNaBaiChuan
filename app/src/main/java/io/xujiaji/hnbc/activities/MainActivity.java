package io.xujiaji.hnbc.activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
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
import io.xujiaji.hnbc.config.C;
import io.xujiaji.hnbc.contracts.MainContract;
import io.xujiaji.hnbc.factory.FragmentFactory;
import io.xujiaji.hnbc.fragments.BaseMainFragment;
import io.xujiaji.hnbc.presenters.MainPresenter;
import io.xujiaji.hnbc.utils.ActivityUtils;
import io.xujiaji.hnbc.utils.LogUtil;
import io.xujiaji.hnbc.utils.ToastUtil;
import no.agens.depth.lib.tween.interpolators.ExpoIn;
import no.agens.depth.lib.tween.interpolators.QuintOut;

public class MainActivity extends BaseActivity<MainPresenter> implements MainContract.View {
    //当前Fragment
    private BaseMainFragment currentFragment;
    private static boolean isMenuVisible = false;
    @BindView(R.id.menu_container)
    ViewGroup menu;
    private ObjectAnimator translationOpen, translationClose;

    private AnimatorListenerAdapter animListener = new AnimatorListenerAdapter() {
        @Override
        public void onAnimationEnd(Animator animation) {
            super.onAnimationEnd(animation);
            LogUtil.e3("isMenuVisible = " + isMenuVisible);
            isMenuVisible = !isMenuVisible;
        }
    };

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onInit() {
        super.onInit();
        currentFragment = FragmentFactory.newFragment(C.fragment.HOME);
        addFragment(currentFragment, R.id.fragment_container);
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
        translationOpen = ObjectAnimator.ofFloat(menu, View.TRANSLATION_Y, menu.getHeight(), 0);
        translationOpen.setDuration(1000);
        translationOpen.setInterpolator(new QuintOut());
        translationOpen.setStartDelay(150);
//        translationOpen.addListener(animListener);
        translationOpen.start();
        currentFragment.animateTOMenu();
    }

    @Override
    public void menuClose() {
        if (!isMenuVisible) {
            return;
        }
        isMenuVisible = false;
        translationClose = ObjectAnimator.ofFloat(menu, View.TRANSLATION_Y, menu.getHeight());
        translationClose.setDuration(750);
        translationClose.setInterpolator(new ExpoIn());
//        translationClose.addListener(animListener);
        translationClose.start();
    }

    @Override
    public void menuToggle() {
        if (isMenuVisible) {
            menuClose();
        } else {
            menuOpen();
        }
    }

    @Override
    public void clickHome() {
        LogUtil.e3("clickHome()");
        goToFragment(C.fragment.HOME);
    }

    @Override
    public void clickUserInfo() {
        LogUtil.e3("clickUserInfo() " + presenter.checkLocalUser());
        if (presenter.checkLocalUser()) {
            goToFragment(C.fragment.USER_INFO);
        } else {
            goToFragment(C.fragment.LOGIN);
        }
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

        if (FragmentFactory.menuName(position).contains(currentFragment.getClass().getSimpleName())) {
//            menuClose();
//            currentFragment.revertFromMenu();
            onBackPressed();
            return;
        }
        switch (position) {
            case C.M_Menu.HOME:
                clickHome();
                break;
            case C.M_Menu.USER_INFO:
                clickUserInfo();
                break;
            case C.M_Menu.COLLECT:
                clickCollect();
                break;
            case C.M_Menu.RELEASE:
                clickRelease();
                break;
            case C.M_Menu.SET:
                clickSet();
                break;
        }
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


    public static boolean isMenuVisible() {
        return isMenuVisible;
    }

    /**
     * 跳转到另一个fragment
     *
     * @param fragmentKey fragment对应的key值
     */
    public void goToFragment(String fragmentKey) {
        BaseMainFragment newFragment = FragmentFactory.getOrCreate(fragmentKey);
        currentFragment.exitFromMenu();
        final BaseMainFragment oldFragment = currentFragment;
        currentFragment = newFragment;
        newFragment.setIntroAnimate(true);
        if (newFragment.isAdded()) {
            LogUtil.e3("已经添加" + newFragment.getClass().getSimpleName() + "，显示");
            getFragmentManager().beginTransaction().show(newFragment).commit();
            newFragment.introAnimate();
        } else {
            LogUtil.e3("没有添加" + newFragment.getClass().getSimpleName() + "，添加显示");
            getFragmentManager().beginTransaction().add(R.id.fragment_container, newFragment).commit();
        }
        getWindow().getDecorView().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (oldFragment.isDeleted()) {
                    getFragmentManager().beginTransaction().remove(oldFragment).commit();
                    FragmentFactory.rmFrag(oldFragment.getClass().getSimpleName());
                } else {
                    getFragmentManager().beginTransaction().hide(oldFragment).commit();
                }
            }
        }, 2000);
        menuClose();
    }
}
