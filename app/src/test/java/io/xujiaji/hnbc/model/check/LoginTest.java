package io.xujiaji.hnbc.model.check;

import org.junit.Test;

/**
 * Created by jiana on 16-11-5.
 */
public class LoginTest {

    @Test
    public void checkAccount() throws Exception {
        System.out.println(LoginCheck.checkAccount("_.%12sdfas3"));
    }

}