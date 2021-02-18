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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.LatLng;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.LatLngBounds;

class HuaweiLatLngBounds implements LatLngBounds {

    private final com.huawei.hms.maps.model.LatLngBounds mDelegate;

    private LatLng mSouthwest;
    private LatLng mNortheast;
    private LatLng mCenter;

    private HuaweiLatLngBounds(@NonNull com.huawei.hms.maps.model.LatLngBounds delegate) {
        mDelegate = delegate;
    }

    HuaweiLatLngBounds(@NonNull LatLng southwest, @NonNull LatLng northeast) {
        this(new com.huawei.hms.maps.model.LatLngBounds(
                HuaweiLatLng.unwrap(southwest), HuaweiLatLng.unwrap(northeast)));
    }

    @NonNull
    @Override
    public LatLng getSouthwest() {
        if (mSouthwest == null) {
            mSouthwest = HuaweiLatLng.wrap(mDelegate.southwest);
        }
        return mSouthwest;
    }

    @NonNull
    @Override
    public LatLng getNortheast() {
        if (mNortheast == null) {
            mNortheast = HuaweiLatLng.wrap(mDelegate.northeast);
        }
        return mNortheast;
    }

    @Override
    public boolean contains(@NonNull LatLng point) {
        return mDelegate.contains(HuaweiLatLng.unwrap(point));
    }

    @NonNull
    @Override
    public LatLngBounds including(@NonNull LatLng point) {
        return HuaweiLatLngBounds.wrap(mDelegate.including(HuaweiLatLng.unwrap(point)));
    }

    @NonNull
    @Override
    public LatLng getCenter() {
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

    @NonNull
    @Override
    public String toString() {
        return mDelegate.toString();
    }


    static LatLngBounds wrap(com.huawei.hms.maps.model.LatLngBounds delegate) {
        return new HuaweiLatLngBounds(delegate);
    }

    @Nullable
    static com.huawei.hms.maps.model.LatLngBounds unwrap(@Nullable LatLngBounds wrapped) {
        return wrapped == null ? null : ((HuaweiLatLngBounds) wrapped).mDelegate;
    }


    static class Builder implements LatLngBounds.Builder {
        private final com.huawei.hms.maps.model.LatLngBounds.Builder mDelegate;

        Builder() {
            mDelegate = com.huawei.hms.maps.model.LatLngBounds.builder();
        }

        @NonNull
        @Override
        public LatLngBounds.Builder include(@NonNull LatLng point) {
            mDelegate.include(HuaweiLatLng.unwrap(point));
            return this;
        }

        @NonNull
        @Override
        public LatLngBounds build() {
            return HuaweiLatLngBounds.wrap(mDelegate.build());
        }
    }

}
