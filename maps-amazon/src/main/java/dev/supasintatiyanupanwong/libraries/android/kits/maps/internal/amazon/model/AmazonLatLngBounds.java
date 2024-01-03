/*
 * Copyright 2021 Supasin Tatiyanupanwong
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

package dev.supasintatiyanupanwong.libraries.android.kits.maps.internal.amazon.model;

import static androidx.annotation.RestrictTo.Scope.LIBRARY;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;

import java.util.Objects;

import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.LatLng;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.LatLngBounds;

@RestrictTo(LIBRARY)
public class AmazonLatLngBounds implements LatLngBounds {

    private final com.amazon.geo.mapsv2.model.LatLngBounds mDelegate;

    private LatLng mSouthwest;
    private LatLng mNortheast;
    private LatLng mCenter;

    private AmazonLatLngBounds(@NonNull com.amazon.geo.mapsv2.model.LatLngBounds delegate) {
        mDelegate = delegate;
    }

    public AmazonLatLngBounds(@NonNull LatLng southwest, @NonNull LatLng northeast) {
        mDelegate = new com.amazon.geo.mapsv2.model.LatLngBounds(
                Objects.requireNonNull(AmazonLatLng.unwrap(southwest), "southwest == null"),
                Objects.requireNonNull(AmazonLatLng.unwrap(northeast), "northeast == null")
        );
    }

    @Override public @NonNull LatLng getSouthwest() {
        if (mSouthwest == null) {
            mSouthwest = AmazonLatLng.wrap(mDelegate.southwest);
        }
        return mSouthwest;
    }

    @Override public @NonNull LatLng getNortheast() {
        if (mNortheast == null) {
            mNortheast = AmazonLatLng.wrap(mDelegate.northeast);
        }
        return mNortheast;
    }

    @Override public boolean contains(@NonNull LatLng point) {
        final @Nullable com.amazon.geo.mapsv2.model.LatLng unwrapped = AmazonLatLng.unwrap(point);
        if (unwrapped == null) {
            return false;
        } else {
            return mDelegate.contains(unwrapped);
        }
    }

    @Override public @NonNull LatLngBounds including(@NonNull LatLng point) {
        return AmazonLatLngBounds.wrap(mDelegate.including(
                Objects.requireNonNull(AmazonLatLng.unwrap(point), "point == null"))
        );
    }

    @Override public @NonNull LatLng getCenter() {
        if (mCenter == null) {
            mCenter = AmazonLatLng.wrap(mDelegate.getCenter());
        }
        return mCenter;
    }

    @Override public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        AmazonLatLngBounds that = (AmazonLatLngBounds) obj;

        return mDelegate.equals(that.mDelegate);
    }

    @Override public int hashCode() {
        return mDelegate.hashCode();
    }

    @Override public @NonNull String toString() {
        return mDelegate.toString();
    }


    static @Nullable LatLngBounds wrap(
            @Nullable com.amazon.geo.mapsv2.model.LatLngBounds delegate
    ) {
        return delegate == null ? null : new AmazonLatLngBounds(delegate);
    }

    static @Nullable com.amazon.geo.mapsv2.model.LatLngBounds unwrap(
            @Nullable LatLngBounds wrapped
    ) {
        return wrapped == null ? null : ((AmazonLatLngBounds) wrapped).mDelegate;
    }


    public static class Builder implements LatLngBounds.Builder {
        private final com.amazon.geo.mapsv2.model.LatLngBounds.Builder mDelegate;

        public Builder() {
            mDelegate = com.amazon.geo.mapsv2.model.LatLngBounds.builder();
        }

        @Override public @NonNull LatLngBounds.Builder include(@NonNull LatLng point) {
            mDelegate.include(Objects.requireNonNull(AmazonLatLng.unwrap(point), "point == null"));
            return this;
        }

        @Override public @NonNull LatLngBounds build() {
            return Objects.requireNonNull(AmazonLatLngBounds.wrap(mDelegate.build()));
        }
    }
}
