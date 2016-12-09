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

import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

import io.xujiaji.hnbc.R;
import io.xujiaji.hnbc.model.entity.MainTag;

/**
 * Created by jiana on 16-7-24.
 */
public class MainRecyclerAdapter extends BaseQuickAdapter<MainTag, BaseViewHolder> {
    private List<Integer> widthList;
    public MainRecyclerAdapter(List<MainTag> mainTags) {
        super(R.layout.item_main_tag, mainTags);
        widthList = new ArrayList<>();
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, MainTag mainTag) {
        TextView textView = baseViewHolder.getView(R.id.tagContent);
        textView.setText(mainTag.getName());
        ViewGroup.LayoutParams params = textView.getLayoutParams();
        int position = baseViewHolder.getAdapterPosition();
        if (widthList.size() <= position || widthList.get(position) == null) {
            widthList.add(params.width);
        } else {
            params.width = widthList.get(position);
            textView.setLayoutParams(params);
        }
    }
}
