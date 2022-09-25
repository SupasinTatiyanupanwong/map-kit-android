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

import android.graphics.Point;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;

import com.huawei.hms.maps.CameraUpdateFactory;

import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.CameraPosition;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.CameraUpdate;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.LatLng;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.LatLngBounds;

@RestrictTo(LIBRARY)
public class HuaweiCameraUpdate implements CameraUpdate {

    public static final Factory FACTORY = new Factory() {
        @Override public @NonNull CameraUpdate zoomIn() {
            return new HuaweiCameraUpdate(CameraUpdateFactory.zoomIn());
        }

        @Override public @NonNull CameraUpdate zoomOut() {
            return new HuaweiCameraUpdate(CameraUpdateFactory.zoomOut());
        }

        @Override public @NonNull CameraUpdate scrollBy(float xPixel, float yPixel) {
            return new HuaweiCameraUpdate(CameraUpdateFactory.scrollBy(xPixel, yPixel));
        }

        @Override public @NonNull CameraUpdate zoomTo(float zoom) {
            return new HuaweiCameraUpdate(CameraUpdateFactory.zoomTo(zoom));
        }

        @Override public @NonNull CameraUpdate zoomBy(float amount) {
            return new HuaweiCameraUpdate(CameraUpdateFactory.zoomBy(amount));
        }

        @Override public @NonNull CameraUpdate zoomBy(float amount, @NonNull Point focus) {
            //noinspection ConstantConditions
            if (focus == null) {
                return new HuaweiCameraUpdate(CameraUpdateFactory.zoomBy(amount));
            } else {
                return new HuaweiCameraUpdate(CameraUpdateFactory.zoomBy(amount, focus));
            }
        }

        @Override public @NonNull CameraUpdate newCameraPosition(@NonNull CameraPosition cameraPosition) {
            return new HuaweiCameraUpdate(CameraUpdateFactory.newCameraPosition(
                    HuaweiCameraPosition.unwrap(cameraPosition)
            ));
        }

        @Override public @NonNull CameraUpdate newLatLng(@NonNull LatLng latLng) {
            return new HuaweiCameraUpdate(CameraUpdateFactory.newLatLng(
                    HuaweiLatLng.unwrap(latLng)
            ));
        }

        @Override public @NonNull CameraUpdate newLatLngZoom(@NonNull LatLng latLng, float zoom) {
            return new HuaweiCameraUpdate(CameraUpdateFactory.newLatLngZoom(
                    HuaweiLatLng.unwrap(latLng), zoom
            ));
        }

        @Override public @NonNull CameraUpdate newLatLngBounds(@NonNull LatLngBounds bounds, int padding) {
            return new HuaweiCameraUpdate(CameraUpdateFactory.newLatLngBounds(
                    HuaweiLatLngBounds.unwrap(bounds), padding
            ));
        }

        @Override public @NonNull CameraUpdate newLatLngBounds(
                @NonNull LatLngBounds bounds,
                int width,
                int height,
                int padding) {
            return new HuaweiCameraUpdate(CameraUpdateFactory.newLatLngBounds(
                    HuaweiLatLngBounds.unwrap(bounds), width, height, padding
            ));
        }
    };


    private final com.huawei.hms.maps.CameraUpdate mDelegate;

    private HuaweiCameraUpdate(com.huawei.hms.maps.CameraUpdate delegate) {
        mDelegate = delegate;
    }

    @Override public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        HuaweiCameraUpdate that = (HuaweiCameraUpdate) obj;

        return mDelegate.equals(that.mDelegate);
    }

    @Override public int hashCode() {
        return mDelegate.hashCode();
    }

    @Override public @NonNull String toString() {
        return mDelegate.toString();
    }


    static com.huawei.hms.maps.CameraUpdate unwrap(CameraUpdate wrapped) {
        return ((HuaweiCameraUpdate) wrapped).mDelegate;
    }

}
