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

import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.TileOverlay;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.TileProvider;

@RestrictTo(LIBRARY)
public class AmazonTileOverlay implements TileOverlay {

    private final @NonNull com.amazon.geo.mapsv2.model.TileOverlay mDelegate;

    private AmazonTileOverlay(@NonNull com.amazon.geo.mapsv2.model.TileOverlay delegate) {
        mDelegate = delegate;
    }

    @Override public void remove() {
        mDelegate.remove();
    }

    @Override public void clearTileCache() {
        mDelegate.clearTileCache();
    }

    @Override public String getId() {
        return mDelegate.getId();
    }

    @Override public void setZIndex(float zIndex) {
        mDelegate.setZIndex(zIndex);
    }

    @Override public float getZIndex() {
        return mDelegate.getZIndex();
    }

    @Override public void setVisible(boolean visible) {
        mDelegate.setVisible(visible);
    }

    @Override public boolean isVisible() {
        return mDelegate.isVisible();
    }

    @Override public void setFadeIn(boolean fadeIn) {
        mDelegate.setFadeIn(fadeIn);
    }

    @Override public boolean getFadeIn() {
        return mDelegate.getFadeIn();
    }

    @Override public void setTransparency(float transparency) {
        // Not supported, no-op.
    }

    @Override public float getTransparency() {
        return 0; // Not supported, 0 is opaque.
    }

    @Override public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        AmazonTileOverlay that = (AmazonTileOverlay) obj;

        return mDelegate.equals(that.mDelegate);
    }

    @Override public int hashCode() {
        return mDelegate.hashCode();
    }

    @Override public @NonNull String toString() {
        return mDelegate.toString();
    }


    static @Nullable TileOverlay wrap(@Nullable com.amazon.geo.mapsv2.model.TileOverlay delegate) {
        return delegate == null ? null : new AmazonTileOverlay(delegate);
    }


    public static class Options implements TileOverlay.Options {
        private final com.amazon.geo.mapsv2.model.TileOverlayOptions mDelegate;

        public Options() {
            mDelegate = new com.amazon.geo.mapsv2.model.TileOverlayOptions();
        }

        @Override public @NonNull TileOverlay.Options tileProvider(TileProvider tileProvider) {
            mDelegate.tileProvider(AmazonTileProvider.unwrap(tileProvider));
            return this;
        }

        @Override public @NonNull TileOverlay.Options zIndex(float zIndex) {
            mDelegate.zIndex(zIndex);
            return this;
        }

        @Override public @NonNull TileOverlay.Options visible(boolean visible) {
            mDelegate.visible(visible);
            return this;
        }

        @Override public @NonNull TileOverlay.Options fadeIn(boolean fadeIn) {
            mDelegate.fadeIn(fadeIn);
            return this;
        }

        @Override public @NonNull TileOverlay.Options transparency(float transparency) {
            // Not supported, no-op.
            return this;
        }

        @Override public TileProvider getTileProvider() {
            return AmazonTileProvider.wrap(mDelegate.getTileProvider());
        }

        @Override public float getZIndex() {
            return mDelegate.getZIndex();
        }

        @Override public boolean isVisible() {
            return mDelegate.isVisible();
        }

        @Override public boolean getFadeIn() {
            return mDelegate.getFadeIn();
        }

        @Override public float getTransparency() {
            return 0; // Not supported, 0 is opaque.
        }


        static @Nullable com.amazon.geo.mapsv2.model.TileOverlayOptions unwrap(
                @Nullable TileOverlay.Options wrapped) {
            return wrapped == null ? null : ((Options) wrapped).mDelegate;
        }
    }

}
