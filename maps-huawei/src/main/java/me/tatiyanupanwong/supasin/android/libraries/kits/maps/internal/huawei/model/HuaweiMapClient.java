/*
 * Copyright (C) 2020 Supasin Tatiyanupanwong
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package me.tatiyanupanwong.supasin.android.libraries.kits.maps.internal.huawei.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.location.Location;
import android.view.View;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RawRes;
import androidx.annotation.RequiresPermission;

import me.tatiyanupanwong.supasin.android.libraries.kits.maps.model.CameraPosition;
import me.tatiyanupanwong.supasin.android.libraries.kits.maps.model.CameraUpdate;
import me.tatiyanupanwong.supasin.android.libraries.kits.maps.model.Circle;
import me.tatiyanupanwong.supasin.android.libraries.kits.maps.model.GroundOverlay;
import me.tatiyanupanwong.supasin.android.libraries.kits.maps.model.IndoorBuilding;
import me.tatiyanupanwong.supasin.android.libraries.kits.maps.model.LatLngBounds;
import me.tatiyanupanwong.supasin.android.libraries.kits.maps.model.LocationSource;
import me.tatiyanupanwong.supasin.android.libraries.kits.maps.model.MapClient;
import me.tatiyanupanwong.supasin.android.libraries.kits.maps.model.Marker;
import me.tatiyanupanwong.supasin.android.libraries.kits.maps.model.Polygon;
import me.tatiyanupanwong.supasin.android.libraries.kits.maps.model.Polyline;
import me.tatiyanupanwong.supasin.android.libraries.kits.maps.model.Projection;
import me.tatiyanupanwong.supasin.android.libraries.kits.maps.model.TileOverlay;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;

@SuppressWarnings("unused")
class HuaweiMapClient implements MapClient {

    private final com.huawei.hms.maps.HuaweiMap mDelegate;
    private final UiSettings mSettings;

    private Rect mLastPadding;

    HuaweiMapClient(@NonNull com.huawei.hms.maps.HuaweiMap map) {
        mDelegate = map;
        mSettings = new UiSettings(map.getUiSettings());
    }

    @NonNull
    @Override
    public CameraPosition getCameraPosition() {
        return HuaweiCameraPosition.wrap(mDelegate.getCameraPosition());
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
        mDelegate.moveCamera(HuaweiCameraUpdate.unwrap(update));
    }

    @Override
    public void animateCamera(@NonNull CameraUpdate update) {
        mDelegate.animateCamera(HuaweiCameraUpdate.unwrap(update));
    }

    @Override
    public void animateCamera(
            @NonNull CameraUpdate update, @Nullable final CancelableCallback callback) {
        mDelegate.animateCamera(
                HuaweiCameraUpdate.unwrap(update),
                callback == null
                        ? null
                        : new com.huawei.hms.maps.HuaweiMap.CancelableCallback() {
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
                HuaweiCameraUpdate.unwrap(update),
                durationMs,
                callback == null
                        ? null
                        : new com.huawei.hms.maps.HuaweiMap.CancelableCallback() {
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

    @NonNull
    @Override
    public Polyline addPolyline(Polyline.Options options) {
        return HuaweiPolyline.wrap(mDelegate.addPolyline(HuaweiPolyline.Options.unwrap(options)));
    }

    @NonNull
    @Override
    public Polygon addPolygon(Polygon.Options options) {
        return HuaweiPolygon.wrap(mDelegate.addPolygon(HuaweiPolygon.Options.unwrap(options)));
    }

    @NonNull
    @Override
    public Circle addCircle(Circle.Options options) {
        return HuaweiCircle.wrap(mDelegate.addCircle(HuaweiCircle.Options.unwrap(options)));
    }

    @NonNull
    @Override
    public Marker addMarker(Marker.Options options) {
        return HuaweiMarker.wrap(mDelegate.addMarker(HuaweiMarker.Options.unwrap(options)));
    }

    @NonNull
    @Override
    public GroundOverlay addGroundOverlay(GroundOverlay.Options options) {
        return HuaweiGroundOverlay.wrap(
                mDelegate.addGroundOverlay(HuaweiGroundOverlay.Options.unwrap(options)));
    }

    @NonNull
    @Override
    public TileOverlay addTileOverlay(TileOverlay.Options options) {
        return HuaweiTileOverlay.wrap(
                mDelegate.addTileOverlay(HuaweiTileOverlay.Options.unwrap(options)));
    }

    @Override
    public void clear() {
        mDelegate.clear();
    }

    @Nullable
    @Override
    public IndoorBuilding getFocusedBuilding() {
        return HuaweiIndoorBuilding.wrap(mDelegate.getFocusedBuilding());
    }

    @Override
    public void setOnIndoorStateChangeListener(
            @Nullable final OnIndoorStateChangeListener listener) {
        mDelegate.setOnIndoorStateChangeListener(listener == null
                ? null
                : new com.huawei.hms.maps.HuaweiMap.OnIndoorStateChangeListener() {
                    @Override
                    public void onIndoorBuildingFocused() {
                        listener.onIndoorBuildingFocused();
                    }

                    @Override
                    public void onIndoorLevelActivated(
                            com.huawei.hms.maps.model.IndoorBuilding indoorBuilding) {
                        listener.onIndoorLevelActivated(
                                HuaweiIndoorBuilding.wrap(mDelegate.getFocusedBuilding()));
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
        // Huawei Map will automatically enable the my-location button once its layer is enabled.
        final boolean isButtonEnabled = mDelegate.getUiSettings().isMyLocationButtonEnabled();
        mDelegate.setMyLocationEnabled(enabled);
        mDelegate.getUiSettings().setMyLocationButtonEnabled(isButtonEnabled);
    }

    @Override
    public void setLocationSource(@Nullable final LocationSource source) {
        mDelegate.setLocationSource(source == null
                ? null
                : new com.huawei.hms.maps.LocationSource() {
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

    @NonNull
    @Override
    public MapClient.UiSettings getUiSettings() {
        return mSettings;
    }

    @NonNull
    @Override
    public Projection getProjection() {
        return HuaweiProjection.wrap(mDelegate.getProjection());
    }

    @Override
    public void setOnCameraMoveStartedListener(
            @Nullable final OnCameraMoveStartedListener listener) {
        mDelegate.setOnCameraMoveStartedListener(listener == null
                ? null
                : new com.huawei.hms.maps.HuaweiMap.OnCameraMoveStartedListener() {
                    @Override
                    public void onCameraMoveStarted(int reason) {
                        listener.onCameraMoveStarted(reason);
                    }
                }
        );
    }

    @Override
    public void setOnCameraMoveListener(@Nullable final OnCameraMoveListener listener) {
        mDelegate.setOnCameraMoveListener(listener == null
                ? null
                : new com.huawei.hms.maps.HuaweiMap.OnCameraMoveListener() {
                    @Override
                    public void onCameraMove() {
                        listener.onCameraMove();
                    }
                }
        );
    }

    @Override
    public void setOnCameraMoveCanceledListener(
            @Nullable final OnCameraMoveCanceledListener listener) {
        mDelegate.setOnCameraMoveCanceledListener(listener == null
                ? null
                : new com.huawei.hms.maps.HuaweiMap.OnCameraMoveCanceledListener() {
                    @Override
                    public void onCameraMoveCanceled() {
                        listener.onCameraMoveCanceled();
                    }
                }
        );
    }

    @Override
    public void setOnCameraIdleListener(@Nullable final OnCameraIdleListener listener) {
        mDelegate.setOnCameraIdleListener(listener == null
                ? null
                : new com.huawei.hms.maps.HuaweiMap.OnCameraIdleListener() {
                    @Override
                    public void onCameraIdle() {
                        listener.onCameraIdle();
                    }
                }
        );
    }

    @Override
    public void setOnMapClickListener(@Nullable final OnMapClickListener listener) {
        mDelegate.setOnMapClickListener(listener == null
                ? null
                : new com.huawei.hms.maps.HuaweiMap.OnMapClickListener() {
                    @Override
                    public void onMapClick(com.huawei.hms.maps.model.LatLng point) {
                        listener.onMapClick(HuaweiLatLng.wrap(point));
                    }
                }
        );
    }

    @Override
    public void setOnMapLongClickListener(@Nullable final OnMapLongClickListener listener) {
        mDelegate.setOnMapLongClickListener(listener == null
                ? null
                : new com.huawei.hms.maps.HuaweiMap.OnMapLongClickListener() {
                    @Override
                    public void onMapLongClick(com.huawei.hms.maps.model.LatLng point) {
                        listener.onMapLongClick(HuaweiLatLng.wrap(point));
                    }
                }
        );
    }

    @Override
    public void setOnMarkerClickListener(@Nullable final OnMarkerClickListener listener) {
        mDelegate.setOnMarkerClickListener(listener == null
                ? null
                : new com.huawei.hms.maps.HuaweiMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(com.huawei.hms.maps.model.Marker marker) {
                        return listener.onMarkerClick(HuaweiMarker.wrap(marker));
                    }
                }
        );
    }

    @Override
    public void setOnMarkerDragListener(@Nullable final OnMarkerDragListener listener) {
        mDelegate.setOnMarkerDragListener(listener == null
                ? null
                : new com.huawei.hms.maps.HuaweiMap.OnMarkerDragListener() {
                    @Override
                    public void onMarkerDragStart(com.huawei.hms.maps.model.Marker marker) {
                        listener.onMarkerDragStart(HuaweiMarker.wrap(marker));
                    }

                    @Override
                    public void onMarkerDrag(com.huawei.hms.maps.model.Marker marker) {

                    }

                    @Override
                    public void onMarkerDragEnd(com.huawei.hms.maps.model.Marker marker) {

                    }
                }
        );
    }

    @Override
    public void setOnInfoWindowClickListener(@Nullable final OnInfoWindowClickListener listener) {
        mDelegate.setOnInfoWindowClickListener(listener == null
                ? null
                : new com.huawei.hms.maps.HuaweiMap.OnInfoWindowClickListener() {
                    @Override
                    public void onInfoWindowClick(com.huawei.hms.maps.model.Marker marker) {
                        listener.onInfoWindowClick(HuaweiMarker.wrap(marker));
                    }
                }
        );
    }

    @Override
    public void setOnInfoWindowLongClickListener(
            @Nullable final OnInfoWindowLongClickListener listener) {
        mDelegate.setOnInfoWindowLongClickListener(listener == null
                ? null
                : new com.huawei.hms.maps.HuaweiMap.OnInfoWindowLongClickListener() {
                    @Override
                    public void onInfoWindowLongClick(
                            com.huawei.hms.maps.model.Marker marker) {
                        listener.onInfoWindowLongClick(HuaweiMarker.wrap(marker));
                    }
                }
        );
    }

    @Override
    public void setOnInfoWindowCloseListener(@Nullable final OnInfoWindowCloseListener listener) {
        mDelegate.setOnInfoWindowCloseListener(listener == null
                ? null
                : new com.huawei.hms.maps.HuaweiMap.OnInfoWindowCloseListener() {
                    @Override
                    public void onInfoWindowClose(com.huawei.hms.maps.model.Marker marker) {
                        listener.onInfoWindowClose(HuaweiMarker.wrap(marker));
                    }
                }
        );
    }

    @Override
    public void setInfoWindowAdapter(@Nullable final InfoWindowAdapter adapter) {
        mDelegate.setInfoWindowAdapter(adapter == null
                ? null
                : new com.huawei.hms.maps.HuaweiMap.InfoWindowAdapter() {
                    @Override
                    public View getInfoWindow(com.huawei.hms.maps.model.Marker marker) {
                        return adapter.getInfoWindow(HuaweiMarker.wrap(marker));
                    }

                    @Override
                    public View getInfoContents(com.huawei.hms.maps.model.Marker marker) {
                        return adapter.getInfoContents(HuaweiMarker.wrap(marker));
                    }
                }
        );
    }

    @Override
    public void setOnMyLocationButtonClickListener(
            @Nullable final OnMyLocationButtonClickListener listener) {
        mDelegate.setOnMyLocationButtonClickListener(listener == null
                ? null
                : new com.huawei.hms.maps.HuaweiMap.OnMyLocationButtonClickListener() {
                    @Override
                    public boolean onMyLocationButtonClick() {
                        return listener.onMyLocationButtonClick();
                    }
                }
        );
    }

    @Override
    public void setOnMyLocationClickListener(@Nullable final OnMyLocationClickListener listener) {
        mDelegate.setOnMyLocationClickListener(listener == null
                ? null
                : new com.huawei.hms.maps.HuaweiMap.OnMyLocationClickListener() {
                    @Override
                    public void onMyLocationClick(@NonNull Location location) {
                        listener.onMyLocationClick(location);
                    }
                }
        );
    }

    @Override
    public void setOnMapLoadedCallback(@Nullable final OnMapLoadedCallback callback) {
        mDelegate.setOnMapLoadedCallback(callback == null
                ? null
                : new com.huawei.hms.maps.HuaweiMap.OnMapLoadedCallback() {
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
        mDelegate.setOnGroundOverlayClickListener(listener == null
                ? null
                : new com.huawei.hms.maps.HuaweiMap.OnGroundOverlayClickListener() {
                    @Override
                    public void onGroundOverlayClick(
                            com.huawei.hms.maps.model.GroundOverlay groundOverlay) {
                        listener.onGroundOverlayClick(HuaweiGroundOverlay.wrap(groundOverlay));
                    }
                }
        );
    }

    @Override
    public void setOnCircleClickListener(@Nullable final OnCircleClickListener listener) {
        mDelegate.setOnCircleClickListener(listener == null
                ? null
                : new com.huawei.hms.maps.HuaweiMap.OnCircleClickListener() {
                    @Override
                    public void onCircleClick(com.huawei.hms.maps.model.Circle circle) {
                        listener.onCircleClick(HuaweiCircle.wrap(circle));
                    }
                }
        );
    }

    @Override
    public void setOnPolygonClickListener(@Nullable final OnPolygonClickListener listener) {
        mDelegate.setOnPolygonClickListener(listener == null
                ? null
                : new com.huawei.hms.maps.HuaweiMap.OnPolygonClickListener() {
                    @Override
                    public void onPolygonClick(com.huawei.hms.maps.model.Polygon polygon) {
                        listener.onPolygonClick(HuaweiPolygon.wrap(polygon));
                    }
                }
        );
    }

    @Override
    public void setOnPolylineClickListener(@Nullable final OnPolylineClickListener listener) {
        mDelegate.setOnPolylineClickListener(listener == null
                ? null
                : new com.huawei.hms.maps.HuaweiMap.OnPolylineClickListener() {
                    @Override
                    public void onPolylineClick(
                            com.huawei.hms.maps.model.Polyline polyline) {
                        listener.onPolylineClick(HuaweiPolyline.wrap(polyline));
                    }
                }
        );
    }

    @Override
    public void snapshot(@NonNull final SnapshotReadyCallback callback) {
        mDelegate.snapshot(new com.huawei.hms.maps.HuaweiMap.SnapshotReadyCallback() {
            @Override
            public void onSnapshotReady(Bitmap bitmap) {
                callback.onSnapshotReady(bitmap);
            }
        });
    }

    @Override
    public void snapshot(@NonNull final SnapshotReadyCallback callback, @Nullable Bitmap bitmap) {
        mDelegate.snapshot(new com.huawei.hms.maps.HuaweiMap.SnapshotReadyCallback() {
            @Override
            public void onSnapshotReady(Bitmap bitmap) {
                callback.onSnapshotReady(bitmap);
            }
        }, bitmap);
    }

    @Override
    public void setPadding(int left, int top, int right, int bottom) {
        if (mLastPadding == null) {
            mLastPadding = new Rect(-1, -1, -1, -1);
        }

        // setPadding causes an entire screen to re-layout which can causes new setPadding call and
        // that'll going forever, e.g. in ViewTreeObserver.OnGlobalLayoutListener#onGlobalLayout().
        // Such issue does not exists in Google Map.
        if (mLastPadding.left == left &&
                mLastPadding.top == top &&
                mLastPadding.right == right &&
                mLastPadding.bottom == bottom) {
            return;
        }

        mLastPadding.set(left, top, right, bottom);
        mDelegate.setPadding(left, top, right, bottom);
    }

    @Override
    public void setContentDescription(String description) {
        mDelegate.setContentDescription(description);
    }

    @Override
    public void setOnPoiClickListener(@Nullable final OnPoiClickListener listener) {
        mDelegate.setOnPoiClickListener(listener == null
                ? null
                : new com.huawei.hms.maps.HuaweiMap.OnPoiClickListener() {
                    @Override
                    public void onPoiClick(
                            com.huawei.hms.maps.model.PointOfInterest pointOfInterest) {
                        listener.onPoiClick(HuaweiPointOfInterest.wrap(pointOfInterest));
                    }
                }
        );
    }

    @Override
    public boolean setMapStyle(@Nullable MapClient.Style.Options style) {
        return mDelegate.setMapStyle(Style.Options.unwrap(style));
    }

    @Override
    public void setMinZoomPreference(float minZoomPreference) {
        mDelegate.setMinZoomPreference(minZoomPreference);
    }

    @Override
    public void setMaxZoomPreference(float maxZoomPreference) {
        mDelegate.setMaxZoomPreference(maxZoomPreference);
    }

    @Override
    public void resetMinMaxZoomPreference() {
        mDelegate.resetMinMaxZoomPreference();
    }

    @Override
    public void setLatLngBoundsForCameraTarget(@Nullable LatLngBounds bounds) {
        mDelegate.setLatLngBoundsForCameraTarget(HuaweiLatLngBounds.unwrap(bounds));
    }


    static class Style implements MapClient.Style {
        static class Options implements MapClient.Style.Options {
            private final com.huawei.hms.maps.model.MapStyleOptions mDelegate;

            Options(String json) {
                mDelegate = new com.huawei.hms.maps.model.MapStyleOptions(json);
            }

            Options(@NonNull Context context, @RawRes int resourceId) {
                mDelegate = com.huawei.hms.maps.model.MapStyleOptions
                        .loadRawResourceStyle(context, resourceId);
            }

            @Override
            public boolean equals(@Nullable Object obj) {
                if (this == obj) {
                    return true;
                }
                if (obj == null || getClass() != obj.getClass()) {
                    return false;
                }

                Options that = (Options) obj;

                return mDelegate.equals(that.mDelegate);
            }

            @Override
            public int hashCode() {
                return mDelegate.hashCode();
            }

            @NonNull
            @Override
            public String toString() {
                return mDelegate.toString();
            }


            @Nullable
            static com.huawei.hms.maps.model.MapStyleOptions unwrap(
                    @Nullable MapClient.Style.Options wrapped) {
                return wrapped == null ? null : ((Style.Options) wrapped).mDelegate;
            }
        }
    }


    static class UiSettings implements MapClient.UiSettings {
        private final com.huawei.hms.maps.UiSettings mDelegate;

        UiSettings(@NonNull com.huawei.hms.maps.UiSettings delegate) {
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
            mDelegate.setScrollGesturesEnabledDuringRotateOrZoom(enabled);
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
            return mDelegate.isScrollGesturesEnabledDuringRotateOrZoom();
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
