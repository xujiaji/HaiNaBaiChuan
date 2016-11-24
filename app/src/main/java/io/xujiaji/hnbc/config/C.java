/*
 * Copyright 2016 XuJiaji
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
package io.xujiaji.hnbc.config;

/**
 * 常量
 */
public class C {
    /**
     * Bmob云后台
     */
    public static class CBmob {
        public static final String BMOB_ID = "b9e6bbef9e92ca59a543ff3702b1836d";
    }

    /**
     * 文件配置
     */
    public static class CFile {
        public static final String APP_FILE = "developers_backpack";
        public static final String XML_PREFERENCE_NAME = "developers_backpack";
    }

    /**
     * 首页中央tag标签
     */
    public static String[] MAIN_TAG = {"手机号码归属地",
            "历史上的今天",
            "股票数据",
            "天气预报",
            "身份证查询",
            "笑话大全",
            "IP地址",
            "图灵机器人",
            "汇率",
            "微信精选",
            "万年历",
            "周公解梦",
            "新华字典",
            "QQ号测吉凶",
            "邮编查询",
            "电视节目时间表",
            "简/繁/火星字体转换",
            "新闻头条"};
    /**
     * 男
     */
    public static final int SEX_MAN = 0;
    /**
     * 女
     */
    public static final int SEX_WOMAN = 1;
    /**
     * 性别保密
     */
    public static final int SEX_SECRET = 2;

    /**
     * 首页菜单位置标识
     */
    public static class M_Menu {
        /**
         * 首页
         */
        public static final int HOME = 0;
        /**
         * 个人信息
         */
        public static final int USER_INFO = 1;
        /**
         * 我的收藏
         */
        public static final int COLLECT = 2;
        /**
         * 我的发布
         */
        public static final int RELEASE = 3;
        /**
         * 设置
         */
        public static final int SET = 4;
    }

    public static class data {
        /**
         * 保存文章数据
         */
        public static final String KEY_POST = "post_key";

        /**
         * 保存用户信息
         */
        public static final String KEY_USER = "user_info";
    }

    /**
     * 首页fragment Key管理
     */
    public static class fragment {
        /**
         * key：主页
         */
        public static final String HOME = "MainFragment";
        /**
         * key：用户信息
         */
        public static final String USER_INFO = "UserInfoFragment";

        /**
         * 用户列表
         */
        public static final String USER_LIST = "UserListFragment";

        /**
         * 编辑个人信息
         */
        public static final String EDIT_USER_INFO = "EditUserInfoFragment";

        /**
         * key：阅读文章
         */
        public static final String READ_ARTICLE = "ReadArticleFragment";

        /**
         * key：登陆
         */
        public static final String LOGIN = "LoginFragment";

        /**
         * key：注册
         */
        public static final String REGISTER = "RegisterFragment";
        /**
         * key：收藏
         */
        public static final String COLLECT = "CollectFragment";
        /**
         * key：发布
         */
        public static final String RELEASE = "ReleaseFragment";
        /**
         * key：设置
         */
        public static final String SET = "SetFragment";

    }

    /**
     * 弹出菜单id号
     */
    public static class pupmenu {
        /**
         * 编辑
         */
        public static final int EDIT = 0;
        /**
         * 退出登陆
         */
        public static final int EXIT_LOGIN = 1;

    }

    /**
     * 个人信息编辑界面对应的id号
     */
    public static class euii {
        /**
         * 头像
         */
        public static final int HEAD = 0;
        /**
         * 昵称
         */
        public static final int NICKNAME = 1;
        /**
         * 性别
         */
        public static final int SEX = 2;
        /**
         * 手机
         */
        public static final int PHONE = 3;
        /**
         * 城市
         */
        public static final int CITY = 4;
        /**
         * 生日
         */
        public static final int BIRTHDAY = 5;
        /**
         * Email
         */
        public static final int EMAIL = 6;
        /**
         * 密码
         */
        public static final int PASSWORD = 7;
        /**
         * 签名
         */
        public static final int SIGN = 8;
    }

    public static class login {
        /**
         * qq登陆
         */
        public static final String QQ = "qq";
        /**
         * 新浪登陆
         */
        public static final String SINA = "weibo";
        /**
         * 微信登陆
         */
        public static final String WEIXIN = "weixin";

        public static final String FACEBOOK = "facebook";

        public static class data {
            public static final String PARAM_ACCESS_TOKEN = "access_token";
            public static final String PARAM_EXPIRES_IN = "expires_in";
            public static final String PARAM_OPEN_ID = "openid";
            public static final String PARAM_NICKNAME = "nickname";
            public static final String PARAM_CITY = "city";
            public static final String PARAM_HEAD_PIC = "head_pic";
            public static final String PARAM_GENDER = "gender";
        }
    }

    /**
     * sdk配置
     */
    public static class sdk {
        public static final String QQ_APP_ID = "1105814896";
    }

    /**
     * SharedPreferences 存储key
     */
    public static class preference {
        /**
         * 文章
         */
        public static final String ARTICLE = "article";
        /**
         * 文章标题
         */
        public static final String ARTICLE_TITLE = "article_title";
    }
}
