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

import java.util.Collections;
import java.util.List;

import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.JointType;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.LatLng;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.PatternItem;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.Polygon;

@RestrictTo(LIBRARY)
public class NopPolygon {

    private NopPolygon() {}


    public static class Options implements Polygon.Options {
        public static final @NonNull Polygon.Options NULL = new NopPolygon.Options();

        private Options() {}

        @Override public @NonNull Polygon.Options add(LatLng point) {
            // Not supported, no-op.
            return this;
        }

        @Override public @NonNull Polygon.Options add(LatLng... points) {
            // Not supported, no-op.
            return this;
        }

        @Override public @NonNull Polygon.Options addAll(Iterable<LatLng> points) {
            // Not supported, no-op.
            return this;
        }

        @Override public @NonNull Polygon.Options addHole(Iterable<LatLng> points) {
            // Not supported, no-op.
            return this;
        }

        @Override public @NonNull Polygon.Options strokeWidth(float width) {
            // Not supported, no-op.
            return this;
        }

        @Override public @NonNull Polygon.Options strokeColor(int color) {
            // Not supported, no-op.
            return this;
        }

        @Override public @NonNull Polygon.Options strokeJointType(int jointType) {
            // Not supported, no-op.
            return this;
        }

        @Override public @NonNull Polygon.Options strokePattern(
                @Nullable List<PatternItem> pattern) {
            // Not supported, no-op.
            return this;
        }

        @Override public @NonNull Polygon.Options fillColor(int color) {
            // Not supported, no-op.
            return this;
        }

        @Override public @NonNull Polygon.Options zIndex(float zIndex) {
            // Not supported, no-op.
            return this;
        }

        @Override public @NonNull Polygon.Options visible(boolean visible) {
            // Not supported, no-op.
            return this;
        }

        @Override public @NonNull Polygon.Options geodesic(boolean geodesic) {
            // Not supported, no-op.
            return this;
        }

        @Override public @NonNull Polygon.Options clickable(boolean clickable) {
            // Not supported, no-op.
            return this;
        }

        @NonNull @Override public List<LatLng> getPoints() {
            return Collections.emptyList(); // Not supported, fallback to default.
        }

        @NonNull @Override public List<List<LatLng>> getHoles() {
            return Collections.emptyList(); // Not supported, fallback to default.
        }

        @Override public float getStrokeWidth() {
            return 0f; // Not supported, fallback to default.
        }

        @Override public int getStrokeColor() {
            return 0; // Not supported, fallback to default.
        }

        @Override public int getStrokeJointType() {
            return JointType.DEFAULT; // Not supported, fallback to default.
        }

        @Override public @Nullable List<PatternItem> getStrokePattern() {
            return null; // Not supported, fallback to default.
        }

        @Override public int getFillColor() {
            return 0; // Not supported, fallback to default.
        }

        @Override public float getZIndex() {
            return 0f; // Not supported, fallback to default.
        }

        @Override public boolean isVisible() {
            return false; // Not supported, fallback to default.
        }

        @Override public boolean isGeodesic() {
            return false; // Not supported, fallback to default.
        }

        @Override public boolean isClickable() {
            return false; // Not supported, fallback to default.
        }
    }

}
