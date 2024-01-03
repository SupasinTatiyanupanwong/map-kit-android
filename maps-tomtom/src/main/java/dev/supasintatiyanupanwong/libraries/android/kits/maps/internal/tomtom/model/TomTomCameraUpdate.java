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

import android.graphics.Point;
import android.graphics.PointF;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;

import com.tomtom.sdk.maps.display.camera.CameraOptions;
import com.tomtom.sdk.maps.display.camera.CameraOptionsFactory;

import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.CameraPosition;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.CameraUpdate;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.LatLng;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.LatLngBounds;

@RestrictTo(LIBRARY)
public class TomTomCameraUpdate implements CameraUpdate {

    public static final Factory FACTORY = new Factory() {
        @Override public @NonNull CameraUpdate zoomIn() {
            return wrap(CameraOptionsFactory.INSTANCE.zoomIn());
        }

        @Override public @NonNull CameraUpdate zoomOut() {
            return wrap(CameraOptionsFactory.INSTANCE.zoomOut());
        }

        @Override public @NonNull CameraUpdate scrollBy(float xPixel, float yPixel) {
            return wrap(CameraOptionsFactory.INSTANCE.moveBy(new PointF(xPixel, yPixel)));
        }

        @Override public @NonNull CameraUpdate zoomTo(float zoom) {
            return wrap(CameraOptionsFactory.INSTANCE.zoomTo(zoom));
        }

        @Override public @NonNull CameraUpdate zoomBy(float amount) {
            return wrap(CameraOptionsFactory.INSTANCE.zoomBy(amount));
        }

        @Override public @NonNull CameraUpdate zoomBy(float amount, @NonNull Point focus) {
            final @NonNull CameraOptions options = CameraOptionsFactory.INSTANCE.scaleBy(0, focus);

            //noinspection ConstantConditions
            if (focus != null) {
                options.deepCopy(
                        options.getPosition(),
                        options.getZoom() + amount,
                        options.getTilt(),
                        options.getRotation()
                );
            }

            return wrap(options);
        }

        @Override public @NonNull CameraUpdate newCameraPosition(
                @NonNull CameraPosition camera
        ) {
            return wrap(
                    new CameraOptions(
                            TomTomLatLng.unwrap(camera.getTarget()),
                            (double) camera.getZoom(),
                            (double) camera.getTilt(),
                            (double) camera.getBearing()
                    )
            );
        }

        @Override public @NonNull CameraUpdate newLatLng(@NonNull LatLng latLng) {
            return wrap(CameraOptionsFactory.INSTANCE.lookAt(TomTomLatLng.unwrap(latLng)));
        }

        @Override public @NonNull CameraUpdate newLatLngZoom(@NonNull LatLng latLng, float zoom) {
            return wrap(CameraOptionsFactory.INSTANCE.lookAt(TomTomLatLng.unwrap(latLng), zoom));
        }

        @Override public @NonNull CameraUpdate newLatLngBounds(
                @NonNull LatLngBounds bounds,
                int padding
        ) {
            return wrap(
                    CameraOptionsFactory.INSTANCE.lookAt(
                            TomTomLatLngBounds.unwrapToGeoBounds(bounds),
                            null,
                            padding
                    )
            );
        }

        @Override public @NonNull CameraUpdate newLatLngBounds(
                @NonNull LatLngBounds bounds,
                int width,
                int height,
                int padding
        ) {
            return wrap(
                    CameraOptionsFactory.INSTANCE.lookAt(
                            TomTomLatLngBounds.unwrapToGeoBounds(bounds),
                            null,
                            padding
                    )
            );
        }
    };


    private final com.tomtom.sdk.maps.display.camera.CameraOptions mDelegate;

    private TomTomCameraUpdate(@NonNull com.tomtom.sdk.maps.display.camera.CameraOptions delegate) {
        mDelegate = delegate;
    }

    @Override public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        TomTomCameraUpdate that = (TomTomCameraUpdate) obj;

        return mDelegate.equals(that.mDelegate);
    }

    @Override public int hashCode() {
        return mDelegate.hashCode();
    }

    @Override public @NonNull String toString() {
        return mDelegate.toString();
    }


    static CameraUpdate wrap(com.tomtom.sdk.maps.display.camera.CameraOptions delegate) {
        return new TomTomCameraUpdate(delegate);
    }

    static com.tomtom.sdk.maps.display.camera.CameraOptions unwrap(CameraUpdate wrapped) {
        return ((TomTomCameraUpdate) wrapped).mDelegate;
    }

}
