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

import androidx.annotation.IdRes;
import androidx.annotation.Keep;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import dev.supasintatiyanupanwong.libraries.android.kits.maps.MapKit;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.MapKitBackend;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.internal.nop.model.NopBitmapDescriptor;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.internal.nop.model.NopCameraPosition;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.internal.nop.model.NopCameraUpdate;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.internal.nop.model.NopCap;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.internal.nop.model.NopCircle;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.internal.nop.model.NopGroundOverlay;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.internal.nop.model.NopLatLng;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.internal.nop.model.NopLatLngBounds;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.internal.nop.model.NopMapClient;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.internal.nop.model.NopMarker;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.internal.nop.model.NopPatternItem;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.internal.nop.model.NopPolygon;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.internal.nop.model.NopPolyline;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.internal.nop.model.NopTile;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.internal.nop.model.NopTileOverlay;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.internal.nop.model.NopTileProvider;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.internal.nop.model.NopVisibleRegion;
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
        return NopBitmapDescriptor.FACTORY;
    }

    @Override public @NonNull ButtCap newButtCap() {
        return NopCap.NULL; // Not supported, null object for API safe.
    }

    @Override public @NonNull CameraUpdate.Factory getCameraUpdateFactory() {
        return NopCameraUpdate.FACTORY;
    }

    @Override public @NonNull CameraPosition newCameraPositionFromLatLngZoom(
            @NonNull LatLng target,
            float zoom) {
        return NopCameraPosition.NULL; // Not supported, null object for API safe.
    }

    @Override public @NonNull CameraPosition.Builder newCameraPositionBuilder() {
        return NopCameraPosition.Builder.NULL; // Not supported, null object for API safe.
    }

    @Override public @NonNull CameraPosition.Builder newCameraPositionBuilder(
            @NonNull CameraPosition camera) {
        return NopCameraPosition.Builder.NULL; // Not supported, null object for API safe.
    }

    @Override public @NonNull Circle.Options newCircleOptions() {
        return NopCircle.Options.NULL; // Not supported, null object for API safe.
    }

    @Override public @NonNull CustomCap newCustomCap(
            @NonNull BitmapDescriptor bitmapDescriptor,
            float refWidth) {
        return NopCap.NULL; // Not supported, null object for API safe.
    }

    @Override public @NonNull CustomCap newCustomCap(@NonNull BitmapDescriptor bitmapDescriptor) {
        return NopCap.NULL; // Not supported, null object for API safe.
    }

    @Override public @NonNull Dot newDot() {
        return NopPatternItem.NULL; // Not supported, null object for API safe.
    }

    @Override public @NonNull Dash newDash(float length) {
        return NopPatternItem.NULL; // Not supported, null object for API safe.
    }

    @Override public @NonNull Gap newGap(float length) {
        return NopPatternItem.NULL; // Not supported, null object for API safe.
    }

    @Override public @NonNull GroundOverlay.Options newGroundOverlayOptions() {
        return NopGroundOverlay.Options.NULL; // Not supported, null object for API safe.
    }

    @Override public @NonNull LatLng newLatLng(double latitude, double longitude) {
        return NopLatLng.NULL; // Not supported, null object for API safe.
    }

    @Override public @NonNull LatLngBounds newLatLngBounds(
            @NonNull LatLng southwest,
            @NonNull LatLng northeast) {
        return NopLatLngBounds.NULL; // Not supported, null object for API safe.
    }

    @Override public @NonNull LatLngBounds.Builder newLatLngBoundsBuilder() {
        return NopLatLngBounds.Builder.NULL; // Not supported, null object for API safe.
    }

    @Override public @NonNull MapClient.Style.Options newMapStyleOptions(String json) {
        return NopMapClient.Style.Options.NULL; // Not supported, null object for API safe.
    }

    @Override public @NonNull MapClient.Style.Options newMapStyleOptions(
            @NonNull Context context,
            int resourceId) {
        return NopMapClient.Style.Options.NULL; // Not supported, null object for API safe.
    }

    @Override public @NonNull Marker.Options newMarkerOptions() {
        return NopMarker.Options.NULL; // Not supported, null object for API safe.
    }

    @Override public @NonNull Polygon.Options newPolygonOptions() {
        return NopPolygon.Options.NULL; // Not supported, null object for API safe.
    }

    @Override public @NonNull Polyline.Options newPolylineOptions() {
        return NopPolyline.Options.NULL; // Not supported, null object for API safe.
    }

    @Override public @NonNull RoundCap newRoundCap() {
        return NopCap.NULL; // Not supported, null object for API safe.
    }

    @Override public @NonNull SquareCap newSquareCap() {
        return NopCap.NULL; // Not supported, null object for API safe.
    }

    @Override public @NonNull TileOverlay.Options newTileOverlayOptions() {
        return NopTileOverlay.Options.NULL; // Not supported, null object for API safe.
    }

    @Override public @NonNull Tile newTile(int width, int height, byte[] data) {
        return NopTile.NULL; // Not supported, null object for API safe.
    }

    @Override public @NonNull Tile noTile() {
        return NopTile.NULL; // Not supported, null object for API safe.
    }

    @Override public @NonNull TileProvider newUrlTileProvider(
            int width,
            int height,
            @NonNull UrlTileProvider tileProvider) {
        return NopTileProvider.NULL; // Not supported, null object for API safe.
    }

    @Override public @NonNull VisibleRegion newVisibleRegion(
            @NonNull LatLng nearLeft,
            @NonNull LatLng nearRight,
            @NonNull LatLng farLeft,
            @NonNull LatLng farRight,
            @NonNull LatLngBounds bounds) {
        return NopVisibleRegion.NULL; // Not supported, null object for API safe.
    }

    @Override public void getMapAsync(
            @NonNull Fragment fragment,
            @NonNull MapKit.OnMapReadyCallback callback) {
        // Not supported, no-op.
    }


    @Keep
    public static @NonNull MapKitBackend buildIfSupported(@NonNull Context context) {
        return new MapboxMapsBackend();
    }

}
