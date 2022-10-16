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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;

import com.tomtom.sdk.maps.display.marker.MarkerOptions;

import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.BitmapDescriptor;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.LatLng;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.Marker;

@RestrictTo(LIBRARY)
public class TomTomMarker implements Marker {

    private final @NonNull com.tomtom.sdk.maps.display.marker.Marker mDelegate;

    private TomTomMarker(@NonNull com.tomtom.sdk.maps.display.marker.Marker delegate) {
        mDelegate = delegate;
    }

    @Override public void remove() {
        mDelegate.remove();
    }

    @Override public @NonNull String getId() {
        return String.valueOf(mDelegate.a);
    }

    @Override public void setPosition(@NonNull LatLng latLng) {
        mDelegate.setCoordinate(TomTomLatLng.unwrap(latLng));
    }

    @Override public @NonNull LatLng getPosition() {
        return TomTomLatLng.wrap(mDelegate.getCoordinate());
    }

    @Override public void setZIndex(float zIndex) {
        // No-op on TomTom Map
    }

    @Override public float getZIndex() {
        return Float.NaN;
    }

    @Override public void setIcon(@Nullable BitmapDescriptor iconDescriptor) {
        mDelegate.setPinImage(TomTomBitmapDescriptor.unwrap(iconDescriptor));
    }

    @Override public void setAnchor(float anchorU, float anchorV) {
        // No-op on TomTom Map
    }

    @Override public void setInfoWindowAnchor(float anchorU, float anchorV) {
        // No-op on TomTom Map
    }

    @Override public void setTitle(@Nullable String title) {
        // No-op on TomTom Map
    }

    @Override public String getTitle() {
        return mDelegate.getBalloonText();
    }

    @Override public void setSnippet(@Nullable String snippet) {
        // No-op on TomTom Map
    }

    @Override public String getSnippet() {
        return "";
    }

    @Override public void setDraggable(boolean draggable) {
        // No-op on TomTom Map
    }

    @Override public boolean isDraggable() {
        return false;
    }

    @Override public void showInfoWindow() {
        mDelegate.select();
    }

    @Override public void hideInfoWindow() {
        mDelegate.deselect();
    }

    @Override public boolean isInfoWindowShown() {
        return mDelegate.isSelected();
    }

    @Override public void setVisible(boolean visible) {
        mDelegate.setVisible(visible);
    }

    @Override public boolean isVisible() {
        return mDelegate.isVisible();
    }

    @Override public void setFlat(boolean flat) {
        // No-op on TomTom Map
    }

    @Override public boolean isFlat() {
        return false;
    }

    @Override public void setRotation(float rotation) { // TODO
        // No-op on TomTom Map
    }

    @Override public float getRotation() { // TODO
        return Float.NaN;
    }

    @Override public void setAlpha(float alpha) { // TODO
        // No-op on TomTom Map
    }

    @Override public float getAlpha() { // TODO
        return Float.NaN;
    }

    @Override public void setTag(@Nullable Object tag) {
        mDelegate.setTag(tag);
    }

    @Override public @Nullable Object getTag() {
        return mDelegate.getTag();
    }

    @Override public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        TomTomMarker that = (TomTomMarker) obj;

        return mDelegate.equals(that.mDelegate);
    }

    @Override public int hashCode() {
        return mDelegate.hashCode();
    }

    @Override public @NonNull String toString() {
        return mDelegate.toString();
    }


    static @NonNull Marker wrap(@NonNull com.tomtom.sdk.maps.display.marker.Marker delegate) {
        return new TomTomMarker(delegate);
    }


    public static class Options implements Marker.Options {
        private LatLng mPosition;
        private @Nullable BitmapDescriptor mIcon;
        private @Nullable String mTitle;
        private boolean mVisible = true;

        public Options() {}

        @Override public @NonNull Marker.Options position(@NonNull LatLng latLng) {
            mPosition = latLng;
            return this;
        }

        @Override public @NonNull Marker.Options zIndex(float zIndex) {
            // No-op on TomTom Map
            return this;
        }

        @Override public @NonNull Marker.Options icon(@Nullable BitmapDescriptor iconDescriptor) {
            mIcon = iconDescriptor;
            return this;
        }

        @Override public @NonNull Marker.Options anchor(float anchorU, float anchorV) {
            // No-op on TomTom Map
            return this;
        }

        @Override public @NonNull Marker.Options infoWindowAnchor(float anchorU, float anchorV) {
            // No-op on TomTom Map
            return this;
        }

        @Override public @NonNull Marker.Options title(@Nullable String title) {
            mTitle = title;
            return this;
        }

        @Override public @NonNull Marker.Options snippet(@Nullable String snippet) {
            // No-op on TomTom Map
            return this;
        }

        @Override public @NonNull Marker.Options draggable(boolean draggable) {
            // No-op on TomTom Map
            return this;
        }

        @Override public @NonNull Marker.Options visible(boolean visible) {
            mVisible = visible;
            return this;
        }

        @Override public @NonNull Marker.Options flat(boolean flat) {
            // No-op on TomTom Map
            return this;
        }

        @Override public @NonNull Marker.Options rotation(float rotation) {
            // No-op on TomTom Map
            return this;
        }

        @Override public @NonNull Marker.Options alpha(float alpha) {
            // No-op on TomTom Map
            return this;
        }

        @Override public LatLng getPosition() {
            return mPosition;
        }

        @Override public String getTitle() {
            return mTitle;
        }

        @Override public String getSnippet() {
            return null;
        }

        @Override public @Nullable BitmapDescriptor getIcon() {
            return mIcon;
        }

        @Override public float getAnchorU() {
            return Float.NaN;
        }

        @Override public float getAnchorV() {
            return Float.NaN;
        }

        @Override public boolean isDraggable() {
            return false;
        }

        @Override public boolean isVisible() {
            return mVisible;
        }

        @Override public boolean isFlat() {
            return false;
        }

        @Override public float getRotation() {
            return Float.NaN;
        }

        @Override public float getInfoWindowAnchorU() {
            return Float.NaN;
        }

        @Override public float getInfoWindowAnchorV() {
            return Float.NaN;
        }

        @Override public float getAlpha() {
            return Float.NaN;
        }

        @Override public float getZIndex() {
            return Float.NaN;
        }


        static @NonNull com.tomtom.sdk.maps.display.marker.MarkerOptions unwrap(
                @NonNull Marker.Options wrapped
        ) {
            return new MarkerOptions(
                    TomTomLatLng.unwrap(wrapped.getPosition()),
                    TomTomBitmapDescriptor.unwrap(wrapped.getIcon()),
                    null,
                    null,
                    null,
                    null,
                    wrapped.getTitle() == null ? "" : wrapped.getTitle()
            );
        }
    }

}
