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
 * An immutable class representing a latitude/longitude aligned rectangle.
 *
 * @since 1.0.0
 */
public interface LatLngBounds {

    /**
     * Returns southwest corner of the bound.
     *
     * @return Southwest corner of the bound.
     */
    @NonNull
    LatLng getSouthwest();

    /**
     * Returns northeast corner of the bound.
     *
     * @return Northeast corner of the bound.
     */
    @NonNull
    LatLng getNortheast();

    /**
     * Returns whether this contains the given LatLng.
     *
     * @param point the LatLng to test.
     * @return {@code true} if this contains the given point; {@code false} if not.
     */
    boolean contains(@NonNull LatLng point);

    /**
     * Returns a new {@link LatLngBounds} that extends this LatLngBounds to include the given
     * {@link LatLng}. This will return the smallest LatLngBounds that contains both this and the
     * extra point.
     *
     * <p>In particular, it will consider extending the bounds both in the eastward and westward
     * directions (one of which may cross the antimeridian) and choose the smaller of the two. In
     * the case that both directions result in a LatLngBounds of the same size, this will extend
     * it in the eastward direction.
     *
     * @param point A {@link LatLng} to be included in the new bounds
     * @return A new {@link LatLngBounds} that contains this and the extra point.
     */
    @NonNull
    LatLngBounds including(@NonNull LatLng point);

    /**
     * Returns the center of this LatLngBounds. The center is simply the average of the coordinates
     * (taking into account if it crosses the antimeridian). This is approximately the geographical
     * center (it would be exact if the Earth were a perfect sphere). It will not necessarily be
     * the center of the rectangle as drawn on the map due to the Mercator projection.
     *
     * @return A {@link LatLng} that is the center of the LatLngBounds.
     */
    @NonNull
    LatLng getCenter();


    /**
     * This is a builder that is able to create a minimum bound based on a set of LatLng points.
     */
    interface Builder {
        /**
         * Includes this point for building of the bounds. The bounds will be extended in a minimum
         * way to include this point.
         *
         * <p>More precisely, it will consider extending the bounds both in the eastward and
         * westward directions (one of which may cross the antimeridian) and choose the smaller of
         * the two. In the case that both directions result in a LatLngBounds of the same size,
         * this will extend it in the eastward direction. For example, adding points (0, -179) and
         * (1, 179) will create a bound crossing the 180 longitude.
         *
         * @param point A {@link LatLng} to be included in the bounds.
         * @return This {@link Builder} object for method chaining.
         */
        @NonNull
        Builder include(@NonNull LatLng point);

        /**
         * Creates the LatLng bounds.
         *
         * @return The created {@link LatLngBounds} object.
         * @throws IllegalStateException If no points have been included.
         */
        @NonNull
        LatLngBounds build();
    }

}
