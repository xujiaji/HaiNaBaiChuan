/*
 * Copyright 2016 XuJiaji
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
package io.xujiaji.hnbc.contracts;

import io.xujiaji.xmvp.contracts.XContract;

/**
 * Created by jiana on 16-7-27.
 */
public interface EditorContract {
    interface Presenter extends XContract.Presenter {
        /**
         * 上传图片
         * @param path 图片地址
         */
        void uploadPic(String path);

        /**
         * 上传文章
         * @param coverPicture 封面图
         * @param title 标题
         * @param article 文章
         */
        void uploadArticle(String coverPicture, String title, String article);
    }

    interface View extends XContract.View {

        void showCompressingPicDialog();

        void compressedPicSuccess();

        /**
         * 显示上传文章进度
         */
        void showUploadArticleProgress();

        /**
         * 显示上传进度条
         */
        void showUploadPicProgress();
        /**
         * 上传图片的进度
         * @param progress 当前进度
         */
        void uploadPicProgress(int progress);

        /**
         * 上传图片后的图片链接
         * @param link 图片链接
         */
        void uploadedPicLink(String link);

        void uploadPicFail(String err);

        /**
         * 上传文章成功
         */
        void uploadArticleSuccess();

        /**
         * 上传文章失败
         * @param err
         */
        void uploadArticleFail(String err);
    }
}
