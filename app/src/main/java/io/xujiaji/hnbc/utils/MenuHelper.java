package io.xujiaji.hnbc.utils;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;
import java.util.Random;

import io.xujiaji.hnbc.R;
import no.agens.depth.lib.CircularSplashView;

/**
 * Created by jiana on 16-11-9.
 */

public class MenuHelper {
    private LinearLayout menu;
    private MenuItemClickListener listener;

    public void setListener(MenuItemClickListener listener) {
        this.listener = listener;
    }

    public interface MenuItemClickListener {
        void itemClick(int position);
    }
    public void createMenu(LinearLayout group, List<String> menuList) {
        menu = group;
        for (String item : menuList) {
            addMenuItem(menu, item, menuList.indexOf(item));
        }
        selectMenuItem(0, group.getContext().getResources().getColor(android.R.color.holo_red_light));
        menu.setTranslationY(20000);
    }

    public void selectMenuItem(int menuIndex, int color) {
        for (int i = 0; i < menu.getChildCount(); i++) {
            View menuItem = menu.getChildAt(i);
            if (i == menuIndex)
                select(menuItem, color);
            else
                unSelect(menuItem);
        }
    }

    private void addMenuItem(ViewGroup menu, String text, int menuIndex) {
        int[] colors = {android.R.color.holo_red_light,
                android.R.color.holo_green_light,
                android.R.color.darker_gray,
                android.R.color.holo_orange_light,
                android.R.color.holo_blue_light};
        Context context = menu.getContext();
        ViewGroup item = (ViewGroup) LayoutInflater.from(context).inflate(R.layout.menu_item, menu, false);
        ((TextView) item.findViewById(R.id.item_text)).setText(text);
        CircularSplashView ic = (CircularSplashView) item.findViewById(R.id.circle);
        ic.setSplash(getBitmap(context));
        ic.setSplashColor(context.getResources().getColor(colors[new Random().nextInt(colors.length)]));
        item.setOnClickListener(getMenuItemCLick(menuIndex, context.getResources().getColor(colors[new Random().nextInt(colors.length)])));
        int padding = (int) context.getResources().getDimension(R.dimen.menu_item_height_padding);
        menu.addView(item, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) context.getResources().getDimension(R.dimen.menu_item_height) + padding));
        item.setPadding(0, 0, 0, padding);
        item.setBackground(context.getResources().getDrawable(R.drawable.menu_btn, null));
    }

    private View.OnClickListener getMenuItemCLick(final int menuIndex, final int color) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectMenuItem(menuIndex, color);
                if (listener != null) {
                    listener.itemClick(menuIndex);
                }
            }
        };
    }

    /**
     * 选择
     *
     * @param menuItem
     * @param color
     */
    private void select(View menuItem, int color) {
        final CircularSplashView circle = (CircularSplashView) menuItem.findViewById(R.id.circle);
        circle.setScaleX(1f);
        circle.setScaleY(1f);
        circle.setVisibility(View.VISIBLE);
        circle.introAnimate();
        fadeColoTo(color, (TextView) menuItem.findViewById(R.id.item_text));
    }


    private void unSelect(View menuItem) {
        final View circle = menuItem.findViewById(R.id.circle);
        circle.animate().scaleX(0).scaleY(0).setDuration(150).withEndAction(new Runnable() {
            @Override
            public void run() {
                circle.setVisibility(View.INVISIBLE);
            }
        }).start();
        fadeColoTo(Color.BLACK, (TextView) menuItem.findViewById(R.id.item_text));
    }

    private void fadeColoTo(int newColor, TextView view) {
        ObjectAnimator color = ObjectAnimator.ofObject(view, "TextColor", new ArgbEvaluator(), view.getCurrentTextColor(), newColor);
        color.setDuration(200);
        color.start();
    }


    private Bitmap getBitmap(Context context) {
//        Drawable drawable = getResources().getDrawable(drawableRes);
        Bitmap bitmap = Bitmap.createBitmap(50, 50, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
//        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
//        drawable.draw(canvas);
        Paint paint = new Paint();
        paint.setColor(context.getResources().getColor(android.R.color.holo_red_light));
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawArc(0, 0, 50, 50, 0, 360, true, paint);
        return bitmap;
    }
}
