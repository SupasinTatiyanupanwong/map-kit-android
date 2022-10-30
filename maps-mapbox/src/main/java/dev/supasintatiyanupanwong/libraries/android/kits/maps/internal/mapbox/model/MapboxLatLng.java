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
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.LatLng;

@RestrictTo(LIBRARY)
public class MapboxLatLng implements LatLng {

    private final @NonNull com.mapbox.geojson.Point mDelegate;

    private MapboxLatLng(@NonNull com.mapbox.geojson.Point delegate) {
        mDelegate = delegate;
    }

    public MapboxLatLng(double latitude, double longitude) {
        this(com.mapbox.geojson.Point.fromLngLat(longitude, latitude));
    }

    @Override public double getLatitude() {
        return mDelegate.latitude();
    }

    @Override public double getLongitude() {
        return mDelegate.longitude();
    }

    @Override public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        MapboxLatLng that = (MapboxLatLng) obj;

        return mDelegate.equals(that.mDelegate);
    }

    @Override public int hashCode() {
        return mDelegate.hashCode();
    }

    @Override public @NonNull String toString() {
        return mDelegate.toString();
    }


    @Contract("null -> null; !null -> !null")
    static @Nullable LatLng wrap(@Nullable com.mapbox.geojson.Point delegate) {
        return delegate == null ? null : new MapboxLatLng(delegate);
    }

    @Contract("null -> null; !null -> !null")
    static @Nullable List<LatLng> wrap(@Nullable Iterable<com.mapbox.geojson.Point> delegates) {
        if (delegates == null) {
            return null;
        }

        Iterator<com.mapbox.geojson.Point> iter = delegates.iterator();
        if (!iter.hasNext()) {
            return Collections.emptyList();
        }

        List<LatLng> list = new ArrayList<>();
        while (iter.hasNext()) {
            list.add(wrap(iter.next()));
        }
        return list;
    }

    @Contract("null -> null; !null -> !null")
    static @Nullable com.mapbox.geojson.Point unwrap(@Nullable LatLng wrapped) {
        return wrapped == null ? null : ((MapboxLatLng) wrapped).mDelegate;
    }

    @Contract("null -> null; !null -> !null")
    static @Nullable List<com.mapbox.geojson.Point> unwrap(@Nullable Iterable<LatLng> wrappeds) {
        if (wrappeds == null) {
            return null;
        }

        Iterator<LatLng> iter = wrappeds.iterator();
        if (!iter.hasNext()) {
            return Collections.emptyList();
        }

        List<com.mapbox.geojson.Point> list = new ArrayList<>();
        while (iter.hasNext()) {
            final @Nullable com.mapbox.geojson.Point unwrapped = unwrap(iter.next());
            if (unwrapped != null) {
                list.add(unwrapped);
            }
        }
        return list;
    }

}
