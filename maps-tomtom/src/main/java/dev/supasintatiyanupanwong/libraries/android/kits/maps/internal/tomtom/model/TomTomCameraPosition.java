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

package dev.supasintatiyanupanwong.libraries.android.kits.maps.internal.tomtom.model;

import static androidx.annotation.RestrictTo.Scope.LIBRARY;

import androidx.annotation.FloatRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;

import java.util.Objects;

import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.CameraPosition;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.LatLng;

@RestrictTo(LIBRARY)
public class TomTomCameraPosition implements CameraPosition {

    private final com.tomtom.sdk.maps.display.camera.CameraPosition mDelegate;

    private LatLng mTarget;

    private TomTomCameraPosition(com.tomtom.sdk.maps.display.camera.CameraPosition delegate) {
        mDelegate = delegate;
    }

    @Override public @NonNull LatLng getTarget() {
        if (mTarget == null) {
            mTarget = TomTomLatLng.wrap(mDelegate.getPosition());
        }
        return mTarget;
    }

    @Override public float getZoom() {
        return (float) mDelegate.getZoom();
    }

    @Override public float getTilt() {
        return (float) mDelegate.getTilt();
    }

    @Override public float getBearing() {
        return (float) mDelegate.getRotation();
    }

    @Override public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        TomTomCameraPosition that = (TomTomCameraPosition) obj;

        return mDelegate.equals(that.mDelegate);
    }

    @Override public int hashCode() {
        return mDelegate.hashCode();
    }

    @Override public @NonNull String toString() {
        return mDelegate.toString();
    }


    static CameraPosition wrap(com.tomtom.sdk.maps.display.camera.CameraPosition delegate) {
        return new TomTomCameraPosition(delegate);
    }

    static com.tomtom.sdk.maps.display.camera.CameraPosition unwrap(CameraPosition wrapped) {
        return ((TomTomCameraPosition) wrapped).mDelegate;
    }


    public static class Builder implements CameraPosition.Builder {
        private @Nullable LatLng mTarget;
        private float mZoom;
        private float mTilt;
        private float mBearing;

        public Builder() {}

        public Builder(@NonNull CameraPosition camera) {
            Objects.requireNonNull(camera, "camera == null");

            mTarget = camera.getTarget();
            mZoom = camera.getZoom();
            mTilt = camera.getTilt();
            mBearing = camera.getBearing();
        }

        @Override public final @NonNull CameraPosition.Builder target(@NonNull LatLng location) {
            mTarget = Objects.requireNonNull(location, "location == null");
            return this;
        }

        @Override public final @NonNull CameraPosition.Builder zoom(float zoom) {
            mZoom = zoom;
            return this;
        }

        @Override public final @NonNull CameraPosition.Builder tilt(
                @FloatRange(from = 0.0, to = 90.0) float tilt
        ) {
            mTilt = tilt;
            return this;
        }

        @Override public final @NonNull CameraPosition.Builder bearing(float bearing) {
            mBearing = bearing;
            return this;
        }

        @Override public @NonNull CameraPosition build() {
            return TomTomCameraPosition.wrap(
                    new com.tomtom.sdk.maps.display.camera.CameraPosition(
                            TomTomLatLng.unwrap(mTarget),
                            mZoom,
                            mTilt,
                            mBearing
                    )
            );
        }
    }

}
