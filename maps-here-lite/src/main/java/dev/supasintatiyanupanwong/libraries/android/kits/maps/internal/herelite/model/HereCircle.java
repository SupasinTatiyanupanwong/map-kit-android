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

import android.graphics.Color;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.arch.core.util.Function;

import com.here.NativeBase;
import com.here.sdk.core.GeoCircle;
import com.here.sdk.mapviewlite.MapCircle;
import com.here.sdk.mapviewlite.MapCircleStyle;
import com.here.sdk.mapviewlite.PixelFormat;

import org.jetbrains.annotations.Contract;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.UUID;

import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.Circle;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.LatLng;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.PatternItem;

@RestrictTo(LIBRARY)
public class HereCircle implements Circle {

    private final @NonNull com.here.sdk.mapviewlite.MapCircle mDelegate;
    private final @NonNull com.here.sdk.mapviewlite.MapCircleStyle mStyle;
    private final @NonNull Function<MapCircle, Void> mRemover;

    private final @NonNull String mId;

    private @NonNull com.here.sdk.core.GeoCircle mGeoCircle;

    private @Nullable Object mTag;

    HereCircle(@NonNull Circle.Options options, @NonNull Function<MapCircle, Void> remover) {
        final @NonNull GeoCircle circle = new GeoCircle(
                HereLatLng.unwrap(options.getCenter()),
                options.getRadius()
        );
        mGeoCircle = circle;

        final @NonNull MapCircleStyle style = new MapCircleStyle();
        style.setFillColor(options.getFillColor(), PixelFormat.ARGB_8888);
        style.setStrokeColor(options.getStrokeColor(), PixelFormat.ARGB_8888);
        style.setStrokeWidthInPixels(options.getStrokeWidth());
        style.setDrawOrder((long) options.getZIndex());
        mStyle = style;

        mDelegate = new MapCircle(circle, style);
        mRemover = remover;

        @NonNull String id;
        try {
            final @NonNull Field idField = NativeBase.class.getDeclaredField("nativeHandle");
            idField.setAccessible(true);
            id = String.valueOf(idField.getLong(mDelegate));
        } catch (ReflectiveOperationException ignored) {
            id = UUID.randomUUID().toString();
        }
        mId = id;
    }

    @Override public void remove() {
        mRemover.apply(mDelegate);
    }

    @Override public @NonNull String getId() {
        return mId;
    }

    @Override public void setCenter(@NonNull LatLng center) {
        try {
            final @NonNull GeoCircle geo =
                    new GeoCircle(HereLatLng.unwrap(center), mGeoCircle.radiusInMeters);
            final @NonNull Method method =
                    MapCircle.class.getDeclaredMethod("updateGeometry", GeoCircle.class);
            method.setAccessible(true);
            method.invoke(mDelegate, geo);
            mGeoCircle = geo;
        } catch (ReflectiveOperationException ignored) {
        }
    }

    @Override public LatLng getCenter() {
        return HereLatLng.wrap(mGeoCircle.center);
    }

    @Override public void setRadius(double radius) {
        try {
            final @NonNull GeoCircle geo = new GeoCircle(mGeoCircle.center, radius);
            final @NonNull Method method =
                    MapCircle.class.getDeclaredMethod("updateGeometry", GeoCircle.class);
            method.setAccessible(true);
            method.invoke(mDelegate, geo);
            mGeoCircle = geo;
        } catch (ReflectiveOperationException ignored) {
        }
    }

    @Override public double getRadius() {
        return mGeoCircle.radiusInMeters;
    }

    @Override public void setStrokeWidth(float width) {
        mStyle.setStrokeWidthInPixels(width);
        mDelegate.updateStyle(mStyle);
    }

    @Override public float getStrokeWidth() {
        return (float) mStyle.getStrokeWidthInPixels();
    }

    @Override public void setStrokeColor(int color) {
        mStyle.setStrokeColor(color, PixelFormat.ARGB_8888);
        mDelegate.updateStyle(mStyle);
    }

    @Override public int getStrokeColor() {
        return (int) mStyle.getStrokeColor(PixelFormat.ARGB_8888);
    }

    @Override public void setStrokePattern(@Nullable List<PatternItem> pattern) {
        // No-op on HERE Maps (Lite Edition)
    }

    @Override public @Nullable List<PatternItem> getStrokePattern() {
        return null;
    }

    @Override public void setFillColor(int color) {
        mStyle.setFillColor(color, PixelFormat.ARGB_8888);
        mDelegate.updateStyle(mStyle);
    }

    @Override public int getFillColor() {
        return (int) mStyle.getFillColor(PixelFormat.ARGB_8888);
    }

    @Override public void setZIndex(float zIndex) {
        mStyle.setDrawOrder((long) zIndex);
        mDelegate.updateStyle(mStyle);
    }

    @Override public float getZIndex() {
        return mStyle.getDrawOrder();
    }

    @Override public void setVisible(boolean visible) {
        mDelegate.setVisible(visible);
    }

    @Override public boolean isVisible() {
        return mDelegate.isVisible();
    }

    @Override public void setClickable(boolean clickable) {
        // No-op on HERE Maps (Lite Edition)
    }

    @Override public boolean isClickable() {
        return true;
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

        HereCircle that = (HereCircle) obj;

        return mDelegate.equals(that.mDelegate);
    }

    @Override public int hashCode() {
        return mDelegate.hashCode();
    }

    @Override public @NonNull String toString() {
        return mDelegate.toString();
    }


    @Contract("null -> null; !null -> !null")
    static @Nullable com.here.sdk.mapviewlite.MapCircle unwrap(@Nullable Circle wrapped) {
        return wrapped == null ? null : ((HereCircle) wrapped).mDelegate;
    }


    public static class Options implements Circle.Options {
        private LatLng mCenter;
        private double mRadius = 0f;
        private float mStrokeWidth = 10f;
        private @ColorInt int mStrokeColor = Color.TRANSPARENT;
        private @ColorInt int mFillColor = Color.BLUE;
        private float mZIndex = 0f;
        private boolean mVisible = false;

        public Options() {}

        @Override public @NonNull Circle.Options center(@NonNull LatLng center) {
            mCenter = center;
            return this;
        }

        @Override public @NonNull Circle.Options radius(double radius) {
            mRadius = radius;
            return this;
        }

        @Override public @NonNull Circle.Options strokeWidth(float width) {
            mStrokeWidth = width;
            return this;
        }

        @Override public @NonNull Circle.Options strokeColor(@ColorInt int color) {
            mStrokeColor = color;
            return this;
        }

        @Override public @NonNull Circle.Options strokePattern(
                @Nullable List<PatternItem> pattern
        ) {
            // No-op on HERE Maps (Lite Edition)
            return this;
        }

        @Override public @NonNull Circle.Options fillColor(@ColorInt int color) {
            mFillColor = color;
            return this;
        }

        @Override public @NonNull Circle.Options zIndex(float zIndex) {
            mZIndex = zIndex;
            return this;
        }

        @Override public @NonNull Circle.Options visible(boolean visible) {
            mVisible = visible;
            return this;
        }

        @Override public @NonNull Circle.Options clickable(boolean clickable) {
            // No-op on HERE Maps (Lite Edition)
            return this;
        }

        @Override public LatLng getCenter() {
            return mCenter;
        }

        @Override public double getRadius() {
            return mRadius;
        }

        @Override public float getStrokeWidth() {
            return mStrokeWidth;
        }

        @Override public @ColorInt int getStrokeColor() {
            return mStrokeColor;
        }

        @Override public @Nullable List<PatternItem> getStrokePattern() {
            return null;
        }

        @Override public @ColorInt int getFillColor() {
            return mFillColor;
        }

        @Override public float getZIndex() {
            return mZIndex;
        }

        @Override public boolean isVisible() {
            return mVisible;
        }

        @Override public boolean isClickable() {
            return true;
        }
    }

}
