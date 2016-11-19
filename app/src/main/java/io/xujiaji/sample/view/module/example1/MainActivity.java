package io.xujiaji.sample.view.module.example1;

import android.widget.TextView;

import butterknife.BindView;
import io.xujiaji.sample.R;
import io.xujiaji.sample.contracts.MainContract;
import io.xujiaji.sample.presenters.MainPresenter;
import io.xujiaji.xmvp.view.base.BaseActivity;

public class MainActivity extends BaseActivity<MainPresenter> implements MainContract.View {
    @BindView(R.id.tvText)
    TextView tvText;

    @Override
    protected int getContentId() {
        return R.layout.activity_main2;
    }


    @Override
    public void showText(String text) {
        tvText.setText(text);
    }
}
