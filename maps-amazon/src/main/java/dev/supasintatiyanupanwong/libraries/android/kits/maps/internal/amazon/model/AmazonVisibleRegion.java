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

import static androidx.annotation.RestrictTo.Scope.LIBRARY;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;

import java.util.Objects;

import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.LatLng;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.LatLngBounds;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.VisibleRegion;

@RestrictTo(LIBRARY)
public class AmazonVisibleRegion implements VisibleRegion {

    private final @NonNull com.amazon.geo.mapsv2.model.VisibleRegion mDelegate;

    private @Nullable LatLng mNearLeft;
    private @Nullable LatLng mNearRight;
    private @Nullable LatLng mFarLeft;
    private @Nullable LatLng mFarRight;
    private @Nullable LatLngBounds mBounds;

    private AmazonVisibleRegion(@NonNull com.amazon.geo.mapsv2.model.VisibleRegion delegate) {
        mDelegate = delegate;
    }

    public AmazonVisibleRegion(
            @NonNull LatLng nearLeft,
            @NonNull LatLng nearRight,
            @NonNull LatLng farLeft,
            @NonNull LatLng farRight,
            @NonNull LatLngBounds bounds
    ) {
        mDelegate = new com.amazon.geo.mapsv2.model.VisibleRegion(
                Objects.requireNonNull(AmazonLatLng.unwrap(nearLeft), "nearLeft == null"),
                Objects.requireNonNull(AmazonLatLng.unwrap(nearRight), "nearRight == null"),
                Objects.requireNonNull(AmazonLatLng.unwrap(farLeft), "farLeft == null"),
                Objects.requireNonNull(AmazonLatLng.unwrap(farRight), "farRight == null"),
                Objects.requireNonNull(AmazonLatLngBounds.unwrap(bounds), "bounds == null")
        );
    }

    @Override public @NonNull LatLng getNearLeft() {
        if (mNearLeft == null) {
            mNearLeft = AmazonLatLng.wrap(mDelegate.nearLeft);
        }
        return mNearLeft;
    }

    @Override public @NonNull LatLng getNearRight() {
        if (mNearRight == null) {
            mNearRight = AmazonLatLng.wrap(mDelegate.nearRight);
        }
        return mNearRight;
    }

    @Override public @NonNull LatLng getFarLeft() {
        if (mFarLeft == null) {
            mFarLeft = AmazonLatLng.wrap(mDelegate.farLeft);
        }
        return mFarLeft;
    }

    @Override public @NonNull LatLng getFarRight() {
        if (mFarRight == null) {
            mFarRight = AmazonLatLng.wrap(mDelegate.farRight);
        }
        return mFarRight;
    }

    @Override public @NonNull LatLngBounds getLatLngBounds() {
        if (mBounds == null) {
            mBounds = AmazonLatLngBounds.wrap(mDelegate.latLngBounds);
        }
        return mBounds;
    }

    @Override public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        AmazonVisibleRegion that = (AmazonVisibleRegion) obj;

        return mDelegate.equals(that.mDelegate);
    }

    @Override public int hashCode() {
        return mDelegate.hashCode();
    }

    @Override public @NonNull String toString() {
        return mDelegate.toString();
    }


    static @NonNull VisibleRegion wrap(
            @NonNull com.amazon.geo.mapsv2.model.VisibleRegion delegate
    ) {
        return new AmazonVisibleRegion(delegate);
    }
}
