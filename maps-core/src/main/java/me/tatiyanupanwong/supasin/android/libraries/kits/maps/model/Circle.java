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

import android.graphics.Color;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

/**
 * A circle on the earth's surface (spherical cap).
 *
 * @since 1.0.0
 */
public interface Circle {

    /**
     * Removes this circle from the map. After a circle has been removed, the behavior of all its
     * methods is undefined.
     */
    void remove();

    /**
     * Gets this circle's id. The id will be unique amongst all Circles on a map.
     *
     * @return This circle's id.
     */
    String getId();

    /**
     * Sets the center using a {@link LatLng}.
     *
     * <p>The center must not be null, as there is no default value.
     *
     * @param center The geographic center of the circle, specified as a {@link LatLng}.
     */
    void setCenter(LatLng center);

    /**
     * Returns the center as a {@link LatLng}.
     *
     * @return The geographic center as a {@link LatLng}.
     */
    LatLng getCenter();

    /**
     * Sets the radius in meters.
     *
     * <p>The radius must be zero or greater.
     *
     * @param radius The radius, in meters.
     */
    void setRadius(double radius);

    /**
     * Returns the circle's radius, in meters.
     *
     * @return The radius, in meters.
     */
    double getRadius();

    /**
     * Sets the stroke width.
     *
     * <p>The stroke width is the width (in screen pixels) of the circle's outline. It must be
     * zero or greater. If it is zero then no outline is drawn. The default value is 10.
     *
     * @param width The stroke width, in screen pixels.
     * @throws IllegalArgumentException if width is negative.
     */
    void setStrokeWidth(float width);

    /**
     * Returns the stroke width.
     *
     * @return The width in screen pixels.
     */
    float getStrokeWidth();

    /**
     * Sets the stroke color.
     *
     * <p>The stroke color is the color of this circle's outline, in the integer format specified
     * by {@link Color}. If TRANSPARENT is used then no outline is drawn.
     *
     * @param color The stroke color in the {@link Color} format.
     */
    void setStrokeColor(int color);

    /**
     * Returns the stroke color.
     *
     * @return The color of the circle in ARGB format.
     */
    int getStrokeColor();

    /**
     * Sets the stroke pattern of the circle's outline. The default stroke pattern is solid,
     * represented by {@code null}.
     *
     * @param pattern The stroke pattern.
     */
    void setStrokePattern(@Nullable List<PatternItem> pattern);

    /**
     * Returns the stroke pattern of this circle's outline.
     *
     * @return The stroke pattern.
     */
    @Nullable
    List<PatternItem> getStrokePattern();

    /**
     * Sets the fill color.
     *
     * <p>The fill color is the color inside the circle, in the integer format specified by
     * {@link Color}. If TRANSPARENT is used then no fill is drawn.
     *
     * @param color The color in the {@link Color} format.
     */
    void setFillColor(int color);

    /**
     * Returns the fill color of this Circle.
     *
     * @return The fill color of the circle in ARGB format.
     */
    int getFillColor();

    /**
     * Sets the zIndex.
     *
     * <p>Overlays (such as circles) with higher zIndices are drawn above those with lower indices.
     *
     * @param zIndex The zIndex value.
     */
    void setZIndex(float zIndex);

    /**
     * Returns the zIndex.
     *
     * @return The zIndex of this circle.
     */
    float getZIndex();

    /**
     * Sets the visibility of the circle.
     *
     * If this circle is not visible then it will not be drawn. All other state is preserved.
     * Circles are visible by default.
     *
     * @param visible {@code true} to make this circle visible; {@code false} to make it invisible.
     */
    void setVisible(boolean visible);

    /**
     * Checks whether the circle is visible.
     *
     * @return {@code true} if the circle is visible; {@code false} if it is invisible.
     */
    boolean isVisible();

    /**
     * Sets the clickability of the circle. If the circle is clickable, your app will receive
     * notifications to the {@link MapClient.OnCircleClickListener} when the user clicks the circle.
     * The event listener is registered through {@link
     * MapClient#setOnCircleClickListener(MapClient.OnCircleClickListener) setOnCircleClickListener(
     * MapClient.OnCircleClickListener)}.
     *
     * @param clickable New clickability setting for the circle.
     */
    void setClickable(boolean clickable);

    /**
     * Gets the clickability of the circle. If the circle is clickable, your app will receive
     * notifications to the {@link MapClient.OnCircleClickListener} when the user clicks the circle.
     * The event listener is registered through {@link
     * MapClient#setOnCircleClickListener(MapClient.OnCircleClickListener) setOnCircleClickListener(
     * MapClient.OnCircleClickListener)}.
     *
     * @return {@code true} if the circle is clickable; {@code false} if it is not clickable.
     */
    boolean isClickable();

    /**
     * Sets the tag for the circle.
     *
     * <p>You can use this property to associate an arbitrary {@code Object} with this circle.
     * For example, the {@code Object} can contain data about what the circle represents. This
     * is easier than storing a separate {@code Map<Circle, Object>}. As another example, you
     * can associate a {@code String} ID corresponding to the ID from a data set. It is your
     * responsibility to call {@code setTag(null)} to clear the tag when you no longer need it,
     * to prevent memory leaks in your app.
     *
     * @param tag If {@code null}, the tag is cleared.
     */
    void setTag(@Nullable Object tag);

    /**
     * Gets the tag for the circle.
     *
     * @return The tag if a tag was set with {@code setTag}; {@code null} if no tag has been set.
     */
    @Nullable
    Object getTag();


    /**
     * Defines options for a {@link Circle}.
     */
    interface Options {
        /**
         * Sets the center using a LatLng.
         *
         * <p>The center must not be null.
         *
         * <p>This method is mandatory because there is no default center.
         *
         * @param center The geographic center as a LatLng.
         * @return This {@link Options} object for method chaining.
         */
        @NonNull
        Options center(LatLng center);

        /**
         * Sets the radius in meters.
         *
         * <p>The radius must be zero or greater. The default radius is zero.
         *
         * @param radius Radius in meters.
         * @return This {@link Options} object for method chaining.
         */
        @NonNull
        Options radius(double radius);

        /**
         * Sets the stroke width.
         *
         * <p>The stroke width is the width (in screen pixels) of the circle's outline.
         * It must be zero or greater. If it is zero then no outline is drawn.
         *
         * <p>The default width is 10 pixels.
         *
         * @param width Width in pixels.
         * @return This {@link Options} object for method chaining.
         */
        @NonNull
        Options strokeWidth(float width);

        /**
         * Sets the stroke color.
         *
         * <p>The stroke color is the color of this circle's outline, in the integer format
         * specified by Color. If TRANSPARENT is used then no outline is drawn.
         *
         * <p>By default the stroke color is black (0xff000000).
         *
         * @param color Color in the {@link Color} format.
         * @return This {@link Options} object for method chaining.
         */
        @NonNull
        Options strokeColor(int color);

        /**
         * Sets a stroke pattern for the circle's outline. The default stroke pattern is solid,
         * represented by {@code null}.
         *
         * @param pattern A stroke pattern for the circle's outline.
         * @return This {@link Options} object for method chaining.
         */
        @NonNull
        Options strokePattern(@Nullable List<PatternItem> pattern);

        /**
         * Sets the fill color.
         *
         * <p>The fill color is the color inside the circle, in the integer format specified by
         * {@link Color}. If TRANSPARENT is used then no fill is drawn.
         *
         * <p>By default the fill color is transparent (0x00000000).
         *
         * @param color Color in the {@link Color} format.
         * @return This {@link Options} object for method chaining.
         */
        @NonNull
        Options fillColor(int color);

        /**
         * Sets the zIndex.
         *
         * <p>Overlays (such as circles) with higher zIndices are drawn above those with lower
         * indices.
         *
         * <p>By default the zIndex is 0.0.
         *
         * @param zIndex The zIndex value.
         * @return This {@link Options} object for method chaining.
         */
        @NonNull
        Options zIndex(float zIndex);

        /**
         * Sets the visibility.
         *
         * <p>If this circle is not visible then it is not drawn, but all other state is preserved.
         *
         * @param visible {@code false} to make this circle invisible.
         * @return This {@link Options} object for method chaining.
         */
        @NonNull
        Options visible(boolean visible);

        /**
         * Specifies whether this circle is clickable. The default setting is {@code false}.
         *
         * @param clickable {@code false} to make this circle not clickable.
         * @return This {@link Options} object for method chaining.
         */
        @NonNull
        Options clickable(boolean clickable);

        /**
         * Returns the center as a {@link LatLng}.
         *
         * @return The geographic center as a {@link LatLng}.
         */
        LatLng getCenter();

        /**
         * Returns the circle's radius, in meters.
         *
         * @return The radius in meters.
         */
        double getRadius();

        /**
         * Returns the stroke width.
         *
         * @return The width in screen pixels.
         */
        float getStrokeWidth();

        /**
         * Returns the stroke color.
         *
         * @return The color in the {@link Color} format.
         */
        int getStrokeColor();

        /**
         * Returns the stroke pattern set in this {@link Options} object for the circle's outline.
         *
         * @return The stroke pattern of the circle's outline.
         */
        @Nullable
        List<PatternItem> getStrokePattern();

        /**
         * Returns the fill color.
         *
         * @return The color in the {@link Color} format.
         */
        int getFillColor();

        /**
         * Returns the zIndex.
         *
         * @return The zIndex value.
         */
        float getZIndex();

        /**
         * Checks whether the circle is visible.
         *
         * @return {@code true} if the circle is visible; {@code false} if it is invisible.
         */
        boolean isVisible();

        /**
         * Gets the clickability setting for the circle.
         *
         * @return {@code true} if the circle is clickable; {@code false} if it is not.
         */
        boolean isClickable();
    }

}
