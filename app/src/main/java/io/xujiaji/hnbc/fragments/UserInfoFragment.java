package io.xujiaji.hnbc.fragments;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import butterknife.BindView;
import butterknife.OnClick;
import io.xujiaji.hnbc.R;
import io.xujiaji.hnbc.activities.MainActivity;
import io.xujiaji.hnbc.contracts.UserInfoContract;
import io.xujiaji.hnbc.model.entity.User;
import io.xujiaji.hnbc.presenters.UserInfoPresenter;
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
                presenter.requestOpenMore(more);
                break;
        }
    }

    public static UserInfoFragment newInstance() {
        return new UserInfoFragment();
    }

    @Override
    public void displayUser(User user) {
        tvUsername.setText("@" + user.getUsername());
        tvNickname.setText(user.getNickname());
        tvSign.setText(user.getSign());
        presenter.requestDisplayHeadPic(imgHead, user.getHeadPic());
        presenter.requestDisplayUserInfoBg(imgUserInfoBg, "http://easyread.ph.126.net/TakfO4_UqLa9ieRuhJGEYQ==/7917100546012586604.jpg");
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
        final MainActivity mainActivity = (MainActivity) getActivity();
        mainActivity.menuToggle();
        return true;
    }

}
