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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.arch.core.util.Function;

import com.mapbox.maps.plugin.annotation.generated.PointAnnotation;

import java.util.Arrays;

import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.BitmapDescriptor;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.LatLng;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.Marker;

@RestrictTo(LIBRARY)
public class MapboxMarker implements Marker {

    private final @NonNull PointAnnotation mDelegate;
    private final @NonNull Function<PointAnnotation, Void> mRemoveFunction;

    private float mAlpha;
    private boolean mVisible;

    private @Nullable Object mTag;

    private MapboxMarker(
            @NonNull PointAnnotation delegate,
            @NonNull Function<PointAnnotation, Void> removeFunction,
            float alpha,
            boolean isVisible
    ) {
        mDelegate = delegate;
        mRemoveFunction = removeFunction;

        mAlpha = alpha;
        mVisible = isVisible;
        invalidateOpacity();
    }

    @Override public void remove() {
        mRemoveFunction.apply(mDelegate);
        mTag = null;
    }

    @Override public @NonNull String getId() {
        return String.valueOf(mDelegate.getId());
    }

    @Override public void setPosition(@NonNull LatLng latLng) {
        mDelegate.setPoint(MapboxLatLng.unwrap(latLng));
    }

    @Override public @NonNull LatLng getPosition() {
        return MapboxLatLng.wrap(mDelegate.getPoint());
    }

    @Override public void setZIndex(float zIndex) {
        mDelegate.setSymbolSortKey((double) zIndex);
    }

    @Override public float getZIndex() {
        final @Nullable Double sortKey = mDelegate.getSymbolSortKey();
        return sortKey == null ? 0f : sortKey.floatValue();
    }

    @Override public void setIcon(@Nullable BitmapDescriptor iconDescriptor) {
        mDelegate.setIconImageBitmap(
                MapboxBitmapDescriptor.unwrap(
                        iconDescriptor == null
                                ? MapboxBitmapDescriptorFactory.INSTANCE.defaultMarker()
                                : iconDescriptor
                )
        );
    }

    @Override public void setAnchor(float anchorU, float anchorV) {
        mDelegate.setIconOffset(Arrays.asList((double) anchorU, (double) anchorV));
    }

    @Override public void setInfoWindowAnchor(float anchorU, float anchorV) {
        // TODO
    }

    @Override public void setTitle(@Nullable String title) {
        // TODO
    }

    @Override public String getTitle() {
        return null;
    }

    @Override public void setSnippet(@Nullable String snippet) {
        // TODO
    }

    @Override public String getSnippet() {
        return null;
    }

    @Override public void setDraggable(boolean draggable) {
        mDelegate.setDraggable(draggable);
    }

    @Override public boolean isDraggable() {
        return mDelegate.isDraggable();
    }

    @Override public void showInfoWindow() {
        // TODO
    }

    @Override public void hideInfoWindow() {
        // TODO
    }

    @Override public boolean isInfoWindowShown() {
        return false; // TODO
    }

    @Override public void setVisible(boolean visible) {
        mVisible = visible;
        invalidateOpacity();
    }

    @Override public boolean isVisible() {
        return mVisible;
    }

    @Override public void setFlat(boolean flat) {
        // TODO
    }

    @Override public boolean isFlat() {
        return false;
    }

    @Override public void setRotation(float rotation) {
        mDelegate.setIconRotate((double) rotation);
    }

    @Override public float getRotation() {
        final @Nullable Double rotate = mDelegate.getIconRotate();
        return rotate == null ? 0f : rotate.floatValue();
    }

    @Override public void setAlpha(float alpha) {
        mAlpha = alpha;
        invalidateOpacity();
    }

    @Override public float getAlpha() {
        return mAlpha;
    }

    @Override public void setTag(@Nullable Object tag) {
        mTag = tag;
    }

    @Override public @Nullable Object getTag() {
        return mTag;
    }

    @Override public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        MapboxMarker that = (MapboxMarker) obj;

        return mDelegate.equals(that.mDelegate);
    }

    @Override public int hashCode() {
        return mDelegate.hashCode();
    }

    @Override public @NonNull String toString() {
        return mDelegate.toString();
    }


    private void invalidateOpacity() {
        mDelegate.setIconOpacity((double) (mVisible ? mAlpha : 0));
    }


    static @NonNull Marker wrap(
            @NonNull PointAnnotation delegate,
            @NonNull Function<PointAnnotation, Void> removeFunction,
            float alpha,
            boolean isVisible
    ) {
        return new MapboxMarker(delegate, removeFunction, alpha, isVisible);
    }

}
