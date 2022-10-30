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

import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.ButtCap;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.Cap;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.CustomCap;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.RoundCap;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.SquareCap;

abstract class TomTomCap implements Cap {

    static Cap wrap(com.tomtom.sdk.maps.display.polyline.CapType delegate) {
        if (delegate == com.tomtom.sdk.maps.display.polyline.CapType.NONE) {
            return TomTomButtCap.INSTANCE;

        } else if (delegate == com.tomtom.sdk.maps.display.polyline.CapType.ROUND) {
            return TomTomRoundCap.INSTANCE;

        } else if (delegate == com.tomtom.sdk.maps.display.polyline.CapType.RECTANGLE) {
            return TomTomSquareCap.INSTANCE;
        }

        throw new UnsupportedOperationException("Unsupported cap type " + delegate.toString());
    }

    static com.tomtom.sdk.maps.display.polyline.CapType unwrap(Cap wrapped) {
        if (wrapped instanceof ButtCap) {
            return com.tomtom.sdk.maps.display.polyline.CapType.NONE;

        } else if (wrapped instanceof CustomCap) {
            return com.tomtom.sdk.maps.display.polyline.CapType.NONE;

        } else if (wrapped instanceof RoundCap) {
            return com.tomtom.sdk.maps.display.polyline.CapType.ROUND;

        } else if (wrapped instanceof SquareCap) {
            return com.tomtom.sdk.maps.display.polyline.CapType.RECTANGLE;
        }

        throw new UnsupportedOperationException("Unsupported cap type " + wrapped.toString());
    }

}
