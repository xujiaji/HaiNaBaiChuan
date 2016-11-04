package io.xujiaji.hnbc.model.net;

import android.content.Intent;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import io.xujiaji.hnbc.activities.WelcomeActivity;
import io.xujiaji.hnbc.model.entity.Wel;
import io.xujiaji.hnbc.service.DownLoadWelPicService;
import io.xujiaji.hnbc.utils.LogUtil;
import io.xujiaji.hnbc.utils.OtherUtils;

/**
 * 网络请求
 *
 */
public class NetRequest {
    private static NetRequest mNetRequest;
    private NetRequest() {}
    public static NetRequest Instance() {
        if (mNetRequest == null) {
            synchronized (NetRequest.class) {
                mNetRequest = new NetRequest();
            }
        }
        return mNetRequest;
    }

    public void getWelcomePic (final WelcomeActivity context) {
        BmobQuery<Wel> bmobQuery = new BmobQuery<>();
        bmobQuery.addWhereEqualTo("imgDate", OtherUtils.currDay());
        bmobQuery.findObjects(new FindListener<Wel>() {
            @Override
            public void done(List<Wel> list, BmobException e) {
                if (list == null) {
                    return;
                }
                LogUtil.e3("list.size() = " + list.size());
                for (int i = 0; i < list.size(); i++) {
                    LogUtil.e3(list.get(i).toString());
                    Intent intent = new Intent(context, DownLoadWelPicService.class);
                    intent.putExtra("imgPath", list.get(i).getImgAddress());
                    context.startService(intent);
                    break;
                }
            }
        });
    }


}
