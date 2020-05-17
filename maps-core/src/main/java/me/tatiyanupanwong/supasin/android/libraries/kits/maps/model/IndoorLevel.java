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
 * Represents a level in a building.
 *
 * <p>IndoorLevel objects are only equal by id. It is possible that may have different contents.
 *
 * <p>While a level is usually enclosed by a single building, a level might be enclosed by several
 * buildings (e.g., a carpark level might span multiple buildings).
 *
 * @since 1.0.0
 */
public interface IndoorLevel {

    /**
     * Localized display name for the level, e.g. "Ground floor". Returns an empty string if no
     * name is defined.
     */
    String getName();

    /**
     * Localized short display name for the level, e.g. "1". Returns an empty string if no
     * shortName is defined.
     */
    String getShortName();

    /**
     * Sets this level as the visible level in its building. If a level is enclosed in several
     * buildings, then all those buildings will have this level set as active.
     */
    void activate();

}
