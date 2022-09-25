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

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;

import android.graphics.Bitmap;
import android.location.Location;
import android.view.View;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresPermission;
import androidx.annotation.UiThread;

import dev.supasintatiyanupanwong.libraries.android.kits.maps.MapFragment;

/**
 * This is the main class of the Map Kit and is the entry point for all methods related to the map.
 * You cannot instantiate a {@link MapClient} object directly, rather, you must obtain one from
 * {@link MapFragment#getMapAsync} that you have added to your application.
 *
 * <p>Note: Similar to a {@link View} object, a {@link MapClient} can only be read and modified from
 * the Android UI thread. Calling {@link MapClient} methods from another thread will result in an
 * exception.
 *
 * <p>You can adjust the viewpoint of a map by changing the position of the camera (as opposed to
 * moving the map). You can use the map's camera to set parameters such as location, zoom level,
 * tilt angle, and bearing.
 *
 * @since 1.2.0
 */
@UiThread
public interface MapClient {

    /**
     * No base map tiles.
     */
    int MAP_TYPE_NONE = 0;

    /**
     * Basic maps.
     */
    int MAP_TYPE_NORMAL = 1;

    /**
     * Satellite maps with no labels.
     */
    int MAP_TYPE_SATELLITE = 2;

    /**
     * Terrain maps.
     */
    int MAP_TYPE_TERRAIN = 3;

    /**
     * Satellite maps with a transparent layer of major streets.
     */
    int MAP_TYPE_HYBRID = 4;


    /**
     * Gets the current position of the camera.
     *
     * <p>The {@link CameraPosition} returned is a snapshot of the current position, and will not
     * automatically update when the camera moves.
     *
     * @return The current position of the Camera.
     */
    @NonNull CameraPosition getCameraPosition();

    /**
     * Returns the maximum zoom level for the current camera position. This takes into account what
     * map type is currently being used, e.g., satellite or terrain may have a lower max zoom level
     * than the base map tiles.
     *
     * @return The maximum zoom level available at the current camera position.
     */
    float getMaxZoomLevel();

    /**
     * Returns the minimum zoom level. This is the same for every location (unlike the maximum zoom
     * level) but may vary between devices and map sizes.
     *
     * @return The minimum zoom level available.
     */
    float getMinZoomLevel();

    /**
     * Repositions the camera according to the instructions defined in the update. The move is
     * instantaneous, and a subsequent {@link #getCameraPosition()} will reflect the new position.
     *
     * <p>See {@link CameraUpdate.Factory} for a set of updates.
     *
     * @param update The change that should be applied to the camera.
     */
    void moveCamera(@NonNull CameraUpdate update);

    /**
     * Animates the movement of the camera from the current position to the position defined in
     * the update. During the animation, a call to {@link #getCameraPosition()} returns an
     * intermediate location of the camera.
     *
     * <p>See {@link CameraUpdate.Factory} for a set of updates.
     *
     * @param update The change that should be applied to the camera.
     */
    void animateCamera(@NonNull CameraUpdate update);

    /**
     * Animates the movement of the camera from the current position to the position defined in
     * the update and calls an optional callback on completion.
     *
     * <p>See {@link CameraUpdate.Factory} for a set of updates.
     *
     * <p>During the animation, a call to {@link #getCameraPosition()} returns an intermediate
     * location of the camera.
     *
     * @param update The change that should be applied to the camera.
     * @param callback The callback to invoke from the Android UI thread when the animation stops.
     * If the animation completes normally, {@link CancelableCallback#onFinish() onFinish()} is
     * called; otherwise, {@link CancelableCallback#onCancel() onCancel()} is called. Do not update
     * or animate the camera from within {@link CancelableCallback#onCancel() onCancel()}.
     */
    void animateCamera(@NonNull CameraUpdate update, @Nullable CancelableCallback callback);

    /**
     * Moves the map according to the update with an animation over a specified duration, and calls
     * an optional callback on completion.
     *
     * <p>See {@link CameraUpdate.Factory} for a set of updates.
     *
     * <p>If {@link #getCameraPosition()} is called during the animation, it will return the
     * current location of the camera in flight.
     *
     * @param update The change that should be applied to the camera.
     * @param durationMs The duration of the animation in milliseconds. This must be strictly
     * positive, otherwise an {@link IllegalArgumentException} will be thrown.
     * @param callback An optional callback to be notified from the Android UI thread when the
     * animation stops. If the animation stops due to its natural completion, the callback will
     * be notified with {@link CancelableCallback#onFinish() onFinish()}. If the animation stops
     * due to interruption by a later camera movement or a user gesture, {@link
     * CancelableCallback#onCancel() onCancel()} will be called. The callback should not attempt
     * to move or animate the camera in its cancellation method. If a callback isn't required,
     * leave it as {@code null}.
     */
    void animateCamera(
            @NonNull CameraUpdate update,
            @IntRange(from = 0) int durationMs,
            @Nullable CancelableCallback callback);

    /**
     * Stops the camera animation if there is one in progress. When the method is called, the
     * camera stops moving immediately and remains in that position.
     */
    void stopAnimation();

    /**
     * Adds a polyline to this map.
     *
     * @param options A polyline options object that defines how to render the Polyline.
     * @return The {@link Polyline} object that is added to the map. Might be {@code null} if
     * there's an error adding the polyline.
     */
    @Nullable Polyline addPolyline(@NonNull Polyline.Options options);

    /**
     * Adds a polygon to this map.
     *
     * @param options A polygon options object that defines how to render the Polygon.
     * @return The {@link Polygon} object that is added to the map. Might be {@code null} if
     * there's an error adding the polygon.
     */
    @Nullable Polygon addPolygon(@NonNull Polygon.Options options);

    /**
     * Add a circle to this map.
     *
     * @param options A circle options object that defines how to render the Circle.
     * @return The {@link Circle} object that is added to the map. Might be {@code null} if
     * there's an error adding the circle.
     */
    @Nullable Circle addCircle(@NonNull Circle.Options options);

    /**
     * Adds a marker to this map.
     *
     * <p>The marker's icon is rendered on the map at the location Marker.position. Clicking the
     * marker centers the camera on the marker. If Marker.title is defined, the map shows an info
     * box with the marker's title and snippet. If the marker is draggable, long-clicking and then
     * dragging the marker moves it.
     *
     * @param options A marker options object that defines how to render the marker.
     * @return The {@link Marker} that was added to the map. Might be {@code null} if there's an
     * error adding the marker.
     */
    @Nullable Marker addMarker(@NonNull Marker.Options options);

    /**
     * Adds an image to this map.
     *
     * @param options A ground-overlay options object that defines how to render the overlay.
     * Options must have an image (AnchoredBitmap) and position specified.
     * @return The {@link GroundOverlay} that was added to the map. Might be {@code null} if
     * there's an error adding the overlay.
     */
    @Nullable GroundOverlay addGroundOverlay(@NonNull GroundOverlay.Options options);

    /**
     * Adds a tile overlay to this map. See {@link TileOverlay} for more information.
     *
     * Note that unlike other overlays, if the map is recreated, tile overlays are not
     * automatically restored and must be re-added manually.
     *
     * @param options A tile-overlay options object that defines how to render the overlay.
     * Options must have a {@link TileProvider} specified, otherwise an {@link
     * IllegalArgumentException} will be thrown.
     * @return The {@link TileOverlay} that was added to the map. Might be {@code null} if there's
     * an error adding the overlay.
     */
    @Nullable TileOverlay addTileOverlay(@NonNull TileOverlay.Options options);

    /**
     * Removes all markers, polylines, polygons, overlays, etc from the map.
     */
    void clear();

    /**
     * Gets the currently focused building.
     *
     * @return The current focused building or {@code null} if no building is focused.
     */
    @Nullable IndoorBuilding getFocusedBuilding();

    /**
     * Sets or clears the listener for indoor events. Only one listener can ever be set. Setting a
     * new listener will remove the previous listener.
     *
     * @param listener The listener for indoor events if non-null; otherwise, clears the listener.
     */
    void setOnIndoorStateChangeListener(@Nullable OnIndoorStateChangeListener listener);

    /**
     * Gets the type of map that's currently displayed. See {@link #MAP_TYPE_NORMAL}, {@link
     * #MAP_TYPE_SATELLITE}, {@link #MAP_TYPE_TERRAIN} for possible values.
     *
     * @return The map type.
     */
    int getMapType();

    /**
     * Sets the type of map tiles that should be displayed. The allowable values are:
     *
     * <ul>
     * <li>{@link #MAP_TYPE_NORMAL}: Basic map.
     * <li>{@link #MAP_TYPE_SATELLITE}: Satellite imagery.
     * <li>{@link #MAP_TYPE_HYBRID}: Satellite imagery with roads and labels.
     * <li>{@link #MAP_TYPE_TERRAIN}: Topographic data.
     * <li>{@link #MAP_TYPE_NONE}: No base map tiles.
     *</ul>
     *
     * @param type type	The type of map to display.
     */
    void setMapType(int type);

    /**
     * Gets whether the map is drawing traffic data. This is subject to the availability of
     * traffic data.
     *
     * @return {@code true} if traffic data is enabled; {@code false} otherwise.
     */
    boolean isTrafficEnabled();

    /**
     * Sets whether the traffic data should be enabled.
     *
     * @param enabled {@code true} to try to enable traffic data; {@code false} to disable traffic
     * data.
     */
    void setTrafficEnabled(boolean enabled);

    /**
     * Gets whether indoor maps are currently enabled.
     *
     * @return {@code true} if indoor maps are enabled; {@code false} if indoor maps are disabled.
     */
    boolean isIndoorEnabled();

    /**
     * Sets whether indoor maps should be enabled. Currently, indoor maps can only be shown on one
     * map at a time and by default, this is the first map added to your application. To enable
     * indoor maps on another map, you must first disable indoor maps on the original map. If you
     * try to enable indoor maps when it is enabled on another map, nothing will happen and this
     * will return {@code false}. When Indoor is not enabled for a map, all methods related to
     * indoor will return {@code null}, or {@code false}.
     *
     * @param enabled {@code true} to try to enable indoor maps; {@code false} to disable indoor
     * maps.
     * @return Whether it was possible to enable indoor maps.
     */
    boolean setIndoorEnabled(boolean enabled);

    /**
     * Gets whether 3D buildings layer is enabled.
     *
     * @return {@code true} if buildings are enabled; {@code false} otherwise.
     */
    boolean isBuildingsEnabled();

    /**
     * Sets whether 3D buildings layer is enabled.
     *
     * @param enabled {@code true} to enable the 3D buildings layer; {@code false} to disable 3D
     * buildings.
     */
    void setBuildingsEnabled(boolean enabled);

    /**
     * Gets the status of the my-location layer.
     *
     * @return {@code true} if the my-location layer is enabled; {@code false} otherwise.
     */
    boolean isMyLocationEnabled();

    @RequiresPermission(anyOf = {ACCESS_COARSE_LOCATION, ACCESS_FINE_LOCATION})
    void setMyLocationEnabled(boolean enabled);

    /**
     * Replaces the location source of the my-location layer.
     *
     * @param source A location source to use in the my-location layer. Set to {@code null} to use
     * the default location source.
     */
    void setLocationSource(@Nullable LocationSource source);

    /**
     * Gets the user interface settings for the map.
     *
     * @return The {@link UiSettings} for this map.
     */
    @NonNull UiSettings getUiSettings();

    /**
     * Returns a {@link Projection} object that you can use to convert between screen coordinates
     * and latitude/longitude coordinates.
     *
     * <p></p>The {@code Projection} returned is a snapshot of the current projection, and will not
     * automatically update when the camera moves. As this operation is expensive, you should get
     * the projection only once per screen. Map Kit uses the Mercator projection to create its
     * maps from geographic data and convert points on the map into geographic coordinates.
     *
     * @return The {@link Projection} of the map in its current state.
     */
    @NonNull Projection getProjection();

    /**
     * Sets a callback that's invoked when the camera starts moving or the reason for camera motion
     * has changed.
     *
     * @param listener The callback that's invoked. To unset the callback, use {@code null}.
     * Do not update or animate the camera from within {@code onCameraMoveStarted()}.
     */
    void setOnCameraMoveStartedListener(@Nullable OnCameraMoveStartedListener listener);

    /**
     * Sets a callback that is invoked repeatedly while the camera is in motion. The callback may
     * be invoked as often as once every frame and should not perform expensive operations.
     *
     * @param listener The callback that's invoked. To unset the callback, use {@code null}.
     */
    void setOnCameraMoveListener(@Nullable OnCameraMoveListener listener);

    /**
     * Sets a callback that is invoked when camera motion is stopped or interrupted by a new type
     * of animation.
     *
     * @param listener The callback that's invoked. To unset the callback, use {@code null}.
     * Do not update or animate the camera from within {@code onCameraMoveCanceled()}.
     */
    void setOnCameraMoveCanceledListener(@Nullable OnCameraMoveCanceledListener listener);

    /**
     * Sets a callback that is invoked when camera movement has ended.
     *
     * @param listener The callback that's invoked. To unset the callback, use {@code null}.
     */
    void setOnCameraIdleListener(@Nullable OnCameraIdleListener listener);

    /**
     * Sets a callback that's invoked when the map is tapped.
     *
     * @param listener The callback that's invoked. To unset the callback, use {@code null}.
     */
    void setOnMapClickListener(@Nullable OnMapClickListener listener);

    /**
     * Sets a callback that's invoked when the map is long pressed.
     *
     * @param listener The callback that's invoked. To unset the callback, use {@code null}.
     */
    void setOnMapLongClickListener(@Nullable OnMapLongClickListener listener);

    /**
     * Sets a callback that's invoked when a marker is clicked.
     *
     * @param listener The callback that's invoked. To unset the callback, use {@code null}.
     */
    void setOnMarkerClickListener(@Nullable OnMarkerClickListener listener);

    /**
     * Sets a callback that's invoked when a marker is dragged.
     *
     * @param listener The callback that's invoked. To unset the callback, use {@code null}.
     */
    void setOnMarkerDragListener(@Nullable OnMarkerDragListener listener);

    /**
     * Sets a callback that's invoked when a marker info window is clicked.
     *
     * @param listener The callback that's invoked. To unset the callback, use {@code null}.
     */
    void setOnInfoWindowClickListener(@Nullable OnInfoWindowClickListener listener);

    /**
     * Sets a callback that's invoked when a marker info window is long pressed.
     *
     * @param listener The callback that's invoked. To unset the callback, use {@code null}.
     */
    void setOnInfoWindowLongClickListener(@Nullable OnInfoWindowLongClickListener listener);

    /**
     * Sets a callback that's invoked when a marker's info window is closed.
     *
     * @param listener The callback that's invoked. To unset the callback, use {@code null}.
     */
    void setOnInfoWindowCloseListener(@Nullable OnInfoWindowCloseListener listener);

    /**
     * Sets a custom renderer for the contents of info windows.
     *
     * <p>Like the map's event listeners, this state is not serialized with the map. If the map
     * gets re-created (e.g., due to a configuration change), you must ensure that you call this
     * method again in order to preserve the customization.
     *
     * @param adapter The adapter to use for info window contents, or {@code null} to use the
     * default content rendering in info windows.
     */
    void setInfoWindowAdapter(@Nullable InfoWindowAdapter adapter);

    /**
     * Sets a callback that's invoked when the my location button is clicked.
     *
     * <p>If the {@code listener} returns {@code true}, the event is consumed and the default
     * behavior will not occur. If it returns false, the default behavior will occur (i.e. The
     * camera moves such that it is centered on the user's location).
     *
     * @param listener The callback that's invoked. To unset the callback, use {@code null}.
     */
    void setOnMyLocationButtonClickListener(@Nullable OnMyLocationButtonClickListener listener);

    /**
     * Sets a callback that's invoked when the My Location dot (which signifies the user's
     * location) is clicked.
     *
     * @param listener The callback that's invoked. To unset the callback, use {@code null}.
     */
    void setOnMyLocationClickListener(@Nullable OnMyLocationClickListener listener);

    /**
     * Sets a callback that's invoked when this map has finished rendering. The callback will only
     * be invoked once.
     *
     * If this method is called when the map is fully rendered, the callback will be invoked
     * immediately. This event will not fire if the map never loads due to connectivity issues, or
     * if the map is continuously changing and never completes loading due to the user constantly
     * interacting with the map.
     *
     * @param callback The callback that's invoked. To unset the callback, use {@code null}.
     */
    void setOnMapLoadedCallback(@Nullable OnMapLoadedCallback callback);

    /**
     * Sets a callback that's invoked when a ground overlay is clicked.
     *
     * @param listener The callback that's invoked. To unset the callback, use {@code null}.
     */
    void setOnGroundOverlayClickListener(@Nullable OnGroundOverlayClickListener listener);

    /**
     * Sets a callback that's invoked when a circle is clicked.
     *
     * @param listener The callback that's invoked. To unset the callback, use {@code null}.
     */
    void setOnCircleClickListener(@Nullable OnCircleClickListener listener);

    /**
     * Sets a callback that's invoked when a polygon is clicked.
     *
     * @param listener The callback that's invoked. To unset the callback, use {@code null}.
     */
    void setOnPolygonClickListener(@Nullable OnPolygonClickListener listener);

    /**
     * Sets a callback that's invoked when a polyline is clicked.
     *
     * @param listener The callback that's invoked. To unset the callback, use {@code null}.
     */
    void setOnPolylineClickListener(@Nullable OnPolylineClickListener listener);

    /**
     * Takes a snapshot of the map.
     *
     * <p>You can use snapshots within your application when an interactive map would be difficult,
     * or impossible, to use. For example, images produced with the {@code snapshot()} method can
     * be used to display a thumbnail of the map in your app, or a snapshot in the notification
     * center.
     *
     * <p><b>Note</b>: Images of the map must not be transmitted to your servers, or otherwise used
     * outside of the application. If you need to send a map to another application or user, send
     * data that allows them to reconstruct the map for the new user instead of a snapshot.
     *
     * @param callback Callback method invoked when the snapshot is taken.
     */
    void snapshot(@NonNull SnapshotReadyCallback callback);

    /**
     * Takes a snapshot of the map.
     *
     * <p>This method is equivalent to {@link #snapshot(SnapshotReadyCallback)} but lets you
     * provide a pre-allocated {@link Bitmap}. If the bitmap does not match the current dimensions
     * of the map, another bitmap will be allocated that fits the map's dimensions.
     *
     * <p>Although in most cases the object passed by the callback method is the same as the one
     * given in the parameter to this method, in some cases the returned object can be different
     * (e.g., if the view's dimensions have changed by the time the snapshot is actually taken).
     * Thus, you should only trust the content of the bitmap passed by the callback method.
     *
     * @param callback Callback method invoked when the snapshot is taken.
     * @param bitmap A pre-allocated bitmap. If {@code null}, behaves like {@link
     * #snapshot(SnapshotReadyCallback)}.
     */
    void snapshot(@NonNull SnapshotReadyCallback callback, @Nullable Bitmap bitmap);

    /**
     * Sets padding on the map.
     *
     * <p>This method allows you to define a visible region on the map, to signal to the map that
     * portions of the map around the edges may be obscured, by setting padding on each of the four
     * edges of the map. Map functions will be adapted to the padding. For example, the zoom
     * controls, compass, copyright notices and logo will be moved to fit inside the defined region,
     * camera movements will be relative to the center of the visible region, etc.
     *
     * @param left The number of pixels of padding to be added on the left of the map.
     * @param top The number of pixels of padding to be added on the top of the map.
     * @param right The number of pixels of padding to be added on the right of the map.
     * @param bottom The number of pixels of padding to be added on the bottom of the map.
     */
    void setPadding(int left, int top, int right, int bottom);

    /**
     * Sets a contentDescription for the map.
     *
     * <p>This is used to provide a spoken description of the map in accessibility mode.
     *
     * @param description A string to use as a description.
     */
    void setContentDescription(String description);

    /**
     * Sets a listener which will be triggered when a POI is clicked or tapped.
     *
     * <p>Pass {@code null} to clear the listener.
     *
     * @param listener The callback that's invoked when a POI is clicked. To unset the callback,
     * use {@code null}.
     */
    void setOnPoiClickListener(@Nullable OnPoiClickListener listener);

    /**
     * Sets the styling of the base map.
     *
     * <p>Using the style options, you can apply custom styles to features and elements on the map.
     * See {@link MapClient.Style.Options} for style definition details.
     *
     * <p>Set to {@code null} to clear any previous custom styling.
     *
     * @param style The style of map to display.
     * @return {@code true} if the style was successfully parsed; {@code false} if problems were
     * detected with the {@link MapClient.Style.Options}, including, e.g. unparsable styling JSON,
     * unrecognized feature type, unrecognized element type, or invalid styler keys. If the return
     * value is {@code false}, the current style is left unchanged.
     */
    boolean setMapStyle(@Nullable Style.Options style);

    /**
     * Sets a preferred lower bound for the camera zoom.
     *
     * <p>When the minimum zoom changes, the SDK adjusts all later camera updates to respect that
     * minimum if possible. Note that there are technical considerations that may prevent the SDK
     * from allowing users to zoom too low.
     *
     * <p>The SDK resolves any conflicts in the minimum and maximum values. If you set the minimum
     * zoom to a higher zoom level than the current maximum, then the SDK uses the new minimum
     * value for both minimum and maximum. If you set the maximum zoom to a lower zoom level than
     * the minimum, the SDK uses the new maximum value for both minimum and maximum. For example:
     * Assume the current minimum zoom is 8 and the maximum is 14. Then you set the minimum zoom
     * to 16. The SDK uses a minimum zoom of 16 and a maximum zoom of 16.
     *
     * @param minZoomPreference The preferred lower bound.
     */
    void setMinZoomPreference(float minZoomPreference);

    /**
     * Sets a preferred upper bound for the camera zoom.
     *
     * <p>When the maximum zoom changes, the SDK adjusts all later camera updates to respect that
     * maximum if possible. Note that there are technical considerations that may prevent the SDK
     * from allowing users to zoom too deep into the map. For example, satellite or terrain may
     * have a lower maximum zoom than the base map tiles.
     *
     * <p>The SDK resolves any conflicts in the minimum and maximum values. If you set the minimum
     * zoom to a higher zoom level than the current maximum, then the SDK uses the new minimum
     * value for both minimum and maximum. If you set the maximum zoom to a lower zoom level than
     * the minimum, the SDK uses the new maximum value for both minimum and maximum. For example:
     * Assume the current minimum zoom is 8 and the maximum is 14. Then you set the maximum zoom
     * to 6. The SDK uses a minimum zoom of 6 and a maximum zoom of 6.
     *
     * @param maxZoomPreference The preferred upper bound.
     */
    void setMaxZoomPreference(float maxZoomPreference);

    /**
     * Removes any previously specified upper and lower zoom bounds.
     */
    void resetMinMaxZoomPreference();

    /**
     * Specifies a {@link LatLngBounds} to constrain the camera target, so that when users scroll
     * and pan the map, the camera target does not move outside these bounds.
     *
     * <p>Set to {@code null} to clear the bounding box completely. The new bounds replaces any
     * previously specified bounding box.
     *
     * <p>When the LatLngBounds changes, the SDK adjusts all later camera updates to respect those
     * bounds if possible. Note that there are technical considerations that may prevent the SDK
     * from keeping the camera target strictly within the bounds. For example, floating point
     * precision rounding errors or a very low zoom level.
     *
     * @param bounds The bounds to constrain the camera target within.
     */
    void setLatLngBoundsForCameraTarget(@Nullable LatLngBounds bounds);


    /**
     * Interface definition for a callback to be invoked when a POI is clicked.
     */
    interface OnPoiClickListener {
        /**
         * Called when a POI is clicked.
         *
         * <p>This is called on the Android UI thread.
         *
         * @param poi The point of interest that was clicked.
         */
        @UiThread
        void onPoiClick(@NonNull PointOfInterest poi);
    }

    /**
     * Interface definition for a callback to be invoked when a ground overlay is clicked.
     */
    interface OnGroundOverlayClickListener {
        /**
         * Called when a ground overlay is clicked.
         *
         * <p>This is called on the Android UI thread.
         *
         * @param groundOverlay The ground overlay that was clicked.
         */
        @UiThread
        void onGroundOverlayClick(@NonNull GroundOverlay groundOverlay);
    }

    /**
     * Interface definition for a callback to be invoked when the map has finished rendering.
     * This occurs after all tiles required to render the map have been fetched, and all labeling
     * is complete. This event will not fire if the map never loads due to connectivity issues, or
     * if the map is continuously changing and never completes loading due to the user constantly
     * interacting with the map.
     */
    interface OnMapLoadedCallback {
        /**
         * Called when the map has finished rendering. This will only be called once. You must
         * request another callback if you want to be notified again.
         *
         * <p>This is called on the Android UI thread.
         */
        @UiThread
        void onMapLoaded();
    }

    /**
     * Interface definition for a callback to be invoked when the My Location dot (which signifies
     * the user's location) is clicked.
     */
    interface OnMyLocationClickListener {
        /**
         * Called when the My Location dot is clicked.
         *
         * <p>This is called on the Android UI thread.
         */
        @UiThread
        void onMyLocationClick(@NonNull Location location);
    }

    /**
     * Interface definition for a callback to be invoked when the My Location button is clicked.
     */
    interface OnMyLocationButtonClickListener {
        /**
         * Called when the my location button is clicked.
         *
         * <p>This is called on the Android UI thread.
         *
         * @return {@code true} if the listener has consumed the event (i.e., the default behavior
         * should not occur); {@code false} otherwise (i.e., the default behavior should occur).
         * The default behavior is for the camera move such that it is centered on the user
         * location.
         */
        @UiThread
        boolean onMyLocationButtonClick();
    }

    /**
     * Provides views for customized rendering of info windows.
     *
     * <p>Methods on this provider are called when it is time to show an info window for a marker,
     * regardless of the cause (either a user gesture or a programmatic call to {@link
     * Marker#showInfoWindow() showInfoWindow()}. Since there is only one info window shown at any
     * one time, this provider may choose to reuse views, or it may choose to create new views on
     * each method invocation.
     *
     * <p>When constructing an info window, methods in this class are called in a defined order. To
     * replace the default info window, override {@link #getInfoWindow(Marker)} with your custom
     * rendering and return {@code null} for {@link #getInfoContents(Marker)}. To replace only the
     * info window contents inside the default info window frame (the callout bubble), return
     * {@code null} in {@link #getInfoWindow(Marker)} and override {@link #getInfoContents(Marker)}
     * instead.
     */
    interface InfoWindowAdapter {
        /**
         * Provides a custom info window for a marker. If this method returns a view, it is used
         * for the entire info window. If you change this view after this method is called, those
         * changes will not necessarily be reflected in the rendered info window. If this method
         * returns {@code null}, the default info window frame will be used, with contents provided
         * by {@link #getInfoContents(Marker)}.
         *
         * @param marker The marker for which an info window is being populated.
         * @return A custom info window for {@code marker}, or {@code null} to use the default
         * info window frame with {@linkplain #getInfoContents(Marker) custom contents}.
         */
        @Nullable View getInfoWindow(@NonNull Marker marker);

        /**
         * Provides custom contents for the default info window frame of a marker. This method is
         * only called if {@link #getInfoWindow(Marker)} first returns {@code null}. If this method
         * returns a view, it will be placed inside the default info window frame. If you change
         * this view after this method is called, those changes will not necessarily be reflected
         * in the rendered info window. If this method returns {@code null}, the default rendering
         * will be used instead.
         *
         * @param marker The marker for which an info window is being populated.
         * @return A custom view to display as contents in the info window for {@code marker}, or
         * {@code null} to use the default content rendering instead.
         */
        @Nullable View getInfoContents(@NonNull Marker marker);
    }

    /**
     * Interface definition for a callback to be invoked when the snapshot has been taken.
     */
    interface SnapshotReadyCallback {
        /**
         * Invoked when the snapshot has been taken.
         *
         * <p>This is called on the Android UI thread.
         *
         * @param snapshot A bitmap containing the map as it is currently rendered, or {@code null}
         * if the snapshot could not be taken.
         */
        @UiThread
        void onSnapshotReady(@Nullable Bitmap snapshot);
    }

    /**
     * Interface definition for a callback to be invoked when a task is complete or canceled.
     */
    interface CancelableCallback {
        /**
         * Invoked when a task is canceled.
         */
        void onCancel();

        /**
         * Invoked when a task is complete.
         */
        void onFinish();
    }

    /**
     * Interface definition for a callback to be invoked when a marker's info window is closed.
     */
    interface OnInfoWindowCloseListener {
        /**
         * Called when the marker's info window is closed.
         *
         * <p>This is called on the Android UI thread.
         *
         * @param marker The marker of the info window that was closed.
         */
        @UiThread
        void onInfoWindowClose(@NonNull Marker marker);
    }

    /**
     * Interface definition for a callback to be invoked when a marker's info window is long
     * clicked.
     */
    interface OnInfoWindowLongClickListener {
        /**
         * Called when the marker's info window is clicked.
         *
         * <p>This is called on the Android UI thread.
         *
         * @param marker The marker to which the info window is anchored.
         */
        @UiThread
        void onInfoWindowLongClick(@NonNull Marker marker);
    }

    /**
     * Interface definition for a callback to be invoked when a marker's info window is clicked.
     */
    interface OnInfoWindowClickListener {
        /**
         * Called when the marker's info window is clicked.
         *
         * <p>This is called on the Android UI thread.
         *
         * @param marker The marker of the info window that was clicked.
         */
        @UiThread
        void onInfoWindowClick(@NonNull Marker marker);
    }

    /**
     * Interface definition for a callback to be invoked when the user drags a marker.
     */
    interface OnMarkerDragListener {
        /**
         * Called when a marker starts being dragged. The marker's location can be accessed via
         * {@link Marker#getPosition() getPosition()}; this position may be different to the
         * position prior to the start of the drag because the marker is popped up above the
         * touch point.
         *
         * <p>This is called on the Android UI thread.
         *
         * @param marker The marker being dragged.
         */
        @UiThread
        void onMarkerDragStart(@NonNull Marker marker);

        /**
         * Called repeatedly while a marker is being dragged. The marker's location can be accessed
         * via {@link Marker#getPosition()}.
         *
         * <p>This is called on the Android UI thread.
         *
         * @param marker The marker being dragged.
         */
        @UiThread
        void onMarkerDrag(@NonNull Marker marker);

        /**
         * Called when a marker has finished being dragged. The marker's location can be accessed
         * via {@link Marker#getPosition()}.
         *
         * <p>This is called on the Android UI thread.
         *
         * @param marker The marker that was dragged.
         */
        @UiThread
        void onMarkerDragEnd(@NonNull Marker marker);
    }

    /**
     * Interface definition for a callback to be invoked when a marker is clicked.
     */
    interface OnMarkerClickListener {
        /**
         * Called when a marker has been clicked.
         *
         * Note: the first thing that happens when a marker is clicked or tapped is that any
         * currently showing info window is closed, and the {@link OnInfoWindowCloseListener}
         * is triggered. Then the {@code OnMarkerClickListener} is triggered. Therefore, calling
         * {@link Marker#isInfoWindowShown() isInfoWindowShown()} on any marker from the {@code
         * OnMarkerClickListener} will return {@code false}.
         *
         * @param marker The marker that was clicked.
         * @return {@code true} if the listener has consumed the event (i.e., the default behavior
         * should not occur); {@code false} otherwise (i.e., the default behavior should occur).
         * The default behavior is for the camera to move to the marker and an info window to
         * appear.
         */
        @UiThread
        boolean onMarkerClick(@NonNull Marker marker);
    }

    /**
     * Interface definition for a callback to be invoked when a polyline is clicked.
     */
    interface OnPolylineClickListener {
        /**
         * Called when a polyline is clicked.
         *
         * <p>This is called on the Android UI thread.
         *
         * @param polyline The polyline that is clicked.
         */
        @UiThread
        void onPolylineClick(@NonNull Polyline polyline);
    }

    /**
     * Interface definition for a callback to be invoked when a polygon is clicked.
     */
    interface OnPolygonClickListener {
        /**
         * Called when a polygon is clicked.
         *
         * <p>This is called on the Android UI thread.
         *
         * @param polygon The polygon that is clicked.
         */
        @UiThread
        void onPolygonClick(@NonNull Polygon polygon);
    }

    /**
     * Interface definition for a callback to be invoked when a circle is clicked.
     */
    interface OnCircleClickListener {
        /**
         * Called when a circle is clicked.
         *
         * <p>This is called on the Android UI thread.
         *
         * @param circle The circle that is clicked.
         */
        @UiThread
        void onCircleClick(@NonNull Circle circle);
    }

    /**
     * Interface definition for a callback to be invoked when the camera movement has ended.
     */
    interface OnCameraIdleListener {
        /**
         * Called when camera movement has ended, there are no pending animations and the user has
         * stopped interacting with the map.
         *
         * <p>This is called on the Android UI thread.
         */
        @UiThread
        void onCameraIdle();
    }

    /**
     * Interface definition for a callback to be invoked when the camera movement has started.
     */
    interface OnCameraMoveStartedListener {
        /**
         * Camera motion initiated in response to user gestures on the map. For example: pan, tilt,
         * pinch to zoom, or rotate.
         */
        int REASON_GESTURE = 1;

        /**
         * Non-gesture animation initiated in response to user actions. For example: zoom buttons,
         * my location button, or marker clicks.
         */
        int REASON_API_ANIMATION = 2;

        /**
         * Developer initiated animation.
         */
        int REASON_DEVELOPER_ANIMATION = 3;

        /**
         * Called when the camera starts moving after it has been idle or when the reason for
         * camera motion has changed. Do not update or animate the camera from within this method.
         *
         * <p>This is called on the Android UI thread.
         *
         * @param reason The reason for the camera change. Possible values:
         * <ul>
         * <li>{@link #REASON_GESTURE}: User gestures on the map.
         * <li>{@link #REASON_API_ANIMATION}: Default animations resulting from user interaction.
         * <li>{@link #REASON_DEVELOPER_ANIMATION}: Developer animations.
         * </ul>
         */
        @UiThread
        void onCameraMoveStarted(int reason);
    }

    /**
     * Interface definition for a callback to be invoked when the camera's position is changed.
     */
    interface OnCameraMoveListener {
        /**
         * Called repeatedly as the camera continues to move after an {@link
         * OnCameraMoveStartedListener#onCameraMoveStarted onCameraMoveStarted()} call. This may be
         * called as often as once every frame and should not perform expensive operations.
         *
         * <p>This is called on the Android UI thread.
         */
        @UiThread
        void onCameraMove();
    }

    /**
     * Interface definition for a callback to be invoked when the camera's motion has been stopped
     * or when the camera starts moving for a new reason.
     */
    interface OnCameraMoveCanceledListener {
        /**
         * Called when the developer explicitly calls the {@link #stopAnimation()} method or if the
         * reason for camera motion has changed before the {@link
         * OnCameraIdleListener#onCameraIdle onCameraIdle} had a chance to fire after the previous
         * animation. Do not update or animate the camera from within this method.
         *
         * <p>This is called on the Android UI thread.
         */
        @UiThread
        void onCameraMoveCanceled();
    }

    /**
     * Interface definition for a callback to be invoked when the map is long pressed.
     */
    interface OnMapLongClickListener {
        /**
         * Called when the user makes a long-press gesture on the map, but only if none of the
         * overlays of the map handled the gesture.
         *
         * <p>This is called on the Android UI thread.
         */
        @UiThread
        void onMapLongClick(@NonNull LatLng point);
    }

    /**
     * Interface definition for a callback to be invoked when the map is clicked.
     */
    interface OnMapClickListener {
        /**
         * Called when the user makes a tap gesture on the map, but only if none of the overlays of
         * the map handled the gesture.
         *
         * <p>This is called on the Android UI thread.
         *
         * @param point The point on the ground (projected from the screen point) that was tapped.
         */
        @UiThread
        void onMapClick(@NonNull LatLng point);
    }

    /**
     * Interface definition for a callback to be invoked when the indoor state changes.
     */
    interface OnIndoorStateChangeListener {
        /**
         * The map maintains a notion of focused building, which is the building currently centered
         * in the viewport or otherwise selected by the user through the UI or the location
         * provider. This callback is called when the focused building changes.
         *
         * <p>This method will only be called after the building data has become available.
         *
         * <p>The focused building is not referenced as a parameter of this method due to
         * synchronization issues: if multiple focus requests are handled, listeners may be
         * notified out-of-order, so should rely on getFocusedBuilding() itself to provide the most
         * up-to-date information. It is possible that more than one onIndoorBuildingFocused call
         * will be made without the focused building actually changing.
         *
         * <p>This is called on the Android UI thread.
         */
        @UiThread
        void onIndoorBuildingFocused();

        /**
         * The map keeps track of the active level for each building which has been visited or
         * otherwise had a level selected. When that level changes, this callback will be triggered
         * regardless of whether the building is focused or not. This callback is also called when
         * the default level first becomes available.
         *
         * <p>This method will only be called after the building data has become available.
         *
         * <p>This is called on the Android UI thread.
         *
         * @param building The building for which the active level has changed, never null.
         */
        @UiThread
        void onIndoorLevelActivated(@NonNull IndoorBuilding building);
    }


    interface Style {
        /**
         * Defines styling options for a {@link MapClient}.
         *
         * <p>With style options you can customize the presentation of the standard map styles,
         * changing the visual display of features like roads, parks, and other points of interest.
         * As well as changing the style of these features, you can also hide features entirely.
         * This means that you can emphasize particular components of the map or make the map
         * complement the content of your app.
         */
        interface Options {}
    }


    /**
     * Settings for the user interface for a {@link MapClient}. To obtain this interface, call
     * {@link MapClient#getUiSettings getUiSettings()}.
     */
    interface UiSettings {
        /**
         * Enables or disables the zoom controls. If enabled, the zoom controls are a pair of
         * buttons (one for zooming in, one for zooming out) that appear on the screen. When
         * pressed, they cause the camera to zoom in (or out) by one zoom level. If disabled,
         * the zoom controls are not shown.
         *
         * <p>By default, the zoom controls are enabled.
         *
         * @param enabled {@code true} to enable the zoom controls; {@code false} to disable the
         * zoom controls.
         */
        void setZoomControlsEnabled(boolean enabled);

        /**
         * Enables or disables the compass. The compass is an icon on the map that indicates the
         * direction of north on the map. If enabled, it is only shown when the camera is tilted
         * or rotated away from its default orientation (tilt of 0 and a bearing of 0). When a
         * user clicks the compass, the camera orients itself to its default orientation and fades
         * away shortly after. If disabled, the compass will never be displayed.
         *
         * <p>By default, the compass is enabled (and hence shown when the camera is not in the
         * default orientation).
         *
         * @param enabled {@code true} to enable the compass; {@code false} to disable the compass.
         */
        void setCompassEnabled(boolean enabled);

        /**
         * Enables or disables the my-location button. The my-location button causes the camera to
         * move such that the user's location is in the center of the map. If the button is
         * enabled, it is only shown when the my-location layer is enabled.
         *
         * <p>By default, the my-location button is enabled (and hence shown when the my-location
         * layer is enabled).
         *
         * @param enabled {@code true} to enable the my-location button; {@code false} to disable
         * the my-location button.
         */
        void setMyLocationButtonEnabled(boolean enabled);

        /**
         * Sets whether the indoor level picker is enabled when indoor mode is enabled. If {@code
         * true}, the level picker will appear when a building with indoor maps is focused. If
         * {@code false}, no level picker will appear - an application will need to provide its
         * own way of selecting levels.
         *
         * <p>The default behaviour is to show the level picker.
         *
         * @param enabled {@code true} to show; {@code false} to hide the level picker.
         */
        void setIndoorLevelPickerEnabled(boolean enabled);

        /**
         * Sets the preference for whether scroll gestures should be enabled or disabled. If
         * enabled, users can swipe to pan the camera. If disabled, swiping has no effect. This
         * setting doesn't restrict programmatic movement and animation of the camera.
         *
         * <p>By default, scroll gestures are enabled.
         *
         * @param enabled {@code true} to enable scroll gestures; {@code false} to disable scroll
         * gestures.
         */
        void setScrollGesturesEnabled(boolean enabled);

        /**
         * Sets the preference for whether zoom gestures should be enabled or disabled. If enabled,
         * users can either double tap/two-finger tap or pinch to zoom the camera. If disabled,
         * these gestures have no effect. This setting doesn't affect the zoom buttons, nor does
         * it restrict programmatic movement and animation of the camera.
         *
         * <p>By default, zoom gestures are enabled.
         *
         * @param enabled {@code true} to enable zoom gestures; {@code false} to disable zoom
         * gestures.
         */
        void setZoomGesturesEnabled(boolean enabled);

        /**
         * Sets the preference for whether tilt gestures should be enabled or disabled. If enabled,
         * users can use a two-finger vertical down swipe to tilt the camera. If disabled, users
         * cannot tilt the camera via gestures. This setting doesn't restrict users from tapping
         * the compass icon to reset the camera orientation, nor does it restrict programmatic
         * movement and animation of the camera.
         *
         * <p>By default, tilt gestures are enabled.
         *
         * @param enabled {@code true} to enable tilt gestures; {@code false} to disable tilt
         * gestures.
         */
        void setTiltGesturesEnabled(boolean enabled);

        /**
         * Sets the preference for whether rotate gestures should be enabled or disabled. If
         * enabled, users can use a two-finger rotate gesture to rotate the camera. If disabled,
         * users cannot rotate the camera via gestures. This setting doesn't restrict the user from
         * tapping the compass icon to reset the camera orientation, nor does it restrict
         * programmatic movements and animation of the camera.
         *
         * <p>By default, rotate gestures are enabled.
         *
         * @param enabled {@code true} to enable rotate; {@code false} to disable rotate gestures.
         */
        void setRotateGesturesEnabled(boolean enabled);

        /**
         * Sets the preference for whether scroll gestures can take place at the same time as a
         * zoom or rotate gesture. If enabled, users can scroll the map while rotating or zooming
         * the map. If disabled, the map cannot be scrolled while the user rotates or zooms the
         * map using gestures. This setting doesn't disable scroll gestures entirely, only during
         * rotation and zoom gestures, nor does it restrict programmatic movements and animation
         * of the camera.
         *
         * <p>By default, scroll gestures are enabled during rotation and zoom.
         *
         * @param enabled {@code true} to enable scroll gestures during rotate or zoom gestures;
         * {@code false} to disable scroll gestures during rotate or zoom gestures.
         */
        void setScrollGesturesEnabledDuringRotateOrZoom(boolean enabled);

        /**
         * Sets the preference for whether all gestures should be enabled or disabled. If enabled,
         * all gestures are available; otherwise, all gestures are disabled. This doesn't restrict
         * users from tapping any on screen buttons to move the camera (e.g., compass or zoom
         * controls), nor does it restrict programmatic movements and animation.
         *
         * @param enabled {@code true} to enable all gestures; {@code false} to disable all
         * gestures.
         */
        void setAllGesturesEnabled(boolean enabled);

        /**
         * Sets the preference for whether the Map Toolbar should be enabled or disabled. If
         * enabled, and the Map Toolbar can be shown in the current context, users will see a bar
         * with various context-dependent actions.
         *
         * <p>By default, the Map Toolbar is enabled.
         *
         * @param enabled {@code true} to enable the Map Toolbar; {@code false} to disable the Map
         * Toolbar.
         */
        void setMapToolbarEnabled(boolean enabled);

        /**
         * Gets whether the zoom controls are enabled/disabled.
         *
         * @return {@code true} if the zoom controls are enabled; {@code false} if the zoom
         * controls are disabled;
         */
        boolean isZoomControlsEnabled();

        /**
         * Gets whether the compass is enabled/disabled.
         *
         * @return {@code true} if the compass is enabled; {@code false} if the compass is
         * disabled.
         */
        boolean isCompassEnabled();

        /**
         * Gets whether the my-location button is enabled/disabled.
         *
         * @return {@code true} if the my-location button is enabled; {@code false} if the
         * my-location button is disabled.
         */
        boolean isMyLocationButtonEnabled();

        /**
         * Gets whether the indoor level picker is enabled/disabled. That is, whether the level
         * picker will appear when a building with indoor maps is focused.
         *
         * @return {@code true} if the level picker is enabled; {@code false} if the level picker
         * is disabled.
         */
        boolean isIndoorLevelPickerEnabled();

        /**
         * Gets whether scroll gestures are enabled/disabled.
         *
         * @return {@code true} if scroll gestures are enabled; {@code false} if scroll gestures
         * are disabled.
         */
        boolean isScrollGesturesEnabled();

        /**
         * Gets whether scroll gestures are enabled/disabled during rotation and zoom gestures.
         *
         * @return {@code true} if scroll gestures are enabled during rotate or zoom gestures;
         * {@code false} if scroll gestures are disabled during rotate or zoom gestures.
         */
        boolean isScrollGesturesEnabledDuringRotateOrZoom();

        /**
         * Gets whether zoom gestures are enabled/disabled.
         *
         * @return {@code true} if zoom gestures are enabled; {@code false} if zoom gestures are
         * disabled.
         */
        boolean isZoomGesturesEnabled();

        /**
         * Gets whether tilt gestures are enabled/disabled.
         *
         * @return {@code true} if tilt gestures are enabled; {@code false} if tilt gestures are
         * disabled.
         */
        boolean isTiltGesturesEnabled();

        /**
         * Gets whether rotate gestures are enabled/disabled.
         *
         * @return {@code true} if rotate gestures are enabled; {@code false} if rotate gestures
         * are disabled.
         */
        boolean isRotateGesturesEnabled();

        /**
         * Gets whether the Map Toolbar is enabled/disabled.
         *
         * @return {@code true} if the Map Toolbar is enabled; {@code false} otherwise.
         */
        boolean isMapToolbarEnabled();
    }

}
