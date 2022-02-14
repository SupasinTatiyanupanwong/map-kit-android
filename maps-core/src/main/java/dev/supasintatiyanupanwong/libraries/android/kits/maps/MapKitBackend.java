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

import static androidx.annotation.RestrictTo.Scope.LIBRARY_GROUP;

import android.content.Context;

import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.RawRes;
import androidx.annotation.RestrictTo;
import androidx.annotation.UiThread;
import androidx.fragment.app.Fragment;

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

/**
 * @since 1.2.0
 */
@RestrictTo(LIBRARY_GROUP)
public interface MapKitBackend {

    @LayoutRes int getMapFragmentLayoutRes();

    @IdRes int getMapFragmentIdRes();


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
