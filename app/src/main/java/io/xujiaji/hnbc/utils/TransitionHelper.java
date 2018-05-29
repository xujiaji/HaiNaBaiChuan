/*
 * Copyright 2018 XuJiaji
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

package io.xujiaji.hnbc.utils;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.support.annotation.NonNull;
import android.view.View;

import io.xujiaji.hnbc.R;
import no.agens.depth.lib.DepthLayout;
import no.agens.depth.lib.tween.interpolators.BackOut;
import no.agens.depth.lib.tween.interpolators.CircInOut;
import no.agens.depth.lib.tween.interpolators.ExpoIn;
import no.agens.depth.lib.tween.interpolators.ExpoOut;
import no.agens.depth.lib.tween.interpolators.QuadInOut;
import no.agens.depth.lib.tween.interpolators.QuintInOut;
import no.agens.depth.lib.tween.interpolators.QuintOut;

public class TransitionHelper {

    public static final float TARGET_SCALE = 0.5f;
    public static final float TARGET_ROTATION = -50f;
    public static final float TARGET_ROTATION_X = 60f;
    public static final int MOVE_Y_STEP = 15;
    public static final int DURATION = 1100;
    public static final QuintOut VALUEinterpolator = new QuintOut();

    public static final int FISRTDELAY = 300;

    public static DepthLayout getLayout(View root, int idRes) {
        return (DepthLayout) root.findViewById(idRes);
    }

    public static void startIntroAnim(View root, AnimatorListenerAdapter introEndListener) {
        DepthLayout dl = getLayout(root, R.id.root_dl);
        ObjectAnimator objectAnimator = null;
        if (dl != null) {
            objectAnimator = introAnimate(dl, 0, 30f, 15, 180);
        }
        dl = getLayout(root, R.id.appbar);
        if (dl != null) {
            objectAnimator = introAnimate(dl, MOVE_Y_STEP, 20f, 30, 170);
        }
        dl = getLayout(root, R.id.status);
        if (dl != null) {
            objectAnimator = introAnimate(dl, MOVE_Y_STEP * 2, 20f, 75, 210);
        }
//        dl = getLayout(root, R.id.fab_right);
//        if (dl != null) {
//            objectAnimator = introAnimate(dl, MOVE_Y_STEP * 2f, 20f, 45, 190);
//        }
        dl = getLayout(root, R.id.fab_container);
        if (dl != null) {
            objectAnimator = introAnimate(dl, MOVE_Y_STEP * 2f, 20f, 45, 190);
        }
        dl = getLayout(root, R.id.dl2);
        if (dl != null) {
            objectAnimator = introAnimate(dl, MOVE_Y_STEP, 20f, 60, 200);
        }
        dl = getLayout(root, R.id.dl3);
        if (dl != null) {
            objectAnimator = introAnimate(dl, MOVE_Y_STEP * 2, 20f, 75, 210);
        }
        if (objectAnimator != null) {
            objectAnimator.addListener(introEndListener);
        }
    }

    static ObjectAnimator introAnimate(final DepthLayout target, final float moveY, final float customElevation, long delay, int subtractDelay) {
        LogUtil.e2("rootView.getTranslationY() = " + target.getTranslationY());
        target.setPivotY(getDistanceToCenter(target));
        target.setPivotX(getDistanceToCenterX(target));
        target.setCameraDistance(10000 * target.getResources().getDisplayMetrics().density);

        ObjectAnimator translationY2 = ObjectAnimator.ofFloat(target, View.TRANSLATION_Y, target.getResources().getDisplayMetrics().heightPixels, -moveY * target.getResources().getDisplayMetrics().density).setDuration(800);
        translationY2.setInterpolator(new ExpoOut());
        translationY2.setStartDelay(700 + subtractDelay);
        translationY2.start();
        target.setTranslationY(target.getResources().getDisplayMetrics().heightPixels);
        translationY2.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                LogUtil.e3("end y = " + (-moveY * target.getResources().getDisplayMetrics().density));
            }
        });

        ObjectAnimator translationX2 = ObjectAnimator.ofFloat(target, View.TRANSLATION_X, -target.getResources().getDisplayMetrics().widthPixels, 0).setDuration(800);
        translationX2.setInterpolator(new ExpoOut());
        translationX2.setStartDelay(700 + subtractDelay);
        translationX2.start();
        target.setTranslationX(-target.getResources().getDisplayMetrics().widthPixels);

        ObjectAnimator translationY = ObjectAnimator.ofFloat(target, View.TRANSLATION_Y, 0).setDuration(700);
        translationY.setInterpolator(new BackOut());
        translationY.setStartDelay(700 + 800);
        translationY.start();


        ObjectAnimator rotationX = ObjectAnimator.ofFloat(target, View.ROTATION_X, TARGET_ROTATION_X, 0).setDuration(1000);
        rotationX.setInterpolator(new QuintInOut());
        rotationX.setStartDelay(700 + FISRTDELAY + subtractDelay);
        rotationX.start();
        target.setRotationX(TARGET_ROTATION_X);

        ObjectAnimator elevation = ObjectAnimator.ofFloat(target, "CustomShadowElevation", customElevation * target.getResources().getDisplayMetrics().density, target.getCustomShadowElevation()).setDuration(1000);
        elevation.setInterpolator(new QuintInOut());
        elevation.setStartDelay(700 + FISRTDELAY + subtractDelay * 2);
        elevation.start();
        target.setCustomShadowElevation(customElevation * target.getResources().getDisplayMetrics().density);

        ObjectAnimator scaleX = ObjectAnimator.ofFloat(target, View.SCALE_X, TARGET_SCALE, 1f).setDuration(1000);
        scaleX.setInterpolator(new CircInOut());
        scaleX.setStartDelay(700 + FISRTDELAY + subtractDelay);
        scaleX.start();
        target.setScaleX(TARGET_SCALE);

        ObjectAnimator scaleY = ObjectAnimator.ofFloat(target, View.SCALE_Y, TARGET_SCALE, 1f).setDuration(1000);
        scaleY.setInterpolator(new CircInOut());
        scaleY.setStartDelay(700 + FISRTDELAY + subtractDelay);
        scaleY.start();
        target.setScaleY(TARGET_SCALE);
        scaleY.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);

            }
        });

        ObjectAnimator rotation = ObjectAnimator.ofFloat(target, View.ROTATION, TARGET_ROTATION, 0).setDuration(1400);
        rotation.setInterpolator(new QuadInOut());
        rotation.setStartDelay(FISRTDELAY + subtractDelay);
        rotation.start();
        target.setRotation(TARGET_ROTATION);
        rotation.addListener(getShowStatusBarListener(target));
        return scaleY;
    }


    public static void startExitAnim(View root) {
        exitAnimate((DepthLayout) root.findViewById(R.id.root_dl), 0, 30f, 15, 190, true);
        exitAnimate((DepthLayout) root.findViewById(R.id.appbar), MOVE_Y_STEP, 20f, 30, 170, true);
        exitAnimate((DepthLayout) root.findViewById(R.id.fab_container), MOVE_Y_STEP * 2f, 20f, 45, 210, true);
        exitAnimate((DepthLayout) root.findViewById(R.id.dl2), MOVE_Y_STEP, 20f, 60, 230, true);
        exitAnimate((DepthLayout) root.findViewById(R.id.dl3), MOVE_Y_STEP * 2, 20f, 75, 250, true);
        hideStatusBar(root);
    }

    private static void hideStatusBar(View root) {
//        View decorView = ((Activity) root.getContext()).getWindow().getDecorView();
//        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
//        decorView.setSystemUiVisibility(uiOptions);
    }


    static ValueAnimator exitAnimate(final DepthLayout target, final float moveY, final float customElevation, long delay, int subtractDelay, boolean continueOffscreen) {

        target.setPivotY(getDistanceToCenter(target));
        target.setPivotX(getDistanceToCenterX(target));
        target.setCameraDistance(10000 * target.getResources().getDisplayMetrics().density);

        ObjectAnimator rotationX = ObjectAnimator.ofFloat(target, View.ROTATION_X, TARGET_ROTATION_X).setDuration(DURATION);
        rotationX.setInterpolator(VALUEinterpolator);
        rotationX.setStartDelay(delay);
        rotationX.start();

        ObjectAnimator elevation = ObjectAnimator.ofFloat(target, "CustomShadowElevation", target.getCustomShadowElevation(), customElevation * target.getResources().getDisplayMetrics().density).setDuration(DURATION);
        elevation.setInterpolator(VALUEinterpolator);
        elevation.setStartDelay(delay);
        elevation.start();

        ObjectAnimator scaleX = ObjectAnimator.ofFloat(target, View.SCALE_X, TARGET_SCALE).setDuration(DURATION);
        scaleX.setInterpolator(new QuintOut());
        scaleX.setStartDelay(delay);
        scaleX.start();

        ObjectAnimator scaleY = ObjectAnimator.ofFloat(target, View.SCALE_Y, TARGET_SCALE).setDuration(DURATION);
        scaleY.setInterpolator(new QuintOut());
        scaleY.setStartDelay(delay);
        scaleY.start();

        ObjectAnimator rotation = ObjectAnimator.ofFloat(target, View.ROTATION, TARGET_ROTATION).setDuration(1600);
        rotation.setInterpolator(VALUEinterpolator);
        rotation.setStartDelay(delay);
        rotation.start();

        ObjectAnimator translationY = ObjectAnimator.ofFloat(target, View.TRANSLATION_Y, -moveY * target.getResources().getDisplayMetrics().density).setDuration(subtractDelay);
        translationY.setInterpolator(VALUEinterpolator);
        translationY.setStartDelay(delay);
        translationY.start();
        if (continueOffscreen) {
            continueOutToRight(target, moveY, subtractDelay);
        }
        return scaleY;
    }

    private static void continueOutToRight(DepthLayout target, float moveY, int subtractDelay) {
        ObjectAnimator translationY2 = ObjectAnimator.ofFloat(target, View.TRANSLATION_Y, -moveY * target.getResources().getDisplayMetrics().density, -target.getResources().getDisplayMetrics().heightPixels).setDuration(900);
        translationY2.setInterpolator(new ExpoIn());
        translationY2.setStartDelay(0 + subtractDelay);

        translationY2.start();

        ObjectAnimator translationX2 = ObjectAnimator.ofFloat(target, View.TRANSLATION_X, target.getTranslationX(), target.getResources().getDisplayMetrics().widthPixels).setDuration(900);
        translationX2.setInterpolator(new ExpoIn());
        translationX2.setStartDelay(0 + subtractDelay);
        translationX2.start();
    }

    public static float getDistanceToCenter(View target) {
        float viewCenter = target.getTop() + target.getHeight() / 2f;
        float rootCenter = ((View) target.getParent()).getHeight() / 2;
        return target.getHeight() / 2f + rootCenter - viewCenter;
    }

    public static float getDistanceToCenterX(View target) {
        float viewCenter = target.getLeft() + target.getWidth() / 2f;
        float rootCenter = ((View) target.getParent()).getWidth() / 2;
        return target.getWidth() / 2f + rootCenter - viewCenter;
    }

    public static void animateToMenuState(View root, AnimatorListenerAdapter onMenuAnimFinished) {
        hideStatusBar(root);
        DepthLayout dl = getLayout(root, R.id.root_dl);
        ValueAnimator valueAnimator = null;
        if (dl != null) {
            valueAnimator = exitAnimate(dl, 0, 30f, 15, 190, false);
        }
        dl = getLayout(root, R.id.appbar);
        if (dl != null) {
            valueAnimator = exitAnimate(dl, MOVE_Y_STEP, 20f, 30, 170, false);
        }
        dl = getLayout(root, R.id.status);
        if (dl != null) {
            valueAnimator = exitAnimate(dl, MOVE_Y_STEP * 2, 20f, 75, 250, false);
        }
//        dl = getLayout(root, R.id.fab_right);
//        if (dl != null) {
//            valueAnimator = exitAnimate(dl, MOVE_Y_STEP * 2f, 20f, 45, 210, false);
//        }
        dl = getLayout(root, R.id.fab_container);
        if (dl != null) {
            valueAnimator = exitAnimate(dl, MOVE_Y_STEP * 2f, 20f, 45, 210, false);
        }
        dl = getLayout(root, R.id.dl2);
        if (dl != null) {
            valueAnimator = exitAnimate(dl, MOVE_Y_STEP, 20f, 60, 230, false);
        }
        dl = getLayout(root, R.id.dl3);
        if (dl != null) {
            valueAnimator = exitAnimate(dl, MOVE_Y_STEP * 2, 20f, 75, 250, false);
        }

        if (valueAnimator != null) {
            valueAnimator.addListener(onMenuAnimFinished);
        }

        ObjectAnimator translationY = ObjectAnimator.ofFloat(root, View.TRANSLATION_Y, -90f * root.getResources().getDisplayMetrics().density).setDuration(DURATION);
        translationY.setInterpolator(VALUEinterpolator);
        translationY.start();
    }

    private static void makeAppFullscreen(View target) {
//        ((Activity) target.getContext()).getWindow().setStatusBarColor(Color.TRANSPARENT);
//        ((Activity) target.getContext()).getWindow().getDecorView().setSystemUiVisibility(
//                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
    }

    public static void animateMenuOut(View root) {
        DepthLayout dl = getLayout(root, R.id.root_dl);
        if (dl != null) {
            continueOutToRight(dl, 0, 20);
        }
        dl = getLayout(root, R.id.appbar);
        if (dl != null) {
            continueOutToRight(dl, MOVE_Y_STEP, 0);
        }
        dl = getLayout(root, R.id.status);
        if (dl != null) {
            continueOutToRight(dl, MOVE_Y_STEP * 2, 80);
        }
//        dl = getLayout(root, R.id.fab_right);
//        if (dl != null) {
//            continueOutToRight(dl, MOVE_Y_STEP * 2f, 40);
//        }
        dl = getLayout(root, R.id.fab_container);
        if (dl != null) {
            continueOutToRight(dl, MOVE_Y_STEP * 2f, 40);
        }
        dl = getLayout(root, R.id.dl2);
        if (dl != null) {
            continueOutToRight(dl, MOVE_Y_STEP, 60);
        }
        dl = getLayout(root, R.id.dl3);
        if (dl != null) {
            continueOutToRight(dl, MOVE_Y_STEP * 2, 80);
        }

    }

    /*
        public static void animateToMenuState(View root, AnimatorListenerAdapter onMenuAnimFinished) {
        hideStatusBar(root);
        DepthLayout dl = getLayout(root, R.id.root_dl);
        if (dl != null) {

        }
        dl = getLayout(root, R.id.appbar);
        if (dl != null) {

        }
        dl = getLayout(root, R.id.status);
        if (dl != null) {

        }
        dl = getLayout(root, R.id.fab_container);
        if (dl != null) {

        }
        dl = getLayout(root, R.id.dl2);
        if (dl != null) {

        }
        dl = getLayout(root, R.id.dl3);
        if (dl != null) {

        }


    }
     */

    public static void startRevertFromMenu(View root, AnimatorListenerAdapter animatorListenerAdapter) {
        DepthLayout dl = getLayout(root, R.id.root_dl);
        ObjectAnimator oa = null;
        if (dl != null) {//exitAnimate(dl, 0, 30f, 15, 190, false);
            oa = revertFromMenu(dl, 30f, 10, 0);
        }
        dl = getLayout(root, R.id.appbar);
        if (dl != null) {//exitAnimate(dl, MOVE_Y_STEP, 20f, 30, 170, false);
            oa = revertFromMenu(dl, 20f, 0, 0);
        }
        dl = getLayout(root, R.id.status);
        if (dl != null) {//exitAnimate(dl, MOVE_Y_STEP * 2, 20f, 75, 250, false);
            oa = revertFromMenu(dl, 20f, 40, 2);
        }
//        dl = getLayout(root, R.id.fab_right);
//        if (dl != null) {//exitAnimate(dl, MOVE_Y_STEP * 2f, 20f, 45, 210, false);
//            oa = revertFromMenu(dl, 20f, 20, 6);
//        }
        dl = getLayout(root, R.id.fab_container);
        if (dl != null) {//exitAnimate(dl, MOVE_Y_STEP * 2f, 20f, 45, 210, false);
            oa = revertFromMenu(dl, 20f, 20, 6);
        }
        dl = getLayout(root, R.id.dl2);
        if (dl != null) {//exitAnimate(dl, MOVE_Y_STEP, 20f, 60, 230, false);
            oa = revertFromMenu(dl, 20f, 30, 1);
        }
        dl = getLayout(root, R.id.dl3);
        if (dl != null) {//exitAnimate(dl, MOVE_Y_STEP * 2, 20f, 75, 250, false).addListener(onMenuAnimFinished);
            oa = revertFromMenu(dl, 20f, 40, 2);
        }

        if (oa != null) {
            oa.addListener(animatorListenerAdapter);
        }
//        ObjectAnimator translationY = ObjectAnimator.ofFloat(root, View.TRANSLATION_Y, -90f * root.getResources().getDisplayMetrics().density).setDuration(DURATION);
//        translationY.setInterpolator(VALUEinterpolator);
//        translationY.start();

        ObjectAnimator translationY = ObjectAnimator.ofFloat(root, View.TRANSLATION_Y, 0).setDuration(DURATION);
        translationY.setInterpolator(new QuintInOut());
        translationY.start();
    }


    static ObjectAnimator revertFromMenu(final DepthLayout target, final float customElevation, int subtractDelay, float targetElevation) {

        target.setPivotY(getDistanceToCenter(target));
        target.setPivotX(getDistanceToCenterX(target));
        target.setCameraDistance(10000 * target.getResources().getDisplayMetrics().density);


        ObjectAnimator translationY = ObjectAnimator.ofFloat(target, View.TRANSLATION_Y, 0).setDuration(700);
        translationY.setInterpolator(new BackOut());
        translationY.setStartDelay(250 + FISRTDELAY + subtractDelay);
        translationY.start();


        ObjectAnimator rotationX = ObjectAnimator.ofFloat(target, View.ROTATION_X, target.getRotationX(), 0).setDuration(1000);
        rotationX.setInterpolator(new QuintInOut());
        rotationX.setStartDelay(FISRTDELAY + subtractDelay);
        rotationX.start();
        target.setRotationX(TARGET_ROTATION_X);

        ObjectAnimator elevation = ObjectAnimator.ofFloat(target, "CustomShadowElevation", target.getCustomShadowElevation(), targetElevation * target.getResources().getDisplayMetrics().density).setDuration(1000);
        elevation.setInterpolator(new QuintInOut());
        elevation.setStartDelay(FISRTDELAY + subtractDelay * 2);
        elevation.start();
        target.setCustomShadowElevation(customElevation * target.getResources().getDisplayMetrics().density);

        ObjectAnimator scaleX = ObjectAnimator.ofFloat(target, View.SCALE_X, target.getScaleX(), 1f).setDuration(1000);
        scaleX.setInterpolator(new CircInOut());
        scaleX.setStartDelay(FISRTDELAY + subtractDelay);
        scaleX.start();
        target.setScaleX(TARGET_SCALE);

        ObjectAnimator scaleY = ObjectAnimator.ofFloat(target, View.SCALE_Y, target.getScaleY(), 1f).setDuration(1000);
        scaleY.setInterpolator(new CircInOut());
        scaleY.setStartDelay(FISRTDELAY + subtractDelay);
        scaleY.start();
        target.setScaleY(TARGET_SCALE);

        ObjectAnimator rotation = ObjectAnimator.ofFloat(target, View.ROTATION, target.getRotation(), 0).setDuration(1100);
        rotation.setInterpolator(new QuintInOut());
        rotation.setStartDelay(subtractDelay);
        rotation.start();
        rotation.addListener(getShowStatusBarListener(target));
        return scaleY;
    }

    @NonNull
    private static AnimatorListenerAdapter getShowStatusBarListener(final DepthLayout target) {
        return new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                makeAppFullscreen(target);
            }
        };
    }

}
