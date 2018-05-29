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

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;

import java.util.List;

import butterknife.BindView;
import cn.pedant.SweetAlert.SweetAlertDialog;
import io.xujiaji.hnbc.R;
import io.xujiaji.hnbc.activity.MainActivity;
import io.xujiaji.hnbc.adapter.SetAdapter;
import io.xujiaji.hnbc.config.C;
import io.xujiaji.hnbc.contract.SetContract;
import io.xujiaji.hnbc.fragment.base.BaseMainFragment;
import io.xujiaji.hnbc.model.data.DataFiller;
import io.xujiaji.hnbc.model.entity.Set;
import io.xujiaji.hnbc.presenter.SetPresenter;
import io.xujiaji.hnbc.utils.ActivityUtils;
import io.xujiaji.hnbc.utils.FileUtils;
import io.xujiaji.hnbc.utils.ToastUtil;

/**
 * Created by jiana on 16-11-20.
 * 设置
 */

public class SetFragment extends BaseMainFragment<SetPresenter> implements SetContract.View {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rvSet)
    RecyclerView rvSet;
    private List<Set> setData;
    private SweetAlertDialog mDialog;
    private SetAdapter adapter;

    public static SetFragment newInstance() {
        return new SetFragment();
    }

    @Override
    public void introAnimate() {
        super.introAnimate();
        presenter.scanCacheSize();
    }

    @Override
    protected void onInit() {
        super.onInit();
        ActivityUtils.initBar(toolbar, R.string.set);
        FileUtils.getAllCacheFile();
        initRecycler();
    }

    private void initRecycler() {
        rvSet.setLayoutManager(new LinearLayoutManager(getActivity()));
        setData = DataFiller.getSetShowData();
        adapter = new SetAdapter(setData);
        rvSet.setAdapter(adapter);
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
//        rvSet.addOnItemTouchListener(new OnItemClickListener() {
//            @Override
//            public void SimpleOnItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
//                switch (i) {
//                    case C.set.CLEAR:
//                        showStartCleanCache();
//                        break;
//                    case C.set.ABOUT:
//                        MainActivity.startFragment(C.fragment.ABOUT);
//                        break;
//                }
//            }
//        });
    }

    @Override
    public void showCacheSize(String size) {
        setData.get(C.set.CLEAR).setValue(size);
        adapter.notifyItemChanged(C.set.CLEAR);
    }

    @Override
    public void showStartCleanCache() {
        mDialog = new SweetAlertDialog(getActivity())
                .setTitleText("是否清理缓存文件？")
                .showCancelButton(true)
                .showConfirmButton(true)
                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismissWithAnimation();
                    }
                })
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.showConfirmButton(false);
                        sweetAlertDialog.showCancelButton(false);
                        sweetAlertDialog.changeAlertType(SweetAlertDialog.PROGRESS_TYPE);
                        sweetAlertDialog.setCancelable(false);
                        presenter.cleanCache();
                    }
                });

        mDialog.show();
    }

    @Override
    public void showCleanCacheSuccess() {
        setData.get(C.set.CLEAR).setValue("0.0KB");
        adapter.notifyDataSetChanged();
        if (mDialog == null || !mDialog.isShowing()) {
            return;
        }
        mDialog.setTitleText("清理成功！");
        mDialog.changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
        mDialog.dismissWithAnimation();
    }

    @Override
    public void showCleanCacheFail(String err) {
        ToastUtil.getInstance().showLongT(err);
        if (mDialog == null || !mDialog.isShowing()) {
            return;
        }
        mDialog.setTitleText(err);
        mDialog.changeAlertType(SweetAlertDialog.ERROR_TYPE);
        mDialog.dismissWithAnimation();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_set;
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
