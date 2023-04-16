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

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.gson.JsonObject;
import com.mapbox.maps.extension.style.utils.ColorUtils;
import com.mapbox.maps.plugin.annotation.generated.CircleAnnotationOptions;

import java.util.List;

import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.LatLng;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.Circle;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.PatternItem;

public class MapboxCircleOptions implements Circle.Options {

    private final @NonNull CircleAnnotationOptions mDelegate = new CircleAnnotationOptions();

    private final @NonNull JsonObject mData = new JsonObject();

    private MapboxCircleOptions() {
        mData.addProperty("mapkit-clickable", false);
        mDelegate.withData(mData);
    }

    @Override public @NonNull Circle.Options center(@NonNull LatLng center) {
        //noinspection ConstantValue
        if (center != null) {
            mDelegate.withGeometry(MapboxLatLng.unwrap(center));
        }
        return this;
    }

    @Override public @NonNull Circle.Options radius(double radius) {
        mDelegate.withCircleRadius(radius);
        return this;
    }

    @Override public @NonNull Circle.Options strokeWidth(float width) {
        mDelegate.withCircleStrokeWidth(width);
        return this;
    }

    @Override public @NonNull Circle.Options strokeColor(int color) {
        mDelegate.withCircleStrokeColor(color);
        return this;
    }

    @Override public @NonNull Circle.Options strokePattern(@Nullable List<PatternItem> pattern) {
        return this;
    }

    @Override public @NonNull Circle.Options fillColor(int color) {
        mDelegate.withCircleColor(color);
        return this;
    }

    @Override public @NonNull Circle.Options zIndex(float zIndex) {
        mDelegate.withCircleSortKey(zIndex);
        return this;
    }

    @Override public @NonNull Circle.Options visible(boolean visible) {
        mDelegate.withCircleOpacity(visible ? 1 : 0);
        mDelegate.withCircleStrokeOpacity(visible ? 1 : 0);
        return this;
    }

    @Override public @NonNull Circle.Options clickable(boolean clickable) {
        mData.addProperty("mapkit-clickable", clickable);
        return this;
    }

    @Override public LatLng getCenter() {
        return MapboxLatLng.wrap(mDelegate.getGeometry());
    }

    @Override public double getRadius() {
        final @Nullable Double radius = mDelegate.getCircleRadius();
        return radius == null ? 0f : radius.floatValue();
    }

    @Override public float getStrokeWidth() {
        final @Nullable Double width = mDelegate.getCircleStrokeWidth();
        return width == null ? 0f : width.floatValue();
    }

    @Override public @ColorInt int getStrokeColor() {
        return ColorUtils.INSTANCE.rgbaToColor(mDelegate.getCircleStrokeColor());
    }

    @Override public @Nullable List<PatternItem> getStrokePattern() {
        return null;
    }

    @Override public @ColorInt int getFillColor() {
        return ColorUtils.INSTANCE.rgbaToColor(mDelegate.getCircleColor());
    }

    @Override public float getZIndex() {
        final @Nullable Double sortKey = mDelegate.getCircleSortKey();
        return sortKey == null ? 0f : sortKey.floatValue();
    }

    @Override public boolean isVisible() {
        final @Nullable Double opacity = mDelegate.getCircleOpacity();
        return opacity != null && opacity != 0;
    }

    @Override public boolean isClickable() {
        return mData.getAsJsonPrimitive("mapkit-clickable").getAsBoolean();
    }



    static CircleAnnotationOptions unwrap(Circle.Options wrapped) {
        return ((MapboxCircleOptions) wrapped).mDelegate;
    }

}
