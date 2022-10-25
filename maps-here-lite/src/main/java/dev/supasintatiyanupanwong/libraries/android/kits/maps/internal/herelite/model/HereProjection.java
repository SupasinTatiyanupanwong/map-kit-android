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

import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.LatLng;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.Projection;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.VisibleRegion;

@RestrictTo(LIBRARY)
class HereProjection implements Projection {

    private final @NonNull com.here.sdk.mapviewlite.Camera mCamera;
    private final @NonNull com.here.sdk.core.GeoBox mDelegate;

    private HereProjection(@NonNull com.here.sdk.mapviewlite.Camera camera) {
        mCamera = camera;
        mDelegate = camera.getBoundingBox();
    }

    @Override public @Nullable LatLng fromScreenLocation(Point point) {
        return HereLatLng.wrap(mCamera.viewToGeoCoordinates(HerePoint.unwrap(point)));
    }

    @Override public @NonNull Point toScreenLocation(LatLng location) {
        return HerePoint.wrap(mCamera.geoToViewCoordinates(HereLatLng.unwrap(location)));
    }

    @Override public @NonNull VisibleRegion getVisibleRegion() {
        return HereVisibleRegion.wrap(mDelegate);
    }

    @Override public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        HereProjection that = (HereProjection) obj;

        return mDelegate.equals(that.mDelegate);
    }

    @Override public int hashCode() {
        return mDelegate.hashCode();
    }

    @Override public @NonNull String toString() {
        return mDelegate.toString();
    }


    static @NonNull Projection wrap(@NonNull com.here.sdk.mapviewlite.Camera delegate) {
        return new HereProjection(delegate);
    }

}
