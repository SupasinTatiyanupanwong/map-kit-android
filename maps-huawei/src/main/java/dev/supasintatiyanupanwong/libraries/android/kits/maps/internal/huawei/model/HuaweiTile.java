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

package dev.supasintatiyanupanwong.libraries.android.kits.maps.internal.huawei.model;

import static androidx.annotation.RestrictTo.Scope.LIBRARY;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;

import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.Tile;

@RestrictTo(LIBRARY)
public class HuaweiTile implements Tile {

    private final com.huawei.hms.maps.model.Tile mDelegate;

    private HuaweiTile(com.huawei.hms.maps.model.Tile delegate) {
        mDelegate = delegate;
    }

    public HuaweiTile(int width, int height, byte[] data) {
        this(new com.huawei.hms.maps.model.Tile(width, height, data));
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

        HuaweiTile that = (HuaweiTile) obj;

        return mDelegate.equals(that.mDelegate);
    }

    @Override public int hashCode() {
        return mDelegate.hashCode();
    }

    @Override public @NonNull String toString() {
        return mDelegate.toString();
    }


    static Tile wrap(com.huawei.hms.maps.model.Tile delegate) {
        return new HuaweiTile(delegate);
    }

    static com.huawei.hms.maps.model.Tile unwrap(Tile wrapped) {
        return ((HuaweiTile) wrapped).mDelegate;
    }

}
