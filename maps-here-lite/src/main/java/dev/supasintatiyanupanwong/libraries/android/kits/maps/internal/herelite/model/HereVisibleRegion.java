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

package dev.supasintatiyanupanwong.libraries.android.kits.maps.internal.herelite.model;

import static androidx.annotation.RestrictTo.Scope.LIBRARY;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;

import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.LatLng;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.LatLngBounds;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.VisibleRegion;

@RestrictTo(LIBRARY)
public class HereVisibleRegion implements VisibleRegion {

    private final @NonNull com.here.sdk.core.GeoBox mDelegate;

    private LatLng mNearLeft;
    private LatLng mNearRight;
    private LatLng mFarLeft;
    private LatLng mFarRight;
    private LatLngBounds mLatLngBounds;

    private HereVisibleRegion(@NonNull com.here.sdk.core.GeoBox delegate) {
        mDelegate = delegate;
    }

    public HereVisibleRegion(@NonNull LatLngBounds latLngBounds) {
        this(HereLatLngBounds.unwrap(latLngBounds));
    }

    @Override public @NonNull LatLng getNearLeft() {
        if (mNearLeft == null) {
            mNearLeft = HereLatLng.wrap(mDelegate.southWestCorner);
        }
        return mNearLeft;
    }

    @Override public @NonNull LatLng getNearRight() {
        if (mNearRight == null) {
            mNearRight = HereLatLng.wrap(mDelegate.northEastCorner);
        }
        return mNearRight;
    }

    @Override public @NonNull LatLng getFarLeft() {
        if (mFarLeft == null) {
            mFarLeft = HereLatLng.wrap(mDelegate.southWestCorner);
        }
        return mFarLeft;
    }

    @Override public @NonNull LatLng getFarRight() {
        if (mFarRight == null) {
            mFarRight = HereLatLng.wrap(mDelegate.northEastCorner);
        }
        return mFarRight;
    }

    @Override public @NonNull LatLngBounds getLatLngBounds() {
        if (mLatLngBounds == null) {
            mLatLngBounds = HereLatLngBounds.wrap(mDelegate);
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

        HereVisibleRegion that = (HereVisibleRegion) obj;

        return mDelegate.equals(that.mDelegate);
    }

    @Override public int hashCode() {
        return mDelegate.hashCode();
    }

    @Override public @NonNull String toString() {
        return mDelegate.toString();
    }


    static @NonNull VisibleRegion wrap(@NonNull com.here.sdk.core.GeoBox delegate) {
        return new HereVisibleRegion(delegate);
    }

}
