package io.xujiaji.developersbackpack.activities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import butterknife.ButterKnife;
import io.xujiaji.developersbackpack.R;
import io.xujiaji.developersbackpack.contracts.Contract;
import io.xujiaji.developersbackpack.utils.ActivityUtils;
import io.xujiaji.developersbackpack.utils.LogHelper;

/**
 * 项目中Activity的基类
 */
public abstract class BaseActivity<T extends Contract.BasePresenter> extends AppCompatActivity implements View.OnClickListener {
    /**
     * 日志输出标志
     **/
    protected final String TAG = this.getClass().getSimpleName();

    protected T presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogHelper.changeTag(TAG);
        setContentView(getContentId());
        ButterKnife.bind(this);
        presenter = getPresenter();
        initView();
        addListener();
        initStatus();
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
        return (V) findViewById(resId);
    }

    /**
     * [绑定控件]
     *
     * @param resId 控件id
     * @return view
     */
    protected <V extends View> V $(View view, int resId) {
        return (V) view.findViewById(resId);
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
     * 添加监听
     */
    protected void addListener(){

    }

    /**
     * 添加Fragment
     */
    protected void addFragment(Fragment fragment,@IdRes int idRes) {
        FragmentManager manager = getFragmentManager();
        String tag = fragment.getClass().getSimpleName();
        Fragment f = manager.findFragmentByTag(tag);
        if (f == null) {
            manager.beginTransaction().add(idRes, fragment, tag).commit();
        }
    }

    /**
     * 初始化控件
     */
    protected abstract void initView();

    /**
     * view的点击事件
     *
     * @param id view的id
     */
    protected abstract void click(int id);

    protected abstract int getContentId();

    protected abstract T getPresenter();

}
