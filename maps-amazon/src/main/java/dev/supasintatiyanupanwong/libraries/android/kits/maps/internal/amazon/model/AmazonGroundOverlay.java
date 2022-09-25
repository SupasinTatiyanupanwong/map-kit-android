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
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.GroundOverlay;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.LatLng;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.LatLngBounds;

@RestrictTo(LIBRARY)
public class AmazonGroundOverlay implements GroundOverlay {

    private final @NonNull com.amazon.geo.mapsv2.model.GroundOverlay mDelegate;

    private @Nullable Object mTag; // Providing tag support for Amazon's GroundOverlay

    private AmazonGroundOverlay(@NonNull com.amazon.geo.mapsv2.model.GroundOverlay delegate) {
        mDelegate = delegate;
    }

    @Override public void remove() {
        mDelegate.remove();
    }

    @Override public String getId() {
        return mDelegate.getId();
    }

    @Override public void setPosition(LatLng latLng) {
        mDelegate.setPosition(AmazonLatLng.unwrap(latLng));
    }

    @Override public LatLng getPosition() {
        return AmazonLatLng.wrap(mDelegate.getPosition());
    }

    @Override public void setImage(@NonNull BitmapDescriptor imageDescriptor) {
        mDelegate.setImage(AmazonBitmapDescriptor.unwrap(imageDescriptor));
    }

    @Override public float getWidth() {
        return mDelegate.getWidth();
    }

    @Override public float getHeight() {
        return mDelegate.getHeight();
    }

    @Override public void setDimensions(float width) {
        mDelegate.setDimensions(width);
    }

    @Override public void setDimensions(float width, float height) {
        mDelegate.setDimensions(width, height);
    }

    @Override public LatLngBounds getBounds() {
        return AmazonLatLngBounds.wrap(mDelegate.getBounds());
    }

    @Override public void setPositionFromBounds(LatLngBounds bounds) {
        mDelegate.setPositionFromBounds(AmazonLatLngBounds.unwrap(bounds));
    }

    @Override public float getBearing() {
        return mDelegate.getBearing();
    }

    @Override public void setBearing(float bearing) {
        mDelegate.setBearing(bearing);
    }

    @Override public float getZIndex() {
        return mDelegate.getZIndex();
    }

    @Override public void setZIndex(float zIndex) {
        mDelegate.setZIndex(zIndex);
    }

    @Override public void setVisible(boolean visible) {
        mDelegate.setVisible(visible);
    }

    @Override public boolean isVisible() {
        return mDelegate.isVisible();
    }

    @Override public void setClickable(boolean clickable) {
        // Not supported, no-op.
    }

    @Override public boolean isClickable() {
        return false; // Not supported.
    }

    @Override public void setTransparency(float transparency) {
        mDelegate.setTransparency(transparency);
    }

    @Override public float getTransparency() {
        return mDelegate.getTransparency();
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

        AmazonGroundOverlay that = (AmazonGroundOverlay) obj;

        return mDelegate.equals(that.mDelegate) && Objects.equals(mTag, that.mTag);
    }

    @Override public int hashCode() {
        return mDelegate.hashCode();
    }

    @Override public @NonNull String toString() {
        return mDelegate.toString();
    }


    static @Nullable GroundOverlay wrap(
            @Nullable com.amazon.geo.mapsv2.model.GroundOverlay delegate) {
        return delegate == null ?  null : new AmazonGroundOverlay(delegate);
    }


    public static class Options implements GroundOverlay.Options {
        private final @NonNull com.amazon.geo.mapsv2.model.GroundOverlayOptions mDelegate;

        public Options() {
            mDelegate = new com.amazon.geo.mapsv2.model.GroundOverlayOptions();
        }

        @Override public @NonNull GroundOverlay.Options image(
                @NonNull BitmapDescriptor imageDescriptor) {
            mDelegate.image(AmazonBitmapDescriptor.unwrap(imageDescriptor));
            return this;
        }

        @Override public @NonNull GroundOverlay.Options anchor(float anchorU, float anchorV) {
            mDelegate.anchor(anchorU, anchorV);
            return this;
        }

        @Override public @NonNull GroundOverlay.Options position(
                @NonNull LatLng location,
                float width) {
            mDelegate.position(AmazonLatLng.unwrap(location), width);
            return this;
        }

        @Override public @NonNull GroundOverlay.Options position(
                @NonNull LatLng location,
                float width,
                float height) {
            mDelegate.position(AmazonLatLng.unwrap(location), width, height);
            return this;
        }

        @Override public @NonNull GroundOverlay.Options positionFromBounds(LatLngBounds bounds) {
            mDelegate.positionFromBounds(AmazonLatLngBounds.unwrap(bounds));
            return this;
        }

        @Override public @NonNull GroundOverlay.Options bearing(float bearing) {
            mDelegate.bearing(bearing);
            return this;
        }

        @Override public @NonNull GroundOverlay.Options zIndex(float zIndex) {
            mDelegate.zIndex(zIndex);
            return this;
        }

        @Override public @NonNull GroundOverlay.Options visible(boolean visible) {
            mDelegate.visible(visible);
            return this;
        }

        @Override public @NonNull GroundOverlay.Options transparency(float transparency) {
            mDelegate.transparency(transparency);
            return this;
        }

        @Override public @NonNull GroundOverlay.Options clickable(boolean clickable) {
            // Not supported, no-op.
            return this;
        }

        @Override public BitmapDescriptor getImage() {
            return AmazonBitmapDescriptor.wrap(mDelegate.getImage());
        }

        @Override public @Nullable LatLng getLocation() {
            return AmazonLatLng.wrap(mDelegate.getLocation());
        }

        @Override public float getWidth() {
            return mDelegate.getWidth();
        }

        @Override public float getHeight() {
            return mDelegate.getHeight();
        }

        @Override public @Nullable LatLngBounds getBounds() {
            return AmazonLatLngBounds.wrap(mDelegate.getBounds());
        }

        @Override public float getBearing() {
            return mDelegate.getBearing();
        }

        @Override public float getZIndex() {
            return mDelegate.getZIndex();
        }

        @Override public float getTransparency() {
            return mDelegate.getTransparency();
        }

        @Override public float getAnchorU() {
            return mDelegate.getAnchorU();
        }

        @Override public float getAnchorV() {
            return mDelegate.getAnchorV();
        }

        @Override public boolean isVisible() {
            return mDelegate.isVisible();
        }

        @Override public boolean isClickable() {
            return false; // Not supported.
        }


        static @Nullable com.amazon.geo.mapsv2.model.GroundOverlayOptions unwrap(
                @Nullable GroundOverlay.Options wrapped) {
            return wrapped == null ? null : ((Options) wrapped).mDelegate;
        }
    }

}
