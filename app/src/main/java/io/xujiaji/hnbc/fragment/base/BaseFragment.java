/*
 * Copyright 2016 XuJiaji
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
package io.xujiaji.hnbc.fragment.base;

import android.os.Bundle;
import android.view.View;

import butterknife.ButterKnife;
import io.xujiaji.hnbc.R;
import io.xujiaji.hnbc.utils.ActivityUtils;
import io.xujiaji.xmvp.presenters.XBasePresenter;
import io.xujiaji.xmvp.view.base.XBaseFragment;

/**
 * 项目中Fragment的基类
 */
public abstract class BaseFragment<T extends XBasePresenter> extends XBaseFragment<T> {

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initStatus();
    }

    private void initStatus() {
        View status = ButterKnife.findById(getRootView(), R.id.status);
        if (status != null) {
            ActivityUtils.initSatus(status);
        }
    }
}
