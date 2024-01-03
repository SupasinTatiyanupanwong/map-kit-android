/*
 * Copyright 2021 Supasin Tatiyanupanwong
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

package dev.supasintatiyanupanwong.libraries.android.kits.maps.internal.amazon;

import android.content.Context;

import androidx.annotation.IdRes;
import androidx.annotation.Keep;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RawRes;
import androidx.fragment.app.Fragment;

import com.amazon.geo.mapsv2.util.AmazonMapsRuntimeUtil;
import com.amazon.geo.mapsv2.util.ConnectionResult;

import dev.supasintatiyanupanwong.libraries.android.kits.maps.MapKit;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.MapKitBackend;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.internal.amazon.model.AmazonBitmapDescriptor;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.internal.amazon.model.AmazonCameraPosition;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.internal.amazon.model.AmazonCameraUpdate;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.internal.amazon.model.AmazonCircle;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.internal.amazon.model.AmazonGroundOverlay;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.internal.amazon.model.AmazonLatLng;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.internal.amazon.model.AmazonLatLngBounds;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.internal.amazon.model.AmazonMapClient;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.internal.amazon.model.AmazonMarker;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.internal.amazon.model.AmazonPolygon;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.internal.amazon.model.AmazonPolyline;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.internal.amazon.model.AmazonTile;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.internal.amazon.model.AmazonTileOverlay;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.internal.amazon.model.AmazonVisibleRegion;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.internal.nil.NilMapsBackend;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.BitmapDescriptor;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.CameraPosition;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.CameraUpdate;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.Circle;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.GroundOverlay;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.LatLng;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.LatLngBounds;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.MapClient;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.Marker;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.Polygon;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.Polyline;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.Tile;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.TileOverlay;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.VisibleRegion;

@SuppressWarnings("unused")
@Keep
class AmazonMapsBackend extends NilMapsBackend {

    private AmazonMapsBackend() {}

    @Override public @LayoutRes int getMapFragmentLayoutRes() {
        return R.layout.kits_maps_internal_amazon_map_view;
    }

    @Override public @IdRes int getMapFragmentIdRes() {
        return R.id.kits_maps_internal_map_fragment;
    }


    @Override public @NonNull BitmapDescriptor.Factory getBitmapDescriptorFactory() {
        return AmazonBitmapDescriptor.FACTORY;
    }

    @Override public @NonNull CameraUpdate.Factory getCameraUpdateFactory() {
        return AmazonCameraUpdate.FACTORY;
    }

    @Override public @NonNull CameraPosition newCameraPositionFromLatLngZoom(
            @NonNull LatLng target,
            float zoom) {
        return newCameraPositionBuilder()
                .target(target)
                .zoom(zoom)
                .build();
    }

    @Override public @NonNull CameraPosition.Builder newCameraPositionBuilder() {
        return new AmazonCameraPosition.Builder();
    }

    @Override public @NonNull CameraPosition.Builder newCameraPositionBuilder(
            @NonNull CameraPosition camera) {
        return new AmazonCameraPosition.Builder(camera);
    }

    @Override public @NonNull Circle.Options newCircleOptions() {
        return new AmazonCircle.Options();
    }

    @Override public @NonNull GroundOverlay.Options newGroundOverlayOptions() {
        return new AmazonGroundOverlay.Options();
    }

    @Override public @NonNull LatLng newLatLng(double latitude, double longitude) {
        return new AmazonLatLng(latitude, longitude);
    }

    @Override public @NonNull LatLngBounds newLatLngBounds(
            @NonNull LatLng southwest,
            @NonNull LatLng northeast) {
        return new AmazonLatLngBounds(southwest, northeast);
    }

    @Override public @NonNull LatLngBounds.Builder newLatLngBoundsBuilder() {
        return new AmazonLatLngBounds.Builder();
    }

    @Override public @NonNull MapClient.Style.Options newMapStyleOptions(String json) {
        return AmazonMapClient.Style.Options.NULL; // Not supported, null object for API safe.
    }

    @Override public @NonNull MapClient.Style.Options newMapStyleOptions(
            @NonNull Context context, @RawRes int resourceId) {
        return AmazonMapClient.Style.Options.NULL; // Not supported, null object for API safe.
    }

    @Override public @NonNull Marker.Options newMarkerOptions() {
        return new AmazonMarker.Options();
    }

    @Override public @NonNull Polygon.Options newPolygonOptions() {
        return new AmazonPolygon.Options();
    }

    @Override public @NonNull Polyline.Options newPolylineOptions() {
        return new AmazonPolyline.Options();
    }

    @Override public @NonNull TileOverlay.Options newTileOverlayOptions() {
        return new AmazonTileOverlay.Options();
    }

    @Override public @NonNull Tile newTile(int width, int height, byte[] data) {
        return new AmazonTile(width, height, data);
    }

    @Override public @NonNull VisibleRegion newVisibleRegion(
            @NonNull LatLng nearLeft,
            @NonNull LatLng nearRight,
            @NonNull LatLng farLeft,
            @NonNull LatLng farRight,
            @NonNull LatLngBounds bounds) {
        return new AmazonVisibleRegion(nearLeft, nearRight, farLeft, farRight, bounds);
    }

    @Override public void getMapAsync(
            @NonNull Fragment fragment,
            @NonNull final MapKit.OnMapReadyCallback callback) {
        ((com.amazon.geo.mapsv2.SupportMapFragment) fragment)
                .getMapAsync(new com.amazon.geo.mapsv2.OnMapReadyCallback() {
                    @Override public void onMapReady(
                            @NonNull com.amazon.geo.mapsv2.AmazonMap amazonMap) {
                        callback.onMapReady(new AmazonMapClient(amazonMap));
                    }
                });
    }


    @Keep
    public static @Nullable MapKitBackend buildIfSupported(@NonNull Context context) {
        final int result = AmazonMapsRuntimeUtil.isAmazonMapsRuntimeAvailable(context);
        if (result != ConnectionResult.SUCCESS) {
            return null;
        }

        return new AmazonMapsBackend();
    }
}
