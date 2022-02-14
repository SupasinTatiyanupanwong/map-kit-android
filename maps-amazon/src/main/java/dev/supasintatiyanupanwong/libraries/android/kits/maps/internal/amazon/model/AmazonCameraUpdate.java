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

import static androidx.annotation.RestrictTo.Scope.LIBRARY;

import android.graphics.Point;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;

import com.amazon.geo.mapsv2.CameraUpdateFactory;

import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.CameraPosition;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.CameraUpdate;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.LatLng;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.LatLngBounds;

@RestrictTo(LIBRARY)
public class AmazonCameraUpdate implements CameraUpdate {

    public static final Factory FACTORY = new Factory() {
        @Override
        public @NonNull CameraUpdate zoomIn() {
            return new AmazonCameraUpdate(CameraUpdateFactory.zoomIn());
        }

        @Override
        public @NonNull CameraUpdate zoomOut() {
            return new AmazonCameraUpdate(CameraUpdateFactory.zoomOut());
        }

        @Override
        public @NonNull CameraUpdate scrollBy(float xPixel, float yPixel) {
            return new AmazonCameraUpdate(CameraUpdateFactory.scrollBy(xPixel, yPixel));
        }

        @Override
        public @NonNull CameraUpdate zoomTo(float zoom) {
            return new AmazonCameraUpdate(CameraUpdateFactory.zoomTo(zoom));
        }

        @Override
        public @NonNull CameraUpdate zoomBy(float amount) {
            return new AmazonCameraUpdate(CameraUpdateFactory.zoomBy(amount));
        }

        @Override
        public @NonNull CameraUpdate zoomBy(float amount, Point focus) {
            return new AmazonCameraUpdate(CameraUpdateFactory.zoomBy(amount, focus));
        }

        @Override
        public @NonNull CameraUpdate newCameraPosition(CameraPosition cameraPosition) {
            return new AmazonCameraUpdate(CameraUpdateFactory.newCameraPosition(
                    AmazonCameraPosition.unwrap(cameraPosition)
            ));
        }

        @Override
        public @NonNull CameraUpdate newLatLng(@NonNull LatLng latLng) {
            return new AmazonCameraUpdate(CameraUpdateFactory.newLatLng(
                    AmazonLatLng.unwrap(latLng)
            ));
        }

        @Override
        public @NonNull CameraUpdate newLatLngZoom(@NonNull LatLng latLng, float zoom) {
            return new AmazonCameraUpdate(CameraUpdateFactory.newLatLngZoom(
                    AmazonLatLng.unwrap(latLng), zoom
            ));
        }

        @Override
        public @NonNull CameraUpdate newLatLngBounds(@NonNull LatLngBounds bounds, int padding) {
            return new AmazonCameraUpdate(CameraUpdateFactory.newLatLngBounds(
                    AmazonLatLngBounds.unwrap(bounds), padding
            ));
        }

        @Override
        public @NonNull CameraUpdate newLatLngBounds(
                @NonNull LatLngBounds bounds,
                int width,
                int height,
                int padding) {
            return new AmazonCameraUpdate(CameraUpdateFactory.newLatLngBounds(
                    AmazonLatLngBounds.unwrap(bounds), width, height, padding
            ));
        }
    };


    private final com.amazon.geo.mapsv2.CameraUpdate mDelegate;

    private AmazonCameraUpdate(com.amazon.geo.mapsv2.CameraUpdate delegate) {
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

        AmazonCameraUpdate that = (AmazonCameraUpdate) obj;

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


    static com.amazon.geo.mapsv2.CameraUpdate unwrap(CameraUpdate wrapped) {
        return ((AmazonCameraUpdate) wrapped).mDelegate;
    }

}
