/*
 * Copyright 2016 XuJiaji
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.xujiaji.hnbc.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.listener.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.xujiaji.hnbc.R;
import io.xujiaji.hnbc.config.C;
import io.xujiaji.hnbc.model.entity.PopupItem;
import io.xujiaji.hnbc.utils.LogUtil;
import io.xujiaji.hnbc.utils.ScreenUtils;

/**
 * Created by jiana on 16-11-6.
 */

public class PupList extends PopupWindow {
    private int[] location = null;
    private ViewHolder viewHolder;
    private int windLeft, windRight;

    public PupList(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.popupwindow_list, null);
        viewHolder = new ViewHolder(view);
        this.setContentView(view);
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        this.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        // 刷新状态
        this.update();
        this.setAnimationStyle(R.style.popupShowAnExit);
        this.setBackgroundDrawable(new BitmapDrawable(context.getResources(), (Bitmap) null));
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int left = viewHolder.rv.getLeft();
                final int top = viewHolder.rv.getTop();
                final int right = viewHolder.rv.getRight();
                final int bottom = viewHolder.rv.getBottom();
                LogUtil.e3("left = " + left + "; top = " + top + "; right = " + right + "; bottom = " + bottom);
                final int y = (int) event.getY();
                final int x = (int) event.getX();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (x < left || x > right || y < top || y > top) {
                        PupList.this.dismiss();
                    }
                }
                return true;
            }
        });

    }

    public void show(View view) {
        if (this.isShowing()) {
            return;
        }
        if (location == null) {
            location = new int[2];
            view.getLocationOnScreen(location);
            LogUtil.e2("location[0] = " + location[0]);
            LogUtil.e2("location[1] = " + location[1]);
            windLeft = ScreenUtils.getScreenWidth(getContentView().getContext()) - view.getWidth() / 2;
            windRight = location[1] + this.getHeight() + view.getHeight();
        }
        this.showAtLocation(view,
                Gravity.NO_GRAVITY,
                windLeft,
                windRight);
    }

    static class ViewHolder {
        @BindView(R.id.rv)
        RecyclerView rv;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
            rv.setLayoutManager(new LinearLayoutManager(view.getContext()));
            final List<PopupItem> data = new ArrayList<>();
            data.add(new PopupItem("编辑", C.pupmenu.EDIT, R.drawable.ic_border_color_black_24dp));
            data.add(new PopupItem("退出登录", C.pupmenu.EXIT_LOGIN, R.drawable.ic_keyboard_tab_black_24dp));
            rv.setAdapter(new PupAdapter(data));
            rv.addOnItemTouchListener(new OnItemClickListener() {
                @Override
                public void SimpleOnItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                    if (listener != null) {
                        listener.itemClick(data.get(i).getId());
                    }
                }
            });
        }

        private PupListener listener;

        public void setListener(PupListener listener) {
            this.listener = listener;
        }
    }

    private static class PupAdapter extends BaseQuickAdapter<PopupItem, BaseViewHolder>{

        public PupAdapter(List<PopupItem> data) {
            super(R.layout.item_popup, data);
        }

        @Override
        protected void convert(BaseViewHolder baseViewHolder, PopupItem popupItem) {
//            MaterialRippleHelper.ripple(baseViewHolder.getConvertView());
            baseViewHolder.setImageResource(R.id.imgMark, popupItem.getImgRes());
            baseViewHolder.setText(R.id.tvItem, popupItem.getName());
        }
    }


    public void setListener(PupListener listener) {
        viewHolder.setListener(listener);
    }

    public interface PupListener {
        void itemClick(int itemId);
    }
}
