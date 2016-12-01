package io.xujiaji.hnbc.adapters;

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
