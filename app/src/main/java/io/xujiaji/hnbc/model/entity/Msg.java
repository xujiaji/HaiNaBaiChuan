package io.xujiaji.hnbc.model.entity;

/**
 * Created by jiana on 16-11-7.
 */

public class Msg {
    /**
     * 跳转fragment
     */
    public static final int GOTO_FRAGMENT = 0;
    /**
     * 选择菜单
     */
    public static final int CHOOSE_MENU = 1;
    public final int type;
    public final String fragment;
    public final int menuIndex;

    public Msg(int type, int menuIndex) {
        this.type = type;
        this.menuIndex = menuIndex;
        fragment = null;
    }

    public Msg(int type, String fragment) {
        this.type = type;
        this.fragment = fragment;
        menuIndex = 0;
    }
}
