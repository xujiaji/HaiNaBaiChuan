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
package io.xujiaji.hnbc.adapters;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.List;

import io.xujiaji.hnbc.R;
import io.xujiaji.hnbc.model.entity.Comment;
import io.xujiaji.hnbc.model.entity.CommentFill;
import io.xujiaji.hnbc.model.entity.Reply;
import io.xujiaji.hnbc.model.entity.ReplyFill;
import io.xujiaji.hnbc.utils.ImgLoadUtil;

public class ExpandableCommentItemAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder> {

    public static final int TYPE_LEVEL_0 = 0;
    public static final int TYPE_LEVEL_1 = 1;

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public ExpandableCommentItemAdapter(List<MultiItemEntity> data) {
        super(data);
        addItemType(TYPE_LEVEL_0, R.layout.item_comment);
        addItemType(TYPE_LEVEL_1, R.layout.item_comment_reply);
    }

    @Override
    protected void convert(final BaseViewHolder holder, final MultiItemEntity item) {
        switch (holder.getItemViewType()) {
            case TYPE_LEVEL_0:
                final CommentFill commentFill = (CommentFill) item;
                final Comment comment = commentFill.getComment();
                holder.setText(R.id.tvNickname, comment.getUser().getNickname())
                        .setText(R.id.tvDate, comment.getCreatedAt())
                        .setText(R.id.tvComment, comment.getContent());
                holder.addOnClickListener(R.id.btnReply)
                        .addOnClickListener(R.id.imgHead);
                ImageView head = holder.getView(R.id.imgHead);
                ImgLoadUtil.loadHead(mContext, head, comment.getUser().getHeadPic());
                expandAll(holder.getAdapterPosition(), true);
                break;
            case TYPE_LEVEL_1:
                final Reply reply = ((ReplyFill) item).getReply();
                holder.setText(R.id.tvReplyUser, reply.getSpeakUser().getNickname() + " ")
                        .setText(R.id.tvReplyWho, reply.getReplyUser().getNickname())
                        .setText(R.id.tvReply, reply.getContent());
                holder.addOnClickListener(R.id.tvReplyUser)
                        .addOnClickListener(R.id.tvReplyWho);
                break;
        }
    }
}
