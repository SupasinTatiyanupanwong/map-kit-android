/*
 * Copyright (C) 2020 Supasin Tatiyanupanwong
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package me.tatiyanupanwong.supasin.android.libraries.kits.maps.model;

import androidx.annotation.NonNull;

/**
 * An immutable class that aggregates all camera position parameters such as location, zoom level,
 * tilt angle, and bearing.
 *
 * <p>Use {@link CameraPosition.Builder} to construct a {@link CameraPosition} instance, which you
 * can then use in conjunction with {@link CameraUpdate.Factory}.
 *
 * @since 1.0.0
 */
public interface CameraPosition {

    /**
     * Gets the location that the camera is pointing at.
     *
     * @return The location that the camera is pointing at.
     */
    @NonNull
    LatLng getTarget();

    /**
     * Gets zoom level near the center of the screen. See {@link Builder#zoom(float) zoom(float)}
     * for the definition of the camera's zoom level.
     *
     * @return Zoom level near the center of the screen.
     */
    float getZoom();

    /**
     * Gets the angle, in degrees, of the camera angle from the nadir (directly facing the
     * Earth). See {@link Builder#tilt(float) tilt(float)} for details of restrictions on the
     * range of values.
     *
     * @return The angle, in degrees, of the camera angle from the nadir (directly facing the
     * Earth).
     */
    float getTilt();

    /**
     * Gets direction that the camera is pointing in, in degrees clockwise from north.
     *
     * @return Direction that the camera is pointing in, in degrees clockwise from north.
     */
    float getBearing();


    /**
     * Builds camera position.
     */
    interface Builder {
        /**
         * Sets the location that the camera is pointing at.
         *
         * @param location The location that the camera is pointing at.
         * @return This {@link Builder} object for method chaining.
         */
        @NonNull
        Builder target(LatLng location);

        /**
         * Sets the zoom level of the camera. Zoom level is defined such that at zoom level 0,
         * the whole world is approximately 256dp wide (assuming that the camera is not tilted).
         * Increasing the zoom level by 1 doubles the width of the world on the screen.
         * Hence at zoom level N, the width of the world is approximately 256 * 2<sup>N</sup> dp,
         * i.e., at zoom level 2, the whole world is approximately 1024dp wide.
         *
         * When changing the camera position for a map, the zoom level of the camera is restricted
         * to a certain range depending on various factors including location, map type and map
         * size. Use {@link Map#getMinZoomLevel} and {@link Map#getMaxZoomLevel} to find the
         * restrictions. Note that the camera zoom need not be an integer value.
         *
         * @param zoom The zoom level of the camera.
         * @return This {@link Builder} object for method chaining.
         */
        @NonNull
        Builder zoom(float zoom);

        /**
         * Sets the angle, in degrees, of the camera from the nadir (directly facing the Earth).
         * When changing the camera position for a map, this value is restricted depending on the
         * zoom level of the camera. The restrictions are as follows:
         *
         * <ul>
         * <li>For zoom levels less than 10 the maximum is 30.
         * <li>For zoom levels from 10 to 14 the maximum increases linearly from 30 to 45 (e.g. at
         * zoom level 12, the maximum is 37.5).
         * <li>For zoom levels from 14 to 15.5 the maximum increases linearly from 45 to 67.5.
         * <li>For zoom levels greater than 15.5 the maximum is 67.5.
         * </ul>
         *
         * <p>The minimum is always 0 (directly down). If you specify a value outside this range
         * and try to move the camera to this camera position it will be clamped to these bounds.
         *
         * @param tilt The angle, in degrees, of the camera from the nadir (directly facing the
         * Earth).
         * @return This {@link Builder} object for method chaining.
         */
        @NonNull
        Builder tilt(float tilt);

        /**
         * Sets the direction that the camera is pointing in, in degrees clockwise from north.
         *
         * @param bearing The direction that the camera is pointing in, in degrees clockwise from
         * north.
         * @return This {@link Builder} object for method chaining.
         */
        @NonNull
        Builder bearing(float bearing);

        /**
         * Builds a {@link CameraPosition}.
         *
         * @return The constructed {@link CameraPosition} object.
         */
        @NonNull
        CameraPosition build();
    }

}
