package io.xujiaji.hnbc.model.net;

import android.app.Activity;
import android.content.Intent;

import com.facebook.login.LoginManager;

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.LogInListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;
import io.xujiaji.hnbc.R;
import io.xujiaji.hnbc.activities.WelcomeActivity;
import io.xujiaji.hnbc.app.App;
import io.xujiaji.hnbc.config.C;
import io.xujiaji.hnbc.factory.ErrMsgFactory;
import io.xujiaji.hnbc.model.entity.BannerData;
import io.xujiaji.hnbc.model.entity.Comment;
import io.xujiaji.hnbc.model.entity.Post;
import io.xujiaji.hnbc.model.entity.Reply;
import io.xujiaji.hnbc.model.entity.User;
import io.xujiaji.hnbc.model.entity.Wel;
import io.xujiaji.hnbc.service.DownLoadWelPicService;
import io.xujiaji.hnbc.utils.LogUtil;
import io.xujiaji.hnbc.utils.MD5Util;
import io.xujiaji.hnbc.utils.NetCheck;
import io.xujiaji.hnbc.utils.OtherUtils;
import io.xujiaji.hnbc.utils.compressor.Compressor;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

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
     * 添加评论
     */
    public void addComment(Post post, String content, final RequestListener<String> listener) {
        User user = BmobUser.getCurrentUser(User.class);
        final Comment comment = new Comment();
        comment.setContent(content);
        comment.setPost(post);
        comment.setUser(user);
        comment.save(new SaveListener<String>() {

            @Override
            public void done(String objectId,BmobException e) {
                if(e==null){
                    listener.success(App.getAppContext().getString(R.string.add_comment_success));
                }else{
                    listener.error(ErrMsgFactory.errMSG(e.getErrorCode()));
                }
            }

        });
    }

    /**
     * 回复评论
     */
    public void replyComment(User replyUser, Comment comment, String content, final RequestListener listener) {
        Reply reply = new Reply();
        reply.setSpeakUser(BmobUser.getCurrentUser(User.class));
        reply.setComment(comment);
        reply.setContent(content);
        reply.setReplyUser(replyUser);
        reply.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if(e==null){
                    listener.success(null);
                }else{
                    listener.error(ErrMsgFactory.errMSG(e.getErrorCode()));
                }
            }
        });
    }

    /**
     * 获取所有回复
     * @param comments
     * @param listener
     */
    public void getCommentReply(final List<Comment> comments, final RequestListener<List<Reply>> listener) {
        final List<Reply> replyList = new ArrayList<>();
        final int numTotal = comments.size();
        nowNum = 0;
        Observable.from(comments)
                .flatMap(new Func1<Comment, Observable<List<Reply>>>() {
                    @Override
                    public Observable<List<Reply>> call(Comment comment) {
                        BmobQuery<Reply> query = new BmobQuery<Reply>();
                        query.addWhereEqualTo("comment", new BmobPointer(comment));
                        query.include("speakUser,replyUser");
                        return query.findObjectsObservable(Reply.class);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Reply>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable throwable) {

                    }

                    @Override
                    public void onNext(List<Reply> replies) {
                        replyList.addAll(replies);
                        nowNum ++;
                        if (Integer.compare(numTotal, nowNum) == 0) {
                            listener.success(replyList);
                        }
                    }
                });
    }
    private int nowNum = 0;

    /**
     * 获取文章的评论
     */
    public void getPostComment(String postId, final RequestListener<List<Comment>> listener) {
        BmobQuery<Comment> query = new BmobQuery<>();
        Post post = new Post();
        post.setObjectId(postId);
        query.addWhereEqualTo("post", new BmobPointer(post));
        query.include("user");
        query.order("-createdAt");
        query.findObjects(new FindListener<Comment>() {
            @Override
            public void done(List<Comment> list, BmobException e) {
                if (e == null) {
//                    for (final Comment comment : list) {
//                        map.put(comment, null);
//                        BmobQuery<Reply> query = new BmobQuery<>();
//                        query.addWhereEqualTo("comment", new BmobPointer(comment));
//                        query.include("speakUser,replyUser");
//                        query.findObjects(new FindListener<Reply>() {
//                            @Override
//                            public void done(List<Reply> list, BmobException e) {
//                                if (e == null) {
//                                    map.put(comment, list);
//                                }
//                            }
//                        });
//                    }
                    listener.success(list);
                }
            }
        });
    }

    /**
     * 获取广告信息
     *
     * @param listener
     */
    public void pullBannerData(final RequestListener<List<BannerData>> listener) {
        if (!checkNet(listener)) return;
        BmobQuery<BannerData> query = new BmobQuery<>();
        query.findObjects(new FindListener<BannerData>() {
            @Override
            public void done(List<BannerData> list, BmobException e) {
                if (e == null) {
                    listener.success(list);
                } else {
                    listener.error(ErrMsgFactory.errMSG(e.getErrorCode()));
                }
            }
        });

    }

    /**
     * 获取帖子数据集合
     *
     * @param size 获取的数据条数
     * @return
     */
    public void pullPostList(int currentIndex, int size, final RequestListener<List<Post>> listener) {
        if (!checkNet(listener)) return;
        BmobQuery<Post> query = new BmobQuery<>();
        query.setSkip(currentIndex);
        query.setLimit(size);
        query.order("-createdAt");
        query.include("author");
        //先判断是否有缓存
//        boolean isCache = query.hasCachedResult(Post.class);
//        if(isCache){
//            query.setCachePolicy(BmobQuery.CachePolicy.CACHE_ELSE_NETWORK);	// 先从缓存取数据，如果没有的话，再从网络取。
//        }else{
//            query.setCachePolicy(BmobQuery.CachePolicy.NETWORK_ELSE_CACHE);	// 如果没有缓存的话，则先从网络中取
//        }

        query.findObjectsObservable(Post.class)
//                .map(new Func1<List<Post>, List<Post>>() {
//                    @Override
//                    public List<Post> call(List<Post> posts) {
//                        for (final Post post : posts) {
//                            String objectId = post.getAuthor().getObjectId();
//                            BmobQuery<User> query = new BmobQuery<>();
//                            query.addWhereEqualTo("objectId", objectId);
//                            query.findObjects(new FindListener<User>() {
//                                @Override
//                                public void done(List<User> object, BmobException e) {
//                                    if (e == null) {
//                                        if (object.size() > 0)
//                                            post.setAuthor(object.get(0));
//                                    }
//                                }
//                            });
//                        }
//                        return posts;
//                    }
//                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Post>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable throwable) {
                        LogUtil.e2(throwable.toString());
                        listener.error(throwable.toString());
                    }

                    @Override
                    public void onNext(List<Post> posts) {
                        listener.success(posts);
                    }
                });
    }

    /**
     * 上传发表帖子
     *
     * @param coverPicture
     * @param title
     * @param article
     */
    public void uploadArticle(String coverPicture, String title, String article, final RequestListener<String> listener) {
        if (!checkNet(listener)) return;
        User user = BmobUser.getCurrentUser(User.class);
        if (user == null) {
            listener.error(App.getAppContext().getString(R.string.please_login));
            return;
        }
        Post post = new Post();
        post.setContent(article);
        post.setTitle(title);
        post.setCoverPicture(coverPicture);
        post.setAuthor(user);
        post.save(new SaveListener<String>() {

            @Override
            public void done(String s, BmobException e) {
                if (e == null) {
                    listener.success(s);
                } else {
                    listener.error(ErrMsgFactory.errMSG(e.getErrorCode()));
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
        if (!checkNet(listener)) return;
        user.signUp(new SaveListener<User>() {
            @Override
            public void done(User user, BmobException e) {
                if (e == null) {
                    listener.success(user);
                } else {
                    listener.error(ErrMsgFactory.errMSG(e.getErrorCode()));
                }
            }
        });
    }

    public void facebookLogin(final String username, final String password, final RequestListener<User> listener) {
        if (!checkNet(listener)) return;
        User user = new User();
        user.setUsername(username);
        user.setPassword(MD5Util.getMD5(password));
        user.signUp(new SaveListener<User>() {
            @Override
            public void done(User user, BmobException e) {
                if (e == null) {
                    login(username, password, listener);
                } else {
                    if (Integer.compare(202, e.getErrorCode()) == 0) {
                        login(username, password, listener);
                        return;
                    }
                    listener.error(ErrMsgFactory.errMSG(e.getErrorCode()));
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
        if (!checkNet(listener)) return;
        BmobUser.loginByAccount(username, MD5Util.getMD5(password), new LogInListener<User>() {
            @Override
            public void done(User user, BmobException e) {
                if (e == null) {
                    listener.success(user);
                } else {
                    listener.error(ErrMsgFactory.errMSG(e.getErrorCode()));
                }
            }
        });
    }

    /**
     * 第三方登陆或注册
     */
    public void thirdLogin(Activity context, final String loginType, final ThirdLoginLister<String> listener) {
        if (!checkNet(listener)) return;
        LoginSDKHelper
                .getIntance()
                .login(context, loginType)
                .addListener(new LoginSDKHelper.ThirdLoginListener() {
                    @Override
                    public void loginSuccess(Map<String, String> data) {
                        if (listener != null) {
                            listener.thirdLoginSuccess();
                        }
                    }

                    @Override
                    public void getUserInfoSuccess(Map<String, String> data) {

                        BmobUser.BmobThirdUserAuth authInfo =
                                new BmobUser.BmobThirdUserAuth(loginType,
                                        data.get(C.login.data.PARAM_ACCESS_TOKEN),
                                        data.get(C.login.data.PARAM_EXPIRES_IN),
                                        MD5Util.getMD5(data.get(C.login.data.PARAM_OPEN_ID)));

                        BmobUser.loginWithAuthData(authInfo, new LogInListener<JSONObject>() {
                            @Override
                            public void done(JSONObject userAuth, BmobException e) {
                                listener.success(userAuth.toString());
                            }
                        });
                    }

                    @Override
                    public void fail(String failMsg) {
                        if (listener != null) {
                            listener.error(failMsg);
                        }
                    }
                });
    }

    /**
     * 退出登录
     */
    public void exitLogin() {
        BmobUser.logOut();   //清除缓存用户对象
        LoginManager.getInstance().logOut();
    }

    /**
     * 编辑签名
     *
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
                    listener.error(ErrMsgFactory.errMSG(e.getErrorCode()));
                }
            }
        });
    }

    /**
     * 上传图片
     *
     * @param file
     * @param listener
     */
    public void uploadPic(File file, final UploadPicListener<String> listener) {
        if (!checkNet(listener)) return;
        listener.compressingPic();
        Compressor.getDefault(App.getAppContext())
                .compressToFileAsObservable(file)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<File>() {
                    @Override
                    public void call(File file) {
                        listener.compressedPicSuccess();
                        final BmobFile bmobFile = new BmobFile(file);
                        bmobFile.uploadblock(new UploadFileListener() {
                            @Override
                            public void done(BmobException e) {
                                if (e == null) {
                                    listener.success(bmobFile.getFileUrl());
                                } else {
                                    listener.error(ErrMsgFactory.errMSG(e.getErrorCode()));
                                }
                            }

                            @Override
                            public void onProgress(Integer value) {
                                super.onProgress(value);
                                listener.progress(value);
                            }
                        });
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        listener.error(throwable.toString());
                    }
                });
    }

    /**
     * 上传头像
     *
     * @param file
     * @param listener
     */
    public void uploadHeadPic(File file, final RequestListener<String> listener) {
        if (!checkNet(listener)) return;
        final BmobFile bmobFile = new BmobFile(file);
        final User newUser = new User();
        bmobFile.uploadblock(new UploadFileListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    //bmobFile.getFileUrl()--返回的上传文件的完整地址
                    LogUtil.e3("上传文件成功:" + bmobFile.getFileUrl());
                    newUser.setHeadPic(bmobFile.getFileUrl());
                    BmobUser bmobUser = BmobUser.getCurrentUser();
                    newUser.update(bmobUser.getObjectId(), new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e == null) {
                                listener.success(newUser.getHeadPic());
                                LogUtil.e3("更新用户信息成功");
                            } else {
                                listener.error(ErrMsgFactory.errMSG(e.getErrorCode()));
                                LogUtil.e3("更新用户信息失败:" + e.getMessage());
                            }
                        }
                    });
                } else {
                    listener.error(ErrMsgFactory.errMSG(e.getErrorCode()));
                    LogUtil.e3("上传头像失败：" + e.getMessage());
                }

            }

            @Override
            public void onProgress(Integer value) {
                // 返回的上传进度（百分比）
            }
        });
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
                newUser.setBirthday((Date) info);
                break;
            case SEX:
                newUser.setSex((Integer) info);
                break;
            case PHONE:
                newUser.setMobilePhoneNumber((String) info);
                break;
            case CITY:
                newUser.setCity((String) info);
                break;
            case EMAIL:
                newUser.setEmail((String) info);
                break;
            case SIGN:
                newUser.setSign((String) info);
                break;
            default:
                throw new RuntimeException("UpdateType don't have " + type);
        }
        BmobUser bmobUser = BmobUser.getCurrentUser();
        newUser.update(bmobUser.getObjectId(), new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    listener.success(newUser);
                    LogUtil.e3("更新用户信息成功");
                } else {
                    listener.error(ErrMsgFactory.errMSG(e.getErrorCode()));
                    LogUtil.e3("更新用户信息失败:" + e.getMessage());
                }
            }
        });
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
        BmobUser.updateCurrentUserPassword(MD5Util.getMD5(oldPwd), MD5Util.getMD5(newPwd), new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    listener.success(null);
                } else {
                    listener.error(ErrMsgFactory.errMSG(e.getErrorCode()));
                }
            }
        });
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

    private boolean checkNet(RequestListener listener) {
        if (NetCheck.isConnected(App.getAppContext())) {
            return true;
        } else {
            listener.error(App.getAppContext().getString(R.string.please_check_network_status));
            return false;
        }
    }
}
