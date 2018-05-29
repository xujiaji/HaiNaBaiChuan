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

import android.app.FragmentManager;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerClickListener;

import java.lang.ref.WeakReference;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.xujiaji.hnbc.R;
import io.xujiaji.hnbc.activity.MainActivity;
import io.xujiaji.hnbc.adapter.MainBottomRecyclerAdapter;
import io.xujiaji.hnbc.adapter.MainRecyclerAdapter;
import io.xujiaji.hnbc.config.C;
import io.xujiaji.hnbc.contract.MainContract;
import io.xujiaji.hnbc.fragment.base.BaseRefreshFragment;
import io.xujiaji.hnbc.model.entity.Post;
import io.xujiaji.hnbc.presenter.MainFragPresenter;
import io.xujiaji.hnbc.utils.GlideImageLoader;
import io.xujiaji.hnbc.utils.LogUtil;
import io.xujiaji.hnbc.utils.MaterialRippleHelper;
import io.xujiaji.hnbc.utils.ScreenUtils;
import io.xujiaji.hnbc.utils.ToastUtil;
import io.xujiaji.hnbc.widget.SheetLayout;
import me.everything.android.ui.overscroll.OverScrollDecoratorHelper;

public class MainFragment extends BaseRefreshFragment<Post, MainFragPresenter> implements MainContract.MainFragView {

    /**
     * 底部布局到达顶部
     */
    public static final int BOTTOM_VIEW_STATUS_TOP = 1;
    /**
     * 底部布局到达底部
     */
    public static final int BOTTOM_VIEW_STATUS_BOTTOM = 0;

    private boolean fabContentOpen = false;
    @BindView(R.id.appbar)
    ViewGroup appbar;
    @BindView(R.id.dl2)
    ViewGroup dl2;
    @BindView(R.id.dl3)
    ViewGroup dl3;
    @BindView(R.id.status)
    View status;
    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.mainRecycler)
    RecyclerView mainRecycler;
    @BindView(R.id.mainBottomRecycler)
    RecyclerView mainBottomRecycler;
    @BindView(R.id.imgScrollInfo)
    ImageView imgScrollInfo;
    @BindView(R.id.menu)
    ImageView menu;
    @BindView(R.id.swipeLayout)
    SwipeRefreshLayout swipeLayout;
    private MainFragHandler handler;
    private Runnable runnableTop, runnableBottom;
    private boolean scrollRunning;//底部view是否正在滚动
    private int oldTopMargin;//底部view最开始的topMargin值
    private RelativeLayout.LayoutParams params = null;//底部view的layoutParams
    private int minTopMargin;//底部view最小topMargin值
    private int bottomViewNowStatus;
    @BindView(R.id.bottom_sheet)
    SheetLayout mSheetLayout;

    public static MainFragment newInstance() {
        return new MainFragment();
    }


//    @Override
//    public void onHiddenChanged(boolean hidden) {
//        super.onHiddenChanged(hidden);
//        LogUtil.e3("hidden = " + hidden);
//        if (!hidden) {
//            introAnimate();
//        }
//    }

    @OnClick({R.id.menu, R.id.imgScrollInfo, R.id.fab})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.menu:
                if (!scrollRunning) {
                    ((MainActivity) getActivity()).menuToggle();
                    return;
                }

                if (bottomViewNowStatus == BOTTOM_VIEW_STATUS_TOP) {
                    handler.removeCallbacks(runnableBottom);
                    params.topMargin = minTopMargin;
                    dl3.setLayoutParams(params);
                    handler.sendEmptyMessageDelayed(MainFragHandler.OPEN_MENU, 100);
                } else if (bottomViewNowStatus == BOTTOM_VIEW_STATUS_BOTTOM) {
                    handler.removeCallbacks(runnableTop);
                    params.topMargin = oldTopMargin;
                    dl3.setLayoutParams(params);
                    handler.sendEmptyMessageDelayed(MainFragHandler.OPEN_MENU, 100);
                }
                scrollRunning = false;
                break;
            case R.id.imgScrollInfo:
                if (MainActivity.isMenuVisible()) {
                    return;
                }
                if (bottomViewNowStatus == BOTTOM_VIEW_STATUS_TOP) {
                    contentLayoutToDown();
                } else if (bottomViewNowStatus == BOTTOM_VIEW_STATUS_BOTTOM) {
                    contentLayoutToTop();
                }
                break;
            case R.id.fab:
                addWriteFragment();
                fabContentOpen = true;
                mSheetLayout.expandFab();
                break;
        }
    }

    /**
     * 添加写作页面的fragment
     */
    private void addWriteFragment() {
        FragmentManager manager = getFragmentManager();
        String tag = EditorFragment.class.getSimpleName();
        if (manager.findFragmentByTag(tag) == null) {
            manager.beginTransaction().add(R.id.bottom_sheet, EditorFragment.newInstance(), tag).commit();
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_main;
    }


    @Override
    protected void onInit() {
        super.onInit();
        handler = new MainFragHandler(this);
        MaterialRippleHelper.ripple(imgScrollInfo);
        initStatusHeight();
        initBanner();
        initRecycler();
        initSheetLayout();
        presenter.requestLoadHead(menu);
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

    /**
     * 初始化广告图片
     */
    private void initBanner() {
        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());
        //设置banner样式
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE);
    }

    @Override
    protected void updateUserInfo() {
        super.updateUserInfo();
        presenter.requestLoadHead(menu);
    }

    private void initSheetLayout() {
        View view = ButterKnife.findById(getRootView(), R.id.fab_container);
        mSheetLayout.setFab(view);
        mSheetLayout.setFabAnimationEndListener(new SheetLayout.OnFabAnimationEndListener() {
            @Override
            public void onFabAnimationEnd() {
            }
        });
    }

    private void initRecycler() {
        mainRecycler.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.HORIZONTAL));
        OverScrollDecoratorHelper.setUpOverScroll(mainRecycler, OverScrollDecoratorHelper.ORIENTATION_HORIZONTAL);
        mainRecycler.setAdapter(new MainRecyclerAdapter(presenter.getTags()));
    }


    private void initStatusHeight() {
        int statusHeight = ScreenUtils.getStatusHeight(getActivity());
        ViewGroup.LayoutParams statusParams = status.getLayoutParams();
        statusParams.height = statusHeight;
        status.setLayoutParams(statusParams);

        ViewGroup.LayoutParams params = appbar.getLayoutParams();
        params.height = params.height + statusHeight;
        appbar.setLayoutParams(params);

        RelativeLayout.LayoutParams dl2Params = (RelativeLayout.LayoutParams) dl2.getLayoutParams();
        dl2Params.topMargin = dl2Params.topMargin + statusHeight;
        dl2.setLayoutParams(dl2Params);
    }

    @Override
    protected void onListener() {

        mainBottomRecycler.addOnItemTouchListener(new OnItemChildClickListener() {
            @Override
            public void SimpleOnItemChildClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                LogUtil.e2("position = " + i);
                LogUtil.e3(view.toString());
                switch (view.getId()) {
                    case R.id.tvUserName:
                        //跳转到用户信息查看
                        goLookUser(baseQuickAdapter, i);
                        break;
                    case R.id.imgHead:
                        //跳转到用户信息查看
                        goLookUser(baseQuickAdapter, i);
                        break;
                    case R.id.layoutBaseArticle:
                        saveData(C.data.KEY_POST, baseQuickAdapter.getData().get(i));
                        MainActivity.startFragment(C.fragment.READ_ARTICLE);
                        break;
                    case R.id.btnLike:
                        Post post = (Post) baseQuickAdapter.getData().get(i);
                        presenter.requestLike(post);
                        break;
                    case R.id.btnFollow:
                        Post postF = (Post) baseQuickAdapter.getData().get(i);
                        presenter.requestFollow(postF.getAuthor());
                        break;
                }
            }

            private void goLookUser(BaseQuickAdapter baseQuickAdapter, int i){
                Post post = (Post) baseQuickAdapter.getData().get(i);
                saveData(C.data.KEY_USER, post.getAuthor());
                UserInfoFragment.SelfSwitch = false;
                MainActivity.startFragment(C.fragment.USER_INFO);
            }
        });

        mainBottomRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            int dyDiff = 0;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
//                LogUtil.e1("newState = " + newState + "; dyDiff = " + dyDiff);
                LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
                if (dyDiff <= 0 && newState == 0 && manager.findFirstVisibleItemPosition() == 0) {
                    contentLayoutToDown();
                }

                if (newState == 0) {
                    dyDiff = 0;
//                    LogUtil.e1("set newState == 0");
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
//                LogUtil.e1("dy = " + dy);
                dyDiff += dy;
                if (dy > 0) {
                    contentLayoutToTop();
                }
            }
        });

        banner.setOnBannerClickListener(new OnBannerClickListener() {
            @Override
            public void OnBannerClick(int position) {
                presenter.requestOpenBannerLink(getActivity(), position - 1);
            }
        });
    }

    /**
     * 布局到顶部
     */
    @Override
    public void contentLayoutToTop() {
        if (scrollRunning || MainActivity.isMenuVisible()) {
            return;
        }
        if (runnableTop != null) {
            contentLayoutToTopListener(true);
            handler.post(runnableTop);
            return;
        }
        runnableTop = new Runnable() {
            @Override
            public void run() {
                if (params == null) {
                    params = (RelativeLayout.LayoutParams) dl3.getLayoutParams();
                    oldTopMargin = params.topMargin;
                    minTopMargin = appbar.getHeight();
                }

                if (params.topMargin > minTopMargin) {
                    params.topMargin -= 30;
                    dl3.setLayoutParams(params);
                    handler.post(this);
                } else {
                    imgScrollInfo.setImageResource(R.drawable.ic_scroll_down);
                    handler.removeCallbacks(this);
                    contentLayoutToTopListener(false);
                }
            }
        };
        contentLayoutToTopListener(true);
        handler.post(runnableTop);
    }

    /**
     * 布局到达顶部监听
     */
    @Override
    public void contentLayoutToTopListener(boolean start) {
        scrollRunning = start;
        if (!start) {
            bottomViewNowStatus = BOTTOM_VIEW_STATUS_TOP;
        }
    }

    @Override
    public void contentLayoutToDown() {
        if (scrollRunning || MainActivity.isMenuVisible()) {
            return;
        }
        if (runnableBottom != null) {
            contentLayoutToDownListener(true);
            handler.post(runnableBottom);
            return;
        }
        runnableBottom = new Runnable() {
            @Override
            public void run() {
                if (params == null) {
                    params = (RelativeLayout.LayoutParams) dl3.getLayoutParams();
                    oldTopMargin = params.topMargin;
                    minTopMargin = appbar.getHeight();
                }

                if (params.topMargin < oldTopMargin) {
                    params.topMargin += 30;
                    dl3.setLayoutParams(params);
                    handler.post(this);
                } else {
                    imgScrollInfo.setImageResource(R.drawable.ic_scroll_top);
                    handler.removeCallbacks(this);
                    contentLayoutToDownListener(false);
                }
            }
        };
        contentLayoutToDownListener(true);
        handler.post(runnableBottom);
    }

    @Override
    public void contentLayoutToDownListener(boolean start) {
        scrollRunning = start;
        if (!start) {
            bottomViewNowStatus = BOTTOM_VIEW_STATUS_BOTTOM;
        }
    }

    @Override
    public void pullBannerDataSuccess(List<String> titles, List<String> images) {
        //设置图片集合
        banner.setImages(images);
        banner.setBannerTitles(titles);
        //banner设置方法全部调用完毕时最后调用
        banner.start();
    }

    @Override
    public void pullBannerDataFail(String err) {
        ToastUtil.getInstance().showShortT(err);
    }


    @Override
    public void likePostSuccess() {
        ToastUtil.getInstance().showShortT(R.string.like_cuccess);
    }

    @Override
    public void likePostFail(String err) {
        ToastUtil.getInstance().showShortT(err);
    }

    @Override
    public void followUserSuccess() {
        ToastUtil.getInstance().showShortT(R.string.follow_success);
    }

    @Override
    public void followUserFail(String err) {
        ToastUtil.getInstance().showShortT(err);
    }


    public static final class MainFragHandler extends Handler {
        /**
         * 打开菜单
         */
        public static final int OPEN_MENU = 0X22;
        private WeakReference<MainFragment> wr;

        public MainFragHandler(MainFragment mf) {
            wr = new WeakReference<>(mf);
        }

        @Override
        public void handleMessage(Message msg) {
            MainFragment mf = wr.get();
            if (mf == null) {
                return;
            }
            switch (msg.what) {
                case OPEN_MENU:
                    ((MainActivity) mf.getActivity()).menuOpen();
                    break;
            }
        }
    }

    @Override
    public boolean clickBack() {
        if (super.clickBack()) {
            return true;
        }
        if (fabContentOpen) {
            mSheetLayout.contractFab();
            fabContentOpen = false;
            return true;
        }
        return false;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (banner != null) {
            banner.stopAutoPlay();
            banner.removeAllViews();
            banner = null;
        }
    }
}
