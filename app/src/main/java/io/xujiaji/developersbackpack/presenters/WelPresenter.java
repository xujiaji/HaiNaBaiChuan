package io.xujiaji.developersbackpack.presenters;

import android.widget.ImageView;

import java.io.File;

import io.xujiaji.developersbackpack.activities.WelcomeActivity;
import io.xujiaji.developersbackpack.contracts.WelcomeContract;
import io.xujiaji.developersbackpack.model.net.NetRequest;
import io.xujiaji.developersbackpack.utils.ImageLoadHelper;
import io.xujiaji.developersbackpack.utils.LogHelper;
import io.xujiaji.developersbackpack.utils.OtherUtils;
import io.xujiaji.developersbackpack.utils.SharedPreferencesUtil;

public class WelPresenter implements WelcomeContract.Presenter {
    public WelcomeContract.View view;
    public WelPresenter(WelcomeContract.View view) {
        this.view = view;
    }

    private void getNetImg() {
        NetRequest.Instance().getWelcomePic((WelcomeActivity) view);
    }

    @Override
    public void setWelPic(ImageView pic) {
        String welPicPath = SharedPreferencesUtil.getWelPicPath();
        LogHelper.E("获取了本地图片：" + welPicPath);
        if (welPicPath == null || "null".equals(welPicPath)) {
            getNetImg();
            return;
        }
        String nowName = welPicPath.substring(welPicPath.lastIndexOf('/') + 1, welPicPath.lastIndexOf('.'));
        LogHelper.E("本地图片名：" + nowName);
        boolean isLoad = false;
        if (!(OtherUtils.currDay()).equals(nowName)) {
            getNetImg();
            isLoad = true;
        }
        File file = new File(welPicPath);
        if (file.exists()) {
            ImageLoadHelper.loadNoCache(pic.getContext(), pic, file);
        } else if (!isLoad) {
            getNetImg();
        }
    }

    @Override
    public void start() {
        view.startAnim();
    }
}
