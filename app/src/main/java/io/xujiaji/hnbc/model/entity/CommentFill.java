package io.xujiaji.hnbc.model.entity;

import com.chad.library.adapter.base.entity.AbstractExpandableItem;
import com.chad.library.adapter.base.entity.MultiItemEntity;

import io.xujiaji.hnbc.adapters.ExpandableCommentItemAdapter;

/**
 * Created by jiana on 16-11-17.
 */

public class CommentFill extends AbstractExpandableItem<ReplyFill> implements MultiItemEntity {
    private Comment comment;

    public CommentFill(Comment comment) {
        this.comment = comment;
    }

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }

    @Override
    public int getLevel() {
        return 0;
    }

    @Override
    public int getItemType() {
        return ExpandableCommentItemAdapter.TYPE_LEVEL_0;
    }
}
