package io.xujiaji.hnbc.fragments;

import android.app.FragmentManager;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.lang.ref.WeakReference;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.xujiaji.hnbc.R;
import io.xujiaji.hnbc.activities.MainActivity;
import io.xujiaji.hnbc.adapters.MainBottomRecyclerAdapter;
import io.xujiaji.hnbc.adapters.MainPagerAdapter;
import io.xujiaji.hnbc.adapters.MainRecyclerAdapter;
import io.xujiaji.hnbc.contracts.MainContract;
import io.xujiaji.hnbc.presenters.MainFragPresenter;
import io.xujiaji.hnbc.utils.MaterialRippleHelper;
import io.xujiaji.hnbc.utils.ScreenUtils;
import io.xujiaji.hnbc.widget.SheetLayout;
import me.everything.android.ui.overscroll.OverScrollDecoratorHelper;
import me.relex.circleindicator.CircleIndicator;

public class MainFragment extends BaseMainFragment<MainFragPresenter> implements MainContract.MainFragView {
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
    @BindView(R.id.mainViewPager)
    ViewPager mainViewPager;
    @BindView(R.id.mainIndicator)
    CircleIndicator mainIndicator;
    @BindView(R.id.mainRecycler)
    RecyclerView mainRecycler;
    @BindView(R.id.mainBottomRecycler)
    RecyclerView mainBottomRecycler;
    @BindView(R.id.imgScrollInfo)
    ImageView imgScrollInfo;
    @BindView(R.id.menu)
    ImageView menu;
    private MainFragHandler handler;
    private Runnable runnableTop, runnableBottom;
    private boolean scrollRunning;//底部view是否正在滚动
    private int oldTopMargin;//底部view最开始的topMargin值
    private RelativeLayout.LayoutParams params = null;//底部view的layoutParams
    private int minTopMargin;//底部view最小topMargin值
    private int bottomViewNowStatus;
    @BindView(R.id.bottom_sheet)
    SheetLayout mSheetLayout;
    private MainPagerAdapter mMainPagerAdapter;


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
                    ((MainActivity) getActivity()).menuOpen();
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
        String tag = WriteFragment.class.getSimpleName();
        if (manager.findFragmentByTag(tag) == null) {
            manager.beginTransaction().add(R.id.bottom_sheet, WriteFragment.newInstance(), tag).commit();
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_main;
    }


    @Override
    protected void onInit() {
        handler = new MainFragHandler(this);
        MaterialRippleHelper.ripple(imgScrollInfo);
        initStatusHeight();
        initViewPager();
        initRecycler();
        initSheetLayout();
        presenter.requestLoadHead(menu);
    }

    @Override
    protected void updateShowHeadPic() {
        super.updateShowHeadPic();
        presenter.requestLoadHead(menu);
    }

    private void initSheetLayout() {
        View view = ButterKnife.findById(rootView, R.id.fab_container);
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

        mainBottomRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        mainBottomRecycler.setAdapter(new MainBottomRecyclerAdapter(presenter.getPersonMsgs()));
    }

    private void initViewPager() {
        mMainPagerAdapter = new MainPagerAdapter(getActivity());
        mainViewPager.setAdapter(mMainPagerAdapter);
        mainIndicator.setViewPager(mainViewPager);
        presenter.autoScrollPager();
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
        mainViewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        presenter.stop();
                        break;
                    case MotionEvent.ACTION_UP:
                        presenter.autoScrollPager();
                        break;
                }
                return false;
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
    public void currentPager(int position) {
        mainViewPager.setCurrentItem(position, true);
    }

    @Override
    public int getPagerSize() {
        return mMainPagerAdapter.getCount();
    }

    @Override
    public int getPagerNowPosition() {
        return mainViewPager.getCurrentItem();
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
}
