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

import java.io.File;
import java.util.List;

import io.xujiaji.hnbc.R;
import io.xujiaji.hnbc.activity.WelcomeActivity;
import io.xujiaji.hnbc.app.App;
import io.xujiaji.hnbc.model.entity.BannerData;
import io.xujiaji.hnbc.model.entity.Comment;
import io.xujiaji.hnbc.model.entity.Post;
import io.xujiaji.hnbc.model.entity.Reply;
import io.xujiaji.hnbc.model.entity.User;
import io.xujiaji.hnbc.utils.NetCheck;
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

    }

    /**
     * 请求获取用户粉丝数量
     */
    public void requestFansNum(User user, final RequestListener<String> listener) {

    }


    /**
     * 请求获取用户粉丝
     */
    public void requestFans(User user, final RequestListener<List<User>> listener) {

    }

    /**
     * 请求获取用户关注其他用户数量
     */
    public void requestFocusNum(User user, final RequestListener<String> listener) {

    }

    /**
     * 请求获取用户关注其他用户
     */
    public void requestFocus(User user, final RequestListener<List<User>> listener) {

    }

    /**
     * 请求获取用户喜欢的文章数量
     */
    public void requestCollectNum(User user, final RequestListener<String> listener) {

    }

    /**
     * 跟随该用户
     *
     * @param user
     * @param listener
     */
    public void followUser(final User user, final RequestListener<String> listener) {


    }

    private void startFollowRequest(User user, final RequestListener<String> listener) {

    }

    /**
     * 喜欢文章
     *
     * @param post
     * @param listener
     */
    public void likeComment(final Post post, final RequestListener<String> listener) {

    }

    private void startLikeRequest(Post post, final RequestListener<String> listener) {

    }

    /**
     * 添加评论
     */
    public void addComment(Post post, String content, final RequestListener<String> listener) {

    }

    /**
     * 回复评论
     */
    public void replyComment(User replyUser, Comment comment, String content, final RequestListener listener) {

    }

    /**
     * 获取所有回复
     *
     * @param comments
     * @param listener
     */
    public void getCommentReply(final List<Comment> comments, final RequestListener<List<Reply>> listener) {

    }

    private int nowNum = 0;

    /**
     * 获取文章的评论
     */
    public void getPostComment(String postId, final RequestListener<List<Comment>> listener) {

    }

    /**
     * 获取广告信息
     *
     * @param listener
     */
    public void pullBannerData(final RequestListener<List<BannerData>> listener) {
    }

    /**
     * 获取该用户发布的帖子
     * @param currentIndex
     * @param size
     * @param listener
     */
    public void pullReleasePost(int currentIndex, int size, final RequestListener<List<Post>> listener) {

    }

    /**
     * 获取收藏/喜欢的帖子
     * @param currentIndex
     * @param size
     * @param listener
     */
    public void pullCollectPost(String userId, int currentIndex, int size, final RequestListener<List<Post>> listener) {

    }
    /*
        BmobQuery<Post> query = new BmobQuery<>();
        BmobQuery<User> innerQuery = new BmobQuery<>();
        innerQuery.addWhereEqualTo("objectId", user.getObjectId());
        query.addWhereMatchesQuery("likes", "_User", innerQuery);
        query.count(Post.class, new CountListener() {
            @Override
            public void done(Integer integer, BmobException e) {
                if (e == null) {
                    listener.success(Integer.toString(integer));
                } else {
                    listener.success("0");
                }
            }
        });

     */

    /**
     * 获取帖子数据集合
     *
     * @param size 获取的数据条数
     * @return
     */
    public void pullPostList(int currentIndex, int size, final RequestListener<List<Post>> listener) {

    }

    /**
     * 上传发表帖子
     *
     * @param coverPicture
     * @param title
     * @param article
     */
    public void uploadArticle(String coverPicture, String title, String article, final RequestListener<String> listener) {

    }

    /**
     * 注册
     *
     * @param user
     */
    public void userRegister(User user, final RequestListener<User> listener) {

    }

    public void facebookLogin(final String username, final String password, final RequestListener<User> listener) {

    }


    /**
     * 登录
     *
     * @param username
     * @param password
     * @param listener
     */
    public void login(String username, String password, final RequestListener<User> listener) {

    }

    /**
     * 第三方登陆或注册
     */
    public void thirdLogin(Activity context, final String loginType, final ThirdLoginLister<String> listener) {

    }

    /**
     * 退出登录
     */
    public void exitLogin() {

    }

    /**
     * 编辑签名
     *
     * @param sign
     * @param listener
     */
    public void editSign(String sign, final RequestListener<String> listener) {

    }

    /**
     * 上传图片
     *
     * @param file
     * @param listener
     */
    public void uploadPic(File file, final UploadPicListener<String> listener) {

    }

    /**
     * 上传头像
     *
     * @param file
     * @param listener
     */
    public void uploadHeadPic(File file, final RequestListener<String> listener) {

    }

    /**
     * 更新用户信息
     *
     * @param type
     * @param info
     * @param listener
     * @param <T>
     */
    public <T> void updateInfo(UpdateType type, T info, final RequestListener<User> listener) {
        if (!checkNet(listener)) return;
        final User newUser = new User();
        switch (type) {
            case NICKNAME:
                newUser.setNickname((String) info);
                break;
            case BIRTHDAY:
                newUser.setBirthday((String) info);
                break;
            case SEX:
                newUser.setSex((Integer) info);
                break;
            case PHONE:
//                newUser.setMobilePhoneNumber((String) info);
                break;
            case CITY:
                newUser.setCity((String) info);
                break;
            case EMAIL:
//                newUser.setEmail((String) info);
                break;
            case SIGN:
                newUser.setSign((String) info);
                break;
            default:
                throw new RuntimeException("UpdateType don't have " + type);
        }

    }

    /**
     * 更新密码
     *
     * @param oldPwd
     * @param newPwd
     * @param listener
     */
    public void updatePassword(String oldPwd, String newPwd, final RequestListener<User> listener) {
        if (!checkNet(listener)) return;

    }


    /**
     * 更新类型
     */
    public enum UpdateType {
        NICKNAME,
        BIRTHDAY,
        SEX,
        PHONE,
        CITY,
        EMAIL,
        SIGN
    }

    public interface RequestListener<T> {
        void success(T t);

        void error(String err);
    }

    public interface ProgressListener<T> extends RequestListener<T> {
        void progress(int i);
    }

    public interface UploadPicListener<T> extends ProgressListener<T> {
        void compressingPic();

        void compressedPicSuccess();
    }

    public interface ThirdLoginLister<T> extends RequestListener<T> {
        void thirdLoginSuccess();
    }

    /**
     * 检测是否登陆
     */
    public boolean checkLoginStatus(RequestListener listener) {

        return true;
    }

    /**
     * 检测网络
     * @param listener
     * @return
     */
    private boolean checkNet(RequestListener listener) {
        if (NetCheck.isConnected(App.getAppContext())) {
            return true;
        } else {
            listener.error(App.getAppContext().getString(R.string.please_check_network_status));
            return false;
        }
    }
}
