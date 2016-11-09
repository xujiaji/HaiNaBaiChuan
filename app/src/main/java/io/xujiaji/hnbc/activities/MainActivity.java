package io.xujiaji.hnbc.activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.res.Configuration;
import android.view.View;
import android.widget.LinearLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import io.xujiaji.hnbc.R;
import io.xujiaji.hnbc.config.C;
import io.xujiaji.hnbc.contracts.MainContract;
import io.xujiaji.hnbc.factory.FragmentFactory;
import io.xujiaji.hnbc.fragments.BaseMainFragment;
import io.xujiaji.hnbc.model.entity.Msg;
import io.xujiaji.hnbc.presenters.MainPresenter;
import io.xujiaji.hnbc.utils.ActivityUtils;
import io.xujiaji.hnbc.utils.LogUtil;
import io.xujiaji.hnbc.utils.MenuHelper;
import io.xujiaji.hnbc.utils.ToastUtil;
import no.agens.depth.lib.tween.interpolators.ExpoIn;
import no.agens.depth.lib.tween.interpolators.QuintOut;

public class MainActivity extends BaseActivity<MainPresenter> implements MainContract.View {
    //当前Fragment
    private BaseMainFragment currentFragment;
    private static boolean isMenuVisible = false;
    @BindView(R.id.menu_container)
    LinearLayout menu;
    private MenuHelper menuHelper;
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
        menuHelper = new MenuHelper();
        menuHelper.createMenu(menu, presenter.getMenuData());
//        NetRequest.Instance().uploadHeadPic(new File("/storage/emulated/0/Download/trim.jpg"));
//        NetRequest.Instance().editSign("求仁而得仁，又何怨 发愤忘食，乐以忘忧，不知老之将至 敬鬼神而远之 子罕言利，与命，与仁", new NetRequest.RequestListener<String>() {
//            @Override
//            public void success(String s) {
//
//            }
//
//            @Override
//            public void error(BmobException err) {
//
//            }
//        });
    }

    @Override
    protected int getContentId() {
        return R.layout.activity_main;
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

    @Override
    protected void onListener() {
        super.onListener();
        menuHelper.setListener(new MenuHelper.MenuItemClickListener() {
            @Override
            public void itemClick(int position) {
                menuItemStatus(position);
            }
        });
    }

    /**
     * 更新menu的item标记状态
     */
    public void menuItemStatus(int position) {
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


    /*
     * eventBus事件处理
     * ----------------------------------------------------------------------
     */

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    /**
     * 通过fragmentKey启动对应的Fragment
     * @param fragmentKey
     */
    public static void startFragment(String fragmentKey) {
        EventBus.getDefault().post(new Msg(Msg.GOTO_FRAGMENT, fragmentKey));
    }

    /**
     * 通过menuIndex选择菜单项
     * @param menuIndex
     */
    public static void clickMenuItem(int menuIndex) {
        if (ActivityUtils.isFastClick()) {
            return;
        }
        EventBus.getDefault().post(new Msg(Msg.CHOOSE_MENU, menuIndex));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMsgEvent(Msg msg) {
        switch (msg.type) {
            case Msg.GOTO_FRAGMENT:
                goToFragment(msg.fragment);
                break;
            case Msg.CHOOSE_MENU:
                menuHelper.selectMenuItem(msg.menuIndex, getResources().getColor(R.color.colorPrimary));
                menuItemStatus(msg.menuIndex);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        currentFragment.onActivityResult(requestCode, resultCode, data);
    }
}
