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

import java.util.Date;

import io.xujiaji.hnbc.contracts.base.Contract;

/**
 * Created by jiana on 16-11-7.
 */

public interface EditUserInfoContract {
    interface Presenter extends Contract.BasePresenter {
        void requestChangeHeadPic(String picPath);
        void requestChangeNickname(String nickname);
        void requestChangePhone(String phoneNumber);
        void requestChangeSign(String sign);
        void requestChangeCity(String city);
        void requestChangeSex(int sex);
        void requestBirthday(Date birthday);
        void requestChangeEmail(String email);
        void requestChangePassword(String oldPwd, String newPwd);
    }

    interface View extends Contract.BaseView {
        void changeHeadPicSuccess();
        void changeHeadPicFail(String err);
        void changeNicknameSuccess();
        void changeNicknameFail(String err);
        void changePhoneSuccess();
        void changePhoneFail(String err);
        void changeSignSuccess();
        void changeSignFail(String err);
        void changeCitySuccess();
        void changeCityFail(String err);
        void changeSexSuccess();
        void changeSexFail(String err);
        void changeBirthdaySuccess();
        void changeBirthdayFail(String err);
        void changeEmailSuccess();
        void changeEmailFail(String err);
        void changePasswordSuccess();
        void changePasswordFail(String err);
    }
}
