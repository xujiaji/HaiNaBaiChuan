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
package io.xujiaji.hnbc.adapter;

import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;

import io.xujiaji.hnbc.R;
import io.xujiaji.hnbc.model.entity.Post;
import io.xujiaji.hnbc.utils.ImgLoadUtil;

public class MainBottomRecyclerAdapter extends BaseQuickAdapter<Post, BaseViewHolder> {
    public MainBottomRecyclerAdapter() {
        super(R.layout.item_main_card, new ArrayList<Post>());
    }

//    public static List<Post> get() {
//        List<Post> list = new ArrayList<>();
//        for (int i = 0; i < 20; i++) {
//            Post post = new Post();
//            User user = new User();
//            user.setNickname("Test");
//            post.setAuthor(user);
//            list.add(post);
//        }
//        return list;
//    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, Post post) {
        baseViewHolder.setText(R.id.tvUserName, "@" + post.getAuthor().getNickname())
                .addOnClickListener(R.id.layoutBaseArticle)
                .addOnClickListener(R.id.btnLike)
                .addOnClickListener(R.id.btnFollow)
                .addOnClickListener(R.id.imgHead)
                .addOnClickListener(R.id.tvUserName)
                .setText(R.id.tvTopContent, post.getCreatedAt())
                .setText(R.id.tvBottomContent, post.getTitle());

        ImgLoadUtil.loadHead(mContext, (ImageView) baseViewHolder.getView(R.id.imgHead), post.getAuthor().getHeadPic());
        if (post.getCoverPicture() == null) {
            baseViewHolder.getView(R.id.imageThumb).setVisibility(View.GONE);
        } else {
            baseViewHolder.getView(R.id.imageThumb).setVisibility(View.VISIBLE);
            ImgLoadUtil.load(mContext, (ImageView) baseViewHolder.getView(R.id.imageThumb), post.getCoverPicture());
        }
    }
}
