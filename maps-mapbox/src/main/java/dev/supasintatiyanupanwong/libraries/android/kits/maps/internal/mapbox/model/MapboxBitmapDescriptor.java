/*
 * Copyright 2023 Supasin Tatiyanupanwong
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

package dev.supasintatiyanupanwong.libraries.android.kits.maps.internal.mapbox.model;

import static androidx.annotation.RestrictTo.Scope.LIBRARY;

import android.graphics.Bitmap;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;

import org.jetbrains.annotations.Contract;

import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.BitmapDescriptor;

@RestrictTo(LIBRARY)
public class MapboxBitmapDescriptor implements BitmapDescriptor {

    private final @NonNull Bitmap mDelegate;

    private MapboxBitmapDescriptor(@NonNull Bitmap delegate) {
        mDelegate = delegate;
    }

    @Override public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        MapboxBitmapDescriptor that = (MapboxBitmapDescriptor) obj;

        return mDelegate.equals(that.mDelegate);
    }

    @Override public int hashCode() {
        return mDelegate.hashCode();
    }

    @Override public @NonNull String toString() {
        return mDelegate.toString();
    }


    @Contract("null -> null; !null -> !null")
    static @Nullable BitmapDescriptor wrap(@Nullable Bitmap delegate) {
        return delegate == null ? null : new MapboxBitmapDescriptor(delegate);
    }

    @Contract("null -> null; !null -> !null")
    static @Nullable Bitmap unwrap(@Nullable BitmapDescriptor wrapped) {
        return wrapped == null ? null : ((MapboxBitmapDescriptor) wrapped).mDelegate;
    }

}
