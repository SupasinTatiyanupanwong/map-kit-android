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
import java.util.List;

import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.Cap;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.JointType;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.LatLng;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.PatternItem;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.Polyline;

@RestrictTo(LIBRARY)
public class TomTomPolyline implements Polyline {

    private final @NonNull com.tomtom.sdk.maps.display.polyline.Polyline mDelegate;
    private final boolean mClickable;

    private @Nullable Object mTag;

    private TomTomPolyline(
            @NonNull com.tomtom.sdk.maps.display.polyline.Polyline delegate,
            boolean clickable
    ) {
        mDelegate = delegate;
        mClickable = clickable;
    }

    @Override public void remove() {
        mDelegate.remove();
        mTag = null;
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

    @Override public void setWidth(float width) {
        mDelegate.setLineWidths(TomTomWidth.unwrap(width));
    }

    @Override public float getWidth() {
        return TomTomWidth.wrap(mDelegate.getLineWidths());
    }

    @Override public void setColor(@ColorInt int color) {
        mDelegate.setLineColor(TomTomColor.unwrap(color));
    }

    @Override public @ColorInt int getColor() {
        return TomTomColor.wrap(mDelegate.getLineColor());
    }

    @Override public void setStartCap(@NonNull Cap startCap) {
        mDelegate.setStartCap(TomTomCap.unwrap(startCap));
    }

    @Override public @NonNull Cap getStartCap() {
        return TomTomCap.wrap(mDelegate.getStartCap());
    }

    @Override public void setEndCap(@NonNull Cap endCap) {
        mDelegate.setEndCap(TomTomCap.unwrap(endCap));
    }

    @Override public @NonNull Cap getEndCap() {
        return TomTomCap.wrap(mDelegate.getEndCap());
    }

    @Override public void setJointType(int jointType) {
        // No-op on TomTom Map
    }

    @Override public int getJointType() {
        return JointType.DEFAULT;
    }

    @Override public void setPattern(@Nullable List<PatternItem> pattern) {
        // No-op on TomTom Map
    }

    @Override public @Nullable List<PatternItem> getPattern() {
        return null;
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

        TomTomPolyline that = (TomTomPolyline) obj;

        return mDelegate.equals(that.mDelegate);
    }

    @Override public int hashCode() {
        return mDelegate.hashCode();
    }

    @Override public @NonNull String toString() {
        return mDelegate.toString();
    }


    static @NonNull Polyline wrap(
            @NonNull com.tomtom.sdk.maps.display.polyline.Polyline delegate,
            boolean clickable
    ) {
        return new TomTomPolyline(delegate, clickable);
    }


    public static class Options implements Polyline.Options {
        private final @NonNull List<LatLng> mPoints = new ArrayList<>();
        private float mWidth = 4.0f;
        private int mColor = Color.BLUE;
        private @NonNull Cap mStartCap = TomTomButtCap.INSTANCE;
        private @NonNull Cap mEndCap = TomTomButtCap.INSTANCE;
        private boolean mClickable = false;

        public Options() {}

        @Override public @NonNull Polyline.Options add(LatLng point) {
            mPoints.add(point);
            return this;
        }

        @Override public @NonNull Polyline.Options add(LatLng... points) {
            mPoints.addAll(Arrays.asList(points));
            return this;
        }

        @Override public @NonNull Polyline.Options addAll(Iterable<LatLng> points) {
            for (LatLng point : points) {
                mPoints.add(point);
            }
            return this;
        }

        @Override public @NonNull Polyline.Options width(float width) {
            mWidth = width;
            return this;
        }

        @Override public @NonNull Polyline.Options color(int color) {
            mColor = color;
            return this;
        }

        @Override public @NonNull Polyline.Options startCap(@NonNull Cap startCap) {
            mStartCap = startCap;
            return this;
        }

        @Override public @NonNull Polyline.Options endCap(@NonNull Cap endCap) {
            mEndCap = endCap;
            return this;
        }

        @Override public @NonNull Polyline.Options jointType(int jointType) {
            // No-op on TomTom Map
            return this;
        }

        @Override public @NonNull Polyline.Options pattern(@Nullable List<PatternItem> pattern) {
            // No-op on TomTom Map
            return this;
        }

        @Override public @NonNull Polyline.Options zIndex(float zIndex) {
            // No-op on TomTom Map
            return this;
        }

        @Override public @NonNull Polyline.Options visible(boolean visible) {
            // No-op on TomTom Map
            return this;
        }

        @Override public @NonNull Polyline.Options geodesic(boolean geodesic) {
            // No-op on TomTom Map
            return this;
        }

        @Override public @NonNull Polyline.Options clickable(boolean clickable) {
            mClickable = clickable;
            return this;
        }

        @Override public List<LatLng> getPoints() {
            return mPoints;
        }

        @Override public float getWidth() {
            return mWidth;
        }

        @Override public int getColor() {
            return mColor;
        }

        @Override public @NonNull Cap getStartCap() {
            return mStartCap;
        }

        @Override public @NonNull Cap getEndCap() {
            return mEndCap;
        }

        @Override public int getJointType() {
            return JointType.DEFAULT;
        }

        @Override public @Nullable List<PatternItem> getPattern() {
            return null; // No-op on TomTom Map
        }

        @Override public float getZIndex() {
            return Float.NaN; // No-op on TomTom Map
        }

        @Override public boolean isVisible() {
            return true; // No-op on TomTom Map
        }

        @Override public boolean isGeodesic() {
            return false; // No-op on TomTom Map
        }

        @Override public boolean isClickable() {
            return mClickable;
        }


        static @NonNull com.tomtom.sdk.maps.display.polyline.PolylineOptions unwrap(
                @NonNull Polyline.Options wrapped
        ) {
            return new com.tomtom.sdk.maps.display.polyline.PolylineOptions(
                    TomTomLatLng.unwrap(wrapped.getPoints()),
                    TomTomColor.unwrap(wrapped.getColor()),
                    TomTomWidth.unwrap(wrapped.getWidth()),
                    TomTomColor.unwrap(Color.TRANSPARENT),
                    TomTomWidth.unwrap(0),
                    TomTomCap.unwrap(wrapped.getStartCap()),
                    TomTomCap.unwrap(wrapped.getEndCap()),
                    wrapped.isClickable()
            );
        }
    }

}
