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

package io.xujiaji.hnbc.fragments;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import io.xujiaji.hnbc.R;
import io.xujiaji.hnbc.activities.MainActivity;
import io.xujiaji.hnbc.config.C;
import io.xujiaji.hnbc.contracts.UserInfoContract;
import io.xujiaji.hnbc.factory.FragmentFactory;
import io.xujiaji.hnbc.fragments.base.BaseMainFragment;
import io.xujiaji.hnbc.model.entity.User;
import io.xujiaji.hnbc.presenters.UserInfoPresenter;
import io.xujiaji.hnbc.widget.PupList;
import io.xujiaji.hnbc.widget.TextViewNew;

/**
 * Created by jiana on 16-11-6.
 */

public class UserInfoFragment extends BaseMainFragment<UserInfoPresenter> implements UserInfoContract.View {
    /**
     * 是否是自己
     */
    public static boolean SelfSwitch = true;
    @BindView(R.id.imgHead)
    ImageView imgHead;
    @BindView(R.id.imgUserInfoBg)
    ImageView imgUserInfoBg;
    @BindView(R.id.tvUsername)
    TextView tvUsername;
    @BindView(R.id.tvNickname)
    TextViewNew tvNickname;
    @BindView(R.id.tvSign)
    TextViewNew tvSign;
    @BindView(R.id.menu)
    ImageView menu;
    @BindView(R.id.more)
    ImageView more;
    @BindView(R.id.tvFans)
    TextView tvFans;
    @BindView(R.id.tvFocus)
    TextView tvFocus;
    @BindView(R.id.tvCollect)
    TextView tvCollect;
    private PupList pupList;

    @Override
    protected void onInit() {
        super.onInit();
        boldText();
    }

    @Override
    public void introAnimate() {
        super.introAnimate();
        presenter.requestUserInfo();
    }

    /**
     * 加粗字体
     */
    private void boldText() {
        tvUsername.getPaint().setFakeBoldText(true);
        tvNickname.getPaint().setFakeBoldText(true);
    }

    @OnClick({R.id.menu, R.id.more})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.menu:
                clickBack();
                break;
            case R.id.more:
                openMore();
                break;
        }
    }

    private void openMore() {
        if (pupList == null) {
            pupList = new PupList(getActivity());
            pupList.setListener(new PupList.PupListener() {
                @Override
                public void itemClick(int itemId) {
                    if (pupList != null) {
                        pupList.dismiss();
                    }
                    switch (itemId) {
                        case C.pupmenu.EDIT:
                            MainActivity.startFragment(C.fragment.EDIT_USER_INFO);
                            break;
                        case C.pupmenu.EXIT_LOGIN:
                            presenter.requestExitLogin();
                            break;
                    }
                }
            });
        }
        pupList.show(more);
    }

    public static UserInfoFragment newInstance() {
        return new UserInfoFragment();
    }

    @Override
    protected void updateUserInfo() {
        super.updateUserInfo();
        presenter.requestUserInfo();
    }

    @Override
    public void displayUser(User user) {
        if (SelfSwitch) {
            more.setVisibility(View.VISIBLE);
            more.setClickable(true);
        } else {
            more.setVisibility(View.INVISIBLE);
            more.setClickable(false);
        }
        tvUsername.setText("@" + user.getUsername());
        tvNickname.setText(user.getNickname());
        tvSign.setText(user.getSign());
        presenter.requestDisplayHeadPic(imgHead, user.getHeadPic());
        presenter.requestDisplayUserInfoBg(imgUserInfoBg, user.getHeadPic());
    }

    @Override
    public void exitLoginSuccess() {
        setDeleted(true);
        FragmentFactory.updatedUser();
        MainActivity.startFragment(C.fragment.LOGIN);
        pupList = null;
    }

    @Override
    public void showFansNum(String num) {
        tvFans.setText(num);
    }

    @Override
    public void showFocusNum(String num) {
        tvFocus.setText(num);
    }

    @Override
    public void showCollectNum(String num) {
        tvCollect.setText(num);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_user_info;
    }

    @Override
    public boolean clickBack() {
        if (super.clickBack()) {
            return true;
        }
        SelfSwitch = true;
        MainActivity.clickMenuItem(C.M_Menu.HOME);
        return true;
    }

}
