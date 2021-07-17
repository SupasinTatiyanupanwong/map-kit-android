/*
 * Copyright 2021 Supasin Tatiyanupanwong
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

package dev.supasintatiyanupanwong.libraries.android.kits.maps.internal.amazon.model;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.LatLng;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.LatLngBounds;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.VisibleRegion;

class AmazonVisibleRegion implements VisibleRegion {

    private final com.amazon.geo.mapsv2.model.VisibleRegion mDelegate;

    private LatLng mNearLeft;
    private LatLng mNearRight;
    private LatLng mFarLeft;
    private LatLng mFarRight;
    private LatLngBounds mLatLngBounds;

    private AmazonVisibleRegion(com.amazon.geo.mapsv2.model.VisibleRegion delegate) {
        mDelegate = delegate;
    }

    AmazonVisibleRegion(
            LatLng nearLeft,
            LatLng nearRight,
            LatLng farLeft,
            LatLng farRight,
            LatLngBounds latLngBounds) {
        this(new com.amazon.geo.mapsv2.model.VisibleRegion(
                AmazonLatLng.unwrap(nearLeft),
                AmazonLatLng.unwrap(nearRight),
                AmazonLatLng.unwrap(farLeft),
                AmazonLatLng.unwrap(farRight),
                AmazonLatLngBounds.unwrap(latLngBounds)));
    }

    @Override
    public @NonNull LatLng getNearLeft() {
        if (mNearLeft == null) {
            mNearLeft = AmazonLatLng.wrap(mDelegate.nearLeft);
        }
        return mNearLeft;
    }

    @Override
    public @NonNull LatLng getNearRight() {
        if (mNearRight == null) {
            mNearRight = AmazonLatLng.wrap(mDelegate.nearRight);
        }
        return mNearRight;
    }

    @Override
    public @NonNull LatLng getFarLeft() {
        if (mFarLeft == null) {
            mFarLeft = AmazonLatLng.wrap(mDelegate.farLeft);
        }
        return mFarLeft;
    }

    @Override
    public @NonNull LatLng getFarRight() {
        if (mFarRight == null) {
            mFarRight = AmazonLatLng.wrap(mDelegate.farRight);
        }
        return mFarRight;
    }

    @Override
    public @NonNull LatLngBounds getLatLngBounds() {
        if (mLatLngBounds == null) {
            mLatLngBounds = AmazonLatLngBounds.wrap(mDelegate.latLngBounds);
        }
        return mLatLngBounds;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        AmazonVisibleRegion that = (AmazonVisibleRegion) obj;

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


    static VisibleRegion wrap(com.amazon.geo.mapsv2.model.VisibleRegion delegate) {
        return new AmazonVisibleRegion(delegate);
    }

}
