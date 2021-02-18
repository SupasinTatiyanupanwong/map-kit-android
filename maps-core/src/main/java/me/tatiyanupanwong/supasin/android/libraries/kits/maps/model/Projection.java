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

package me.tatiyanupanwong.supasin.android.libraries.kits.maps.model;

import android.graphics.Point;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * A projection is used to translate between on screen location and geographic coordinates on the
 * surface of the Earth ({@link LatLng}). Screen location is in screen pixels (not display pixels)
 * with respect to the top left corner of the map (and not necessarily of the whole screen).
 *
 * @since 1.0.0
 */
public interface Projection {

    /**
     * Returns the geographic location that corresponds to a screen location. The screen location
     * is specified in screen pixels (not display pixels) relative to the top left of the map (not
     * the top left of the whole screen).
     *
     * @param point A {@link Point} on the screen in screen pixels.
     * @return The {@link LatLng} corresponding to the point on the screen, or {@code null} if the
     * ray through the given screen point does not intersect the ground plane (this might be the
     * case if the map is heavily tilted).
     */
    @Nullable
    LatLng fromScreenLocation(Point point);

    /**
     * Returns a screen location that corresponds to a geographical coordinate ({@link LatLng}).
     * The screen location is in screen pixels (not display pixels) relative to the top left of the
     * map (not of the whole screen).
     *
     * @param location A {@link LatLng} on the map to convert to a screen location.
     * @return A {@link Point} representing the screen location in screen pixels.
     */
    @NonNull
    Point toScreenLocation(LatLng location);

    /**
     * Gets a projection of the viewing frustum for converting between screen coordinates and
     * geo-latitude/longitude coordinates.
     *
     * @return The projection of the viewing frustum in its current state.
     */
    @NonNull
    VisibleRegion getVisibleRegion();

}
