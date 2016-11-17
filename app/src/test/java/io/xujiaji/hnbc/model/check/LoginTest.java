package io.xujiaji.hnbc.model.check;

import org.junit.Test;

import io.xujiaji.hnbc.utils.check.LoginCheck;

/**
 * Created by jiana on 16-11-5.
 */
public class LoginTest {

    @Test
    public void checkAccount() throws Exception {
        System.out.println(LoginCheck.checkAccount("_.%12sdfas3"));
    }

}