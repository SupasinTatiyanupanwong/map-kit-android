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

import androidx.annotation.FloatRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * An icon placed at a particular point on the map's surface. A marker icon is drawn oriented
 * against the device's screen rather than the map's surface; i.e., it will not necessarily
 * change orientation due to map rotations, tilting, or zooming.
 *
 * @since 1.0.0
 */
public interface Marker {

    /**
     * Removes this marker from the map. After a marker has been removed, the behavior of all
     * its methods is undefined.
     */
    void remove();

    /**
     * Gets the id of the marker. The id will be unique amongst all Markers on a map.
     *
     * @return This marker's id.
     */
    @NonNull String getId();

    /**
     * Sets the position of the marker.
     */
    void setPosition(@NonNull LatLng latLng);

    /**
     * Returns the position of the marker.
     *
     * @return A {@link LatLng} object specifying the marker's current position.
     */
    @NonNull LatLng getPosition();

    /**
     * Sets the zIndex of the marker.
     */
    void setZIndex(float zIndex);

    /**
     * Gets the zIndex of the marker.
     *
     * @return This marker's zIndex.
     */
    float getZIndex();

    /**
     * Sets the icon for the marker.
     *
     * @param iconDescriptor If {@code null}, the default marker is used.
     */
    void setIcon(@Nullable BitmapDescriptor iconDescriptor);

    /**
     * Sets the anchor point for the marker.
     *
     * <p>The anchor specifies the point in the icon image that is anchored to the marker's
     * position on the Earth's surface.
     *
     * <p>The anchor point is specified in the continuous space [0.0, 1.0] x [0.0, 1.0], where
     * (0, 0) is the top-left corner of the image, and (1, 1) is the bottom-right corner. The
     * anchoring point in a <i>W</i> x <i>H</i> image is the nearest discrete grid point in a
     * <i>(W + 1)</i> x <i>(H + 1)</i> grid, obtained by scaling the then rounding. For example,
     * in a 4 x 2 image, the anchor point (0.7, 0.6) resolves to the grid point at (3, 1).
     *
     * <pre>
     * *-----+-----+-----+-----*
     * |     |     |     |     |
     * |     |     |     |     |
     * +-----+-----+-----+-----+
     * |     |     |   X |     |   (U, V) = (0.7, 0.6)
     * |     |     |     |     |
     * *-----+-----+-----+-----*
     *
     * *-----+-----+-----+-----*
     * |     |     |     |     |
     * |     |     |     |     |
     * +-----+-----+-----X-----+   (X, Y) = (3, 1)
     * |     |     |     |     |
     * |     |     |     |     |
     * *-----+-----+-----+-----*
     * </pre>
     *
     * @param anchorU u-coordinate of the anchor, as a ratio of the image width.
     * @param anchorV v-coordinate of the anchor, as a ratio of the image height.
     */
    void setAnchor(
            @FloatRange(from = 0.0, to = 1.0) float anchorU,
            @FloatRange(from = 0.0, to = 1.0) float anchorV
    );

    /**
     * Specifies the point in the marker image at which to anchor the info window when it is
     * displayed. This is specified in the same coordinate system as the anchor. See
     * {@link #setAnchor(float, float)} for more details.
     * The default is the top middle of the image.
     *
     * @param anchorU u-coordinate of the anchor, as a ratio of the image width.
     * @param anchorV v-coordinate of the anchor, as a ratio of the image height.
     */
    void setInfoWindowAnchor(
            @FloatRange(from = 0.0, to = 1.0) float anchorU,
            @FloatRange(from = 0.0, to = 1.0) float anchorV
    );

    /**
     * Sets the title of the marker.
     *
     * @param title If {@code null}, the title is cleared.
     */
    void setTitle(@Nullable String title);

    /**
     * Gets the title of the marker.
     *
     * @return A string containing the marker's title.
     */
    String getTitle();

    /**
     * Sets the snippet of the marker.
     *
     * @param snippet If {@code null}, the snippet is cleared.
     */
    void setSnippet(@Nullable String snippet);

    /**
     * Gets the snippet of the marker.
     *
     * @return A string containing the marker's snippet.
     */
    String getSnippet();

    /**
     * Sets the draggability of the marker. When a marker is draggable, it can be moved by the user
     * by long pressing on the marker.
     */
    void setDraggable(boolean draggable);

    /**
     * Gets the draggability of the marker. When a marker is draggable, it can be moved by the user
     * by long pressing on the marker.
     *
     * @return {@code true} if the marker is draggable; {@code false} otherwise.
     */
    boolean isDraggable();

    /**
     * Shows the info window of this marker on the map, if this marker {@link #isVisible()}.
     */
    void showInfoWindow();

    /**
     * Hides the info window if it is shown from this marker.
     *
     * <p>This method has no effect if this marker is not visible.
     */
    void hideInfoWindow();

    /**
     * Returns whether the info window is currently shown above this marker. This does not consider
     * whether or not the info window is actually visible on screen.
     */
    boolean isInfoWindowShown();

    /**
     * Sets the visibility of this marker. If set to false and an info window is currently showing
     * for this marker, this will hide the info window.
     *
     * @param visible {@code true} to make this marker visible; {@code false} to make it invisible.
     */
    void setVisible(boolean visible);

    /**
     * Gets the visibility setting of this marker. Note that this does not indicate whether the
     * marker is within the screen's viewport. It indicates whether the marker will be drawn if
     * it is contained in the screen's viewport.
     *
     * @return This marker's visibility.
     */
    boolean isVisible();

    /**
     * Sets whether this marker should be flat against the map {@code true} or a billboard facing
     * the camera {@code false}.
     */
    void setFlat(boolean flat);

    /**
     * Gets the flat setting of the Marker.
     *
     * @return {@code true} if the marker is flat against the map; {@code false} if the marker
     * should face the camera.
     */
    boolean isFlat();

    /**
     * Sets the rotation of the marker in degrees clockwise about the marker's anchor point. The
     * axis of rotation is perpendicular to the marker. A rotation of 0 corresponds to the default
     * position of the marker.
     */
    void setRotation(float rotation);

    /**
     * Gets the rotation of the marker.
     *
     * @return The rotation of the marker in degrees clockwise from the default position.
     */
    float getRotation();

    /**
     * Sets the alpha (opacity) of the marker. This is a value from 0 to 1, where 0 means the
     * marker is completely transparent and 1 means the marker is completely opaque.
     */
    void setAlpha(float alpha);

    /**
     * Gets the alpha of the marker.
     *
     * @return The alpha of the marker in the range [0, 1].
     */
    @FloatRange(from = 0.0, to = 1.0)
    float getAlpha();

    /**
     * Sets the tag for the marker.
     *
     * <p>You can use this property to associate an arbitrary {@code Object} with this marker. For
     * example, the {@code Object} can contain data about what the marker represents. This is easier
     * than storing a separate {@code Map<Marker, Object>}. As another example, you can associate a
     * {@code String} ID corresponding to the ID from a data set. It is your responsibility to call
     * {@code setTag(null)} to clear the tag when you no longer need it, to prevent memory leaks in
     * your app.
     *
     * @param tag If {@code null}, the tag is cleared.
     */
    void setTag(@Nullable Object tag);

    /**
     * Gets the tag for the marker.
     *
     * @return The tag if a tag was set with {@code setTag}; {@code null} if no tag has been set.
     */
    @Nullable Object getTag();


    /**
     * Defines options for a {@link Marker}.
     */
    interface Options {
        /**
         * Sets the location for the marker.
         */
        @NonNull Options position(@NonNull LatLng latLng);

        /**
         * Sets the zIndex for the marker.
         */
        @NonNull Options zIndex(float zIndex);

        /**
         * Sets the icon for the marker.
         *
         * @param iconDescriptor If {@code null} , the default marker is used.
         */
        @NonNull Options icon(@Nullable BitmapDescriptor iconDescriptor);

        /**
         * Sets the anchor point for the marker.
         *
         * <p>The anchor specifies the point in the icon image that is anchored to the marker's
         * position on the Earth's surface.
         *
         * <p>The anchor point is specified in the continuous space [0.0, 1.0] x [0.0, 1.0], where
         * (0, 0) is the top-left corner of the image, and (1, 1) is the bottom-right corner. The
         * anchoring point in a <i>W</i> x <i>H</i> image is the nearest discrete grid point in a
         * <i>(W + 1)</i> x <i>(H + 1)</i> grid, obtained by scaling the then rounding. For example,
         * in a 4 x 2 image, the anchor point (0.7, 0.6) resolves to the grid point at (3, 1).
         *
         * <pre>
         * *-----+-----+-----+-----*
         * |     |     |     |     |
         * |     |     |     |     |
         * +-----+-----+-----+-----+
         * |     |     |   X |     |   (U, V) = (0.7, 0.6)
         * |     |     |     |     |
         * *-----+-----+-----+-----*
         *
         * *-----+-----+-----+-----*
         * |     |     |     |     |
         * |     |     |     |     |
         * +-----+-----+-----X-----+   (X, Y) = (3, 1)
         * |     |     |     |     |
         * |     |     |     |     |
         * *-----+-----+-----+-----*
         * </pre>
         *
         * @param anchorU u-coordinate of the anchor, as a ratio of the image width.
         * @param anchorV v-coordinate of the anchor, as a ratio of the image height.
         */
        @NonNull Options anchor(
                @FloatRange(from = 0.0, to = 1.0) float anchorU,
                @FloatRange(from = 0.0, to = 1.0) float anchorV);

        /**
         * Specifies the point in the marker image at which to anchor the info window when it is
         * displayed. This is specified in the same coordinate system as the anchor. See
         * {@link #anchor(float, float)} for more details.
         * The default is the top middle of the image.
         *
         * @param anchorU u-coordinate of the anchor, as a ratio of the image width.
         * @param anchorV v-coordinate of the anchor, as a ratio of the image height.
         */
        @NonNull Options infoWindowAnchor(
                @FloatRange(from = 0.0, to = 1.0) float anchorU,
                @FloatRange(from = 0.0, to = 1.0) float anchorV);

        /**
         * Sets the title for the marker.
         */
        @NonNull Options title(@Nullable String title);

        /**
         * Sets the snippet for the marker.
         */
        @NonNull Options snippet(@Nullable String snippet);

        /**
         * Sets the draggability for the marker.
         */
        @NonNull Options draggable(boolean draggable);

        /**
         * Sets the visibility for the marker.
         */
        @NonNull Options visible(boolean visible);

        /**
         * Sets whether this marker should be flat against the map {@code true} or a billboard
         * facing the camera {@code false}. If the marker is flat against the map, it will remain
         * stuck to the map as the camera rotates and tilts but will still remain the same size as
         * the camera zooms, unlike a {@link GroundOverlay}. If the marker is a billboard, it will
         * always be drawn facing the camera and will rotate and tilt with the camera. The default
         * value is {@code false}.
         */
        @NonNull Options flat(boolean flat);

        /**
         * Sets the rotation of the marker in degrees clockwise about the marker's anchor point.
         * The axis of rotation is perpendicular to the marker. A rotation of 0 corresponds to the
         * default position of the marker. When the marker is flat on the map, the default position
         * is North aligned and the rotation is such that the marker always remains flat on the map.
         * When the marker is a billboard, the default position is pointing up and the rotation is
         * such that the marker is always facing the camera. The default value is 0.
         */
        @NonNull Options rotation(float rotation);

        /**
         * Sets the alpha (opacity) of the marker. This is a value from 0 to 1, where 0 means the
         * marker is completely transparent and 1 means the marker is completely opaque.
         */
        @NonNull Options alpha(@FloatRange(from = 0.0, to = 1.0) float alpha);

        /**
         * Gets the title set for this {@link Marker.Options object}.
         *
         * @return A {@link LatLng} object specifying the marker's current position.
         */
        LatLng getPosition();

        /**
         * Gets the title set for this {@link Marker.Options object}.
         *
         * @return A string containing the marker's title.
         */
        String getTitle();

        /**
         * Gets the snippet set for this {@link Marker.Options object}.
         *
         * @return A string containing the marker's snippet.
         */
        String getSnippet();

        /**
         * Gets the custom icon descriptor set for this {@link Marker.Options} object.
         *
         * @return A {@link BitmapDescriptor} representing the custom icon, or {@code null} if no
         * custom icon is set.
         */
        @Nullable BitmapDescriptor getIcon();

        /**
         * Horizontal distance, normalized to [0, 1], of the anchor from the left edge.
         *
         * @return The u value of the anchor.
         */
        @FloatRange(from = 0.0, to = 1.0)
        float getAnchorU();

        /**
         * Vertical distance, normalized to [0, 1], of the anchor from the top edge.
         *
         * @return The v value of the anchor.
         */
        @FloatRange(from = 0.0, to = 1.0)
        float getAnchorV();

        /**
         * Gets the draggability setting for this {@link Marker.Options} object.
         *
         * @return {@code true} if the marker is draggable; otherwise, returns {@code false}.
         */
        boolean isDraggable();

        /**
         * Gets the visibility setting for this {@link Marker.Options} object.
         *
         * @return {@code true} if the marker is visible; otherwise, returns {@code false}.
         */
        boolean isVisible();

        /**
         * Gets the flat setting for this {@link Marker.Options} object.
         *
         * @return {@code true} if the marker is flat against the map; {@code false} if the marker
         * should face the camera.
         */
        boolean isFlat();

        /**
         * Gets the rotation set for this {@link Marker.Options} object.
         *
         * @return The rotation of the marker in degrees clockwise from the default position.
         */
        float getRotation();

        /**
         * Horizontal distance, normalized to [0, 1], of the info window anchor from the left edge.
         *
         * @return The u value of the info window anchor.
         */
        @FloatRange(from = 0.0, to = 1.0)
        float getInfoWindowAnchorU();

        /**
         * Vertical distance, normalized to [0, 1], of the info window anchor from the top edge.
         *
         * @return The v value of the info window anchor.
         */
        @FloatRange(from = 0.0, to = 1.0)
        float getInfoWindowAnchorV();

        /**
         * Gets the alpha set for this {@link Marker.Options} object.
         *
         * @return The alpha of the marker.
         */
        @FloatRange(from = 0.0, to = 1.0)
        float getAlpha();

        /**
         * Gets the zIndex set for this {@link Marker.Options} object.
         *
         * @return The zIndex of the marker.
         */
        float getZIndex();
    }

}
