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

import org.jetbrains.annotations.Contract;

import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.Tile;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.TileProvider;

@RestrictTo(LIBRARY)
public class AmazonTile implements Tile {

    private final com.amazon.geo.mapsv2.model.Tile mDelegate;

    private AmazonTile(com.amazon.geo.mapsv2.model.Tile delegate) {
        mDelegate = delegate;
    }

    public AmazonTile(int width, int height, byte[] data) {
        this(new com.amazon.geo.mapsv2.model.Tile(width, height, data));
    }

    @Override public int getWidth() {
        return mDelegate.width;
    }

    @Override public int getHeight() {
        return mDelegate.height;
    }

    @Override public @Nullable byte[] getData() {
        return mDelegate.data;
    }


    @Override public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        AmazonTile that = (AmazonTile) obj;

        return mDelegate.equals(that.mDelegate);
    }

    @Override public int hashCode() {
        return mDelegate.hashCode();
    }

    @Override public @NonNull String toString() {
        return mDelegate.toString();
    }


    @Contract("null -> null; !null -> !null")
    static @Nullable Tile wrap(@Nullable com.amazon.geo.mapsv2.model.Tile delegate) {
        if (delegate == null) {
            return null;
        } else if (com.amazon.geo.mapsv2.model.TileProvider.NO_TILE.equals(delegate)) {
            return TileProvider.NO_TILE;
        } else {
            return new AmazonTile(delegate);
        }
    }

    @Contract("null -> null; !null -> !null")
    static @Nullable com.amazon.geo.mapsv2.model.Tile unwrap(@Nullable Tile wrapped) {
        if (wrapped == null) {
            return null;
        } else if (TileProvider.NO_TILE.equals(wrapped)) {
            return com.amazon.geo.mapsv2.model.TileProvider.NO_TILE;
        } else {
            return ((AmazonTile) wrapped).mDelegate;
        }
    }
}
