/*
 * Copyright (C) 2020 Supasin Tatiyanupanwong
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
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

import java.util.List;

import me.tatiyanupanwong.supasin.android.libraries.kits.maps.model.Circle;
import me.tatiyanupanwong.supasin.android.libraries.kits.maps.model.LatLng;
import me.tatiyanupanwong.supasin.android.libraries.kits.maps.model.PatternItem;

class GoogleCircle implements Circle {

    private final com.google.android.gms.maps.model.Circle mDelegate;

    private GoogleCircle(com.google.android.gms.maps.model.Circle delegate) {
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
    public void setCenter(LatLng center) {
        mDelegate.setCenter(GoogleLatLng.unwrap(center));
    }

    @Override
    public LatLng getCenter() {
        return GoogleLatLng.wrap(mDelegate.getCenter());
    }

    @Override
    public void setRadius(double radius) {
        mDelegate.setRadius(radius);
    }

    @Override
    public double getRadius() {
        return mDelegate.getRadius();
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

        GoogleCircle that = (GoogleCircle) obj;

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


    static Circle wrap(com.google.android.gms.maps.model.Circle delegate) {
        return new GoogleCircle(delegate);
    }


    static class Options implements Circle.Options {
        private final com.google.android.gms.maps.model.CircleOptions mDelegate;

        Options() {
            mDelegate = new com.google.android.gms.maps.model.CircleOptions();
        }

        @NonNull
        @Override
        public Circle.Options center(LatLng center) {
            mDelegate.center(GoogleLatLng.unwrap(center));
            return this;
        }

        @NonNull
        @Override
        public Circle.Options radius(double radius) {
            mDelegate.radius(radius);
            return this;
        }

        @NonNull
        @Override
        public Circle.Options strokeWidth(float width) {
            mDelegate.strokeWidth(width);
            return this;
        }

        @NonNull
        @Override
        public Circle.Options strokeColor(int color) {
            mDelegate.strokeColor(color);
            return this;
        }

        @NonNull
        @Override
        public Circle.Options strokePattern(@Nullable List<PatternItem> pattern) {
            mDelegate.strokePattern(GooglePatternItem.unwrap(pattern));
            return this;
        }

        @NonNull
        @Override
        public Circle.Options fillColor(int color) {
            mDelegate.fillColor(color);
            return this;
        }

        @NonNull
        @Override
        public Circle.Options zIndex(float zIndex) {
            mDelegate.zIndex(zIndex);
            return this;
        }

        @NonNull
        @Override
        public Circle.Options visible(boolean visible) {
            mDelegate.visible(visible);
            return this;
        }

        @NonNull
        @Override
        public Circle.Options clickable(boolean clickable) {
            mDelegate.clickable(clickable);
            return this;
        }

        @Override
        public LatLng getCenter() {
            return GoogleLatLng.wrap(mDelegate.getCenter());
        }

        @Override
        public double getRadius() {
            return mDelegate.getRadius();
        }

        @Override
        public float getStrokeWidth() {
            return mDelegate.getStrokeWidth();
        }

        @Override
        public int getStrokeColor() {
            return mDelegate.getStrokeColor();
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
        public boolean isClickable() {
            return mDelegate.isClickable();
        }


        static com.google.android.gms.maps.model.CircleOptions unwrap(Circle.Options wrapped) {
            return ((GoogleCircle.Options) wrapped).mDelegate;
        }
    }

}
