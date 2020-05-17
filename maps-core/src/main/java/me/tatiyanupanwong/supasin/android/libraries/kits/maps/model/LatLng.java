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

/**
 * An immutable class representing a pair of latitude and longitude coordinates, stored as degrees.
 *
 * @since 1.0.0
 */
public interface LatLng {

    /**
     * Latitude, in degrees. This value is in the range [-90, 90].
     */
    double getLatitude();

    /**
     * Longitude, in degrees. This value is in the range [-180, 180).
     */
    double getLongitude();

}
