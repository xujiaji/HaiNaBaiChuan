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

package io.xujiaji.hnbc.model.entity;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import io.xujiaji.hnbc.adapters.ExpandableCommentItemAdapter;

/**
 * Created by jiana on 16-11-17.
 */

public class ReplyFill implements MultiItemEntity {
    private Reply reply;
    public ReplyFill(Reply reply) {
        this.reply = reply;
    }


    public Reply getReply() {
        return reply;
    }

    public void setReply(Reply reply) {
        this.reply = reply;
    }

    @Override
    public int getItemType() {
        return ExpandableCommentItemAdapter.TYPE_LEVEL_1;
    }
}
