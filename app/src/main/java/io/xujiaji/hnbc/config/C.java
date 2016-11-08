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
         * 编辑个人信息
         */
        public static final String EDIT_USER_INFO = "EditUserInfoFragment";
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
        public static final String COLLECT = "collect";
        /**
         * key：发布
         */
        public static final String RELEASE = "release";
        /**
         * key：设置
         */
        public static final String SET = "set";

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
}
