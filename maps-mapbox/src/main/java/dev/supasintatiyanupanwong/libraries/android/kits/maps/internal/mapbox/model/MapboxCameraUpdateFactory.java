/*
 * Copyright 2023 Supasin Tatiyanupanwong
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

package dev.supasintatiyanupanwong.libraries.android.kits.maps.internal.mapbox.model;

import static androidx.annotation.RestrictTo.Scope.LIBRARY;

import android.graphics.Point;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;

import com.mapbox.maps.CameraOptions;
import com.mapbox.maps.EdgeInsets;
import com.mapbox.maps.ScreenCoordinate;
import com.mapbox.maps.plugin.animation.MapAnimationOptions;

import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.BitmapDescriptor;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.CameraPosition;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.CameraUpdate;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.LatLng;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.LatLngBounds;

@RestrictTo(LIBRARY)
public class MapboxCameraUpdateFactory implements CameraUpdate.Factory {

    public static final CameraUpdate.Factory INSTANCE = new MapboxCameraUpdateFactory();

    private MapboxCameraUpdateFactory() {}

    @Override public @NonNull CameraUpdate zoomIn() {
        return new MapboxCameraUpdate.ZoomBy(1f);
    }

    @Override public @NonNull CameraUpdate zoomOut() {
        return new MapboxCameraUpdate.ZoomBy(-1f);
    }

    @Override public @NonNull CameraUpdate scrollBy(float xPixel, float yPixel) {
        return new MapboxCameraUpdate.ScrollBy(xPixel, yPixel);
    }

    @Override public @NonNull CameraUpdate zoomTo(float zoom) {
        return new MapboxCameraUpdate.ZoomTo(zoom);
    }

    @Override public @NonNull CameraUpdate zoomBy(float amount) {
        return new MapboxCameraUpdate.ZoomBy(amount);
    }

    @Override public @NonNull CameraUpdate zoomBy(float amount, @NonNull Point focus) {
        return new MapboxCameraUpdate.ZoomBy(amount, focus);
    }

    @Override public @NonNull CameraUpdate newCameraPosition(
            @NonNull CameraPosition cameraPosition
    ) {
        return new MapboxCameraUpdate.Position(cameraPosition);
    }

    @Override public @NonNull CameraUpdate newLatLng(@NonNull LatLng latLng) {
        return new MapboxCameraUpdate.Target(latLng);
    }

    @Override public @NonNull CameraUpdate newLatLngZoom(@NonNull LatLng latLng, float zoom) {
        return new MapboxCameraUpdate.TargetWithZoom(latLng, zoom);
    }

    @Override public @NonNull CameraUpdate newLatLngBounds(
            @NonNull LatLngBounds bounds,
            int padding
    ) {
        return new MapboxCameraUpdate.TargetWithBounds(bounds, padding);
    }

    @Override public @NonNull CameraUpdate newLatLngBounds(
            @NonNull LatLngBounds bounds,
            int width,
            int height,
            int padding
    ) {
        return new MapboxCameraUpdate.TargetWithBounds(bounds, padding);
    }

}
