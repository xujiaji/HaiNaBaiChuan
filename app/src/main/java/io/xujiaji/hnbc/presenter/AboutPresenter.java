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

package io.xujiaji.hnbc.presenter;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import io.xujiaji.hnbc.app.App;
import io.xujiaji.hnbc.contract.AboutContract;
import io.xujiaji.xmvp.presenters.XBasePresenter;

/**
 * Created by jiana on 01/12/16.
 */

public class AboutPresenter extends XBasePresenter<AboutContract.View> implements AboutContract.Presenter {
    public AboutPresenter(AboutContract.View view) {
        super(view);
    }

    @Override
    public void getNowVersion() {
        PackageInfo pi = null;
        try {
            PackageManager pm = App.getAppContext().getPackageManager();
            pi = pm.getPackageInfo(App.getAppContext().getPackageName(),
                    PackageManager.GET_CONFIGURATIONS);
            view.showNowVersion(pi.versionName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
