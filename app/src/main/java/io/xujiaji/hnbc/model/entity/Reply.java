package io.xujiaji.hnbc.model.entity;

import cn.bmob.v3.BmobObject;

/**
 * Created by jiana on 16-11-17.
 * 回复类，回复评论
 */

public class Reply extends BmobObject {
    //评论
    private Comment comment;
    //说话的人
    private User speakUser;
    //回复者
    private User replyUser;
    //回复内容
    private String content;

    public User getSpeakUser() {
        return speakUser;
    }

    public void setSpeakUser(User speakUser) {
        this.speakUser = speakUser;
    }

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }

    public User getReplyUser() {
        return replyUser;
    }

    public void setReplyUser(User replyUser) {
        this.replyUser = replyUser;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
