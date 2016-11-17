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
