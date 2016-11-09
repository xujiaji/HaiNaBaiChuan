package io.xujiaji.hnbc.fragments;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import butterknife.BindView;
import butterknife.OnClick;
import io.xujiaji.hnbc.R;
import io.xujiaji.hnbc.activities.MainActivity;
import io.xujiaji.hnbc.config.C;
import io.xujiaji.hnbc.contracts.UserInfoContract;
import io.xujiaji.hnbc.factory.FragmentFactory;
import io.xujiaji.hnbc.model.entity.User;
import io.xujiaji.hnbc.presenters.UserInfoPresenter;
import io.xujiaji.hnbc.widget.PupList;
import io.xujiaji.hnbc.widget.TextViewNew;

/**
 * Created by jiana on 16-11-6.
 */

public class UserInfoFragment extends BaseMainFragment<UserInfoPresenter> implements UserInfoContract.View {

    @BindView(R.id.imgHead)
    ImageView imgHead;
    @BindView(R.id.dl2)
    RelativeLayout dl2;
    @BindView(R.id.imgUserInfoBg)
    ImageView imgUserInfoBg;
    @BindView(R.id.tvUsername)
    TextViewNew tvUsername;
    @BindView(R.id.tvNickname)
    TextViewNew tvNickname;
    @BindView(R.id.tvSign)
    TextViewNew tvSign;
    @BindView(R.id.menu)
    ImageView menu;
    @BindView(R.id.more)
    ImageView more;
    private PupList pupList;

    @Override
    protected void onInit() {
        super.onInit();
        boldText();
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
        tvUsername.setText("@" + user.getUsername());
        tvNickname.setText(user.getNickname());
        tvSign.setText(user.getSign());
        presenter.requestDisplayHeadPic(imgHead, user.getHeadPic());
        presenter.requestDisplayUserInfoBg(imgUserInfoBg, "http://c.hiphotos.baidu.com/zhidao/pic/item/8c1001e93901213f1268ab8757e736d12f2e9516.jpg");
    }

    @Override
    public void exitLoginSuccess() {
        setDeleted(true);
        FragmentFactory.updatedUser();
        MainActivity.startFragment(C.fragment.LOGIN);
        pupList = null;
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
        MainActivity.clickMenuItem(C.M_Menu.HOME);
        return true;
    }

}
