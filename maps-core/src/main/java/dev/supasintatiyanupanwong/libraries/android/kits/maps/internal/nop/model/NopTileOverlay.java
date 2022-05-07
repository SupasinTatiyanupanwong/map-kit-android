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
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.TileOverlay;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.TileProvider;

@RestrictTo(LIBRARY)
public class NopTileOverlay {

    private NopTileOverlay() {}


    public static class Options implements TileOverlay.Options {
        public static final @NonNull TileOverlay.Options NULL = new Options();

        private Options() {}

        @Override public @NonNull TileOverlay.Options tileProvider(TileProvider tileProvider) {
            // Not supported, no-op.
            return this;
        }

        @Override public @NonNull TileOverlay.Options zIndex(float zIndex) {
            // Not supported, no-op.
            return this;
        }

        @Override public @NonNull TileOverlay.Options visible(boolean visible) {
            // Not supported, no-op.
            return this;
        }

        @Override public @NonNull TileOverlay.Options fadeIn(boolean fadeIn) {
            // Not supported, no-op.
            return this;
        }

        @Override public @NonNull TileOverlay.Options transparency(float transparency) {
            // Not supported, no-op.
            return this;
        }

        @Override public TileProvider getTileProvider() {
            return NopTileProvider.NULL; // Not supported, null object for API safe.
        }

        @Override public float getZIndex() {
            return 0f; // Not supported, fallback to default.
        }

        @Override public boolean isVisible() {
            return false; // Not supported, fallback to default.
        }

        @Override public boolean getFadeIn() {
            return false; // Not supported, fallback to default.
        }

        @Override public float getTransparency() {
            return 0; // Not supported, fallback to default.
        }
    }

}
