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

package dev.supasintatiyanupanwong.libraries.android.kits.maps.internal.huawei.model;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.RawRes;
import androidx.fragment.app.Fragment;

import com.huawei.hms.api.ConnectionResult;
import com.huawei.hms.api.HuaweiApiAvailability;

import java.util.Arrays;
import java.util.List;

import dev.supasintatiyanupanwong.libraries.android.kits.maps.MapKit;
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

@SuppressWarnings("unused")
public class HuaweiMapFactory implements MapFactory {
    private static final List<Integer> UNAVAILABLE_RESULTS = Arrays.asList(
            ConnectionResult.SERVICE_DISABLED,
            ConnectionResult.SERVICE_MISSING,
            ConnectionResult.SERVICE_INVALID);

    private HuaweiMapFactory() {}

    @NonNull
    @Override
    public BitmapDescriptor.Factory getBitmapDescriptorFactory() {
        return HuaweiBitmapDescriptor.FACTORY;
    }

    @NonNull
    @Override
    public ButtCap newButtCap() {
        return new HuaweiButtCap();
    }

    @NonNull
    @Override
    public CameraUpdate.Factory getCameraUpdateFactory() {
        return HuaweiCameraUpdate.FACTORY;
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
        return new HuaweiCameraPosition.Builder();
    }

    @NonNull
    @Override
    public CameraPosition.Builder newCameraPositionBuilder(@NonNull CameraPosition camera) {
        return new HuaweiCameraPosition.Builder(camera);
    }

    @NonNull
    @Override
    public Circle.Options newCircleOptions() {
        return new HuaweiCircle.Options();
    }

    @NonNull
    @Override
    public CustomCap newCustomCap(@NonNull BitmapDescriptor bitmapDescriptor, float refWidth) {
        return new HuaweiCustomCap(bitmapDescriptor, refWidth);
    }

    @NonNull
    @Override
    public CustomCap newCustomCap(@NonNull BitmapDescriptor bitmapDescriptor) {
        return new HuaweiCustomCap(bitmapDescriptor);
    }

    @NonNull
    @Override
    public Dot newDot() {
        return new HuaweiDot();
    }

    @NonNull
    @Override
    public Dash newDash(float length) {
        return new HuaweiDash(length);
    }

    @NonNull
    @Override
    public Gap newGap(float length) {
        return new HuaweiGap(length);
    }

    @NonNull
    @Override
    public GroundOverlay.Options newGroundOverlayOptions() {
        return new HuaweiGroundOverlay.Options();
    }

    @NonNull
    @Override
    public LatLng newLatLng(double latitude, double longitude) {
        return new HuaweiLatLng(latitude, longitude);
    }

    @NonNull
    @Override
    public LatLngBounds newLatLngBounds(@NonNull LatLng southwest, @NonNull LatLng northeast) {
        return new HuaweiLatLngBounds(southwest, northeast);
    }

    @NonNull
    @Override
    public LatLngBounds.Builder newLatLngBoundsBuilder() {
        return new HuaweiLatLngBounds.Builder();
    }

    @NonNull
    @Override
    public MapClient.Style.Options newMapStyleOptions(String json) {
        return new HuaweiMapClient.Style.Options(json);
    }

    @NonNull
    @Override
    public MapClient.Style.Options newMapStyleOptions(
            @NonNull Context context, @RawRes int resourceId) {
        return new HuaweiMapClient.Style.Options(context, resourceId);
    }

    @NonNull
    @Override
    public Marker.Options newMarkerOptions() {
        return new HuaweiMarker.Options();
    }

    @NonNull
    @Override
    public Polygon.Options newPolygonOptions() {
        return new HuaweiPolygon.Options();
    }

    @NonNull
    @Override
    public Polyline.Options newPolylineOptions() {
        return new HuaweiPolyline.Options();
    }

    @NonNull
    @Override
    public RoundCap newRoundCap() {
        return new HuaweiRoundCap();
    }

    @NonNull
    @Override
    public SquareCap newSquareCap() {
        return new HuaweiSquareCap();
    }

    @NonNull
    @Override
    public TileOverlay.Options newTileOverlayOptions() {
        return new HuaweiTileOverlay.Options();
    }

    @NonNull
    @Override
    public Tile newTile(int width, int height, byte[] data) {
        return new HuaweiTile(width, height, data);
    }

    @NonNull
    @Override
    public Tile noTile() {
        return HuaweiTileProvider.NO_TILE;
    }

    @NonNull
    @Override
    public TileProvider newUrlTileProvider(
            int width, int height, @NonNull UrlTileProvider tileProvider) {
        return new HuaweiUrlTileProvider(width, height, tileProvider);
    }

    @NonNull
    @Override
    public VisibleRegion newVisibleRegion(
            LatLng nearLeft,
            LatLng nearRight,
            LatLng farLeft,
            LatLng farRight,
            LatLngBounds latLngBounds) {
        return new HuaweiVisibleRegion(nearLeft, nearRight, farLeft, farRight, latLngBounds);
    }

    @Override
    public void getMapAsync(
            @NonNull Fragment fragment,
            @NonNull final MapKit.OnMapReadyCallback callback) {
        ((com.huawei.hms.maps.SupportMapFragment) fragment)
                .getMapAsync(new com.huawei.hms.maps.OnMapReadyCallback() {
                    @Override
                    public void onMapReady(com.huawei.hms.maps.HuaweiMap huaweiMap) {
                        callback.onMapReady(new HuaweiMapClient(huaweiMap));
                    }
                });
    }


    @NonNull
    public static MapFactory buildIfSupported(@NonNull Context context) {
        final int result =
                HuaweiApiAvailability.getInstance().isHuaweiMobileServicesAvailable(context);
        if (UNAVAILABLE_RESULTS.contains(result)) {
            throw new UnsupportedOperationException("Huawei Maps is not available.");
        }

        return new HuaweiMapFactory();
    }

}
