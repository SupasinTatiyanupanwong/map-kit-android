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

import android.graphics.Color;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;

import java.util.List;

import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.Circle;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.LatLng;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.PatternItem;

@RestrictTo(LIBRARY)
public class NopCircle {

    private NopCircle() {}


    public static class Options implements Circle.Options {
        public static final @NonNull Circle.Options NULL = new NopCircle.Options();

        private Options() {}

        @Override public @NonNull Circle.Options center(@NonNull LatLng center) {
            // Not supported, no-op.
            return this;
        }

        @Override public @NonNull Circle.Options radius(double radius) {
            // Not supported, no-op.
            return this;
        }

        @Override public @NonNull Circle.Options strokeWidth(float width) {
            // Not supported, no-op.
            return this;
        }

        @Override public @NonNull Circle.Options strokeColor(int color) {
            // Not supported, no-op.
            return this;
        }

        @Override public @NonNull Circle.Options strokePattern(
                @Nullable List<PatternItem> pattern) {
            // Not supported, no-op.
            return this;
        }

        @Override public @NonNull Circle.Options fillColor(int color) {
            // Not supported, no-op.
            return this;
        }

        @Override public @NonNull Circle.Options zIndex(float zIndex) {
            // Not supported, no-op.
            return this;
        }

        @Override public @NonNull Circle.Options visible(boolean visible) {
            // Not supported, no-op.
            return this;
        }

        @Override public @NonNull Circle.Options clickable(boolean clickable) {
            // Not supported, no-op.
            return this;
        }

        @Override public @NonNull LatLng getCenter() {
            return NopLatLng.NULL; // Not supported, null object for API safe.
        }

        @Override public double getRadius() {
            return 0; // Not supported, fallback to default.
        }

        @Override public float getStrokeWidth() {
            return 0f; // Not supported, fallback to default.
        }

        @Override public int getStrokeColor() {
            return Color.TRANSPARENT; // Not supported, fallback to default.
        }

        @Override public @Nullable List<PatternItem> getStrokePattern() {
            return null; // Not supported, fallback to default.
        }

        @Override public int getFillColor() {
            return Color.TRANSPARENT; // Not supported, fallback to default.
        }

        @Override public float getZIndex() {
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
