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

package me.tatiyanupanwong.supasin.android.libraries.kits.maps.internal.google.model;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.RawRes;
import androidx.fragment.app.Fragment;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import java.util.Arrays;
import java.util.List;

import me.tatiyanupanwong.supasin.android.libraries.kits.maps.MapKit;
import me.tatiyanupanwong.supasin.android.libraries.kits.maps.model.BitmapDescriptor;
import me.tatiyanupanwong.supasin.android.libraries.kits.maps.model.ButtCap;
import me.tatiyanupanwong.supasin.android.libraries.kits.maps.model.CameraPosition;
import me.tatiyanupanwong.supasin.android.libraries.kits.maps.model.CameraUpdate;
import me.tatiyanupanwong.supasin.android.libraries.kits.maps.model.Circle;
import me.tatiyanupanwong.supasin.android.libraries.kits.maps.model.CustomCap;
import me.tatiyanupanwong.supasin.android.libraries.kits.maps.model.Dash;
import me.tatiyanupanwong.supasin.android.libraries.kits.maps.model.Dot;
import me.tatiyanupanwong.supasin.android.libraries.kits.maps.model.Gap;
import me.tatiyanupanwong.supasin.android.libraries.kits.maps.model.GroundOverlay;
import me.tatiyanupanwong.supasin.android.libraries.kits.maps.model.LatLng;
import me.tatiyanupanwong.supasin.android.libraries.kits.maps.model.LatLngBounds;
import me.tatiyanupanwong.supasin.android.libraries.kits.maps.model.MapClient;
import me.tatiyanupanwong.supasin.android.libraries.kits.maps.model.MapFactory;
import me.tatiyanupanwong.supasin.android.libraries.kits.maps.model.Marker;
import me.tatiyanupanwong.supasin.android.libraries.kits.maps.model.Polygon;
import me.tatiyanupanwong.supasin.android.libraries.kits.maps.model.Polyline;
import me.tatiyanupanwong.supasin.android.libraries.kits.maps.model.RoundCap;
import me.tatiyanupanwong.supasin.android.libraries.kits.maps.model.SquareCap;
import me.tatiyanupanwong.supasin.android.libraries.kits.maps.model.Tile;
import me.tatiyanupanwong.supasin.android.libraries.kits.maps.model.TileOverlay;
import me.tatiyanupanwong.supasin.android.libraries.kits.maps.model.TileProvider;
import me.tatiyanupanwong.supasin.android.libraries.kits.maps.model.UrlTileProvider;
import me.tatiyanupanwong.supasin.android.libraries.kits.maps.model.VisibleRegion;

@SuppressWarnings("unused")
class GoogleMapFactory implements MapFactory {

    private static final List<Integer> UNAVAILABLE_RESULTS = Arrays.asList(
            ConnectionResult.SERVICE_DISABLED,
            ConnectionResult.SERVICE_MISSING,
            ConnectionResult.SERVICE_INVALID);

    private GoogleMapFactory() {}

    @NonNull
    @Override
    public BitmapDescriptor.Factory getBitmapDescriptorFactory() {
        return GoogleBitmapDescriptor.FACTORY;
    }

    @NonNull
    @Override
    public ButtCap newButtCap() {
        return new GoogleButtCap();
    }

    @NonNull
    @Override
    public CameraUpdate.Factory getCameraUpdateFactory() {
        return GoogleCameraUpdate.FACTORY;
    }

    @NonNull
    @Override
    public CameraPosition newCameraPositionFromLatLngZoom(@NonNull LatLng target, float zoom) {
        return newCameraPositionBuilder()
                .target(target)
                .zoom(zoom)
                .build();
    }

    @NonNull
    @Override
    public CameraPosition.Builder newCameraPositionBuilder() {
        return new GoogleCameraPosition.Builder();
    }

    @NonNull
    @Override
    public CameraPosition.Builder newCameraPositionBuilder(@NonNull CameraPosition camera) {
        return new GoogleCameraPosition.Builder(camera);
    }

    @NonNull
    @Override
    public Circle.Options newCircleOptions() {
        return new GoogleCircle.Options();
    }

    @NonNull
    @Override
    public CustomCap newCustomCap(@NonNull BitmapDescriptor bitmapDescriptor, float refWidth) {
        return new GoogleCustomCap(bitmapDescriptor, refWidth);
    }

    @NonNull
    @Override
    public CustomCap newCustomCap(@NonNull BitmapDescriptor bitmapDescriptor) {
        return new GoogleCustomCap(bitmapDescriptor);
    }

    @NonNull
    @Override
    public Dot newDot() {
        return new GoogleDot();
    }

    @NonNull
    @Override
    public Dash newDash(float length) {
        return new GoogleDash(length);
    }

    @NonNull
    @Override
    public Gap newGap(float length) {
        return new GoogleGap(length);
    }

    @NonNull
    @Override
    public GroundOverlay.Options newGroundOverlayOptions() {
        return new GoogleGroundOverlay.Options();
    }

    @NonNull
    @Override
    public LatLng newLatLng(double latitude, double longitude) {
        return new GoogleLatLng(latitude, longitude);
    }

    @NonNull
    @Override
    public LatLngBounds newLatLngBounds(@NonNull LatLng southwest, @NonNull LatLng northeast) {
        return new GoogleLatLngBounds(southwest, northeast);
    }

    @NonNull
    @Override
    public LatLngBounds.Builder newLatLngBoundsBuilder() {
        return new GoogleLatLngBounds.Builder();
    }

    @NonNull
    @Override
    public MapClient.Style.Options newMapStyleOptions(String json) {
        return new GoogleMapClient.Style.Options(json);
    }

    @NonNull
    @Override
    public MapClient.Style.Options newMapStyleOptions(
            @NonNull Context context, @RawRes int resourceId) {
        return new GoogleMapClient.Style.Options(context, resourceId);
    }

    @NonNull
    @Override
    public Marker.Options newMarkerOptions() {
        return new GoogleMarker.Options();
    }

    @NonNull
    @Override
    public Polygon.Options newPolygonOptions() {
        return new GooglePolygon.Options();
    }

    @NonNull
    @Override
    public Polyline.Options newPolylineOptions() {
        return new GooglePolyline.Options();
    }

    @NonNull
    @Override
    public RoundCap newRoundCap() {
        return new GoogleRoundCap();
    }

    @NonNull
    @Override
    public SquareCap newSquareCap() {
        return new GoogleSquareCap();
    }

    @NonNull
    @Override
    public TileOverlay.Options newTileOverlayOptions() {
        return new GoogleTileOverlay.Options();
    }

    @NonNull
    @Override
    public Tile newTile(int width, int height, byte[] data) {
        return new GoogleTile(width, height, data);
    }

    @NonNull
    @Override
    public Tile noTile() {
        return GoogleTileProvider.NO_TILE;
    }

    @NonNull
    @Override
    public TileProvider newUrlTileProvider(
            int width, int height, @NonNull UrlTileProvider tileProvider) {
        return new GoogleUrlTileProvider(width, height, tileProvider);
    }

    @NonNull
    @Override
    public VisibleRegion newVisibleRegion(
            LatLng nearLeft,
            LatLng nearRight,
            LatLng farLeft,
            LatLng farRight,
            LatLngBounds latLngBounds) {
        return new GoogleVisibleRegion(nearLeft, nearRight, farLeft, farRight, latLngBounds);
    }


    /**
     * @see #getMapAsync(Fragment, MapKit.OnMapReadyCallback)
     * @deprecated As of 1.2.0, use {@link #getMapAsync(Fragment, MapKit.OnMapReadyCallback)}
     * instead.
     */
    @SuppressWarnings("deprecation")
    @Deprecated
    @Override
    public void getMapAsync(
            @NonNull Fragment fragment,
            @NonNull final OnMapReadyCallback callback) {
        ((com.google.android.gms.maps.SupportMapFragment) fragment)
                .getMapAsync(new com.google.android.gms.maps.OnMapReadyCallback() {
                    @Override
                    public void onMapReady(com.google.android.gms.maps.GoogleMap googleMap) {
                        callback.onMapReady(new GoogleMapClient(googleMap));
                    }
                });
    }

    @Override
    public void getMapAsync(
            @NonNull Fragment fragment,
            @NonNull final MapKit.OnMapReadyCallback callback) {
        ((com.google.android.gms.maps.SupportMapFragment) fragment)
                .getMapAsync(new com.google.android.gms.maps.OnMapReadyCallback() {
                    @Override
                    public void onMapReady(com.google.android.gms.maps.GoogleMap googleMap) {
                        callback.onMapReady(new GoogleMapClient(googleMap));
                    }
                });
    }


    @NonNull
    public static MapFactory buildIfSupported(@NonNull Context context) {
        final int result =
                GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(context);
        if (UNAVAILABLE_RESULTS.contains(result)) {
            throw new UnsupportedOperationException("Google Maps is not available.");
        }

        return new GoogleMapFactory();
    }

}
