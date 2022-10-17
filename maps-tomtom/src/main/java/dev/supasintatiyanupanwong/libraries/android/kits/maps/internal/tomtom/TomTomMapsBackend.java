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

package dev.supasintatiyanupanwong.libraries.android.kits.maps.internal.tomtom;

import android.content.Context;

import androidx.annotation.IdRes;
import androidx.annotation.Keep;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.tomtom.sdk.maps.display.TomTomMap;
import com.tomtom.sdk.maps.display.ui.MapView;
import com.tomtom.sdk.maps.display.ui.OnMapReadyCallback;

import dev.supasintatiyanupanwong.libraries.android.kits.maps.MapKit;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.MapKitBackend;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.internal.nop.model.NopGroundOverlay;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.internal.nop.model.NopMapClient;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.internal.nop.model.NopPatternItem;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.internal.nop.model.NopTile;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.internal.nop.model.NopTileOverlay;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.internal.nop.model.NopTileProvider;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.internal.tomtom.model.TomTomBitmapDescriptor;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.internal.tomtom.model.TomTomButtCap;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.internal.tomtom.model.TomTomCameraPosition;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.internal.tomtom.model.TomTomCameraUpdate;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.internal.tomtom.model.TomTomCircle;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.internal.tomtom.model.TomTomLatLng;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.internal.tomtom.model.TomTomLatLngBounds;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.internal.tomtom.model.TomTomMapClient;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.internal.tomtom.model.TomTomMarker;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.internal.tomtom.model.TomTomPolygon;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.internal.tomtom.model.TomTomPolyline;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.internal.tomtom.model.TomTomRoundCap;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.internal.tomtom.model.TomTomSquareCap;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.internal.tomtom.model.TomTomVisibleRegion;
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
class TomTomMapsBackend implements MapKitBackend {

    private TomTomMapsBackend() {}

    @Override public @LayoutRes int getMapFragmentLayoutRes() {
        return R.layout.kits_maps_internal_tomtom_map_view;
    }

    @Override public @IdRes int getMapFragmentIdRes() {
        return R.id.kits_maps_internal_map_fragment;
    }


    @Override public @NonNull BitmapDescriptor.Factory getBitmapDescriptorFactory() {
        return TomTomBitmapDescriptor.FACTORY;
    }

    @Override public @NonNull ButtCap newButtCap() {
        return TomTomButtCap.INSTANCE;
    }

    @Override public @NonNull CameraUpdate.Factory getCameraUpdateFactory() {
        return TomTomCameraUpdate.FACTORY;
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
        return new TomTomCameraPosition.Builder();
    }

    @Override public @NonNull CameraPosition.Builder newCameraPositionBuilder(
            @NonNull CameraPosition camera
    ) {
        return new TomTomCameraPosition.Builder(camera);
    }

    @Override public @NonNull Circle.Options newCircleOptions() {
        return new TomTomCircle.Options();
    }

    @Override public @NonNull CustomCap newCustomCap(
            @NonNull BitmapDescriptor bitmapDescriptor,
            float refWidth
    ) {
        return TomTomButtCap.INSTANCE;
    }

    @Override public @NonNull CustomCap newCustomCap(@NonNull BitmapDescriptor bitmapDescriptor) {
        return TomTomButtCap.INSTANCE;
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
        return new TomTomLatLng(latitude, longitude);
    }

    @Override public @NonNull LatLngBounds newLatLngBounds(
            @NonNull LatLng southwest,
            @NonNull LatLng northeast
    ) {
        return new TomTomLatLngBounds(southwest, northeast);
    }

    @Override public @NonNull LatLngBounds.Builder newLatLngBoundsBuilder() {
        return new TomTomLatLngBounds.Builder();
    }

    @Override public @NonNull MapClient.Style.Options newMapStyleOptions(String json) {
        return NopMapClient.Style.Options.NULL; // TODO
    }

    @Override public @NonNull MapClient.Style.Options newMapStyleOptions(
            @NonNull Context context,
            int resourceId
    ) {
        return NopMapClient.Style.Options.NULL; // TODO
    }

    @Override public @NonNull Marker.Options newMarkerOptions() {
        return new TomTomMarker.Options();
    }

    @Override public @NonNull Polygon.Options newPolygonOptions() {
        return new TomTomPolygon.Options();
    }

    @Override public @NonNull Polyline.Options newPolylineOptions() {
        return new TomTomPolyline.Options();
    }

    @Override public @NonNull RoundCap newRoundCap() {
        return TomTomRoundCap.INSTANCE;
    }

    @Override public @NonNull SquareCap newSquareCap() {
        return TomTomSquareCap.INSTANCE;
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
        return new TomTomVisibleRegion(nearLeft, nearRight, farLeft, farRight, bounds);
    }

    @Override public void getMapAsync(
            @NonNull Fragment fragment,
            @NonNull MapKit.OnMapReadyCallback callback
    ) {
        final @NonNull MapView mapView = ((MapView) fragment.requireView());
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override public void onMapReady(@NonNull TomTomMap map) {
                callback.onMapReady(new TomTomMapClient(mapView, map));
            }
        });
    }


    @Keep
    public static @Nullable MapKitBackend buildIfSupported(@NonNull Context context) {
        try {
            Class.forName("com.tomtom.sdk.maps.display.TomTomMap");

            return new TomTomMapsBackend();
        } catch (ClassNotFoundException ignored) {
            return null;
        }
    }

}
