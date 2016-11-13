package io.xujiaji.hnbc.activities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import butterknife.ButterKnife;
import io.xujiaji.hnbc.R;
import io.xujiaji.hnbc.presenters.BasePresenter;
import io.xujiaji.hnbc.utils.ActivityUtils;
import io.xujiaji.hnbc.utils.GenericHelper;

/**
 * 项目中Activity的基类
 */
public abstract class BaseActivity<T extends BasePresenter> extends AppCompatActivity {
    protected T presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        beforeSetContentView();
        setContentView(getContentId());
        ButterKnife.bind(this);
        try{
            presenter = GenericHelper.initPresenter(this);
        }catch (Exception e) {
            e.printStackTrace();
        }
        onInit();
        onListener();
        initStatus();
        if (presenter != null) {
            presenter.start();
        }
    }

    protected void beforeSetContentView() {

    }

    private void initStatus() {
        View status = ButterKnife.findById(this, R.id.status);
        if (status != null) {
            ActivityUtils.initSatus(status);
        }
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

    protected void onInit() {}
    protected void onListener() {}

    protected abstract int getContentId();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.end();
    }
}
