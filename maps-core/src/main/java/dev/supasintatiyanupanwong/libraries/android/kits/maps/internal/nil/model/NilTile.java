/*
 * Copyright 2022 Supasin Tatiyanupanwong
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

package dev.supasintatiyanupanwong.libraries.android.kits.maps.internal.nil.model;

import static androidx.annotation.RestrictTo.Scope.LIBRARY;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;

import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.Tile;

@RestrictTo(LIBRARY)
public class NilTile implements Tile {

    public static final @NonNull Tile INSTANCE = new NilTile();

    private NilTile() {}

    @Override public int getWidth() {
        return 0; // Not supported, fallback to default.
    }

    @Override public int getHeight() {
        return 0; // Not supported, fallback to default.
    }

    @Override public @Nullable byte[] getData() {
        return null; // Not supported, fallback to default.
    }

}
