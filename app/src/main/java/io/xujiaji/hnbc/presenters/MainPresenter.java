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

package io.xujiaji.hnbc.presenters;

import java.util.ArrayList;
import java.util.List;

import io.xujiaji.hnbc.R;
import io.xujiaji.hnbc.app.App;
import io.xujiaji.hnbc.contracts.MainContract;
import io.xujiaji.hnbc.model.data.DataFiller;
import io.xujiaji.hnbc.model.entity.User;
import io.xujiaji.xmvp.presenters.XBasePresenter;

/**
 * Created by jiana on 16-7-22.
 */
public class MainPresenter extends XBasePresenter<MainContract.View> implements MainContract.Presenter {
    private List<String> menuData;
    public MainPresenter(MainContract.View view) {
        super(view);
        menuData = new ArrayList<>();
        menuData.add(App.getAppContext().getString(R.string.main_page));
        menuData.add(App.getAppContext().getString(R.string.self_info));
        menuData.add(App.getAppContext().getString(R.string.my_collect));
        menuData.add(App.getAppContext().getString(R.string.my_release));
        menuData.add(App.getAppContext().getString(R.string.set));
    }

    @Override
    public List<String> getMenuData() {
        return menuData;
    }

    @Override
    public boolean checkLocalUser() {
        return DataFiller.getLocalUser() != null;
    }

    @Override
    public User getUser() {
        return DataFiller.getLocalUser();
    }


    @Override
    public void start() {
//        ApiNet.phoneNumAttrApiInstance().search("10000", "f3e2cd2caa8f1eb446078eb63edf3f87")
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Subscriber<PhoneNumAttr>() {
//                    @Override
//                    public void onCompleted() {
//
//                    }
//
//                    @Override
//                    public void onError(Throwable throwable) {
//
//                    }
//
//                    @Override
//                    public void onNext(PhoneNumAttr phoneNumAttr) {
//
//                    }
//                });
    }

    @Override
    public void end() {
        super.end();
        menuData = null;
    }
}
