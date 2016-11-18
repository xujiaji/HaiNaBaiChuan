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

package io.xujiaji.hnbc.utils.check;

import static android.text.TextUtils.isEmpty;

/**
 * Created by jiana on 16-11-5.
 */

public class LoginCheck {

    /**
     * 检测签名
     *
     * @param sign
     * @return
     */
    public static String checkSign(String sign) {
        if (isEmpty(sign)) {
            return "[Err]请输入签名！";
        }
        if (sign.length() > 60) {
            return "[Err]签名不能超过60字！";
        }
        return null;
    }

    public static String checkNickname(String nickname) {
        if (isEmpty(nickname)) {
            return "[Err]请输入昵称！";
        }
        return null;
    }

    /**
     * 检测老旧密码，更新新密码
     *
     * @param oldPwd
     * @param newPwd
     * @return
     */
    public static String checkChangePassword(String oldPwd, String newPwd) {
        if (isEmpty(oldPwd)) {
            return "[Err]老密码不能为空！";
        }
        if (isEmpty(newPwd)) {
            return "[Err]新密码不能为空！";
        }
        return checkPassword(newPwd);
    }

    /**
     * 检测两次密码是否相同
     *
     * @param password
     * @param confirmPassword
     * @return
     */
    public static String checkConfirmPassword(String password, String confirmPassword) {
        if (isEmpty(confirmPassword)) {
            return "[Err]密码确认不能为空！";
        }
        if (isEmpty(password)) {
            return "[Err]请输入上一栏密码！";
        }
        if (password.equals(confirmPassword)) {
            return null;
        } else {
            return "[Err]两次密码不一样！";
        }
    }

    /**
     * 检测邮箱地址
     *
     * @param email
     * @return
     */
    public static String checkEmail(String email) {
        if (isEmpty(email)) {
            return "[Err]邮箱不能为空！";
        }
        if (email.matches("[\\w!#$%&'*+/=?^_`{|}~-]+(?:\\.[\\w!#$%&'*+/=?^_`{|}~-]+)*@(?:[\\w](?:[\\w-]*[\\w])?\\.)+[\\w](?:[\\w-]*[\\w])?")) {
            return null;
        } else {
            return "您的邮箱地址有误！";
        }
    }

    /**
     * 检测手机号
     *
     * @param phone
     * @return
     */
    public static String checkPhone(String phone) {
        if (isEmpty(phone)) {
            return "[Err]手机号不能为空！";
        }
        if (phone.matches("^(0|86|17951)?(13[0-9]|15[012356789]|17[678]|18[0-9]|14[57])[0-9]{8}$")) {
            return null;
        } else {
            return "[Err]请检测您的手机号！";
        }
    }

    /**
     * 检测输入的账户是否合法
     *
     * @param name
     * @return
     */
    public static String checkAccount(String name) {
        if (isEmpty(name)) {
            return "[Err]账户不能为空！";
        }
        if (name.length() > 20) {
            return "[Err]账号不能长于20位";
        }
        if (name.matches("[a-zA-Z0-9_.,!]+")) {
            return null;
        } else {
            return "[☞]账号用她们来组合:a-z A-Z 0-9 _ . , !";
        }
    }

    /**
     * 检测输入的密码是否合法
     *
     * @param password
     * @return
     */
    public static String checkPassword(String password) {
        if (isEmpty(password)) {
            return "[Err]密码不能为空！";
        }
        if (password.length() < 6) {
            return "[Err]密码要超过六位！";
        }
        if (password.length() > 20) {
            return "[Err]密码不能长于20位";
        }
        if (password.matches("[a-zA-Z0-9_.,!]+")) {
            return null;
        } else {
            return "[☞]密码用她们来组合:a-z A-Z 0-9 _ . , !";
        }
    }
}
