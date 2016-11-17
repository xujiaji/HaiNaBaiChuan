package io.xujiaji.hnbc.presenters;

import java.util.ArrayList;
import java.util.List;

import io.xujiaji.hnbc.R;
import io.xujiaji.hnbc.app.App;
import io.xujiaji.hnbc.contracts.MainContract;
import io.xujiaji.hnbc.model.data.DataFiller;

/**
 * Created by jiana on 16-7-22.
 */
public class MainPresenter extends BasePresenter <MainContract.View> implements MainContract.Presenter {
    private List<String> menuData;
    public MainPresenter(MainContract.View view) {
        super(view);
        menuData = new ArrayList<>();
        menuData.add(App.getAppContext().getString(R.string.main_page));
        menuData.add(App.getAppContext().getString(R.string.self_info));
        menuData.add(App.getAppContext().getString(R.string.my_collect));
        menuData.add(App.getAppContext().getString(R.string.my_release));
        menuData.add(App.getAppContext().getString(R.string.set));
    }

    @Override
    public List<String> getMenuData() {
        return menuData;
    }

    @Override
    public boolean checkLocalUser() {
        return DataFiller.getLocalUser() != null;
    }


    @Override
    public void start() {
//        ApiNet.phoneNumAttrApiInstance().search("10000", "f3e2cd2caa8f1eb446078eb63edf3f87")
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Subscriber<PhoneNumAttr>() {
//                    @Override
//                    public void onCompleted() {
//
//                    }
//
//                    @Override
//                    public void onError(Throwable throwable) {
//
//                    }
//
//                    @Override
//                    public void onNext(PhoneNumAttr phoneNumAttr) {
//
//                    }
//                });
    }

    @Override
    public void end() {
        super.end();
        menuData = null;
    }
}
