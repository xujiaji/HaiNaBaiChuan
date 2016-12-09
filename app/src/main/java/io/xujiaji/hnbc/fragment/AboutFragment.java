package io.xujiaji.hnbc.fragment;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.Toolbar;
import android.view.View;

import butterknife.BindView;
import butterknife.OnClick;
import io.xujiaji.hnbc.R;
import io.xujiaji.hnbc.activity.MainActivity;
import io.xujiaji.hnbc.config.C;
import io.xujiaji.hnbc.contract.AboutContract;
import io.xujiaji.hnbc.fragment.base.BaseMainFragment;
import io.xujiaji.hnbc.presenter.AboutPresenter;
import io.xujiaji.hnbc.utils.ActivityUtils;
import io.xujiaji.hnbc.utils.ToastUtil;
import io.xujiaji.hnbc.widget.TextViewNew;

/**
 * Created by jiana on 01/12/16.
 */

public class AboutFragment extends BaseMainFragment<AboutPresenter> implements AboutContract.View {

    @BindView(R.id.tvNowVersion)
    TextViewNew tvNowVersion;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @OnClick(R.id.btnContactMe)
    void clickContactMe(View view) {
        Uri uri = Uri.parse(getString(R.string.sen_email_to_xu));
        Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
        //intent.putExtra(Intent.EXTRA_CC, email); // 抄送人
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.from_hnbc)); // 主题
        // intent.putExtra(Intent.EXTRA_TEXT, "这是邮件的正文部分"); // 正文
        try {
            startActivity(intent);
        } catch (Exception e) {
            ToastUtil.getInstance().showLongT(getString(R.string.cannot_find_email_app));
        }
    }

    public static AboutFragment newInstance() {
        return new AboutFragment();
    }

    @Override
    protected void onInit() {
        super.onInit();
        ActivityUtils.initBar(toolbar, R.string.about);
        presenter.getNowVersion();
    }

    @Override
    protected void onListener() {
        super.onListener();
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickBack();
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_about;
    }

    @Override
    public boolean clickBack() {
        if (super.clickBack()) {
            return true;
        }
        setDeleted(true);
        MainActivity.startFragment(C.fragment.SET);
        return true;
    }

    @Override
    public void showNowVersion(String version) {
        tvNowVersion.setText(getString(R.string.now_version) + " : " + version);
    }


}
