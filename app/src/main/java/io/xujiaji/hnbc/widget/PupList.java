package io.xujiaji.hnbc.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.xujiaji.hnbc.R;
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
        }
    }
}
