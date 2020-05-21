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

package me.tatiyanupanwong.supasin.android.libraries.kits.maps.internal.google.model;

import androidx.annotation.FloatRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import me.tatiyanupanwong.supasin.android.libraries.kits.maps.model.CameraPosition;
import me.tatiyanupanwong.supasin.android.libraries.kits.maps.model.LatLng;

class GoogleCameraPosition implements CameraPosition {

    private final com.google.android.gms.maps.model.CameraPosition mDelegate;

    private LatLng mTarget;

    private GoogleCameraPosition(com.google.android.gms.maps.model.CameraPosition delegate) {
        mDelegate = delegate;
    }

    @NonNull
    @Override
    public LatLng getTarget() {
        if (mTarget == null) {
            mTarget = GoogleLatLng.wrap(mDelegate.target);
        }
        return mTarget;
    }

    @Override
    public float getZoom() {
        return mDelegate.zoom;
    }

    @Override
    public float getTilt() {
        return mDelegate.tilt;
    }

    @Override
    public float getBearing() {
        return mDelegate.bearing;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        GoogleCameraPosition that = (GoogleCameraPosition) obj;

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


    static CameraPosition wrap(com.google.android.gms.maps.model.CameraPosition delegate) {
        return new GoogleCameraPosition(delegate);
    }

    static com.google.android.gms.maps.model.CameraPosition unwrap(CameraPosition wrapped) {
        return ((GoogleCameraPosition) wrapped).mDelegate;
    }


    static class Builder implements CameraPosition.Builder {
        private final com.google.android.gms.maps.model.CameraPosition.Builder mDelegate;

        Builder() {
            mDelegate = com.google.android.gms.maps.model.CameraPosition.builder();
        }

        Builder(CameraPosition camera) {
            mDelegate = com.google.android.gms.maps.model.CameraPosition.builder(
                    GoogleCameraPosition.unwrap(camera));
        }

        @NonNull
        @Override
        public CameraPosition.Builder target(@NonNull LatLng location) {
            mDelegate.target(GoogleLatLng.unwrap(location));
            return this;
        }

        @NonNull
        @Override
        public CameraPosition.Builder zoom(float zoom) {
            mDelegate.zoom(zoom);
            return this;
        }

        @NonNull
        @Override
        public CameraPosition.Builder tilt(@FloatRange(from = 0.0, to = 90.0) float tilt) {
            mDelegate.tilt(tilt);
            return this;
        }

        @NonNull
        @Override
        public CameraPosition.Builder bearing(float bearing) {
            mDelegate.bearing(bearing);
            return this;
        }

        @NonNull
        @Override
        public CameraPosition build() {
            return GoogleCameraPosition.wrap(mDelegate.build());
        }
    }

}
