package io.xujiaji.sample.presenters;

import io.xujiaji.sample.contracts.MainContract;
import io.xujiaji.sample.model.DataFill;
import io.xujiaji.xmvp.presenters.BasePresenter;

/**
 * Created by jiana on 16-11-19.
 */

public class MainPresenter extends BasePresenter<MainContract.View> implements MainContract.Presenter {

    public MainPresenter(MainContract.View view) {
        super(view);
    }

    @Override
    public void start() {
        super.start();
        requestTextData();
    }

    @Override
    public void requestTextData() {
        String textData = DataFill.getText();
        view.showText(textData);
    }
}
