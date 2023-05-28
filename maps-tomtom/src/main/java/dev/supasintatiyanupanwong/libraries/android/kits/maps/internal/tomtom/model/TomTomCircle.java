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

package dev.supasintatiyanupanwong.libraries.android.kits.maps.internal.tomtom.model;

import static androidx.annotation.RestrictTo.Scope.LIBRARY;

import android.graphics.Color;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;

import com.tomtom.sdk.maps.display.circle.RadiusUnit;

import java.util.List;

import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.Circle;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.LatLng;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.PatternItem;

@RestrictTo(LIBRARY)
public class TomTomCircle implements Circle {

    private final @NonNull com.tomtom.sdk.maps.display.circle.Circle mDelegate;
    private final boolean mClickable;

    private @Nullable Object mTag;


    private TomTomCircle(
            @NonNull com.tomtom.sdk.maps.display.circle.Circle delegate,
            boolean clickable
    ) {
        mDelegate = delegate;
        mClickable = clickable;
    }

    @Override public void remove() {
        mDelegate.remove();
    }

    @Override public @NonNull String getId() {
        return String.valueOf(mDelegate.a);
    }

    @Override public void setCenter(@NonNull LatLng center) {
        mDelegate.setCoordinate(TomTomLatLng.unwrap(center));
    }

    @Override public LatLng getCenter() {
        return TomTomLatLng.wrap(mDelegate.getCoordinate());
    }

    @Override public void setRadius(double radius) {
        mDelegate.setRadius(radius);
    }

    @Override public double getRadius() {
        return mDelegate.getRadius();
    }

    @Override public void setStrokeWidth(float width) {
        mDelegate.setOutlineRadius(width);
    }

    @Override public float getStrokeWidth() {
        return (float) mDelegate.getOutlineRadius();
    }

    @Override public void setStrokeColor(@ColorInt int color) {
        mDelegate.setOutlineColor(TomTomColor.unwrap(color));
    }

    @Override public @ColorInt int getStrokeColor() {
        return TomTomColor.wrap(mDelegate.getOutlineColor());
    }

    @Override public void setStrokePattern(@Nullable List<PatternItem> pattern) {
        // No-op on TomTom Map
    }

    @Override public @Nullable List<PatternItem> getStrokePattern() {
        return null;
    }

    @Override public void setFillColor(@ColorInt int color) {
        mDelegate.setFillColor(TomTomColor.unwrap(color));
    }

    @Override public @ColorInt int getFillColor() {
        return TomTomColor.wrap(mDelegate.getFillColor());
    }

    @Override public void setZIndex(float zIndex) {
        // No-op on TomTom Map
    }

    @Override public float getZIndex() {
        return Float.NaN;
    }

    @Override public void setVisible(boolean visible) {
        mDelegate.setVisible(visible);
    }

    @Override public boolean isVisible() {
        return mDelegate.isVisible();
    }

    @Override public void setClickable(boolean clickable) {
        // No-op on TomTom Map
    }

    @Override public boolean isClickable() {
        return mClickable;
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

        TomTomCircle that = (TomTomCircle) obj;

        return mDelegate.equals(that.mDelegate);
    }

    @Override public int hashCode() {
        return mDelegate.hashCode();
    }

    @Override public @NonNull String toString() {
        return mDelegate.toString();
    }


    static @NonNull Circle wrap(
            @NonNull com.tomtom.sdk.maps.display.circle.Circle delegate,
            boolean clickable
    ) {
        return new TomTomCircle(delegate, clickable);
    }


    public static class Options implements Circle.Options {
        private LatLng mCenter;
        private double mRadius = 0f;
        private float mStrokeWidth = 10f;
        private @ColorInt int mStrokeColor = Color.TRANSPARENT;
        private @ColorInt int mFillColor = Color.BLUE;
        private boolean mClickable = false;

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
            // No-op on TomTom Map
            return this;
        }

        @Override public @NonNull Circle.Options fillColor(@ColorInt int color) {
            mFillColor = color;
            return this;
        }

        @Override public @NonNull Circle.Options zIndex(float zIndex) {
            // No-op on TomTom Map
            return this;
        }

        @Override public @NonNull Circle.Options visible(boolean visible) {
            // No-op on TomTom Map
            return this;
        }

        @Override public @NonNull Circle.Options clickable(boolean clickable) {
            mClickable = clickable;
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
            return Float.NaN;
        }

        @Override public boolean isVisible() {
            return true;
        }

        @Override public boolean isClickable() {
            return mClickable;
        }


        static com.tomtom.sdk.maps.display.circle.CircleOptions unwrap(Circle.Options wrapped) {
            return new com.tomtom.sdk.maps.display.circle.CircleOptions(
                    TomTomLatLng.unwrap(wrapped.getCenter()),
                    wrapped.getRadius(),
                    RadiusUnit.METERS,
                    TomTomColor.unwrap(wrapped.getFillColor()),
                    TomTomColor.unwrap(wrapped.getStrokeColor()),
                    wrapped.getStrokeWidth(),
                    wrapped.isClickable()
            );
        }
    }

}
