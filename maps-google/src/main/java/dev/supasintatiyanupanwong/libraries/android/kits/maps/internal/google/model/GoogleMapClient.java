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

package dev.supasintatiyanupanwong.libraries.android.kits.maps.internal.google.model;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static androidx.annotation.RestrictTo.Scope.LIBRARY;

import android.graphics.Bitmap;
import android.view.View;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresPermission;
import androidx.annotation.RestrictTo;

import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.CameraPosition;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.CameraUpdate;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.Circle;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.GroundOverlay;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.IndoorBuilding;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.LatLngBounds;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.LocationSource;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.MapClient;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.MapStyle;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.Marker;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.Polygon;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.Polyline;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.Projection;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.TileOverlay;

@SuppressWarnings("unused")
@RestrictTo(LIBRARY)
public class GoogleMapClient implements MapClient {

    private final com.google.android.gms.maps.GoogleMap mDelegate;
    private final UiSettings mSettings;

    public GoogleMapClient(@NonNull com.google.android.gms.maps.GoogleMap map) {
        mDelegate = map;
        mSettings = new UiSettings(map.getUiSettings());
    }

    @Override public @NonNull CameraPosition getCameraPosition() {
        return GoogleCameraPosition.wrap(mDelegate.getCameraPosition());
    }

    @Override public float getMaxZoomLevel() {
        return mDelegate.getMaxZoomLevel();
    }

    @Override public float getMinZoomLevel() {
        return mDelegate.getMinZoomLevel();
    }

    @Override public void moveCamera(@NonNull CameraUpdate update) {
        mDelegate.moveCamera(GoogleCameraUpdate.unwrap(update));
    }

    @Override public void animateCamera(@NonNull CameraUpdate update) {
        mDelegate.animateCamera(GoogleCameraUpdate.unwrap(update));
    }

    @Override public void animateCamera(
            @NonNull CameraUpdate update,
            final @Nullable CancelableCallback callback
    ) {
        mDelegate.animateCamera(
                GoogleCameraUpdate.unwrap(update),
                callback == null
                        ? null
                        : new com.google.android.gms.maps.GoogleMap.CancelableCallback() {
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
                GoogleCameraUpdate.unwrap(update),
                durationMs,
                callback == null
                        ? null
                        : new com.google.android.gms.maps.GoogleMap.CancelableCallback() {
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

    @Override public @NonNull Polyline addPolyline(@NonNull Polyline.Options options) {
        return GooglePolyline.wrap(mDelegate.addPolyline(GooglePolyline.Options.unwrap(options)));
    }

    @Override public @NonNull Polygon addPolygon(@NonNull Polygon.Options options) {
        return GooglePolygon.wrap(mDelegate.addPolygon(GooglePolygon.Options.unwrap(options)));
    }

    @Override public @NonNull Circle addCircle(@NonNull Circle.Options options) {
        return GoogleCircle.wrap(mDelegate.addCircle(GoogleCircle.Options.unwrap(options)));
    }

    @Override public @NonNull Marker addMarker(@NonNull Marker.Options options) {
        return GoogleMarker.wrap(mDelegate.addMarker(GoogleMarker.Options.unwrap(options)));
    }

    @Override public @NonNull GroundOverlay addGroundOverlay(
            @NonNull GroundOverlay.Options options
    ) {
        return GoogleGroundOverlay.wrap(
                mDelegate.addGroundOverlay(GoogleGroundOverlay.Options.unwrap(options))
        );
    }

    @Override public @NonNull TileOverlay addTileOverlay(@NonNull TileOverlay.Options options) {
        return GoogleTileOverlay.wrap(
                mDelegate.addTileOverlay(GoogleTileOverlay.Options.unwrap(options))
        );
    }

    @Override public void clear() {
        mDelegate.clear();
    }

    @Override public @Nullable IndoorBuilding getFocusedBuilding() {
        return GoogleIndoorBuilding.wrap(mDelegate.getFocusedBuilding());
    }

    @Override public void setOnIndoorStateChangeListener(
            final @Nullable OnIndoorStateChangeListener listener
    ) {
        mDelegate.setOnIndoorStateChangeListener(listener == null
                ? null
                : new com.google.android.gms.maps.GoogleMap.OnIndoorStateChangeListener() {
                    @Override public void onIndoorBuildingFocused() {
                        listener.onIndoorBuildingFocused();
                    }

                    @Override public void onIndoorLevelActivated(
                            @NonNull com.google.android.gms.maps.model.IndoorBuilding indoorBuilding
                    ) {
                        listener.onIndoorLevelActivated(
                                GoogleIndoorBuilding.wrap(mDelegate.getFocusedBuilding())
                        );
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
                : new com.google.android.gms.maps.LocationSource() {
                    @Override public void activate(
                            final @NonNull OnLocationChangedListener listener
                    ) {
                        source.activate(listener::onLocationChanged);
                    }

                    @Override public void deactivate() {
                        source.deactivate();
                    }
                });
    }

    @Override public @NonNull MapClient.UiSettings getUiSettings() {
        return mSettings;
    }

    @Override public @NonNull Projection getProjection() {
        return GoogleProjection.wrap(mDelegate.getProjection());
    }

    @Override public void setOnCameraMoveStartedListener(
            final @Nullable OnCameraMoveStartedListener listener
    ) {
        mDelegate.setOnCameraMoveStartedListener(listener == null
                ? null
                : listener::onCameraMoveStarted
        );
    }

    @Override public void setOnCameraMoveListener(
            final @Nullable OnCameraMoveListener listener
    ) {
        mDelegate.setOnCameraMoveListener(listener == null
                ? null
                : listener::onCameraMove
        );
    }

    @Override public void setOnCameraMoveCanceledListener(
            final @Nullable OnCameraMoveCanceledListener listener
    ) {
        mDelegate.setOnCameraMoveCanceledListener(listener == null
                ? null
                : listener::onCameraMoveCanceled
        );
    }

    @Override public void setOnCameraIdleListener(
            final @Nullable OnCameraIdleListener listener
    ) {
        mDelegate.setOnCameraIdleListener(listener == null
                ? null
                : listener::onCameraIdle
        );
    }

    @Override public void setOnMapClickListener(
            final @Nullable OnMapClickListener listener
    ) {
        mDelegate.setOnMapClickListener(listener == null
                ? null
                : point -> listener.onMapClick(GoogleLatLng.wrap(point))
        );
    }

    @Override public void setOnMapLongClickListener(
            final @Nullable OnMapLongClickListener listener
    ) {
        mDelegate.setOnMapLongClickListener(listener == null
                ? null
                : point -> listener.onMapLongClick(GoogleLatLng.wrap(point))
        );
    }

    @Override public void setOnMarkerClickListener(
            final @Nullable OnMarkerClickListener listener
    ) {
        mDelegate.setOnMarkerClickListener(listener == null
                ? null
                : marker -> listener.onMarkerClick(GoogleMarker.wrap(marker))
        );
    }

    @Override public void setOnMarkerDragListener(
            final @Nullable OnMarkerDragListener listener
    ) {
        mDelegate.setOnMarkerDragListener(listener == null
                ? null
                : new com.google.android.gms.maps.GoogleMap.OnMarkerDragListener() {
                    @Override public void onMarkerDragStart(
                            @NonNull com.google.android.gms.maps.model.Marker marker
                    ) {
                        listener.onMarkerDragStart(GoogleMarker.wrap(marker));
                    }

                    @Override public void onMarkerDrag(
                            @NonNull com.google.android.gms.maps.model.Marker marker
                    ) {
                        listener.onMarkerDrag(GoogleMarker.wrap(marker));
                    }

                    @Override public void onMarkerDragEnd(
                            @NonNull com.google.android.gms.maps.model.Marker marker
                    ) {
                        listener.onMarkerDragEnd(GoogleMarker.wrap(marker));
                    }
                }
        );
    }

    @Override public void setOnInfoWindowClickListener(
            final @Nullable OnInfoWindowClickListener listener
    ) {
        mDelegate.setOnInfoWindowClickListener(listener == null
                ? null
                : marker -> listener.onInfoWindowClick(GoogleMarker.wrap(marker))
        );
    }

    @Override public void setOnInfoWindowLongClickListener(
            final @Nullable OnInfoWindowLongClickListener listener
    ) {
        mDelegate.setOnInfoWindowLongClickListener(listener == null
                ? null
                : marker -> listener.onInfoWindowLongClick(GoogleMarker.wrap(marker))
        );
    }

    @Override public void setOnInfoWindowCloseListener(
            final @Nullable OnInfoWindowCloseListener listener
    ) {
        mDelegate.setOnInfoWindowCloseListener(listener == null
                ? null
                : marker -> listener.onInfoWindowClose(GoogleMarker.wrap(marker))
        );
    }

    @Override public void setInfoWindowAdapter(final @Nullable InfoWindowAdapter adapter) {
        mDelegate.setInfoWindowAdapter(adapter == null
                ? null
                : new com.google.android.gms.maps.GoogleMap.InfoWindowAdapter() {
                    @Override public View getInfoWindow(
                            @NonNull com.google.android.gms.maps.model.Marker marker
                    ) {
                        return adapter.getInfoWindow(GoogleMarker.wrap(marker));
                    }

                    @Override public View getInfoContents(
                            @NonNull com.google.android.gms.maps.model.Marker marker
                    ) {
                        return adapter.getInfoContents(GoogleMarker.wrap(marker));
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
        mDelegate.setOnMyLocationClickListener(listener == null
                ? null
                : listener::onMyLocationClick
        );
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
        mDelegate.setOnGroundOverlayClickListener(listener == null
                ? null
                : overlay -> listener.onGroundOverlayClick(GoogleGroundOverlay.wrap(overlay))
        );
    }

    @Override public void setOnCircleClickListener(
            final @Nullable OnCircleClickListener listener
    ) {
        mDelegate.setOnCircleClickListener(listener == null
                ? null
                : circle -> listener.onCircleClick(GoogleCircle.wrap(circle))
        );
    }

    @Override public void setOnPolygonClickListener(
            final @Nullable OnPolygonClickListener listener
    ) {
        mDelegate.setOnPolygonClickListener(listener == null
                ? null
                : polygon -> listener.onPolygonClick(GooglePolygon.wrap(polygon))
        );
    }

    @Override public void setOnPolylineClickListener(
            final @Nullable OnPolylineClickListener listener
    ) {
        mDelegate.setOnPolylineClickListener(listener == null
                ? null
                : polyline -> listener.onPolylineClick(GooglePolyline.wrap(polyline))
        );
    }

    @Override public void snapshot(final @NonNull SnapshotReadyCallback callback) {
        mDelegate.snapshot(callback::onSnapshotReady);
    }

    @Override public void snapshot(
            final @NonNull SnapshotReadyCallback callback,
            @Nullable Bitmap bitmap
    ) {
        mDelegate.snapshot(callback::onSnapshotReady, bitmap);
    }

    @Override public void setPadding(int left, int top, int right, int bottom) {
        mDelegate.setPadding(left, top, right, bottom);
    }

    @Override public void setContentDescription(String description) {
        mDelegate.setContentDescription(description);
    }

    @Override public void setOnPoiClickListener(final @Nullable OnPoiClickListener listener) {
        mDelegate.setOnPoiClickListener(listener == null
                ? null
                : poi -> listener.onPoiClick(GooglePointOfInterest.wrap(poi))
        );
    }

    @Override public boolean setMapStyle(@Nullable MapClient.Style.Options style) {
        return mDelegate.setMapStyle(GoogleMapStyle.Options.unwrap(style));
    }

    @Override public boolean setMapStyle(@Nullable MapStyle.Options style) {
        return mDelegate.setMapStyle(GoogleMapStyle.Options.unwrap(style));
    }

    @Override public void setMinZoomPreference(float minZoomPreference) {
        mDelegate.setMinZoomPreference(minZoomPreference);
    }

    @Override public void setMaxZoomPreference(float maxZoomPreference) {
        mDelegate.setMaxZoomPreference(maxZoomPreference);
    }

    @Override public void resetMinMaxZoomPreference() {
        mDelegate.resetMinMaxZoomPreference();
    }

    @Override public void setLatLngBoundsForCameraTarget(@Nullable LatLngBounds bounds) {
        mDelegate.setLatLngBoundsForCameraTarget(GoogleLatLngBounds.unwrap(bounds));
    }


    static class UiSettings implements MapClient.UiSettings {
        private final com.google.android.gms.maps.UiSettings mDelegate;

        UiSettings(@NonNull com.google.android.gms.maps.UiSettings delegate) {
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
            mDelegate.setScrollGesturesEnabledDuringRotateOrZoom(enabled);
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
            return mDelegate.isScrollGesturesEnabledDuringRotateOrZoom();
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
