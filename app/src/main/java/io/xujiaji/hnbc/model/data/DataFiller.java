package io.xujiaji.hnbc.model.data;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobUser;
import io.xujiaji.hnbc.config.C;
import io.xujiaji.hnbc.model.entity.EditItem;
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
     * 获取本地用户信息
     */
    public static User getLocalUser() {
        return BmobUser.getCurrentUser(User.class);
    }

    /**
     * 填充编辑个人信息界面显示信息
     * @return
     */
    public static List<EditItem> getEditList() {
        User user = getLocalUser();
        if (user == null) {
            return null;
        }
        List<EditItem> editItems = new ArrayList<>();
        editItems.add(C.euii.HEAD, new EditItem(user.getHeadPic(), "@" + user.getUsername(), "      "));
        editItems.add(C.euii.NICKNAME, new EditItem("昵称", user.getNickname()));
        editItems.add(C.euii.SEX, new EditItem("性别", user.getSex() == 0 ? "男" : user.getSex() == 1 ? "女" : "保密"));
        editItems.add(C.euii.PHONE, new EditItem("手机", user.getMobilePhoneNumber()));
        editItems.add(C.euii.CITY, new EditItem("城市", user.getCity() == null ? "从星空而来" : user.getCity()));
        try{
            editItems.add(C.euii.BIRTHDAY, new EditItem("生日", SimpleDateFormat.getDateInstance().format(user.getBirthday())));
        }catch (Exception e) {
            editItems.add(C.euii.BIRTHDAY, new EditItem("生日", "yyyy-MM-dd"));
            e.printStackTrace();
        }
        editItems.add(C.euii.EMAIL, new EditItem("Email", user.getEmail()));
        editItems.add(C.euii.PASSWORD, new EditItem("密码", "***"));
        editItems.add(C.euii.SIGN, new EditItem("签名", user.getSign()));
        return editItems;
    }
}
