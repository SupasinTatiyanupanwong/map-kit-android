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

package dev.supasintatiyanupanwong.libraries.android.kits.maps.internal.mapbox.model;

import static androidx.annotation.RestrictTo.Scope.LIBRARY;

import androidx.annotation.FloatRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;

import com.mapbox.maps.ExtensionUtils;

import org.jetbrains.annotations.Contract;

import java.util.Objects;

import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.CameraPosition;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.LatLng;

@RestrictTo(LIBRARY)
public class MapboxCameraPosition implements CameraPosition {

    private final @NonNull com.mapbox.maps.CameraOptions mDelegate;

    private LatLng mTarget;

    private MapboxCameraPosition(@NonNull com.mapbox.maps.CameraOptions delegate) {
        mDelegate = delegate;
    }

    @Override public @NonNull LatLng getTarget() {
        if (mTarget == null) {
            mTarget = MapboxLatLng.wrap(mDelegate.getCenter());
        }
        //noinspection ConstantConditions
        return mTarget;
    }

    @Override public float getZoom() {
        //noinspection ConstantConditions
        return mDelegate.getZoom().floatValue();
    }

    @Override public float getTilt() {
        //noinspection ConstantConditions
        return mDelegate.getPitch().floatValue();
    }

    @Override public float getBearing() {
        //noinspection ConstantConditions
        return mDelegate.getBearing().floatValue();
    }

    @Override public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        MapboxCameraPosition that = (MapboxCameraPosition) obj;

        return mDelegate.equals(that.mDelegate);
    }

    @Override public int hashCode() {
        return mDelegate.hashCode();
    }

    @Override public @NonNull String toString() {
        return mDelegate.toString();
    }


    @Contract("null -> null; !null -> !null")
    static @Nullable CameraPosition wrap(@Nullable com.mapbox.maps.CameraState delegate) {
        return delegate == null
                ? null
                : new MapboxCameraPosition(ExtensionUtils.toCameraOptions(delegate));
    }

    @Contract("null -> null; !null -> !null")
    static @Nullable CameraPosition wrap(@Nullable com.mapbox.maps.CameraOptions delegate) {
        return delegate == null ? null : new MapboxCameraPosition(delegate);
    }

    @Contract("null -> null; !null -> !null")
    static @Nullable com.mapbox.maps.CameraOptions unwrap(@Nullable CameraPosition wrapped) {
        return wrapped == null ? null : ((MapboxCameraPosition) wrapped).mDelegate;
    }


    public static class Builder implements CameraPosition.Builder {
        private final @NonNull com.mapbox.maps.CameraOptions.Builder mDelegate;

        public Builder() {
            mDelegate = new com.mapbox.maps.CameraOptions.Builder();
        }

        public Builder(@NonNull CameraPosition camera) {
            this();
            //noinspection ConstantConditions
            if (camera != null) {
                target(camera.getTarget());
                zoom(camera.getZoom());
                tilt(camera.getTilt());
                bearing(camera.getBearing());
            }
        }

        @Override public @NonNull CameraPosition.Builder target(@NonNull LatLng location) {
            mDelegate.center(
                    Objects.requireNonNull(MapboxLatLng.unwrap(location), "location == null"));
            return this;
        }

        @Override public @NonNull CameraPosition.Builder zoom(float zoom) {
            mDelegate.zoom((double) zoom);
            return this;
        }

        @Override public @NonNull CameraPosition.Builder tilt(
                @FloatRange(from = 0.0, to = 90.0) float tilt) {
            mDelegate.pitch((double) tilt);
            return this;
        }

        @Override public @NonNull CameraPosition.Builder bearing(float bearing) {
            mDelegate.bearing((double) bearing);
            return this;
        }

        @Override public @NonNull CameraPosition build() {
            return MapboxCameraPosition.wrap(mDelegate.build());
        }
    }

}
