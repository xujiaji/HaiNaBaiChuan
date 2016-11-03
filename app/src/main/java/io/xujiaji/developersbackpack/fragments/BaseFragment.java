package io.xujiaji.developersbackpack.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.Constructor;

import butterknife.ButterKnife;
import io.xujiaji.developersbackpack.R;
import io.xujiaji.developersbackpack.contracts.Contract;
import io.xujiaji.developersbackpack.utils.ActivityUtils;
import io.xujiaji.developersbackpack.utils.GenericHelper;
import io.xujiaji.developersbackpack.utils.LogHelper;

/**
 * 项目中Fragment的基类
 */
public abstract class BaseFragment<T extends Contract.BasePresenter> extends Fragment implements View.OnClickListener {
    /**
     * 日志输出标志
     **/
    protected final String TAG = this.getClass().getSimpleName();

    protected T presenter;

    protected View rootView;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LogHelper.changeTag(TAG);
//        presenter = getPresenter();
        try {
            LogHelper.E("BaseFragment ----- getPresenter = " + getPresenter());
            Constructor construct = getPresenter().getConstructor(getViewClass());
            LogHelper.E("construct = " + construct);
            presenter = (T) construct.newInstance(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
        rootView = inflater.inflate(getLayoutId(), container, false);
        ButterKnife.bind(this, rootView);
        initStatus();
        initView();
        addListener();
        return rootView;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    private void initStatus() {
        View status = $(R.id.status);
        if (status != null) {
            ActivityUtils.initSatus(status);
        }
    }

    /**
     * [绑定控件]
     *
     * @param resId 控件id
     * @return view
     */
    protected <V extends View> V $(int resId) {
        return (V) rootView.findViewById(resId);
    }

    protected <Z extends ViewGroup> Z $G(int resId) {
        return (Z) rootView.findViewById(resId);
    }

    /**
     * 初始化view的点击事件
     *
     * @param viewIds
     */
    protected void initClick(int... viewIds) {
        if (viewIds == null || viewIds.length == 0) return;
        for (int viewId : viewIds) {
            $(viewId).setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View view) {
        click(view.getId());
    }

    /**
     * view的点击事件
     *
     * @param id view的id
     */
    protected abstract void click(int id);

    /**
     * 添加监听
     */
    protected void addListener(){

    }

    protected abstract int getLayoutId();

    private Class<T> getPresenter() {
        return GenericHelper.getViewClass(getClass());
    }

    protected abstract Class getViewClass();

    /**
     * 初始化控件
     */
    protected abstract void initView();

    public View getRootView() {
        return this.rootView;
    }
}
