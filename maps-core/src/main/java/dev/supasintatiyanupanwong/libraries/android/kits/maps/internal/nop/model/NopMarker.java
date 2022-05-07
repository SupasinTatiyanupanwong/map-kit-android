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
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;

import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.BitmapDescriptor;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.LatLng;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.Marker;

@RestrictTo(LIBRARY)
public class NopMarker {

    private NopMarker() {}


    public static class Options implements Marker.Options {
        public static final @NonNull Marker.Options NULL = new Options();

        private Options() {}

        @Override public @NonNull Marker.Options position(@NonNull LatLng latLng) {
            // Not supported, no-op.
            return this;
        }

        @Override public @NonNull Marker.Options zIndex(float zIndex) {
            // Not supported, no-op.
            return this;
        }

        @Override public @NonNull Marker.Options icon(@Nullable BitmapDescriptor iconDescriptor) {
            // Not supported, no-op.
            return this;
        }

        @Override public @NonNull Marker.Options anchor(float anchorU, float anchorV) {
            // Not supported, no-op.
            return this;
        }

        @Override public @NonNull Marker.Options infoWindowAnchor(float anchorU, float anchorV) {
            // Not supported, no-op.
            return this;
        }

        @Override public @NonNull Marker.Options title(@Nullable String title) {
            // Not supported, no-op.
            return this;
        }

        @Override public @NonNull Marker.Options snippet(@Nullable String snippet) {
            // Not supported, no-op.
            return this;
        }

        @Override public @NonNull Marker.Options draggable(boolean draggable) {
            // Not supported, no-op.
            return this;
        }

        @Override public @NonNull Marker.Options visible(boolean visible) {
            // Not supported, no-op.
            return this;
        }

        @Override public @NonNull Marker.Options flat(boolean flat) {
            // Not supported, no-op.
            return this;
        }

        @Override public @NonNull Marker.Options rotation(float rotation) {
            // Not supported, no-op.
            return this;
        }

        @Override public @NonNull Marker.Options alpha(float alpha) {
            // Not supported, no-op.
            return this;
        }

        @Override public LatLng getPosition() {
            return NopLatLng.NULL; // Not supported, null object for API safe.
        }

        @Override public String getTitle() {
            return null; // Not supported, fallback to default.
        }

        @Override public String getSnippet() {
            return null; // Not supported, fallback to default.
        }

        @Override public @Nullable BitmapDescriptor getIcon() {
            return null; // Not supported, fallback to default.
        }

        @Override public float getAnchorU() {
            return 0f; // Not supported, fallback to default.
        }

        @Override public float getAnchorV() {
            return 0f; // Not supported, fallback to default.
        }

        @Override public boolean isDraggable() {
            return false; // Not supported, fallback to default.
        }

        @Override public boolean isVisible() {
            return false; // Not supported, fallback to default.
        }

        @Override public boolean isFlat() {
            return false; // Not supported, fallback to default.
        }

        @Override public float getRotation() {
            return 0f; // Not supported, fallback to default.
        }

        @Override public float getInfoWindowAnchorU() {
            return 0f; // Not supported, fallback to default.
        }

        @Override public float getInfoWindowAnchorV() {
            return 0f; // Not supported, fallback to default.
        }

        @Override public float getAlpha() {
            return 0f; // Not supported, fallback to default.
        }

        @Override public float getZIndex() {
            return 0f; // Not supported, fallback to default.
        }
    }

}
