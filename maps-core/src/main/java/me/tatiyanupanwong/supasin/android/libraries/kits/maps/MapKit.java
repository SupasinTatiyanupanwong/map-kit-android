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

package me.tatiyanupanwong.supasin.android.libraries.kits.maps;

import androidx.annotation.NonNull;

import me.tatiyanupanwong.supasin.android.libraries.kits.maps.model.Map;

/**
 * Main entry point for Maps API.
 *
 * @since 1.0.0
 */
public final class MapKit {

    private static final Map.Factory FACTORY = MapsPlatform.get().getMapFactory();

    private MapKit() {} // No instances!

    /**
     * Obtains a factory object to interact with Maps API for the current platform.
     *
     * @return The {@link Map.Factory} for the current platform.
     */
    @NonNull
    public static Map.Factory getFactory() {
        return FACTORY;
    }

}
