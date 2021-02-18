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

import android.content.Context;
import android.graphics.Bitmap;

import androidx.annotation.FloatRange;
import androidx.annotation.NonNull;

/**
 * Defines a Bitmap image. For a marker, this class can be used to set the image of the marker
 * icon. For a ground overlay, it can be used to set the image to place on the surface of the
 * earth.
 *
 * <p>To obtain a {@link BitmapDescriptor} use the factory class {@link BitmapDescriptor.Factory}.
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
         * @return The {@link BitmapDescriptor} that was loaded from the asset or null if failed
         * to load.
         */
        @NonNull
        BitmapDescriptor fromResource(int resourceId);

        /**
         * Creates a {@link BitmapDescriptor} using the name of a Bitmap image in the assets
         * directory.
         *
         * @param assetName The name of a Bitmap image in the assets directory.
         * @return The {@link BitmapDescriptor} that was loaded from the asset or null if failed
         * to load.
         */
        @NonNull
        BitmapDescriptor fromAsset(String assetName);

        /**
         * Creates a {@link BitmapDescriptor} using the name of a Bitmap image file located in the
         * internal storage. In particular, this calls {@link Context#openFileInput(String)
         * openFileInput(String)}.
         *
         * @param fileName The name of the Bitmap image file.
         * @return The {@link BitmapDescriptor} that was loaded from the asset or null if failed
         * to load.
         */
        @NonNull
        BitmapDescriptor fromFile(String fileName);

        /**
         * Creates a {@link BitmapDescriptor} from the absolute file path of a Bitmap image.
         *
         * @param absolutePath The absolute file path of a Bitmap image.
         * @return The {@link BitmapDescriptor} that was loaded from the asset or null if failed
         * to load.
         */
        @NonNull
        BitmapDescriptor fromPath(String absolutePath);

        /**
         * Creates a {@link BitmapDescriptor} that refers to the default marker image.
         *
         * @return The {@link BitmapDescriptor} that refers to the default marker image.
         */
        @NonNull
        BitmapDescriptor defaultMarker();

        /**
         * Creates a {@link BitmapDescriptor} that refers to the default marker image.
         *
         * @param hue The hue of the marker. Value must be greater or equal to 0 and less than 360.
         * @return The {@link BitmapDescriptor} that refers to the default marker image.
         */
        @NonNull
        BitmapDescriptor defaultMarker(
                @FloatRange(from = 0, to = 360, toInclusive = false) float hue);

        /**
         * Creates a {@link BitmapDescriptor} from a given Bitmap image.
         *
         * @param image The bitmap image.
         * @return The {@link BitmapDescriptor} from a given Bitmap image.
         */
        @NonNull
        BitmapDescriptor fromBitmap(Bitmap image);
    }

}
