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

import android.graphics.Bitmap;
import android.location.Location;
import android.view.View;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresPermission;

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

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;

@SuppressWarnings("unused")
class AmazonMapClient implements MapClient {

    private final com.amazon.geo.mapsv2.AmazonMap mDelegate;
    private final UiSettings mSettings;

    AmazonMapClient(@NonNull com.amazon.geo.mapsv2.AmazonMap map) {
        mDelegate = map;
        mSettings = new UiSettings(map.getUiSettings());
    }

    @Override
    public @NonNull CameraPosition getCameraPosition() {
        return AmazonCameraPosition.wrap(mDelegate.getCameraPosition());
    }

    @Override
    public float getMaxZoomLevel() {
        return mDelegate.getMaxZoomLevel();
    }

    @Override
    public float getMinZoomLevel() {
        return mDelegate.getMinZoomLevel();
    }

    @Override
    public void moveCamera(@NonNull CameraUpdate update) {
        mDelegate.moveCamera(AmazonCameraUpdate.unwrap(update));
    }

    @Override
    public void animateCamera(@NonNull CameraUpdate update) {
        mDelegate.animateCamera(AmazonCameraUpdate.unwrap(update));
    }

    @Override
    public void animateCamera(
            @NonNull CameraUpdate update, @Nullable final CancelableCallback callback) {
        mDelegate.animateCamera(
                AmazonCameraUpdate.unwrap(update),
                callback == null
                        ? null
                        : new com.amazon.geo.mapsv2.AmazonMap.CancelableCallback() {
                            @Override
                            public void onFinish() {
                                callback.onFinish();
                            }

                            @Override
                            public void onCancel() {
                                callback.onCancel();
                            }
                        }
        );
    }

    @Override
    public void animateCamera(
            @NonNull CameraUpdate update,
            @IntRange(from = 0) int durationMs,
            @Nullable final CancelableCallback callback) {
        mDelegate.animateCamera(
                AmazonCameraUpdate.unwrap(update),
                durationMs,
                callback == null
                        ? null
                        : new com.amazon.geo.mapsv2.AmazonMap.CancelableCallback() {
                            @Override
                            public void onFinish() {
                                callback.onFinish();
                            }

                            @Override
                            public void onCancel() {
                                callback.onCancel();
                            }
                        }
        );
    }

    @Override
    public void stopAnimation() {
        mDelegate.stopAnimation();
    }

    @Override
    public @NonNull Polyline addPolyline(Polyline.Options options) {
        return AmazonPolyline.wrap(mDelegate.addPolyline(AmazonPolyline.Options.unwrap(options)));
    }

    @Override
    public @NonNull Polygon addPolygon(Polygon.Options options) {
        return AmazonPolygon.wrap(mDelegate.addPolygon(AmazonPolygon.Options.unwrap(options)));
    }

    @Override
    public @NonNull Circle addCircle(Circle.Options options) {
        return AmazonCircle.wrap(mDelegate.addCircle(AmazonCircle.Options.unwrap(options)));
    }

    @Override
    public @NonNull Marker addMarker(Marker.Options options) {
        return AmazonMarker.wrap(mDelegate.addMarker(AmazonMarker.Options.unwrap(options)));
    }

    @Override
    public @NonNull GroundOverlay addGroundOverlay(GroundOverlay.Options options) {
        return AmazonGroundOverlay.wrap(
                mDelegate.addGroundOverlay(AmazonGroundOverlay.Options.unwrap(options)));
    }

    @Override
    public @NonNull TileOverlay addTileOverlay(TileOverlay.Options options) {
        return AmazonTileOverlay.wrap(
                mDelegate.addTileOverlay(AmazonTileOverlay.Options.unwrap(options)));
    }

    @Override
    public void clear() {
        mDelegate.clear();
    }

    @Override
    public @Nullable IndoorBuilding getFocusedBuilding() {
        return AmazonIndoorBuilding.wrap(mDelegate.getFocusedBuilding());
    }

    @Override
    public void setOnIndoorStateChangeListener(
            @Nullable final OnIndoorStateChangeListener listener) {
        mDelegate.setOnIndoorStateChangeListener(listener == null
                ? null
                : new com.amazon.geo.mapsv2.AmazonMap.OnIndoorStateChangeListener() {
                    @Override
                    public void onIndoorBuildingsFocused() {
                        listener.onIndoorBuildingFocused();
                    }

                    @Override
                    public void onIndoorLevelActivated(
                            com.amazon.geo.mapsv2.model.IndoorBuilding indoorBuilding) {
                        listener.onIndoorLevelActivated(
                                AmazonIndoorBuilding.wrap(mDelegate.getFocusedBuilding()));
                    }
                }
        );
    }

    @Override
    public int getMapType() {
        return mDelegate.getMapType();
    }

    @Override
    public void setMapType(int type) {
        mDelegate.setMapType(type);
    }

    @Override
    public boolean isTrafficEnabled() {
        return mDelegate.isTrafficEnabled();
    }

    @Override
    public void setTrafficEnabled(boolean enabled) {
        mDelegate.setTrafficEnabled(enabled);
    }

    @Override
    public boolean isIndoorEnabled() {
        return mDelegate.isIndoorEnabled();
    }

    @Override
    public boolean setIndoorEnabled(boolean enabled) {
        return mDelegate.setIndoorEnabled(enabled);
    }

    @Override
    public boolean isBuildingsEnabled() {
        return mDelegate.isBuildingsEnabled();
    }

    @Override
    public void setBuildingsEnabled(boolean enabled) {
        mDelegate.setBuildingsEnabled(enabled);
    }

    @Override
    public boolean isMyLocationEnabled() {
        return mDelegate.isMyLocationEnabled();
    }

    @RequiresPermission(anyOf = {ACCESS_COARSE_LOCATION, ACCESS_FINE_LOCATION})
    @Override
    public void setMyLocationEnabled(boolean enabled) {
        mDelegate.setMyLocationEnabled(enabled);
    }

    @Override
    public void setLocationSource(@Nullable final LocationSource source) {
        mDelegate.setLocationSource(source == null
                ? null
                : new com.amazon.geo.mapsv2.LocationSource() {
                    @Override
                    public void activate(final OnLocationChangedListener listener) {
                        source.activate(new LocationSource.OnLocationChangedListener() {
                            @Override
                            public void onLocationChanged(@NonNull Location location) {
                                listener.onLocationChanged(location);
                            }
                        });
                    }

                    @Override
                    public void deactivate() {
                        source.deactivate();
                    }
                });
    }

    @Override
    public @NonNull MapClient.UiSettings getUiSettings() {
        return mSettings;
    }

    @Override
    public @NonNull Projection getProjection() {
        return AmazonProjection.wrap(mDelegate.getProjection());
    }

    @Override
    public void setOnCameraMoveStartedListener(
            @Nullable final OnCameraMoveStartedListener listener) {
        // Not supported, no-op.
    }

    @Override
    public void setOnCameraMoveListener(@Nullable final OnCameraMoveListener listener) {
        // Not supported, no-op.
    }

    @Override
    public void setOnCameraMoveCanceledListener(
            @Nullable final OnCameraMoveCanceledListener listener) {
        // Not supported, no-op.
    }

    @Override
    public void setOnCameraIdleListener(@Nullable final OnCameraIdleListener listener) {
        // Not supported, no-op.
    }

    @Override
    public void setOnMapClickListener(@Nullable final OnMapClickListener listener) {
        mDelegate.setOnMapClickListener(listener == null
                ? null
                : new com.amazon.geo.mapsv2.AmazonMap.OnMapClickListener() {
                    @Override
                    public void onMapClick(com.amazon.geo.mapsv2.model.LatLng point) {
                        listener.onMapClick(AmazonLatLng.wrap(point));
                    }
                }
        );
    }

    @Override
    public void setOnMapLongClickListener(@Nullable final OnMapLongClickListener listener) {
        mDelegate.setOnMapLongClickListener(listener == null
                ? null
                : new com.amazon.geo.mapsv2.AmazonMap.OnMapLongClickListener() {
                    @Override
                    public void onMapLongClick(com.amazon.geo.mapsv2.model.LatLng point) {
                        listener.onMapLongClick(AmazonLatLng.wrap(point));
                    }
                }
        );
    }

    @Override
    public void setOnMarkerClickListener(@Nullable final OnMarkerClickListener listener) {
        mDelegate.setOnMarkerClickListener(listener == null
                ? null
                : new com.amazon.geo.mapsv2.AmazonMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(com.amazon.geo.mapsv2.model.Marker marker) {
                        return listener.onMarkerClick(AmazonMarker.wrap(marker));
                    }
                }
        );
    }

    @Override
    public void setOnMarkerDragListener(@Nullable final OnMarkerDragListener listener) {
        mDelegate.setOnMarkerDragListener(listener == null
                ? null
                : new com.amazon.geo.mapsv2.AmazonMap.OnMarkerDragListener() {
                    @Override
                    public void onMarkerDragStart(com.amazon.geo.mapsv2.model.Marker marker) {
                        listener.onMarkerDragStart(AmazonMarker.wrap(marker));
                    }

                    @Override
                    public void onMarkerDrag(com.amazon.geo.mapsv2.model.Marker marker) {
                        listener.onMarkerDrag(AmazonMarker.wrap(marker));
                    }

                    @Override
                    public void onMarkerDragEnd(com.amazon.geo.mapsv2.model.Marker marker) {
                        listener.onMarkerDragEnd(AmazonMarker.wrap(marker));
                    }
                }
        );
    }

    @Override
    public void setOnInfoWindowClickListener(@Nullable final OnInfoWindowClickListener listener) {
        mDelegate.setOnInfoWindowClickListener(listener == null
                ? null
                : new com.amazon.geo.mapsv2.AmazonMap.OnInfoWindowClickListener() {
                    @Override
                    public void onInfoWindowClick(com.amazon.geo.mapsv2.model.Marker marker) {
                        listener.onInfoWindowClick(AmazonMarker.wrap(marker));
                    }
                }
        );
    }

    @Override
    public void setOnInfoWindowLongClickListener(
            @Nullable final OnInfoWindowLongClickListener listener) {
        // Not supported, no-op.
    }

    @Override
    public void setOnInfoWindowCloseListener(
            @Nullable final OnInfoWindowCloseListener listener) {
        // Not supported, no-op.
    }

    @Override
    public void setInfoWindowAdapter(@Nullable final InfoWindowAdapter adapter) {
        mDelegate.setInfoWindowAdapter(adapter == null
                ? null
                : new com.amazon.geo.mapsv2.AmazonMap.InfoWindowAdapter() {
                    @Override
                    public View getInfoWindow(com.amazon.geo.mapsv2.model.Marker marker) {
                        return adapter.getInfoWindow(AmazonMarker.wrap(marker));
                    }

                    @Override
                    public View getInfoContents(com.amazon.geo.mapsv2.model.Marker marker) {
                        return adapter.getInfoContents(AmazonMarker.wrap(marker));
                    }
                }
        );
    }

    @Override
    public void setOnMyLocationButtonClickListener(
            @Nullable final OnMyLocationButtonClickListener listener) {
        mDelegate.setOnMyLocationButtonClickListener(listener == null
                ? null
                : new com.amazon.geo.mapsv2.AmazonMap.OnMyLocationButtonClickListener() {
                    @Override
                    public boolean onMyLocationButtonClick() {
                        return listener.onMyLocationButtonClick();
                    }
                }
        );
    }

    @Override
    public void setOnMyLocationClickListener(@Nullable final OnMyLocationClickListener listener) {
        // Not supported, no-op.
    }

    @Override
    public void setOnMapLoadedCallback(@Nullable final OnMapLoadedCallback callback) {
        mDelegate.setOnMapLoadedCallback(callback == null
                ? null
                : new com.amazon.geo.mapsv2.AmazonMap.OnMapLoadedCallback() {
                    @Override
                    public void onMapLoaded() {
                        callback.onMapLoaded();
                    }
                }
        );
    }

    @Override
    public void setOnGroundOverlayClickListener(
            @Nullable final OnGroundOverlayClickListener listener) {
        // Not supported, no-op.
    }

    @Override
    public void setOnCircleClickListener(@Nullable final OnCircleClickListener listener) {
        // Not supported, no-op.
    }

    @Override
    public void setOnPolygonClickListener(@Nullable final OnPolygonClickListener listener) {
        // Not supported, no-op.
    }

    @Override
    public void setOnPolylineClickListener(@Nullable final OnPolylineClickListener listener) {
        // Not supported, no-op.
    }

    @Override
    public void snapshot(@NonNull final SnapshotReadyCallback callback) {
        mDelegate.snapshot(new com.amazon.geo.mapsv2.AmazonMap.SnapshotReadyCallback() {
            @Override
            public void onSnapshotReady(Bitmap bitmap) {
                callback.onSnapshotReady(bitmap);
            }
        });
    }

    @Override
    public void snapshot(@NonNull final SnapshotReadyCallback callback, @Nullable Bitmap bitmap) {
        mDelegate.snapshot(new com.amazon.geo.mapsv2.AmazonMap.SnapshotReadyCallback() {
            @Override
            public void onSnapshotReady(Bitmap bitmap) {
                callback.onSnapshotReady(bitmap);
            }
        }, bitmap);
    }

    @Override
    public void setPadding(int left, int top, int right, int bottom) {
        mDelegate.setPadding(left, top, right, bottom);
    }

    @Override
    public void setContentDescription(String description) {
        // Not supported, no-op.
    }

    @Override
    public void setOnPoiClickListener(@Nullable final OnPoiClickListener listener) {
        // Not supported, no-op.
    }

    @Override
    public boolean setMapStyle(@Nullable MapClient.Style.Options style) {
        return false; // Not supported, the style is left unchanged.
    }

    @Override
    public void setMinZoomPreference(float minZoomPreference) {
        // Not supported, no-op.
    }

    @Override
    public void setMaxZoomPreference(float maxZoomPreference) {
        // Not supported, no-op.
    }

    @Override
    public void resetMinMaxZoomPreference() {
        // Not supported, no-op.
    }

    @Override
    public void setLatLngBoundsForCameraTarget(@Nullable LatLngBounds bounds) {
        // Not supported, no-op.
    }


    static class Style implements MapClient.Style {
        static class Options implements MapClient.Style.Options {
            static final @NonNull Options NULL = new Options();

            private Options() {}
        }
    }


    static class UiSettings implements MapClient.UiSettings {
        private final com.amazon.geo.mapsv2.UiSettings mDelegate;

        UiSettings(@NonNull com.amazon.geo.mapsv2.UiSettings delegate) {
            mDelegate = delegate;
        }

        @Override
        public void setZoomControlsEnabled(boolean enabled) {
            mDelegate.setZoomControlsEnabled(enabled);
        }

        @Override
        public void setCompassEnabled(boolean enabled) {
            mDelegate.setCompassEnabled(enabled);
        }

        @Override
        public void setMyLocationButtonEnabled(boolean enabled) {
            mDelegate.setMyLocationButtonEnabled(enabled);
        }

        @Override
        public void setIndoorLevelPickerEnabled(boolean enabled) {
            mDelegate.setIndoorLevelPickerEnabled(enabled);
        }

        @Override
        public void setScrollGesturesEnabled(boolean enabled) {
            mDelegate.setScrollGesturesEnabled(enabled);
        }

        @Override
        public void setZoomGesturesEnabled(boolean enabled) {
            mDelegate.setZoomGesturesEnabled(enabled);
        }

        @Override
        public void setTiltGesturesEnabled(boolean enabled) {
            mDelegate.setTiltGesturesEnabled(enabled);
        }

        @Override
        public void setRotateGesturesEnabled(boolean enabled) {
            mDelegate.setRotateGesturesEnabled(enabled);
        }

        @Override
        public void setScrollGesturesEnabledDuringRotateOrZoom(boolean enabled) {
            // Not supported, no-op.
        }

        @Override
        public void setAllGesturesEnabled(boolean enabled) {
            mDelegate.setAllGesturesEnabled(enabled);
        }

        @Override
        public void setMapToolbarEnabled(boolean enabled) {
            mDelegate.setMapToolbarEnabled(enabled);
        }

        @Override
        public boolean isZoomControlsEnabled() {
            return mDelegate.isZoomControlsEnabled();
        }

        @Override
        public boolean isCompassEnabled() {
            return mDelegate.isCompassEnabled();
        }

        @Override
        public boolean isMyLocationButtonEnabled() {
            return mDelegate.isMyLocationButtonEnabled();
        }

        @Override
        public boolean isIndoorLevelPickerEnabled() {
            return mDelegate.isIndoorLevelPickerEnabled();
        }

        @Override
        public boolean isScrollGesturesEnabled() {
            return mDelegate.isScrollGesturesEnabled();
        }

        @Override
        public boolean isScrollGesturesEnabledDuringRotateOrZoom() {
            return false; // Not supported, fallback to default.
        }

        @Override
        public boolean isZoomGesturesEnabled() {
            return mDelegate.isZoomGesturesEnabled();
        }

        @Override
        public boolean isTiltGesturesEnabled() {
            return mDelegate.isTiltGesturesEnabled();
        }

        @Override
        public boolean isRotateGesturesEnabled() {
            return mDelegate.isRotateGesturesEnabled();
        }

        @Override
        public boolean isMapToolbarEnabled() {
            return mDelegate.isMapToolbarEnabled();
        }
    }

}
