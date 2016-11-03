package io.xujiaji.developersbackpack.adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import io.xujiaji.developersbackpack.R;

public class MainPagerAdapter extends PagerAdapter {
    private List<ImageView> list;
    public MainPagerAdapter(Context con) {
        list = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            ImageView img = new ImageView(con);
            img.setImageResource(R.mipmap.fengjing);
            img.setScaleType(ImageView.ScaleType.CENTER_CROP);
            img.setLayoutParams(new ViewPager.LayoutParams());
            list.add(img);
        }
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(list.get(position));
        return list.get(position);
    }

}
