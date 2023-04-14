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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.mapbox.maps.plugin.annotation.generated.PointAnnotationOptions;

import java.util.Arrays;

import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.BitmapDescriptor;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.LatLng;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.Marker;

public class MapboxMarkerOptions implements Marker.Options {

    private final @NonNull PointAnnotationOptions mDelegate;

    private @Nullable BitmapDescriptor mIconDescriptorCache;

    private float mAlpha;
    private boolean mVisible;

    public MapboxMarkerOptions() {
        mDelegate = new PointAnnotationOptions();

        // Ensuring default values
        icon(null);
        anchor(0.5f, 0.8f);
        alpha(1);
        visible(true);
    }

    @Override public @NonNull Marker.Options position(@NonNull LatLng latLng) {
        //noinspection ConstantValue
        if (latLng != null) {
            mDelegate.withGeometry(MapboxLatLng.unwrap(latLng));
        }
        return this;
    }

    @Override public @NonNull Marker.Options zIndex(float zIndex) {
        mDelegate.withSymbolSortKey(zIndex);
        return this;
    }

    @Override public @NonNull Marker.Options icon(@Nullable BitmapDescriptor iconDescriptor) {
        mIconDescriptorCache = iconDescriptor;
        mDelegate.withIconImage(
                MapboxBitmapDescriptor.unwrap(
                        iconDescriptor == null
                                ? MapboxBitmapDescriptorFactory.INSTANCE.defaultMarker()
                                : iconDescriptor
                )
        );
        return this;
    }

    @Override public @NonNull Marker.Options anchor(float anchorU, float anchorV) {
        mDelegate.withIconOffset(Arrays.asList((double) anchorU, (double) anchorV));
        return this;
    }

    @Override public @NonNull Marker.Options infoWindowAnchor(float anchorU, float anchorV) {
        return this;
    }

    @Override public @NonNull Marker.Options title(@Nullable String title) {
        return this;
    }

    @Override public @NonNull Marker.Options snippet(@Nullable String snippet) {
        return this;
    }

    @Override public @NonNull Marker.Options draggable(boolean draggable) {
        mDelegate.withDraggable(draggable);
        return this;
    }

    @Override public @NonNull Marker.Options visible(boolean visible) {
        mVisible = visible;
        invalidateOpacity();
        return this;
    }

    @Override public @NonNull Marker.Options flat(boolean flat) {
        return this;
    }

    @Override public @NonNull Marker.Options rotation(float rotation) {
        mDelegate.withIconRotate(rotation);
        return this;
    }

    @Override public @NonNull Marker.Options alpha(float alpha) {
        mAlpha = alpha;
        invalidateOpacity();
        return this;
    }

    @Override public LatLng getPosition() {
        return MapboxLatLng.wrap(mDelegate.getGeometry());
    }

    @Override public String getTitle() {
        return null;
    }

    @Override public String getSnippet() {
        return null;
    }

    @Override public @Nullable BitmapDescriptor getIcon() {
        return mIconDescriptorCache;
    }

    @Override public float getAnchorU() {
        //noinspection DataFlowIssue
        return mDelegate.getIconOffset().get(0).floatValue();
    }

    @Override public float getAnchorV() {
        //noinspection DataFlowIssue
        return mDelegate.getIconOffset().get(1).floatValue();
    }

    @Override public boolean isDraggable() {
        return mDelegate.getDraggable();
    }

    @Override public boolean isVisible() {
        return mVisible;
    }

    @Override public boolean isFlat() {
        return false;
    }

    @Override public float getRotation() {
        final @Nullable Double rotate = mDelegate.getIconRotate();
        return rotate == null ? 0f : rotate.floatValue();
    }

    @Override public float getInfoWindowAnchorU() {
        return 0f;
    }

    @Override public float getInfoWindowAnchorV() {
        return 0f;
    }

    @Override public float getAlpha() {
        return mAlpha;
    }

    @Override public float getZIndex() {
        final @Nullable Double sortKey = mDelegate.getSymbolSortKey();
        return sortKey == null ? 0f : sortKey.floatValue();
    }


    private void invalidateOpacity() {
        mDelegate.withIconOpacity(mVisible ? mAlpha : 0);
    }


    static PointAnnotationOptions unwrap(Marker.Options wrapped) {
        return ((MapboxMarkerOptions) wrapped).mDelegate;
    }

}
