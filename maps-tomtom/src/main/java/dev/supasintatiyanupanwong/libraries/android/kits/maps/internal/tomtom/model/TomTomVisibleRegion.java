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

import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.LatLng;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.LatLngBounds;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.VisibleRegion;

@RestrictTo(LIBRARY)
public class TomTomVisibleRegion implements VisibleRegion {

    private final @NonNull com.tomtom.sdk.maps.display.map.VisibleRegion mDelegate;

    private LatLng mNearLeft;
    private LatLng mNearRight;
    private LatLng mFarLeft;
    private LatLng mFarRight;
    private LatLngBounds mLatLngBounds;

    private TomTomVisibleRegion(@NonNull com.tomtom.sdk.maps.display.map.VisibleRegion delegate) {
        mDelegate = delegate;
    }

    public TomTomVisibleRegion(
            LatLng nearLeft,
            LatLng nearRight,
            LatLng farLeft,
            LatLng farRight,
            LatLngBounds latLngBounds) {
        mDelegate = new com.tomtom.sdk.maps.display.map.VisibleRegion(
                TomTomLatLng.unwrap(nearLeft),
                TomTomLatLng.unwrap(nearRight),
                TomTomLatLng.unwrap(farLeft),
                TomTomLatLng.unwrap(farRight),
                TomTomLatLngBounds.unwrapToGeoBoundingBox(latLngBounds)
        );
    }

    @Override public @NonNull LatLng getNearLeft() {
        if (mNearLeft == null) {
            mNearLeft = TomTomLatLng.wrap(mDelegate.getNearLeft());
        }
        return mNearLeft;
    }

    @Override public @NonNull LatLng getNearRight() {
        if (mNearRight == null) {
            mNearRight = TomTomLatLng.wrap(mDelegate.getNearRight());
        }
        return mNearRight;
    }

    @Override public @NonNull LatLng getFarLeft() {
        if (mFarLeft == null) {
            mFarLeft = TomTomLatLng.wrap(mDelegate.getFarLeft());
        }
        return mFarLeft;
    }

    @Override public @NonNull LatLng getFarRight() {
        if (mFarRight == null) {
            mFarRight = TomTomLatLng.wrap(mDelegate.getFarRight());
        }
        return mFarRight;
    }

    @Override public @NonNull LatLngBounds getLatLngBounds() {
        if (mLatLngBounds == null) {
            mLatLngBounds = TomTomLatLngBounds.wrapGeoBoundingBox(mDelegate.getBounds());
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

        TomTomVisibleRegion that = (TomTomVisibleRegion) obj;

        return mDelegate.equals(that.mDelegate);
    }

    @Override public int hashCode() {
        return mDelegate.hashCode();
    }

    @Override public @NonNull String toString() {
        return mDelegate.toString();
    }


    static @NonNull VisibleRegion wrap(
            @NonNull com.tomtom.sdk.maps.display.map.VisibleRegion delegate
    ) {
        return new TomTomVisibleRegion(delegate);
    }

}
