package io.xujiaji.hnbc.presenters;

import java.io.File;

import io.xujiaji.hnbc.R;
import io.xujiaji.hnbc.app.App;
import io.xujiaji.hnbc.contracts.EditorContract;
import io.xujiaji.hnbc.model.net.NetRequest;

/**
 * Created by jiana on 16-7-27.
 */
public class EditorPresenter extends BasePresenter <EditorContract.View> implements EditorContract.Presenter{

    public EditorPresenter(EditorContract.View view) {
        super(view);
    }

    @Override
    public void uploadPic(String path) {
        view.showUploadPicProgress();
        NetRequest.Instance().uploadPic(new File(path), new NetRequest.UploadPicListener<String>() {
            @Override
            public void compressingPic() {
                view.showCompressingPicDialog();
            }

            @Override
            public void compressedPicSuccess() {
                view.compressedPicSuccess();
            }

            @Override
            public void progress(int i) {
                view.uploadPicProgress(i);
            }

            @Override
            public void success(String s) {
                view.uploadedPicLink(s);
            }

            @Override
            public void error(String err) {
                view.uploadPicFail(err);
            }
        });
    }

    @Override
    public void uploadArticle(String coverPicture, String title, String article) {
        view.showUploadArticleProgress();
        if ("".equals(title)) {
            view.uploadArticleFail(App.getAppContext().getString(R.string.please_input_title));
            return;
        }
        if ("".equals(article)) {
            view.uploadArticleFail(App.getAppContext().getString(R.string.please_input_content));
        }

        NetRequest.Instance().uploadArticle(coverPicture, title, article, new NetRequest.RequestListener<String>() {
            @Override
            public void success(String s) {
                view.uploadArticleSuccess();
            }

            @Override
            public void error(String err) {
                view.uploadArticleFail(err);
            }
        });


    }
}
