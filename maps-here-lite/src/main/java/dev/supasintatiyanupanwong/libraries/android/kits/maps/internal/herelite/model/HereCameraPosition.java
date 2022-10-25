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

package dev.supasintatiyanupanwong.libraries.android.kits.maps.internal.herelite.model;

import static androidx.annotation.RestrictTo.Scope.LIBRARY;

import androidx.annotation.FloatRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;

import java.util.Arrays;
import java.util.Objects;

import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.CameraPosition;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.LatLng;

@RestrictTo(LIBRARY)
public class HereCameraPosition implements CameraPosition {

    private final @NonNull LatLng mTarget;
    private final float mZoom;
    private final float mTilt;
    private final float mBearing;

    private HereCameraPosition(@NonNull com.here.sdk.mapviewlite.Camera camera) {
        mTarget = HereLatLng.wrap(camera.getTarget());
        mZoom = (float) camera.getZoomLevel();
        mTilt = (float) camera.getTilt();
        mBearing = (float) camera.getBearing();
    }

    private HereCameraPosition(@NonNull LatLng target, float zoom, float tilt, float bearing) {
        mTarget = Objects.requireNonNull(target, "null camera target");
        mZoom = zoom;
        mTilt = tilt;
        mBearing = bearing;
    }

    @Override public @NonNull LatLng getTarget() {
        return mTarget;
    }

    @Override public float getZoom() {
        return mZoom;
    }

    @Override public float getTilt() {
        return mTilt;
    }

    @Override public float getBearing() {
        return mBearing;
    }

    @Override public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        HereCameraPosition that = (HereCameraPosition) obj;

        return mTarget.equals(that.mTarget)
                && mZoom == that.mZoom
                && mTilt == that.mTilt
                && mBearing == that.mBearing;
    }

    @Override public int hashCode() {
        return Objects.hash(mTarget, mZoom, mTilt, mBearing);
    }

    @Override public @NonNull String toString() {
        return "HereCameraPosition(target="
                + mTarget
                + ", zoom="
                + mZoom
                + ", tilt="
                + mTilt
                + ", bearing="
                + mBearing
                + ')';
    }


    static @NonNull CameraPosition wrap(@NonNull com.here.sdk.mapviewlite.Camera delegate) {
        return new HereCameraPosition(delegate);
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
            //noinspection ConstantConditions
            return new HereCameraPosition(mTarget, mZoom, mTilt, mBearing);
        }
    }

}
