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

import org.jetbrains.annotations.Contract;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import dev.supasintatiyanupanwong.libraries.android.kits.maps.internal.nil.model.NilCap;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.Cap;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.JointType;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.LatLng;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.PatternItem;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.Polyline;

@RestrictTo(LIBRARY)
public class AmazonPolyline implements Polyline {

    private final @NonNull com.amazon.geo.mapsv2.model.Polyline mDelegate;
    private final @NonNull AmazonMapClient$TagManager mTagManager;

    private AmazonPolyline(
            @NonNull com.amazon.geo.mapsv2.model.Polyline delegate,
            @NonNull AmazonMapClient$TagManager tagManager
    ) {
        mDelegate = delegate;
        mTagManager = tagManager;
    }

    @Override public void remove() {
        mTagManager.remove(getId());
        mDelegate.remove();
    }

    @Override public String getId() {
        return mDelegate.getId();
    }

    @Override public void setPoints(List<LatLng> points) {
        mDelegate.setPoints(AmazonLatLng.unwrap(points));
    }

    @Override public List<LatLng> getPoints() {
        return AmazonLatLng.wrap(mDelegate.getPoints());
    }

    @Override public void setWidth(float width) {
        mDelegate.setWidth(width);
    }

    @Override public float getWidth() {
        return mDelegate.getWidth();
    }

    @Override public void setColor(int color) {
        mDelegate.setColor(color);
    }

    @Override public int getColor() {
        return mDelegate.getColor();
    }

    @Override public void setStartCap(@NonNull Cap startCap) {
        // Not supported, no-op.
    }

    @Override public @NonNull Cap getStartCap() {
        return NilCap.INSTANCE; // Not supported, null object for API safe.
    }

    @Override public void setEndCap(@NonNull Cap endCap) {
        // Not supported, no-op.
    }

    @Override public @NonNull Cap getEndCap() {
        return NilCap.INSTANCE; // Not supported, null object for API safe.
    }

    @Override public void setJointType(int jointType) {
        // Not supported, no-op.
    }

    @Override public int getJointType() {
        return JointType.DEFAULT; // Not supported, fallback to default.
    }

    @Override public void setPattern(@Nullable List<PatternItem> pattern) {
        // Not supported, no-op.
    }

    @Override public @Nullable List<PatternItem> getPattern() {
        return null; // Not supported.
    }

    @Override public void setZIndex(float zIndex) {
        mDelegate.setZIndex(zIndex);
    }

    @Override public float getZIndex() {
        return mDelegate.getZIndex();
    }

    @Override public void setVisible(boolean visible) {
        mDelegate.setVisible(visible);
    }

    @Override public boolean isVisible() {
        return mDelegate.isVisible();
    }

    @Override public void setGeodesic(boolean geodesic) {
        mDelegate.setGeodesic(geodesic);
    }

    @Override public boolean isGeodesic() {
        return mDelegate.isGeodesic();
    }

    @Override public void setClickable(boolean clickable) {
        // Not supported, no-op.
    }

    @Override public boolean isClickable() {
        return false; // Not supported.
    }

    @Override public void setTag(@Nullable Object tag) {
        mTagManager.setTag(getId(), tag);
    }

    @Override public @Nullable Object getTag() {
        return mTagManager.getTag(getId());
    }

    @Override public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        AmazonPolyline that = (AmazonPolyline) obj;

        return mDelegate.equals(that.mDelegate) &&
                Objects.equals(mTagManager.getTag(getId()), mTagManager.getTag(that.getId()));
    }

    @Override public int hashCode() {
        return mDelegate.hashCode();
    }

    @Override public @NonNull String toString() {
        return mDelegate.toString();
    }


    @Contract("null, _ -> null; !null, _ -> !null")
    static @Nullable Polyline wrap(
            @Nullable com.amazon.geo.mapsv2.model.Polyline delegate,
            @NonNull AmazonMapClient$TagManager tagManager
    ) {
        return delegate == null ? null : new AmazonPolyline(delegate, tagManager);
    }


    public static class Options implements Polyline.Options {
        private final com.amazon.geo.mapsv2.model.PolylineOptions mDelegate;

        public Options() {
            mDelegate = new com.amazon.geo.mapsv2.model.PolylineOptions();
        }

        @Override public @NonNull Polyline.Options add(LatLng point) {
            mDelegate.add(AmazonLatLng.unwrap(point));
            return this;
        }

        @Override public @NonNull Polyline.Options add(LatLng... points) {
            mDelegate.addAll(AmazonLatLng.unwrap(Arrays.asList(points)));
            return this;
        }

        @Override public @NonNull Polyline.Options addAll(Iterable<LatLng> points) {
            mDelegate.addAll(AmazonLatLng.unwrap(points));
            return this;
        }

        @Override public @NonNull Polyline.Options width(float width) {
            mDelegate.width(width);
            return this;
        }

        @Override public @NonNull Polyline.Options color(int color) {
            mDelegate.color(color);
            return this;
        }

        @Override public @NonNull Polyline.Options startCap(@NonNull Cap startCap) {
            // Not supported, no-op.
            return this;
        }

        @Override public @NonNull Polyline.Options endCap(@NonNull Cap endCap) {
            // Not supported, no-op.
            return this;
        }

        @Override public @NonNull Polyline.Options jointType(int jointType) {
            // Not supported, no-op.
            return this;
        }

        @Override public @NonNull Polyline.Options pattern(@Nullable List<PatternItem> pattern) {
            // Not supported, no-op.
            return this;
        }

        @Override public @NonNull Polyline.Options zIndex(float zIndex) {
            mDelegate.zIndex(zIndex);
            return this;
        }

        @Override public @NonNull Polyline.Options visible(boolean visible) {
            mDelegate.visible(visible);
            return this;
        }

        @Override public @NonNull Polyline.Options geodesic(boolean geodesic) {
            mDelegate.geodesic(geodesic);
            return this;
        }

        @Override public @NonNull Polyline.Options clickable(boolean clickable) {
            // Not supported, no-op.
            return this;
        }

        @Override public List<LatLng> getPoints() {
            return AmazonLatLng.wrap(mDelegate.getPoints());
        }

        @Override public float getWidth() {
            return mDelegate.getWidth();
        }

        @Override public int getColor() {
            return mDelegate.getColor();
        }

        @Override public @NonNull Cap getStartCap() {
            return NilCap.INSTANCE; // Not supported, null object for API safe.
        }

        @Override public @NonNull Cap getEndCap() {
            return NilCap.INSTANCE; // Not supported, null object for API safe.
        }

        @Override public int getJointType() {
            return JointType.DEFAULT; // Not supported, fallback to default.
        }

        @Override public @Nullable List<PatternItem> getPattern() {
            return null; // Not supported.
        }

        @Override public float getZIndex() {
            return mDelegate.getZIndex();
        }

        @Override public boolean isVisible() {
            return mDelegate.isVisible();
        }

        @Override public boolean isGeodesic() {
            return mDelegate.isGeodesic();
        }

        @Override public boolean isClickable() {
            return false; // Not supported.
        }


        static @Nullable com.amazon.geo.mapsv2.model.PolylineOptions unwrap(
                @Nullable Polyline.Options wrapped
        ) {
            return wrapped == null ? null : ((Options) wrapped).mDelegate;
        }
    }
}
