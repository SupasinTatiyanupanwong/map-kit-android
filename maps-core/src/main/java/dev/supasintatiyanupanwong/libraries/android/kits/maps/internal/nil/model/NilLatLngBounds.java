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

package dev.supasintatiyanupanwong.libraries.android.kits.maps.internal.nil.model;

import static androidx.annotation.RestrictTo.Scope.LIBRARY;

import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;

import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.LatLng;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.LatLngBounds;

@RestrictTo(LIBRARY)
public class NilLatLngBounds implements LatLngBounds {

    public static final @NonNull LatLngBounds INSTANCE = new NilLatLngBounds();

    private NilLatLngBounds() {}

    @Override public @NonNull LatLng getSouthwest() {
        return NilLatLng.INSTANCE; // Not supported, null object for API safe.
    }

    @Override public @NonNull LatLng getNortheast() {
        return NilLatLng.INSTANCE; // Not supported, null object for API safe.
    }

    @Override public boolean contains(@NonNull LatLng point) {
        return false; // Not supported, fallback to default.
    }

    @Override public @NonNull LatLngBounds including(@NonNull LatLng point) {
        return NilLatLngBounds.INSTANCE; // Not supported, null object for API safe.
    }

    @Override public @NonNull LatLng getCenter() {
        return NilLatLng.INSTANCE; // Not supported, null object for API safe.
    }


    public static class Builder implements LatLngBounds.Builder {
        public static final @NonNull LatLngBounds.Builder INSTANCE = new Builder();

        private Builder() {}

        @Override public @NonNull LatLngBounds.Builder include(@NonNull LatLng point) {
            // Not supported, no-op.
            return this;
        }

        @Override public @NonNull LatLngBounds build() {
            return NilLatLngBounds.INSTANCE; // Not supported, null object for API safe.
        }
    }

}
