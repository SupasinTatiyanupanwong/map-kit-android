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

import androidx.annotation.FloatRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.UiThread;

/**
 * A ground overlay is an image that is fixed to a map.
 *
 * <p>Methods in this class must be called on the Android UI thread. If not, an {@link
 * IllegalStateException} will be thrown at runtime.
 */
@UiThread
public interface GroundOverlay {

    /**
     * Removes this ground overlay from the map. After a ground overlay has been removed, the
     * behavior of all its methods is undefined.
     */
    void remove();

    /**
     * Gets this ground overlay's id. The id will be unique amongst all GroundOverlays on a map.
     *
     * @return The id of this ground overlay.
     */
    String getId();

    /**
     * Sets the position of the ground overlay by changing the location of the anchor point.
     * Preserves all other properties of the image.
     *
     * @param latLng A {@link LatLng} that is the new location to place the anchor point.
     */
    void setPosition(LatLng latLng);

    /**
     * Gets the location of the anchor point.
     *
     * @return The position on the map (a {@link LatLng}).
     */
    LatLng getPosition();

    /**
     * Sets the image for the Ground Overlay. The new image will occupy the same bounds as the old
     * image.
     *
     * @param imageDescriptor the {@link BitmapDescriptor} to use for this ground overlay.
     */
    void setImage(@NonNull BitmapDescriptor imageDescriptor);

    /**
     * Gets the height of the ground overlay.
     *
     * @return The height of the ground overlay in meters.
     */
    float getWidth();

    /**
     * Gets the height of the ground overlay.
     *
     * @return The height of the ground overlay in meters.
     */
    float getHeight();

    /**
     * Sets the width of the ground overlay. The height of the ground overlay will be adapted
     * accordingly to preserve aspect ratio.
     *
     * @param width Width in meters.
     */
    void setDimensions(float width);

    /**
     * Sets the dimensions of the ground overlay. The image will be stretched to fit the dimensions.
     *
     * @param width Width in meters.
     * @param height Height in meters.
     */
    void setDimensions(float width, float height);

    /**
     * Gets the bounds for the ground overlay. This ignores the rotation of the ground overlay.
     *
     * @return A {@link LatLngBounds} that contains the ground overlay, ignoring rotation.
     */
    LatLngBounds getBounds();

    /**
     * Sets the position of the ground overlay by fitting it to the given {@link LatLngBounds}.
     * This method will ignore the rotation (bearing) of the ground overlay when positioning it,
     * but the bearing will still be used when drawing it.
     *
     * @param bounds A {@link LatLngBounds} in which to place the ground overlay
     */
    void setPositionFromBounds(LatLngBounds bounds);

    /**
     * Gets the bearing of the ground overlay in degrees clockwise from north.
     *
     * @return The bearing of the ground overlay.
     */
    float getBearing();

    /**
     * Sets the bearing of the ground overlay (the direction that the vertical axis of the ground
     * overlay points) in degrees clockwise from north. The rotation is performed about the anchor
     * point.
     *
     * @param bearing Bearing in degrees clockwise from north.
     */
    void setBearing(float bearing);

    /**
     * Gets the zIndex of this ground overlay.
     *
     * @return The zIndex of this ground overlay.
     */
    float getZIndex();

    /**
     * Sets the zIndex of this ground overlay.
     *
     * @param zIndex The zIndex of this ground overlay.
     */
    void setZIndex(float zIndex);

    /**
     * Sets the visibility of this ground overlay. When not visible, a ground overlay is not drawn,
     * but it keeps all of its other properties.
     *
     * @param visible If {@code true}, then the ground overlay is visible; otherwise, it is not.
     */
    void setVisible(boolean visible);

    /**
     * Gets the visibility of this ground overlay. Note that this does not return whether the
     * ground overlay is actually on screen, but whether it will be drawn if it is contained
     * in the camera's viewport.
     *
     * @return This ground overlay's visibility.
     */
    boolean isVisible();

    /**
     * Sets the clickability of the ground overlay. If the ground overlay is clickable, your app
     * will receive notifications to the {@link Map.OnGroundOverlayClickListener} when the
     * user clicks the ground overlay. The event listener is registered through {@link
     * Map#setOnGroundOverlayClickListener(Map.OnGroundOverlayClickListener)}.
     *
     * @param clickable New clickability setting for the ground overlay.
     */
    void setClickable(boolean clickable);

    /**
     * Gets the clickability of the ground overlay. If the ground overlay is clickable, your app
     * will receive notifications to the {@link Map.OnGroundOverlayClickListener} when the
     * user clicks the ground overlay. The event listener is registered through {@link
     * Map#setOnGroundOverlayClickListener(Map.OnGroundOverlayClickListener)}.
     *
     * @return {@code true} if the circle is clickable; {@code false} otherwise.
     */
    boolean isClickable();

    /**
     * Sets the transparency of this ground overlay.
     *
     * @param transparency a float in the range {@code [0..1]} where 0 means that the ground
     * overlay is opaque and 1 means that the ground overlay is transparent.
     */
    void setTransparency(@FloatRange(from = 0.0, to = 1.0) float transparency);

    /**
     * Gets the transparency of this ground overlay.
     *
     * @return The transparency of this ground overlay.
     */
    float getTransparency();

    /**
     * Sets the tag for the ground overlay.
     *
     * <p>You can use this property to associate an arbitrary {@code Object} with this ground
     * overlay. For example, the {@code Object} can contain data about what the ground overlay
     * represents. This is easier than storing a separate {@code Map<GroundOverlay, Object>}.
     * As another example, you can associate a String ID corresponding to the ID from a data set.
     * It is your responsibility to call {@code setTag(null)} to clear the tag when you no longer
     * need it, to prevent memory leaks in your app.
     *
     * @param tag If {@code null}, the tag is cleared.
     */
    void setTag(@Nullable Object tag);

    /**
     * Gets the tag for the ground overlay.
     *
     * @return The tag if a tag was set with {@code setTag}; {@code null} if no tag has been set.
     */
    Object getTag();


    /**
     * Defines options for a ground overlay.
     */
    interface Options {
        /**
         * Specifies the image for this ground overlay.
         *
         * <p>To load an image as a texture (which is used to draw the image on a map), it must be
         * converted into an image with sides that are powers of two. This is so that a mipmap can
         * be created in order to render the texture at various zoom levels. Hence, to conserve
         * memory by avoiding this conversion, it is advised that the dimensions of the image are
         * powers of two.
         *
         * @param imageDescriptor The {@link BitmapDescriptor} to use for this ground overlay.
         * @return This {@link Options} object for method chaining.
         *
         * @see <a href="https://en.wikipedia.org/wiki/Mipmap">Mipmap (Wikipedia)</a>
         */
        @NonNull
        Options image(@NonNull BitmapDescriptor imageDescriptor);

        /**
         * Specifies the anchor.
         *
         * <p>The anchor aligns with the ground overlay's location.
         *
         * <p>The anchor point is specified in 2D continuous space where (0,0), (1,0), (0,1) and
         * (1,1) denote the top-left, top-right, bottom-left and bottom-right corners respectively.
         *
         * <pre>
         * *-----+-----+-----+-----*
         * |     |     |     |     |
         * |     |     |     |     |
         * +-----+-----+-----+-----+
         * |     |     |   X |     |   (U, V) = (0.7, 0.6)
         * |     |     |     |     |
         * *-----+-----+-----+-----*
         * </pre>
         *
         * @param anchorU u-coordinate of the anchor
         * @param anchorV v-coordinate of the anchor
         * @return This {@link Options} object for method chaining.
         */
        @NonNull
        Options anchor(
                @FloatRange(from = 0.0, to = 1.0) float anchorU,
                @FloatRange(from = 0.0, to = 1.0) float anchorV);

        /**
         * Specifies the position for this ground overlay using an anchor point (a {@link LatLng})
         * and the width (in meters). The height will be adapted accordingly to preserve aspect
         * ratio.
         *
         * @param location The location on the map {@link LatLng} to which the anchor point in the
         * given image will remain fixed. The anchor will remain fixed to the position on the
         * ground when transformations are applied (e.g., setDimensions, setBearing, etc.).
         * @param width The width of the overlay (in meters). The height will be determined
         * automatically based on the image aspect ratio.
         * @return This {@link Options} object for method chaining.
         */
        @NonNull
        Options position(@NonNull LatLng location, float width);

        /**
         * Specifies the position for this ground overlay using an anchor point (a {@link LatLng}),
         * width and height (both in meters). When rendered, the image will be scaled to fit the
         * dimensions specified.
         *
         * @param location The location on the map LatLng to which the anchor point in the given
         * image will remain fixed. The anchor will remain fixed to the position on the ground when
         * transformations are applied (e.g., setDimensions, setBearing, etc.).
         * @param width The width of the overlay (in meters).
         * @param height The height of the overlay (in meters).
         * @return This {@link Options} object for method chaining.
         */
        @NonNull
        Options position(@NonNull LatLng location, float width, float height);

        /**
         * Specifies the position for this ground overlay.
         *
         * @param bounds A {@link LatLngBounds} in which to place the ground overlay.
         * @return This {@link Options} object for method chaining.
         */
        @NonNull
        Options positionFromBounds(LatLngBounds bounds);

        /**
         * Specifies the bearing of the ground overlay in degrees clockwise from north.
         * The rotation is performed about the anchor point. If not specified, the default is 0
         * (i.e., up on the image points north).
         *
         * <p>Note that latitude-longitude bound applies before the rotation.
         *
         * @param bearing The bearing in degrees clockwise from north. Values outside the range
         * [0, 360) will be normalized.
         * @return This {@link Options} object for method chaining.
         */
        @NonNull
        Options bearing(float bearing);

        /**
         * Specifies the ground overlay's zIndex, i.e., the order in which it will be drawn.
         *
         * @param zIndex The zIndex for this ground overlay.
         * @return This {@link Options} object for method chaining.
         */
        @NonNull
        Options zIndex(float zIndex);

        /**
         * Specifies the visibility for the ground overlay. The default visibility is {@code true}.
         *
         * @param visible The visibility for this ground overlay.
         * @return This {@link Options} object for method chaining.
         */
        @NonNull
        Options visible(boolean visible);

        /**
         * Specifies the transparency of the ground overlay. The default transparency is {@code 0}
         * (opaque).
         *
         * @param transparency a float in the range {@code [0..1]} where {@code 0} means that the
         * ground overlay is opaque and {@code 1} means that the ground overlay is transparent.
         * @return This {@link Options} object for method chaining.
         */
        @NonNull
        Options transparency(@FloatRange(from = 0.0, to = 1.0) float transparency);

        /**
         * Specifies whether the ground overlay is clickable. The default clickability is {@code
         * false}.
         *
         * @param clickable The new clickability setting.
         * @return This {@link Options} object for method chaining.
         */
        @NonNull
        Options clickable(boolean clickable);

        /**
         * Gets the image descriptor set for this {@link Options} object.
         *
         * @return A {@link BitmapDescriptor} representing the image of the ground overlay.
         */
        BitmapDescriptor getImage();

        /**
         * Gets the location set for this {@link Options} object.
         *
         * @return The location to place the anchor of the ground overlay. This will be
         * {@code null} if the position was set using {@link #positionFromBounds(LatLngBounds)}.
         */
        LatLng getLocation();

        /**
         * Gets the width set for this {@link Options} object.
         *
         * @return The width of the ground overlay.
         */
        float getWidth();

        /**
         * Gets the height set for this {@link Options} object.
         *
         * @return The height of the ground overlay.
         */
        float getHeight();

        /**
         * Gets the bounds set for this {@link Options} object.
         *
         * @return The bounds of the ground overlay. This will be {@code null} if the position was
         * set using {@link #position(LatLng, float)} or {@link #position(LatLng, float, float)}
         */
        LatLngBounds getBounds();

        /**
         * Gets the bearing set for this {@link Options} object.
         *
         * @return The bearing of the ground overlay.
         */
        float getBearing();

        /**
         * Gets the zIndex set for this {@link Options} object.
         *
         * @return The zIndex of the ground overlay.
         */
        float getZIndex();

        /**
         * Gets the transparency set for this {@link Options} object.
         *
         * @return The transparency of the ground overlay.
         */
        float getTransparency();

        /**
         * Horizontal relative anchor; {@code 0.0} and {@code 1.0} denote left and right edges
         * respectively. Other anchor values are interpolated accordingly.
         *
         * @return The horizontal edge-relative anchor location.
         */
        float getAnchorU();

        /**
         * Vertical relative anchor; {@code 0.0} and {@code 1.0} denote top and bottom edges
         * respectively. Other anchor values are interpolated accordingly.
         *
         * @return The vertical edge-relative anchor location.
         */
        float getAnchorV();

        /**
         * Gets the visibility setting for this {@link Options} object.
         *
         * @return {@code true} if this ground overlay is visible; {@code false} if it is not.
         */
        boolean isVisible();

        /**
         * Gets the clickability setting for this {@link Options} object.
         *
         * @return {@code true} if this ground overlay is clickable; {@code false} if it is not.
         */
        boolean isClickable();
    }

}
