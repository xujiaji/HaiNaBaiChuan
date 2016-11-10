package io.xujiaji.hnbc.fragments;

import android.support.design.widget.TextInputLayout;
import android.text.TextPaint;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
import io.xujiaji.hnbc.R;
import io.xujiaji.hnbc.activities.MainActivity;
import io.xujiaji.hnbc.config.C;
import io.xujiaji.hnbc.contracts.LoginContract;
import io.xujiaji.hnbc.factory.FragmentFactory;
import io.xujiaji.hnbc.presenters.LoginPresenter;
import io.xujiaji.hnbc.utils.LogUtil;
import io.xujiaji.hnbc.utils.ToastUtil;
import no.agens.depth.lib.DepthLayout;

public class LoginFragment extends BaseMainFragment<LoginPresenter> implements LoginContract.View {
    @BindView(R.id.status)
    DepthLayout status;
    @BindView(R.id.username)
    EditText username;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.passwordWrapper)
    TextInputLayout passwordWrapper;
    @BindView(R.id.usernameWrapper)
    TextInputLayout usernameWrapper;
//    @BindView(R.id.google_progress)
//    GoogleProgressBar googleProgressBar;
    @BindView(R.id.btnLogin)
    Button btnLogin;
    private SweetAlertDialog dialog;

    /**
     * 当注册后来后设置这个值
     */
    private String registerUsername = null;

    public void setRegisterUsername(String name) {
        registerUsername = name;
    }

    @Override
    public void introAnimate() {
        super.introAnimate();
        if (registerUsername == null) {
            return;
        }
        username.setText(registerUsername);
    }

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_login;
    }

    @Override
    protected void onInit() {
        super.onInit();
        initOtherLoginBold();
    }

    private void showDialog() {
        dialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);
        dialog.setTitleText("正在登录");
        dialog.showCancelButton(false);
        dialog.showConfirmButton(false);
        dialog.show();
    }

    /**
     * 将其他登录方式加粗
     */
    private void initOtherLoginBold() {
        TextView tvOtherLogin = ButterKnife.findById(rootView, R.id.tvOtherLogin);
        TextPaint paint = tvOtherLogin.getPaint();
        paint.setFakeBoldText(true);
    }


    @OnClick({R.id.btnLogin, R.id.btnRegistered, R.id.btnLoginQQ, R.id.btnLoginWeixin, R.id.btnLoginSina})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnLogin:
                onLogging(true);
                usernameWrapper.setErrorEnabled(false);
                usernameWrapper.setErrorEnabled(false);
                presenter.requestLogin(username.getText().toString().trim(), password.getText().toString().trim());
                break;
            case R.id.btnRegistered:
                MainActivity.startFragment(C.fragment.REGISTER);
                break;
            case R.id.btnLoginQQ:
                presenter.requestQQ();
                break;
            case R.id.btnLoginWeixin:
                presenter.requestWeiXin();
                break;
            case R.id.btnLoginSina:
                presenter.requestSina();
                break;

        }
    }

    @Override
    public void nameFormatError(String err) {
        LogUtil.e3("");
        onLogging(false);
        usernameWrapper.setError(err);
    }

    @Override
    public void passwordFormatError(String err) {
        LogUtil.e3("");
        onLogging(false);
        passwordWrapper.setError(err);
    }

    @Override
    public void callLoginSuccess() {
        dialog.changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
        dialog.setTitleText("登陆成功");
        onLogging(false);
        FragmentFactory.updatedUser();
        setDeleted(true);
        MainActivity.startFragment(C.fragment.USER_INFO);
    }

    @Override
    public void callLoginFail(final String failMassage) {
        LogUtil.e3("");
        onLogging(false);
        ToastUtil.getInstance().showLongT(failMassage);
    }



    /**
     * 是否正在登录中
     * @param isLogging
     */
    private void onLogging(boolean isLogging) {
        LogUtil.e3("isLogging = " + isLogging);
        if (isLogging) {
            btnLogin.setClickable(false);
            btnLogin.setTextColor(getResources().getColor(R.color.btn_long_normal));
            showDialog();
//            btnLogin.setVisibility(View.GONE);
//            googleProgressBar.setVisibility(View.VISIBLE);
        } else {
            btnLogin.postDelayed(new Runnable() {
                @Override
                public void run() {
//                    googleProgressBar.setVisibility(View.GONE);
//                    btnLogin.setVisibility(View.VISIBLE);
                    btnLogin.setClickable(true);
                    btnLogin.setTextColor(getResources().getColor(R.color.btn_login_press));
                    dialog.changeAlertType(SweetAlertDialog.ERROR_TYPE);
                    dialog.dismissWithAnimation();
                }
            }, 1000);
        }
    }


    @Override
    public boolean clickBack() {
        if (super.clickBack()) {
            return true;
        }
        LogUtil.e3("clickBack");
        MainActivity.clickMenuItem(C.M_Menu.HOME);
        return true;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (dialog != null) {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
            dialog = null;
        }
    }
}
