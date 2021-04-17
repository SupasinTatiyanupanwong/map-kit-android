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

import android.graphics.Point;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.LatLng;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.Projection;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.VisibleRegion;

class GoogleProjection implements Projection {

    private final com.google.android.gms.maps.Projection mDelegate;

    private GoogleProjection(com.google.android.gms.maps.Projection delegate) {
        mDelegate = delegate;
    }

    @Override
    public @Nullable LatLng fromScreenLocation(Point point) {
        return GoogleLatLng.wrap(mDelegate.fromScreenLocation(point));
    }

    @Override
    public @NonNull Point toScreenLocation(LatLng location) {
        return mDelegate.toScreenLocation(GoogleLatLng.unwrap(location));
    }

    @Override
    public @NonNull VisibleRegion getVisibleRegion() {
        return GoogleVisibleRegion.wrap(mDelegate.getVisibleRegion());
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        GoogleProjection that = (GoogleProjection) obj;

        return mDelegate.equals(that.mDelegate);
    }

    @Override
    public int hashCode() {
        return mDelegate.hashCode();
    }

    @Override
    public @NonNull String toString() {
        return mDelegate.toString();
    }


    static Projection wrap(com.google.android.gms.maps.Projection delegate) {
        return new GoogleProjection(delegate);
    }

}
