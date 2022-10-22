package dev.supasintatiyanupanwong.libraries.android.kits.maps.internal.herelite.model;

import static androidx.annotation.RestrictTo.Scope.LIBRARY;

import android.graphics.Bitmap;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;

import com.here.sdk.mapviewlite.CameraLimits;
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

    public HereMapClient(@NonNull MapViewLite mapView) {
        mMapView = mapView;
    }

    @Override public @NonNull CameraPosition getCameraPosition() {
        return null;
    }

    @Override public float getMaxZoomLevel() {
        return (float) mMapView.getCamera().getLimits().getMaxZoomLevel();
    }

    @Override public float getMinZoomLevel() {
        return (float) mMapView.getCamera().getLimits().getMinZoomLevel();
    }

    @Override public void moveCamera(@NonNull CameraUpdate update) {

    }

    @Override public void animateCamera(@NonNull CameraUpdate update) {

    }

    @Override public void animateCamera(
            @NonNull CameraUpdate update,
            final @Nullable CancelableCallback callback
    ) {

    }

    @Override public void animateCamera(
            @NonNull CameraUpdate update,
            int durationMs,
            final @Nullable CancelableCallback callback
    ) {

    }

    @Override public void stopAnimation() {

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
        return null;
    }

    @Override public void setOnIndoorStateChangeListener(
            final @Nullable OnIndoorStateChangeListener listener
    ) {

    }

    @Override public int getMapType() {
        return 0;
    }

    @Override public void setMapType(int type) {

    }

    @Override public boolean isTrafficEnabled() {
        return false;
    }

    @Override public void setTrafficEnabled(boolean enabled) {

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

    }

    @Override public boolean isMyLocationEnabled() {
        return false;
    }

    @Override public void setMyLocationEnabled(boolean enabled) {

    }

    @Override public void setLocationSource(@Nullable LocationSource source) {

    }

    @Override public @NonNull UiSettings getUiSettings() {
        return null;
    }

    @Override public @NonNull Projection getProjection() {
        return null;
    }

    @Override public void setOnCameraMoveStartedListener(
            final @Nullable OnCameraMoveStartedListener listener) {

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
        mMapView.captureScreenshot(callback::onSnapshotReady);
    }

    @Override public void snapshot(
            final @NonNull SnapshotReadyCallback callback,
            final @Nullable Bitmap bitmap
    ) {
        mMapView.captureScreenshot(callback::onSnapshotReady);
    }

    @Override public void setPadding(int left, int top, int right, int bottom) {

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

}
