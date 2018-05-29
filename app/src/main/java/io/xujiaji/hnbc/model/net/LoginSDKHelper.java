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


package io.xujiaji.hnbc.model.net;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.tencent.connect.UserInfo;
import com.tencent.connect.common.Constants;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import io.xujiaji.hnbc.app.App;
import io.xujiaji.hnbc.config.C;
import io.xujiaji.hnbc.utils.LogUtil;

/**
 * Created by jiana on 16-11-10.
 */

public class LoginSDKHelper {
    private static LoginSDKHelper helper;
    private Tencent mTencent;
    private IUiListener qqIUiListener;
    private Map<String, String> data;
//    private CallbackManager callbackManager;

    private LoginSDKHelper() {
        data = new HashMap<>();
    }

    public static LoginSDKHelper getIntance() {
        if (helper == null) {
            synchronized (LoginSDKHelper.class) {
                helper = new LoginSDKHelper();
            }
        }
        return helper;
    }

    public LoginSDKHelper login(Activity context, String loginType) {
        switch (loginType) {
            case C.login.QQ:
                initQQ();
                mTencent.login(context, "all", qqIUiListener);
                break;
            case C.login.WEIXIN:
                break;
            case C.login.SINA:
                break;
        }
        return this;
    }

//    private LoginSDKHelper loginFacebook(Activity context) {
////        FacebookSdk.sdkInitialize(App.getAppContext());
////        AppEventsLogger.activateApp((Application)App.getAppContext());
//        callbackManager = CallbackManager.Factory.create();
//        LoginManager loginManager = LoginManager.getInstance();
//        loginManager.logInWithPublishPermissions(
//                context,
//                null);
//        loginManager.registerCallback(callbackManager,
//                new FacebookCallback<LoginResult>() {
//                    @Override
//                    public void onSuccess(LoginResult loginResult) {
//                        LogUtil.e2(loginResult.toString());
//                        // App code
//                    }
//
//                    @Override
//                    public void onCancel() {
//                        // App code
//                    }
//
//                    @Override
//                    public void onError(FacebookException exception) {
//                        LogUtil.e2(exception.toString());
//                        // App code
//                    }
//                });
//        return this;
//    }


    public LoginSDKHelper initQQ() {
        mTencent = Tencent.createInstance(C.sdk.QQ_APP_ID, App.getAppContext());
        qqIUiListener = new IUiListener() {
            @Override
            public void onComplete(Object response) {
                if (null == response || ((JSONObject) response).length() == 0) {
                    if (listener != null) {
                        listener.fail("返回为空,登录失败");
                    }
                    return;
                }
                doComplete((JSONObject) response);
            }

            protected void doComplete(JSONObject values) {
                String token = null, expires = null, openId = null;
                try {
                    token = values.getString(Constants.PARAM_ACCESS_TOKEN);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    expires = values.getString(Constants.PARAM_EXPIRES_IN);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    openId = values.getString(Constants.PARAM_OPEN_ID);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (!TextUtils.isEmpty(token) && !TextUtils.isEmpty(expires)
                        && !TextUtils.isEmpty(openId)) {
                    mTencent.setAccessToken(token, expires);
                    mTencent.setOpenId(openId);
                }

                data.put(C.login.data.PARAM_ACCESS_TOKEN, token);
                data.put(C.login.data.PARAM_EXPIRES_IN, expires);
                data.put(C.login.data.PARAM_OPEN_ID, openId);
                if (listener != null) {
                    listener.loginSuccess(data);
                }
                getQQUserInfo(App.getAppContext());
            }

            @Override
            public void onError(UiError e) {
                if (listener != null) {
                    listener.fail("onError: " + e.errorDetail);
                }
            }

            @Override
            public void onCancel() {
                if (listener != null) {
                    listener.fail("取消QQ登录");
                }
            }
        };
        return this;
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constants.REQUEST_LOGIN ||
                requestCode == Constants.REQUEST_APPBAR) {
            Tencent.onActivityResultData(requestCode, resultCode, data, qqIUiListener);
        }

//        if (callbackManager != null) {
//            callbackManager.onActivityResult(requestCode, resultCode, data);
//        }
    }


    private void getQQUserInfo(Context context) {
        if (mTencent == null || !mTencent.isSessionValid()) {
            return;
        }

        IUiListener mIUiListener = new IUiListener() {

            @Override
            public void onError(UiError e) {
                if (listener != null) {
                    listener.fail("onError: " + e.errorDetail);
                }
            }

            @Override
            public void onComplete(final Object response) {
                if (response == null) return;
                JSONObject jsonResponse = (JSONObject) response;
                try {
                    data.put(C.login.data.PARAM_NICKNAME, jsonResponse.getString("nickname"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                try {
                    data.put(C.login.data.PARAM_GENDER, jsonResponse.getString("gender"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                try {
                    data.put(C.login.data.PARAM_CITY, jsonResponse.getString("city"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                try {
                    data.put(C.login.data.PARAM_HEAD_PIC, jsonResponse.getString("figureurl_qq_2"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                LogUtil.e2(response.toString());
                if (listener != null) {
                    listener.getUserInfoSuccess(data);
                }
            }

            @Override
            public void onCancel() {
                if (listener != null) {
                    listener.fail("获取用户信息被取消");
                }
            }
        };
        UserInfo mInfo = new UserInfo(context, mTencent.getQQToken());
        mInfo.getUserInfo(mIUiListener);

    }

    public void destroy() {
        data.clear();
//        callbackManager = null;
        data = null;
        mTencent = null;
        helper = null;
//        {
//            "ret":0, "openid":"316452C2BB789D418A135B347A877797", "access_token":
//            "C168D86A0CC7AD96D95FCD1056B24786", "pay_token":
//            "16AC4DADAADCCBB399C5B3B882300211", "expires_in":7776000, "pf":
//            "desktop_m_qq-10000144-android-2002-", "pfkey":
//            "383f19a416b314a74969f0630f8099b0", "msg":"", "login_cost":303, "query_authority_cost":
//            261, "authority_cost":0
//        }

//        {
//            "ret":0, "msg":"", "is_lost":0, "nickname":"触摸边界", "gender":"男", "province":"", "city":
//            "开罗", "figureurl":
//            "http:\/\/qzapp.qlogo.cn\/qzapp\/1105814896\/316452C2BB789D418A135B347A877797\/30", "figureurl_1":
//            "http:\/\/qzapp.qlogo.cn\/qzapp\/1105814896\/316452C2BB789D418A135B347A877797\/50", "figureurl_2":
//            "http:\/\/qzapp.qlogo.cn\/qzapp\/1105814896\/316452C2BB789D418A135B347A877797\/100", "figureurl_qq_1":
//            "http:\/\/q.qlogo.cn\/qqapp\/1105814896\/316452C2BB789D418A135B347A877797\/40", "figureurl_qq_2":
//            "http:\/\/q.qlogo.cn\/qqapp\/1105814896\/316452C2BB789D418A135B347A877797\/100", "is_yellow_vip":
//            "0", "vip":"0", "yellow_vip_level":"0", "level":"0", "is_yellow_year_vip":"0"
//        }
    }


    private ThirdLoginListener listener;

    public void addListener(ThirdLoginListener listener) {
        this.listener = listener;
    }

    public interface ThirdLoginListener {
        void loginSuccess(Map<String, String> data);

        void getUserInfoSuccess(Map<String, String> data);

        void fail(String failMsg);
    }


}
