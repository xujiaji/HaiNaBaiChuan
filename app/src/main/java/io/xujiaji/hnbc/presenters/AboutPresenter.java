package io.xujiaji.hnbc.presenters;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import io.xujiaji.hnbc.app.App;
import io.xujiaji.hnbc.contracts.AboutContract;

/**
 * Created by jiana on 01/12/16.
 */

public class AboutPresenter extends BasePresenter<AboutContract.View> implements AboutContract.Presenter {
    public AboutPresenter(AboutContract.View view) {
        super(view);
    }

    @Override
    public void getNowVersion() {
        PackageInfo pi = null;
        try {
            PackageManager pm = App.getAppContext().getPackageManager();
            pi = pm.getPackageInfo(App.getAppContext().getPackageName(),
                    PackageManager.GET_CONFIGURATIONS);
            view.showNowVersion(pi.versionName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
