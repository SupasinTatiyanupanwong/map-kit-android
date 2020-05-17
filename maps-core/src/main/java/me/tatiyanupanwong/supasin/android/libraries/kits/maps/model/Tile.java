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

package me.tatiyanupanwong.supasin.android.libraries.kits.maps.model;

import android.graphics.BitmapFactory;

import androidx.annotation.Nullable;

/**
 * Contains information about a Tile that is returned by a {@link TileProvider}.
 *
 * @since 1.0.0
 */
public interface Tile {

    /**
     * The width of the image encoded by {@link #getData() data} in pixels.
     */
    int getWidth();

    /**
     * The height of the image encoded by {@link #getData() data} in pixels.
     */
    int getHeight();

    /**
     * A byte array containing the image data. The image will be created from this data by calling
     * {@link BitmapFactory#decodeByteArray(byte[], int, int) decodeByteArray(byte[], int, int)}.
     */
    @Nullable
    byte[] getData();

}
