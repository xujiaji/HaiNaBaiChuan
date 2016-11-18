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

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import io.xujiaji.hnbc.R;
import io.xujiaji.hnbc.model.data.DataFiller;
import io.xujiaji.hnbc.model.entity.EditItem;
import io.xujiaji.hnbc.utils.ImgLoadUtil;

/**
 * Created by jiana on 16-11-7.
 */

public class EditUserInfoAdapter extends BaseQuickAdapter<EditItem, BaseViewHolder> {
    public EditUserInfoAdapter() {
        super(R.layout.item_edit_user_info, DataFiller.getEditList());
    }

    public void update() {
        List<EditItem> itemList = getData();
        itemList.clear();
        List<EditItem> newList = DataFiller.getEditList();
        if (newList == null) {
            return;
        }
        itemList.addAll(newList);
        notifyDataSetChanged();
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, EditItem editItem) {
        ImageView imgHead = baseViewHolder.getView(R.id.imgHead);
        TextView tvTypeName = baseViewHolder.getView(R.id.tvTypeName);
        tvTypeName.getPaint().setFakeBoldText(true);
        baseViewHolder.setText(R.id.tvTypeName, editItem.getTypeName())
                .setText(R.id.tvValue, editItem.getValue());
        if (baseViewHolder.getAdapterPosition() == 0) {
            imgHead.setVisibility(View.VISIBLE);
            ImgLoadUtil.loadHead(mContext, imgHead, editItem.getHeadPic());
        } else {
            imgHead.setVisibility(View.GONE);
        }
    }
}
