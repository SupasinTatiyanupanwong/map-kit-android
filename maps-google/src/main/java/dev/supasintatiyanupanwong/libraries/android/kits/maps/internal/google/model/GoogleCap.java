/*
 * Copyright 2020 Supasin Tatiyanupanwong
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

package dev.supasintatiyanupanwong.libraries.android.kits.maps.internal.google.model;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.ButtCap;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.Cap;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.CustomCap;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.RoundCap;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.SquareCap;

abstract class GoogleCap implements Cap {

    static @NonNull Cap wrap(@Nullable com.google.android.gms.maps.model.Cap delegate) {
        if (delegate instanceof com.google.android.gms.maps.model.ButtCap) {
            return GoogleButtCap.wrap((com.google.android.gms.maps.model.ButtCap) delegate);

        } else if (delegate instanceof com.google.android.gms.maps.model.CustomCap) {
            return GoogleCustomCap.wrap((com.google.android.gms.maps.model.CustomCap) delegate);

        } else if (delegate instanceof com.google.android.gms.maps.model.RoundCap) {
            return GoogleRoundCap.wrap((com.google.android.gms.maps.model.RoundCap) delegate);

        } else if (delegate instanceof com.google.android.gms.maps.model.SquareCap) {
            return GoogleSquareCap.wrap((com.google.android.gms.maps.model.SquareCap) delegate);
        }

        throw new UnsupportedOperationException(
                "Unsupported cap type " + (delegate != null ? delegate.toString() : null)
        );
    }

    static @Nullable com.google.android.gms.maps.model.Cap unwrap(@Nullable Cap wrapped) {
        if (wrapped instanceof ButtCap) {
            return GoogleButtCap.unwrap((ButtCap) wrapped);

        } else if (wrapped instanceof CustomCap) {
            return GoogleCustomCap.unwrap((CustomCap) wrapped);

        } else if (wrapped instanceof RoundCap) {
            return GoogleRoundCap.unwrap((RoundCap) wrapped);

        } else if (wrapped instanceof SquareCap) {
            return GoogleSquareCap.unwrap((SquareCap) wrapped);
        }

        throw new UnsupportedOperationException(
                "Unsupported cap type " + (wrapped != null ? wrapped.toString() : null)
        );
    }
}
