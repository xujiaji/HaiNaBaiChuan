package io.xujiaji.hnbc.presenters;

import android.widget.ImageView;

import java.io.File;

import io.xujiaji.hnbc.activities.WelcomeActivity;
import io.xujiaji.hnbc.contracts.WelcomeContract;
import io.xujiaji.hnbc.model.net.NetRequest;
import io.xujiaji.hnbc.utils.ImgLoadUtil;
import io.xujiaji.hnbc.utils.OtherUtils;
import io.xujiaji.hnbc.utils.SharedPreferencesUtil;

public class WelPresenter extends BasePresenter <WelcomeContract.View> implements WelcomeContract.Presenter {
    public WelPresenter(WelcomeContract.View view) {
        super(view);
    }

    private void getNetImg() {
        NetRequest.Instance().getWelcomePic((WelcomeActivity) view);
    }

    @Override
    public void setWelPic(ImageView pic) {
        String welPicPath = SharedPreferencesUtil.getWelPicPath();
        if (welPicPath == null || "null".equals(welPicPath)) {
            getNetImg();
            return;
        }
        String nowName = welPicPath.substring(welPicPath.lastIndexOf('/') + 1, welPicPath.lastIndexOf('.'));
        boolean isLoad = false;
        if (!(OtherUtils.currDay()).equals(nowName)) {
            getNetImg();
            isLoad = true;
        }
        File file = new File(welPicPath);
        if (file.exists()) {
            ImgLoadUtil.loadNoCache(pic.getContext(), pic, file);
        } else if (!isLoad) {
            getNetImg();
        }
    }

    @Override
    public void start() {
        super.start();
        view.startAnim();
    }

}
