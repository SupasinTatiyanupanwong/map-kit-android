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

import static androidx.annotation.RestrictTo.Scope.LIBRARY;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;

import com.tomtom.sdk.maps.display.polyline.CapType;

import dev.supasintatiyanupanwong.libraries.android.kits.maps.internal.tomtom.R;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.BitmapDescriptor;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.ButtCap;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.CustomCap;

@RestrictTo(LIBRARY)
public class TomTomButtCap extends TomTomCap implements ButtCap, CustomCap {

    public static final @NonNull TomTomButtCap INSTANCE = new TomTomButtCap();

    private BitmapDescriptor mBitmapDescriptor;

    private TomTomButtCap() {}

    @Override public @NonNull BitmapDescriptor getBitmapDescriptor() {
        if (mBitmapDescriptor == null) {
            mBitmapDescriptor = TomTomBitmapDescriptor.FACTORY.fromResource(R.drawable.empty);
        }
        return mBitmapDescriptor;
    }

    @Override public float getRefWidth() {
        return 0;
    }

    @SuppressWarnings("EqualsWhichDoesntCheckParameterClass")
    @Override public boolean equals(@Nullable Object obj) {
        return CapType.NONE.equals(obj);
    }

    @Override public int hashCode() {
        return CapType.NONE.hashCode();
    }

    @Override public @NonNull String toString() {
        return CapType.NONE.toString();
    }

}
