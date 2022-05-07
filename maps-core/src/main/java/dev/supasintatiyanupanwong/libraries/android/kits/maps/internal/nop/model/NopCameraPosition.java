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

import androidx.annotation.FloatRange;
import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;

import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.CameraPosition;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.LatLng;

@RestrictTo(LIBRARY)
public class NopCameraPosition implements CameraPosition {

    public static final @NonNull CameraPosition NULL = new NopCameraPosition();

    private NopCameraPosition() {}

    @Override public @NonNull LatLng getTarget() {
        return NopLatLng.NULL; // Not supported, null object for API safe.
    }

    @Override public float getZoom() {
        return 0f; // Not supported, fallback to default.
    }

    @Override public float getTilt() {
        return 0f; // Not supported, fallback to default.
    }

    @Override public float getBearing() {
        return 0f; // Not supported, fallback to default.
    }


    public static class Builder implements CameraPosition.Builder {
        public static final @NonNull CameraPosition.Builder NULL = new NopCameraPosition.Builder();

        private Builder() {}

        @Override public @NonNull CameraPosition.Builder target(@NonNull LatLng location) {
            // Not supported, no-op.
            return this;
        }

        @Override public @NonNull CameraPosition.Builder zoom(float zoom) {
            // Not supported, no-op.
            return this;
        }

        @Override public @NonNull CameraPosition.Builder tilt(
                @FloatRange(from = 0.0, to = 90.0) float tilt) {
            // Not supported, no-op.
            return this;
        }

        @Override public @NonNull CameraPosition.Builder bearing(float bearing) {
            // Not supported, no-op.
            return this;
        }

        @Override public @NonNull CameraPosition build() {
            return NopCameraPosition.NULL; // Not supported, null object for API safe.
        }
    }

}
