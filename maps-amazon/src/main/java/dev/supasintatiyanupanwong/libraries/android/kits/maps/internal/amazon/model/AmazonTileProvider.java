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

import java.net.URL;

import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.Tile;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.TileProvider;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.UrlTileProvider;

@RestrictTo(LIBRARY)
public class AmazonTileProvider implements TileProvider {

    private static final @NonNull URL NULL_URL;
    static {
        try {
            NULL_URL = new URL("http://null.sun.com/");
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    private final com.amazon.geo.mapsv2.model.TileProvider mDelegate;

    AmazonTileProvider(com.amazon.geo.mapsv2.model.TileProvider delegate) {
        mDelegate = delegate;
    }

    private AmazonTileProvider(final TileProvider tileProvider) {
        // Amazon Maps SDK only supports URL tile overlay
        if (tileProvider instanceof UrlTileProvider) {
            final UrlTileProvider urlTileProvider = (UrlTileProvider) tileProvider;
            mDelegate = new com.amazon.geo.mapsv2.model.UrlTileProvider(
                    urlTileProvider.getWidth(),
                    urlTileProvider.getHeight()
            ) {
                @Override public @NonNull URL getTileUrl(int x, int y, int zoom) {
                    final @Nullable URL url = urlTileProvider.getTileUrl(x, y, zoom);
                    return url == null ? NULL_URL : url;
                }
            };
        } else {
            mDelegate = new com.amazon.geo.mapsv2.model.UrlTileProvider(-1, -1) {
                @Override public @NonNull URL getTileUrl(int x, int y, int zoom) {
                    return NULL_URL;
                }
            };
        }
    }

    @Override public Tile getTile(int x, int y, int zoom) {
        return AmazonTile.wrap(mDelegate.getTile(x, y, zoom));
    }


    @Override public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        AmazonTileProvider that = (AmazonTileProvider) obj;

        return mDelegate.equals(that.mDelegate);
    }

    @Override public int hashCode() {
        return mDelegate.hashCode();
    }

    @Override public @NonNull String toString() {
        return mDelegate.toString();
    }


    static TileProvider wrap(com.amazon.geo.mapsv2.model.TileProvider delegate) {
        return new AmazonTileProvider(delegate);
    }

    static com.amazon.geo.mapsv2.model.TileProvider unwrap(TileProvider wrapped) {
        return new AmazonTileProvider(wrapped).mDelegate;
    }
}
