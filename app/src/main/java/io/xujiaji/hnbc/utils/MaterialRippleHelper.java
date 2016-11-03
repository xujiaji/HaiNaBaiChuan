package io.xujiaji.hnbc.utils;

import android.view.View;

import com.balysv.materialripple.MaterialRippleLayout;

import io.xujiaji.hnbc.R;
import io.xujiaji.hnbc.app.App;

public class MaterialRippleHelper {
    private MaterialRippleHelper() {
    }

    public static void ripple(View view) {
        MaterialRippleLayout.on(view)
                .rippleColor(App.getAppContext().getResources().getColor(R.color.theme_red))
                .rippleAlpha(0.2f)
                .rippleHover(true)
                .create();
    }
}
