package io.xujiaji.hnbc.contracts;

/**
 * Created by jiana on 16-7-27.
 */
public interface EditorContract {
    interface Presenter extends Contract.BasePresenter {
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

    interface View extends Contract.BaseView {

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
