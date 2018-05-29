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

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import butterknife.BindView;
import io.xujiaji.hnbc.R;
import io.xujiaji.hnbc.activity.MainActivity;
import io.xujiaji.hnbc.adapter.MainBottomRecyclerAdapter;
import io.xujiaji.hnbc.config.C;
import io.xujiaji.hnbc.contract.ReleaseContract;
import io.xujiaji.hnbc.fragment.base.BaseRefreshFragment;
import io.xujiaji.hnbc.model.entity.Post;
import io.xujiaji.hnbc.presenter.ReleasePresenter;
import io.xujiaji.hnbc.utils.ActivityUtils;

/**
 * Created by jiana on 16-11-20.
 * 收藏
 */

public class ReleaseFragment extends BaseRefreshFragment<Post, ReleasePresenter> implements ReleaseContract.View {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.mainBottomRecycler)
    RecyclerView mainBottomRecycler;
    @BindView(R.id.swipeLayout)
    SwipeRefreshLayout swipeLayout;

    public static ReleaseFragment newInstance() {
        return new ReleaseFragment();
    }

    @Override
    protected void onInit() {
        super.onInit();
        ActivityUtils.initBar(toolbar, R.string.my_release);//初始化标题
        swipeLayout.setRefreshing(true);
    }

    @Override
    protected BaseQuickAdapter<Post, BaseViewHolder> getAdapter() {
        return new MainBottomRecyclerAdapter();
    }

    @Override
    protected SwipeRefreshLayout getSwipeLayout() {
        return swipeLayout;
    }

    @Override
    protected RecyclerView getRecyclerView() {
        return mainBottomRecycler;
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
    protected int getLayoutId() {
        return R.layout.fragment_release;
    }

    @Override
    public boolean clickBack() {
        if (super.clickBack()) {
            return true;
        }
        MainActivity.clickMenuItem(C.M_Menu.HOME);
        return true;
    }

}
