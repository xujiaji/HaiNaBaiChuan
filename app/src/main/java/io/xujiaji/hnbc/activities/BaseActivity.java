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

package io.xujiaji.hnbc.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;
import io.xujiaji.hnbc.presenters.BasePresenter;
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
        if (presenter != null) {
            presenter.start();
        }
    }

    protected void beforeSetContentView() {

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
