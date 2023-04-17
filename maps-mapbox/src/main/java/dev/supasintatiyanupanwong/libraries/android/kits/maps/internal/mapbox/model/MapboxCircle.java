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

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;

import com.mapbox.maps.plugin.annotation.generated.CircleAnnotation;

import org.jetbrains.annotations.Contract;

import java.util.List;

import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.Circle;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.LatLng;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.PatternItem;

@RestrictTo(LIBRARY)
public class MapboxCircle implements Circle {

    private final @NonNull CircleAnnotation mDelegate;
    private final @NonNull MapboxCircleExtensions mExtensions;

    private MapboxCircle(
            @NonNull CircleAnnotation delegate,
            @NonNull MapboxCircleExtensions extensions
    ) {
        mDelegate = delegate;
        mExtensions = extensions;
    }

    @Override public void remove() {
        mExtensions.remove(mDelegate);
        mExtensions.setTag(mDelegate, null);
    }

    @Override public @NonNull String getId() {
        return String.valueOf(mDelegate.getId());
    }

    @Override public void setCenter(@NonNull LatLng center) {
        mDelegate.setGeometry(MapboxLatLng.unwrap(center));
        mExtensions.invalidate(mDelegate);
    }

    @Override public @NonNull LatLng getCenter() {
        return MapboxLatLng.wrap(mDelegate.getGeometry());
    }

    @Override public void setRadius(double radius) {
        mDelegate.setCircleRadius(radius);
        mExtensions.invalidate(mDelegate);
    }

    @Override public double getRadius() {
        final @Nullable Double radius = mDelegate.getCircleRadius();
        return radius == null ? 0 : radius;
    }

    @Override public void setStrokeWidth(float width) {
        mDelegate.setCircleStrokeWidth((double) width);
        mExtensions.invalidate(mDelegate);
    }

    @Override public float getStrokeWidth() {
        final @Nullable Double width = mDelegate.getCircleStrokeWidth();
        return width == null ? 0 : width.floatValue();
    }

    @Override public void setStrokeColor(@ColorInt int color) {
        mDelegate.setCircleStrokeColorInt(color);
        mExtensions.invalidate(mDelegate);
    }

    @Override public @ColorInt int getStrokeColor() {
        final @Nullable Integer color = mDelegate.getCircleStrokeColorInt();
        return color == null ? 0 : color;
    }

    @Override public void setStrokePattern(@Nullable List<PatternItem> pattern) {

    }

    @Override public @Nullable List<PatternItem> getStrokePattern() {
        return null;
    }

    @Override public void setFillColor(@ColorInt int color) {
        mDelegate.setCircleColorInt(color);
        mExtensions.invalidate(mDelegate);
    }

    @Override public @ColorInt int getFillColor() {
        final @Nullable Integer color = mDelegate.getCircleColorInt();
        return color == null ? 0 : color;
    }

    @Override public void setZIndex(float zIndex) {
        mDelegate.setCircleSortKey((double) zIndex);
        mExtensions.invalidate(mDelegate);
    }

    @Override public float getZIndex() {
        final @Nullable Double sortKey = mDelegate.getCircleSortKey();
        return sortKey == null ? 0f : sortKey.floatValue();
    }

    @Override public void setVisible(boolean visible) {
        mDelegate.setCircleOpacity(visible ? 1.0 : 0);
        mDelegate.setCircleStrokeOpacity(visible ? 1.0 : 0);
        mExtensions.invalidate(mDelegate);
    }

    @Override public boolean isVisible() {
        final @Nullable Double opacity = mDelegate.getCircleOpacity();
        return opacity != null && opacity != 0;
    }

    @Override public void setClickable(boolean clickable) {
        mExtensions.setClickable(mDelegate, clickable);
    }

    @Override public boolean isClickable() {
        return mExtensions.isClickable(mDelegate);
    }

    @Override public void setTag(@Nullable Object tag) {
        mExtensions.setTag(mDelegate, tag);
    }

    @Override public @Nullable Object getTag() {
        return mExtensions.getTag(mDelegate);
    }


    @Override public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        MapboxCircle that = (MapboxCircle) obj;

        return mDelegate.equals(that.mDelegate);
    }

    @Override public int hashCode() {
        return mDelegate.hashCode();
    }

    @Override public @NonNull String toString() {
        return mDelegate.toString();
    }


    @Contract("null, _ -> null; !null, _ -> !null")
    static @Nullable Circle wrap(
            @Nullable CircleAnnotation delegate,
            @NonNull MapboxCircleExtensions extensions
    ) {
        return delegate == null ? null : new MapboxCircle(delegate, extensions);
    }

}
