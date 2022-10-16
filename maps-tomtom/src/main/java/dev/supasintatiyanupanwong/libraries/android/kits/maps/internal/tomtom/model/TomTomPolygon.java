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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.JointType;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.LatLng;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.PatternItem;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.Polygon;

@RestrictTo(LIBRARY)
public class TomTomPolygon implements Polygon {

    private final @NonNull com.tomtom.sdk.maps.display.polygon.Polygon mDelegate;
    private final boolean mClickable;

    private @Nullable Object mTag;

    private TomTomPolygon(
            @NonNull com.tomtom.sdk.maps.display.polygon.Polygon delegate,
            boolean clickable
    ) {
        mDelegate = delegate;
        mClickable = clickable;
    }

    @Override public void remove() {
        mDelegate.remove();
    }

    @Override public String getId() {
        return String.valueOf(mDelegate.a);
    }

    @Override public void setPoints(List<LatLng> points) {
        mDelegate.setCoordinates(TomTomLatLng.unwrap(points));
    }

    @Override public List<LatLng> getPoints() {
        return TomTomLatLng.wrap(mDelegate.getCoordinates());
    }

    @Override public void setHoles(List<? extends List<LatLng>> holes) {
        // No-op on TomTom Map
    }

    @Override public List<List<LatLng>> getHoles() {
        return Collections.emptyList();
    }

    @Override public void setStrokeWidth(float width) {
        mDelegate.setOutlineWidth(width);
    }

    @Override public float getStrokeWidth() {
        return (float) mDelegate.getOutlineWidth();
    }

    @Override public void setStrokeColor(@ColorInt int color) {
        mDelegate.setOutlineColor(TomTomColor.unwrap(color));
    }

    @Override public @ColorInt int getStrokeColor() {
        return TomTomColor.wrap(mDelegate.getOutlineColor());
    }

    @Override public void setStrokeJointType(int jointType) {
        // No-op on TomTom Map
    }

    @Override public int getStrokeJointType() {
        return JointType.DEFAULT;
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

    @Override public void setGeodesic(boolean geodesic) {
        // No-op on TomTom Map
    }

    @Override public boolean isGeodesic() {
        return false;
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

        TomTomPolygon that = (TomTomPolygon) obj;

        return mDelegate.equals(that.mDelegate);
    }

    @Override public int hashCode() {
        return mDelegate.hashCode();
    }

    @Override public @NonNull String toString() {
        return mDelegate.toString();
    }


    static @NonNull Polygon wrap(
            @NonNull com.tomtom.sdk.maps.display.polygon.Polygon delegate,
            boolean clickable
    ) {
        return new TomTomPolygon(delegate, clickable);
    }


    public static class Options implements Polygon.Options {
        private final @NonNull List<LatLng> mPoints = new ArrayList<>();
        private float mStrokeWidth = 10f;
        private @ColorInt int mStrokeColor = Color.BLACK;
        private @ColorInt int mFillColor = Color.BLACK;
        private boolean mVisible = false;
        private boolean mClickable = false;

        public Options() {}

        @Override public @NonNull Polygon.Options add(LatLng point) {
            if (point != null) {
                mPoints.add(point);
            }
            return this;
        }

        @Override public @NonNull Polygon.Options add(LatLng... points) {
            for (LatLng point : points) {
                add(point);
            }
            return this;
        }

        @Override public @NonNull Polygon.Options addAll(Iterable<LatLng> points) {
            for (LatLng point : points) {
                add(point);
            }
            return this;
        }

        @Override public @NonNull Polygon.Options addHole(@NonNull Iterable<LatLng> points) {
            // No-op on TomTom Map
            return this;
        }

        @Override public @NonNull Polygon.Options strokeWidth(float width) {
            mStrokeWidth = width;
            return this;
        }

        @Override public @NonNull Polygon.Options strokeColor(int color) {
            mStrokeColor = color;
            return this;
        }

        @Override public @NonNull Polygon.Options strokeJointType(int jointType) {
            // No-op on TomTom Map
            return this;
        }

        @Override public @NonNull Polygon.Options strokePattern(@Nullable List<PatternItem> pattern) {
            // No-op on TomTom Map
            return this;
        }

        @Override public @NonNull Polygon.Options fillColor(int color) {
            mFillColor = color;
            return this;
        }

        @Override public @NonNull Polygon.Options zIndex(float zIndex) {
            // No-op on TomTom Map
            return this;
        }

        @Override public @NonNull Polygon.Options visible(boolean visible) {
            mVisible = visible;
            return this;
        }

        @Override public @NonNull Polygon.Options geodesic(boolean geodesic) {
            // No-op on TomTom Map
            return this;
        }

        @Override public @NonNull Polygon.Options clickable(boolean clickable) {
            mClickable = clickable;
            return this;
        }

        @Override @NonNull public List<LatLng> getPoints() {
            return mPoints;
        }

        @Override public @NonNull List<List<LatLng>> getHoles() {
            // No-op on TomTom Map
            return Collections.emptyList();
        }

        @Override public float getStrokeWidth() {
            return mStrokeWidth;
        }

        @Override public int getStrokeColor() {
            return mStrokeColor;
        }

        @Override public int getStrokeJointType() {
            return JointType.DEFAULT;
        }

        @Override public @Nullable List<PatternItem> getStrokePattern() {
            // No-op on TomTom Map
            return null;
        }

        @Override public int getFillColor() {
            return mFillColor;
        }

        @Override public float getZIndex() {
            return Float.NaN;
        }

        @Override public boolean isVisible() {
            return mVisible;
        }

        @Override public boolean isGeodesic() {
            return false;
        }

        @Override public boolean isClickable() {
            return mClickable;
        }


        static @NonNull com.tomtom.sdk.maps.display.polygon.PolygonOptions unwrap(
                @NonNull Polygon.Options wrapped
        ) {
            return new com.tomtom.sdk.maps.display.polygon.PolygonOptions(
                    TomTomLatLng.unwrap(wrapped.getPoints()),
                    TomTomColor.unwrap(wrapped.getStrokeColor()),
                    wrapped.getStrokeWidth(),
                    TomTomColor.unwrap(wrapped.getFillColor()),
                    null,
                    false,
                    wrapped.isClickable()
            );
        }
    }

}
