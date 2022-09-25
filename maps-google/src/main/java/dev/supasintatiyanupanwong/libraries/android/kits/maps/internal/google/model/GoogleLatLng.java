/*
 * Copyright 2020 Supasin Tatiyanupanwong
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

package dev.supasintatiyanupanwong.libraries.android.kits.maps.internal.google.model;

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
public class GoogleLatLng implements LatLng {

    private final @NonNull com.google.android.gms.maps.model.LatLng mDelegate;

    private GoogleLatLng(@NonNull com.google.android.gms.maps.model.LatLng delegate) {
        mDelegate = delegate;
    }

    public GoogleLatLng(double latitude, double longitude) {
        this(new com.google.android.gms.maps.model.LatLng(latitude, longitude));
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

        GoogleLatLng that = (GoogleLatLng) obj;

        return mDelegate.equals(that.mDelegate);
    }

    @Override public int hashCode() {
        return mDelegate.hashCode();
    }

    @Override public @NonNull String toString() {
        return mDelegate.toString();
    }


    static @Nullable LatLng wrap(@Nullable com.google.android.gms.maps.model.LatLng delegate) {
        return delegate == null ? null : new GoogleLatLng(delegate);
    }

    static @Nullable List<LatLng> wrap(
            @Nullable Iterable<com.google.android.gms.maps.model.LatLng> delegates) {
        if (delegates == null) {
            return null;
        }

        Iterator<com.google.android.gms.maps.model.LatLng> iter = delegates.iterator();
        if (!iter.hasNext()) {
            return Collections.emptyList();
        }

        List<LatLng> list = new ArrayList<>();
        while (iter.hasNext()) {
            list.add(wrap(iter.next()));
        }
        return list;
    }

    static @Nullable com.google.android.gms.maps.model.LatLng unwrap(@Nullable LatLng wrapped) {
        return wrapped == null ? null : ((GoogleLatLng) wrapped).mDelegate;
    }

    static @Nullable List<com.google.android.gms.maps.model.LatLng> unwrap(
            @Nullable Iterable<LatLng> wrappeds) {
        if (wrappeds == null) {
            return null;
        }

        Iterator<LatLng> iter = wrappeds.iterator();
        if (!iter.hasNext()) {
            return Collections.emptyList();
        }

        List<com.google.android.gms.maps.model.LatLng> list = new ArrayList<>();
        while (iter.hasNext()) {
            final @Nullable com.google.android.gms.maps.model.LatLng unwrapped =
                    unwrap(iter.next());
            if (unwrapped != null) {
                list.add(unwrapped);
            }
        }
        return list;
    }

}
