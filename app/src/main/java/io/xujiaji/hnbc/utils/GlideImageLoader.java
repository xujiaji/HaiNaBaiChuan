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
import com.youth.banner.loader.ImageLoader;

import io.xujiaji.hnbc.R;

public class GlideImageLoader extends ImageLoader {
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        /**
          常用的图片加载库：
          Universal Image Loader：一个强大的图片加载库，包含各种各样的配置，最老牌，使用也最广泛。      
          Picasso: Square出品，必属精品。和OkHttp搭配起来更配呦！          
          Volley ImageLoader：Google官方出品，可惜不能加载本地图片~          
          Fresco：Facebook出的，天生骄傲！不是一般的强大。         
          Glide：Google推荐的图片加载库，专注于流畅的滚动。
         */

        //Glide 加载图片简单用法
        Glide.with(context)
                .load(path)
                .placeholder(R.mipmap.fengjing)
                .error(R.mipmap.fengjing)
                .crossFade()
                .into(imageView);
    }
//    //提供createImageView 方法，如果不用可以不重写这个方法，方便fresco自定义ImageView
//    @Override
//    public ImageView createImageView(Context context) {
//        SimpleDraweeView simpleDraweeView=new SimpleDraweeView(context);
//        return simpleDraweeView;
//    }
}