/*
 * Copyright 2020 Supasin Tatiyanupanwong
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

package dev.supasintatiyanupanwong.libraries.android.kits.maps.model;

import static androidx.annotation.RestrictTo.Scope.LIBRARY_GROUP;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import androidx.annotation.DrawableRes;
import androidx.annotation.FloatRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;

import dev.supasintatiyanupanwong.libraries.android.kits.maps.MapKit;

/**
 * Defines a Bitmap image. For a marker, this class can be used to set the image of the marker
 * icon. For a ground overlay, it can be used to set the image to place on the surface of the
 * earth.
 * <p>
 * To obtain a {@link BitmapDescriptor} use the factory class {@link BitmapDescriptor.Factory}.
 *
 * @since 1.0.0
 */
public interface BitmapDescriptor {

    /**
     * Used to create a definition of a Bitmap image, used for marker icons and ground overlays.
     */
    interface Factory {
        float HUE_RED = 0.0F;
        float HUE_ORANGE = 30.0F;
        float HUE_YELLOW = 60.0F;
        float HUE_GREEN = 120.0F;
        float HUE_CYAN = 180.0F;
        float HUE_AZURE = 210.0F;
        float HUE_BLUE = 240.0F;
        float HUE_VIOLET = 270.0F;
        float HUE_MAGENTA = 300.0F;
        float HUE_ROSE = 330.0F;

        /**
         * Creates a {@link BitmapDescriptor} using the resource ID of a Bitmap image.
         *
         * @param resourceId The resource ID of a Bitmap image.
         * @return The {@link BitmapDescriptor} for the image with the given resource ID or
         * {@code null} if failed to load.
         */
        @Nullable BitmapDescriptor fromResource(@DrawableRes int resourceId);

        /**
         * Creates a {@link BitmapDescriptor} using the name of a Bitmap image in the assets
         * directory.
         *
         * @param assetName The name of a Bitmap image in the assets directory.
         * @return The {@link BitmapDescriptor} that was loaded from the asset or {@code null}
         * if failed to load.
         */
        @Nullable BitmapDescriptor fromAsset(@NonNull String assetName);

        /**
         * Creates a {@link BitmapDescriptor} using the name of a Bitmap image file located in
         * the internal storage. In particular, this calls {@link Context#openFileInput(String)
         * openFileInput(String)}.
         *
         * @param fileName The name of the Bitmap image file.
         * @return The {@link BitmapDescriptor} for the image in the app's internal storage or
         * {@code null} if failed to load.
         */
        @Nullable BitmapDescriptor fromFile(@NonNull String fileName);

        /**
         * Creates a {@link BitmapDescriptor} from the absolute file path of a Bitmap image.
         *
         * @param absolutePath The absolute file path of a Bitmap image.
         * @return The {@link BitmapDescriptor} that was loaded from the absolute path or
         * {@code null} if failed to load.
         */
        @Nullable BitmapDescriptor fromPath(@NonNull String absolutePath);

        /**
         * Creates a {@link BitmapDescriptor} that refers to a colorization of the default marker
         * image.
         *
         * @param hue The hue of the marker. Value must be greater or equal to 0 and less than 360.
         * @return The {@link BitmapDescriptor} for the colored default marker image.
         */
        @NonNull BitmapDescriptor defaultMarker(
                @FloatRange(from = 0, to = 360, toInclusive = false) float hue
        );

        /**
         * Creates a {@link BitmapDescriptor} that refers to a colorization of the default marker
         * image. For convenience, there is a predefined set of hue values. See example {@link
         * #HUE_YELLOW}.
         *
         * @return The {@link BitmapDescriptor} that refers to the default marker image.
         */
        @NonNull BitmapDescriptor defaultMarker();

        /**
         * Creates a {@link BitmapDescriptor} from a given Bitmap image.
         *
         * @param image The image referenced in the descriptor.
         * @return The {@link BitmapDescriptor} from a given Bitmap image.
         */
        @Nullable BitmapDescriptor fromBitmap(@NonNull Bitmap image);
    }


    @SuppressLint("Range")
    @RestrictTo(LIBRARY_GROUP)
    static @Nullable Bitmap fromResourceInternal(@DrawableRes int resourceId) {
        return fromResourceInternal(resourceId, -1);
    }

    @RestrictTo(LIBRARY_GROUP)
    static @Nullable Bitmap fromResourceInternal(
            @DrawableRes int resourceId,
            @FloatRange(from = 0, to = 360, toInclusive = false) float hue
    ) {
        if (resourceId == 0) {
            return null;
        }

        final @Nullable Drawable drawable =
                ContextCompat.getDrawable(MapKit.getApplicationContext(), resourceId);
        if (drawable == null) {
            return null;
        }

        if (hue != -1) {
            DrawableCompat.setTint(drawable.mutate(), Color.HSVToColor(new float[] { hue, 1, 1 }));
        }

        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        } else {
            Bitmap bitmap = Bitmap.createBitmap(
                    drawable.getIntrinsicWidth(),
                    drawable.getIntrinsicHeight(),
                    Bitmap.Config.ARGB_8888
            );
            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            drawable.draw(canvas);
            return bitmap;
        }
    }

}
