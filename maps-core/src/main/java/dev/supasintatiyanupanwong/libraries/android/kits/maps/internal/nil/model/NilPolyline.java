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

package dev.supasintatiyanupanwong.libraries.android.kits.maps.internal.nil.model;

import static androidx.annotation.RestrictTo.Scope.LIBRARY;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;

import java.util.Collections;
import java.util.List;

import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.Cap;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.JointType;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.LatLng;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.PatternItem;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.Polyline;

@RestrictTo(LIBRARY)
public class NilPolyline {

    private NilPolyline() {}


    public static class Options implements Polyline.Options {
        public static final @NonNull Polyline.Options INSTANCE = new Options();

        private Options() {}

        @Override public @NonNull Polyline.Options add(LatLng point) {
            // Not supported, no-op.
            return this;
        }

        @Override public @NonNull Polyline.Options add(LatLng... points) {
            // Not supported, no-op.
            return this;
        }

        @Override public @NonNull Polyline.Options addAll(Iterable<LatLng> points) {
            // Not supported, no-op.
            return this;
        }

        @Override public @NonNull Polyline.Options width(float width) {
            // Not supported, no-op.
            return this;
        }

        @Override public @NonNull Polyline.Options color(int color) {
            // Not supported, no-op.
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
            // Not supported, no-op.
            return this;
        }

        @Override public @NonNull Polyline.Options visible(boolean visible) {
            // Not supported, no-op.
            return this;
        }

        @Override public @NonNull Polyline.Options geodesic(boolean geodesic) {
            // Not supported, no-op.
            return this;
        }

        @Override public @NonNull Polyline.Options clickable(boolean clickable) {
            // Not supported, no-op.
            return this;
        }

        @Override public List<LatLng> getPoints() {
            return Collections.emptyList(); // Not supported, fallback to default.
        }

        @Override public float getWidth() {
            return 0f; // Not supported, fallback to default.
        }

        @Override public int getColor() {
            return 0; // Not supported, fallback to default.
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
            return null; // Not supported, fallback to default.
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
