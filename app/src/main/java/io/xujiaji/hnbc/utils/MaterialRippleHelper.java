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
