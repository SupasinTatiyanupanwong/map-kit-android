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

package me.tatiyanupanwong.supasin.android.libraries.kits.maps.internal.google.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.location.Location;
import android.view.View;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RawRes;
import androidx.fragment.app.Fragment;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import java.util.Arrays;
import java.util.List;

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
import me.tatiyanupanwong.supasin.android.libraries.kits.maps.model.IndoorBuilding;
import me.tatiyanupanwong.supasin.android.libraries.kits.maps.model.LatLng;
import me.tatiyanupanwong.supasin.android.libraries.kits.maps.model.LatLngBounds;
import me.tatiyanupanwong.supasin.android.libraries.kits.maps.model.LocationSource;
import me.tatiyanupanwong.supasin.android.libraries.kits.maps.model.Map;
import me.tatiyanupanwong.supasin.android.libraries.kits.maps.model.Marker;
import me.tatiyanupanwong.supasin.android.libraries.kits.maps.model.Polygon;
import me.tatiyanupanwong.supasin.android.libraries.kits.maps.model.Polyline;
import me.tatiyanupanwong.supasin.android.libraries.kits.maps.model.Projection;
import me.tatiyanupanwong.supasin.android.libraries.kits.maps.model.RoundCap;
import me.tatiyanupanwong.supasin.android.libraries.kits.maps.model.SquareCap;
import me.tatiyanupanwong.supasin.android.libraries.kits.maps.model.Tile;
import me.tatiyanupanwong.supasin.android.libraries.kits.maps.model.TileOverlay;
import me.tatiyanupanwong.supasin.android.libraries.kits.maps.model.TileProvider;
import me.tatiyanupanwong.supasin.android.libraries.kits.maps.model.UrlTileProvider;
import me.tatiyanupanwong.supasin.android.libraries.kits.maps.model.VisibleRegion;

@SuppressWarnings("unused")
class GoogleMap implements Map {

    private final com.google.android.gms.maps.GoogleMap mDelegate;
    private final UiSettings mSettings;

    private GoogleMap(@NonNull com.google.android.gms.maps.GoogleMap map) {
        mDelegate = map;
        mSettings = new UiSettings(map.getUiSettings());
    }

    @NonNull
    @Override
    public CameraPosition getCameraPosition() {
        return GoogleCameraPosition.wrap(mDelegate.getCameraPosition());
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
        mDelegate.moveCamera(GoogleCameraUpdate.unwrap(update));
    }

    @Override
    public void animateCamera(@NonNull CameraUpdate update) {
        mDelegate.animateCamera(GoogleCameraUpdate.unwrap(update));
    }

    @Override
    public void animateCamera(
            @NonNull CameraUpdate update, @Nullable final CancelableCallback callback) {
        mDelegate.animateCamera(
                GoogleCameraUpdate.unwrap(update),
                callback == null
                        ? null
                        : new com.google.android.gms.maps.GoogleMap.CancelableCallback() {
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
                GoogleCameraUpdate.unwrap(update),
                durationMs,
                callback == null
                        ? null
                        : new com.google.android.gms.maps.GoogleMap.CancelableCallback() {
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
        return GooglePolyline.wrap(mDelegate.addPolyline(GooglePolyline.Options.unwrap(options)));
    }

    @NonNull
    @Override
    public Polygon addPolygon(Polygon.Options options) {
        return GooglePolygon.wrap(mDelegate.addPolygon(GooglePolygon.Options.unwrap(options)));
    }

    @NonNull
    @Override
    public Circle addCircle(Circle.Options options) {
        return GoogleCircle.wrap(mDelegate.addCircle(GoogleCircle.Options.unwrap(options)));
    }

    @NonNull
    @Override
    public Marker addMarker(Marker.Options options) {
        return GoogleMarker.wrap(mDelegate.addMarker(GoogleMarker.Options.unwrap(options)));
    }

    @NonNull
    @Override
    public GroundOverlay addGroundOverlay(GroundOverlay.Options options) {
        return GoogleGroundOverlay.wrap(
                mDelegate.addGroundOverlay(GoogleGroundOverlay.Options.unwrap(options)));
    }

    @NonNull
    @Override
    public TileOverlay addTileOverlay(TileOverlay.Options options) {
        return GoogleTileOverlay.wrap(
                mDelegate.addTileOverlay(GoogleTileOverlay.Options.unwrap(options)));
    }

    @Override
    public void clear() {
        mDelegate.clear();
    }

    @Nullable
    @Override
    public IndoorBuilding getFocusedBuilding() {
        return GoogleIndoorBuilding.wrap(mDelegate.getFocusedBuilding());
    }

    @Override
    public void setOnIndoorStateChangeListener(
            @Nullable final OnIndoorStateChangeListener listener) {
        mDelegate.setOnIndoorStateChangeListener(listener == null
                ? null
                : new com.google.android.gms.maps.GoogleMap.OnIndoorStateChangeListener() {
                    @Override
                    public void onIndoorBuildingFocused() {
                        listener.onIndoorBuildingFocused();
                    }

                    @Override
                    public void onIndoorLevelActivated(
                            com.google.android.gms.maps.model.IndoorBuilding indoorBuilding) {
                        listener.onIndoorLevelActivated(
                                GoogleIndoorBuilding.wrap(mDelegate.getFocusedBuilding()));
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

    @Override
    public void setMyLocationEnabled(boolean enabled) {
        mDelegate.setMyLocationEnabled(enabled);
    }

    @Override
    public void setLocationSource(@Nullable final LocationSource source) {
        mDelegate.setLocationSource(source == null
                ? null
                : new com.google.android.gms.maps.LocationSource() {
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
    public Map.UiSettings getUiSettings() {
        return mSettings;
    }

    @NonNull
    @Override
    public Projection getProjection() {
        return GoogleProjection.wrap(mDelegate.getProjection());
    }

    @Override
    public void setOnCameraMoveStartedListener(
            @Nullable final OnCameraMoveStartedListener listener) {
        mDelegate.setOnCameraMoveStartedListener(listener == null
                ? null
                : new com.google.android.gms.maps.GoogleMap.OnCameraMoveStartedListener() {
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
                : new com.google.android.gms.maps.GoogleMap.OnCameraMoveListener() {
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
                : new com.google.android.gms.maps.GoogleMap.OnCameraMoveCanceledListener() {
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
                : new com.google.android.gms.maps.GoogleMap.OnCameraIdleListener() {
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
                : new com.google.android.gms.maps.GoogleMap.OnMapClickListener() {
                    @Override
                    public void onMapClick(com.google.android.gms.maps.model.LatLng point) {
                        listener.onMapClick(GoogleLatLng.wrap(point));
                    }
                }
        );
    }

    @Override
    public void setOnMapLongClickListener(@Nullable final OnMapLongClickListener listener) {
        mDelegate.setOnMapLongClickListener(listener == null
                ? null
                : new com.google.android.gms.maps.GoogleMap.OnMapLongClickListener() {
                    @Override
                    public void onMapLongClick(com.google.android.gms.maps.model.LatLng point) {
                        listener.onMapLongClick(GoogleLatLng.wrap(point));
                    }
                }
        );
    }

    @Override
    public void setOnMarkerClickListener(@Nullable final OnMarkerClickListener listener) {
        mDelegate.setOnMarkerClickListener(listener == null
                ? null
                : new com.google.android.gms.maps.GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(com.google.android.gms.maps.model.Marker marker) {
                        return listener.onMarkerClick(GoogleMarker.wrap(marker));
                    }
                }
        );
    }

    @Override
    public void setOnMarkerDragListener(@Nullable final OnMarkerDragListener listener) {
        mDelegate.setOnMarkerDragListener(listener == null
                ? null
                : new com.google.android.gms.maps.GoogleMap.OnMarkerDragListener() {
                    @Override
                    public void onMarkerDragStart(com.google.android.gms.maps.model.Marker marker) {
                        listener.onMarkerDragStart(GoogleMarker.wrap(marker));
                    }

                    @Override
                    public void onMarkerDrag(com.google.android.gms.maps.model.Marker marker) {
                        listener.onMarkerDrag(GoogleMarker.wrap(marker));
                    }

                    @Override
                    public void onMarkerDragEnd(com.google.android.gms.maps.model.Marker marker) {
                        listener.onMarkerDragEnd(GoogleMarker.wrap(marker));
                    }
                }
        );
    }

    @Override
    public void setOnInfoWindowClickListener(@Nullable final OnInfoWindowClickListener listener) {
        mDelegate.setOnInfoWindowClickListener(listener == null
                ? null
                : new com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener() {
                    @Override
                    public void onInfoWindowClick(com.google.android.gms.maps.model.Marker marker) {
                        listener.onInfoWindowClick(GoogleMarker.wrap(marker));
                    }
                }
        );
    }

    @Override
    public void setOnInfoWindowLongClickListener(
            @Nullable final OnInfoWindowLongClickListener listener) {
        mDelegate.setOnInfoWindowLongClickListener(listener == null
                ? null
                : new com.google.android.gms.maps.GoogleMap.OnInfoWindowLongClickListener() {
                    @Override
                    public void onInfoWindowLongClick(
                            com.google.android.gms.maps.model.Marker marker) {
                        listener.onInfoWindowLongClick(GoogleMarker.wrap(marker));
                    }
                }
        );
    }

    @Override
    public void setOnInfoWindowCloseListener(@Nullable final OnInfoWindowCloseListener listener) {
        mDelegate.setOnInfoWindowCloseListener(listener == null
                ? null
                : new com.google.android.gms.maps.GoogleMap.OnInfoWindowCloseListener() {
                    @Override
                    public void onInfoWindowClose(com.google.android.gms.maps.model.Marker marker) {
                        listener.onInfoWindowClose(GoogleMarker.wrap(marker));
                    }
                }
        );
    }

    @Override
    public void setInfoWindowAdapter(@Nullable final InfoWindowAdapter adapter) {
        mDelegate.setInfoWindowAdapter(adapter == null
                ? null
                : new com.google.android.gms.maps.GoogleMap.InfoWindowAdapter() {
                    @Override
                    public View getInfoWindow(com.google.android.gms.maps.model.Marker marker) {
                        return adapter.getInfoWindow(GoogleMarker.wrap(marker));
                    }

                    @Override
                    public View getInfoContents(com.google.android.gms.maps.model.Marker marker) {
                        return adapter.getInfoContents(GoogleMarker.wrap(marker));
                    }
                }
        );
    }

    @Override
    public void setOnMyLocationButtonClickListener(
            @Nullable final OnMyLocationButtonClickListener listener) {
        mDelegate.setOnMyLocationButtonClickListener(listener == null
                ? null
                : new com.google.android.gms.maps.GoogleMap.OnMyLocationButtonClickListener() {
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
                : new com.google.android.gms.maps.GoogleMap.OnMyLocationClickListener() {
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
                : new com.google.android.gms.maps.GoogleMap.OnMapLoadedCallback() {
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
                : new com.google.android.gms.maps.GoogleMap.OnGroundOverlayClickListener() {
                    @Override
                    public void onGroundOverlayClick(
                            com.google.android.gms.maps.model.GroundOverlay groundOverlay) {
                        listener.onGroundOverlayClick(GoogleGroundOverlay.wrap(groundOverlay));
                    }
                }
        );
    }

    @Override
    public void setOnCircleClickListener(@Nullable final OnCircleClickListener listener) {
        mDelegate.setOnCircleClickListener(listener == null
                ? null
                : new com.google.android.gms.maps.GoogleMap.OnCircleClickListener() {
                    @Override
                    public void onCircleClick(com.google.android.gms.maps.model.Circle circle) {
                        listener.onCircleClick(GoogleCircle.wrap(circle));
                    }
                }
        );
    }

    @Override
    public void setOnPolygonClickListener(@Nullable final OnPolygonClickListener listener) {
        mDelegate.setOnPolygonClickListener(listener == null
                ? null
                : new com.google.android.gms.maps.GoogleMap.OnPolygonClickListener() {
                    @Override
                    public void onPolygonClick(com.google.android.gms.maps.model.Polygon polygon) {
                        listener.onPolygonClick(GooglePolygon.wrap(polygon));
                    }
                }
        );
    }

    @Override
    public void setOnPolylineClickListener(@Nullable final OnPolylineClickListener listener) {
        mDelegate.setOnPolylineClickListener(listener == null
                ? null
                : new com.google.android.gms.maps.GoogleMap.OnPolylineClickListener() {
                    @Override
                    public void onPolylineClick(
                            com.google.android.gms.maps.model.Polyline polyline) {
                        listener.onPolylineClick(GooglePolyline.wrap(polyline));
                    }
                }
        );
    }

    @Override
    public void snapshot(@NonNull final SnapshotReadyCallback callback) {
        mDelegate.snapshot(new com.google.android.gms.maps.GoogleMap.SnapshotReadyCallback() {
            @Override
            public void onSnapshotReady(Bitmap bitmap) {
                callback.onSnapshotReady(bitmap);
            }
        });
    }

    @Override
    public void snapshot(@NonNull final SnapshotReadyCallback callback, @Nullable Bitmap bitmap) {
        mDelegate.snapshot(new com.google.android.gms.maps.GoogleMap.SnapshotReadyCallback() {
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
        mDelegate.setContentDescription(description);
    }

    @Override
    public void setOnPoiClickListener(@Nullable final OnPoiClickListener listener) {
        mDelegate.setOnPoiClickListener(listener == null
                ? null
                : new com.google.android.gms.maps.GoogleMap.OnPoiClickListener() {
                    @Override
                    public void onPoiClick(
                            com.google.android.gms.maps.model.PointOfInterest pointOfInterest) {
                        listener.onPoiClick(GooglePointOfInterest.wrap(pointOfInterest));
                    }
                }
        );
    }

    @Override
    public boolean setMapStyle(@Nullable Map.Style.Options style) {
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
        mDelegate.setLatLngBoundsForCameraTarget(GoogleLatLngBounds.unwrap(bounds));
    }


    static class Style implements Map.Style {
        static class Options implements Map.Style.Options {
            private final com.google.android.gms.maps.model.MapStyleOptions mDelegate;

            Options(String json) {
                mDelegate = new com.google.android.gms.maps.model.MapStyleOptions(json);
            }

            Options(@NonNull Context context, @RawRes int resourceId) {
                mDelegate = com.google.android.gms.maps.model.MapStyleOptions
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
            static com.google.android.gms.maps.model.MapStyleOptions unwrap(
                    @Nullable Map.Style.Options wrapped) {
                return wrapped == null ? null : ((Style.Options) wrapped).mDelegate;
            }
        }
    }


    static class UiSettings implements Map.UiSettings {
        private final com.google.android.gms.maps.UiSettings mDelegate;

        UiSettings(@NonNull com.google.android.gms.maps.UiSettings delegate) {
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


    static class Factory implements Map.Factory {
        private static final List<Integer> UNAVAILABLE_RESULTS = Arrays.asList(
                ConnectionResult.SERVICE_DISABLED,
                ConnectionResult.SERVICE_MISSING,
                ConnectionResult.SERVICE_INVALID);

        Factory(Context context) {
            final int result =
                    GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(context);
            if (UNAVAILABLE_RESULTS.contains(result)) {
                throw new UnsupportedOperationException("Google Maps is not available.");
            }
        }

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
        public CameraPosition newCameraPosition(
                @NonNull LatLng target, float zoom, float tilt, float bearing) {
            return new GoogleCameraPosition(target, zoom, tilt, bearing);
        }

        @NonNull
        @Override
        public CameraPosition newCameraPositionFromLatLngZoom(@NonNull LatLng target, float zoom) {
            return new GoogleCameraPosition(target, zoom);
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
        public Map.Style.Options newMapStyleOptions(String json) {
            return new GoogleMap.Style.Options(json);
        }

        @NonNull
        @Override
        public Map.Style.Options newMapStyleOptions(@NonNull Context context, @RawRes int resourceId) {
            return new GoogleMap.Style.Options(context, resourceId);
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


        @Override
        public void getMapAsync(
                @NonNull Fragment fragment,
                @NonNull final OnMapReadyCallback callback) {
            ((com.google.android.gms.maps.SupportMapFragment) fragment)
                    .getMapAsync(new com.google.android.gms.maps.OnMapReadyCallback() {
                        @Override
                        public void onMapReady(com.google.android.gms.maps.GoogleMap googleMap) {
                            callback.onMapReady(new GoogleMap(googleMap));
                        }
                    });
        }
    }

}
