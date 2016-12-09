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
