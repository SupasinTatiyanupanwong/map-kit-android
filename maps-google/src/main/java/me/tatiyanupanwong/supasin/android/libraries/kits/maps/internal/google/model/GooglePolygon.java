/*
 * Copyright 2020 Supasin Tatiyanupanwong
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

package me.tatiyanupanwong.supasin.android.libraries.kits.maps.internal.google.model;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import me.tatiyanupanwong.supasin.android.libraries.kits.maps.model.LatLng;
import me.tatiyanupanwong.supasin.android.libraries.kits.maps.model.PatternItem;
import me.tatiyanupanwong.supasin.android.libraries.kits.maps.model.Polygon;

class GooglePolygon implements Polygon {

    private final com.google.android.gms.maps.model.Polygon mDelegate;

    private GooglePolygon(com.google.android.gms.maps.model.Polygon delegate) {
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
        mDelegate.setPoints(GoogleLatLng.unwrap(points));
    }

    @Override
    public List<LatLng> getPoints() {
        return GoogleLatLng.wrap(mDelegate.getPoints());
    }

    @Override
    public void setHoles(List<? extends List<LatLng>> holes) {
        List<List<com.google.android.gms.maps.model.LatLng>> gHoles = new ArrayList<>();
        for (List<LatLng> hole : holes) {
            gHoles.add(GoogleLatLng.unwrap(hole));
        }
        mDelegate.setHoles(gHoles);
    }

    @Override
    public List<List<LatLng>> getHoles() {
        List<List<LatLng>> holes = new ArrayList<>();
        for (List<com.google.android.gms.maps.model.LatLng> hole : mDelegate.getHoles()) {
            holes.add(GoogleLatLng.wrap(hole));
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
        mDelegate.setStrokeJointType(jointType);
    }

    @Override
    public int getStrokeJointType() {
        return mDelegate.getStrokeJointType();
    }

    @Override
    public void setStrokePattern(@Nullable List<PatternItem> pattern) {
        mDelegate.setStrokePattern(GooglePatternItem.unwrap(pattern));
    }

    @Nullable
    @Override
    public List<PatternItem> getStrokePattern() {
        return GooglePatternItem.wrap(mDelegate.getStrokePattern());
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
        mDelegate.setClickable(clickable);
    }

    @Override
    public boolean isClickable() {
        return mDelegate.isClickable();
    }

    @Override
    public void setTag(@Nullable Object tag) {
        mDelegate.setTag(tag);
    }

    @Nullable
    @Override
    public Object getTag() {
        return mDelegate.getTag();
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        GooglePolygon that = (GooglePolygon) obj;

        return mDelegate.equals(that.mDelegate);
    }

    @Override
    public int hashCode() {
        return mDelegate.hashCode();
    }

    @NonNull
    @Override
    public String toString() {
        return mDelegate.toString();
    }


    static Polygon wrap(com.google.android.gms.maps.model.Polygon delegate) {
        return new GooglePolygon(delegate);
    }


    static class Options implements Polygon.Options {
        private final com.google.android.gms.maps.model.PolygonOptions mDelegate;

        Options() {
            mDelegate = new com.google.android.gms.maps.model.PolygonOptions();
        }

        @NonNull
        @Override
        public Polygon.Options add(LatLng point) {
            mDelegate.add(GoogleLatLng.unwrap(point));
            return this;
        }

        @NonNull
        @Override
        public Polygon.Options add(LatLng... points) {
            mDelegate.addAll(GoogleLatLng.unwrap(Arrays.asList(points)));
            return this;
        }

        @NonNull
        @Override
        public Polygon.Options addAll(Iterable<LatLng> points) {
            mDelegate.addAll(GoogleLatLng.unwrap(points));
            return this;
        }

        @NonNull
        @Override
        public Polygon.Options addHole(Iterable<LatLng> points) {
            mDelegate.addHole(GoogleLatLng.unwrap(points));
            return this;
        }

        @NonNull
        @Override
        public Polygon.Options strokeWidth(float width) {
            mDelegate.strokeWidth(width);
            return this;
        }

        @NonNull
        @Override
        public Polygon.Options strokeColor(int color) {
            mDelegate.strokeColor(color);
            return this;
        }

        @NonNull
        @Override
        public Polygon.Options strokeJointType(int jointType) {
            mDelegate.strokeJointType(jointType);
            return this;
        }

        @NonNull
        @Override
        public Polygon.Options strokePattern(@Nullable List<PatternItem> pattern) {
            mDelegate.strokePattern(GooglePatternItem.unwrap(pattern));
            return this;
        }

        @NonNull
        @Override
        public Polygon.Options fillColor(int color) {
            mDelegate.fillColor(color);
            return this;
        }

        @NonNull
        @Override
        public Polygon.Options zIndex(float zIndex) {
            mDelegate.zIndex(zIndex);
            return this;
        }

        @NonNull
        @Override
        public Polygon.Options visible(boolean visible) {
            mDelegate.visible(visible);
            return this;
        }

        @NonNull
        @Override
        public Polygon.Options geodesic(boolean geodesic) {
            mDelegate.geodesic(geodesic);
            return this;
        }

        @NonNull
        @Override
        public Polygon.Options clickable(boolean clickable) {
            mDelegate.clickable(clickable);
            return this;
        }

        @Override
        public List<LatLng> getPoints() {
            return GoogleLatLng.wrap(mDelegate.getPoints());
        }

        @Override
        public List<List<LatLng>> getHoles() {
            List<List<LatLng>> holes = new ArrayList<>();
            for (List<com.google.android.gms.maps.model.LatLng> hole : mDelegate.getHoles()) {
                holes.add(GoogleLatLng.wrap(hole));
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
            return mDelegate.getStrokeJointType();
        }

        @Nullable
        @Override
        public List<PatternItem> getStrokePattern() {
            return GooglePatternItem.wrap(mDelegate.getStrokePattern());
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
            return mDelegate.isClickable();
        }


        static com.google.android.gms.maps.model.PolygonOptions unwrap(Polygon.Options wrapped) {
            return ((GooglePolygon.Options) wrapped).mDelegate;
        }
    }

}
