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

package dev.supasintatiyanupanwong.libraries.android.kits.maps.internal.mapbox.model;

import static androidx.annotation.RestrictTo.Scope.LIBRARY;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;

import org.jetbrains.annotations.Contract;

import java.util.ArrayList;
import java.util.List;

import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.LatLng;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.LatLngBounds;
import kotlin.collections.CollectionsKt;

@RestrictTo(LIBRARY)
public class MapboxLatLngBounds implements LatLngBounds {

    private final @NonNull com.mapbox.maps.CoordinateBounds mDelegate;

    private LatLng mSouthwest;
    private LatLng mNortheast;
    private LatLng mCenter;

    private MapboxLatLngBounds(@NonNull com.mapbox.maps.CoordinateBounds delegate) {
        mDelegate = delegate;
    }

    public MapboxLatLngBounds(@NonNull LatLng southwest, @NonNull LatLng northeast) {
        this(new com.mapbox.maps.CoordinateBounds(
                MapboxLatLng.unwrap(southwest),
                MapboxLatLng.unwrap(northeast)
        ));
    }

    @Override public @NonNull LatLng getSouthwest() {
        if (mSouthwest == null) {
            mSouthwest = MapboxLatLng.wrap(mDelegate.getSouthwest());
        }
        return mSouthwest;
    }

    @Override public @NonNull LatLng getNortheast() {
        if (mNortheast == null) {
            mNortheast = MapboxLatLng.wrap(mDelegate.getNortheast());
        }
        return mNortheast;
    }

    @Override public boolean contains(@NonNull LatLng point) {
        return mDelegate.contains(MapboxLatLng.unwrap(point), false);
    }

    @Override public @NonNull LatLngBounds including(@NonNull LatLng point) {
        return MapboxLatLngBounds.wrap(mDelegate.extend(MapboxLatLng.unwrap(point)));
    }

    @Override public @NonNull LatLng getCenter() {
        if (mCenter == null) {
            mCenter = MapboxLatLng.wrap(mDelegate.center());
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

        MapboxLatLngBounds that = (MapboxLatLngBounds) obj;

        return mDelegate.equals(that.mDelegate);
    }

    @Override public int hashCode() {
        return mDelegate.hashCode();
    }

    @Override public @NonNull String toString() {
        return mDelegate.toString();
    }


    @Contract("null -> null; !null -> !null")
    static @Nullable LatLngBounds wrap(@Nullable com.mapbox.maps.CoordinateBounds delegate) {
        return delegate == null ? null : new MapboxLatLngBounds(delegate);
    }

    @Contract("null -> null; !null -> !null")
    static @Nullable com.mapbox.maps.CoordinateBounds unwrap(@Nullable LatLngBounds wrapped) {
        return wrapped == null ? null : ((MapboxLatLngBounds) wrapped).mDelegate;
    }


    public static class Builder implements LatLngBounds.Builder {
        private final @NonNull List<LatLng> mPoints = new ArrayList<>();

        public Builder() {}

        @Override public @NonNull LatLngBounds.Builder include(@NonNull LatLng point) {
            mPoints.add(point);
            return this;
        }

        @Override public @NonNull LatLngBounds build() {
            final @NonNull LatLng southwest;
            final @NonNull LatLng northeast;

            if (mPoints.isEmpty()) {
                southwest = new MapboxLatLng(0, 0);
                northeast = new MapboxLatLng(0, 0);
            } else {
                final LatLng southwestLatPoint = CollectionsKt
                        .minByOrNull(mPoints, LatLng::getLatitude);
                final LatLng southwestLngPoint = CollectionsKt
                        .minByOrNull(mPoints, LatLng::getLongitude);
                if (southwestLatPoint == null || southwestLngPoint == null) {
                    southwest = new MapboxLatLng(0, 0);
                } else {
                    southwest = new MapboxLatLng(
                            southwestLatPoint.getLatitude(),
                            southwestLngPoint.getLongitude()
                    );
                }

                final LatLng northeastLatPoint = CollectionsKt
                        .minByOrNull(mPoints, LatLng::getLatitude);
                final LatLng northeastLngPoint = CollectionsKt
                        .minByOrNull(mPoints, LatLng::getLongitude);
                if (northeastLatPoint == null || northeastLngPoint == null) {
                    northeast = new MapboxLatLng(0, 0);
                } else {
                    northeast = new MapboxLatLng(
                            northeastLatPoint.getLatitude(),
                            northeastLngPoint.getLongitude()
                    );
                }
            }

            return new MapboxLatLngBounds(southwest, northeast);
        }
    }

}
