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

import android.graphics.Point;

import androidx.annotation.NonNull;

/**
 * Defines a camera move. An object of this type can be used to modify a map's camera by calling
 * {@link MapClient#animateCamera(CameraUpdate) animateCamera(CameraUpdate)}, {@link
 * MapClient#animateCamera(CameraUpdate, MapClient.CancelableCallback) animateCamera(CameraUpdate,
 * MapClient.CancelableCallback)} or {@link MapClient#moveCamera(CameraUpdate) moveCamera(
 * CameraUpdate)}.
 *
 * <p>To obtain a {@link CameraUpdate} use the factory class {@link CameraUpdate.Factory}.
 *
 * @since 1.0.0
 */
public interface CameraUpdate {

    /**
     * A class containing methods for creating {@link CameraUpdate} objects that change a map's
     * camera. To modify the map's camera, call {@link MapClient#animateCamera(CameraUpdate)
     * animateCamera(CameraUpdate)}, {@link MapClient#animateCamera(CameraUpdate,
     * MapClient.CancelableCallback) animateCamera(CameraUpdate, MapClient.CancelableCallback)}
     * or {@link MapClient#moveCamera(CameraUpdate) moveCamera(CameraUpdate)},using a {@link
     * CameraUpdate} object created with this class.
     */
    interface Factory {
        /**
         * Returns a {@link CameraUpdate} that zooms in on the map by moving the viewpoint's
         * height closer to the Earth's surface. The zoom increment is 1.0.
         *
         * @return A {@link CameraUpdate} containing the transformation.
         */
        @NonNull CameraUpdate zoomIn();

        /**
         * Returns a {@link CameraUpdate} that zooms out on the map by moving the viewpoint's
         * height farther away from the Earth's surface. The zoom increment is -1.0.
         *
         * @return A {@link CameraUpdate} containing the transformation.
         */
        @NonNull CameraUpdate zoomOut();

        /**
         * Returns a {@link CameraUpdate} that scrolls the camera over the map, shifting the
         * center of view by the specified number of pixels in the x and y directions.
         *
         * <p>The scrolling is relative to the camera's current orientation. For example, if the
         * camera is bearing 90 degrees, then east is "up" and scrolling right will move the
         * camera south.
         *
         * @param xPixel The number of pixels to scroll horizontally. A positive value moves the
         * camera to the right, with respect to its current orientation. A negative value moves
         * the camera to the left, with respect to its current orientation.
         * @param yPixel The number of pixels to scroll vertically. A positive value moves the
         * camera downwards, with respect to its current orientation. A negative value moves the
         * camera upwards, with respect to its current orientation.
         * @return A {@link CameraUpdate} containing the transformation.
         */
        @NonNull CameraUpdate scrollBy(float xPixel, float yPixel);

        /**
         * Returns a {@link CameraUpdate} that moves the camera viewpoint to a particular zoom
         * level.
         *
         * @param zoom The desired zoom level, in the range of 2.0 to 21.0. Values below this
         * range are set to 2.0, and values above it are set to 21.0. Increase the value to
         * zoom in. Not all areas have tiles at the largest zoom levels.
         * @return A {@link CameraUpdate} containing the transformation.
         */
        @NonNull CameraUpdate zoomTo(float zoom);

        /**
         * Returns a {@link CameraUpdate} that shifts the zoom level of the current camera
         * viewpoint.
         *
         * <p>This method is different to {@link #zoomTo(float)} in that zoom is relative
         * to the current camera.
         *
         * @param amount Amount to change the zoom level. Positive values indicate zooming closer
         * to the surface of the Earth while negative values indicate zooming away from the
         * surface of the Earth.
         * @return A {@link CameraUpdate} containing the transformation.
         */
        @NonNull CameraUpdate zoomBy(float amount);

        /**
         * Returns a {@link CameraUpdate} that shifts the zoom level of the current camera
         * viewpoint.
         *
         * <p>A point specified by focus will remain fixed (i.e., it corresponds to the same
         * lat/long both before and after the zoom process).
         *
         * <p>This method is different to {@link #zoomTo(float)} in that zoom is relative to the
         * current camera.
         *
         * <p>For example, if the {@link LatLng} (11.11, 22.22) is currently at the screen location
         * (23, 45). After calling this method with a zoom amount and this {@link LatLng}, the
         * screen location of this {@link LatLng} will still be (23, 45).
         *
         * @param amount Amount to change the zoom level. Positive values indicate zooming closer
         * to the surface of the Earth while negative values indicate zooming away from the
         * surface of the Earth.
         * @param focus Pixel location on the screen that is to remain fixed after the zooming
         * process. The lat/long that was at that pixel location before the camera move will
         * remain the same after the camera has moved.
         * @return A {@link CameraUpdate} containing the transformation.
         */
        @NonNull CameraUpdate zoomBy(float amount, @NonNull Point focus);

        /**
         * Returns a {@link CameraUpdate} that moves the camera to a specified
         * {@link CameraPosition}. In effect, this creates a transformation from the
         * {@link CameraPosition} object's latitude, longitude, zoom level, bearing and tilt.
         *
         * @return A {@link CameraUpdate} containing the transformation.
         */
        @NonNull CameraUpdate newCameraPosition(@NonNull CameraPosition cameraPosition);

        /**
         * Returns a {@link CameraUpdate} that moves the camera to a specified
         * {@link CameraPosition}. In effect, this creates a transformation from the
         * {@link CameraPosition} object's latitude, longitude, zoom level, bearing and tilt.
         *
         * @param latLng A {@link LatLng} object containing the desired latitude and longitude.
         * @return A {@link CameraUpdate} containing the transformation.
         */
        @NonNull CameraUpdate newLatLng(@NonNull LatLng latLng);

        /**
         * Returns a {@link CameraUpdate} that moves the center of the screen to a latitude and
         * longitude specified by a {@link LatLng} object, and moves to the given zoom level.
         *
         * @param latLng A {@link LatLng} containing the desired latitude and longitude.
         * @param zoom the desired zoom level, in the range of 2.0 to 21.0. Values below this
         * range are set to 2.0, and values above it are set to 21.0. Increase the value to zoom
         * in. Not all areas have tiles at the largest zoom levels.
         * @return A {@link CameraUpdate} containing the transformation.
         */
        @NonNull CameraUpdate newLatLngZoom(@NonNull LatLng latLng, float zoom);

        /**
         * Returns a {@link CameraUpdate} that transforms the camera such that the specified
         * latitude/longitude bounds are centered on screen at the greatest possible zoom level.
         * You can specify padding, in order to inset the bounding box from the map view's edges.
         * The returned {@link CameraUpdate} has a bearing of 0 and a tilt of 0.
         *
         * <p>Do not change the camera with this camera update until the map has undergone layout
         * (in order for this method to correctly determine the appropriate bounding box and zoom
         * level, the map must have a size). Otherwise an {@link IllegalStateException} will be
         * thrown. It is NOT sufficient for the map to be available; the view containing the map
         * must have also undergone layout such that its dimensions have been determined. If you
         * cannot be sure that this has occurred, use {@link #newLatLngBounds(LatLngBounds, int,
         * int, int)} instead and provide the dimensions of the map manually.
         *
         * @param bounds Region to fit on screen.
         * @param padding Space (in px) to leave between the bounding box edges and the view
         * edges. This value is applied to all four sides of the bounding box.
         * @return A {@link CameraUpdate} containing the transformation.
         */
        @NonNull CameraUpdate newLatLngBounds(@NonNull LatLngBounds bounds, int padding);

        /**
         * Returns a {@link CameraUpdate} that transforms the camera such that the specified
         * latitude/longitude bounds are centered on screen within a bounding box of specified
         * dimensions at the greatest possible zoom level. You can specify additional padding,
         * to further restrict the size of the bounding box. The returned {@link CameraUpdate}
         * has a bearing of 0 and a tilt of 0.
         *
         * <p>Unlike {@link #newLatLngBounds(LatLngBounds, int)}, you can use the {@link
         * CameraUpdate} returned by this method to change the camera prior to the map's the
         * layout phase, because the arguments specify the desired size of the bounding box.
         *
         * @param bounds The region to fit in the bounding box.
         * @param width Bounding box width in pixels (px).
         * @param height Bounding box height in pixels (px).
         * @param padding Additional size restriction (in px) of the bounding box.
         * @return A {@link CameraUpdate} containing the transformation.
         */
        @NonNull CameraUpdate newLatLngBounds(
                @NonNull LatLngBounds bounds,
                int width,
                int height,
                int padding);
    }

}
