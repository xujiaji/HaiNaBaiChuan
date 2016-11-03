package io.xujiaji.hnbc.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.io.File;

import io.xujiaji.hnbc.R;
import io.xujiaji.hnbc.model.entity.Write;

/**
 * 图片加载帮助类
 */
public class ImageLoadHelper {
    private ImageLoadHelper() {
    }

    public static void loadNoCache(Context context, ImageView img, File file) {
        Glide.with(context)
                .load(file)
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(img);
    }

    public static void loadWriteImg(Context context, final ImageView img, final Write.Content content) {
        Glide.with(context)
                .load(content.getImg())
                .error(R.mipmap.fengjing)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(img);

    }
}
