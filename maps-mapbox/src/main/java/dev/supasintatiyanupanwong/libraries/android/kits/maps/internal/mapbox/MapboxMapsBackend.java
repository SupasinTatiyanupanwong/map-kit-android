/*
 * Copyright 2022 Supasin Tatiyanupanwong
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

package dev.supasintatiyanupanwong.libraries.android.kits.maps.internal.mapbox;

import android.content.Context;
import android.view.View;

import androidx.annotation.IdRes;
import androidx.annotation.Keep;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.mapbox.maps.MapView;

import dev.supasintatiyanupanwong.libraries.android.kits.maps.MapKit;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.MapKitBackend;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.internal.mapbox.model.MapboxBitmapDescriptorFactory;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.internal.mapbox.model.MapboxCameraPosition;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.internal.mapbox.model.MapboxCameraUpdateFactory;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.internal.mapbox.model.MapboxLatLng;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.internal.mapbox.model.MapboxLatLngBounds;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.internal.mapbox.model.MapboxMapClient;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.internal.mapbox.model.MapboxMarkerOptions;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.internal.nil.model.NilCap;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.internal.nil.model.NilCircle;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.internal.nil.model.NilGroundOverlay;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.internal.nil.model.NilMapClient;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.internal.nil.model.NilMarker;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.internal.nil.model.NilPatternItem;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.internal.nil.model.NilPolygon;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.internal.nil.model.NilPolyline;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.internal.nil.model.NilTile;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.internal.nil.model.NilTileOverlay;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.internal.nil.model.NilTileProvider;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.internal.nil.model.NilVisibleRegion;
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

@SuppressWarnings("unused")
class MapboxMapsBackend implements MapKitBackend {

    private MapboxMapsBackend() {}

    @Override public @LayoutRes int getMapFragmentLayoutRes() {
        return R.layout.kits_maps_internal_mapbox_map_view;
    }

    @Override public @IdRes int getMapFragmentIdRes() {
        return R.id.kits_maps_internal_map_fragment;
    }


    @Override public @NonNull BitmapDescriptor.Factory getBitmapDescriptorFactory() {
        return MapboxBitmapDescriptorFactory.INSTANCE;
    }

    @Override public @NonNull ButtCap newButtCap() {
        return NilCap.INSTANCE; // Not supported, null object for API safe.
    }

    @Override public @NonNull CameraUpdate.Factory getCameraUpdateFactory() {
        return MapboxCameraUpdateFactory.INSTANCE;
    }

    @Override public @NonNull CameraPosition newCameraPositionFromLatLngZoom(
            @NonNull LatLng target,
            float zoom
    ) {
        return newCameraPositionBuilder()
                .target(target)
                .zoom(zoom)
                .build();
    }

    @Override public @NonNull CameraPosition.Builder newCameraPositionBuilder() {
        return new MapboxCameraPosition.Builder();
    }

    @Override public @NonNull CameraPosition.Builder newCameraPositionBuilder(
            @NonNull CameraPosition camera
    ) {
        return new MapboxCameraPosition.Builder(camera);
    }

    @Override public @NonNull Circle.Options newCircleOptions() {
        return NilCircle.Options.INSTANCE; // Not supported, null object for API safe.
    }

    @Override public @NonNull CustomCap newCustomCap(
            @NonNull BitmapDescriptor bitmapDescriptor,
            float refWidth
    ) {
        return NilCap.INSTANCE; // Not supported, null object for API safe.
    }

    @Override public @NonNull CustomCap newCustomCap(@NonNull BitmapDescriptor bitmapDescriptor) {
        return NilCap.INSTANCE; // Not supported, null object for API safe.
    }

    @Override public @NonNull Dot newDot() {
        return NilPatternItem.INSTANCE; // Not supported, null object for API safe.
    }

    @Override public @NonNull Dash newDash(float length) {
        return NilPatternItem.INSTANCE; // Not supported, null object for API safe.
    }

    @Override public @NonNull Gap newGap(float length) {
        return NilPatternItem.INSTANCE; // Not supported, null object for API safe.
    }

    @Override public @NonNull GroundOverlay.Options newGroundOverlayOptions() {
        return NilGroundOverlay.Options.INSTANCE; // Not supported, null object for API safe.
    }

    @Override public @NonNull LatLng newLatLng(double latitude, double longitude) {
        return new MapboxLatLng(latitude, longitude);
    }

    @Override public @NonNull LatLngBounds newLatLngBounds(
            @NonNull LatLng southwest,
            @NonNull LatLng northeast
    ) {
        return new MapboxLatLngBounds(southwest, northeast);
    }

    @Override public @NonNull LatLngBounds.Builder newLatLngBoundsBuilder() {
        return new MapboxLatLngBounds.Builder();
    }

    @Override public @NonNull MapClient.Style.Options newMapStyleOptions(String json) {
        return NilMapClient.Style.Options.INSTANCE; // Not supported, null object for API safe.
    }

    @Override public @NonNull MapClient.Style.Options newMapStyleOptions(
            @NonNull Context context,
            int resourceId
    ) {
        return NilMapClient.Style.Options.INSTANCE; // Not supported, null object for API safe.
    }

    @Override public @NonNull Marker.Options newMarkerOptions() {
        return new MapboxMarkerOptions();
    }

    @Override public @NonNull Polygon.Options newPolygonOptions() {
        return NilPolygon.Options.INSTANCE; // Not supported, null object for API safe.
    }

    @Override public @NonNull Polyline.Options newPolylineOptions() {
        return NilPolyline.Options.INSTANCE; // Not supported, null object for API safe.
    }

    @Override public @NonNull RoundCap newRoundCap() {
        return NilCap.INSTANCE; // Not supported, null object for API safe.
    }

    @Override public @NonNull SquareCap newSquareCap() {
        return NilCap.INSTANCE; // Not supported, null object for API safe.
    }

    @Override public @NonNull TileOverlay.Options newTileOverlayOptions() {
        return NilTileOverlay.Options.INSTANCE; // Not supported, null object for API safe.
    }

    @Override public @NonNull Tile newTile(int width, int height, byte[] data) {
        return NilTile.INSTANCE; // Not supported, null object for API safe.
    }

    @Override public @NonNull Tile noTile() {
        return NilTile.INSTANCE; // Not supported, null object for API safe.
    }

    @Override public @NonNull TileProvider newUrlTileProvider(
            int width,
            int height,
            @NonNull UrlTileProvider tileProvider
    ) {
        return NilTileProvider.INSTANCE; // Not supported, null object for API safe.
    }

    @Override public @NonNull VisibleRegion newVisibleRegion(
            @NonNull LatLng nearLeft,
            @NonNull LatLng nearRight,
            @NonNull LatLng farLeft,
            @NonNull LatLng farRight,
            @NonNull LatLngBounds bounds
    ) {
        return NilVisibleRegion.INSTANCE; // Not supported, null object for API safe.
    }

    @Override public void getMapAsync(
            @NonNull Fragment fragment,
            @NonNull MapKit.OnMapReadyCallback callback
    ) {
        final @Nullable View view = fragment.getView();
        if (view != null) {
            callback.onMapReady(new MapboxMapClient((MapView) view));
        }
    }


    @Keep
    public static @NonNull MapKitBackend buildIfSupported(@NonNull Context context) {
        return new MapboxMapsBackend();
    }

}
