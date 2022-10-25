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

import android.graphics.Point;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;

import com.here.sdk.core.Point2D;
import com.here.sdk.mapviewlite.Padding;

import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.CameraPosition;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.CameraUpdate;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.LatLng;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.LatLngBounds;

@RestrictTo(LIBRARY)
public class HereCameraUpdate implements CameraUpdate {

    public static final CameraUpdate.Factory FACTORY = new CameraUpdate.Factory() {
        @Override public @NonNull CameraUpdate zoomIn() {
            return new HereCameraUpdate.ZoomBy(1f);
        }

        @Override public @NonNull CameraUpdate zoomOut() {
            return new HereCameraUpdate.ZoomBy(-1f);
        }

        @Override public @NonNull CameraUpdate scrollBy(float xPixel, float yPixel) {
            return new HereCameraUpdate.ScrollBy(xPixel, yPixel);
        }

        @Override public @NonNull CameraUpdate zoomTo(float zoom) {
            return new HereCameraUpdate.ZoomTo(zoom);
        }

        @Override public @NonNull CameraUpdate zoomBy(float amount) {
            return new HereCameraUpdate.ZoomBy(amount);
        }

        @Override public @NonNull CameraUpdate zoomBy(float amount, @NonNull Point focus) {
            return new HereCameraUpdate.ZoomBy(amount, focus);
        }

        @Override public @NonNull CameraUpdate newCameraPosition(
                @NonNull CameraPosition cameraPosition
        ) {
            return new HereCameraUpdate.Position(cameraPosition);
        }

        @Override public @NonNull CameraUpdate newLatLng(@NonNull LatLng latLng) {
            return new HereCameraUpdate.Target(latLng);
        }

        @Override public @NonNull CameraUpdate newLatLngZoom(@NonNull LatLng latLng, float zoom) {
            return new HereCameraUpdate.TargetWithZoom(latLng, zoom);
        }

        @Override public @NonNull CameraUpdate newLatLngBounds(
                @NonNull LatLngBounds bounds,
                int padding
        ) {
            return new HereCameraUpdate.TargetWithBounds(bounds, padding);
        }

        @Override public @NonNull CameraUpdate newLatLngBounds(
                @NonNull LatLngBounds bounds,
                int width,
                int height,
                int padding
        ) {
            return new HereCameraUpdate.TargetWithBounds(bounds, padding);
        }
    };


    static class ScrollBy extends HereCameraUpdate {
        private final float mX;
        private final float mY;

        ScrollBy(float xPixel, float yPixel) {
            mX = xPixel;
            mY = yPixel;
        }
    }

    static class ZoomTo extends HereCameraUpdate {
        private final float mZoom;

        ZoomTo(float zoom) {
            mZoom = zoom;
        }
    }

    static class ZoomBy extends HereCameraUpdate {
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

    static class Position extends HereCameraUpdate {
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

    static class Target extends HereCameraUpdate {
        private final @NonNull LatLng mTarget;

        Target(@NonNull LatLng target) {
            mTarget = target;
        }
    }

    static class TargetWithZoom extends HereCameraUpdate {
        private final @NonNull LatLng mTarget;
        private final float mZoom;

        TargetWithZoom(@NonNull LatLng target, float zoom) {
            mTarget = target;
            mZoom = zoom;
        }
    }

    static class TargetWithBounds extends HereCameraUpdate {
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


    static boolean handle(
            @NonNull com.here.sdk.mapviewlite.Camera camera,
            @NonNull CameraUpdate wrapped
    ) {
        if (wrapped instanceof ScrollBy) {
            final @NonNull Point2D viewTarget = camera.geoToViewCoordinates(camera.getTarget());
            camera.setTarget(
                    camera.viewToGeoCoordinates(
                            new Point2D(
                                    viewTarget.x + ((ScrollBy) wrapped).mX,
                                    viewTarget.y + ((ScrollBy) wrapped).mY
                            )
                    )
            );
            return true;
        }

        if (wrapped instanceof ZoomTo) {
            camera.setZoomLevel(((ZoomTo) wrapped).mZoom);
            return true;
        }

        if (wrapped instanceof ZoomBy) {
            final float amount = ((ZoomBy) wrapped).mAmount;
            final @Nullable Point focus = ((ZoomBy) wrapped).mFocus;
            camera.zoomBy(
                    amount == 0 ? 1.0 : Math.pow(2.0, amount),
                    focus == null
                            ? camera.geoToViewCoordinates(camera.getTarget())
                            : HerePoint.unwrap(focus)
            );
            return true;
        }

        if (wrapped instanceof Position) {
            camera.updateCamera(
                    new com.here.sdk.mapviewlite.CameraUpdate(
                            ((Position) wrapped).mTilt,
                            ((Position) wrapped).mBearing,
                            ((Position) wrapped).mZoom,
                            HereLatLng.unwrap(((Position) wrapped).mTarget)
                    )
            );
            return true;
        }

        if (wrapped instanceof Target) {
            camera.setTarget(HereLatLng.unwrap(((Target) wrapped).mTarget));
            return true;
        }

        if (wrapped instanceof TargetWithZoom) {
            camera.setTarget(HereLatLng.unwrap(((TargetWithZoom) wrapped).mTarget));
            camera.setZoomLevel(((TargetWithZoom) wrapped).mZoom);
            return true;
        }

        if (wrapped instanceof TargetWithBounds) {
            final int padding = ((TargetWithBounds) wrapped).mPadding;
            final @NonNull com.here.sdk.mapviewlite.CameraUpdate update =
                    camera.calculateEnclosingCameraUpdate(
                            HereLatLngBounds.unwrap(((TargetWithBounds) wrapped).mBounds),
                            new Padding(padding, padding, padding, padding)
                    );
            update.tilt = 0;
            update.bearing = 0;
            camera.updateCamera(update);
            return true;
        }

        return false;
    }

}
