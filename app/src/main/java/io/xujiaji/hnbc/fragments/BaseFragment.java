package io.xujiaji.hnbc.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.xujiaji.hnbc.R;
import io.xujiaji.hnbc.contracts.Contract;
import io.xujiaji.hnbc.utils.ActivityUtils;
import io.xujiaji.hnbc.utils.GenericHelper;

/**
 * 项目中Fragment的基类
 */
public abstract class BaseFragment<T extends Contract.BasePresenter> extends Fragment {

    protected T presenter;

    protected View rootView;
    private Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        presenter = GenericHelper.initPresenter(this);
        rootView = inflater.inflate(getLayoutId(), container, false);
        unbinder = ButterKnife.bind(this, rootView);
        initStatus();
        onInit();
        onListener();
        return rootView;
    }

    private void initStatus() {
        View status = ButterKnife.findById(rootView, R.id.status);
        if (status != null) {
            ActivityUtils.initSatus(status);
        }
    }


    /**
     * 添加监听
     */
    protected void onListener(){

    }

    protected abstract int getLayoutId();

    /**
     * 初始化控件
     */
    protected void onInit(){}

    public View getRootView() {
        return this.rootView;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
