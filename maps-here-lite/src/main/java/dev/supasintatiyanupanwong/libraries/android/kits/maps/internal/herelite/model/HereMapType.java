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

package dev.supasintatiyanupanwong.libraries.android.kits.maps.internal.herelite.model;

import androidx.annotation.NonNull;

import com.here.sdk.mapviewlite.MapStyle;

import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.MapClient;

final class HereMapType {

    private HereMapType() {}

    static @NonNull MapStyle unwrap(int type) {
        switch (type) {
            case MapClient.MAP_TYPE_NONE:
                return MapStyle.EMPTY;

            case MapClient.MAP_TYPE_NORMAL:
            case MapClient.MAP_TYPE_TERRAIN:
                return MapStyle.NORMAL_DAY;

            case MapClient.MAP_TYPE_SATELLITE:
                return MapStyle.SATELLITE;

            case MapClient.MAP_TYPE_HYBRID:
                return MapStyle.HYBRID_DAY;
        }

        throw new IllegalArgumentException();
    }

}
