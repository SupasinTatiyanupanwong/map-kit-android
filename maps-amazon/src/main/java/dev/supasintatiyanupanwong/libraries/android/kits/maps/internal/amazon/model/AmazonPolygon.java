/*
 * Copyright 2021 Supasin Tatiyanupanwong
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

package dev.supasintatiyanupanwong.libraries.android.kits.maps.internal.amazon.model;

import static androidx.annotation.RestrictTo.Scope.LIBRARY;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.JointType;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.LatLng;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.PatternItem;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.Polygon;

@RestrictTo(LIBRARY)
public class AmazonPolygon implements Polygon {

    private final com.amazon.geo.mapsv2.model.Polygon mDelegate;

    private @Nullable Object mTag; // Providing tag support for Amazon's Polygon

    private AmazonPolygon(com.amazon.geo.mapsv2.model.Polygon delegate) {
        mDelegate = delegate;
    }

    @Override
    public void remove() {
        mDelegate.remove();
    }

    @Override
    public String getId() {
        return mDelegate.getId();
    }

    @Override
    public void setPoints(List<LatLng> points) {
        mDelegate.setPoints(AmazonLatLng.unwrap(points));
    }

    @Override
    public List<LatLng> getPoints() {
        return AmazonLatLng.wrap(mDelegate.getPoints());
    }

    @Override
    public void setHoles(List<? extends List<LatLng>> holes) {
        List<List<com.amazon.geo.mapsv2.model.LatLng>> gHoles = new ArrayList<>();
        for (List<LatLng> hole : holes) {
            gHoles.add(AmazonLatLng.unwrap(hole));
        }
        mDelegate.setHoles(gHoles);
    }

    @Override
    public List<List<LatLng>> getHoles() {
        List<List<LatLng>> holes = new ArrayList<>();
        for (List<com.amazon.geo.mapsv2.model.LatLng> hole : mDelegate.getHoles()) {
            holes.add(AmazonLatLng.wrap(hole));
        }
        return holes;
    }

    @Override
    public void setStrokeWidth(float width) {
        mDelegate.setStrokeWidth(width);
    }

    @Override
    public float getStrokeWidth() {
        return mDelegate.getStrokeWidth();
    }

    @Override
    public void setStrokeColor(int color) {
        mDelegate.setStrokeColor(color);
    }

    @Override
    public int getStrokeColor() {
        return mDelegate.getStrokeColor();
    }

    @Override
    public void setStrokeJointType(int jointType) {
        // Not supported, no-op.
    }

    @Override
    public int getStrokeJointType() {
        return JointType.DEFAULT; // Not supported, fallback to default.
    }

    @Override
    public void setStrokePattern(@Nullable List<PatternItem> pattern) {
        // Not supported, no-op.
    }

    @Override
    public @Nullable List<PatternItem> getStrokePattern() {
        return null; // Not supported.
    }

    @Override
    public void setFillColor(int color) {
        mDelegate.setFillColor(color);
    }

    @Override
    public int getFillColor() {
        return mDelegate.getFillColor();
    }

    @Override
    public void setZIndex(float zIndex) {
        mDelegate.setZIndex(zIndex);
    }

    @Override
    public float getZIndex() {
        return mDelegate.getZIndex();
    }

    @Override
    public void setVisible(boolean visible) {
        mDelegate.setVisible(visible);
    }

    @Override
    public boolean isVisible() {
        return mDelegate.isVisible();
    }

    @Override
    public void setGeodesic(boolean geodesic) {
        mDelegate.setGeodesic(geodesic);
    }

    @Override
    public boolean isGeodesic() {
        return mDelegate.isGeodesic();
    }

    @Override
    public void setClickable(boolean clickable) {
        // Not supported, no-op.
    }

    @Override
    public boolean isClickable() {
        return false; // Not supported.
    }

    @Override
    public void setTag(@Nullable Object tag) {
        mTag = tag;
    }

    @Override
    public @Nullable Object getTag() {
        return mTag;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        AmazonPolygon that = (AmazonPolygon) obj;

        return mDelegate.equals(that.mDelegate) && Objects.equals(mTag, that.mTag);
    }

    @Override
    public int hashCode() {
        return mDelegate.hashCode();
    }

    @Override
    public @NonNull String toString() {
        return mDelegate.toString();
    }


    static Polygon wrap(com.amazon.geo.mapsv2.model.Polygon delegate) {
        return new AmazonPolygon(delegate);
    }


    public static class Options implements Polygon.Options {
        private final com.amazon.geo.mapsv2.model.PolygonOptions mDelegate;

        public Options() {
            mDelegate = new com.amazon.geo.mapsv2.model.PolygonOptions();
        }

        @Override
        public @NonNull Polygon.Options add(LatLng point) {
            mDelegate.add(AmazonLatLng.unwrap(point));
            return this;
        }

        @Override
        public @NonNull Polygon.Options add(LatLng... points) {
            mDelegate.addAll(AmazonLatLng.unwrap(Arrays.asList(points)));
            return this;
        }

        @Override
        public @NonNull Polygon.Options addAll(Iterable<LatLng> points) {
            mDelegate.addAll(AmazonLatLng.unwrap(points));
            return this;
        }

        @Override
        public @NonNull Polygon.Options addHole(Iterable<LatLng> points) {
            mDelegate.addHole(AmazonLatLng.unwrap(points));
            return this;
        }

        @Override
        public @NonNull Polygon.Options strokeWidth(float width) {
            mDelegate.strokeWidth(width);
            return this;
        }

        @Override
        public @NonNull Polygon.Options strokeColor(int color) {
            mDelegate.strokeColor(color);
            return this;
        }

        @Override
        public @NonNull Polygon.Options strokeJointType(int jointType) {
            // Not supported, no-op.
            return this;
        }

        @Override
        public @NonNull Polygon.Options strokePattern(@Nullable List<PatternItem> pattern) {
            // Not supported, no-op.
            return this;
        }

        @Override
        public @NonNull Polygon.Options fillColor(int color) {
            mDelegate.fillColor(color);
            return this;
        }

        @Override
        public @NonNull Polygon.Options zIndex(float zIndex) {
            mDelegate.zIndex(zIndex);
            return this;
        }

        @Override
        public @NonNull Polygon.Options visible(boolean visible) {
            mDelegate.visible(visible);
            return this;
        }

        @Override
        public @NonNull Polygon.Options geodesic(boolean geodesic) {
            mDelegate.geodesic(geodesic);
            return this;
        }

        @Override
        public @NonNull Polygon.Options clickable(boolean clickable) {
            // Not supported, no-op.
            return this;
        }

        @Override
        public List<LatLng> getPoints() {
            return AmazonLatLng.wrap(mDelegate.getPoints());
        }

        @Override
        public List<List<LatLng>> getHoles() {
            List<List<LatLng>> holes = new ArrayList<>();
            for (List<com.amazon.geo.mapsv2.model.LatLng> hole : mDelegate.getHoles()) {
                holes.add(AmazonLatLng.wrap(hole));
            }
            return holes;
        }

        @Override
        public float getStrokeWidth() {
            return mDelegate.getStrokeWidth();
        }

        @Override
        public int getStrokeColor() {
            return mDelegate.getStrokeColor();
        }

        @Override
        public int getStrokeJointType() {
            return JointType.DEFAULT; // Not supported, fallback to default.
        }

        @Override
        public @Nullable List<PatternItem> getStrokePattern() {
            return null; // Not supported.
        }

        @Override
        public int getFillColor() {
            return mDelegate.getFillColor();
        }

        @Override
        public float getZIndex() {
            return mDelegate.getZIndex();
        }

        @Override
        public boolean isVisible() {
            return mDelegate.isVisible();
        }

        @Override
        public boolean isGeodesic() {
            return mDelegate.isGeodesic();
        }

        @Override
        public boolean isClickable() {
            return false; //  Not supported.
        }


        static com.amazon.geo.mapsv2.model.PolygonOptions unwrap(Polygon.Options wrapped) {
            return ((Options) wrapped).mDelegate;
        }
    }

}
