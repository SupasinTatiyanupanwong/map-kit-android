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
import androidx.annotation.Nullable;
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

    @Override
    public @NonNull BitmapDescriptor.Factory getBitmapDescriptorFactory() {
        return HuaweiBitmapDescriptor.FACTORY;
    }

    @Override
    public @NonNull ButtCap newButtCap() {
        return new HuaweiButtCap();
    }

    @Override
    public @NonNull CameraUpdate.Factory getCameraUpdateFactory() {
        return HuaweiCameraUpdate.FACTORY;
    }

    @Override
    public @NonNull CameraPosition newCameraPositionFromLatLngZoom(
            @NonNull LatLng target,
            float zoom) {
        return newCameraPositionBuilder()
                .target(target)
                .zoom(zoom)
                .build();
    }

    @Override
    public @NonNull CameraPosition.Builder newCameraPositionBuilder() {
        return new HuaweiCameraPosition.Builder();
    }

    @Override
    public @NonNull CameraPosition.Builder newCameraPositionBuilder(
            @NonNull CameraPosition camera) {
        return new HuaweiCameraPosition.Builder(camera);
    }

    @Override
    public @NonNull Circle.Options newCircleOptions() {
        return new HuaweiCircle.Options();
    }

    @Override
    public @NonNull CustomCap newCustomCap(
            @NonNull BitmapDescriptor bitmapDescriptor,
            float refWidth) {
        return new HuaweiCustomCap(bitmapDescriptor, refWidth);
    }

    @Override
    public @NonNull CustomCap newCustomCap(@NonNull BitmapDescriptor bitmapDescriptor) {
        return new HuaweiCustomCap(bitmapDescriptor);
    }

    @Override
    public @NonNull Dot newDot() {
        return new HuaweiDot();
    }

    @Override
    public @NonNull Dash newDash(float length) {
        return new HuaweiDash(length);
    }

    @Override
    public @NonNull Gap newGap(float length) {
        return new HuaweiGap(length);
    }

    @Override
    public @NonNull GroundOverlay.Options newGroundOverlayOptions() {
        return new HuaweiGroundOverlay.Options();
    }

    @Override
    public @NonNull LatLng newLatLng(double latitude, double longitude) {
        return new HuaweiLatLng(latitude, longitude);
    }

    @Override
    public @NonNull LatLngBounds newLatLngBounds(
            @NonNull LatLng southwest,
            @NonNull LatLng northeast) {
        return new HuaweiLatLngBounds(southwest, northeast);
    }

    @Override
    public @NonNull LatLngBounds.Builder newLatLngBoundsBuilder() {
        return new HuaweiLatLngBounds.Builder();
    }

    @Override
    public @NonNull MapClient.Style.Options newMapStyleOptions(String json) {
        return new HuaweiMapClient.Style.Options(json);
    }

    @Override
    public @NonNull MapClient.Style.Options newMapStyleOptions(
            @NonNull Context context,
            @RawRes int resourceId) {
        return new HuaweiMapClient.Style.Options(context, resourceId);
    }

    @Override
    public @NonNull Marker.Options newMarkerOptions() {
        return new HuaweiMarker.Options();
    }

    @Override
    public @NonNull Polygon.Options newPolygonOptions() {
        return new HuaweiPolygon.Options();
    }

    @Override
    public @NonNull Polyline.Options newPolylineOptions() {
        return new HuaweiPolyline.Options();
    }

    @Override
    public @NonNull RoundCap newRoundCap() {
        return new HuaweiRoundCap();
    }

    @Override
    public @NonNull SquareCap newSquareCap() {
        return new HuaweiSquareCap();
    }

    @Override
    public @NonNull TileOverlay.Options newTileOverlayOptions() {
        return new HuaweiTileOverlay.Options();
    }

    @Override
    public @NonNull Tile newTile(int width, int height, byte[] data) {
        return new HuaweiTile(width, height, data);
    }

    @Override
    public @NonNull Tile noTile() {
        return HuaweiTileProvider.NO_TILE;
    }

    @Override
    public @NonNull TileProvider newUrlTileProvider(
            int width,
            int height,
            @NonNull UrlTileProvider tileProvider) {
        return new HuaweiUrlTileProvider(width, height, tileProvider);
    }

    @Override
    public @NonNull VisibleRegion newVisibleRegion(
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


    public static @Nullable MapFactory buildIfSupported(@NonNull Context context) {
        final int result =
                HuaweiApiAvailability.getInstance().isHuaweiMobileServicesAvailable(context);
        if (UNAVAILABLE_RESULTS.contains(result)) {
            return null;
        }

        return new HuaweiMapFactory();
    }

}