package io.xujiaji.hnbc.presenters;

import android.os.Handler;
import android.widget.ImageView;

import java.util.List;

import cn.bmob.v3.BmobUser;
import io.xujiaji.hnbc.R;
import io.xujiaji.hnbc.contracts.MainContract;
import io.xujiaji.hnbc.fragments.MainFragment;
import io.xujiaji.hnbc.model.data.DataFiller;
import io.xujiaji.hnbc.model.entity.MainTag;
import io.xujiaji.hnbc.model.entity.Post;
import io.xujiaji.hnbc.model.entity.User;
import io.xujiaji.hnbc.model.net.NetRequest;
import io.xujiaji.hnbc.utils.ImgLoadUtil;

/**
 * Created by jiana on 16-7-22.
 */
public class MainFragPresenter extends BasePresenter <MainContract.MainFragView> implements MainContract.MainFragPersenter  {
    private Handler handler;
    //是否已经加载完所有数据
    private boolean loadOver = false;
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
    }

    @Override
    public void start() {
        super.start();
        requestUpdateListData();
    }

    @Override
    public void end() {
        super.end();
        runnable = null;
        handler = null;
    }

    @Override
    public void autoScrollPager() {
        handler = new Handler();
        handler.postDelayed(runnable, 2000);
    }

    @Override
    public List<MainTag> getTags() {
        return DataFiller.getTagsData();
    }

    @Override
    public void requestLoadHead(final ImageView head) {
        User user = BmobUser.getCurrentUser(User.class);
        if (user == null) {
            head.setImageResource(R.drawable.head);
            return;
        }
        ImgLoadUtil.loadHead(head.getContext(), head, user.getHeadPic());
    }

    @Override
    public void requestUpdateListData() {
        NetRequest.Instance().pullPostList(0, MainFragment.PAGE_SIZE, new NetRequest.RequestListener<List<Post>>() {
            @Override
            public void success(List<Post> posts) {
                loadOver = false;
                view.updateListDateSuccess(posts);
            }

            @Override
            public void error(String err) {
                view.updateListDateFail(err);
            }
        });
    }

    @Override
    public void requestLoadListData(int currentNum) {
        NetRequest.Instance().pullPostList(currentNum, MainFragment.PAGE_SIZE, new NetRequest.RequestListener<List<Post>>() {
            @Override
            public void success(List<Post> posts) {
                if (posts == null || posts.size() == 0) {
                    loadOver = true;
                    view.loadListDateOver();
                } else {
                    loadOver = false;
                    view.loadListDateSuccess(posts);
                }
            }

            @Override
            public void error(String err) {
                if (!loadOver) {
                    view.loadListFail(err);
                }
            }
        });
    }


    public void stop() {
        handler.removeCallbacks(runnable);
    }
}
