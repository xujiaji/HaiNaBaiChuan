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

package io.xujiaji.hnbc.fragment.base;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import io.xujiaji.hnbc.R;
import io.xujiaji.hnbc.contract.base.RefreshContract;
import io.xujiaji.hnbc.utils.LogUtil;
import io.xujiaji.hnbc.utils.ToastUtil;
import io.xujiaji.xmvp.presenters.XBasePresenter;

/**
 * Created by jiana on 16-11-20.
 * 抽象刷新功能
 */

public abstract class BaseRefreshFragment<X, T extends XBasePresenter> extends BaseMainFragment<T> implements
        RefreshContract.View<X>,
        BaseQuickAdapter.RequestLoadMoreListener,
        SwipeRefreshLayout.OnRefreshListener {

    public static final int PAGE_SIZE = 6;
    private int mCurrentCounter = 0;
    private BaseQuickAdapter<X, BaseViewHolder> mAdapter;
    SwipeRefreshLayout swipeLayout;
    private View notLoadingView;
    private RecyclerView mRecyclerView;

    @Override
    protected void onInit() {
        super.onInit();
        mAdapter = getAdapter();
        mRecyclerView = getRecyclerView();
        swipeLayout = getSwipeLayout();

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        swipeLayout.setOnRefreshListener(this);
        mAdapter.openLoadAnimation();
//        mAdapter.openLoadMore(PAGE_SIZE);
//        mAdapter.setOnLoadMoreListener(this);
        mRecyclerView.setAdapter(mAdapter);
    }

    protected abstract BaseQuickAdapter<X, BaseViewHolder> getAdapter();

    protected abstract SwipeRefreshLayout getSwipeLayout();

    protected abstract RecyclerView getRecyclerView();

    /**
     * 更新列表成功
     */
    @Override
    public void updateListSuccess(List<X> datas) {
        if (isHidden()) {
            swipeLayout.setRefreshing(false);
            return;
        }
        mAdapter.setNewData(datas);
//        mAdapter.openLoadMore(PAGE_SIZE);
        mAdapter.removeAllFooterView();
        mCurrentCounter = PAGE_SIZE;
        swipeLayout.setRefreshing(false);
    }

    /**
     * 更新失败
     */
    @Override
    public void updateListFail(String err) {
        if (isHidden()) {
            swipeLayout.setRefreshing(false);
            return;
        }
        swipeLayout.setRefreshing(false);
        ToastUtil.getInstance().showLongT(err);
    }

    /**
     * 加载数据成功
     */
    @Override
    public void loadListDataSuccess(List<X> datas) {
        if (isHidden()) {
            return;
        }
        mAdapter.addData(datas);
        mCurrentCounter = mAdapter.getData().size();
    }

    /**
     * 加载数据失败
     *
     * @param err
     */
    @Override
    public void loadListDataFail(String err) {
        if (isHidden()) {
            return;
        }
        if (swipeLayout.isRefreshing()) {
            swipeLayout.setRefreshing(false);
        }
        mRecyclerView.post(new Runnable() {
            @Override
            public void run() {
//                mAdapter.showLoadMoreFailedView();
            }
        });
        ToastUtil.getInstance().showLongT(err);
    }

    /**
     * 数据已经被加载完
     */
    @Override
    public void loadListDateOver() {
//        mAdapter.loadComplete();
        if (notLoadingView == null) {
            notLoadingView = LayoutInflater.from(getActivity()).inflate(R.layout.not_loading, (ViewGroup) mRecyclerView.getParent(), false);
        }
        mAdapter.addFooterView(notLoadingView);
    }


    @Override
    public void onRefresh() {
        LogUtil.e3("getPresenter()     = " + getPresenter());
        getPresenter().requestUpdateListData();
    }

    @Override
    public void onLoadMoreRequested() {
        getPresenter().requestLoadListData(mCurrentCounter);
    }

    private RefreshContract.Presenter getPresenter() {
        return  (RefreshContract.Presenter) presenter;
    }
}
