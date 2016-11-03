package io.xujiaji.developersbackpack.presenters;

import java.util.ArrayList;
import java.util.List;

import io.xujiaji.developersbackpack.R;
import io.xujiaji.developersbackpack.app.App;
import io.xujiaji.developersbackpack.contracts.MainContract;

/**
 * Created by jiana on 16-7-22.
 */
public class MainPresenter implements MainContract.Presenter {
    private MainContract.View view;
    private List<String> menuData;
    public MainPresenter(MainContract.View view) {
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
    public void start() {

    }
}
