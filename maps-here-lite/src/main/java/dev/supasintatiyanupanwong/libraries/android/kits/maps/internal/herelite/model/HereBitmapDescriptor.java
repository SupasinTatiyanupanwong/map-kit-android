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

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;

import com.here.sdk.mapviewlite.MapImageFactory;

import org.jetbrains.annotations.Contract;

import java.io.FileNotFoundException;
import java.io.IOException;

import dev.supasintatiyanupanwong.libraries.android.kits.maps.MapKit;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.internal.herelite.R;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.BitmapDescriptor;

@RestrictTo(LIBRARY)
public class HereBitmapDescriptor implements BitmapDescriptor {

    public static final Factory FACTORY = new Factory() {
        @Override public @NonNull BitmapDescriptor defaultMarker() {
            //noinspection ConstantConditions
            return fromBitmap(BitmapDescriptor.fromResource(R.drawable.ic_location_pin_filled_24dp));
        }

        @Override public @NonNull BitmapDescriptor defaultMarker(float hue) {
            //noinspection ConstantConditions
            return fromBitmap(BitmapDescriptor.fromResource(R.drawable.ic_location_pin_filled_24dp));
        }

        @Override public @Nullable BitmapDescriptor fromAsset(@NonNull String assetName) {
            //noinspection ConstantConditions
            if (assetName == null) {
                return null;
            }

            try {
                final @NonNull AssetManager manager = MapKit.getApplicationContext().getAssets();
                return fromBitmap(BitmapFactory.decodeStream(manager.open(assetName)));
            } catch (IOException ignored) {
                return null;
            }
        }

        @Override public @Nullable BitmapDescriptor fromBitmap(@Nullable Bitmap image) {
            return image == null ? null : wrap(MapImageFactory.fromBitmap(image));
        }

        @Override public @Nullable BitmapDescriptor fromFile(@NonNull String fileName) {
            //noinspection ConstantConditions
            if (fileName == null) {
                return null;
            }

            try {
                return fromBitmap(
                        BitmapFactory.decodeStream(
                                MapKit.getApplicationContext().openFileInput(fileName)
                        )
                );
            } catch (FileNotFoundException ignored) {
                return null;
            }
        }

        @Override public @Nullable BitmapDescriptor fromPath(@NonNull String absolutePath) {
            //noinspection ConstantConditions
            if (absolutePath == null) {
                return null;
            }

            return fromBitmap(BitmapFactory.decodeFile(absolutePath));
        }

        @Override public @Nullable BitmapDescriptor fromResource(@DrawableRes int resourceId) {
            if (resourceId == 0) {
                return null;
            }

            return fromBitmap(BitmapDescriptor.fromResource(resourceId));
        }
    };


    private final @NonNull com.here.sdk.mapviewlite.MapImage mDelegate;

    private HereBitmapDescriptor(@NonNull com.here.sdk.mapviewlite.MapImage delegate) {
        mDelegate = delegate;
    }

    @Override public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        HereBitmapDescriptor that = (HereBitmapDescriptor) obj;

        return mDelegate.equals(that.mDelegate);
    }

    @Override public int hashCode() {
        return mDelegate.hashCode();
    }

    @Override public @NonNull String toString() {
        return mDelegate.toString();
    }


    @Contract("null -> null; !null -> !null")
    static @Nullable BitmapDescriptor wrap(@Nullable com.here.sdk.mapviewlite.MapImage delegate) {
        return delegate == null ? null : new HereBitmapDescriptor(delegate);
    }

    @Contract("null -> null; !null -> !null")
    static @Nullable com.here.sdk.mapviewlite.MapImage unwrap(@Nullable BitmapDescriptor wrapped) {
        return wrapped == null ? null : ((HereBitmapDescriptor) wrapped).mDelegate;
    }

}
