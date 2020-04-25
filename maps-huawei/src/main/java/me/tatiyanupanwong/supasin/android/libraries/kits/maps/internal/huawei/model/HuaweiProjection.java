/*
 * Copyright (C) 2020 Supasin Tatiyanupanwong
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package me.tatiyanupanwong.supasin.android.libraries.kits.maps.internal.huawei.model;

import android.graphics.Point;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import me.tatiyanupanwong.supasin.android.libraries.kits.maps.model.LatLng;
import me.tatiyanupanwong.supasin.android.libraries.kits.maps.model.Projection;
import me.tatiyanupanwong.supasin.android.libraries.kits.maps.model.VisibleRegion;

class HuaweiProjection implements Projection {

    private final com.huawei.hms.maps.Projection mDelegate;

    private HuaweiProjection(com.huawei.hms.maps.Projection delegate) {
        mDelegate = delegate;
    }

    @Nullable
    @Override
    public LatLng fromScreenLocation(Point point) {
        return HuaweiLatLng.wrap(mDelegate.fromScreenLocation(point));
    }

    @NonNull
    @Override
    public Point toScreenLocation(LatLng location) {
        return mDelegate.toScreenLocation(HuaweiLatLng.unwrap(location));
    }

    @NonNull
    @Override
    public VisibleRegion getVisibleRegion() {
        return HuaweiVisibleRegion.wrap(mDelegate.getVisibleRegion());
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        HuaweiProjection that = (HuaweiProjection) obj;

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


    static Projection wrap(com.huawei.hms.maps.Projection delegate) {
        return new HuaweiProjection(delegate);
    }

}
