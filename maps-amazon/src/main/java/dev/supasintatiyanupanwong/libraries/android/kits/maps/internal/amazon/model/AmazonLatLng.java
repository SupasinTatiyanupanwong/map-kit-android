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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.LatLng;

class AmazonLatLng implements LatLng {

    private final com.amazon.geo.mapsv2.model.LatLng mDelegate;

    private AmazonLatLng(@NonNull com.amazon.geo.mapsv2.model.LatLng delegate) {
        mDelegate = delegate;
    }

    AmazonLatLng(double latitude, double longitude) {
        this(new com.amazon.geo.mapsv2.model.LatLng(latitude, longitude));
    }

    @Override
    public double getLatitude() {
        return mDelegate.latitude;
    }

    @Override
    public double getLongitude() {
        return mDelegate.longitude;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        AmazonLatLng that = (AmazonLatLng) obj;

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


    static LatLng wrap(com.amazon.geo.mapsv2.model.LatLng delegate) {
        return new AmazonLatLng(delegate);
    }

    static List<LatLng> wrap(Iterable<com.amazon.geo.mapsv2.model.LatLng> delegates) {
        if (delegates == null) {
            return null;
        }

        Iterator<com.amazon.geo.mapsv2.model.LatLng> iter = delegates.iterator();
        List<LatLng> list = new ArrayList<>();
        while (iter.hasNext()) {
            list.add(wrap(iter.next()));
        }
        return list;
    }

    static com.amazon.geo.mapsv2.model.LatLng unwrap(LatLng wrapped) {
        return ((AmazonLatLng) wrapped).mDelegate;
    }

    static List<com.amazon.geo.mapsv2.model.LatLng> unwrap(Iterable<LatLng> wrappeds) {
        if (wrappeds == null) {
            return null;
        }

        Iterator<LatLng> iter = wrappeds.iterator();
        List<com.amazon.geo.mapsv2.model.LatLng> list = new ArrayList<>();
        while (iter.hasNext()) {
            list.add(unwrap(iter.next()));
        }
        return list;
    }

}
