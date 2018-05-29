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

package io.xujiaji.hnbc.fragment;

import android.support.annotation.StringRes;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.List;

import butterknife.BindView;
import io.xujiaji.hnbc.R;
import io.xujiaji.hnbc.activity.MainActivity;
import io.xujiaji.hnbc.adapter.UserListAdapter;
import io.xujiaji.hnbc.config.C;
import io.xujiaji.hnbc.contract.UserListContract;
import io.xujiaji.hnbc.fragment.base.BaseMainFragment;
import io.xujiaji.hnbc.model.entity.User;
import io.xujiaji.hnbc.presenter.UserListPresenter;
import io.xujiaji.hnbc.utils.ActivityUtils;
import io.xujiaji.hnbc.utils.ToastUtil;

/**
 * Created by jiana on 16-11-24.
 */

public class UserListFragment extends BaseMainFragment<UserListPresenter> implements UserListContract.View {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rvUserList)
    RecyclerView rvUserList;

    public static UserListFragment newInstance() {
        return new UserListFragment();
    }

    @Override
    protected void onInit() {
        super.onInit();
        rvUserList.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    public void showTitle(@StringRes int title) {
        ActivityUtils.initBar(toolbar, title);
    }

    @Override
    protected void onListener() {
        super.onListener();
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickBack();
            }
        });
    }

    @Override
    public void requestUserListFail(String err) {
        ToastUtil.getInstance().showShortT(err);
    }

    @Override
    public void showList(List<User> users) {
        rvUserList.setAdapter(new UserListAdapter(users));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_user_list;
    }


    @Override
    public boolean clickBack() {
        if (super.clickBack()) {
            return true;
        }
        setDeleted(true);
        MainActivity.startFragment(C.fragment.USER_INFO);
        return true;
    }
}
