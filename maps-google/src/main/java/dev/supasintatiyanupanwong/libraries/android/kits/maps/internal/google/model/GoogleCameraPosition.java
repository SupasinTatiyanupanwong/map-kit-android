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

import static androidx.annotation.RestrictTo.Scope.LIBRARY;

import androidx.annotation.FloatRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;

import java.util.Objects;

import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.CameraPosition;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.LatLng;

@RestrictTo(LIBRARY)
public class GoogleCameraPosition implements CameraPosition {

    private final @NonNull com.google.android.gms.maps.model.CameraPosition mDelegate;

    private @Nullable LatLng mTarget;

    private GoogleCameraPosition(
            @NonNull com.google.android.gms.maps.model.CameraPosition delegate
    ) {
        mDelegate = delegate;
    }

    @Override public @NonNull LatLng getTarget() {
        if (mTarget == null) {
            mTarget = GoogleLatLng.wrap(mDelegate.target);
        }
        return mTarget;
    }

    @Override public float getZoom() {
        return mDelegate.zoom;
    }

    @Override public float getTilt() {
        return mDelegate.tilt;
    }

    @Override public float getBearing() {
        return mDelegate.bearing;
    }

    @Override public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        GoogleCameraPosition that = (GoogleCameraPosition) obj;

        return mDelegate.equals(that.mDelegate);
    }

    @Override public int hashCode() {
        return mDelegate.hashCode();
    }

    @Override public @NonNull String toString() {
        return mDelegate.toString();
    }


    static CameraPosition wrap(com.google.android.gms.maps.model.CameraPosition delegate) {
        return new GoogleCameraPosition(delegate);
    }

    static com.google.android.gms.maps.model.CameraPosition unwrap(CameraPosition wrapped) {
        return ((GoogleCameraPosition) wrapped).mDelegate;
    }


    public static class Builder implements CameraPosition.Builder {
        private final @NonNull com.google.android.gms.maps.model.CameraPosition.Builder mDelegate;

        public Builder() {
            mDelegate = com.google.android.gms.maps.model.CameraPosition.builder();
        }

        public Builder(@NonNull CameraPosition camera) {
            mDelegate = com.google.android.gms.maps.model.CameraPosition.builder(
                    Objects.requireNonNull(GoogleCameraPosition.unwrap(camera), "camera == null")
            );
        }

        @Override public @NonNull CameraPosition.Builder target(@NonNull LatLng location) {
            mDelegate.target(
                    Objects.requireNonNull(GoogleLatLng.unwrap(location), "location == null")
            );
            return this;
        }

        @Override public @NonNull CameraPosition.Builder zoom(float zoom) {
            mDelegate.zoom(zoom);
            return this;
        }

        @Override public @NonNull CameraPosition.Builder tilt(
                @FloatRange(from = 0.0, to = 90.0) float tilt
        ) {
            mDelegate.tilt(tilt);
            return this;
        }

        @Override public @NonNull CameraPosition.Builder bearing(float bearing) {
            mDelegate.bearing(bearing);
            return this;
        }

        @Override public @NonNull CameraPosition build() {
            return GoogleCameraPosition.wrap(mDelegate.build());
        }
    }
}
