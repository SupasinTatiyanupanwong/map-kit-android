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

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mapbox.maps.plugin.annotation.generated.CircleAnnotation;

import org.jetbrains.annotations.Contract;

import java.util.List;

import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.Circle;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.LatLng;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.PatternItem;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;

@RestrictTo(LIBRARY)
public class MapboxCircle implements Circle {

    private final @NonNull CircleAnnotation mDelegate;
    private final @NonNull Function1<CircleAnnotation, Void> mRemoveFunction;
    private final @NonNull Function2<CircleAnnotation, Object, Void> mSetTagFunction;
    private final @NonNull Function1<CircleAnnotation, Object> mGetTagFunction;

    private MapboxCircle(
            @NonNull CircleAnnotation delegate,
            @NonNull Function1<CircleAnnotation, Void> removeFunction,
            @NonNull Function2<CircleAnnotation, Object, Void> setTagFunction,
            @NonNull Function1<CircleAnnotation, Object> getTagFunction
    ) {
        mDelegate = delegate;
        mRemoveFunction = removeFunction;
        mSetTagFunction = setTagFunction;
        mGetTagFunction = getTagFunction;
    }

    @Override public void remove() {
        mRemoveFunction.invoke(mDelegate);
        mSetTagFunction.invoke(mDelegate, null);
    }

    @Override public @NonNull String getId() {
        return String.valueOf(mDelegate.getId());
    }

    @Override public void setCenter(@NonNull LatLng center) {
        mDelegate.setGeometry(MapboxLatLng.unwrap(center));
    }

    @Override public @NonNull LatLng getCenter() {
        return MapboxLatLng.wrap(mDelegate.getGeometry());
    }

    @Override public void setRadius(double radius) {
        mDelegate.setCircleRadius(radius);
    }

    @Override public double getRadius() {
        final @Nullable Double radius = mDelegate.getCircleRadius();
        return radius == null ? 0 : radius;
    }

    @Override public void setStrokeWidth(float width) {
        mDelegate.setCircleStrokeWidth((double) width);
    }

    @Override public float getStrokeWidth() {
        final @Nullable Double width = mDelegate.getCircleStrokeWidth();
        return width == null ? 0 : width.floatValue();
    }

    @Override public void setStrokeColor(@ColorInt int color) {
        mDelegate.setCircleStrokeColorInt(color);
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
    }

    @Override public @ColorInt int getFillColor() {
        final @Nullable Integer color = mDelegate.getCircleColorInt();
        return color == null ? 0 : color;
    }

    @Override public void setZIndex(float zIndex) {
        mDelegate.setCircleSortKey((double) zIndex);
    }

    @Override public float getZIndex() {
        final @Nullable Double sortKey = mDelegate.getCircleSortKey();
        return sortKey == null ? 0f : sortKey.floatValue();
    }

    @Override public void setVisible(boolean visible) {
        mDelegate.setCircleOpacity(visible ? 1.0 : 0);
        mDelegate.setCircleStrokeOpacity(visible ? 1.0 : 0);
    }

    @Override public boolean isVisible() {
        final @Nullable Double opacity = mDelegate.getCircleOpacity();
        return opacity != null && opacity != 0;
    }

    @Override public void setClickable(boolean clickable) {
        final @Nullable JsonElement data = mDelegate.getData();
        final @NonNull JsonObject json;
        if (data instanceof JsonObject) {
            json = (JsonObject) data;
        } else {
            json = new JsonObject();
        }
        json.addProperty("mapkit-clickable", clickable);
        mDelegate.setData(json);
    }

    @Override public boolean isClickable() {
        final @Nullable JsonElement data = mDelegate.getData();
        if (data instanceof JsonObject) {
            return ((JsonObject) data).getAsJsonPrimitive("mapkit-clickable").getAsBoolean();
        } else {
            return false;
        }
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

        MapboxCircle that = (MapboxCircle) obj;

        return mDelegate.equals(that.mDelegate);
    }

    @Override public int hashCode() {
        return mDelegate.hashCode();
    }

    @Override public @NonNull String toString() {
        return mDelegate.toString();
    }


    @Contract("null, _, _, _ -> null; !null, _, _, _ -> !null")
    static @Nullable Circle wrap(
            @Nullable CircleAnnotation delegate,
            @NonNull Function1<CircleAnnotation, Void> removeFunction,
            @NonNull Function2<CircleAnnotation, Object, Void> setTagFunction,
            @NonNull Function1<CircleAnnotation, Object> getTagFunction
    ) {
        return delegate == null ? null :
                new MapboxCircle(
                        delegate,
                        removeFunction,
                        setTagFunction,
                        getTagFunction
                );
    }

}
