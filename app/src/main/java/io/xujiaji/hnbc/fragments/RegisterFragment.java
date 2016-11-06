package io.xujiaji.hnbc.fragments;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.widget.RadioGroup;

import com.jpardogo.android.googleprogressbar.library.GoogleProgressBar;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import io.xujiaji.hnbc.R;
import io.xujiaji.hnbc.activities.MainActivity;
import io.xujiaji.hnbc.config.Constant;
import io.xujiaji.hnbc.contracts.RegisterContract;
import io.xujiaji.hnbc.presenters.RegisterPresenter;
import io.xujiaji.hnbc.utils.LogUtil;
import io.xujiaji.hnbc.utils.ToastUtil;

/**
 * Created by jiana on 16-11-5.
 */

public class RegisterFragment extends BaseMainFragment<RegisterPresenter> implements RegisterContract.View {

    @BindView(R.id.usernameWrapper)
    TextInputLayout usernameWrapper;
    @BindView(R.id.nicknameWrapper)
    TextInputLayout nicknameWrapper;
    @BindView(R.id.phoneWrapper)
    TextInputLayout phoneWrapper;
    @BindView(R.id.emailWrapper)
    TextInputLayout emailWrapper;
    @BindView(R.id.passwordWrapper)
    TextInputLayout passwordWrapper;
    @BindView(R.id.passwordConfirmWrapper)
    TextInputLayout passwordConfirmWrapper;
    @BindView(R.id.radio)
    RadioGroup radioGroup;
    @BindView(R.id.btnRegistered)
    FloatingActionButton btnRegistered;
    @BindView(R.id.google_progress)
    GoogleProgressBar googleProgressBar;

    @OnClick(R.id.btnRegistered)
    void onClick(View view) {
        onRegister(true);
        presenter.requestRegister();
    }

    @Override
    protected void onInit() {
        super.onInit();
        radioGroup.check(R.id.rbSexSecret);
    }

    @Override
    protected void onListener() {
        super.onListener();
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rbMan:
                        presenter.sex(Constant.SEX_MAN);
                        break;
                    case R.id.rbWoman:
                        presenter.sex(Constant.SEX_WOMAN);
                        break;
                    case R.id.rbSexSecret:
                        presenter.sex(Constant.SEX_SECRET);
                        break;
                }
            }
        });
    }

    public static RegisterFragment newInstance() {
        return new RegisterFragment();
    }

    @Override
    public void callRegisterSuccess(String username) {
        onRegister(false);
        ToastUtil.getInstance().showLongT("注册成功！\n去登陆吧！");
        final MainActivity mainActivity = (MainActivity) getActivity();
        LoginFragment lf = (LoginFragment) mainActivity.getBaseMainFragments()[MainActivity.MENU_USER_INFO];
        lf.setRegisterUsername(username);
        clickBack();
    }

    @Override
    public void callRegisterFail(String err) {
        onRegister(false);
        ToastUtil.getInstance().showLongT(err);
    }

    @Override
    public void errUsername(String err) {
        usernameWrapper.setError(err);
    }

    @Override
    public void errNickName(String err) {
        nicknameWrapper.setError(err);
    }

    @Override
    public void errPhone(String err) {
        phoneWrapper.setError(err);
    }

    @Override
    public void errEmail(String err) {
        emailWrapper.setError(err);
    }

    @Override
    public void errPassword(String err) {
        passwordWrapper.setError(err);
    }

    @Override
    public void errPasswordConfirm(String err) {
        passwordConfirmWrapper.setError(err);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_register;
    }


    @OnTextChanged(R.id.username)
    public void usernameChanged(CharSequence text) {
        usernameWrapper.setErrorEnabled(false);
        presenter.checkUsername(text.toString());
    }

    @OnTextChanged(R.id.nickname)
    public void nicknameChanged(CharSequence text) {
        nicknameWrapper.setErrorEnabled(false);
        presenter.checkNickname(text.toString());
    }

    @OnTextChanged(R.id.phone)
    public void phoneChanged(CharSequence text) {
        phoneWrapper.setErrorEnabled(false);
        presenter.checkPhone(text.toString());
    }

    @OnTextChanged(R.id.email)
    public void emailChanged(CharSequence text) {
        emailWrapper.setErrorEnabled(false);
        presenter.checkEmail(text.toString());
    }

    @OnTextChanged(R.id.password)
    public void passwordChanged(CharSequence text) {
        passwordWrapper.setErrorEnabled(false);
        presenter.checkPassword(text.toString());
    }

    @OnTextChanged(R.id.passwordConfirm)
    public void passwordConfirmChanged(CharSequence text) {
        passwordConfirmWrapper.setErrorEnabled(false);
        presenter.checkPasswordConfirm(text.toString());
    }

    @Override
    public boolean clickBack() {
        if (super.clickBack()) {
            return true;
        }
        final MainActivity mainActivity = (MainActivity) getActivity();
        mainActivity.fragGoToFrag(mainActivity.getBaseMainFragments()[MainActivity.MENU_USER_INFO],
                this,
                true);
        return true;
    }


    /**
     * 是否正在注册中
     */
    private void onRegister(boolean isRegister) {
        LogUtil.e3("isLogging = " + isRegister);
        if (isRegister) {
            btnRegistered.setClickable(false);
            btnRegistered.setVisibility(View.GONE);
            googleProgressBar.setVisibility(View.VISIBLE);
        } else {
            googleProgressBar.setVisibility(View.GONE);
            btnRegistered.setVisibility(View.VISIBLE);
            btnRegistered.setClickable(true);
        }
    }
}
