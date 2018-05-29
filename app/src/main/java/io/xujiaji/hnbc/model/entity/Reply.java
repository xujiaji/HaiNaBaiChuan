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
