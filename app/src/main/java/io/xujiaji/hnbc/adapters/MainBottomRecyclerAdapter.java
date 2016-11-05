package io.xujiaji.hnbc.adapters;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import io.xujiaji.hnbc.R;
import io.xujiaji.hnbc.model.entity.MainPersonMsg;
import io.xujiaji.hnbc.utils.ImgLoadUtil;

public class MainBottomRecyclerAdapter extends BaseQuickAdapter<MainPersonMsg, BaseViewHolder> {
    public MainBottomRecyclerAdapter(List<MainPersonMsg> data) {
        super(R.layout.item_main_card, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, MainPersonMsg mainPersonMsg) {
        baseViewHolder.setText(R.id.tvUserName, "@" + mainPersonMsg.getName())
                .setText(R.id.tvContent, mainPersonMsg.getMsgContent())
                .setText(R.id.tvUserSign, mainPersonMsg.getSign());

        ImgLoadUtil.loadHead(mContext, (ImageView) baseViewHolder.getView(R.id.imgHead), mainPersonMsg.getHeadPic());
        ImgLoadUtil.load(mContext, (ImageView) baseViewHolder.getView(R.id.imageThumb), mainPersonMsg.getBigPic());
    }
}
