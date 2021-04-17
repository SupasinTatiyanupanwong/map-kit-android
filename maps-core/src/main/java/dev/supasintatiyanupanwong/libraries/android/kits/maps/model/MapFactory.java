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

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.RawRes;
import androidx.annotation.RestrictTo;
import androidx.annotation.UiThread;
import androidx.fragment.app.Fragment;

import dev.supasintatiyanupanwong.libraries.android.kits.maps.MapKit;

import static androidx.annotation.RestrictTo.Scope.LIBRARY_GROUP;

/**
 * @since 1.2.0
 */
@RestrictTo(LIBRARY_GROUP)
public interface MapFactory {

    @NonNull BitmapDescriptor.Factory getBitmapDescriptorFactory();

    @NonNull ButtCap newButtCap();

    @NonNull CameraUpdate.Factory getCameraUpdateFactory();

    @NonNull CameraPosition newCameraPositionFromLatLngZoom(@NonNull LatLng target, float zoom);

    @NonNull CameraPosition.Builder newCameraPositionBuilder();

    @NonNull CameraPosition.Builder newCameraPositionBuilder(@NonNull CameraPosition camera);

    @NonNull Circle.Options newCircleOptions();

    @NonNull CustomCap newCustomCap(@NonNull BitmapDescriptor bitmapDescriptor, float refWidth);

    @NonNull CustomCap newCustomCap(@NonNull BitmapDescriptor bitmapDescriptor);

    @NonNull Dot newDot();

    @NonNull Dash newDash(float length);

    @NonNull Gap newGap(float length);

    @NonNull GroundOverlay.Options newGroundOverlayOptions();

    @NonNull LatLng newLatLng(double latitude, double longitude);

    @NonNull LatLngBounds newLatLngBounds(@NonNull LatLng southwest, @NonNull LatLng northeast);

    @NonNull LatLngBounds.Builder newLatLngBoundsBuilder();

    @NonNull MapClient.Style.Options newMapStyleOptions(String json);

    @NonNull MapClient.Style.Options newMapStyleOptions(
            @NonNull Context context,
            @RawRes int resourceId);

    @NonNull Marker.Options newMarkerOptions();

    @NonNull Polygon.Options newPolygonOptions();

    @NonNull Polyline.Options newPolylineOptions();

    @NonNull RoundCap newRoundCap();

    @NonNull SquareCap newSquareCap();

    @NonNull TileOverlay.Options newTileOverlayOptions();

    @NonNull Tile newTile(int width, int height, byte[] data);

    @NonNull Tile noTile();

    @NonNull TileProvider newUrlTileProvider(
            int width,
            int height,
            @NonNull UrlTileProvider tileProvider);

    @NonNull VisibleRegion newVisibleRegion(
            LatLng nearLeft,
            LatLng nearRight,
            LatLng farLeft,
            LatLng farRight,
            LatLngBounds latLngBounds);

    @UiThread
    void getMapAsync(@NonNull Fragment fragment, @NonNull MapKit.OnMapReadyCallback callback);

}
