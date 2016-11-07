package io.xujiaji.hnbc.model.data;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobUser;
import io.xujiaji.hnbc.config.C;
import io.xujiaji.hnbc.model.entity.MainPersonMsg;
import io.xujiaji.hnbc.model.entity.MainTag;
import io.xujiaji.hnbc.model.entity.User;

/**
 * Created by jiana on 16-11-4.
 */

public class DataFiller {
    /**
     * 获取首页tag标签集合
     * @return
     */
    public static List<MainTag> getTagsData() {
        List<MainTag> mainTags = new ArrayList<>();
        for (int i = 0; i < C.MAIN_TAG.length; i++) {
            mainTags.add(new MainTag(C.MAIN_TAG[i]));
        }
        return mainTags;
    }

    /**
     * 获取Person列表
     * @return
     */
    public static List<MainPersonMsg> getPersonMsgs() {
        List<MainPersonMsg> personMsgs = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            MainPersonMsg mpm = new MainPersonMsg();
            mpm.setBigPic("http://imgsrc.baidu.com/forum/pic/item/08466dc2d562853537a2e6d990ef76c6a6ef639a.jpg");
            mpm.setMsgContent("我曾经得过精神分裂症，但现在我们已经康复了。");
            mpm.setName("小明");
            mpm.setHeadPic("http://img5.duitang.com/uploads/item/201410/08/20141008215357_wvfMx.thumb.164_164_c.gif");
            personMsgs.add(mpm);
        }
        return personMsgs;
    }

    /**
     * 获取本地用户信息
     */
    public static User getLocalUser() {
        return BmobUser.getCurrentUser(User.class);
    }
}
