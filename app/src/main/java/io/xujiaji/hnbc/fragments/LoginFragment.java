package io.xujiaji.hnbc.fragments;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.text.TextPaint;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.jpardogo.android.googleprogressbar.library.GoogleProgressBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.xujiaji.hnbc.R;
import io.xujiaji.hnbc.activities.MainActivity;
import io.xujiaji.hnbc.contracts.LoginContract;
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
    @BindView(R.id.dl2)
    DepthLayout dl2;
    @BindView(R.id.fab_container)
    DepthLayout fabContainer;
    @BindView(R.id.passwordWrapper)
    TextInputLayout passwordWrapper;
    @BindView(R.id.usernameWrapper)
    TextInputLayout usernameWrapper;
    @BindView(R.id.google_progress)
    GoogleProgressBar googleProgressBar;
    @BindView(R.id.btnLogin)
    FloatingActionButton btnLogin;

    private boolean openMenu = false;


//    @Override
//    public void onHiddenChanged(boolean hidden) {
//        super.onHiddenChanged(hidden);
//        if (!hidden) {
//            LogUtil.e3("hidden = " + hidden);
//            introAnimate();
//        }
//    }

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
        initTextInputLayout();
        initOtherLoginBold();
    }

    /**
     * 将其他登录方式加粗
     */
    private void initOtherLoginBold() {
        TextView tvOtherLogin = ButterKnife.findById(rootView, R.id.tvOtherLogin);
        TextPaint paint = tvOtherLogin.getPaint();
        paint.setFakeBoldText(true);
    }

    /**
     * 初始化输入提示
     */
    private void initTextInputLayout() {
        passwordWrapper.setHint(getString(R.string.password));
        usernameWrapper.setHint(getString(R.string.username));
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
                presenter.requestRegistered(getActivity());
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
        LogUtil.e3("");
        onLogging(false);
    }

    @Override
    public void callLoginFail(final String failMassage) {
        LogUtil.e3("");
        new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtil.getInstance().showShortT(failMassage);
                        onLogging(false);
                    }
                });
            }
        }.start();
    }



    /**
     * 是否正在登录中
     * @param isLogging
     */
    private void onLogging(boolean isLogging) {
        LogUtil.e3("isLogging = " + isLogging);
        if (isLogging) {
            btnLogin.setClickable(false);
            btnLogin.setVisibility(View.GONE);
            googleProgressBar.setVisibility(View.VISIBLE);
        } else {
            googleProgressBar.postDelayed(new Runnable() {
                @Override
                public void run() {
                    googleProgressBar.setVisibility(View.GONE);
                    btnLogin.setVisibility(View.VISIBLE);
                    btnLogin.setClickable(true);
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
        ((MainActivity) getActivity()).menuItemStatus(MainActivity.MENU_HOME);
        return true;
    }

}
