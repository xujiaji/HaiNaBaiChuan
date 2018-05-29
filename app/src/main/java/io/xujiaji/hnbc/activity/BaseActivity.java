/*
 * Copyright 2018 XuJiaji
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.xujiaji.hnbc.activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.view.View;

import butterknife.ButterKnife;
import io.xujiaji.hnbc.R;
import io.xujiaji.hnbc.utils.ActivityUtils;
import io.xujiaji.hnbc.utils.LeakUtil;
import io.xujiaji.xmvp.presenters.XBasePresenter;
import io.xujiaji.xmvp.view.base.XBaseActivity;

/**
 * 项目中Activity的基类
 */
public abstract class BaseActivity<T extends XBasePresenter> extends XBaseActivity<T> {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initStatus();
    }

    @Override
    protected void onInit()
    {
        super.onInit();
        ButterKnife.bind(this);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LeakUtil.fixInputMethodManagerLeak(this);
    }
}
