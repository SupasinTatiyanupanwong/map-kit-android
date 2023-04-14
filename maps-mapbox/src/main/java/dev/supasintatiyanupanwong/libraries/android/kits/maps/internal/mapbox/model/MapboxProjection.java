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

import android.graphics.Point;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.arch.core.util.Function;

import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.LatLng;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.Projection;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.VisibleRegion;

public class MapboxProjection implements Projection {

    private final @NonNull Function<Point, LatLng> mFromScreenLocationImplFunction;
    private final @NonNull Function<LatLng, Point> mToScreenLocationImplFunction;
    private final @NonNull Function<Void, VisibleRegion> mGetVisibleRegionImplFunction;

    MapboxProjection(
            final @NonNull Function<Point, LatLng> fromScreenLocationImplFunction,
            final @NonNull Function<LatLng, Point> toScreenLocationImplFunction,
            final @NonNull Function<Void, VisibleRegion> getVisibleRegionImplFunction
    ) {
        mFromScreenLocationImplFunction = fromScreenLocationImplFunction;
        mToScreenLocationImplFunction = toScreenLocationImplFunction;
        mGetVisibleRegionImplFunction = getVisibleRegionImplFunction;
    }

    @Override public @Nullable LatLng fromScreenLocation(Point point) {
        return mFromScreenLocationImplFunction.apply(point);
    }

    @Override public @NonNull Point toScreenLocation(LatLng location) {
        return mToScreenLocationImplFunction.apply(location);
    }

    @Override public @NonNull VisibleRegion getVisibleRegion() {
        return mGetVisibleRegionImplFunction.apply(null);
    }


    @Override public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        MapboxProjection that = (MapboxProjection) obj;

        return getVisibleRegion().equals(that.getVisibleRegion());
    }

    @Override public int hashCode() {
        return getVisibleRegion().hashCode();
    }

    @Override public @NonNull String toString() {
        return getVisibleRegion().toString();
    }

}
