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
package io.xujiaji.hnbc.factory;

/**
 * Created by jiana on 16-11-5.
 */

public class ErrMsgFactory {

    public static String errMSG(int errNum) {
        switch (errNum) {
            case 101:
                return "用户名或密码不正确";
            case 107:
                return "时间格式不正确";
            case 202:
                return "该用户名已经存在";
            case 203:
                return "邮箱已经存在";
            case 204:
                return "必须提供一个邮箱地址";
            case 205:
                return "没有找到此用户名的用户";
            case 206:
                return "登录用户才能修改自己的信息";
            case 207:
                return "验证码错误";
            case 209:
                return "该手机号码已经存在";
            case 210:
                return "旧密码不正确";
            default:
                return "非常抱歉，发生错误：" + errNum;
        }
    }
}
