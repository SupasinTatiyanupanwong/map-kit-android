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

import java.net.URL;

/**
 * An alternative to a {@link TileProvider} that only requires a URL that points to an image to be
 * provided.
 * <p>
 * Note that this class requires that all the images have the same dimensions.
 *
 * @since 1.0.0
 */
public interface UrlTileProvider {

    /**
     * Returns a {@link URL} that points to the image to be used for this tile. If no image is
     * found on the initial request, further requests will be made with an exponential backoff.
     * If you do not wish to provide an image for this tile coordinate, return {@code null}.
     *
     * @param x The x coordinate of the tile. This will be in the range [0, 2<sup>zoom</sup> - 1]
     * inclusive.
     * @param y The y coordinate of the tile. This will be in the range [0, 2<sup>zoom</sup> - 1]
     * inclusive.
     * @param zoom The zoom level of the tile. This will be in the range [{@link
     * MapClient#getMinZoomLevel}, {@link MapClient#getMaxZoomLevel}] inclusive.
     * @return URL a {@link URL} that points to the image to be used for this tile. If you do not
     * wish to provide an image for this tile coordinate, return {@code null}.
     */
    URL getTileUrl(int x, int y, int zoom);

}
