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

import android.graphics.Bitmap;
import android.net.Uri;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;

import com.tomtom.sdk.maps.display.image.ImageFactory;

import org.jetbrains.annotations.Contract;

import java.io.File;

import dev.supasintatiyanupanwong.libraries.android.kits.maps.internal.tomtom.R;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.BitmapDescriptor;

@RestrictTo(LIBRARY)
public class TomTomBitmapDescriptor implements BitmapDescriptor {

    public static final Factory FACTORY = new Factory() {
        @Override public @NonNull BitmapDescriptor defaultMarker() {
            //noinspection ConstantConditions
            return fromBitmap(
                    BitmapDescriptor.fromResourceInternal(
                            R.drawable.mapkit_internal_tomtom_ic_location_pin_filled_24dp
                    )
            );
        }

        @Override public @NonNull BitmapDescriptor defaultMarker(float hue) {
            //noinspection ConstantConditions
            return fromBitmap(
                    BitmapDescriptor.fromResourceInternal(
                            R.drawable.mapkit_internal_tomtom_ic_location_pin_filled_24dp,
                            hue
                    )
            );
        }

        @Override public @Nullable BitmapDescriptor fromAsset(@NonNull String assetName) {
            //noinspection ConstantConditions
            if (assetName == null) {
                return null;
            }

            try {
                return wrap(ImageFactory.INSTANCE.fromAssets(assetName));
            } catch (Exception ignored) {
                return null;
            }
        }

        @Override public @Nullable BitmapDescriptor fromBitmap(@Nullable Bitmap image) {
            return image == null ? null : wrap(ImageFactory.INSTANCE.fromBitmap(image));
        }

        @Override public @Nullable BitmapDescriptor fromFile(@NonNull String fileName) {
            //noinspection ConstantConditions
            if (fileName == null) {
                return null;
            }

            try {
                return wrap(ImageFactory.INSTANCE.fromUri(Uri.fromFile(new File(fileName))));
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
                return wrap(ImageFactory.INSTANCE.fromUri(Uri.fromFile(new File(absolutePath))));
            } catch (Exception ignored) {
                return null;
            }
        }

        @Override public @Nullable BitmapDescriptor fromResource(@DrawableRes int resourceId) {
            if (resourceId == 0) {
                return null;
            }

            return fromBitmap(BitmapDescriptor.fromResourceInternal(resourceId));
        }
    };


    private final com.tomtom.sdk.maps.display.image.Image mDelegate;

    private TomTomBitmapDescriptor(com.tomtom.sdk.maps.display.image.Image delegate) {
        mDelegate = delegate;
    }

    @Override public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        TomTomBitmapDescriptor that = (TomTomBitmapDescriptor) obj;

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
            @Nullable com.tomtom.sdk.maps.display.image.Image delegate
    ) {
        return delegate == null ? null : new TomTomBitmapDescriptor(delegate);
    }

    @Contract("null -> null; !null -> !null")
    static @Nullable com.tomtom.sdk.maps.display.image.Image unwrap(
            @Nullable BitmapDescriptor wrapped
    ) {
        return wrapped == null ? null : ((TomTomBitmapDescriptor) wrapped).mDelegate;
    }

}
