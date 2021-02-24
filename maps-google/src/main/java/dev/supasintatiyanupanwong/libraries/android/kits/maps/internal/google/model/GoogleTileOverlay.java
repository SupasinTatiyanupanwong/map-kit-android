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

package dev.supasintatiyanupanwong.libraries.android.kits.maps.internal.google.model;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.TileOverlay;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.TileProvider;

class GoogleTileOverlay implements TileOverlay {

    private final com.google.android.gms.maps.model.TileOverlay mDelegate;

    private GoogleTileOverlay(com.google.android.gms.maps.model.TileOverlay delegate) {
        mDelegate = delegate;
    }

    @Override
    public void remove() {
        mDelegate.remove();
    }

    @Override
    public void clearTileCache() {
        mDelegate.clearTileCache();
    }

    @Override
    public String getId() {
        return mDelegate.getId();
    }

    @Override
    public void setZIndex(float zIndex) {
        mDelegate.setZIndex(zIndex);
    }

    @Override
    public float getZIndex() {
        return mDelegate.getZIndex();
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
    public void setFadeIn(boolean fadeIn) {
        mDelegate.setFadeIn(fadeIn);
    }

    @Override
    public boolean getFadeIn() {
        return mDelegate.getFadeIn();
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
    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        GoogleTileOverlay that = (GoogleTileOverlay) obj;

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


    static TileOverlay wrap(com.google.android.gms.maps.model.TileOverlay delegate) {
        return new GoogleTileOverlay(delegate);
    }


    static class Options implements TileOverlay.Options {
        private final com.google.android.gms.maps.model.TileOverlayOptions mDelegate;

        Options() {
            mDelegate = new com.google.android.gms.maps.model.TileOverlayOptions();
        }

        @Override
        public @NonNull TileOverlay.Options tileProvider(TileProvider tileProvider) {
            mDelegate.tileProvider(GoogleTileProvider.unwrap(tileProvider));
            return this;
        }

        @Override
        public @NonNull TileOverlay.Options zIndex(float zIndex) {
            mDelegate.zIndex(zIndex);
            return this;
        }

        @Override
        public @NonNull TileOverlay.Options visible(boolean visible) {
            mDelegate.visible(visible);
            return this;
        }

        @Override
        public @NonNull TileOverlay.Options fadeIn(boolean fadeIn) {
            mDelegate.fadeIn(fadeIn);
            return this;
        }

        @Override
        public @NonNull TileOverlay.Options transparency(float transparency) {
            mDelegate.transparency(transparency);
            return this;
        }

        @Override
        public TileProvider getTileProvider() {
            return GoogleTileProvider.wrap(mDelegate.getTileProvider());
        }

        @Override
        public float getZIndex() {
            return mDelegate.getZIndex();
        }

        @Override
        public boolean isVisible() {
            return mDelegate.isVisible();
        }

        @Override
        public boolean getFadeIn() {
            return mDelegate.getFadeIn();
        }

        @Override
        public float getTransparency() {
            return mDelegate.getTransparency();
        }


        static com.google.android.gms.maps.model.TileOverlayOptions unwrap(
                TileOverlay.Options wrapped) {
            return ((GoogleTileOverlay.Options) wrapped).mDelegate;
        }
    }

}
