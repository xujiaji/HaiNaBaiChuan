package io.xujiaji.hnbc.presenters;

import android.os.Handler;

import io.xujiaji.hnbc.contracts.MainContract;

/**
 * Created by jiana on 16-7-22.
 */
public class MainFragPresenter extends BasePresenter implements MainContract.MainFragPersenter  {
    private MainContract.MainFragView view;
    private Handler handler;
    private Runnable runnable = new Runnable() {
        private int x = 1;
        private int nowIndex = 0;
        @Override
        public void run() {
            nowIndex = view.getPagerNowPosition();
            if (nowIndex >= view.getPagerSize() - 1 || nowIndex <= 0) {
                x = -x;
            }
            nowIndex += x;
            view.currentPager(nowIndex);
            handler.postDelayed(this, 2000);
        }
    };
    public MainFragPresenter(MainContract.MainFragView view) {
        super(view);
        this.view = view;
    }


    @Override
    public void start() {

    }

    @Override
    public void autoScrollPager() {
        handler = new Handler();
        handler.postDelayed(runnable, 2000);
    }

    public void stop() {
        handler.removeCallbacks(runnable);
    }
}
