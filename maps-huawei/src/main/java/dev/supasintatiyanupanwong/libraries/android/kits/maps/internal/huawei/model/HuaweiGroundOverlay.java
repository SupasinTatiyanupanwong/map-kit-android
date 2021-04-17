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

import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.BitmapDescriptor;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.GroundOverlay;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.LatLng;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.LatLngBounds;

class HuaweiGroundOverlay implements GroundOverlay {

    private final com.huawei.hms.maps.model.GroundOverlay mDelegate;

    private HuaweiGroundOverlay(com.huawei.hms.maps.model.GroundOverlay delegate) {
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
    public void setPosition(LatLng latLng) {
        mDelegate.setPosition(HuaweiLatLng.unwrap(latLng));
    }

    @Override
    public LatLng getPosition() {
        return HuaweiLatLng.wrap(mDelegate.getPosition());
    }

    @Override
    public void setImage(@NonNull BitmapDescriptor imageDescriptor) {
        mDelegate.setImage(HuaweiBitmapDescriptor.unwrap(imageDescriptor));
    }

    @Override
    public float getWidth() {
        return mDelegate.getWidth();
    }

    @Override
    public float getHeight() {
        return mDelegate.getHeight();
    }

    @Override
    public void setDimensions(float width) {
        mDelegate.setDimensions(width);
    }

    @Override
    public void setDimensions(float width, float height) {
        mDelegate.setDimensions(width, height);
    }

    @Override
    public LatLngBounds getBounds() {
        return HuaweiLatLngBounds.wrap(mDelegate.getBounds());
    }

    @Override
    public void setPositionFromBounds(LatLngBounds bounds) {
        mDelegate.setPositionFromBounds(HuaweiLatLngBounds.unwrap(bounds));
    }

    @Override
    public float getBearing() {
        return mDelegate.getBearing();
    }

    @Override
    public void setBearing(float bearing) {
        mDelegate.setBearing(bearing);
    }

    @Override
    public float getZIndex() {
        return mDelegate.getZIndex();
    }

    @Override
    public void setZIndex(float zIndex) {
        mDelegate.setZIndex(zIndex);
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
    public void setTransparency(float transparency) {
        mDelegate.setTransparency(transparency);
    }

    @Override
    public float getTransparency() {
        return mDelegate.getTransparency();
    }

    @Override
    public void setTag(@Nullable Object tag) {
        mDelegate.setTag(tag);
    }

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

        HuaweiGroundOverlay that = (HuaweiGroundOverlay) obj;

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


    static GroundOverlay wrap(com.huawei.hms.maps.model.GroundOverlay delegate) {
        return new HuaweiGroundOverlay(delegate);
    }


    static class Options implements GroundOverlay.Options {
        private final com.huawei.hms.maps.model.GroundOverlayOptions mDelegate;

        Options() {
            mDelegate = new com.huawei.hms.maps.model.GroundOverlayOptions();
        }

        @Override
        public @NonNull GroundOverlay.Options image(@NonNull BitmapDescriptor imageDescriptor) {
            mDelegate.image(HuaweiBitmapDescriptor.unwrap(imageDescriptor));
            return this;
        }

        @Override
        public @NonNull GroundOverlay.Options anchor(float anchorU, float anchorV) {
            mDelegate.anchor(anchorU, anchorV);
            return this;
        }

        @Override
        public @NonNull GroundOverlay.Options position(@NonNull LatLng location, float width) {
            mDelegate.position(HuaweiLatLng.unwrap(location), width);
            return this;
        }

        @Override
        public @NonNull GroundOverlay.Options position(
                @NonNull LatLng location,
                float width,
                float height) {
            mDelegate.position(HuaweiLatLng.unwrap(location), width, height);
            return this;
        }

        @Override
        public @NonNull GroundOverlay.Options positionFromBounds(LatLngBounds bounds) {
            mDelegate.positionFromBounds(HuaweiLatLngBounds.unwrap(bounds));
            return this;
        }

        @Override
        public @NonNull GroundOverlay.Options bearing(float bearing) {
            mDelegate.bearing(bearing);
            return this;
        }

        @Override
        public @NonNull GroundOverlay.Options zIndex(float zIndex) {
            mDelegate.zIndex(zIndex);
            return this;
        }

        @Override
        public @NonNull GroundOverlay.Options visible(boolean visible) {
            mDelegate.visible(visible);
            return this;
        }

        @Override
        public @NonNull GroundOverlay.Options transparency(float transparency) {
            mDelegate.transparency(transparency);
            return this;
        }

        @Override
        public @NonNull GroundOverlay.Options clickable(boolean clickable) {
            mDelegate.clickable(clickable);
            return this;
        }

        @Override
        public BitmapDescriptor getImage() {
            return HuaweiBitmapDescriptor.wrap(mDelegate.getImage());
        }

        @Override
        public LatLng getLocation() {
            return HuaweiLatLng.wrap(mDelegate.getLocation());
        }

        @Override
        public float getWidth() {
            return mDelegate.getWidth();
        }

        @Override
        public float getHeight() {
            return mDelegate.getHeight();
        }

        @Override
        public LatLngBounds getBounds() {
            return HuaweiLatLngBounds.wrap(mDelegate.getBounds());
        }

        @Override
        public float getBearing() {
            return mDelegate.getBearing();
        }

        @Override
        public float getZIndex() {
            return mDelegate.getZIndex();
        }

        @Override
        public float getTransparency() {
            return mDelegate.getTransparency();
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
        public boolean isVisible() {
            return mDelegate.isVisible();
        }

        @Override
        public boolean isClickable() {
            return mDelegate.isClickable();
        }


        static com.huawei.hms.maps.model.GroundOverlayOptions unwrap(
                GroundOverlay.Options wrapped) {
            return ((HuaweiGroundOverlay.Options) wrapped).mDelegate;
        }
    }

}
