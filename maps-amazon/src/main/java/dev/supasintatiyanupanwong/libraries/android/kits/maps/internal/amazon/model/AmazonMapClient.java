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

package dev.supasintatiyanupanwong.libraries.android.kits.maps.internal.amazon.model;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static androidx.annotation.RestrictTo.Scope.LIBRARY;

import android.graphics.Bitmap;
import android.location.Location;
import android.view.View;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresPermission;
import androidx.annotation.RestrictTo;

import com.amazon.geo.mapsv2.AmazonMap;

import java.util.Objects;

import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.CameraPosition;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.CameraUpdate;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.Circle;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.GroundOverlay;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.IndoorBuilding;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.LatLngBounds;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.LocationSource;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.MapClient;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.Marker;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.Polygon;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.Polyline;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.Projection;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.TileOverlay;

@SuppressWarnings("unused")
@RestrictTo(LIBRARY)
public class AmazonMapClient implements MapClient {

    private final com.amazon.geo.mapsv2.AmazonMap mDelegate;
    private final UiSettings mSettings;

    public AmazonMapClient(@NonNull com.amazon.geo.mapsv2.AmazonMap map) {
        mDelegate = map;
        mSettings = new UiSettings(map.getUiSettings());

        setMapType(MapClient.MAP_TYPE_NORMAL);
    }

    @Override public @NonNull CameraPosition getCameraPosition() {
        return AmazonCameraPosition.wrap(mDelegate.getCameraPosition());
    }

    @Override public float getMaxZoomLevel() {
        return mDelegate.getMaxZoomLevel();
    }

    @Override public float getMinZoomLevel() {
        return mDelegate.getMinZoomLevel();
    }

    @Override public void moveCamera(@NonNull CameraUpdate update) {
        mDelegate.moveCamera(
                Objects.requireNonNull(AmazonCameraUpdate.unwrap(update), "update == null"));
    }

    @Override public void animateCamera(@NonNull CameraUpdate update) {
        mDelegate.animateCamera(
                Objects.requireNonNull(AmazonCameraUpdate.unwrap(update), "update == null"));
    }

    @Override public void animateCamera(
            @NonNull CameraUpdate update,
            final @Nullable CancelableCallback callback
    ) {
        mDelegate.animateCamera(
                Objects.requireNonNull(AmazonCameraUpdate.unwrap(update), "update == null"),
                callback == null
                        ? null
                        : new com.amazon.geo.mapsv2.AmazonMap.CancelableCallback() {
                            @Override public void onFinish() {
                                callback.onFinish();
                            }

                            @Override public void onCancel() {
                                callback.onCancel();
                            }
                        }
        );
    }

    @Override public void animateCamera(
            @NonNull CameraUpdate update,
            @IntRange(from = 0) int durationMs,
            final @Nullable CancelableCallback callback
    ) {
        mDelegate.animateCamera(
                Objects.requireNonNull(AmazonCameraUpdate.unwrap(update), "update == null"),
                durationMs,
                callback == null
                        ? null
                        : new com.amazon.geo.mapsv2.AmazonMap.CancelableCallback() {
                            @Override public void onFinish() {
                                callback.onFinish();
                            }

                            @Override public void onCancel() {
                                callback.onCancel();
                            }
                        }
        );
    }

    @Override public void stopAnimation() {
        mDelegate.stopAnimation();
    }

    @Override public @Nullable Polyline addPolyline(@NonNull Polyline.Options options) {
        final @Nullable com.amazon.geo.mapsv2.model.PolylineOptions unwrap =
                AmazonPolyline.Options.unwrap(options);
        if (unwrap == null) {
            return null;
        } else {
            return AmazonPolyline.wrap(mDelegate.addPolyline(unwrap));
        }
    }

    @Override public @Nullable Polygon addPolygon(@NonNull Polygon.Options options) {
        final @Nullable com.amazon.geo.mapsv2.model.PolygonOptions unwrap =
                AmazonPolygon.Options.unwrap(options);
        if (unwrap == null) {
            return null;
        } else {
            return AmazonPolygon.wrap(mDelegate.addPolygon(unwrap));
        }
    }

    @Override public @Nullable Circle addCircle(@NonNull Circle.Options options) {
        final @Nullable com.amazon.geo.mapsv2.model.CircleOptions unwrap =
                AmazonCircle.Options.unwrap(options);
        if (unwrap == null) {
            return null;
        } else {
            return AmazonCircle.wrap(mDelegate.addCircle(unwrap));
        }
    }

    @Override public @Nullable Marker addMarker(@NonNull Marker.Options options) {
        final @Nullable com.amazon.geo.mapsv2.model.MarkerOptions unwrap =
                AmazonMarker.Options.unwrap(options);
        if (unwrap == null) {
            return null;
        } else {
            return AmazonMarker.wrap(mDelegate.addMarker(unwrap));
        }
    }

    @Override public @Nullable GroundOverlay addGroundOverlay(
            @NonNull GroundOverlay.Options options) {
        final @Nullable com.amazon.geo.mapsv2.model.GroundOverlayOptions unwrap =
                AmazonGroundOverlay.Options.unwrap(options);
        if (unwrap == null) {
            return null;
        } else {
            return AmazonGroundOverlay.wrap(mDelegate.addGroundOverlay(unwrap));
        }
    }

    @Override public @Nullable TileOverlay addTileOverlay(@NonNull TileOverlay.Options options) {
        final @Nullable com.amazon.geo.mapsv2.model.TileOverlayOptions unwrap =
                AmazonTileOverlay.Options.unwrap(options);
        if (unwrap == null) {
            return null;
        } else {
            return AmazonTileOverlay.wrap(mDelegate.addTileOverlay(unwrap));
        }
    }

    @Override public void clear() {
        mDelegate.clear();
    }

    @Override public @Nullable IndoorBuilding getFocusedBuilding() {
        return AmazonIndoorBuilding.wrap(mDelegate.getFocusedBuilding());
    }

    @Override public void setOnIndoorStateChangeListener(
            final @Nullable OnIndoorStateChangeListener listener
    ) {
        mDelegate.setOnIndoorStateChangeListener(listener == null
                ? null
                : new com.amazon.geo.mapsv2.AmazonMap.OnIndoorStateChangeListener() {
                    @Override public void onIndoorBuildingsFocused() {
                        listener.onIndoorBuildingFocused();
                    }

                    @Override public void onIndoorLevelActivated(
                            @NonNull com.amazon.geo.mapsv2.model.IndoorBuilding indoorBuilding
                    ) {
                        listener.onIndoorLevelActivated(
                                AmazonIndoorBuilding.wrap(mDelegate.getFocusedBuilding()));
                    }
                }
        );
    }

    @Override public int getMapType() {
        return mDelegate.getMapType();
    }

    @Override public void setMapType(int type) {
        mDelegate.setMapType(type);
    }

    @Override public boolean isTrafficEnabled() {
        return mDelegate.isTrafficEnabled();
    }

    @Override public void setTrafficEnabled(boolean enabled) {
        mDelegate.setTrafficEnabled(enabled);
    }

    @Override public boolean isIndoorEnabled() {
        return mDelegate.isIndoorEnabled();
    }

    @Override public boolean setIndoorEnabled(boolean enabled) {
        return mDelegate.setIndoorEnabled(enabled);
    }

    @Override public boolean isBuildingsEnabled() {
        return mDelegate.isBuildingsEnabled();
    }

    @Override public void setBuildingsEnabled(boolean enabled) {
        mDelegate.setBuildingsEnabled(enabled);
    }

    @Override public boolean isMyLocationEnabled() {
        return mDelegate.isMyLocationEnabled();
    }

    @RequiresPermission(anyOf = {ACCESS_COARSE_LOCATION, ACCESS_FINE_LOCATION})
    @Override public void setMyLocationEnabled(boolean enabled) {
        mDelegate.setMyLocationEnabled(enabled);
    }

    @Override public void setLocationSource(final @Nullable LocationSource source) {
        mDelegate.setLocationSource(source == null
                ? null
                : new com.amazon.geo.mapsv2.LocationSource() {
                    @Override public void activate(final OnLocationChangedListener listener) {
                        source.activate(listener::onLocationChanged);
                    }

                    @Override public void deactivate() {
                        source.deactivate();
                    }
                }
        );
    }

    @Override public @NonNull MapClient.UiSettings getUiSettings() {
        return mSettings;
    }

    @Override public @NonNull Projection getProjection() {
        return AmazonProjection.wrap(mDelegate.getProjection());
    }

    @Override public void setOnCameraMoveStartedListener(
            final @Nullable OnCameraMoveStartedListener listener
    ) {
        // Not supported, no-op.
    }

    @Override public void setOnCameraMoveListener(
            final @Nullable OnCameraMoveListener listener
    ) {
        // Not supported, no-op.
    }

    @Override public void setOnCameraMoveCanceledListener(
            final @Nullable OnCameraMoveCanceledListener listener
    ) {
        // Not supported, no-op.
    }

    @Override public void setOnCameraIdleListener(
            final @Nullable OnCameraIdleListener listener
    ) {
        // Not supported, no-op.
    }

    @Override public void setOnMapClickListener(
            final @Nullable OnMapClickListener listener
    ) {
        mDelegate.setOnMapClickListener(listener == null
                ? null
                : point -> listener.onMapClick(AmazonLatLng.wrap(point))
        );
    }

    @Override public void setOnMapLongClickListener(
            final @Nullable OnMapLongClickListener listener
    ) {
        mDelegate.setOnMapLongClickListener(listener == null
                ? null
                : point -> listener.onMapLongClick(AmazonLatLng.wrap(point))
        );
    }

    @Override public void setOnMarkerClickListener(
            final @Nullable OnMarkerClickListener listener
    ) {
        mDelegate.setOnMarkerClickListener(listener == null
                ? null
                : marker -> listener.onMarkerClick(AmazonMarker.wrap(marker))
        );
    }

    @Override public void setOnMarkerDragListener(
            final @Nullable OnMarkerDragListener listener
    ) {
        mDelegate.setOnMarkerDragListener(listener == null
                ? null
                : new com.amazon.geo.mapsv2.AmazonMap.OnMarkerDragListener() {
                    @Override public void onMarkerDragStart(
                            @NonNull com.amazon.geo.mapsv2.model.Marker marker
                    ) {
                        listener.onMarkerDragStart(AmazonMarker.wrap(marker));
                    }

                    @Override public void onMarkerDrag(
                            @NonNull com.amazon.geo.mapsv2.model.Marker marker
                    ) {
                        listener.onMarkerDrag(AmazonMarker.wrap(marker));
                    }

                    @Override public void onMarkerDragEnd(
                            @NonNull com.amazon.geo.mapsv2.model.Marker marker
                    ) {
                        listener.onMarkerDragEnd(AmazonMarker.wrap(marker));
                    }
                }
        );
    }

    @Override public void setOnInfoWindowClickListener(
            final @Nullable OnInfoWindowClickListener listener
    ) {
        mDelegate.setOnInfoWindowClickListener(listener == null
                ? null
                : marker -> listener.onInfoWindowClick(AmazonMarker.wrap(marker))
        );
    }

    @Override public void setOnInfoWindowLongClickListener(
            final @Nullable OnInfoWindowLongClickListener listener
    ) {
        // Not supported, no-op.
    }

    @Override public void setOnInfoWindowCloseListener(
            final @Nullable OnInfoWindowCloseListener listener
    ) {
        // Not supported, no-op.
    }

    @Override public void setInfoWindowAdapter(final @Nullable InfoWindowAdapter adapter) {
        mDelegate.setInfoWindowAdapter(adapter == null
                ? null
                : new com.amazon.geo.mapsv2.AmazonMap.InfoWindowAdapter() {
                    @Override public View getInfoWindow(
                            @NonNull com.amazon.geo.mapsv2.model.Marker marker
                    ) {
                        return adapter.getInfoWindow(AmazonMarker.wrap(marker));
                    }

                    @Override public View getInfoContents(
                            @NonNull com.amazon.geo.mapsv2.model.Marker marker
                    ) {
                        return adapter.getInfoContents(AmazonMarker.wrap(marker));
                    }
                }
        );
    }

    @Override public void setOnMyLocationButtonClickListener(
            final @Nullable OnMyLocationButtonClickListener listener
    ) {
        mDelegate.setOnMyLocationButtonClickListener(listener == null
                ? null
                : listener::onMyLocationButtonClick
        );
    }

    @Override public void setOnMyLocationClickListener(
            final @Nullable OnMyLocationClickListener listener
    ) {
        // Not supported, no-op.
    }

    @Override public void setOnMapLoadedCallback(
            final @Nullable OnMapLoadedCallback callback
    ) {
        mDelegate.setOnMapLoadedCallback(callback == null
                ? null
                : callback::onMapLoaded
        );
    }

    @Override public void setOnGroundOverlayClickListener(
            final @Nullable OnGroundOverlayClickListener listener
    ) {
        // Not supported, no-op.
    }

    @Override public void setOnCircleClickListener(
            final @Nullable OnCircleClickListener listener
    ) {
        // Not supported, no-op.
    }

    @Override public void setOnPolygonClickListener(
            final @Nullable OnPolygonClickListener listener
    ) {
        // Not supported, no-op.
    }

    @Override public void setOnPolylineClickListener(
            final @Nullable OnPolylineClickListener listener
    ) {
        // Not supported, no-op.
    }

    @Override public void snapshot(@NonNull final SnapshotReadyCallback callback) {
        mDelegate.snapshot(callback::onSnapshotReady);
    }

    @Override public void snapshot(
            @NonNull final SnapshotReadyCallback callback,
            @Nullable Bitmap bitmap
    ) {
        mDelegate.snapshot(callback::onSnapshotReady, bitmap);
    }

    @Override public void setPadding(int left, int top, int right, int bottom) {
        mDelegate.setPadding(left, top, right, bottom);
    }

    @Override public void setContentDescription(String description) {
        // Not supported, no-op.
    }

    @Override public void setOnPoiClickListener(final @Nullable OnPoiClickListener listener) {
        // Not supported, no-op.
    }

    @Override public boolean setMapStyle(@Nullable MapClient.Style.Options style) {
        return false; // Not supported, the style is left unchanged.
    }

    @Override public void setMinZoomPreference(float minZoomPreference) {
        // Not supported, no-op.
    }

    @Override public void setMaxZoomPreference(float maxZoomPreference) {
        // Not supported, no-op.
    }

    @Override public void resetMinMaxZoomPreference() {
        // Not supported, no-op.
    }

    @Override public void setLatLngBoundsForCameraTarget(@Nullable LatLngBounds bounds) {
        // Not supported, no-op.
    }


    static class UiSettings implements MapClient.UiSettings {
        private final com.amazon.geo.mapsv2.UiSettings mDelegate;

        UiSettings(@NonNull com.amazon.geo.mapsv2.UiSettings delegate) {
            mDelegate = delegate;
        }

        @Override public void setZoomControlsEnabled(boolean enabled) {
            mDelegate.setZoomControlsEnabled(enabled);
        }

        @Override public void setCompassEnabled(boolean enabled) {
            mDelegate.setCompassEnabled(enabled);
        }

        @Override public void setMyLocationButtonEnabled(boolean enabled) {
            mDelegate.setMyLocationButtonEnabled(enabled);
        }

        @Override public void setIndoorLevelPickerEnabled(boolean enabled) {
            mDelegate.setIndoorLevelPickerEnabled(enabled);
        }

        @Override public void setScrollGesturesEnabled(boolean enabled) {
            mDelegate.setScrollGesturesEnabled(enabled);
        }

        @Override public void setZoomGesturesEnabled(boolean enabled) {
            mDelegate.setZoomGesturesEnabled(enabled);
        }

        @Override public void setTiltGesturesEnabled(boolean enabled) {
            mDelegate.setTiltGesturesEnabled(enabled);
        }

        @Override public void setRotateGesturesEnabled(boolean enabled) {
            mDelegate.setRotateGesturesEnabled(enabled);
        }

        @Override public void setScrollGesturesEnabledDuringRotateOrZoom(boolean enabled) {
            // Not supported, no-op.
        }

        @Override public void setAllGesturesEnabled(boolean enabled) {
            mDelegate.setAllGesturesEnabled(enabled);
        }

        @Override public void setMapToolbarEnabled(boolean enabled) {
            mDelegate.setMapToolbarEnabled(enabled);
        }

        @Override public boolean isZoomControlsEnabled() {
            return mDelegate.isZoomControlsEnabled();
        }

        @Override public boolean isCompassEnabled() {
            return mDelegate.isCompassEnabled();
        }

        @Override public boolean isMyLocationButtonEnabled() {
            return mDelegate.isMyLocationButtonEnabled();
        }

        @Override public boolean isIndoorLevelPickerEnabled() {
            return mDelegate.isIndoorLevelPickerEnabled();
        }

        @Override public boolean isScrollGesturesEnabled() {
            return mDelegate.isScrollGesturesEnabled();
        }

        @Override public boolean isScrollGesturesEnabledDuringRotateOrZoom() {
            return false; // Not supported, fallback to default.
        }

        @Override public boolean isZoomGesturesEnabled() {
            return mDelegate.isZoomGesturesEnabled();
        }

        @Override public boolean isTiltGesturesEnabled() {
            return mDelegate.isTiltGesturesEnabled();
        }

        @Override public boolean isRotateGesturesEnabled() {
            return mDelegate.isRotateGesturesEnabled();
        }

        @Override public boolean isMapToolbarEnabled() {
            return mDelegate.isMapToolbarEnabled();
        }
    }
}
