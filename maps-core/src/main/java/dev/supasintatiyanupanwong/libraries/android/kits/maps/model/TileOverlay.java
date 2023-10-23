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

package dev.supasintatiyanupanwong.libraries.android.kits.maps.model;

import androidx.annotation.FloatRange;
import androidx.annotation.NonNull;
import androidx.annotation.UiThread;

/**
 * A tile overlay is a set of images which are displayed on top of the base map tiles. These tiles
 * may be transparent, allowing you to add features to existing maps.
 * <p>
 * Methods in this class must be called on the Android UI thread. If not, an {@link
 * IllegalStateException} will be thrown at runtime.
 *
 * @since 1.0.0
 */
@UiThread
public interface TileOverlay {

    /**
     * Removes this tile overlay from the map.
     */
    void remove();

    /**
     * Clears the tile cache so that all tiles will be requested again from the {@link
     * TileProvider}. The current tiles from this tile overlay will also be cleared from the map
     * after calling this method. The API maintains a small in-memory cache of tiles. If you want
     * to cache tiles for longer, you should implement an on-disk cache.
     */
    void clearTileCache();

    /**
     * Gets this tile overlay's id.
     *
     * @return This tile overlay's id.
     */
    String getId();

    /**
     * Sets the zIndex of this tile overlay.
     *
     * @param zIndex The zIndex of this tile overlay.
     */
    void setZIndex(float zIndex);

    /**
     * Gets the zIndex of this tile overlay.
     *
     * @return The zIndex of the tile overlay.
     */
    float getZIndex();

    /**
     * Sets the visibility of this tile overlay. When not visible, a tile overlay is not drawn,
     * but it keeps all its other properties. Tile overlays are visible by default.
     *
     * @param visible {@code true} to make this overlay visible; {@code false} to make it invisible.
     */
    void setVisible(boolean visible);

    /**
     * Gets the visibility of this tile overlay. Note that this does not return whether the tile
     * overlay is actually within the screen's viewport, but whether it will be drawn if it is
     * contained in the screen's viewport.
     *
     * @return This tile overlay's visibility.
     */
    boolean isVisible();

    /**
     * Sets whether the overlay tiles should fade in.
     *
     * @param fadeIn {@code true} to make the tiles fade in; {@code false} to render them instantly.
     */
    void setFadeIn(boolean fadeIn);

    /**
     * Gets whether the overlay tiles should fade in.
     *
     * @return {@code true} if the tiles are to fade in; {@code false} if they are not.
     */
    boolean getFadeIn();

    /**
     * Sets the transparency of this tile overlay.
     *
     * @param transparency A float in the range {@code 0..1} where {@code 0} means that the tile
     * overlay is opaque and {@code 1} means that the tile overlay is transparent.
     */
    void setTransparency(@FloatRange(from = 0.0, to = 1.0) float transparency);

    /**
     * Gets the transparency of this tile overlay.
     *
     * @return The transparency of this tile overlay.
     */
    float getTransparency();


    /**
     * Defines options for a {@link TileOverlay}.
     */
    interface Options {
        /**
         * Specifies the tile provider to use for this tile overlay.
         *
         * @param tileProvider The {@link TileProvider} to use for this tile overlay.
         * @return This {@link Options} object for method chaining.
         */
        @NonNull Options tileProvider(TileProvider tileProvider);

        /**
         * Specifies the tile overlay's zIndex, i.e., the order in which it will be drawn where
         * overlays with larger values are drawn above those with lower values.
         *
         * @param zIndex The zIndex to use for this tile overlay.
         * @return This {@link Options} object for method chaining.
         */
        @NonNull Options zIndex(float zIndex);

        /**
         * Specifies the visibility for the tile overlay. The default visibility is {@code true}.
         *
         * @param visible The visibility setting to use for this tile overlay.
         * @return This {@link Options} object for method chaining.
         */
        @NonNull Options visible(boolean visible);

        /**
         * Specifies whether the tiles should fade in. The default is {@code true}.
         *
         * @param fadeIn The visibility setting to use for this tile overlay.
         * @return This {@link Options} object for method chaining.
         */
        @NonNull Options fadeIn(boolean fadeIn);

        /**
         * Specifies the transparency of the tile overlay. The default transparency is 0 (opaque).
         *
         * @param transparency A float in the range {@code 0..1} where {@code 0} means that the
         * tile overlay is opaque and {@code 1} means that the tile overlay is transparent.
         * @return This {@link Options} object for method chaining.
         * @throws IllegalArgumentException If the transparency is outside the range {@code 0..1}.
         */
        @NonNull Options transparency(@FloatRange(from = 0.0, to = 1.0) float transparency);

        /**
         * Gets the tile provider set for this {@link Options} object.
         *
         * @return The {@link TileProvider} of the tile overlay.
         */
        TileProvider getTileProvider();

        /**
         * Gets the zIndex set for this {@link Options} object.
         *
         * @return The zIndex of the tile overlay.
         */
        float getZIndex();

        /**
         * Gets the visibility setting for this {@link Options} object.
         *
         * @return {@code true} if the tile overlay is to be visible; {@code false} if it is not.
         */
        boolean isVisible();

        /**
         * Gets whether the tiles should fade in.
         *
         * @return {@code true} if the tiles are to fade in; {@code false} if it is not.
         */
        boolean getFadeIn();

        /**
         * Gets the transparency set for this {@link Options} object.
         *
         * @return The transparency of the tile overlay.
         */
        float getTransparency();
    }

}
