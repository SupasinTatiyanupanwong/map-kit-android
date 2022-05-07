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

import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.LatLng;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.LatLngBounds;

@RestrictTo(LIBRARY)
public class HuaweiLatLngBounds implements LatLngBounds {

    private final com.huawei.hms.maps.model.LatLngBounds mDelegate;

    private LatLng mSouthwest;
    private LatLng mNortheast;
    private LatLng mCenter;

    private HuaweiLatLngBounds(@NonNull com.huawei.hms.maps.model.LatLngBounds delegate) {
        mDelegate = delegate;
    }

    public HuaweiLatLngBounds(@NonNull LatLng southwest, @NonNull LatLng northeast) {
        this(new com.huawei.hms.maps.model.LatLngBounds(
                HuaweiLatLng.unwrap(southwest), HuaweiLatLng.unwrap(northeast)));
    }

    @Override
    public @NonNull LatLng getSouthwest() {
        if (mSouthwest == null) {
            mSouthwest = HuaweiLatLng.wrap(mDelegate.southwest);
        }
        return mSouthwest;
    }

    @Override
    public @NonNull LatLng getNortheast() {
        if (mNortheast == null) {
            mNortheast = HuaweiLatLng.wrap(mDelegate.northeast);
        }
        return mNortheast;
    }

    @Override
    public boolean contains(@NonNull LatLng point) {
        return mDelegate.contains(HuaweiLatLng.unwrap(point));
    }

    @Override
    public @NonNull LatLngBounds including(@NonNull LatLng point) {
        return HuaweiLatLngBounds.wrap(mDelegate.including(HuaweiLatLng.unwrap(point)));
    }

    @Override
    public @NonNull LatLng getCenter() {
        if (mCenter == null) {
            mCenter = HuaweiLatLng.wrap(mDelegate.getCenter());
        }
        return mCenter;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        HuaweiLatLngBounds that = (HuaweiLatLngBounds) obj;

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


    static LatLngBounds wrap(com.huawei.hms.maps.model.LatLngBounds delegate) {
        return new HuaweiLatLngBounds(delegate);
    }

    static @Nullable com.huawei.hms.maps.model.LatLngBounds unwrap(@Nullable LatLngBounds wrapped) {
        return wrapped == null ? null : ((HuaweiLatLngBounds) wrapped).mDelegate;
    }


    public static class Builder implements LatLngBounds.Builder {
        private final com.huawei.hms.maps.model.LatLngBounds.Builder mDelegate;

        public Builder() {
            mDelegate = com.huawei.hms.maps.model.LatLngBounds.builder();
        }

        @Override
        public @NonNull LatLngBounds.Builder include(@NonNull LatLng point) {
            mDelegate.include(HuaweiLatLng.unwrap(point));
            return this;
        }

        @Override
        public @NonNull LatLngBounds build() {
            return HuaweiLatLngBounds.wrap(mDelegate.build());
        }
    }

}
