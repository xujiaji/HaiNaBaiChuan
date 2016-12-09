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
package io.xujiaji.hnbc.contract;

import io.xujiaji.xmvp.contracts.XContract;

/**
 * Created by jiana on 16-11-4.
 */

public interface RegisterContract {
    interface Presenter extends XContract.Presenter{
        void requestRegister();
        void checkUsername(String username);
        void checkNickname(String nickname);
        void checkPhone(String phone);
        void checkEmail(String email);
        void checkPassword(String password);
        void checkPasswordConfirm(String passwordConfirm);
        void sex(int type);
    }

    interface View extends XContract.View {
        void callRegisterSuccess(String username);
        void callRegisterFail(String err);
        void errUsername(String err);
        void errNickName(String err);
        void errPhone(String err);
        void errEmail(String err);
        void errPassword(String err);
        void errPasswordConfirm(String err);
    }
}
