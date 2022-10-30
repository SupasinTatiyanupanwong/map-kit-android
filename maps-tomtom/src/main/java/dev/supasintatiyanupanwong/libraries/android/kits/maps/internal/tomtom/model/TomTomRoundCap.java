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

import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.RoundCap;

@RestrictTo(LIBRARY)
public class TomTomRoundCap extends TomTomCap implements RoundCap {

    public static final @NonNull RoundCap INSTANCE = new TomTomRoundCap();

    private TomTomRoundCap() {}

    @SuppressWarnings("EqualsWhichDoesntCheckParameterClass")
    @Override public boolean equals(@Nullable Object obj) {
        return CapType.ROUND.equals(obj);
    }

    @Override public int hashCode() {
        return CapType.ROUND.hashCode();
    }

    @Override public @NonNull String toString() {
        return CapType.ROUND.toString();
    }

}
