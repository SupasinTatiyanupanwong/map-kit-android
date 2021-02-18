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

import android.graphics.Point;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.maps.CameraUpdateFactory;

import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.CameraPosition;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.CameraUpdate;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.LatLng;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.LatLngBounds;

class GoogleCameraUpdate implements CameraUpdate {

    static final CameraUpdate.Factory FACTORY = new CameraUpdate.Factory() {
        @NonNull
        @Override
        public CameraUpdate zoomIn() {
            return new GoogleCameraUpdate(CameraUpdateFactory.zoomIn());
        }

        @NonNull
        @Override
        public CameraUpdate zoomOut() {
            return new GoogleCameraUpdate(CameraUpdateFactory.zoomOut());
        }

        @NonNull
        @Override
        public CameraUpdate scrollBy(float xPixel, float yPixel) {
            return new GoogleCameraUpdate(CameraUpdateFactory.scrollBy(xPixel, yPixel));
        }

        @NonNull
        @Override
        public CameraUpdate zoomTo(float zoom) {
            return new GoogleCameraUpdate(CameraUpdateFactory.zoomTo(zoom));
        }

        @NonNull
        @Override
        public CameraUpdate zoomBy(float amount) {
            return new GoogleCameraUpdate(CameraUpdateFactory.zoomBy(amount));
        }

        @NonNull
        @Override
        public CameraUpdate zoomBy(float amount, Point focus) {
            return new GoogleCameraUpdate(CameraUpdateFactory.zoomBy(amount, focus));
        }

        @NonNull
        @Override
        public CameraUpdate newCameraPosition(CameraPosition cameraPosition) {
            return new GoogleCameraUpdate(CameraUpdateFactory.newCameraPosition(
                    GoogleCameraPosition.unwrap(cameraPosition)
            ));
        }

        @NonNull
        @Override
        public CameraUpdate newLatLng(@NonNull LatLng latLng) {
            return new GoogleCameraUpdate(CameraUpdateFactory.newLatLng(
                    GoogleLatLng.unwrap(latLng)
            ));
        }

        @NonNull
        @Override
        public CameraUpdate newLatLngZoom(@NonNull LatLng latLng, float zoom) {
            return new GoogleCameraUpdate(CameraUpdateFactory.newLatLngZoom(
                    GoogleLatLng.unwrap(latLng), zoom
            ));
        }

        @NonNull
        @Override
        public CameraUpdate newLatLngBounds(@NonNull LatLngBounds bounds, int padding) {
            return new GoogleCameraUpdate(CameraUpdateFactory.newLatLngBounds(
                    GoogleLatLngBounds.unwrap(bounds), padding
            ));
        }

        @NonNull
        @Override
        public CameraUpdate newLatLngBounds(
                @NonNull LatLngBounds bounds, int width, int height, int padding) {
            return new GoogleCameraUpdate(CameraUpdateFactory.newLatLngBounds(
                    GoogleLatLngBounds.unwrap(bounds), width, height, padding
            ));
        }
    };


    private final com.google.android.gms.maps.CameraUpdate mDelegate;

    private GoogleCameraUpdate(com.google.android.gms.maps.CameraUpdate delegate) {
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

        GoogleCameraUpdate that = (GoogleCameraUpdate) obj;

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


    static com.google.android.gms.maps.CameraUpdate unwrap(CameraUpdate wrapped) {
        return ((GoogleCameraUpdate) wrapped).mDelegate;
    }

}
