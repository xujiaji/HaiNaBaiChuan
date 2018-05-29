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


/**
 * Created by jiana on 16-11-13.
 *
 * 帖子评论类
 */

public class Comment extends Base{
    private String content;//评论内容

    private User user;//评论的用户，Pointer类型，一对一关系

    private Post post; //所评论的帖子，这里体现的是一对多的关系，一个评论只能属于一个微博

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }
}
