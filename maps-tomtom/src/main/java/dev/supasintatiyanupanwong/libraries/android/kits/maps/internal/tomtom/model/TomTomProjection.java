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

import android.annotation.SuppressLint;
import android.graphics.Point;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;

import com.tomtom.sdk.common.Result;
import com.tomtom.sdk.common.location.GeoCoordinate;
import com.tomtom.sdk.maps.display.map.InvalidPointException;

import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.LatLng;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.Projection;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.VisibleRegion;

@SuppressLint("UnsafeOptInUsageError")
@RestrictTo(LIBRARY)
class TomTomProjection implements Projection {

    private final @NonNull com.tomtom.sdk.maps.display.TomTomMap mDelegate;

    TomTomProjection(@NonNull com.tomtom.sdk.maps.display.TomTomMap delegate) {
        mDelegate = delegate;
    }

    @Override public @Nullable LatLng fromScreenLocation(Point point) {
        final @NonNull Result<GeoCoordinate, InvalidPointException> result =
                mDelegate.coordinateForPoint(point);

        if (result.isSuccess()) {
            return TomTomLatLng.wrap(result.value());
        } else {
            return null;
        }
    }

    @Override public @NonNull Point toScreenLocation(LatLng location) {
        return mDelegate.pointForCoordinate(TomTomLatLng.unwrap(location));
    }

    @Override public @NonNull VisibleRegion getVisibleRegion() {
        return TomTomVisibleRegion.wrap(mDelegate.getVisibleRegion().value());
    }

    @Override public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        TomTomProjection that = (TomTomProjection) obj;

        return mDelegate.equals(that.mDelegate);
    }

    @Override public int hashCode() {
        return mDelegate.hashCode();
    }

    @Override public @NonNull String toString() {
        return mDelegate.toString();
    }

}
