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

package dev.supasintatiyanupanwong.libraries.android.kits.maps;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.RawRes;
import androidx.annotation.UiThread;

import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.BitmapDescriptor;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.ButtCap;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.CameraPosition;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.CameraUpdate;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.Circle;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.CustomCap;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.Dash;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.Dot;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.Gap;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.GroundOverlay;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.LatLng;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.LatLngBounds;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.MapClient;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.MapFactory;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.Marker;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.Polygon;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.Polyline;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.RoundCap;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.SquareCap;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.Tile;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.TileOverlay;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.TileProvider;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.UrlTileProvider;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.VisibleRegion;

/**
 * The main entry point for Maps APIs.
 *
 * @since 1.0.0
 */
public final class MapKit {

    private static final MapFactory FACTORY = MapsPlatform.get().getFactory();

    private MapKit() {} // No instances!


    /**
     * @since 1.2.0
     */
    @NonNull
    public static BitmapDescriptor.Factory getBitmapDescriptorFactory() {
        return FACTORY.getBitmapDescriptorFactory();
    }

    /**
     * @since 1.2.0
     */
    @NonNull
    public static ButtCap newButtCap() {
        return FACTORY.newButtCap();
    }

    /**
     * @since 1.2.0
     */
    @NonNull
    public static CameraUpdate.Factory getCameraUpdateFactory() {
        return FACTORY.getCameraUpdateFactory();
    }

    /**
     * @since 1.2.0
     */
    @NonNull
    public static CameraPosition newCameraPositionFromLatLngZoom(
            @NonNull LatLng target, float zoom) {
        return FACTORY.newCameraPositionFromLatLngZoom(target, zoom);
    }

    /**
     * @since 1.2.0
     */
    @NonNull
    public static CameraPosition.Builder newCameraPositionBuilder() {
        return FACTORY.newCameraPositionBuilder();
    }

    /**
     * @since 1.2.0
     */
    @NonNull
    public static CameraPosition.Builder newCameraPositionBuilder(@NonNull CameraPosition camera) {
        return FACTORY.newCameraPositionBuilder(camera);
    }

    /**
     * @since 1.2.0
     */
    @NonNull
    public static Circle.Options newCircleOptions() {
        return FACTORY.newCircleOptions();
    }

    /**
     * @since 1.2.0
     */
    @NonNull
    public static CustomCap newCustomCap(
            @NonNull BitmapDescriptor bitmapDescriptor, float refWidth) {
        return FACTORY.newCustomCap(bitmapDescriptor, refWidth);
    }

    /**
     * @since 1.2.0
     */
    @NonNull
    public static CustomCap newCustomCap(@NonNull BitmapDescriptor bitmapDescriptor) {
        return FACTORY.newCustomCap(bitmapDescriptor);
    }

    /**
     * @since 1.2.0
     */
    @NonNull
    public static Dot newDot() {
        return FACTORY.newDot();
    }

    /**
     * @since 1.2.0
     */
    @NonNull
    public static Dash newDash(float length) {
        return FACTORY.newDash(length);
    }

    /**
     * @since 1.2.0
     */
    @NonNull
    public static Gap newGap(float length) {
        return FACTORY.newGap(length);
    }

    /**
     * @since 1.2.0
     */
    @NonNull
    public static GroundOverlay.Options newGroundOverlayOptions() {
        return FACTORY.newGroundOverlayOptions();
    }

    /**
     * @since 1.2.0
     */
    @NonNull
    public static LatLng newLatLng(double latitude, double longitude) {
        return FACTORY.newLatLng(latitude, longitude);
    }

    /**
     * @since 1.2.0
     */
    @NonNull
    public static LatLngBounds newLatLngBounds(
            @NonNull LatLng southwest, @NonNull LatLng northeast) {
        return FACTORY.newLatLngBounds(southwest, northeast);
    }

    /**
     * @since 1.2.0
     */
    @NonNull
    public static LatLngBounds.Builder newLatLngBoundsBuilder() {
        return FACTORY.newLatLngBoundsBuilder();
    }

    /**
     * @since 1.2.0
     */
    @NonNull
    public static MapClient.Style.Options newMapStyleOptions(String json) {
        return FACTORY.newMapStyleOptions(json);
    }

    /**
     * @since 1.2.0
     */
    @NonNull
    public static MapClient.Style.Options newMapStyleOptions(
            @NonNull Context context, @RawRes int resourceId) {
        return FACTORY.newMapStyleOptions(context, resourceId);
    }

    /**
     * @since 1.2.0
     */
    @NonNull
    public static Marker.Options newMarkerOptions() {
        return FACTORY.newMarkerOptions();
    }

    /**
     * @since 1.2.0
     */
    @NonNull
    public static Polygon.Options newPolygonOptions() {
        return FACTORY.newPolygonOptions();
    }

    /**
     * @since 1.2.0
     */
    @NonNull
    public static Polyline.Options newPolylineOptions() {
        return FACTORY.newPolylineOptions();
    }

    /**
     * @since 1.2.0
     */
    @NonNull
    public static RoundCap newRoundCap() {
        return FACTORY.newRoundCap();
    }

    /**
     * @since 1.2.0
     */
    @NonNull
    public static SquareCap newSquareCap() {
        return FACTORY.newSquareCap();
    }

    /**
     * @since 1.2.0
     */
    @NonNull
    public static TileOverlay.Options newTileOverlayOptions() {
        return FACTORY.newTileOverlayOptions();
    }

    /**
     * @since 1.2.0
     */
    @NonNull
    public static Tile newTile(int width, int height, byte[] data) {
        return FACTORY.newTile(width, height, data);
    }

    /**
     * @since 1.2.0
     */
    @NonNull
    public static Tile noTile() {
        return FACTORY.noTile();
    }

    /**
     * @since 1.2.0
     */
    @NonNull
    public static TileProvider newUrlTileProvider(
            int width, int height, @NonNull UrlTileProvider tileProvider) {
        return FACTORY.newUrlTileProvider(width, height, tileProvider);
    }

    /**
     * @since 1.2.0
     */
    @NonNull
    public static VisibleRegion newVisibleRegion(
            LatLng nearLeft,
            LatLng nearRight,
            LatLng farLeft,
            LatLng farRight,
            LatLngBounds latLngBounds) {
        return FACTORY.newVisibleRegion(nearLeft, nearRight, farLeft, farRight, latLngBounds);
    }


    /**
     * Interface definition for a callback to be invoked when the map is ready to be used.
     *
     * <p>Once an instance of this interface is set on a {@link MapFragment} object, the {@link
     * #onMapReady(MapClient)} method is triggered when the map is ready to be used and provides
     * a non-null instance of {@link MapClient}.
     *
     * @since 1.2.0
     */
    public interface OnMapReadyCallback {
        /**
         * Called when the map is ready to be used.
         *
         * <p>Note that this does not guarantee that the map has undergone layout. Therefore, the
         * map's size may not have been determined by the time the callback method is called. If
         * you need to know the dimensions or call a method in the API that needs to know the
         * dimensions, use {@link OnMapAndViewReadyCallback} instead which encapsulate the logic
         * of {@code OnMapReadyCallback} and {@code View.OnGlobalLayoutListener}.
         *
         * <p>As an example, if you want to update the map's camera using a {@link LatLngBounds}
         * without dimensions, there is a race condition that could trigger an {@link
         * IllegalStateException} if the map has not undergone layout.
         *
         * @param map A non-null instance of a {@link MapClient} associated with the {@link
         * MapFragment} that defines the callback.
         */
        @UiThread
        void onMapReady(@NonNull MapClient map);
    }

    /**
     * Interface definition for a callback to be invoked when the map has undergone layout and
     * ready to be used.
     *
     * <p>Note that this is only necessary if a developer wishes to immediately invoke any method
     * on the {@link MapClient} that also requires the View to have finished layout (ie. anything
     * that needs to know the View's true size like snapshotting).
     *
     * <p>Once an instance of this interface is set on a {@link MapFragment} object, the {@link
     * #onMapAndViewReady(MapClient)} method is triggered when the map has undergone layout and is
     * ready to be used and provides a non-null instance of {@link MapClient}.
     */
    public interface OnMapAndViewReadyCallback {
        /**
         * Called when the map has undergone layout and ready to be used.
         *
         * @param map A non-null instance of a {@link MapClient} associated with the {@link
         * MapFragment} that defines the callback.
         */
        @UiThread
        void onMapAndViewReady(@NonNull MapClient map);
    }

}
