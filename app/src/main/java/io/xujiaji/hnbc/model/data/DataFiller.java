/*
 * Copyright 2018 XuJiaji
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.xujiaji.hnbc.model.data;

import java.util.ArrayList;
import java.util.List;

import io.xujiaji.hnbc.config.C;
import io.xujiaji.hnbc.model.entity.EditItem;
import io.xujiaji.hnbc.model.entity.MainTag;
import io.xujiaji.hnbc.model.entity.Set;
import io.xujiaji.hnbc.model.entity.User;

/**
 * Created by jiana on 16-11-4.
 */

public class DataFiller {
    public static List<Set> getSetShowData() {
        List<Set> list = new ArrayList<>();
        list.add(new Set("缓存大小", "0.0KB"));
        list.add(new Set("关于", ""));
        return list;
    }

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
//        return BmobUser.getCurrentUser(User.class);
        return null;
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
//        List<EditItem> editItems = new ArrayList<>();
//        editItems.add(C.euii.HEAD, new EditItem(user.getHeadPic(), "@" + user.getUsername(), "      "));
//        editItems.add(C.euii.NICKNAME, new EditItem("昵称", user.getNickname()));
//        editItems.add(C.euii.SEX, new EditItem("性别", user.getSex() == 0 ? "男" : user.getSex() == 1 ? "女" : "保密"));
//        editItems.add(C.euii.PHONE, new EditItem("手机", user.getMobilePhoneNumber()));
//        editItems.add(C.euii.CITY, new EditItem("城市", user.getCity() == null ? "从星空而来" : user.getCity()));
//        editItems.add(C.euii.BIRTHDAY, new EditItem("生日", user.getBirthday() == null ? "yyyy-MM-dd" : user.getBirthday()));
//        editItems.add(C.euii.EMAIL, new EditItem("Email", user.getEmail()));
//        editItems.add(C.euii.PASSWORD, new EditItem("密码", "***"));
//        editItems.add(C.euii.SIGN, new EditItem("签名", user.getSign()));
        return null;
    }
}
