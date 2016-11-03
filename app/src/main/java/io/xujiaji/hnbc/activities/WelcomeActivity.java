package io.xujiaji.hnbc.activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;

import com.flaviofaria.kenburnsview.KenBurnsView;
import com.flaviofaria.kenburnsview.RandomTransitionGenerator;
import com.flaviofaria.kenburnsview.Transition;

import io.xujiaji.hnbc.R;
import io.xujiaji.hnbc.contracts.WelcomeContract;
import io.xujiaji.hnbc.presenters.WelPresenter;
import io.xujiaji.hnbc.utils.ScreenUtils;
import no.agens.depth.lib.tween.interpolators.ExpoOut;


public class WelcomeActivity extends BaseActivity<WelcomeContract.Presenter> implements WelcomeContract.View {
    private KenBurnsView mKenBurnsView;

    @Override
    protected void initView() {
        mKenBurnsView = $(R.id.img);
        initKenBurn();
        presenter.setWelPic(mKenBurnsView);
        presenter.start();
    }

    private void initKenBurn() {
        RandomTransitionGenerator rtg = new RandomTransitionGenerator(6000, new AccelerateInterpolator());
        mKenBurnsView.setTransitionGenerator(rtg);
    }

    @Override
    protected void addListener() {
        mKenBurnsView.setTransitionListener(new KenBurnsView.TransitionListener() {
            @Override
            public void onTransitionStart(Transition transition) {
            }

            @Override
            public void onTransitionEnd(Transition transition) {
                startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
                finish();
            }
        });
    }

    @Override
    protected void click(int id) {

    }

    @Override
    protected int getContentId() {
        return R.layout.activity_welcome;
    }

    @Override
    protected WelcomeContract.Presenter getPresenter() {
        return new WelPresenter(this);
    }


    @Override
    public void startAnim() {
        ViewGroup view = $(R.id.layout);
        float curTranslationY = view.getTranslationY();
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, View.TRANSLATION_Y, curTranslationY, ScreenUtils.getScreenHeight(this) / 2);
        animator.setDuration(3000);
        animator.setInterpolator(new ExpoOut());
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                showHello();
            }
        });

        animator.start();
    }

    @Override
    public void showHello() {
        $(R.id.tvHello).setVisibility(View.VISIBLE);
    }
}
