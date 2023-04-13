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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;

import com.mapbox.maps.CoordinateBounds;

import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.LatLng;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.LatLngBounds;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.VisibleRegion;

@RestrictTo(LIBRARY)
public class MapboxVisibleRegion implements VisibleRegion {

    private final @NonNull CoordinateBounds mDelegate;

    private LatLng mNearLeft;
    private LatLng mNearRight;
    private LatLng mFarLeft;
    private LatLng mFarRight;
    private LatLngBounds mLatLngBounds;

    private MapboxVisibleRegion(@NonNull CoordinateBounds delegate) {
        mDelegate = delegate;
    }

    public MapboxVisibleRegion(@NonNull LatLngBounds latLngBounds) {
        mDelegate = MapboxLatLngBounds.unwrap(latLngBounds);
        mLatLngBounds = latLngBounds;
    }

    @Override public @NonNull LatLng getNearLeft() {
        if (mNearLeft == null) {
            mNearLeft = MapboxLatLng.wrap(mDelegate.getSouthwest());
        }
        return mNearLeft;
    }

    @Override public @NonNull LatLng getNearRight() {
        if (mNearRight == null) {
            mNearRight = MapboxLatLng.wrap(mDelegate.southeast());
        }
        return mNearRight;
    }

    @Override public @NonNull LatLng getFarLeft() {
        if (mFarLeft == null) {
            mFarLeft = MapboxLatLng.wrap(mDelegate.northwest());
        }
        return mFarLeft;
    }

    @Override public @NonNull LatLng getFarRight() {
        if (mFarRight == null) {
            mFarRight = MapboxLatLng.wrap(mDelegate.getNortheast());
        }
        return mFarRight;
    }

    @Override public @NonNull LatLngBounds getLatLngBounds() {
        if (mLatLngBounds == null) {
            mLatLngBounds = MapboxLatLngBounds.wrap(mDelegate);
        }
        return mLatLngBounds;
    }

    @Override public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        MapboxVisibleRegion that = (MapboxVisibleRegion) obj;

        return mDelegate.equals(that.mDelegate);
    }

    @Override public int hashCode() {
        return mDelegate.hashCode();
    }

    @Override public @NonNull String toString() {
        return mDelegate.toString();
    }


    static @NonNull VisibleRegion wrap(@NonNull CoordinateBounds delegate) {
        return new MapboxVisibleRegion(delegate);
    }

}
