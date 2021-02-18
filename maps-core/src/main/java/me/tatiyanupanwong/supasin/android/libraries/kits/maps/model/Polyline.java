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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.UiThread;

import java.util.List;

/**
 * A polyline is a list of points, where line segments are drawn between consecutive points.
 *
 * Methods in this class must be called on the Android UI thread. If not, an {@link
 * IllegalStateException} will be thrown at runtime.
 *
 * @since 1.0.0
 */
@UiThread
public interface Polyline {

    /**
     * Removes this polyline from the map. After a polyline has been removed, the behavior of all
     * its methods is undefined.
     */
    void remove();

    /**
     * Gets this polyline's id. The id will be unique amongst all Polylines on a map.
     *
     * @return This polyline's id.
     */
    String getId();

    /**
     * Sets the points of this polyline. This method will take a copy of the points, so further
     * mutations to {@code points} will have no effect on this polyline.
     *
     * @param points An list of {@link LatLng}s that are the vertices of the polyline.
     */
    void setPoints(List<LatLng> points);

    /**
     * Returns a snapshot of the vertices of this polyline at this time. The list returned is a
     * copy of the list of vertices and so changes to the polyline's vertices will not be reflected
     * by this list, nor will changes to this list be reflected by the polyline. To change the
     * vertices of the polyline, call {@link #setPoints(List)}.
     *
     * @return A snapshot of the vertices of this polyline at this time.
     */
    List<LatLng> getPoints();

    /**
     * Sets the width of this polyline.
     *
     * @param width The width in screen pixel.
     */
    void setWidth(float width);

    /**
     * Gets the width of this polyline.
     *
     * @return The width in screen pixels.
     */
    float getWidth();

    /**
     * Sets the color of this polyline.
     *
     * @param color The color in ARGB format.
     */
    void setColor(int color);

    /**
     * Gets the color of this polyline.
     *
     * @return The color in ARGB format.
     */
    int getColor();

    /**
     * Sets the cap at the start vertex of this polyline. The default start cap is {@link ButtCap}.
     *
     * @param startCap The start cap. Must not be {@code null}.
     */
    void setStartCap(@NonNull Cap startCap);

    /**
     * Gets the cap at the start vertex of this polyline.
     *
     * @return The start cap.
     */
    @NonNull
    Cap getStartCap();

    /**
     * Sets the cap at the end vertex of this polyline. The default end cap is {@link ButtCap}.
     *
     * @param endCap The end cap. Must not be {@code null}.
     */
    void setEndCap(@NonNull Cap endCap);

    /**
     * Gets the cap at the end vertex of this polyline.
     *
     * @return The endCap cap.
     */
    @NonNull
    Cap getEndCap();

    /**
     * Sets the joint type for all vertices of the polyline except the start and end vertices.
     *
     * <p>See {@link JointType} for allowed values. The default value {@link JointType#DEFAULT
     * DEFAULT} will be used if joint type is undefined or is not one of the allowed values.
     *
     * @param jointType The joint type.
     */
    void setJointType(int jointType);

    /**
     * Gets the joint type used at all vertices of the polyline except the start and end vertices.
     * See {@link JointType} for possible values.
     *
     * @return The joint type.
     */
    int getJointType();

    /**
     * Sets the stroke pattern of the polyline. The default stroke pattern is solid, represented by
     * {@code null}.
     *
     * @param pattern The stroke pattern.
     */
    void setPattern(@Nullable List<PatternItem> pattern);

    /**
     * Gets the stroke pattern of this polyline.
     *
     * @return The stroke pattern.
     */
    @Nullable
    List<PatternItem> getPattern();

    /**
     * Sets the zIndex of this polyline. Polylines with higher zIndices are drawn above those with
     * lower indices.
     *
     * @param zIndex The zIndex of this polyline.
     */
    void setZIndex(float zIndex);

    /**
     * Gets the zIndex of this polyline.
     *
     * @return The zIndex of this polyline.
     */
    float getZIndex();

    /**
     * Sets the visibility of this polyline. When not visible, a polyline is not drawn, but it
     * keeps all its other properties.
     *
     * @param visible {@code true} to make the polyline visible; {@code false} to make it invisible.
     */
    void setVisible(boolean visible);

    /**
     * Gets the visibility of this polyline.
     *
     * @return This polyline's visibility.
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
     * Sets the clickability of the polyline. If the polyline is clickable, your app will receive
     * notifications to the {@link MapClient.OnPolylineClickListener} when the user clicks the
     * polyline. The event listener is registered through {@link
     * MapClient#setOnPolylineClickListener(MapClient.OnPolylineClickListener)
     * setOnPolylineClickListener(MapClient.OnPolylineClickListener)}.
     *
     * @param clickable New clickability setting for the polyline.
     */
    void setClickable(boolean clickable);

    /**
     * Gets the clickability of the polyline. If the polyline is clickable, your app will receive
     * notifications to the {@link MapClient.OnPolylineClickListener} when the user clicks the
     * polyline. The event listener is registered through {@link
     * MapClient#setOnPolylineClickListener(MapClient.OnPolylineClickListener)
     * setOnPolylineClickListener(MapClient.OnPolylineClickListener)}
     *
     * @return {@code true} if the polyline is clickable; {@code false} otherwise.
     */
    boolean isClickable();

    /**
     * Sets the tag for the polyline.
     *
     * <p>You can use this property to associate an arbitrary {@code Object} with this polyline.
     * For example, the {@code Object} can contain data about what the polyline represents. This is
     * easier than storing a separate {@code Map<Polyline, Object>}. As another example, you can
     * associate a {@code String} ID corresponding to the ID from a data set. It is your
     * responsibility to call {@code setTag(null)} to clear the tag when you no longer need it, to
     * prevent memory leaks in your app.
     *
     * @param tag if {@code null}, the tag is cleared.
     */
    void setTag(@Nullable Object tag);

    /**
     * Gets the tag for the polyline.
     *
     * @return The tag if a tag was set with {@code setTag}; {@code null} if no tag has been set.
     */
    @Nullable
    Object getTag();


    /**
     * Defines options for a {@link Polyline}.
     */
    interface Options {
        /**
         * Adds a vertex to the end of the polyline being built.
         *
         * @return This {@link Options} object for method chaining.
         */
        @NonNull
        Options add(LatLng point);

        /**
         * Adds vertices to the end of the polyline being built.
         *
         * @return This {@link Options} object for method chaining.
         */
        @NonNull
        Options add(LatLng... points);

        /**
         * Adds vertices to the end of the polyline being built.
         *
         * @return This {@link Options} object for method chaining.
         */
        @NonNull
        Options addAll(Iterable<LatLng> points);

        /**
         * Sets the width of the polyline in screen pixels. The default is 10.
         *
         * @return This {@link Options} object for method chaining.
         */
        @NonNull
        Options width(float width);

        /**
         * Sets the color of the polyline as a 32-bit ARGB color. The default color is black
         * ({@code 0xff000000}).
         *
         * @return This {@link Options} object for method chaining.
         */
        @NonNull
        Options color(int color);

        /**
         * Sets the cap at the start vertex of the polyline. The default start cap is {@link
         * ButtCap}.
         *
         * @return This {@link Options} object for method chaining.
         */
        @NonNull
        Options startCap(@NonNull Cap startCap);

        /**
         * Sets the cap at the end vertex of the polyline. The default end cap is {@link ButtCap}.
         *
         * @return This {@link Options} object for method chaining.
         */
        @NonNull
        Options endCap(@NonNull Cap endCap);

        /**
         * Sets the joint type for all vertices of the polyline except the start and end vertices.
         *
         * <p>See {@link JointType} for allowed values. The default value {@link JointType#DEFAULT
         * DEFAULT} will be used if joint type is undefined or is not one of the allowed values.
         *
         * @return This {@link Options} object for method chaining.
         */
        @NonNull
        Options jointType(int jointType);

        /**
         * Sets the stroke pattern for the polyline. The default stroke pattern is solid,
         * represented by {@code null}.
         *
         * @return This {@link Options} object for method chaining.
         */
        @NonNull
        Options pattern(@Nullable List<PatternItem> pattern);

        /**
         * Specifies the polyline's zIndex, i.e., the order in which it will be drawn.
         *
         * @return This {@link Options} object for method chaining.
         */
        @NonNull
        Options zIndex(float zIndex);

        /**
         * Specifies the visibility for the polyline. The default visibility is {@code true}.
         *
         * @return This {@link Options} object for method chaining.
         */
        @NonNull
        Options visible(boolean visible);

        /**
         * Specifies whether to draw each segment of this polyline as a geodesic. The default
         * setting is {@code false}.
         *
         * @return This {@link Options} object for method chaining.
         */
        @NonNull
        Options geodesic(boolean geodesic);

        /**
         * Specifies whether this polyline is clickable. The default setting is {@code false}.
         *
         * @return This {@link Options} object for method chaining.
         */
        @NonNull
        Options clickable(boolean clickable);

        /**
         * Gets the points set for this {@link Options} object.
         *
         * @return the list of {@link LatLng}s specifying the vertices of the polyline.
         */
        List<LatLng> getPoints();

        /**
         * Gets the width set for this {@link Options} object.
         *
         * @return The width of the polyline in screen pixels.
         */
        float getWidth();

        /**
         * Gets the color set for this {@link Options} object.
         *
         * @return The color of the polyline in ARGB format.
         */
        int getColor();

        /**
         * Gets the cap set for the start vertex in this {@link Options} object.
         *
         * @return The start cap of the polyline.
         */
        @NonNull
        Cap getStartCap();

        /**
         * Gets the cap set for the end vertex in this {@link Options} object.
         *
         * @return The end cap of the polyline.
         */
        @NonNull
        Cap getEndCap();

        /**
         * Gets the joint type set in this {@link Options} object for all vertices except the
         * start and end vertices. See {@link JointType} for possible values.
         *
         * @return The joint type of the polyline.
         */
        int getJointType();

        /**
         * Gets the stroke pattern set in this {@link Options} object for the polyline.
         *
         * @return The stroke pattern of the polyline.
         */
        @Nullable
        List<PatternItem> getPattern();

        /**
         * Gets the zIndex set for this {@link Options} object.
         *
         * @return The zIndex of the polyline.
         */
        float getZIndex();

        /**
         * Gets the visibility setting for this {@link Options} object.
         *
         * @return {@code true} if the polyline is visible; {@code false} if it is not.
         */
        boolean isVisible();

        /**
         * Gets the geodesic setting for this {@link Options} object.
         *
         * @return {@code true} if the polyline segments should be geodesics; {@code false} they
         * should not be.
         */
        boolean isGeodesic();

        /**
         * Gets the clickability setting for this {@link Options} object.
         *
         * @return {@code true} if the polyline is clickable; {@code false} if it is not.
         */
        boolean isClickable();
    }

}
