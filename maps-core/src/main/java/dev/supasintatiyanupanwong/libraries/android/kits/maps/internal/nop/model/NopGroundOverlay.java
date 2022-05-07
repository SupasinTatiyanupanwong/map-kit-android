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

package dev.supasintatiyanupanwong.libraries.android.kits.maps.internal.nop.model;

import static androidx.annotation.RestrictTo.Scope.LIBRARY;

import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;

import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.BitmapDescriptor;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.GroundOverlay;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.LatLng;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.LatLngBounds;

@RestrictTo(LIBRARY)
public class NopGroundOverlay {

    private NopGroundOverlay() {}


    public static class Options implements GroundOverlay.Options {
        public static final @NonNull GroundOverlay.Options NULL = new Options();

        private Options() {}

        @Override public @NonNull GroundOverlay.Options image(@NonNull BitmapDescriptor imageDescriptor) {
            // Not supported, no-op.
            return this;
        }

        @Override public @NonNull GroundOverlay.Options anchor(float anchorU, float anchorV) {
            // Not supported, no-op.
            return this;
        }

        @Override public @NonNull GroundOverlay.Options position(
                @NonNull LatLng location,
                float width) {
            // Not supported, no-op.
            return this;
        }

        @Override public @NonNull GroundOverlay.Options position(
                @NonNull LatLng location,
                float width,
                float height) {
            // Not supported, no-op.
            return this;
        }

        @Override public @NonNull GroundOverlay.Options positionFromBounds(LatLngBounds bounds) {
            // Not supported, no-op.
            return this;
        }

        @Override public @NonNull GroundOverlay.Options bearing(float bearing) {
            // Not supported, no-op.
            return this;
        }

        @Override public @NonNull GroundOverlay.Options zIndex(float zIndex) {
            // Not supported, no-op.
            return this;
        }

        @Override public @NonNull GroundOverlay.Options visible(boolean visible) {
            // Not supported, no-op.
            return this;
        }

        @Override public @NonNull GroundOverlay.Options transparency(float transparency) {
            // Not supported, no-op.
            return this;
        }

        @Override public @NonNull GroundOverlay.Options clickable(boolean clickable) {
            // Not supported, no-op.
            return this;
        }

        @Override public BitmapDescriptor getImage() {
            return NopBitmapDescriptor.NULL; // Not supported, null object for API safe.
        }

        @Override public LatLng getLocation() {
            return NopLatLng.NULL; // Not supported, null object for API safe.
        }

        @Override public float getWidth() {
            return 0f; // Not supported, fallback to default.
        }

        @Override public float getHeight() {
            return 0f; // Not supported, fallback to default.
        }

        @Override public LatLngBounds getBounds() {
            return NopLatLngBounds.NULL; // Not supported, null object for API safe.
        }

        @Override public float getBearing() {
            return 0f; // Not supported, fallback to default.
        }

        @Override public float getZIndex() {
            return 0f; // Not supported, fallback to default.
        }

        @Override public float getTransparency() {
            return 0f; // Not supported, fallback to default.
        }

        @Override public float getAnchorU() {
            return 0f; // Not supported, fallback to default.
        }

        @Override public float getAnchorV() {
            return 0f; // Not supported, fallback to default.
        }

        @Override public boolean isVisible() {
            return false; // Not supported, fallback to default.
        }

        @Override public boolean isClickable() {
            return false; // Not supported, fallback to default.
        }
    }

}
