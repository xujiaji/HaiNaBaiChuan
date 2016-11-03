package io.xujiaji.developersbackpack.fragments;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.FragmentManager;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.lang.ref.WeakReference;

import io.xujiaji.developersbackpack.R;
import io.xujiaji.developersbackpack.activities.MainActivity;
import io.xujiaji.developersbackpack.adapters.MainBottomRecyclerAdapter;
import io.xujiaji.developersbackpack.adapters.MainPagerAdapter;
import io.xujiaji.developersbackpack.adapters.MainRecyclerAdapter;
import io.xujiaji.developersbackpack.anim.TransitionHelper;
import io.xujiaji.developersbackpack.contracts.MainContract;
import io.xujiaji.developersbackpack.presenters.MainFragPresenter;
import io.xujiaji.developersbackpack.utils.LogHelper;
import io.xujiaji.developersbackpack.utils.MaterialRippleHelper;
import io.xujiaji.developersbackpack.utils.ScreenUtils;
import io.xujiaji.developersbackpack.widget.SheetLayout;
import me.everything.android.ui.overscroll.OverScrollDecoratorHelper;
import me.relex.circleindicator.CircleIndicator;

public class MainFragment extends BaseFragment<MainFragPresenter> implements MainContract.MainFragView {
    /**
     * 底部布局到达顶部
     */
    public static final int BOTTOM_VIEW_STATUS_TOP = 1;
    /**
     * 底部布局到达底部
     */
    public static final int BOTTOM_VIEW_STATUS_BOTTOM = 0;

    private boolean fabContentOpen = false;


    private ViewGroup appbar, dl2, dl3;
    private View status;
    private ViewPager mainViewPager;
    private CircleIndicator mainIndicator;
    private MainPagerAdapter mMainPagerAdapter;
    private RecyclerView mainRecycler, mainBottomRecycler;
    private ImageView imgScrollInfo;
    private FloatingActionButton fab;
    private MainFragHandler handler;
    private Runnable runnableTop, runnableBottom;
    private boolean scrollRunning;//底部view是否正在滚动
    private int oldTopMargin;//底部view最开始的topMargin值
    private RelativeLayout.LayoutParams params = null;//底部view的layoutParams
    private int minTopMargin;//底部view最小topMargin值
    private int bottomViewNowStatus;
    private SheetLayout mSheetLayout;


    AnimatorListenerAdapter showShadowListener = new AnimatorListenerAdapter() {
        @Override
        public void onAnimationEnd(Animator animation) {
            super.onAnimationEnd(animation);
//            showShadow();
//            waterScene.setPause(false);
        }
    };

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    protected void click(int id) {
        switch (id) {
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
                LogHelper.E("fab clicked");
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
    protected Class<?> getViewClass() {
        return MainContract.MainFragView.class;
    }

    @Override
    protected void initView() {
        handler = new MainFragHandler(this);
        appbar = $G(R.id.appbar);
        dl2 = $G(R.id.dl2);
        status = $(R.id.status);
        fab = $(R.id.fab);
        dl3 = $(R.id.dl3);
        mSheetLayout = $(R.id.bottom_sheet);
        mainViewPager = $(R.id.mainViewPager);
        mainIndicator = $(R.id.mainIndicator);
        mainRecycler = $(R.id.mainRecycler);
        imgScrollInfo = $(R.id.imgScrollInfo);
        mainBottomRecycler = $(R.id.mainBottomRecycler);
        MaterialRippleHelper.ripple(imgScrollInfo);
        initStatusHeight();
        initViewPager();
        initClick(R.id.menu, R.id.imgScrollInfo, R.id.fab);
        initRecycler();
        initSheetLayout();
    }

    private void initSheetLayout() {
        View view = $(R.id.fab_container);
        mSheetLayout.setFab(view);
        mSheetLayout.setFabAnimationEndListener(new SheetLayout.OnFabAnimationEndListener() {
            @Override
            public void onFabAnimationEnd() {
            }
        });
    }

    private void initRecycler() {
        mainRecycler.setLayoutManager(new GridLayoutManager(getActivity(), 2, GridLayoutManager.HORIZONTAL, false));
        OverScrollDecoratorHelper.setUpOverScroll(mainRecycler, OverScrollDecoratorHelper.ORIENTATION_HORIZONTAL);
        mainRecycler.setAdapter(new MainRecyclerAdapter());

        mainBottomRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        mainBottomRecycler.setAdapter(new MainBottomRecyclerAdapter());
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
    protected void addListener() {
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

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
                if (newState == 0 && manager.findFirstVisibleItemPosition() == 0) {
                    contentLayoutToDown();
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
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

    /**
     * 布局到顶部
     */

    @Override
    public void animateTOMenu() {
        LogHelper.E("------------------MainFragment ---- animateTOMenu rootView = " + getView());
        TransitionHelper.animateToMenuState(getView(), new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
            }
        });
//        menuIcon.animateIconState(MaterialMenuDrawable.IconState.ARROW);
//        hideShadow();
//        waterScene.setPause(true);
    }

    @Override
    public void revertFromMenu() {
        TransitionHelper.startRevertFromMenu(getRootView(), showShadowListener);
//        menuIcon.animateIconState(MaterialMenuDrawable.IconState.BURGER);
//        waterScene.setPause(true);
    }

    @Override
    public void exitFromMenu() {
        TransitionHelper.animateMenuOut(getRootView());
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
        if (fabContentOpen) {
            mSheetLayout.contractFab();
            fabContentOpen = false;
            return true;
        }
        return false;
    }
}
