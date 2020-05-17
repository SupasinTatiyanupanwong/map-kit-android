/*
 * Copyright (C) 2020 Supasin Tatiyanupanwong
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package me.tatiyanupanwong.supasin.android.libraries.kits.maps.internal.huawei.model;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import me.tatiyanupanwong.supasin.android.libraries.kits.maps.model.Tile;
import me.tatiyanupanwong.supasin.android.libraries.kits.maps.model.TileProvider;

class HuaweiTileProvider implements TileProvider {
    static final Tile NO_TILE =
            HuaweiTile.wrap(com.huawei.hms.maps.model.TileProvider.NO_TILE);

    private final com.huawei.hms.maps.model.TileProvider mDelegate;

    HuaweiTileProvider(com.huawei.hms.maps.model.TileProvider delegate) {
        mDelegate = delegate;
    }

    private HuaweiTileProvider(final TileProvider tileProvider) {
        this(new com.huawei.hms.maps.model.TileProvider() {
            @Override
            public com.huawei.hms.maps.model.Tile getTile(int x, int y, int zoom) {
                return HuaweiTile.unwrap(tileProvider.getTile(x, y, zoom));
            }
        });
    }

    @Override
    public Tile getTile(int x, int y, int zoom) {
        return HuaweiTile.wrap(mDelegate.getTile(x, y, zoom));
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        HuaweiTileProvider that = (HuaweiTileProvider) obj;

        return mDelegate.equals(that.mDelegate);
    }

    @Override
    public int hashCode() {
        return mDelegate.hashCode();
    }

    @NonNull
    @Override
    public String toString() {
        return mDelegate.toString();
    }


    static TileProvider wrap(com.huawei.hms.maps.model.TileProvider delegate) {
        return new HuaweiTileProvider(delegate);
    }

    static com.huawei.hms.maps.model.TileProvider unwrap(TileProvider wrapped) {
        return new HuaweiTileProvider(wrapped).mDelegate;
    }

}