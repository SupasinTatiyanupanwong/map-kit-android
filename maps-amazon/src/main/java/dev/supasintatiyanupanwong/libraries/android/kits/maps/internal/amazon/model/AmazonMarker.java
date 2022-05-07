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

import java.util.Objects;

import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.BitmapDescriptor;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.LatLng;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.Marker;

@RestrictTo(LIBRARY)
public class AmazonMarker implements Marker {

    private final com.amazon.geo.mapsv2.model.Marker mDelegate;

    private @Nullable Object mTag; // Providing tag support for Amazon's Marker

    private AmazonMarker(@NonNull com.amazon.geo.mapsv2.model.Marker delegate) {
        mDelegate = delegate;
    }

    @Override
    public void remove() {
        mDelegate.remove();
    }

    @Override
    public @NonNull String getId() {
        return mDelegate.getId();
    }

    @Override
    public void setPosition(@NonNull LatLng latLng) {
        mDelegate.setPosition(AmazonLatLng.unwrap(latLng));
    }

    @Override
    public @NonNull LatLng getPosition() {
        return AmazonLatLng.wrap(mDelegate.getPosition());
    }

    @Override
    public void setZIndex(float zIndex) {
        // Not supported, no-op.
    }

    @Override
    public float getZIndex() {
        return 0f; // Not supported, fallback to default.
    }

    @Override
    public void setIcon(@Nullable BitmapDescriptor iconDescriptor) {
        mDelegate.setIcon(AmazonBitmapDescriptor.unwrap(iconDescriptor));
    }

    @Override
    public void setAnchor(float anchorU, float anchorV) {
        mDelegate.setAnchor(anchorU, anchorV);
    }

    @Override
    public void setInfoWindowAnchor(float anchorU, float anchorV) {
        mDelegate.setInfoWindowAnchor(anchorU, anchorV);
    }

    @Override
    public void setTitle(@Nullable String title) {
        mDelegate.setTitle(title);
    }

    @Override
    public String getTitle() {
        return mDelegate.getTitle();
    }

    @Override
    public void setSnippet(@Nullable String snippet) {
        mDelegate.setSnippet(snippet);
    }

    @Override
    public String getSnippet() {
        return mDelegate.getSnippet();
    }

    @Override
    public void setDraggable(boolean draggable) {
        mDelegate.setDraggable(draggable);
    }

    @Override
    public boolean isDraggable() {
        return mDelegate.isDraggable();
    }

    @Override
    public void showInfoWindow() {
        mDelegate.showInfoWindow();
    }

    @Override
    public void hideInfoWindow() {
        mDelegate.hideInfoWindow();
    }

    @Override
    public boolean isInfoWindowShown() {
        return mDelegate.isInfoWindowShown();
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
    public void setFlat(boolean flat) {
        mDelegate.setFlat(false);
    }

    @Override
    public boolean isFlat() {
        return mDelegate.isFlat();
    }

    @Override
    public void setRotation(float rotation) {
        mDelegate.setRotation(rotation);
    }

    @Override
    public float getRotation() {
        return mDelegate.getRotation();
    }

    @Override
    public void setAlpha(float alpha) {
        mDelegate.setAlpha(alpha);
    }

    @Override
    public float getAlpha() {
        return mDelegate.getAlpha();
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

        AmazonMarker that = (AmazonMarker) obj;

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


    static Marker wrap(com.amazon.geo.mapsv2.model.Marker delegate) {
        return new AmazonMarker(delegate);
    }


    public static class Options implements Marker.Options {
        private final com.amazon.geo.mapsv2.model.MarkerOptions mDelegate;

        public Options() {
            mDelegate = new com.amazon.geo.mapsv2.model.MarkerOptions();
        }

        @Override
        public @NonNull Marker.Options position(@NonNull LatLng latLng) {
            mDelegate.position(AmazonLatLng.unwrap(latLng));
            return this;
        }

        @Override
        public @NonNull Marker.Options zIndex(float zIndex) {
            // Not supported, no-op.
            return this;
        }

        @Override
        public @NonNull Marker.Options icon(@Nullable BitmapDescriptor iconDescriptor) {
            mDelegate.icon(AmazonBitmapDescriptor.unwrap(iconDescriptor));
            return this;
        }

        @Override
        public @NonNull Marker.Options anchor(float anchorU, float anchorV) {
            mDelegate.anchor(anchorU, anchorV);
            return this;
        }

        @Override
        public @NonNull Marker.Options infoWindowAnchor(float anchorU, float anchorV) {
            mDelegate.infoWindowAnchor(anchorU, anchorV);
            return this;
        }

        @Override
        public @NonNull Marker.Options title(@Nullable String title) {
            mDelegate.title(title);
            return this;
        }

        @Override
        public @NonNull Marker.Options snippet(@Nullable String snippet) {
            mDelegate.snippet(snippet);
            return this;
        }

        @Override
        public @NonNull Marker.Options draggable(boolean draggable) {
            mDelegate.draggable(draggable);
            return this;
        }

        @Override
        public @NonNull Marker.Options visible(boolean visible) {
            mDelegate.visible(visible);
            return this;
        }

        @Override
        public @NonNull Marker.Options flat(boolean flat) {
            mDelegate.flat(flat);
            return this;
        }

        @Override
        public @NonNull Marker.Options rotation(float rotation) {
            mDelegate.rotation(rotation);
            return this;
        }

        @Override
        public @NonNull Marker.Options alpha(float alpha) {
            mDelegate.alpha(alpha);
            return this;
        }

        @Override
        public LatLng getPosition() {
            return AmazonLatLng.wrap(mDelegate.getPosition());
        }

        @Override
        public String getTitle() {
            return mDelegate.getTitle();
        }

        @Override
        public String getSnippet() {
            return mDelegate.getSnippet();
        }

        @Override
        public @Nullable BitmapDescriptor getIcon() {
            return AmazonBitmapDescriptor.wrap(mDelegate.getIcon());
        }

        @Override
        public float getAnchorU() {
            return mDelegate.getAnchorU();
        }

        @Override
        public float getAnchorV() {
            return mDelegate.getAnchorV();
        }

        @Override
        public boolean isDraggable() {
            return mDelegate.isDraggable();
        }

        @Override
        public boolean isVisible() {
            return mDelegate.isVisible();
        }

        @Override
        public boolean isFlat() {
            return mDelegate.isFlat();
        }

        @Override
        public float getRotation() {
            return mDelegate.getRotation();
        }

        @Override
        public float getInfoWindowAnchorU() {
            return mDelegate.getInfoWindowAnchorU();
        }

        @Override
        public float getInfoWindowAnchorV() {
            return mDelegate.getInfoWindowAnchorV();
        }

        @Override
        public float getAlpha() {
            return mDelegate.getAlpha();
        }

        @Override
        public float getZIndex() {
            return 0f; // Not supported, fallback to default.
        }


        static com.amazon.geo.mapsv2.model.MarkerOptions unwrap(Marker.Options wrapped) {
            return ((Options) wrapped).mDelegate;
        }
    }

}
