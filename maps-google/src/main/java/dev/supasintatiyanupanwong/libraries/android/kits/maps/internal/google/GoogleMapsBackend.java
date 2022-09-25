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

package dev.supasintatiyanupanwong.libraries.android.kits.maps.internal.google;

import android.content.Context;

import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RawRes;
import androidx.fragment.app.Fragment;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import java.util.Arrays;
import java.util.List;

import dev.supasintatiyanupanwong.libraries.android.kits.maps.MapKit;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.MapKitBackend;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.internal.google.model.GoogleBitmapDescriptor;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.internal.google.model.GoogleButtCap;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.internal.google.model.GoogleCameraPosition;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.internal.google.model.GoogleCameraUpdate;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.internal.google.model.GoogleCircle;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.internal.google.model.GoogleCustomCap;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.internal.google.model.GoogleDash;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.internal.google.model.GoogleDot;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.internal.google.model.GoogleGap;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.internal.google.model.GoogleGroundOverlay;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.internal.google.model.GoogleLatLng;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.internal.google.model.GoogleLatLngBounds;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.internal.google.model.GoogleMapClient;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.internal.google.model.GoogleMarker;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.internal.google.model.GooglePolygon;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.internal.google.model.GooglePolyline;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.internal.google.model.GoogleRoundCap;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.internal.google.model.GoogleSquareCap;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.internal.google.model.GoogleTile;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.internal.google.model.GoogleTileOverlay;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.internal.google.model.GoogleTileProvider;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.internal.google.model.GoogleUrlTileProvider;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.internal.google.model.GoogleVisibleRegion;
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
class GoogleMapsBackend implements MapKitBackend {

    private static final List<Integer> UNAVAILABLE_RESULTS = Arrays.asList(
            ConnectionResult.SERVICE_DISABLED,
            ConnectionResult.SERVICE_MISSING,
            ConnectionResult.SERVICE_INVALID);

    private GoogleMapsBackend() {}

    @Override public @LayoutRes int getMapFragmentLayoutRes() {
        return R.layout.kits_maps_internal_google_map_view;
    }

    @Override public @IdRes int getMapFragmentIdRes() {
        return R.id.kits_maps_internal_map_fragment;
    }


    @Override public @NonNull BitmapDescriptor.Factory getBitmapDescriptorFactory() {
        return GoogleBitmapDescriptor.FACTORY;
    }

    @Override public @NonNull ButtCap newButtCap() {
        return new GoogleButtCap();
    }

    @Override public @NonNull CameraUpdate.Factory getCameraUpdateFactory() {
        return GoogleCameraUpdate.FACTORY;
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
        return new GoogleCameraPosition.Builder();
    }

    @Override public @NonNull CameraPosition.Builder newCameraPositionBuilder(
            @NonNull CameraPosition camera) {
        return new GoogleCameraPosition.Builder(camera);
    }

    @Override public @NonNull Circle.Options newCircleOptions() {
        return new GoogleCircle.Options();
    }

    @Override public @NonNull CustomCap newCustomCap(
            @NonNull BitmapDescriptor bitmapDescriptor,
            float refWidth) {
        return new GoogleCustomCap(bitmapDescriptor, refWidth);
    }

    @Override public @NonNull CustomCap newCustomCap(@NonNull BitmapDescriptor bitmapDescriptor) {
        return new GoogleCustomCap(bitmapDescriptor);
    }

    @Override public @NonNull Dot newDot() {
        return new GoogleDot();
    }

    @Override public @NonNull Dash newDash(float length) {
        return new GoogleDash(length);
    }

    @Override public @NonNull Gap newGap(float length) {
        return new GoogleGap(length);
    }

    @Override public @NonNull GroundOverlay.Options newGroundOverlayOptions() {
        return new GoogleGroundOverlay.Options();
    }

    @Override public @NonNull LatLng newLatLng(double latitude, double longitude) {
        return new GoogleLatLng(latitude, longitude);
    }

    @Override public @NonNull LatLngBounds newLatLngBounds(
            @NonNull LatLng southwest,
            @NonNull LatLng northeast) {
        return new GoogleLatLngBounds(southwest, northeast);
    }

    @Override public @NonNull LatLngBounds.Builder newLatLngBoundsBuilder() {
        return new GoogleLatLngBounds.Builder();
    }

    @Override public @NonNull MapClient.Style.Options newMapStyleOptions(String json) {
        return new GoogleMapClient.Style.Options(json);
    }

    @Override public @NonNull MapClient.Style.Options newMapStyleOptions(
            @NonNull Context context, @RawRes int resourceId) {
        return new GoogleMapClient.Style.Options(context, resourceId);
    }

    @Override public @NonNull Marker.Options newMarkerOptions() {
        return new GoogleMarker.Options();
    }

    @Override public @NonNull Polygon.Options newPolygonOptions() {
        return new GooglePolygon.Options();
    }

    @Override public @NonNull Polyline.Options newPolylineOptions() {
        return new GooglePolyline.Options();
    }

    @Override public @NonNull RoundCap newRoundCap() {
        return new GoogleRoundCap();
    }

    @Override public @NonNull SquareCap newSquareCap() {
        return new GoogleSquareCap();
    }

    @Override public @NonNull TileOverlay.Options newTileOverlayOptions() {
        return new GoogleTileOverlay.Options();
    }

    @Override public @NonNull Tile newTile(int width, int height, byte[] data) {
        return new GoogleTile(width, height, data);
    }

    @Override public @NonNull Tile noTile() {
        return GoogleTileProvider.NO_TILE;
    }

    @Override public @NonNull TileProvider newUrlTileProvider(
            int width,
            int height,
            @NonNull UrlTileProvider tileProvider) {
        return new GoogleUrlTileProvider(width, height, tileProvider);
    }

    @Override public @NonNull VisibleRegion newVisibleRegion(
            @NonNull LatLng nearLeft,
            @NonNull LatLng nearRight,
            @NonNull LatLng farLeft,
            @NonNull LatLng farRight,
            @NonNull LatLngBounds bounds) {
        return new GoogleVisibleRegion(nearLeft, nearRight, farLeft, farRight, bounds);
    }

    @Override public void getMapAsync(
            @NonNull Fragment fragment,
            @NonNull final MapKit.OnMapReadyCallback callback) {
        ((com.google.android.gms.maps.SupportMapFragment) fragment).getMapAsync(
                new com.google.android.gms.maps.OnMapReadyCallback() {
                    @Override public void onMapReady(
                            @NonNull com.google.android.gms.maps.GoogleMap googleMap) {
                        callback.onMapReady(new GoogleMapClient(googleMap));
                    }
                });
    }


    public static @Nullable MapKitBackend buildIfSupported(@NonNull Context context) {
        final int result =
                GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(context);
        if (UNAVAILABLE_RESULTS.contains(result)) {
            return null;
        }

        return new GoogleMapsBackend();
    }

}
