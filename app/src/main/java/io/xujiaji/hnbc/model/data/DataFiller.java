package io.xujiaji.hnbc.model.data;

import java.util.ArrayList;
import java.util.List;

import io.xujiaji.hnbc.config.Constant;
import io.xujiaji.hnbc.model.entity.MainPersonMsg;
import io.xujiaji.hnbc.model.entity.MainTag;

/**
 * Created by jiana on 16-11-4.
 */

public class DataFiller {
    public static List<MainTag> getTagsData() {
        List<MainTag> mainTags = new ArrayList<>();
        for (int i = 0; i < Constant.MAIN_TAG.length; i++) {
            mainTags.add(new MainTag(Constant.MAIN_TAG[i]));
        }
        return mainTags;
    }

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
}
