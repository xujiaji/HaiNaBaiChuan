package io.xujiaji.hnbc.activities;

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
import io.xujiaji.hnbc.contracts.MainContract;
import io.xujiaji.hnbc.fragments.MainFragment;
import io.xujiaji.hnbc.presenters.MainPresenter;
import io.xujiaji.hnbc.utils.ActivityUtils;
import io.xujiaji.hnbc.utils.ToastUtil;
import no.agens.depth.lib.tween.interpolators.ExpoIn;
import no.agens.depth.lib.tween.interpolators.QuintOut;

public class MainActivity extends BaseActivity<MainPresenter> implements MainContract.View{
    private MainContract.MainFragView mainFragment;
    private static boolean isMenuVisible = false;

    @BindView(R.id.menu_container)
    ViewGroup menu;

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onInit() {
        super.onInit();
        MainFragment fragment = MainFragment.newInstance();
        mainFragment = fragment;
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
        isMenuVisible = true;
        ObjectAnimator translationY = ObjectAnimator.ofFloat(menu, View.TRANSLATION_Y, menu.getHeight(), 0);
        translationY.setDuration(1000);
        translationY.setInterpolator(new QuintOut());
        translationY.setStartDelay(150);
        translationY.start();
//        selectMenuItem(curretMenuIndex, ((TextView) menu.getChildAt(curretMenuIndex).findViewById(R.id.item_text)).getCurrentTextColor());
//        ((MenuAnimation) currentFragment).animateTOMenu();
        mainFragment.animateTOMenu();
    }

    @Override
    public void menuClose() {
        isMenuVisible = false;
        ObjectAnimator translationY = ObjectAnimator.ofFloat(menu, View.TRANSLATION_Y, menu.getHeight());
        translationY.setDuration(750);
        translationY.setInterpolator(new ExpoIn());
        translationY.start();
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
    private void menuItemStatus(int position) {
        for (int i = 0; i < menu.getChildCount(); i++) {
            if (i == position) {
                ButterKnife.findById(menu.getChildAt(i), R.id.imgMark).setVisibility(View.VISIBLE);
            } else {
                ButterKnife.findById(menu.getChildAt(i), R.id.imgMark).setVisibility(View.GONE);
            }

        }
    }

    @Override
    public void onBackPressed() {
        if (isMenuVisible) {
            menuClose();
            mainFragment.revertFromMenu();
        } else if (mainFragment.clickBack()) {

        }else if (!ActivityUtils.exitBy2Click()){
            ToastUtil.getInstance().showShortT("马上再点一次,退出！");
        } else {
            super.onBackPressed();
        }
    }

    public static boolean isMenuVisible() {
        return isMenuVisible;
    }
}
