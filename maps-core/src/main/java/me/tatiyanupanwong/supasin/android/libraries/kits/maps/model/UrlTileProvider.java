package me.tatiyanupanwong.supasin.android.libraries.kits.maps.model;

import java.net.URL;

/**
 * An alternative to a {@link TileProvider} that only requires a URL that points to an image to be
 * provided.
 *
 * <p>Note that this class requires that all the images have the same dimensions.
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
     * Map#getMinZoomLevel}, {@link Map#getMaxZoomLevel}] inclusive.
     * @return URL a {@link URL} that points to the image to be used for this tile. If you do not
     * wish to provide an image for this tile coordinate, return {@code null}.
     */
    URL getTileUrl(int x, int y, int zoom);

}
