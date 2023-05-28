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

import static androidx.annotation.RestrictTo.Scope.LIBRARY;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;

import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.Tile;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.TileProvider;

@RestrictTo(LIBRARY)
public class GoogleTileProvider implements TileProvider {

    private final com.google.android.gms.maps.model.TileProvider mDelegate;

    GoogleTileProvider(com.google.android.gms.maps.model.TileProvider delegate) {
        mDelegate = delegate;
    }

    private GoogleTileProvider(final TileProvider tileProvider) {
        this(new com.google.android.gms.maps.model.TileProvider() {
            @Override public com.google.android.gms.maps.model.Tile getTile(int x, int y, int zoom) {
                return GoogleTile.unwrap(tileProvider.getTile(x, y, zoom));
            }
        });
    }

    @Override public Tile getTile(int x, int y, int zoom) {
        return GoogleTile.wrap(mDelegate.getTile(x, y, zoom));
    }


    @Override public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        GoogleTileProvider that = (GoogleTileProvider) obj;

        return mDelegate.equals(that.mDelegate);
    }

    @Override public int hashCode() {
        return mDelegate.hashCode();
    }

    @Override public @NonNull String toString() {
        return mDelegate.toString();
    }


    static TileProvider wrap(com.google.android.gms.maps.model.TileProvider delegate) {
        return new GoogleTileProvider(delegate);
    }

    static com.google.android.gms.maps.model.TileProvider unwrap(TileProvider wrapped) {
        return new GoogleTileProvider(wrapped).mDelegate;
    }

}
