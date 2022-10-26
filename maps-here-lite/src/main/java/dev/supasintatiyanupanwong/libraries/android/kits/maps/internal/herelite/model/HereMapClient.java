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

package dev.supasintatiyanupanwong.libraries.android.kits.maps.internal.herelite.model;

import static androidx.annotation.RestrictTo.Scope.LIBRARY;

import static dev.supasintatiyanupanwong.libraries.android.kits.maps.model.MapClient.OnCameraMoveStartedListener.REASON_DEVELOPER_ANIMATION;
import static dev.supasintatiyanupanwong.libraries.android.kits.maps.model.MapClient.OnCameraMoveStartedListener.REASON_GESTURE;

import android.graphics.Bitmap;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;

import com.here.sdk.gestures.GestureType;
import com.here.sdk.mapviewlite.CameraLimits;
import com.here.sdk.mapviewlite.LayerState;
import com.here.sdk.mapviewlite.MapLayer;
import com.here.sdk.mapviewlite.MapScene;
import com.here.sdk.mapviewlite.MapViewLite;

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

@RestrictTo(LIBRARY)
public class HereMapClient implements MapClient {

    private final @NonNull MapViewLite mMapView;
    private final @NonNull UiSettings mSettings;

    private boolean mTrafficEnabled = false;

    private int mType = MAP_TYPE_NORMAL;

    private @Nullable OnCameraMoveStartedListener mCameraMoveStartedListener;
    private @Nullable OnCameraMoveListener mCameraMoveListener;
    private @Nullable OnCameraMoveCanceledListener mCameraMoveCanceledListener;
    private @Nullable OnCameraIdleListener mCameraIdleListener;

    public HereMapClient(@NonNull MapViewLite mapView) {
        mMapView = mapView;
        mSettings = new UiSettings(mapView);

        mapView.getCamera().addObserver(update -> {
            final @Nullable OnCameraMoveListener cameraMoveListener = mCameraMoveListener;
            if (cameraMoveListener != null) {
                cameraMoveListener.onCameraMove();
            }
        });

        mapView.getGestures().setPanListener((state, origin, translation, velocity) -> {
            switch (state) {
                case BEGIN:
                    final @Nullable OnCameraMoveStartedListener cameraMoveStartedListener =
                            mCameraMoveStartedListener;
                    if (cameraMoveStartedListener != null) {
                        cameraMoveStartedListener.onCameraMoveStarted(REASON_GESTURE);
                    }
                    break;
                case UPDATE:
                    final @Nullable OnCameraMoveListener cameraMoveListener = mCameraMoveListener;
                    if (cameraMoveListener != null) {
                        cameraMoveListener.onCameraMove();
                    }
                    break;
                case END:
                    final @Nullable OnCameraIdleListener cameraIdleListener = mCameraIdleListener;
                    if (cameraIdleListener != null) {
                        cameraIdleListener.onCameraIdle();
                    }
                    break;
                case CANCEL:
                    final @Nullable OnCameraMoveCanceledListener cameraMoveCanceledListener =
                            mCameraMoveCanceledListener;
                    if (cameraMoveCanceledListener != null) {
                        cameraMoveCanceledListener.onCameraMoveCanceled();
                    }
                    break;
            }
        });
    }

    @Override public @NonNull CameraPosition getCameraPosition() {
        return HereCameraPosition.wrap(mMapView.getCamera());
    }

    @Override public float getMaxZoomLevel() {
        return (float) mMapView.getCamera().getLimits().getMaxZoomLevel();
    }

    @Override public float getMinZoomLevel() {
        return (float) mMapView.getCamera().getLimits().getMinZoomLevel();
    }

    @Override public void moveCamera(@NonNull CameraUpdate update) {
        final @Nullable OnCameraMoveStartedListener cameraMoveStartedListener =
                mCameraMoveStartedListener;
        if (cameraMoveStartedListener != null) {
            cameraMoveStartedListener.onCameraMoveStarted(REASON_DEVELOPER_ANIMATION);
        }

        if (HereCameraUpdate.handle(mMapView.getCamera(), update)) {
            final @Nullable OnCameraIdleListener cameraIdleListener = mCameraIdleListener;
            if (cameraIdleListener != null) {
                cameraIdleListener.onCameraIdle();
            }
        }
    }

    @Override public void animateCamera(@NonNull CameraUpdate update) {
        moveCamera(update);
    }

    @Override public void animateCamera(
            @NonNull CameraUpdate update,
            final @Nullable CancelableCallback callback
    ) {
        moveCamera(update);
    }

    @Override public void animateCamera(
            @NonNull CameraUpdate update,
            int durationMs,
            final @Nullable CancelableCallback callback
    ) {
        moveCamera(update);
    }

    @Override public void stopAnimation() {
        // No-op on HERE Maps (Lite Edition)
    }

    @Override public @Nullable Polyline addPolyline(final @NonNull Polyline.Options options) {
        return null;
    }

    @Override public @Nullable Polygon addPolygon(final @NonNull Polygon.Options options) {
        return null;
    }

    @Override public @Nullable Circle addCircle(final @NonNull Circle.Options options) {
        return null;
    }

    @Override public @Nullable Marker addMarker(final @NonNull Marker.Options options) {
        return null;
    }

    @Override public @Nullable GroundOverlay addGroundOverlay(
            final @NonNull GroundOverlay.Options options
    ) {
        return null;
    }

    @Override public @Nullable TileOverlay addTileOverlay(
            final @NonNull TileOverlay.Options options
    ) {
        return null;
    }

    @Override public void clear() {

    }

    @Override public @Nullable IndoorBuilding getFocusedBuilding() {
        return null; // No-op on HERE Maps (Lite Edition)
    }

    @Override public void setOnIndoorStateChangeListener(
            final @Nullable OnIndoorStateChangeListener listener
    ) {
        // No-op on HERE Maps (Lite Edition)
    }

    @Override public int getMapType() {
        return mType;
    }

    @Override public void setMapType(int type) {
        mType = type;
        mMapView.getMapScene().loadScene(HereMapType.unwrap(type), null);
    }

    @Override public boolean isTrafficEnabled() {
        return mTrafficEnabled;
    }

    @Override public void setTrafficEnabled(boolean enabled) {
        mTrafficEnabled = enabled;
        try {
            final @NonNull LayerState state = enabled ? LayerState.ENABLED : LayerState.DISABLED;
            mMapView.getMapScene().setLayerState(MapLayer.TRAFFIC_FLOW, state);
            mMapView.getMapScene().setLayerState(MapLayer.TRAFFIC_INCIDENTS, state);
        } catch (MapScene.MapSceneException ignored) {
        }
    }

    @Override public boolean isIndoorEnabled() {
        return false; // No-op on HERE Maps (Lite Edition)
    }

    @Override public boolean setIndoorEnabled(boolean enabled) {
        return false; // No-op on HERE Maps (Lite Edition)
    }

    @Override public boolean isBuildingsEnabled() {
        return false; // No-op on HERE Maps (Lite Edition)
    }

    @Override public void setBuildingsEnabled(boolean enabled) {
        // No-op on HERE Maps (Lite Edition)
    }

    @Override public boolean isMyLocationEnabled() {
        return false; // No-op on HERE Maps (Lite Edition)
    }

    @Override public void setMyLocationEnabled(boolean enabled) {
        // No-op on HERE Maps (Lite Edition)
    }

    @Override public void setLocationSource(@Nullable LocationSource source) {
        // No-op on HERE Maps (Lite Edition)
    }

    @Override public @NonNull UiSettings getUiSettings() {
        return mSettings;
    }

    @Override public @NonNull Projection getProjection() {
        return HereProjection.wrap(mMapView.getCamera());
    }

    @Override public void setOnCameraMoveStartedListener(
            final @Nullable OnCameraMoveStartedListener listener
    ) {
        mCameraMoveStartedListener = listener;
    }

    @Override public void setOnCameraMoveListener(
            final @Nullable OnCameraMoveListener listener
    ) {
        mCameraMoveListener = listener;
    }

    @Override public void setOnCameraMoveCanceledListener(
            final @Nullable OnCameraMoveCanceledListener listener
    ) {
        mCameraMoveCanceledListener = listener;
    }

    @Override public void setOnCameraIdleListener(
            final @Nullable OnCameraIdleListener listener
    ) {
        mCameraIdleListener = listener;
    }

    @Override public void setOnMapClickListener(
            final @Nullable OnMapClickListener listener
    ) {

    }

    @Override public void setOnMapLongClickListener(
            final @Nullable OnMapLongClickListener listener
    ) {

    }

    @Override public void setOnMarkerClickListener(
            final @Nullable OnMarkerClickListener listener
    ) {

    }

    @Override public void setOnMarkerDragListener(
            final @Nullable OnMarkerDragListener listener
    ) {

    }

    @Override public void setOnInfoWindowClickListener(
            final @Nullable OnInfoWindowClickListener listener
    ) {

    }

    @Override public void setOnInfoWindowLongClickListener(
            final @Nullable OnInfoWindowLongClickListener listener
    ) {

    }

    @Override public void setOnInfoWindowCloseListener(
            final @Nullable OnInfoWindowCloseListener listener
    ) {

    }

    @Override public void setInfoWindowAdapter(
            final @Nullable InfoWindowAdapter adapter
    ) {

    }

    @Override public void setOnMyLocationButtonClickListener(
            final @Nullable OnMyLocationButtonClickListener listener
    ) {
        // No-op on HERE Maps (Lite Edition)
    }

    @Override public void setOnMyLocationClickListener(
            final @Nullable OnMyLocationClickListener listener
    ) {
        // No-op on HERE Maps (Lite Edition)
    }

    @Override public void setOnMapLoadedCallback(
            final @Nullable OnMapLoadedCallback callback
    ) {

    }

    @Override public void setOnGroundOverlayClickListener(
            final @Nullable OnGroundOverlayClickListener listener
    ) {

    }

    @Override public void setOnCircleClickListener(
            final @Nullable OnCircleClickListener listener
    ) {

    }

    @Override public void setOnPolygonClickListener(
            final @Nullable OnPolygonClickListener listener
    ) {

    }

    @Override public void setOnPolylineClickListener(
            final @Nullable OnPolylineClickListener listener
    ) {

    }

    @Override public void snapshot(
            final @NonNull SnapshotReadyCallback callback
    ) {
        mMapView.captureScreenshot(callback::onSnapshotReady);
    }

    @Override public void snapshot(
            final @NonNull SnapshotReadyCallback callback,
            final @Nullable Bitmap bitmap
    ) {
        mMapView.captureScreenshot(callback::onSnapshotReady);
    }

    @Override public void setPadding(int left, int top, int right, int bottom) {
        // No-op on HERE Maps (Lite Edition)
    }

    @Override public void setContentDescription(String description) {
        mMapView.setContentDescription(description);
    }

    @Override public void setOnPoiClickListener(final @Nullable OnPoiClickListener listener) {

    }

    @Override public boolean setMapStyle(@Nullable Style.Options style) {
        if (style == null) {
            setMapType(mType);
            return true;
        }

        // TODO load custom style here

        return false;
    }

    @Override public void setMinZoomPreference(float minZoomPreference) {
        try {
            mMapView.getCamera().getLimits()
                    .setMinZoomLevel(Math.max(minZoomPreference, CameraLimits.MIN_ZOOM_LEVEL));
        } catch (CameraLimits.CameraLimitsException ignored) {
        }
    }

    @Override public void setMaxZoomPreference(float maxZoomPreference) {
        try {
            mMapView.getCamera().getLimits()
                    .setMaxZoomLevel(Math.min(maxZoomPreference, CameraLimits.MAX_ZOOM_LEVEL));
        } catch (CameraLimits.CameraLimitsException ignored) {
        }
    }

    @Override public void resetMinMaxZoomPreference() {
        try {
            mMapView.getCamera().getLimits().setMinZoomLevel(CameraLimits.MIN_ZOOM_LEVEL);
            mMapView.getCamera().getLimits().setMaxZoomLevel(CameraLimits.MAX_ZOOM_LEVEL);
        } catch (CameraLimits.CameraLimitsException ignored) {
        }
    }

    @Override public void setLatLngBoundsForCameraTarget(@Nullable LatLngBounds bounds) {
        mMapView.getCamera().getLimits().setTargetArea(HereLatLngBounds.unwrap(bounds));
    }


    static class UiSettings implements MapClient.UiSettings {
        private final @NonNull MapViewLite mMapView;

        private boolean mScrollGesturesEnabled = true;
        private boolean mZoomGesturesEnabled = true;
        private boolean mTiltGesturesEnabled = true;
        private boolean mRotateGesturesEnabled = true;

        public UiSettings(@NonNull MapViewLite mapView) {
            mMapView = mapView;
        }

        @Override public void setZoomControlsEnabled(boolean enabled) {
            // No-op on HERE Maps (Lite Edition)
        }

        @Override public void setCompassEnabled(boolean enabled) {
            // No-op on HERE Maps (Lite Edition)
        }

        @Override public void setMyLocationButtonEnabled(boolean enabled) {
            // No-op on HERE Maps (Lite Edition)
        }

        @Override public void setIndoorLevelPickerEnabled(boolean enabled) {
            // No-op on HERE Maps (Lite Edition)
        }

        @Override public void setScrollGesturesEnabled(boolean enabled) {
            mScrollGesturesEnabled = enabled;
            if (enabled) {
                mMapView.getGestures().enableDefaultAction(GestureType.PAN);
            } else {
                mMapView.getGestures().disableDefaultAction(GestureType.PAN);
            }
        }

        @Override public void setZoomGesturesEnabled(boolean enabled) {
            mZoomGesturesEnabled = enabled;
            mRotateGesturesEnabled = enabled;
            if (enabled) {
                mMapView.getGestures().enableDefaultAction(GestureType.DOUBLE_TAP);
                mMapView.getGestures().enableDefaultAction(GestureType.TWO_FINGER_TAP);
            } else {
                mMapView.getGestures().disableDefaultAction(GestureType.DOUBLE_TAP);
                mMapView.getGestures().disableDefaultAction(GestureType.TWO_FINGER_TAP);
                mMapView.getGestures().disableDefaultAction(GestureType.PINCH_ROTATE);
            }
        }

        @Override public void setTiltGesturesEnabled(boolean enabled) {
            mTiltGesturesEnabled = enabled;
            if (enabled) {
                mMapView.getGestures().enableDefaultAction(GestureType.TWO_FINGER_PAN);
            } else {
                mMapView.getGestures().disableDefaultAction(GestureType.TWO_FINGER_PAN);
            }
        }

        @Override public void setRotateGesturesEnabled(boolean enabled) {
            mRotateGesturesEnabled = enabled;
            if (enabled) {
                mMapView.getGestures().enableDefaultAction(GestureType.PINCH_ROTATE);
            } else {
                mMapView.getGestures().disableDefaultAction(GestureType.PINCH_ROTATE);
            }
        }

        @Override public void setScrollGesturesEnabledDuringRotateOrZoom(boolean enabled) {
            // No-op on HERE Maps (Lite Edition)
        }

        @Override public void setAllGesturesEnabled(boolean enabled) {
            setScrollGesturesEnabled(enabled);
            setZoomGesturesEnabled(enabled);
            setTiltGesturesEnabled(enabled);
            setRotateGesturesEnabled(enabled);
            setScrollGesturesEnabledDuringRotateOrZoom(enabled);
        }

        @Override public void setMapToolbarEnabled(boolean enabled) {
            // No-op on HERE Maps (Lite Edition)
        }

        @Override public boolean isZoomControlsEnabled() {
            return false; // No-op on HERE Maps (Lite Edition)
        }

        @Override public boolean isCompassEnabled() {
            return false; // No-op on HERE Maps (Lite Edition)
        }

        @Override public boolean isMyLocationButtonEnabled() {
            return false; // No-op on HERE Maps (Lite Edition)
        }

        @Override public boolean isIndoorLevelPickerEnabled() {
            return false; // No-op on HERE Maps (Lite Edition)
        }

        @Override public boolean isScrollGesturesEnabled() {
            return mScrollGesturesEnabled;
        }

        @Override public boolean isScrollGesturesEnabledDuringRotateOrZoom() {
            return false; // No-op on HERE Maps (Lite Edition)
        }

        @Override public boolean isZoomGesturesEnabled() {
            return mZoomGesturesEnabled;
        }

        @Override public boolean isTiltGesturesEnabled() {
            return mTiltGesturesEnabled;
        }

        @Override public boolean isRotateGesturesEnabled() {
            return mRotateGesturesEnabled;
        }

        @Override public boolean isMapToolbarEnabled() {
            return false; // No-op on HERE Maps (Lite Edition)
        }
    }

}
