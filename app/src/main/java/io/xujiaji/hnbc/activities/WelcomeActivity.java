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

import butterknife.BindView;
import butterknife.ButterKnife;
import io.xujiaji.hnbc.R;
import io.xujiaji.hnbc.contracts.WelcomeContract;
import io.xujiaji.hnbc.presenters.WelPresenter;
import io.xujiaji.hnbc.utils.ScreenUtils;
import no.agens.depth.lib.tween.interpolators.ExpoOut;


public class WelcomeActivity extends BaseActivity<WelPresenter> implements WelcomeContract.View {
    @BindView(R.id.img)
    KenBurnsView mKenBurnsView;

    @Override
    protected void onInit() {
        super.onInit();
        initKenBurn();
        presenter.setWelPic(mKenBurnsView);
        presenter.start();
    }

    private void initKenBurn() {
        RandomTransitionGenerator rtg = new RandomTransitionGenerator(4000, new AccelerateInterpolator());
        mKenBurnsView.setTransitionGenerator(rtg);
    }

    @Override
    protected void onListener() {
        super.onListener();
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
    protected int getContentId() {
        return R.layout.activity_welcome;
    }


    @Override
    public void startAnim() {
        ViewGroup view = ButterKnife.findById(this, R.id.layout);
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
        ButterKnife.findById(this, R.id.tvHello).setVisibility(View.VISIBLE);
    }
}
