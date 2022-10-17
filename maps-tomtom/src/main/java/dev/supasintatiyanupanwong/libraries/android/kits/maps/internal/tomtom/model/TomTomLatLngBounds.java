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

import com.tomtom.sdk.common.location.GeoBoundingBox;
import com.tomtom.sdk.common.location.GeoCoordinate;

import org.jetbrains.annotations.Contract;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.LatLng;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.LatLngBounds;
import kotlin.collections.CollectionsKt;

@RestrictTo(LIBRARY)
public class TomTomLatLngBounds implements LatLngBounds {

    private final @NonNull LatLng mSouthwest;
    private final @NonNull LatLng mNortheast;
    private LatLng mCenter;

    private TomTomLatLngBounds(@NonNull com.tomtom.sdk.common.location.GeoBoundingBox delegate) {
        mSouthwest = new TomTomLatLng(
                delegate.getBottomRight().getLatitude(),
                delegate.getTopLeft().getLongitude()
        );
        mNortheast = new TomTomLatLng(
                delegate.getTopLeft().getLatitude(),
                delegate.getBottomRight().getLongitude()
        );
    }

    private TomTomLatLngBounds(@NonNull com.tomtom.sdk.common.location.GeoBounds delegate) {
        final @NonNull List<GeoCoordinate> coords = delegate.getCoordinates();
        if (coords.isEmpty()) {
            mSouthwest = new TomTomLatLng(0, 0);
            mNortheast = new TomTomLatLng(0, 0);
        } else {
            final double southwestLat = CollectionsKt
                    .minByOrThrow(coords, GeoCoordinate::getLatitude)
                    .getLatitude();
            final double southwestLng = CollectionsKt
                    .minByOrThrow(coords, GeoCoordinate::getLongitude)
                    .getLongitude();
            mSouthwest = new TomTomLatLng(southwestLat, southwestLng);

            final double northeastLat = CollectionsKt
                    .maxByOrThrow(coords, GeoCoordinate::getLatitude)
                    .getLatitude();
            final double northeastLng = CollectionsKt
                    .maxByOrThrow(coords, GeoCoordinate::getLongitude)
                    .getLongitude();
            mNortheast = new TomTomLatLng(northeastLat, northeastLng);
        }
    }

    public TomTomLatLngBounds(@NonNull LatLng southwest, @NonNull LatLng northeast) {
        mSouthwest = southwest;
        mNortheast = northeast;
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
        return wrapGeoBounds(
                new com.tomtom.sdk.common.location.GeoBounds(
                        Arrays.asList(
                                TomTomLatLng.unwrap(mSouthwest),
                                TomTomLatLng.unwrap(mNortheast),
                                TomTomLatLng.unwrap(point)
                        )
                )
        );
    }

    @Override public @NonNull LatLng getCenter() {
        if (mCenter == null) {
            mCenter = new TomTomLatLng(
                    (mSouthwest.getLatitude() + mNortheast.getLatitude()) * 0.5,
                    (mSouthwest.getLongitude() + mNortheast.getLongitude()) * 0.5
            );
        }
        return mCenter;
    }

    @Override public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        TomTomLatLngBounds that = (TomTomLatLngBounds) obj;

        return mSouthwest.equals(that.mSouthwest) && mNortheast.equals(that.mNortheast);
    }

    @Override public int hashCode() {
        return mSouthwest.hashCode() * 31 + mNortheast.hashCode();
    }

    @Override public @NonNull String toString() {
        return "TomTomLatLngBounds(southwest=" + mSouthwest + ", northeast=" + mNortheast + ')';
    }


    static LatLngBounds wrapGeoBoundingBox(com.tomtom.sdk.common.location.GeoBoundingBox delegate) {
        return new TomTomLatLngBounds(delegate);
    }

    static LatLngBounds wrapGeoBounds(com.tomtom.sdk.common.location.GeoBounds delegate) {
        return new TomTomLatLngBounds(delegate);
    }

    @Contract("null -> null; !null -> !null")
    static @Nullable com.tomtom.sdk.common.location.GeoBoundingBox unwrapToGeoBoundingBox(
            @Nullable LatLngBounds wrapped
    ) {
        return wrapped == null ? null : GeoBoundingBox.fromCoordinates(
                Arrays.asList(
                        TomTomLatLng.unwrap(wrapped.getSouthwest()),
                        TomTomLatLng.unwrap(wrapped.getNortheast())
                )
        );
    }

    @Contract("null -> null; !null -> !null")
    static @Nullable com.tomtom.sdk.common.location.GeoBounds unwrapToGeoBounds(
            @Nullable LatLngBounds wrapped
    ) {
        return wrapped == null ? null : new com.tomtom.sdk.common.location.GeoBounds(
                Arrays.asList(
                        TomTomLatLng.unwrap(wrapped.getSouthwest()),
                        TomTomLatLng.unwrap(wrapped.getNortheast())
                )
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
            return wrapGeoBounds(
                    new com.tomtom.sdk.common.location.GeoBounds(TomTomLatLng.unwrap(mPoints))
            );
        }
    }

}
