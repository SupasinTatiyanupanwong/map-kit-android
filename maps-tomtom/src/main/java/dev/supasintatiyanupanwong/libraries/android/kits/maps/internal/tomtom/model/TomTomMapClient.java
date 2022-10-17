/*
 * Copyright 2022 Supasin Tatiyanupanwong
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

package dev.supasintatiyanupanwong.libraries.android.kits.maps.internal.tomtom.model;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static androidx.annotation.RestrictTo.Scope.LIBRARY;

import static dev.supasintatiyanupanwong.libraries.android.kits.maps.model.MapClient.OnCameraMoveStartedListener.REASON_API_ANIMATION;
import static dev.supasintatiyanupanwong.libraries.android.kits.maps.model.MapClient.OnCameraMoveStartedListener.REASON_DEVELOPER_ANIMATION;
import static dev.supasintatiyanupanwong.libraries.android.kits.maps.model.MapClient.OnCameraMoveStartedListener.REASON_GESTURE;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.location.Location;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresPermission;
import androidx.annotation.RestrictTo;
import androidx.core.view.ViewCompat;

import com.tomtom.sdk.maps.display.TomTomMap;
import com.tomtom.sdk.maps.display.camera.AnimationDuration;
import com.tomtom.sdk.maps.display.common.screen.Padding;
import com.tomtom.sdk.maps.display.gesture.OnMapPanningListener;
import com.tomtom.sdk.maps.display.location.LocationMarkerOptions;
import com.tomtom.sdk.maps.display.location.LocationMarkerType;
import com.tomtom.sdk.maps.display.style.LoadingStyleFailure;
import com.tomtom.sdk.maps.display.style.OnStyleLoadedCallback;
import com.tomtom.sdk.maps.display.style.StandardStyles;
import com.tomtom.sdk.maps.display.ui.MapView;
import com.tomtom.sdk.maps.display.ui.zoom.DefaultZoomControlsView;
import com.tomtom.sdk.maps.display.ui.zoom.ZoomControlsView;

import dev.supasintatiyanupanwong.libraries.android.kits.maps.internal.nop.model.NopGroundOverlay;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.internal.nop.model.NopTileOverlay;
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
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.time.DurationKt;
import kotlin.time.DurationUnit;

@SuppressLint("UnsafeOptInUsageError")
@RestrictTo(LIBRARY)
public class TomTomMapClient implements MapClient {

    private final @NonNull MapView mView;
    private final @NonNull TomTomMap mMap;

    private final @NonNull UiSettings mSettings;
    private final @NonNull Projection mProjection;

    private int mMapType = MAP_TYPE_NORMAL;

    private boolean mTrafficEnabled = false;
    private boolean mMyLocationEnabled = false;

    private @Nullable MapClient.OnCameraMoveStartedListener mCameraMoveStartedListener;
    private @Nullable MapClient.OnCameraMoveListener mCameraMoveListener;
    private @Nullable MapClient.OnCameraMoveCanceledListener mCameraMoveCanceledListener;
    private @Nullable MapClient.OnCameraIdleListener mCameraIdleListener;

    private @Nullable com.tomtom.sdk.maps.display.gesture.OnMapClickListener
            mMapClickListener;
    private @Nullable com.tomtom.sdk.maps.display.gesture.OnMapLongClickListener
            mMapLongClickListener;

    private @Nullable com.tomtom.sdk.maps.display.ui.OnUiComponentClickListener
            mMyLocationButtonClickListener;
    private @Nullable com.tomtom.sdk.maps.display.location.OnLocationMarkerClickListener
            mMyLocationClickListener;

    private @Nullable com.tomtom.sdk.maps.display.marker.OnMarkerClickListener
            mMarkerClickListener;
    private @Nullable com.tomtom.sdk.maps.display.circle.OnCircleClickListener
            mCircleClickListener;
    private @Nullable com.tomtom.sdk.maps.display.polygon.OnPolygonClickListener
            mPolygonClickListener;
    private @Nullable com.tomtom.sdk.maps.display.polyline.OnPolylineClickListener
            mPolylineClickListener;

    @SuppressWarnings("KotlinInternalInJava")
    public TomTomMapClient(@NonNull MapView view, @NonNull TomTomMap map) {
        mView = view;
        mMap = map;

        mSettings = new UiSettings(view, map);
        mProjection = new TomTomProjection(map);

        view.getCompassButton().addOnCompassButtonClickListener(() -> {
            final @Nullable MapClient.OnCameraMoveStartedListener listener =
                    mCameraMoveStartedListener;
            if (listener != null) {
                listener.onCameraMoveStarted(REASON_API_ANIMATION);
            }
        });

        final @NonNull ZoomControlsView zoomControlsView = view.getZoomControlsView();
        if (zoomControlsView instanceof DefaultZoomControlsView) {
            final @NonNull DefaultZoomControlsView defaultZoomControlsView =
                    (DefaultZoomControlsView) view.getZoomControlsView();
            defaultZoomControlsView.setOnZoomInButtonClickListener(new Function0<Unit>() {
                @Override
                public Unit invoke() {
                    final @Nullable MapClient.OnCameraMoveStartedListener listener =
                            mCameraMoveStartedListener;
                    if (listener != null) {
                        listener.onCameraMoveStarted(REASON_API_ANIMATION);
                    }

                    DefaultZoomControlsView.a(this, defaultZoomControlsView.a.c);
                    return null;
                }
            });
            defaultZoomControlsView.setOnZoomOutButtonClickListener(new Function0<Unit>() {
                @Override
                public Unit invoke() {
                    final @Nullable MapClient.OnCameraMoveStartedListener listener =
                            mCameraMoveStartedListener;
                    if (listener != null) {
                        listener.onCameraMoveStarted(REASON_API_ANIMATION);
                    }

                    DefaultZoomControlsView.a(this, defaultZoomControlsView.a.b);
                    return null;
                }
            });
        }

        view.getCurrentLocationButton().addOnCurrentLocationButtonClickListener(() -> {
            final @Nullable MapClient.OnCameraMoveStartedListener listener =
                    mCameraMoveStartedListener;
            if (listener != null) {
                listener.onCameraMoveStarted(REASON_API_ANIMATION);
            }
        });

        map.addOnMapPanningListener(new OnMapPanningListener() {
            @Override public void onMapPanningStarted() {
                final @Nullable MapClient.OnCameraMoveStartedListener listener =
                        mCameraMoveStartedListener;
                if (listener != null) {
                    listener.onCameraMoveStarted(REASON_GESTURE);
                }
            }

            @Override public void onMapPanningOngoing() {
                final @Nullable MapClient.OnCameraMoveListener listener = mCameraMoveListener;
                if (listener != null) {
                    listener.onCameraMove();
                }
            }

            @Override public void onMapPanningEnded() {
                final @Nullable MapClient.OnCameraIdleListener listener = mCameraIdleListener;
                if (listener != null) {
                    listener.onCameraIdle();
                }
            }
        });

        map.addOnCameraChangeListener(() -> {
            final @Nullable MapClient.OnCameraMoveListener listener = mCameraMoveListener;
            if (listener != null) {
                listener.onCameraMove();
            }
        });

        map.addOnCameraSteadyListener(() -> {
            final @Nullable MapClient.OnCameraIdleListener listener = mCameraIdleListener;
            if (listener != null) {
                listener.onCameraIdle();
            }
        });

    }

    @Override public @NonNull CameraPosition getCameraPosition() {
        return TomTomCameraPosition.wrap(mMap.cameraPosition());
    }

    @Override public float getMaxZoomLevel() {
        return (float) mMap.getMaxZoom();
    }

    @Override public float getMinZoomLevel() {
        return (float) mMap.getMinZoom();
    }

    @Override public void moveCamera(@NonNull CameraUpdate update) {
        final @Nullable MapClient.OnCameraMoveStartedListener cameraMoveStartedListener =
                mCameraMoveStartedListener;
        if (cameraMoveStartedListener != null) {
            cameraMoveStartedListener.onCameraMoveStarted(REASON_DEVELOPER_ANIMATION);
        }

        mMap.moveCamera(TomTomCameraUpdate.unwrap(update));
    }

    @Override public void animateCamera(@NonNull CameraUpdate update) {
        animateCamera(update, null);
    }

    @Override public void animateCamera(
            @NonNull CameraUpdate update,
            final @Nullable MapClient.CancelableCallback callback
    ) {
        final @Nullable MapClient.OnCameraMoveStartedListener cameraMoveStartedListener =
                mCameraMoveStartedListener;
        if (cameraMoveStartedListener != null) {
            cameraMoveStartedListener.onCameraMoveStarted(REASON_DEVELOPER_ANIMATION);
        }

        mMap.animateCamera(
                TomTomCameraUpdate.unwrap(update),
                AnimationDuration.Companion.createDefault(),
                new com.tomtom.sdk.maps.display.common.OnCancellableActionCallback() {
                    @Override public void onComplete() {
                        if (callback != null) {
                            callback.onFinish();
                        }
                    }

                    @Override public void onCancelled() {
                        final @Nullable MapClient.OnCameraMoveCanceledListener canceledListener =
                                mCameraMoveCanceledListener;
                        if (canceledListener != null) {
                            canceledListener.onCameraMoveCanceled();
                        }

                        if (callback != null) {
                            callback.onCancel();
                        }
                    }
                }
        );
    }

    @Override public void animateCamera(
            @NonNull CameraUpdate update,
            @IntRange(from = 0) int durationMs,
            final @Nullable MapClient.CancelableCallback callback
    ) {
        final @Nullable MapClient.OnCameraMoveStartedListener cameraMoveStartedListener =
                mCameraMoveStartedListener;
        if (cameraMoveStartedListener != null) {
            cameraMoveStartedListener.onCameraMoveStarted(REASON_DEVELOPER_ANIMATION);
        }

        mMap.animateCamera(
                TomTomCameraUpdate.unwrap(update),
                new AnimationDuration(DurationKt.toDuration(durationMs, DurationUnit.MILLISECONDS)),
                new com.tomtom.sdk.maps.display.common.OnCancellableActionCallback() {
                    @Override public void onComplete() {
                        if (callback != null) {
                            callback.onFinish();
                        }
                    }

                    @Override public void onCancelled() {
                        final @Nullable MapClient.OnCameraMoveCanceledListener canceledListener =
                                mCameraMoveCanceledListener;
                        if (canceledListener != null) {
                            canceledListener.onCameraMoveCanceled();
                        }

                        if (callback != null) {
                            callback.onCancel();
                        }
                    }
                }
        );
    }

    @Override public void stopAnimation() {
        // No-op on TomTom Map
    }

    @Override public @Nullable Polyline addPolyline(@NonNull Polyline.Options options) {
        return TomTomPolyline.wrap(
                mMap.addPolyline(TomTomPolyline.Options.unwrap(options)),
                options.isClickable()
        );
    }

    @Override public @Nullable Polygon addPolygon(@NonNull Polygon.Options options) {
        return TomTomPolygon.wrap(
                mMap.addPolygon(TomTomPolygon.Options.unwrap(options)),
                options.isClickable()
        );
    }

    @Override public @Nullable Circle addCircle(@NonNull Circle.Options options) {
        return TomTomCircle.wrap(
                mMap.addCircle(TomTomCircle.Options.unwrap(options)),
                options.isClickable()
        );
    }

    @Override public @Nullable Marker addMarker(@NonNull Marker.Options options) {
        return TomTomMarker.wrap(mMap.addMarker(TomTomMarker.Options.unwrap(options)));
    }

    @Override public @Nullable GroundOverlay addGroundOverlay(
            @NonNull GroundOverlay.Options options
    ) {
        return NopGroundOverlay.NULL;
    }

    @Override public @Nullable TileOverlay addTileOverlay(@NonNull TileOverlay.Options options) {
        return NopTileOverlay.NULL;
    }

    @Override public void clear() {
        mMap.clear();
    }

    @Override public @Nullable IndoorBuilding getFocusedBuilding() {
        return null; // No-op on TomTom Map
    }

    @Override public void setOnIndoorStateChangeListener(
            final @Nullable MapClient.OnIndoorStateChangeListener listener
    ) {
        // No-op on TomTom Map
    }

    @Override public int getMapType() {
        return mMapType;
    }

    @Override public void setMapType(int type) {
        mMapType = type;
        mMap.loadStyle(
                type == MAP_TYPE_SATELLITE || type == MAP_TYPE_HYBRID
                        ? StandardStyles.INSTANCE.getSATELLITE()
                        : StandardStyles.INSTANCE.getBROWSING(),
                new OnStyleLoadedCallback() {
                    @Override
                    public void onSuccess() {}

                    @Override
                    public void onError(@NonNull LoadingStyleFailure failure) {}
                }
        );
    }

    @Override public boolean isTrafficEnabled() {
        return mTrafficEnabled;
    }

    @Override public void setTrafficEnabled(boolean enabled) {
        mTrafficEnabled = enabled;
        if (enabled) {
            mMap.showTrafficFlow();
            mMap.showTrafficIncidents();
        } else {
            mMap.hideTrafficFlow();
            mMap.hideTrafficIncidents();
        }
    }

    @Override public boolean isIndoorEnabled() {
        return false; // No-op on TomTom Map
    }

    @Override public boolean setIndoorEnabled(boolean enabled) {
        return false; // No-op on TomTom Map
    }

    @Override public boolean isBuildingsEnabled() {
        return false; // No-op on TomTom Map
    }

    @Override public void setBuildingsEnabled(boolean enabled) {
        // No-op on TomTom Map
    }

    @Override public boolean isMyLocationEnabled() {
        return mMyLocationEnabled;
    }

    @RequiresPermission(anyOf = {ACCESS_COARSE_LOCATION, ACCESS_FINE_LOCATION})
    @Override public void setMyLocationEnabled(boolean enabled) {
        mMyLocationEnabled = enabled;
        if (enabled) {
            mMap.enableLocationMarker(
                    new LocationMarkerOptions(LocationMarkerType.POINTER, 1.0, null, null));
        } else {
            mMap.disableLocationMarker();
        }
    }

    @Override public void setLocationSource(final @Nullable LocationSource source) {
//        mDelegate.setLocationEngine(source == null TODO
//                ? null
//                : new com.huawei.hms.maps.LocationSource() {
//            @Override public void activate(final OnLocationChangedListener listener) {
//                source.activate(new LocationSource.OnLocationChangedListener() {
//                    @Override public void onLocationChanged(@NonNull Location location) {
//                        listener.onLocationChanged(location);
//                    }
//                });
//            }
//
//            @Override public void deactivate() {
//                source.deactivate();
//            }
//        });
    }

    @Override public @NonNull MapClient.UiSettings getUiSettings() {
        return mSettings;
    }

    @Override public @NonNull Projection getProjection() {
        return mProjection;
    }

    @Override public void setOnCameraMoveStartedListener(
            final @Nullable MapClient.OnCameraMoveStartedListener listener
    ) {
        mCameraMoveStartedListener = listener;
    }

    @Override public void setOnCameraMoveListener(
            final @Nullable MapClient.OnCameraMoveListener listener
    ) {
        mCameraMoveListener = listener;
    }

    @Override public void setOnCameraMoveCanceledListener(
            final @Nullable MapClient.OnCameraMoveCanceledListener listener
    ) {
        mCameraMoveCanceledListener = listener;
    }

    @Override public void setOnCameraIdleListener(
            final @Nullable MapClient.OnCameraIdleListener listener
    ) {
        mCameraIdleListener = listener;
    }

    @Override public void setOnMapClickListener(
            final @Nullable MapClient.OnMapClickListener listener
    ) {
        if (listener == null) {
            final @Nullable com.tomtom.sdk.maps.display.gesture.OnMapClickListener
                    previous = mMapClickListener;
            if (previous != null) {
                mMap.removeOnMapClickListener(previous);
            }
        } else {
            mMapClickListener = coordinate -> {
                listener.onMapClick(TomTomLatLng.wrap(coordinate));
                return true;
            };
            mMap.addOnMapClickListener(mMapClickListener);
        }
    }

    @Override public void setOnMapLongClickListener(
            final @Nullable MapClient.OnMapLongClickListener listener
    ) {
        if (listener == null) {
            final @Nullable com.tomtom.sdk.maps.display.gesture.OnMapLongClickListener
                    previous = mMapLongClickListener;
            if (previous != null) {
                mMap.removeOnMapLongClickListener(previous);
            }
        } else {
            mMapLongClickListener = coordinate -> {
                listener.onMapLongClick(TomTomLatLng.wrap(coordinate));
                return true;
            };
            mMap.addOnMapLongClickListener(mMapLongClickListener);
        }
    }

    @Override public void setOnMarkerClickListener(
            final @Nullable MapClient.OnMarkerClickListener listener
    ) {
        if (listener == null) {
            final @Nullable com.tomtom.sdk.maps.display.marker.OnMarkerClickListener
                    previous = mMarkerClickListener;
            if (previous != null) {
                mMap.removeOnMarkerClickListener(previous);
            }
        } else {
            mMarkerClickListener = marker -> listener.onMarkerClick(TomTomMarker.wrap(marker));
            mMap.addOnMarkerClickListener(mMarkerClickListener);
        }
    }

    @Override public void setOnMarkerDragListener(
            final @Nullable MapClient.OnMarkerDragListener listener
    ) {
        // No-op on TomTom Map
    }

    @Override public void setOnInfoWindowClickListener(
            final @Nullable MapClient.OnInfoWindowClickListener listener
    ) {
        // No-op on TomTom Map
    }

    @Override public void setOnInfoWindowLongClickListener(
            final @Nullable MapClient.OnInfoWindowLongClickListener listener
    ) {
        // No-op on TomTom Map
    }

    @Override public void setOnInfoWindowCloseListener(
            final @Nullable MapClient.OnInfoWindowCloseListener listener
    ) {
        // No-op on TomTom Map
    }

    @Override public void setInfoWindowAdapter(
            final @Nullable MapClient.InfoWindowAdapter adapter
    ) {
//        mView.setMarkerBalloonViewAdapter(adapter == null TODO
//                ? new TextBalloonViewAdapter()
//                : (BalloonViewAdapter) marker -> null
//        );
    }

    @Override public void setOnMyLocationButtonClickListener(
            final @Nullable MapClient.OnMyLocationButtonClickListener listener
    ) {
        if (listener == null) {
            final @Nullable com.tomtom.sdk.maps.display.ui.OnUiComponentClickListener
                    previous = mMyLocationButtonClickListener;
            if (previous != null) {
                mView.getCurrentLocationButton()
                        .removeOnCurrentLocationButtonClickListener(previous);
            }
        } else {
            mMyLocationButtonClickListener = listener::onMyLocationButtonClick;
            mView.getCurrentLocationButton()
                    .addOnCurrentLocationButtonClickListener(mMyLocationButtonClickListener);
        }
    }

    @Override public void setOnMyLocationClickListener(
            final @Nullable MapClient.OnMyLocationClickListener listener
    ) {
        if (listener == null) {
            final @Nullable com.tomtom.sdk.maps.display.location.OnLocationMarkerClickListener
                    previous = mMyLocationClickListener;
            if (previous != null) {
                mMap.removeOnLocationMarkerClickListener(previous);
            }
        } else {
            mMyLocationClickListener = (point, coordinate) -> {
                final @NonNull Location location = new Location((String) null);
                location.setLatitude(coordinate.getLatitude());
                location.setLongitude(coordinate.getLongitude());
                listener.onMyLocationClick(location);
            };
            mMap.addOnLocationMarkerClickListener(mMyLocationClickListener);
        }
    }

    @Override public void setOnMapLoadedCallback(
            final @Nullable MapClient.OnMapLoadedCallback callback
    ) {
        // No-op on TomTom Map
    }

    @Override public void setOnGroundOverlayClickListener(
            final @Nullable MapClient.OnGroundOverlayClickListener listener
    ) {
        // No-op on TomTom Map
    }

    @Override public void setOnCircleClickListener(
            final @Nullable MapClient.OnCircleClickListener listener
    ) {
        if (listener == null) {
            final @Nullable com.tomtom.sdk.maps.display.circle.OnCircleClickListener
                    previous = mCircleClickListener;
            if (previous != null) {
                mMap.removeOnCircleClickListener(previous);
            }
        } else {
            mCircleClickListener = circle ->
                    listener.onCircleClick(TomTomCircle.wrap(circle, true));
            mMap.addOnCircleClickListener(mCircleClickListener);
        }
    }

    @Override public void setOnPolygonClickListener(
            final @Nullable MapClient.OnPolygonClickListener listener
    ) {
        if (listener == null) {
            final @Nullable com.tomtom.sdk.maps.display.polygon.OnPolygonClickListener
                    previous = mPolygonClickListener;
            if (previous != null) {
                mMap.removeOnPolygonClickListener(previous);
            }
        } else {
            mPolygonClickListener = polygon ->
                    listener.onPolygonClick(TomTomPolygon.wrap(polygon, true));
            mMap.addOnPolygonClickListener(mPolygonClickListener);
        }
    }

    @Override public void setOnPolylineClickListener(
            final @Nullable MapClient.OnPolylineClickListener listener
    ) {
        if (listener == null) {
            final @Nullable com.tomtom.sdk.maps.display.polyline.OnPolylineClickListener
                    previous = mPolylineClickListener;
            if (previous != null) {
                mMap.removeOnPolylineClickListener(previous);
            }
        } else {
            mPolylineClickListener = polyline ->
                    listener.onPolylineClick(TomTomPolyline.wrap(polyline, true));
            mMap.addOnPolylineClickListener(mPolylineClickListener);
        }
    }

    @Override public void snapshot(final @NonNull MapClient.SnapshotReadyCallback callback) {
        snapshot(callback, null);
    }

    @Override public void snapshot(
            final @NonNull MapClient.SnapshotReadyCallback callback,
            @Nullable Bitmap bitmap
    ) {
        if (!ViewCompat.isLaidOut(mView)) {
            return;
        }

        final @NonNull Bitmap workingBitmap;
        if (bitmap == null
                || bitmap.getWidth() != mView.getWidth()
                || bitmap.getHeight() != mView.getHeight()
        ) {
            workingBitmap = Bitmap
                    .createBitmap(mView.getWidth(), mView.getHeight(), Bitmap.Config.ARGB_8888);
        } else {
            workingBitmap = bitmap;
        }
        final @NonNull Canvas canvas = new Canvas(workingBitmap);
        canvas.translate(-mView.getScrollX(), -mView.getScrollY());
        mView.draw(canvas);

        callback.onSnapshotReady(workingBitmap);
    }

    @Override public void setPadding(int left, int top, int right, int bottom) {
        mMap.setPadding(new Padding(left, top, right, bottom));
    }

    @Override public void setContentDescription(String description) {
        mView.setContentDescription(description);
    }

    @Override public void setOnPoiClickListener(
            final @Nullable MapClient.OnPoiClickListener listener
    ) {
        // No-op on TomTom Map
    }

    @Override public boolean setMapStyle(@Nullable MapClient.Style.Options style) {
        return false;

        // TODO
//        mMap.loadStyle(
//                style,
//                new OnStyleLoadedCallback() {
//                    @Override
//                    public void onSuccess() {
//                    }
//
//                    @Override
//                    public void onError(@NonNull LoadingStyleFailure failure) {
//                    }
//                }
//        );
    }

    @Override public void setMinZoomPreference(float minZoomPreference) {
        // No-op on TomTom Map
    }

    @Override public void setMaxZoomPreference(float maxZoomPreference) {
        // No-op on TomTom Map
    }

    @Override public void resetMinMaxZoomPreference() {
        // No-op on TomTom Map
    }

    @Override public void setLatLngBoundsForCameraTarget(@Nullable LatLngBounds bounds) {
        // No-op on TomTom Map
    }


// https://stackoverflow.com/questions/4896223/how-to-get-an-uri-of-an-image-resource-in-android
//    public static class Style implements MapClient.Style {
//        private Style() {}
//
//        public static class Options implements MapClient.Style.Options {
//            private final com.huawei.hms.maps.model.MapStyleOptions mDelegate;
//
//            public Options(String json) {
//                mDelegate = new com.huawei.hms.maps.model.MapStyleOptions(json);
//            }
//
//            public Options(@NonNull Context context, @RawRes int resourceId) {
//                mDelegate = com.huawei.hms.maps.model.MapStyleOptions
//                        .loadRawResourceStyle(context, resourceId);
//            }
//
//            @Override public boolean equals(@Nullable Object obj) {
//                if (this == obj) {
//                    return true;
//                }
//                if (obj == null || getClass() != obj.getClass()) {
//                    return false;
//                }
//
//                Options that = (Options) obj;
//
//                return mDelegate.equals(that.mDelegate);
//            }
//
//            @Override public int hashCode() {
//                return mDelegate.hashCode();
//            }
//
//            @Override public @NonNull String toString() {
//                return mDelegate.toString();
//            }
//
//
//            static @Nullable com.huawei.hms.maps.model.MapStyleOptions unwrap(
//                    @Nullable MapClient.Style.Options wrapped) {
//                return wrapped == null ? null : ((Style.Options) wrapped).mDelegate;
//            }
//        }
//    }


    static class UiSettings implements MapClient.UiSettings {
        private final @NonNull MapView mView;
        private final @NonNull TomTomMap mMap;

        UiSettings(@NonNull MapView view, @NonNull TomTomMap map) {
            mView = view;
            mMap = map;
        }

        @Override public void setZoomControlsEnabled(boolean enabled) {
            mView.getZoomControlsView().setVisible(enabled);
        }

        @Override public void setCompassEnabled(boolean enabled) {
            mView.getCompassButton().setVisibilityPolicy(enabled
                    ? com.tomtom.sdk.maps.display.ui.compass.VisibilityPolicy.VISIBLE
                    : com.tomtom.sdk.maps.display.ui.compass.VisibilityPolicy.INVISIBLE
            );
        }

        @Override public void setMyLocationButtonEnabled(boolean enabled) {
            mView.getCurrentLocationButton().setVisibilityPolicy(enabled
                    ? com.tomtom.sdk.maps.display.ui.currentlocation.VisibilityPolicy.VISIBLE
                    : com.tomtom.sdk.maps.display.ui.currentlocation.VisibilityPolicy.INVISIBLE);
        }

        @Override public void setIndoorLevelPickerEnabled(boolean enabled) {
            // No-op on TomTom Map
        }

        @Override public void setScrollGesturesEnabled(boolean enabled) {
            mMap.setScrollEnabled(enabled);
        }

        @Override public void setZoomGesturesEnabled(boolean enabled) {
            mMap.setZoomEnabled(enabled);
        }

        @Override public void setTiltGesturesEnabled(boolean enabled) {
            mMap.setTiltEnabled(enabled);
        }

        @Override public void setRotateGesturesEnabled(boolean enabled) {
            mMap.setRotationEnabled(enabled);
        }

        @Override public void setScrollGesturesEnabledDuringRotateOrZoom(boolean enabled) {
            mMap.setMultiPointerPanEnabled(enabled);
        }

        @Override public void setAllGesturesEnabled(boolean enabled) {
            setScrollGesturesEnabled(enabled);
            setZoomGesturesEnabled(enabled);
            setTiltGesturesEnabled(enabled);
            setRotateGesturesEnabled(enabled);
            setScrollGesturesEnabledDuringRotateOrZoom(enabled);
        }

        @Override public void setMapToolbarEnabled(boolean enabled) {
            // No-op on TomTom Map
        }

        @Override public boolean isZoomControlsEnabled() {
            return mView.getZoomControlsView().isVisible();
        }

        @Override public boolean isCompassEnabled() {
            return mView.getZoomControlsView().isVisible();
        }

        @Override public boolean isMyLocationButtonEnabled() {
            return mView.getZoomControlsView().isVisible();
        }

        @Override public boolean isIndoorLevelPickerEnabled() {
            return false; // No-op on TomTom Map
        }

        @Override public boolean isScrollGesturesEnabled() {
            return mMap.isScrollEnabled();
        }

        @Override public boolean isScrollGesturesEnabledDuringRotateOrZoom() {
            return mMap.isMultiPointerPanEnabled();
        }

        @Override public boolean isZoomGesturesEnabled() {
            return mMap.isZoomEnabled();
        }

        @Override public boolean isTiltGesturesEnabled() {
            return mMap.isTiltEnabled();
        }

        @Override public boolean isRotateGesturesEnabled() {
            return mMap.isRotationEnabled();
        }

        @Override public boolean isMapToolbarEnabled() {
            return false; // No-op on TomTom Map
        }
    }

}
