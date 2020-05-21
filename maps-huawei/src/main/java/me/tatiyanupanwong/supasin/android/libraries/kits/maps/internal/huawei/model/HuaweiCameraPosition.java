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

import androidx.annotation.FloatRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import me.tatiyanupanwong.supasin.android.libraries.kits.maps.model.CameraPosition;
import me.tatiyanupanwong.supasin.android.libraries.kits.maps.model.LatLng;

class HuaweiCameraPosition implements CameraPosition {

    private final com.huawei.hms.maps.model.CameraPosition mDelegate;

    private LatLng mTarget;

    private HuaweiCameraPosition(com.huawei.hms.maps.model.CameraPosition delegate) {
        mDelegate = delegate;
    }

    @NonNull
    @Override
    public LatLng getTarget() {
        if (mTarget == null) {
            mTarget = HuaweiLatLng.wrap(mDelegate.target);
        }
        return mTarget;
    }

    @Override
    public float getZoom() {
        return mDelegate.zoom;
    }

    @Override
    public float getTilt() {
        return mDelegate.tilt;
    }

    @Override
    public float getBearing() {
        return mDelegate.bearing;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        HuaweiCameraPosition that = (HuaweiCameraPosition) obj;

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


    static CameraPosition wrap(com.huawei.hms.maps.model.CameraPosition delegate) {
        return new HuaweiCameraPosition(delegate);
    }

    static com.huawei.hms.maps.model.CameraPosition unwrap(CameraPosition wrapped) {
        return ((HuaweiCameraPosition) wrapped).mDelegate;
    }


    static class Builder implements CameraPosition.Builder {
        private final com.huawei.hms.maps.model.CameraPosition.Builder mDelegate;

        Builder() {
            mDelegate = com.huawei.hms.maps.model.CameraPosition.builder();
        }

        Builder(CameraPosition camera) {
            mDelegate = com.huawei.hms.maps.model.CameraPosition.builder(
                    HuaweiCameraPosition.unwrap(camera));
        }

        @NonNull
        @Override
        public CameraPosition.Builder target(@NonNull LatLng location) {
            mDelegate.target(HuaweiLatLng.unwrap(location));
            return this;
        }

        @NonNull
        @Override
        public CameraPosition.Builder zoom(float zoom) {
            mDelegate.zoom(zoom);
            return this;
        }

        @NonNull
        @Override
        public CameraPosition.Builder tilt(@FloatRange(from = 0.0, to = 90.0) float tilt) {
            mDelegate.tilt(tilt);
            return this;
        }

        @NonNull
        @Override
        public CameraPosition.Builder bearing(float bearing) {
            mDelegate.bearing(bearing);
            return this;
        }

        @NonNull
        @Override
        public CameraPosition build() {
            return HuaweiCameraPosition.wrap(mDelegate.build());
        }
    }

}
