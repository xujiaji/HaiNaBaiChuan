package io.xujiaji.hnbc.model.net;

import android.content.Intent;

import java.io.File;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.LogInListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;
import io.xujiaji.hnbc.activities.WelcomeActivity;
import io.xujiaji.hnbc.model.entity.User;
import io.xujiaji.hnbc.model.entity.Wel;
import io.xujiaji.hnbc.service.DownLoadWelPicService;
import io.xujiaji.hnbc.utils.LogUtil;
import io.xujiaji.hnbc.utils.OtherUtils;

/**
 * 网络请求
 */
public class NetRequest {
    private static NetRequest mNetRequest;

    private NetRequest() {
    }

    public static NetRequest Instance() {
        if (mNetRequest == null) {
            synchronized (NetRequest.class) {
                mNetRequest = new NetRequest();
            }
        }
        return mNetRequest;
    }

    public void getWelcomePic(final WelcomeActivity context) {
        BmobQuery<Wel> bmobQuery = new BmobQuery<>();
        bmobQuery.addWhereEqualTo("imgDate", OtherUtils.currDay());
        bmobQuery.findObjects(new FindListener<Wel>() {
            @Override
            public void done(List<Wel> list, BmobException e) {
                if (list == null) {
                    return;
                }
                LogUtil.e3("list.size() = " + list.size());
                for (int i = 0; i < list.size(); i++) {
                    LogUtil.e3(list.get(i).toString());
                    Intent intent = new Intent(context, DownLoadWelPicService.class);
                    intent.putExtra("imgPath", list.get(i).getImgAddress());
                    context.startService(intent);
                    break;
                }
            }
        });
    }

    /**
     * 注册
     *
     * @param user
     */
    public void userRegister(User user, final RequestListener<User> listener) {
        user.signUp(new SaveListener<User>() {
            @Override
            public void done(User user, BmobException e) {
                if (e == null) {
                    listener.success(user);
                } else {
                    listener.error(e);
                }
            }
        });
    }

    /**
     * 登录
     *
     * @param username
     * @param password
     * @param listener
     */
    public void login(String username, String password, final RequestListener<User> listener) {
        BmobUser.loginByAccount(username, password, new LogInListener<User>() {
            @Override
            public void done(User user, BmobException e) {
                if (e == null) {
                    listener.success(user);
                } else {
                    listener.error(e);
                }
            }
        });
    }

    /**
     * 退出登录
     */
    public void exitLogin() {
        BmobUser.logOut();   //清除缓存用户对象
    }

    /**
     * 编辑签名
     * @param sign
     * @param listener
     */
    public void editSign(String sign, final RequestListener<String> listener) {
        User user = BmobUser.getCurrentUser(User.class);
        user.setSign(sign);
        user.update(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    listener.success("");
                } else {
                    listener.error(e);
                }
            }
        });
    }

    public void uploadHeadPic(File file) {
        final BmobFile bmobFile = new BmobFile(file);
        final User newUser = new User();
        bmobFile.uploadblock(new UploadFileListener() {

            @Override
            public void done(BmobException e) {
                if(e==null){
                    //bmobFile.getFileUrl()--返回的上传文件的完整地址
                    LogUtil.e3("上传文件成功:" + bmobFile.getFileUrl());
                    newUser.setHeadPic(bmobFile.getFileUrl());
                    BmobUser bmobUser = BmobUser.getCurrentUser();
                    newUser.update(bmobUser.getObjectId(),new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if(e==null){
                                LogUtil.e3("更新用户信息成功");
                            }else{
                                LogUtil.e3("更新用户信息失败:" + e.getMessage());
                            }
                        }
                    });
                }else{
                    LogUtil.e3("上传文件失败：" + e.getMessage());
                }

            }

            @Override
            public void onProgress(Integer value) {
                // 返回的上传进度（百分比）
            }
        });
    }


    public interface RequestListener<T> {
        void success(T t);

        void error(BmobException err);
    }
}
