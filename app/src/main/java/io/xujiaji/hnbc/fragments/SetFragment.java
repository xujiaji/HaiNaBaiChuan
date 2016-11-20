package io.xujiaji.hnbc.fragments;

import android.support.v7.widget.Toolbar;
import android.view.View;

import butterknife.BindView;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
import io.xujiaji.hnbc.R;
import io.xujiaji.hnbc.activities.MainActivity;
import io.xujiaji.hnbc.config.C;
import io.xujiaji.hnbc.contracts.SetContract;
import io.xujiaji.hnbc.fragments.base.BaseMainFragment;
import io.xujiaji.hnbc.presenters.SetPresenter;
import io.xujiaji.hnbc.utils.ActivityUtils;
import io.xujiaji.hnbc.utils.FileUtils;
import io.xujiaji.hnbc.utils.ToastUtil;
import io.xujiaji.hnbc.widget.TextViewNew;

/**
 * Created by jiana on 16-11-20.
 * 设置
 */

public class SetFragment extends BaseMainFragment<SetPresenter> implements SetContract.View {

    @BindView(R.id.tvValue)
    TextViewNew tvValue;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private SweetAlertDialog mDialog;

    public static SetFragment newInstance() {
        return new SetFragment();
    }

    @OnClick(R.id.btnClean)
    public void onClick(View view) {
        showStartCleanCache();
    }


    @Override
    public void introAnimate() {
        super.introAnimate();
        presenter.scanCacheSize();
    }

    @Override
    protected void onInit() {
        super.onInit();
        ActivityUtils.initBar(toolbar, R.string.set);
        FileUtils.getAllCacheFile();
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
    public void showCacheSize(String size) {
        tvValue.setText(size);
    }

    @Override
    public void showStartCleanCache() {
        mDialog = new SweetAlertDialog(getActivity())
                .setTitleText("是否清理缓存文件？")
                .showCancelButton(true)
                .showConfirmButton(true)
                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismissWithAnimation();
                    }
                })
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.showConfirmButton(false);
                        sweetAlertDialog.showCancelButton(false);
                        sweetAlertDialog.changeAlertType(SweetAlertDialog.PROGRESS_TYPE);
                        sweetAlertDialog.setCancelable(false);
                        presenter.cleanCache();
                    }
                });

        mDialog.show();
    }

    @Override
    public void showCleanCacheSuccess() {
        tvValue.setText("0.0KB");
        if (mDialog == null || !mDialog.isShowing()) {
            return;
        }
        mDialog.setTitleText("清理成功！");
        mDialog.changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
        mDialog.dismissWithAnimation();
    }

    @Override
    public void showCleanCacheFail(String err) {
        ToastUtil.getInstance().showLongT(err);
        if (mDialog == null || !mDialog.isShowing()) {
            return;
        }
        mDialog.setTitleText(err);
        mDialog.changeAlertType(SweetAlertDialog.ERROR_TYPE);
        mDialog.dismissWithAnimation();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_set;
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
