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
public class ImgLoadUtil {
    private ImgLoadUtil() {
    }

    public static void loadBitmap(Context context, ImageView img, String url) {
        Glide.with(context)
                .load(url)
                .asBitmap()
                .placeholder(R.mipmap.fengjing)
                .error(R.mipmap.fengjing)
                .into(img);

    }

    public static void load(Context context, ImageView img, String url) {
        Glide.with(context)
                .load(url)
                .into(img);
    }

    public static void loadHead(Context context, ImageView img, String url) {
        Glide.with(context)
                .load(url)
                .crossFade()
                .placeholder(R.drawable.head)
                .error(R.drawable.head)
                .transform(new GlideCircleTransform(context))
                .into(img);
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
