package io.xujiaji.hnbc.model.check;

import static android.text.TextUtils.isEmpty;

/**
 * Created by jiana on 16-11-5.
 */

public class LoginCheck {

    /**
     * 检测输入的账户是否合法
     * @param name
     * @return
     */
    public static String checkAccount(String name) {
        if (isEmpty(name)) {
            return "[错]账户不能为空！";
        }
        if (name.matches("[a-zA-Z0-9_.,!]+")) {
            return null;
        } else {
            return "[提示]账号用她们来组合：a-z A-Z 0-9 _ . , !";
        }
    }

    /**
     * 检测输入的密码是否合法
     * @param password
     * @return
     */
    public static String checkPassword(String password) {
        if (isEmpty(password)) {
            return "[错]密码不能为空！";
        }
        if (password.length() < 6) {
            return "[错]密码要超过六位！";
        }
        if (password.matches("[a-zA-Z0-9_.,!]+")) {
            return null;
        } else {
            return "[提示]密码用她们来组合：a-z A-Z 0-9 _ . , !";
        }
    }
}
