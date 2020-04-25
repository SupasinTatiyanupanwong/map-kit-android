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

import com.huawei.hms.maps.CameraUpdateFactory;

import me.tatiyanupanwong.supasin.android.libraries.kits.maps.model.CameraPosition;
import me.tatiyanupanwong.supasin.android.libraries.kits.maps.model.CameraUpdate;
import me.tatiyanupanwong.supasin.android.libraries.kits.maps.model.LatLng;
import me.tatiyanupanwong.supasin.android.libraries.kits.maps.model.LatLngBounds;

class HuaweiCameraUpdate implements CameraUpdate {

    static final Factory FACTORY = new Factory() {
        @NonNull
        @Override
        public CameraUpdate zoomIn() {
            return new HuaweiCameraUpdate(CameraUpdateFactory.zoomIn());
        }

        @NonNull
        @Override
        public CameraUpdate zoomOut() {
            return new HuaweiCameraUpdate(CameraUpdateFactory.zoomOut());
        }

        @NonNull
        @Override
        public CameraUpdate scrollBy(float xPixel, float yPixel) {
            return new HuaweiCameraUpdate(CameraUpdateFactory.scrollBy(xPixel, yPixel));
        }

        @NonNull
        @Override
        public CameraUpdate zoomTo(float zoom) {
            return new HuaweiCameraUpdate(CameraUpdateFactory.zoomTo(zoom));
        }

        @NonNull
        @Override
        public CameraUpdate zoomBy(float amount) {
            return new HuaweiCameraUpdate(CameraUpdateFactory.zoomBy(amount));
        }

        @NonNull
        @Override
        public CameraUpdate zoomBy(float amount, Point focus) {
            return new HuaweiCameraUpdate(CameraUpdateFactory.zoomBy(amount, focus));
        }

        @NonNull
        @Override
        public CameraUpdate newCameraPosition(CameraPosition cameraPosition) {
            return new HuaweiCameraUpdate(CameraUpdateFactory.newCameraPosition(
                    HuaweiCameraPosition.unwrap(cameraPosition)
            ));
        }

        @NonNull
        @Override
        public CameraUpdate newLatLng(@NonNull LatLng latLng) {
            return new HuaweiCameraUpdate(CameraUpdateFactory.newLatLng(
                    HuaweiLatLng.unwrap(latLng)
            ));
        }

        @NonNull
        @Override
        public CameraUpdate newLatLngZoom(@NonNull LatLng latLng, float zoom) {
            return new HuaweiCameraUpdate(CameraUpdateFactory.newLatLngZoom(
                    HuaweiLatLng.unwrap(latLng), zoom
            ));
        }

        @NonNull
        @Override
        public CameraUpdate newLatLngBounds(@NonNull LatLngBounds bounds, int padding) {
            return new HuaweiCameraUpdate(CameraUpdateFactory.newLatLngBounds(
                    HuaweiLatLngBounds.unwrap(bounds), padding
            ));
        }

        @NonNull
        @Override
        public CameraUpdate newLatLngBounds(
                @NonNull LatLngBounds bounds, int width, int height, int padding) {
            return new HuaweiCameraUpdate(CameraUpdateFactory.newLatLngBounds(
                    HuaweiLatLngBounds.unwrap(bounds), width, height, padding
            ));
        }
    };


    private final com.huawei.hms.maps.CameraUpdate mDelegate;

    private HuaweiCameraUpdate(com.huawei.hms.maps.CameraUpdate delegate) {
        mDelegate = delegate;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        HuaweiCameraUpdate that = (HuaweiCameraUpdate) obj;

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


    static com.huawei.hms.maps.CameraUpdate unwrap(CameraUpdate wrapped) {
        return ((HuaweiCameraUpdate) wrapped).mDelegate;
    }

}
