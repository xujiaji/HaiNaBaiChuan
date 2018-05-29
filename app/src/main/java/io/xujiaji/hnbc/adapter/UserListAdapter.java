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

package io.xujiaji.hnbc.adapter;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import io.xujiaji.hnbc.R;
import io.xujiaji.hnbc.model.entity.User;
import io.xujiaji.hnbc.utils.ImgLoadUtil;

/**
 * Created by jiana on 16-11-24.
 */

public class UserListAdapter extends BaseQuickAdapter<User, BaseViewHolder> {
    public UserListAdapter(List<User> data) {
        super(R.layout.item_user, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, User user) {
        baseViewHolder.setText(R.id.tvUserName, "@" + user.getNickname())
                .setText(R.id.tvTopContent, user.getSign());
        ImageView headPic = baseViewHolder.getView(R.id.imgHead);
        ImgLoadUtil.loadHead(mContext, headPic, user.getHeadPic());
    }
}
