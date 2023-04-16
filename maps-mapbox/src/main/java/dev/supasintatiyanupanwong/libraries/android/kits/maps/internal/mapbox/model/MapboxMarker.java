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

import com.mapbox.maps.plugin.annotation.generated.PointAnnotation;

import org.jetbrains.annotations.Contract;

import java.util.Arrays;

import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.BitmapDescriptor;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.LatLng;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.Marker;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;

@RestrictTo(LIBRARY)
public class MapboxMarker implements Marker {

    private final @NonNull PointAnnotation mDelegate;
    private final @NonNull Function1<PointAnnotation, Void> mRemoveFunction;
    private final @NonNull Function2<PointAnnotation, Object, Void> mSetTagFunction;
    private final @NonNull Function1<PointAnnotation, Object> mGetTagFunction;

    private float mAlpha;
    private boolean mVisible;

    private MapboxMarker(
            @NonNull PointAnnotation delegate,
            @NonNull Function1<PointAnnotation, Void> removeFunction,
            @NonNull Function2<PointAnnotation, Object, Void> setTagFunction,
            @NonNull Function1<PointAnnotation, Object> getTagFunction,
            float alpha,
            boolean isVisible
    ) {
        mDelegate = delegate;
        mRemoveFunction = removeFunction;
        mSetTagFunction = setTagFunction;
        mGetTagFunction = getTagFunction;

        mAlpha = alpha;
        mVisible = isVisible;
        invalidateOpacity();
    }

    @Override public void remove() {
        mRemoveFunction.invoke(mDelegate);
        mSetTagFunction.invoke(mDelegate, null);
    }

    @Override public @NonNull String getId() {
        return String.valueOf(mDelegate.getId());
    }

    @Override public void setPosition(@NonNull LatLng latLng) {
        mDelegate.setGeometry(MapboxLatLng.unwrap(latLng));
    }

    @Override public @NonNull LatLng getPosition() {
        return MapboxLatLng.wrap(mDelegate.getGeometry());
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
        mSetTagFunction.invoke(mDelegate, tag);
    }

    @Override public @Nullable Object getTag() {
        return mGetTagFunction.invoke(mDelegate);
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


    @Contract("null, _, _, _, _, _ -> null; !null, _, _, _, _, _ -> !null")
    static @Nullable Marker wrap(
            @Nullable PointAnnotation delegate,
            @NonNull Function1<PointAnnotation, Void> removeFunction,
            @NonNull Function2<PointAnnotation, Object, Void> setTagFunction,
            @NonNull Function1<PointAnnotation, Object> getTagFunction,
            float alpha,
            boolean isVisible
    ) {
        return delegate == null ? null :
                new MapboxMarker(
                        delegate,
                        removeFunction,
                        setTagFunction,
                        getTagFunction,
                        alpha,
                        isVisible
                );
    }

}
