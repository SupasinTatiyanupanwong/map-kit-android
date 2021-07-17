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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.Tile;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.TileProvider;

class AmazonTileProvider implements TileProvider {
    static final Tile NO_TILE =
            AmazonTile.wrap(com.amazon.geo.mapsv2.model.TileProvider.NO_TILE);

    private final com.amazon.geo.mapsv2.model.TileProvider mDelegate;

    AmazonTileProvider(com.amazon.geo.mapsv2.model.TileProvider delegate) {
        mDelegate = delegate;
    }

    private AmazonTileProvider(final TileProvider tileProvider) {
        this(new com.amazon.geo.mapsv2.model.TileProvider() {
            @Override
            public com.amazon.geo.mapsv2.model.Tile getTile(int x, int y, int zoom) {
                return AmazonTile.unwrap(tileProvider.getTile(x, y, zoom));
            }
        });
    }

    @Override
    public Tile getTile(int x, int y, int zoom) {
        return AmazonTile.wrap(mDelegate.getTile(x, y, zoom));
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        AmazonTileProvider that = (AmazonTileProvider) obj;

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


    static TileProvider wrap(com.amazon.geo.mapsv2.model.TileProvider delegate) {
        return new AmazonTileProvider(delegate);
    }

    static com.amazon.geo.mapsv2.model.TileProvider unwrap(TileProvider wrapped) {
        return new AmazonTileProvider(wrapped).mDelegate;
    }

}
