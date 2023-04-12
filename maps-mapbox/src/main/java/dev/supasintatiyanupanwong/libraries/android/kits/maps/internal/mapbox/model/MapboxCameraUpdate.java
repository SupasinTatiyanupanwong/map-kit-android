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

import android.graphics.Point;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.mapbox.maps.CameraOptions;
import com.mapbox.maps.EdgeInsets;
import com.mapbox.maps.ScreenCoordinate;
import com.mapbox.maps.plugin.animation.MapAnimationOptions;

import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.CameraPosition;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.CameraUpdate;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.LatLng;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.LatLngBounds;

class MapboxCameraUpdate implements CameraUpdate {

    private MapboxCameraUpdate() {}

    static boolean handle(
            @NonNull com.mapbox.maps.MapboxMap map,
            @NonNull com.mapbox.maps.plugin.animation.CameraAnimationsPlugin cameraAnim,
            @NonNull CameraUpdate wrapped,
            @Nullable MapAnimationOptions animationOptions
    ) {
        if (wrapped instanceof ScrollBy) {
            cameraAnim.moveBy(
                    new ScreenCoordinate(((ScrollBy) wrapped).mX, ((ScrollBy) wrapped).mY),
                    animationOptions
            );
            return true;
        }

        if (wrapped instanceof ZoomTo) {
            cameraAnim.flyTo(
                    new CameraOptions.Builder()
                            .zoom((double) ((ZoomTo) wrapped).mZoom)
                            .build(),
                    animationOptions
            );
            return true;
        }

        if (wrapped instanceof ZoomBy) {
            final @Nullable Point focus = ((ZoomBy) wrapped).mFocus;
            cameraAnim.flyTo(
                    new CameraOptions.Builder()
                            .anchor(focus == null ? null : new ScreenCoordinate(focus.x, focus.y))
                            .zoom(map.getCameraState().getZoom() + ((ZoomBy) wrapped).mAmount)
                            .build(),
                    animationOptions
            );
            return true;
        }

        if (wrapped instanceof Position) {
            cameraAnim.flyTo(
                    new CameraOptions.Builder()
                            .center(MapboxLatLng.unwrap(((Position) wrapped).mTarget))
                            .zoom((double) ((Position) wrapped).mZoom)
                            .pitch((double) ((Position) wrapped).mTilt)
                            .bearing((double) ((Position) wrapped).mBearing)
                            .build(),
                    animationOptions
            );
            return true;
        }

        if (wrapped instanceof Target) {
            cameraAnim.flyTo(
                    new CameraOptions.Builder()
                            .center(MapboxLatLng.unwrap(((Target) wrapped).mTarget))
                            .build(),
                    animationOptions
            );
            return true;
        }

        if (wrapped instanceof TargetWithZoom) {
            cameraAnim.flyTo(
                    new CameraOptions.Builder()
                            .center(MapboxLatLng.unwrap(((TargetWithZoom) wrapped).mTarget))
                            .zoom((double) ((TargetWithZoom) wrapped).mZoom)
                            .build(),
                    animationOptions
            );
            return true;
        }

        if (wrapped instanceof TargetWithBounds) {
            final int padding = ((TargetWithBounds) wrapped).mPadding;
            cameraAnim.flyTo(
                    map.cameraForCoordinateBounds(
                            MapboxLatLngBounds.unwrap(((TargetWithBounds) wrapped).mBounds),
                            new EdgeInsets(padding, padding, padding, padding),
                            null,
                            null
                    ),
                    animationOptions
            );
            return true;
        }

        return false;
    }


    static class ScrollBy extends MapboxCameraUpdate {
        private final float mX;
        private final float mY;

        ScrollBy(float xPixel, float yPixel) {
            mX = xPixel;
            mY = yPixel;
        }
    }


    static class ZoomTo extends MapboxCameraUpdate {
        private final float mZoom;

        ZoomTo(float zoom) {
            mZoom = zoom;
        }
    }


    static class ZoomBy extends MapboxCameraUpdate {
        private final float mAmount;
        private final @Nullable Point mFocus;

        ZoomBy(float amount) {
            this(amount, null);
        }

        ZoomBy(float amount, @Nullable Point focus) {
            mAmount = amount;
            mFocus = focus;
        }
    }


    static class Position extends MapboxCameraUpdate {
        private final @NonNull LatLng mTarget;
        private final float mZoom;
        private final float mTilt;
        private final float mBearing;

        Position(@NonNull CameraPosition cameraPosition) {
            mTarget = cameraPosition.getTarget();
            mZoom = cameraPosition.getZoom();
            mTilt = cameraPosition.getTilt();
            mBearing = cameraPosition.getBearing();
        }
    }


    static class Target extends MapboxCameraUpdate {
        private final @NonNull LatLng mTarget;

        Target(@NonNull LatLng target) {
            mTarget = target;
        }
    }


    static class TargetWithZoom extends MapboxCameraUpdate {
        private final @NonNull LatLng mTarget;
        private final float mZoom;

        TargetWithZoom(@NonNull LatLng target, float zoom) {
            mTarget = target;
            mZoom = zoom;
        }
    }


    static class TargetWithBounds extends MapboxCameraUpdate {
        private final @NonNull LatLngBounds mBounds;
        private final int mPadding;

        TargetWithBounds(@NonNull LatLngBounds bounds) {
            this(bounds, 0);
        }

        TargetWithBounds(@NonNull LatLngBounds bounds, int padding) {
            mBounds = bounds;
            mPadding = padding;
        }
    }

}
