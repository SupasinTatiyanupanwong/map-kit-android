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

import android.graphics.Bitmap;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;

import com.amazon.geo.mapsv2.model.BitmapDescriptorFactory;

import org.jetbrains.annotations.Contract;

import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.BitmapDescriptor;

@RestrictTo(LIBRARY)
public class AmazonBitmapDescriptor implements BitmapDescriptor {

    public static final Factory FACTORY = new Factory() {
        @Override public @NonNull BitmapDescriptor defaultMarker() {
            return wrap(BitmapDescriptorFactory.defaultMarker());
        }

        @Override public @NonNull BitmapDescriptor defaultMarker(float hue) {
            return wrap(BitmapDescriptorFactory.defaultMarker(hue));
        }

        @Override public @Nullable BitmapDescriptor fromAsset(@NonNull String assetName) {
            //noinspection ConstantConditions
            if (assetName == null) {
                return null;
            }

            try {
                return wrap(BitmapDescriptorFactory.fromAsset(assetName));
            } catch (Exception ignored) {
                return null;
            }
        }

        @Override public @Nullable BitmapDescriptor fromBitmap(@Nullable Bitmap image) {
            return image == null ? null : wrap(BitmapDescriptorFactory.fromBitmap(image));
        }

        @Override public @Nullable BitmapDescriptor fromFile(@NonNull String fileName) {
            //noinspection ConstantConditions
            if (fileName == null) {
                return null;
            }

            try {
                return wrap(BitmapDescriptorFactory.fromFile(fileName));
            } catch (Exception ignored) {
                return null;
            }
        }

        @Override public @Nullable BitmapDescriptor fromPath(@NonNull String absolutePath) {
            //noinspection ConstantConditions
            if (absolutePath == null) {
                return null;
            }

            try {
                return wrap(BitmapDescriptorFactory.fromPath(absolutePath));
            } catch (Exception ignored) {
                return null;
            }
        }

        @Override public @Nullable BitmapDescriptor fromResource(@DrawableRes int resourceId) {
            if (resourceId == 0) {
                return null;
            }

            try {
                return fromBitmap(BitmapDescriptor.fromResourceInternal(resourceId));
            } catch (Exception ignored) {
                return null;
            }
        }
    };


    private final @NonNull com.amazon.geo.mapsv2.model.BitmapDescriptor mDelegate;

    private AmazonBitmapDescriptor(
            @NonNull com.amazon.geo.mapsv2.model.BitmapDescriptor delegate
    ) {
        mDelegate = delegate;
    }

    @Override public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        AmazonBitmapDescriptor that = (AmazonBitmapDescriptor) obj;

        return mDelegate.equals(that.mDelegate);
    }

    @Override public int hashCode() {
        return mDelegate.hashCode();
    }

    @Override public @NonNull String toString() {
        return mDelegate.toString();
    }


    @Contract("null -> null; !null -> !null")
    static @Nullable BitmapDescriptor wrap(
            @Nullable com.amazon.geo.mapsv2.model.BitmapDescriptor delegate
    ) {
        return delegate == null ? null : new AmazonBitmapDescriptor(delegate);
    }

    @Contract("null -> null; !null -> !null")
    static @Nullable com.amazon.geo.mapsv2.model.BitmapDescriptor unwrap(
            @Nullable BitmapDescriptor wrapped
    ) {
        return wrapped == null ? null : ((AmazonBitmapDescriptor) wrapped).mDelegate;
    }
}
