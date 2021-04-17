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

package dev.supasintatiyanupanwong.libraries.android.kits.maps.internal.huawei.model;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Arrays;
import java.util.List;

import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.Cap;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.LatLng;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.PatternItem;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.Polyline;

class HuaweiPolyline implements Polyline {

    private final com.huawei.hms.maps.model.Polyline mDelegate;

    private HuaweiPolyline(com.huawei.hms.maps.model.Polyline delegate) {
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
        mDelegate.setPoints(HuaweiLatLng.unwrap(points));
    }

    @Override
    public List<LatLng> getPoints() {
        return HuaweiLatLng.wrap(mDelegate.getPoints());
    }

    @Override
    public void setWidth(float width) {
        mDelegate.setWidth(width);
    }

    @Override
    public float getWidth() {
        return mDelegate.getWidth();
    }

    @Override
    public void setColor(int color) {
        mDelegate.setColor(color);
    }

    @Override
    public int getColor() {
        return mDelegate.getColor();
    }

    @Override
    public void setStartCap(@NonNull Cap startCap) {
        mDelegate.setStartCap(HuaweiCap.unwrap(startCap));
    }

    @Override
    public @NonNull Cap getStartCap() {
        return HuaweiCap.wrap(mDelegate.getStartCap());
    }

    @Override
    public void setEndCap(@NonNull Cap endCap) {
        mDelegate.setEndCap(HuaweiCap.unwrap(endCap));
    }

    @Override
    public @NonNull Cap getEndCap() {
        return HuaweiCap.wrap(mDelegate.getEndCap());
    }

    @Override
    public void setJointType(int jointType) {
        mDelegate.setJointType(jointType);
    }

    @Override
    public int getJointType() {
        return mDelegate.getJointType();
    }

    @Override
    public void setPattern(@Nullable List<PatternItem> pattern) {
        mDelegate.setPattern(HuaweiPatternItem.unwrap(pattern));
    }

    @Override
    public @Nullable List<PatternItem> getPattern() {
        return HuaweiPatternItem.wrap(mDelegate.getPattern());
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

    @Override
    public @Nullable Object getTag() {
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

        HuaweiPolyline that = (HuaweiPolyline) obj;

        return mDelegate.equals(that.mDelegate);
    }

    @Override
    public int hashCode() {
        return mDelegate.hashCode();
    }

    @Override
    public @NonNull String toString() {
        return mDelegate.toString();
    }


    static Polyline wrap(com.huawei.hms.maps.model.Polyline delegate) {
        return new HuaweiPolyline(delegate);
    }


    static class Options implements Polyline.Options {
        private final com.huawei.hms.maps.model.PolylineOptions mDelegate;

        Options() {
            mDelegate = new com.huawei.hms.maps.model.PolylineOptions();
        }

        @Override
        public @NonNull Polyline.Options add(LatLng point) {
            mDelegate.add(HuaweiLatLng.unwrap(point));
            return this;
        }

        @Override
        public @NonNull Polyline.Options add(LatLng... points) {
            mDelegate.addAll(HuaweiLatLng.unwrap(Arrays.asList(points)));
            return this;
        }

        @Override
        public @NonNull Polyline.Options addAll(Iterable<LatLng> points) {
            mDelegate.addAll(HuaweiLatLng.unwrap(points));
            return this;
        }

        @Override
        public @NonNull Polyline.Options width(float width) {
            mDelegate.width(width);
            return this;
        }

        @Override
        public @NonNull Polyline.Options color(int color) {
            mDelegate.color(color);
            return this;
        }

        @Override
        public @NonNull Polyline.Options startCap(@NonNull Cap startCap) {
            mDelegate.startCap(HuaweiCap.unwrap(startCap));
            return this;
        }

        @Override
        public @NonNull Polyline.Options endCap(@NonNull Cap endCap) {
            mDelegate.endCap(HuaweiCap.unwrap(endCap));
            return this;
        }

        @Override
        public @NonNull Polyline.Options jointType(int jointType) {
            mDelegate.jointType(jointType);
            return this;
        }

        @Override
        public @NonNull Polyline.Options pattern(@Nullable List<PatternItem> pattern) {
            mDelegate.pattern(HuaweiPatternItem.unwrap(pattern));
            return this;
        }

        @Override
        public @NonNull Polyline.Options zIndex(float zIndex) {
            mDelegate.zIndex(zIndex);
            return this;
        }

        @Override
        public @NonNull Polyline.Options visible(boolean visible) {
            mDelegate.visible(visible);
            return this;
        }

        @Override
        public @NonNull Polyline.Options geodesic(boolean geodesic) {
            mDelegate.geodesic(geodesic);
            return this;
        }

        @Override
        public @NonNull Polyline.Options clickable(boolean clickable) {
            mDelegate.clickable(clickable);
            return this;
        }

        @Override
        public List<LatLng> getPoints() {
            return HuaweiLatLng.wrap(mDelegate.getPoints());
        }

        @Override
        public float getWidth() {
            return mDelegate.getWidth();
        }

        @Override
        public int getColor() {
            return mDelegate.getColor();
        }

        @Override
        public @NonNull Cap getStartCap() {
            return HuaweiCap.wrap(mDelegate.getStartCap());
        }

        @Override
        public @NonNull Cap getEndCap() {
            return HuaweiCap.wrap(mDelegate.getEndCap());
        }

        @Override
        public int getJointType() {
            return mDelegate.getJointType();
        }

        @Override
        public @Nullable List<PatternItem> getPattern() {
            return HuaweiPatternItem.wrap(mDelegate.getPattern());
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


        static com.huawei.hms.maps.model.PolylineOptions unwrap(Polyline.Options wrapped) {
            return ((HuaweiPolyline.Options) wrapped).mDelegate;
        }
    }

}
