/*
 * Copyright 2022 Supasin Tatiyanupanwong
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

package dev.supasintatiyanupanwong.libraries.android.kits.maps.internal.nop.model;

import static androidx.annotation.RestrictTo.Scope.LIBRARY;

import android.graphics.Point;

import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;

import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.CameraPosition;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.CameraUpdate;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.LatLng;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.LatLngBounds;

@RestrictTo(LIBRARY)
public class NopCameraUpdate implements CameraUpdate {

    static final @NonNull CameraUpdate NULL = new NopCameraUpdate();

    public static final @NonNull Factory FACTORY = new Factory() {
        @Override public @NonNull CameraUpdate zoomIn() {
            return NopCameraUpdate.NULL; // Not supported, null object for API safe.
        }

        @Override public @NonNull CameraUpdate zoomOut() {
            return NopCameraUpdate.NULL; // Not supported, null object for API safe.
        }

        @Override public @NonNull CameraUpdate scrollBy(float xPixel, float yPixel) {
            return NopCameraUpdate.NULL; // Not supported, null object for API safe.
        }

        @Override public @NonNull CameraUpdate zoomTo(float zoom) {
            return NopCameraUpdate.NULL; // Not supported, null object for API safe.
        }

        @Override public @NonNull CameraUpdate zoomBy(float amount) {
            return NopCameraUpdate.NULL; // Not supported, null object for API safe.
        }

        @Override public @NonNull CameraUpdate zoomBy(float amount, Point focus) {
            return NopCameraUpdate.NULL; // Not supported, null object for API safe.
        }

        @Override public @NonNull CameraUpdate newCameraPosition(CameraPosition cameraPosition) {
            return NopCameraUpdate.NULL; // Not supported, null object for API safe.
        }

        @Override public @NonNull CameraUpdate newLatLng(@NonNull LatLng latLng) {
            return NopCameraUpdate.NULL; // Not supported, null object for API safe.
        }

        @Override public @NonNull CameraUpdate newLatLngZoom(@NonNull LatLng latLng, float zoom) {
            return NopCameraUpdate.NULL; // Not supported, null object for API safe.
        }

        @Override public @NonNull CameraUpdate newLatLngBounds(
                @NonNull LatLngBounds bounds,
                int padding) {
            return NopCameraUpdate.NULL; // Not supported, null object for API safe.
        }

        @Override public @NonNull CameraUpdate newLatLngBounds(
                @NonNull LatLngBounds bounds,
                int width,
                int height,
                int padding) {
            return NopCameraUpdate.NULL; // Not supported, null object for API safe.
        }
    };


    private NopCameraUpdate() {}

}
