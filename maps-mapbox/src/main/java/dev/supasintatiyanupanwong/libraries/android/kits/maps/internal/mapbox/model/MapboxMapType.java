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

package dev.supasintatiyanupanwong.libraries.android.kits.maps.internal.mapbox.model;

import androidx.annotation.NonNull;

import com.mapbox.maps.Style;

import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.MapClient;

final class MapboxMapType {

    private MapboxMapType() {}

    static @NonNull String unwrap(int type) {
        switch (type) {
            case MapClient.MAP_TYPE_NONE:
            case MapClient.MAP_TYPE_NORMAL:
            case MapClient.MAP_TYPE_TERRAIN:
                return Style.MAPBOX_STREETS;

            case MapClient.MAP_TYPE_SATELLITE:
                return Style.SATELLITE;

            case MapClient.MAP_TYPE_HYBRID:
                return Style.SATELLITE_STREETS;
        }

        throw new IllegalArgumentException();
    }

}
