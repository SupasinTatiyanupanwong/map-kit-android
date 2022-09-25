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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.UiThread;

import java.util.List;

/**
 * A polygon on the earth's surface. A polygon can be convex or concave, it may span the 180
 * meridian and it can have holes that are not filled in.
 *
 * <p>Methods in this class must be called on the Android UI thread. If not, an {@link
 * IllegalStateException} will be thrown at runtime.
 *
 * @since 1.0.0
 */
@UiThread
public interface Polygon {

    /**
     * Removes the polygon from the map. After a polygon has been removed, the behavior of all
     * its methods is undefined.
     */
    void remove();

    /**
     * Gets this polygon's id. The id will be unique amongst all Polygons on a map.
     *
     * @return This polygon's id.
     */
    String getId();

    /**
     * Sets the points of this polygon. This method will take a copy of the points, so further
     * mutations to points will have no effect on this polygon.
     *
     * @param points A list of {@link LatLng}s that are the vertices of the polygon.
     */
    void setPoints(List<LatLng> points);

    /**
     * Returns a snapshot of the vertices of this polygon at this time. The list returned is a
     * copy of the list of vertices and so changes to the polygon's vertices will not be reflected
     * by this list, nor will changes to this list be reflected by the polygon. To change the
     * vertices of the polygon, call {@link #setPoints(List)}.
     *
     * @return A snapshot of the vertices of this polygon at this time.
     */
    List<LatLng> getPoints();

    /**
     * Sets the holes of this polygon. This method will take a copy of the holes, so further
     * mutations to holes will have no effect on this polygon.
     *
     * @param holes An list of holes, where a hole is an list of {@link LatLng}s.
     */
    void setHoles(List<? extends List<LatLng>> holes);

    /**
     * Returns a snapshot of the holes of this polygon at this time. The list returned is a copy
     * of the list of holes and so changes to the polygon's holes will not be reflected by this
     * list, nor will changes to this list be reflected by the polygon.
     *
     * @return A snapshot of the holes of this polygon at this time.
     */
    List<List<LatLng>> getHoles();

    /**
     * Sets the stroke width of this polygon.
     *
     * @param width The width in display pixels.
     */
    void setStrokeWidth(float width);

    /**
     * Gets the stroke width of this polygon.
     *
     * @return The width in screen pixels.
     */
    float getStrokeWidth();

    /**
     * Sets the stroke color of this polygon.
     *
     * @param color The color in ARGB format.
     */
    void setStrokeColor(int color);

    /**
     * Gets the stroke color of this polygon.
     *
     * @return The color in ARGB format.
     */
    int getStrokeColor();

    /**
     * Sets the joint type for all vertices of the polygon's outline.
     *
     * <p>See {@link JointType} for allowed values. The default value {@link JointType#DEFAULT
     * DEFAULT} will be used if joint type is undefined or is not one of the allowed values.
     *
     * @param jointType The stroke joint type.
     */
    void setStrokeJointType(int jointType);

    /**
     * Gets the stroke joint type used at all vertices of the polygon's outline. See {@link
     * JointType} for possible values.
     *
     * @return The stroke joint type.
     */
    int getStrokeJointType();

    /**
     * Sets the stroke pattern of the polygon's outline. The default stroke pattern is solid,
     * represented by {@code null}.
     *
     * @param pattern The stroke pattern
     */
    void setStrokePattern(@Nullable List<PatternItem> pattern);

    /**
     * Gets the stroke pattern of this polygon's outline.
     *
     * @return The stroke pattern.
     */
    @Nullable List<PatternItem> getStrokePattern();

    /**
     * Sets the fill color of this polygon.
     *
     * @param color The color in ARGB format.
     */
    void setFillColor(int color);

    /**
     * Gets the fill color of this polygon.
     *
     * @return The color in ARGB format.
     */
    int getFillColor();

    /**
     * Sets the zIndex of this polygon. Polygons with higher zIndices are drawn above those with
     * lower indices.
     *
     * @param zIndex The zIndex of this polygon.
     */
    void setZIndex(float zIndex);

    /**
     * Gets the zIndex of this polygon.
     *
     * @return The zIndex of the polygon.
     */
    float getZIndex();

    /**
     * Sets the visibility of this polygon. When not visible, a polygon is not drawn, but it keeps
     * all its other properties.
     *
     * @param visible If {@code true}, then the polygon is visible; otherwise, it is not.
     */
    void setVisible(boolean visible);

    /**
     * Gets the visibility of this polygon.
     *
     * @return This polygon visibility.
     */
    boolean isVisible();

    /**
     * Sets whether to draw each segment of the line as a geodesic or not.
     *
     * @param geodesic {@code true} to draw each segment as a geodesic; {@code false} to draw each
     * segment as a straight line on the Mercator projection.
     */
    void setGeodesic(boolean geodesic);

    /**
     * Gets whether each segment of the line is drawn as a geodesic or not.
     *
     * @return {@code true} if each segment is drawn as a geodesic; {@code false} if each segment
     * is drawn as a straight line on the Mercator projection.
     */
    boolean isGeodesic();

    /**
     * Sets the clickability of the polygon. If the polygon is clickable, your app will receive
     * notifications to the {@link MapClient.OnPolygonClickListener} when the user clicks the
     * polygon. The event listener is registered through {@link
     * MapClient#setOnPolygonClickListener(MapClient.OnPolygonClickListener)
     * setOnPolygonClickListener(MapClient.OnPolygonClickListener)}.
     *
     * @param clickable New clickability setting for the polygon.
     */
    void setClickable(boolean clickable);

    /**
     * Gets the clickability of the polygon. If the polygon is clickable, your app will receive
     * notifications to the {@link MapClient.OnPolygonClickListener} when the user clicks the
     * polygon. The event listener is registered through {@link
     * MapClient#setOnPolygonClickListener(MapClient.OnPolygonClickListener)
     * setOnPolygonClickListener(MapClient.OnPolygonClickListener)}.
     *
     * @return {@code true} if the polygon is clickable; otherwise, returns {@code false}.
     */
    boolean isClickable();

    /**
     * Sets the tag for the polygon.
     *
     * <p>You can use this property to associate an arbitrary {@code Object} with this polygon.
     * For example, the {@code Object} can contain data about what the polygon represents. This is
     * easier than storing a separate {@code Map<Polygon, Object>}. As another example, you can
     * associate a {@code String} ID corresponding to the ID from a data set. It is your
     * responsibility to call {@code setTag(null)} to clear the tag when you no longer need it, to
     * prevent memory leaks in your app.
     *
     * @param tag If {@code null}, the tag is cleared.
     */
    void setTag(@Nullable Object tag);

    /**
     * Gets the tag for the polygon.
     *
     * @return The tag if a tag was set with {@code setTag}; {@code null} if no tag has been set.
     */
    @Nullable Object getTag();


    /**
     * Defines options for a {@link Polygon}.
     */
    interface Options {
        /**
         * Adds a vertex to the outline of the polygon being built.
         *
         * @return This {@link Options} object for method chaining.
         */
        @NonNull Options add(LatLng point);

        /**
         * Adds vertices to the outline of the polygon being built.
         *
         * @return This {@link Options} object for method chaining.
         */
        @NonNull Options add(LatLng... points);

        /**
         * Adds vertices to the outline of the polygon being built.
         *
         * @return This {@link Options} object for method chaining.
         */
        @NonNull Options addAll(@NonNull Iterable<LatLng> points);

        /**
         * Adds a hole to the polygon being built.
         *
         * @return This {@link Options} object for method chaining.
         */
        @NonNull Options addHole(@NonNull Iterable<LatLng> points);

        /**
         * Specifies the polygon's stroke width, in display pixels. The default width is 10.
         *
         * @return This {@link Options} object for method chaining.
         */
        @NonNull Options strokeWidth(float width);

        /**
         * Specifies the polygon's stroke color, as a 32-bit ARGB color. The default color is
         * black ({@code 0xff000000}).
         *
         * @return This {@link Options} object for method chaining.
         */
        @NonNull Options strokeColor(int color);

        /**
         * Specifies the joint type for all vertices of the polygon's outline.
         *
         * @return This {@link Options} object for method chaining.
         */
        @NonNull Options strokeJointType(int jointType);

        /**
         * Specifies a stroke pattern for the polygon's outline. The default stroke pattern is
         * solid, represented by {@code null}.
         *
         * @return This {@link Options} object for method chaining.
         */
        @NonNull Options strokePattern(@Nullable List<PatternItem> pattern);

        /**
         * Sets the color of the polygon as a 32-bit ARGB color. The default color is black
         * ({@code 0xff000000}).
         *
         * @return This {@link Options} object for method chaining.
         */
        @NonNull Options fillColor(int color);

        /**
         * Specifies the polygon's zIndex, i.e., the order in which it will be drawn.
         *
         * @return This {@link Options} object for method chaining.
         */
        @NonNull Options zIndex(float zIndex);

        /**
         * Specifies the visibility for the polygon. The default visibility is {@code true}.
         *
         * @return This {@link Options} object for method chaining.
         */
        @NonNull Options visible(boolean visible);

        /**
         * Specifies whether to draw each segment of this polygon as a geodesic. The default
         * setting is {@code false}.
         *
         * @return This {@link Options} object for method chaining.
         */
        @NonNull Options geodesic(boolean geodesic);

        /**
         * Specifies whether this polygon is clickable. The default setting is {@code false}.
         *
         * @return This {@link Options} object for method chaining.
         */
        @NonNull Options clickable(boolean clickable);

        /**
         * Gets the outline set for this {@link Options} object.
         *
         * @return The list of {@link LatLng}s specifying the vertices of the outline of the
         * polygon.
         */
        @NonNull List<LatLng> getPoints();

        /**
         * Gets the holes set for this {@link Options} object.
         *
         * @return The list of {@code List<LatLng>}s specifying the holes of the polygon.
         */
        @NonNull List<List<LatLng>> getHoles();

        /**
         * Gets the stroke width set for this {@link Options} object.
         *
         * @return The stroke width of the polygon in screen pixels.
         */
        float getStrokeWidth();

        /**
         * Gets the stroke color set for this {@link Options} object.
         *
         * @return The stroke color of the polygon in screen pixels.
         */
        int getStrokeColor();

        /**
         * Gets the stroke joint type set in this {@link Options} object for all vertices of the
         * polygon's outline. See {@link JointType} for possible values.
         *
         * @return The stroke joint type of the polygon's outline.
         */
        int getStrokeJointType();

        /**
         * Gets the stroke pattern set in this {@link Options} object for the polygon's outline.
         *
         * @return The stroke pattern of the polygon's outline.
         */
        @Nullable List<PatternItem> getStrokePattern();

        /**
         * Gets the fill color set for this {@link Options} object.
         *
         * @return The fill color of the polygon in screen pixels.
         */
        int getFillColor();

        /**
         * Gets the zIndex set for this {@link Options} object.
         *
         * @return The zIndex of the polygon's outline.
         */
        float getZIndex();

        /**
         * Gets the visibility setting for this {@link Options} object.
         *
         * @return {@code true} if the polygon is visible; {@code false} if it is not.
         */
        boolean isVisible();

        /**
         * Gets the geodesic setting for this {@link Options} object.
         *
         * @return {@code true} if the polygon segments should be geodesics; {@code false} they
         * should not be.
         */
        boolean isGeodesic();

        /**
         * Gets the clickability setting for this {@link Options} object.
         *
         * @return {@code true} if the polygon is clickable; {@code false} if it is not.
         */
        boolean isClickable();
    }

}
