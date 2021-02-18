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

package dev.supasintatiyanupanwong.libraries.android.kits.maps.model;

import java.util.List;

/**
 * Represents a building.
 *
 * <p>Two IndoorBuildings are .equal() if the physical building they represent is the same.
 * However, if a building's structural model changes, e.g., due to an update to building models,
 * then an old IndoorBuilding object and a new IndoorBuilding object will be .equal(), but might
 * have different contents.
 *
 * @since 1.0.0
 */
public interface IndoorBuilding {

    /**
     * Gets the index in the list returned by {@link #getLevels()} of the default level for this
     * building.
     */
    int getDefaultLevelIndex();

    /**
     * Gets the index in the list returned by {@link #getLevels()} of the level that is currently
     * active in this building (default if no active level was previously set).
     */
    int getActiveLevelIndex();

    /**
     * Gets the levels in the building. While a level is usually enclosed by a single building, a
     * level might be enclosed by several buildings (e.g., a carpark level might span multiple
     * buildings). The levels are in 'display order' from top to bottom.
     */
    List<IndoorLevel> getLevels();

    /**
     * Returns true if the building is entirely underground.
     */
    boolean isUnderground();

}
