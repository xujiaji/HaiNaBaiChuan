package io.xujiaji.hnbc.adapters;

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
