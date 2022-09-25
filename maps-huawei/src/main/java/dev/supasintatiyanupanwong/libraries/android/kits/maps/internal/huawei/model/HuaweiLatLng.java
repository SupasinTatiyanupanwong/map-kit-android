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

package dev.supasintatiyanupanwong.libraries.android.kits.maps.internal.huawei.model;

import static androidx.annotation.RestrictTo.Scope.LIBRARY;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.LatLng;

@RestrictTo(LIBRARY)
public class HuaweiLatLng implements LatLng {

    private final com.huawei.hms.maps.model.LatLng mDelegate;

    private HuaweiLatLng(@NonNull com.huawei.hms.maps.model.LatLng delegate) {
        mDelegate = delegate;
    }

    public HuaweiLatLng(double latitude, double longitude) {
        this(new com.huawei.hms.maps.model.LatLng(latitude, longitude));
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

        HuaweiLatLng that = (HuaweiLatLng) obj;

        return mDelegate.equals(that.mDelegate);
    }

    @Override public int hashCode() {
        return mDelegate.hashCode();
    }

    @Override public @NonNull String toString() {
        return mDelegate.toString();
    }


    static LatLng wrap(com.huawei.hms.maps.model.LatLng delegate) {
        return new HuaweiLatLng(delegate);
    }

    static List<LatLng> wrap(Iterable<com.huawei.hms.maps.model.LatLng> delegates) {
        if (delegates == null) {
            return null;
        }

        Iterator<com.huawei.hms.maps.model.LatLng> iter = delegates.iterator();
        List<LatLng> list = new ArrayList<>();
        while (iter.hasNext()) {
            list.add(wrap(iter.next()));
        }
        return list;
    }

    static com.huawei.hms.maps.model.LatLng unwrap(LatLng wrapped) {
        return ((HuaweiLatLng) wrapped).mDelegate;
    }

    static List<com.huawei.hms.maps.model.LatLng> unwrap(Iterable<LatLng> wrappeds) {
        if (wrappeds == null) {
            return null;
        }

        Iterator<LatLng> iter = wrappeds.iterator();
        List<com.huawei.hms.maps.model.LatLng> list = new ArrayList<>();
        while (iter.hasNext()) {
            list.add(unwrap(iter.next()));
        }
        return list;
    }

}
