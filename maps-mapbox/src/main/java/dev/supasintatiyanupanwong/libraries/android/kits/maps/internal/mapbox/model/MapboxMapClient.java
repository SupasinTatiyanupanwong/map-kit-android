/*
 * Copyright 2023 Supasin Tatiyanupanwong
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

package dev.supasintatiyanupanwong.libraries.android.kits.maps.internal.mapbox.model;

import static androidx.annotation.RestrictTo.Scope.LIBRARY;

import android.animation.Animator;
import android.graphics.Bitmap;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;

import com.mapbox.maps.CameraBoundsOptions;
import com.mapbox.maps.CoordinateBounds;
import com.mapbox.maps.EdgeInsets;
import com.mapbox.maps.ExtensionUtils;
import com.mapbox.maps.MapView;
import com.mapbox.maps.MapboxMap;
import com.mapbox.maps.plugin.Plugin;
import com.mapbox.maps.plugin.animation.CameraAnimationsPlugin;
import com.mapbox.maps.plugin.animation.MapAnimationOptions;
import com.mapbox.maps.plugin.annotation.AnnotationConfig;
import com.mapbox.maps.plugin.annotation.AnnotationPlugin;
import com.mapbox.maps.plugin.annotation.generated.CircleAnnotationManager;
import com.mapbox.maps.plugin.annotation.generated.CircleAnnotationManagerKt;
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationManager;
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationManagerKt;
import com.mapbox.maps.plugin.annotation.generated.PolygonAnnotationManager;
import com.mapbox.maps.plugin.annotation.generated.PolygonAnnotationManagerKt;
import com.mapbox.maps.plugin.annotation.generated.PolylineAnnotationManager;
import com.mapbox.maps.plugin.annotation.generated.PolylineAnnotationManagerKt;
import com.mapbox.maps.plugin.compass.CompassPlugin;
import com.mapbox.maps.plugin.gestures.GesturesPlugin;
import com.mapbox.maps.plugin.scalebar.ScaleBarPlugin;

import java.util.Collections;
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

@RestrictTo(LIBRARY)
public class MapboxMapClient implements MapClient {

    private final @NonNull MapView mMapView;
    private final @NonNull MapboxMap mMap;

    private final @NonNull UiSettings mUiSettings;

    private final @NonNull CameraAnimationsPlugin mCameraAnimationsPlugin;
    private final @NonNull GesturesPlugin mGesturesPlugin;

    private final @NonNull PolylineAnnotationManager mPolylineAnnotationManager;
    private final @NonNull PolygonAnnotationManager mPolygonAnnotationManager;
    private final @NonNull CircleAnnotationManager mCircleAnnotationManager;
    private final @NonNull PointAnnotationManager mPointAnnotationManager;

    private int mType = MAP_TYPE_NORMAL;

    private @Nullable com.mapbox.maps.plugin.gestures.OnMapClickListener
            mMapClickListener;
    private @Nullable com.mapbox.maps.plugin.gestures.OnMapLongClickListener
            mMapLongClickListener;

    public MapboxMapClient(@NonNull MapView view) {
        mMapView = view;
        mMap = view.getMapboxMap();

        mUiSettings = new UiSettings(view);

        final @Nullable ScaleBarPlugin scaleBarPlugin =
                mMapView.getPlugin(Plugin.MAPBOX_SCALEBAR_PLUGIN_ID);
        if (scaleBarPlugin != null) {
            scaleBarPlugin.setEnabled(false);
        }

        mCameraAnimationsPlugin =
                Objects.requireNonNull(mMapView.getPlugin(Plugin.MAPBOX_CAMERA_PLUGIN_ID));

        mGesturesPlugin =
                Objects.requireNonNull(mMapView.getPlugin(Plugin.MAPBOX_GESTURES_PLUGIN_ID));

        final @NonNull AnnotationPlugin annotationPlugin =
                Objects.requireNonNull(mMapView.getPlugin(Plugin.MAPBOX_ANNOTATION_PLUGIN_ID));
        mPolylineAnnotationManager = PolylineAnnotationManagerKt.createPolylineAnnotationManager(
                annotationPlugin,
                (AnnotationConfig) null
        );
        mPolygonAnnotationManager = PolygonAnnotationManagerKt.createPolygonAnnotationManager(
                annotationPlugin,
                (AnnotationConfig) null
        );
        mCircleAnnotationManager = CircleAnnotationManagerKt.createCircleAnnotationManager(
                annotationPlugin,
                (AnnotationConfig) null
        );
        mPointAnnotationManager = PointAnnotationManagerKt.createPointAnnotationManager(
                annotationPlugin,
                (AnnotationConfig) null
        );
    }

    @Override public @NonNull CameraPosition getCameraPosition() {
        return MapboxCameraPosition.wrap(mMapView.getMapboxMap().getCameraState());
    }

    @Override public float getMaxZoomLevel() {
        return (float) mMapView.getMapboxMap().getBounds().getMaxZoom();
    }

    @Override public float getMinZoomLevel() {
        return (float) mMapView.getMapboxMap().getBounds().getMinZoom();
    }

    @Override public void moveCamera(@NonNull CameraUpdate update) {
        final @NonNull MapAnimationOptions options = new MapAnimationOptions.Builder()
                .duration(0L)
                .build();

        MapboxCameraUpdate.handle(mMap, mCameraAnimationsPlugin, update, options);
    }

    @Override public void animateCamera(@NonNull CameraUpdate update) {
        MapboxCameraUpdate.handle(mMap, mCameraAnimationsPlugin, update, null);
    }

    @Override public void animateCamera(
            @NonNull CameraUpdate update,
            final @Nullable CancelableCallback callback
    ) {
        final @NonNull MapAnimationOptions.Builder options = new MapAnimationOptions.Builder();

        if (callback != null) {
            options.animatorListener(new Animator.AnimatorListener() {
                @Override public void onAnimationStart(@NonNull Animator animation) {}

                @Override public void onAnimationEnd(@NonNull Animator animation) {
                    callback.onFinish();
                }

                @Override public void onAnimationCancel(@NonNull Animator animation) {
                    callback.onCancel();
                }

                @Override
                public void onAnimationRepeat(@NonNull Animator animation) {}
            });
        }

        MapboxCameraUpdate.handle(mMap, mCameraAnimationsPlugin, update, options.build());
    }

    @Override public void animateCamera(
            @NonNull CameraUpdate update,
            int durationMs,
            final @Nullable CancelableCallback callback
    ) {
        final @NonNull MapAnimationOptions.Builder optionsBuilder =
                new MapAnimationOptions.Builder();

        if (callback != null) {
            optionsBuilder.animatorListener(new Animator.AnimatorListener() {
                @Override public void onAnimationStart(@NonNull Animator animation) {}

                @Override public void onAnimationEnd(@NonNull Animator animation) {
                    callback.onFinish();
                }

                @Override public void onAnimationCancel(@NonNull Animator animation) {
                    callback.onCancel();
                }

                @Override
                public void onAnimationRepeat(@NonNull Animator animation) {}
            });
        }

        optionsBuilder.duration(durationMs);

        final @NonNull MapAnimationOptions options = optionsBuilder.build();
        MapboxCameraUpdate.handle(mMap, mCameraAnimationsPlugin, update, options);
    }

    @Override public void stopAnimation() {
        mCameraAnimationsPlugin.cancelAllAnimators(Collections.emptyList());
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
//        mPointAnnotationManager.
//        mPointAnnotationManager.create(
//                new PointAnnotationOptions().
//        );
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
        mPolylineAnnotationManager.deleteAll();
        mPolygonAnnotationManager.deleteAll();
        mCircleAnnotationManager.deleteAll();
        mPointAnnotationManager.deleteAll();
    }

    @Override public @Nullable IndoorBuilding getFocusedBuilding() {
        return null;
    }

    @Override public void setOnIndoorStateChangeListener(
            final @Nullable OnIndoorStateChangeListener listener
    ) {
        // No-op on Mapbox Maps SDK
    }

    @Override public int getMapType() {
        return mType;
    }

    @Override public void setMapType(int type) {
        mType = type;
        mMapView.getMapboxMap().loadStyleUri(MapboxMapType.unwrap(type));
    }

    @Override public boolean isTrafficEnabled() {
        return false;
    }

    @Override public void setTrafficEnabled(boolean enabled) {
        // No-op on Mapbox Maps SDK
    }

    @Override public boolean isIndoorEnabled() {
        return false;
    }

    @Override public boolean setIndoorEnabled(boolean enabled) {
        return false;
    }

    @Override public boolean isBuildingsEnabled() {
        return false;
    }

    @Override public void setBuildingsEnabled(boolean enabled) {
        // No-op on Mapbox Maps SDK
    }

    @Override public boolean isMyLocationEnabled() {
        return false;
    }

    @Override public void setMyLocationEnabled(boolean enabled) {

    }

    @Override public void setLocationSource(@Nullable LocationSource source) {

    }

    @Override public @NonNull UiSettings getUiSettings() {
        return mUiSettings;
    }

    @Override public @NonNull Projection getProjection() {
        return null;
    }

    @Override public void setOnCameraMoveStartedListener(
            final @Nullable OnCameraMoveStartedListener listener
    ) {

    }

    @Override public void setOnCameraMoveListener(
            final @Nullable OnCameraMoveListener listener
    ) {

    }

    @Override public void setOnCameraMoveCanceledListener(
            final @Nullable OnCameraMoveCanceledListener listener
    ) {

    }

    @Override public void setOnCameraIdleListener(
            final @Nullable OnCameraIdleListener listener
    ) {

    }

    @Override public void setOnMapClickListener(
            final @Nullable OnMapClickListener listener
    ) {
        if (listener == null) {
            final @Nullable com.mapbox.maps.plugin.gestures.OnMapClickListener previous =
                    mMapClickListener;
            if (previous != null) {
                mGesturesPlugin.removeOnMapClickListener(previous);
            }
        } else {
            mMapClickListener = point -> {
                listener.onMapClick(MapboxLatLng.wrap(point));
                return true;
            };
            mGesturesPlugin.addOnMapClickListener(mMapClickListener);
        }
    }

    @Override public void setOnMapLongClickListener(
            final @Nullable OnMapLongClickListener listener
    ) {
        if (listener == null) {
            final @Nullable com.mapbox.maps.plugin.gestures.OnMapLongClickListener previous =
                    mMapLongClickListener;
            if (previous != null) {
                mGesturesPlugin.removeOnMapLongClickListener(previous);
            }
        } else {
            mMapLongClickListener = point -> {
                listener.onMapLongClick(MapboxLatLng.wrap(point));
                return true;
            };
            mGesturesPlugin.addOnMapLongClickListener(mMapLongClickListener);
        }
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

    }

    @Override public void setOnMyLocationClickListener(
            final @Nullable OnMyLocationClickListener listener
    ) {

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
        mMapView.snapshot(callback::onSnapshotReady);
    }

    @Override public void snapshot(
            final @NonNull SnapshotReadyCallback callback,
            final @Nullable Bitmap bitmap
    ) {
        mMapView.snapshot(callback::onSnapshotReady);
    }

    @Override public void setPadding(int left, int top, int right, int bottom) {
        mMap.setCamera(
                ExtensionUtils.toCameraOptions(mMap.getCameraState())
                        .toBuilder()
                        .padding(new EdgeInsets(top, left, bottom, right))
                        .build()
        );
    }

    @Override public void setContentDescription(String description) {
        mMapView.setContentDescription(description);
    }

    @Override public void setOnPoiClickListener(final @Nullable OnPoiClickListener listener) {

    }

    @Override public boolean setMapStyle(@Nullable Style.Options style) {
        return false;
    }

    @Override public void setMinZoomPreference(float minZoomPreference) {
        mMapView.getMapboxMap()
                .setBounds(
                        new CameraBoundsOptions.Builder()
                                .minZoom((double) minZoomPreference)
                                .build()
                );
    }

    @Override public void setMaxZoomPreference(float maxZoomPreference) {
        mMapView.getMapboxMap()
                .setBounds(
                        new CameraBoundsOptions.Builder()
                                .maxZoom((double) maxZoomPreference)
                                .build()
                );
    }

    @Override public void resetMinMaxZoomPreference() {
        mMapView.getMapboxMap()
                .setBounds(
                        new CameraBoundsOptions.Builder()
                                .minZoom(0.0)
                                .maxZoom(25.5)
                                .build()
                );
    }

    @Override public void setLatLngBoundsForCameraTarget(@Nullable LatLngBounds bounds) {
        mMapView.getMapboxMap()
                .setBounds(
                        new CameraBoundsOptions.Builder()
                                .bounds(bounds == null
                                        ? CoordinateBounds.world()
                                        : MapboxLatLngBounds.unwrap(bounds)
                                )
                                .build()
                );
    }


    static class UiSettings implements MapClient.UiSettings {
        private final @NonNull GesturesPlugin mGesturesPlugin;
        private final @NonNull CompassPlugin mCompassPlugin;

        UiSettings(@NonNull MapView view) {
            mGesturesPlugin = Objects
                    .requireNonNull(view.getPlugin(Plugin.MAPBOX_GESTURES_PLUGIN_ID));
            mCompassPlugin = Objects
                    .requireNonNull(view.getPlugin(Plugin.MAPBOX_COMPASS_PLUGIN_ID));
        }

        @Override public void setZoomControlsEnabled(boolean enabled) {
            // TODO
        }

        @Override public void setCompassEnabled(boolean enabled) {
            mCompassPlugin.setEnabled(enabled);
        }

        @Override public void setMyLocationButtonEnabled(boolean enabled) {
            // TODO
        }

        @Override public void setIndoorLevelPickerEnabled(boolean enabled) {
            // No-op on TomTom Map
        }

        @Override public void setScrollGesturesEnabled(boolean enabled) {
            mGesturesPlugin.setScrollEnabled(enabled);
        }

        @Override public void setZoomGesturesEnabled(boolean enabled) {
            // TODO
        }

        @Override public void setTiltGesturesEnabled(boolean enabled) {
            mGesturesPlugin.getGesturesManager().getShoveGestureDetector().setEnabled(false);
        }

        @Override public void setRotateGesturesEnabled(boolean enabled) {
            mGesturesPlugin.setRotateEnabled(enabled);
        }

        @Override public void setScrollGesturesEnabledDuringRotateOrZoom(boolean enabled) {
            mGesturesPlugin.setSimultaneousRotateAndPinchToZoomEnabled(enabled);
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
            return false;
        }

        @Override public boolean isCompassEnabled() {
            return mCompassPlugin.getEnabled();
        }

        @Override public boolean isMyLocationButtonEnabled() {
            return false;
        }

        @Override public boolean isIndoorLevelPickerEnabled() {
            return false; // No-op on TomTom Map
        }

        @Override public boolean isScrollGesturesEnabled() {
            return mGesturesPlugin.getScrollEnabled();
        }

        @Override public boolean isScrollGesturesEnabledDuringRotateOrZoom() {
            return mGesturesPlugin.getSimultaneousRotateAndPinchToZoomEnabled();
        }

        @Override public boolean isZoomGesturesEnabled() {
            return mGesturesPlugin.getPitchEnabled();
        }

        @Override public boolean isTiltGesturesEnabled() {
            return mGesturesPlugin.getGesturesManager().getShoveGestureDetector().isEnabled();
        }

        @Override public boolean isRotateGesturesEnabled() {
            return mGesturesPlugin.getRotateEnabled();
        }

        @Override public boolean isMapToolbarEnabled() {
            return false; // No-op on TomTom Map
        }
    }

}
