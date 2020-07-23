/*
 * Copyright (C) 2020 Supasin Tatiyanupanwong
 * Copyright (C) 2013 Google Inc.
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

package me.tatiyanupanwong.supasin.android.libraries.kits.maps.utils.clustering.algo;

import java.util.Collection;
import java.util.Set;

import me.tatiyanupanwong.supasin.android.libraries.kits.maps.utils.clustering.Cluster;
import me.tatiyanupanwong.supasin.android.libraries.kits.maps.utils.clustering.ClusterItem;

/**
 * Logic for computing clusters
 */
public interface Algorithm<T extends ClusterItem> {

    /**
     * Adds an item to the algorithm
     * @param item the item to be added
     * @return {@code true} if the algorithm contents changed as a result of the call
     */
    boolean addItem(T item);

    /**
     * Adds a collection of items to the algorithm
     * @param items the items to be added
     * @return {@code true} if the algorithm contents changed as a result of the call
     */
    boolean addItems(Collection<T> items);

    void clearItems();

    /**
     * Removes an item from the algorithm
     * @param item the item to be removed
     * @return {@code true} if this algorithm contained the specified element (or equivalently,
     * if this algorithm changed as a result of the call).
     */
    boolean removeItem(T item);

    /**
     * Updates the provided item in the algorithm.
     *
     * @param item the item to be updated
     * @return {@code true} if the item existed in the algorithm and was updated, or {@code false
     * if the item did not exist in the algorithm and the algorithm contents remain unchanged.
     */
    boolean updateItem(T item);

    /**
     * Removes a collection of items from the algorithm.
     *
     * @param items the items to be removed
     * @return {@code true} if this algorithm contents changed as a result of the call
     */
    boolean removeItems(Collection<T> items);

    Set<? extends Cluster<T>> getClusters(float zoom);

    Collection<T> getItems();

    void setMaxDistanceBetweenClusteredItems(int maxDistance);

    int getMaxDistanceBetweenClusteredItems();

    void lock();

    void unlock();
}
