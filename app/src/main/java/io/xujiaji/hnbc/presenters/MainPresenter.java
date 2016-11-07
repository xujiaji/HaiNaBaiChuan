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
public class MainPresenter extends BasePresenter implements MainContract.Presenter {
    private MainContract.View view;
    private List<String> menuData;
    public MainPresenter(MainContract.View view) {
        super(view);
        this.view = view;
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

    }

    @Override
    public void end() {
        view = null;
        menuData = null;
    }
}
