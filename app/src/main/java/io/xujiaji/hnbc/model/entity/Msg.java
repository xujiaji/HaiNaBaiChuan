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

    /**
     * 关闭编辑状态
     */
    public static final int CLICK_BACK = 2;

    public final int type;
    public final String fragment;
    public final int menuIndex;

    public Msg(int type) {
        this.type = type;
        this.menuIndex = 0;
        this.fragment = null;
    }

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
