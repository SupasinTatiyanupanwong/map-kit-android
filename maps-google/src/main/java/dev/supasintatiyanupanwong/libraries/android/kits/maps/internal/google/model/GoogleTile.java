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

import org.jetbrains.annotations.Contract;

import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.Tile;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.TileProvider;

@RestrictTo(LIBRARY)
public class GoogleTile implements Tile {

    private final com.google.android.gms.maps.model.Tile mDelegate;

    private GoogleTile(com.google.android.gms.maps.model.Tile delegate) {
        mDelegate = delegate;
    }

    public GoogleTile(int width, int height, byte[] data) {
        this(new com.google.android.gms.maps.model.Tile(width, height, data));
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

        GoogleTile that = (GoogleTile) obj;

        return mDelegate.equals(that.mDelegate);
    }

    @Override public int hashCode() {
        return mDelegate.hashCode();
    }

    @Override public @NonNull String toString() {
        return mDelegate.toString();
    }


    @Contract("null -> null; !null -> !null")
    static @Nullable Tile wrap(@Nullable com.google.android.gms.maps.model.Tile delegate) {
        if (delegate == null) {
            return null;
        } else if (com.google.android.gms.maps.model.TileProvider.NO_TILE.equals(delegate)) {
            return TileProvider.NO_TILE;
        } else {
            return new GoogleTile(delegate);
        }
    }

    @Contract("null -> null; !null -> !null")
    static @Nullable com.google.android.gms.maps.model.Tile unwrap(@Nullable Tile wrapped) {
        if (wrapped == null) {
            return null;
        } else if (TileProvider.NO_TILE.equals(wrapped)) {
            return com.google.android.gms.maps.model.TileProvider.NO_TILE;
        } else {
            return ((GoogleTile) wrapped).mDelegate;
        }
    }
}
