package io.xujiaji.developersbackpack.utils;

import android.view.View;

import com.balysv.materialripple.MaterialRippleLayout;

import io.xujiaji.developersbackpack.R;
import io.xujiaji.developersbackpack.app.App;

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
