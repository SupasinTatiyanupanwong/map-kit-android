/*
 * Copyright 2022 Supasin Tatiyanupanwong
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package dev.supasintatiyanupanwong.libraries.android.kits.maps.internal.tomtom.model;

import android.graphics.Color;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;

final class TomTomColor {

    private TomTomColor() {}


    static @ColorInt int wrap(@NonNull com.tomtom.sdk.maps.display.common.Color delegate) {
        return Color.argb(
                (int) (0.5f + 255.0f * delegate.getAlpha()),
                (int) (0.5f + 255.0f * delegate.getRed()),
                (int) (0.5f + 255.0f * delegate.getGreen()),
                (int) (0.5f + 255.0f * delegate.getBlue())
        );
    }

    static @NonNull com.tomtom.sdk.maps.display.common.Color unwrap(@ColorInt int wrapped) {
        return new com.tomtom.sdk.maps.display.common.Color(
                Color.red(wrapped),
                Color.green(wrapped),
                Color.blue(wrapped),
                Color.alpha(wrapped)
        );
    }

}
