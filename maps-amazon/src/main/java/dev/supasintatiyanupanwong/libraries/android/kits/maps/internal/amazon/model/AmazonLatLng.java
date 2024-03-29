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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.LatLng;

@RestrictTo(LIBRARY)
public class AmazonLatLng implements LatLng {

    private final @NonNull com.amazon.geo.mapsv2.model.LatLng mDelegate;

    private AmazonLatLng(@NonNull com.amazon.geo.mapsv2.model.LatLng delegate) {
        mDelegate = delegate;
    }

    public AmazonLatLng(double latitude, double longitude) {
        this(new com.amazon.geo.mapsv2.model.LatLng(latitude, longitude));
    }

    @Override public double getLatitude() {
        return mDelegate.latitude;
    }

    @Override public double getLongitude() {
        return mDelegate.longitude;
    }

    @Override public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        AmazonLatLng that = (AmazonLatLng) obj;

        return mDelegate.equals(that.mDelegate);
    }

    @Override public int hashCode() {
        return mDelegate.hashCode();
    }

    @Override public @NonNull String toString() {
        return mDelegate.toString();
    }


    static @Nullable LatLng wrap(@Nullable com.amazon.geo.mapsv2.model.LatLng delegate) {
        return delegate == null ? null : new AmazonLatLng(delegate);
    }

    static @Nullable List<LatLng> wrap(
            @Nullable Iterable<com.amazon.geo.mapsv2.model.LatLng> delegates
    ) {
        if (delegates == null) {
            return null;
        }

        Iterator<com.amazon.geo.mapsv2.model.LatLng> iter = delegates.iterator();
        if (!iter.hasNext()) {
            return Collections.emptyList();
        }

        List<LatLng> list = new ArrayList<>();
        while (iter.hasNext()) {
            final @Nullable LatLng wrapped = wrap(iter.next());
            if (wrapped != null) {
                list.add(wrapped);
            }
        }
        return list;
    }

    static @Nullable com.amazon.geo.mapsv2.model.LatLng unwrap(@Nullable LatLng wrapped) {
        return wrapped == null ? null : ((AmazonLatLng) wrapped).mDelegate;
    }

    static @Nullable List<com.amazon.geo.mapsv2.model.LatLng> unwrap(
            @Nullable Iterable<LatLng> wrappeds
    ) {
        if (wrappeds == null) {
            return null;
        }

        Iterator<LatLng> iter = wrappeds.iterator();
        if (!iter.hasNext()) {
            return Collections.emptyList();
        }

        List<com.amazon.geo.mapsv2.model.LatLng> list = new ArrayList<>();
        while (iter.hasNext()) {
            final @Nullable com.amazon.geo.mapsv2.model.LatLng unwrapped = unwrap(iter.next());
            if (unwrapped != null) {
                list.add(unwrapped);
            }
        }
        return list;
    }
}
