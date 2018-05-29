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

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import io.xujiaji.hnbc.R;
import io.xujiaji.hnbc.model.entity.Set;

/**
 * Created by jiana on 01/12/16.
 */

public class SetAdapter extends BaseQuickAdapter<Set, BaseViewHolder> {

    public SetAdapter(List<Set> data) {
        super(R.layout.item_set, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, Set set) {
        baseViewHolder.setText(R.id.tvTypeName, set.getTypeName())
                .setText(R.id.tvValue, set.getValue());
    }
}
