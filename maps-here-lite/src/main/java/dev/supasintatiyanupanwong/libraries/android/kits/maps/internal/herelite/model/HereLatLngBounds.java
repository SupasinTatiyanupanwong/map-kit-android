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

import org.jetbrains.annotations.Contract;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.LatLng;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.LatLngBounds;

@RestrictTo(LIBRARY)
public class HereLatLngBounds implements LatLngBounds {

    private final @NonNull LatLng mSouthwest;
    private final @NonNull LatLng mNortheast;
    private final @NonNull LatLng mCenter;

    private HereLatLngBounds(@NonNull com.here.sdk.core.GeoBox delegate) {
        this(HereLatLng.wrap(delegate.southWestCorner), HereLatLng.wrap(delegate.northEastCorner));
    }

    public HereLatLngBounds(@NonNull LatLng southwest, @NonNull LatLng northeast) {
        mSouthwest = southwest;
        mNortheast = northeast;
        mCenter = new HereLatLng(
                (southwest.getLatitude() + northeast.getLatitude()) * 0.5,
                (southwest.getLongitude() + northeast.getLongitude()) * 0.5
        );
    }

    @Override public @NonNull LatLng getSouthwest() {
        return mSouthwest;
    }

    @Override public @NonNull LatLng getNortheast() {
        return mNortheast;
    }

    @Override public boolean contains(@NonNull LatLng point) {
        return point.getLatitude() >= mSouthwest.getLatitude()
                && point.getLongitude() >= mSouthwest.getLongitude()
                && point.getLatitude() <= mNortheast.getLatitude()
                && point.getLongitude() <= mNortheast.getLongitude();
    }

    @Override public @NonNull LatLngBounds including(@NonNull LatLng point) {
        return new Builder()
                .include(mSouthwest)
                .include(mNortheast)
                .include(point)
                .build();
    }

    @Override public @NonNull LatLng getCenter() {
        return mCenter;
    }

    @Override public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        HereLatLngBounds that = (HereLatLngBounds) obj;

        return mSouthwest.equals(that.mSouthwest) && mNortheast.equals(that.mNortheast);
    }

    @Override public int hashCode() {
        return mSouthwest.hashCode() * 31 + mNortheast.hashCode();
    }

    @Override public @NonNull String toString() {
        return "HereLatLngBounds(southwest=" + mSouthwest + ", northeast=" + mNortheast + ')';
    }


    static LatLngBounds wrap(com.here.sdk.core.GeoBox delegate) {
        return new HereLatLngBounds(delegate);
    }

    @Contract("null -> null; !null -> !null")
    static @Nullable com.here.sdk.core.GeoBox unwrap(@Nullable LatLngBounds wrapped) {
        return wrapped == null ? null : new com.here.sdk.core.GeoBox(
                HereLatLng.unwrap(wrapped.getSouthwest()),
                HereLatLng.unwrap(wrapped.getNortheast())
        );
    }


    public static class Builder implements LatLngBounds.Builder {
        private final @NonNull List<LatLng> mPoints = new ArrayList<>();

        public Builder() {}

        @Override public @NonNull LatLngBounds.Builder include(@NonNull LatLng point) {
            mPoints.add(point);
            return this;
        }

        @Override public @NonNull LatLngBounds build() {
            final double southwestLat = Collections
                    .min(mPoints, (o1, o2) -> Double.compare(o1.getLatitude(), o2.getLatitude()))
                    .getLatitude();
            final double southwestLng = Collections
                    .min(mPoints, (o1, o2) -> Double.compare(o1.getLongitude(), o2.getLongitude()))
                    .getLongitude();
            final @NonNull LatLng southwest = new HereLatLng(southwestLat, southwestLng);

            final double northeastLat = Collections
                    .max(mPoints, (o1, o2) -> Double.compare(o1.getLatitude(), o2.getLatitude()))
                    .getLatitude();
            final double northeastLng = Collections
                    .max(mPoints, (o1, o2) -> Double.compare(o1.getLongitude(), o2.getLongitude()))
                    .getLongitude();
            final @NonNull LatLng northeast = new HereLatLng(northeastLat, northeastLng);

            return wrap(
                    new com.here.sdk.core.GeoBox(
                            HereLatLng.unwrap(southwest),
                            HereLatLng.unwrap(northeast)
                    )
            );
        }
    }

}
