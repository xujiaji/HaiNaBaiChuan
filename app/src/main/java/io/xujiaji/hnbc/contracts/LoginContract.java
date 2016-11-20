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
package io.xujiaji.hnbc.contracts;

import com.facebook.login.LoginResult;

import io.xujiaji.hnbc.contracts.base.Contract;

/**
 * Created by jiana on 16-11-4.
 */

public interface LoginContract {
    interface Presenter extends Contract.BasePresenter{
        void requestLogin(String name, String password);
        void requestSina();
        void requestQQ();
        void requestWeiXin();
        void requestFacebook(LoginResult loginResult);
    }

    interface View extends Contract.BaseView {
        void showDialog();
        void nameFormatError(String err);
        void passwordFormatError(String err);
        void callLoginSuccess();
        void callLoginFail(String failMassage);
    }
}
