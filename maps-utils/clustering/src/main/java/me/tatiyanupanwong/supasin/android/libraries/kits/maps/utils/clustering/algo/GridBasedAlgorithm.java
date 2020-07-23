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

import androidx.collection.LongSparseArray;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import me.tatiyanupanwong.supasin.android.libraries.kits.maps.utils.clustering.Cluster;
import me.tatiyanupanwong.supasin.android.libraries.kits.maps.utils.clustering.ClusterItem;
import me.tatiyanupanwong.supasin.android.libraries.kits.maps.utils.geometry.Point;
import me.tatiyanupanwong.supasin.android.libraries.kits.maps.utils.projection.SphericalMercatorProjection;

/**
 * Groups markers into a grid.
 */
public class GridBasedAlgorithm<T extends ClusterItem> extends AbstractAlgorithm<T> {
    private static final int DEFAULT_GRID_SIZE = 100;

    private int mGridSize = DEFAULT_GRID_SIZE;

    private final Set<T> mItems = Collections.synchronizedSet(new HashSet<T>());

    /**
     * Adds an item to the algorithm.
     *
     * @param item the item to be added
     * @return {@code true} if the algorithm contents changed as a result of the call
     */
    @Override
    public boolean addItem(T item) {
        return mItems.add(item);
    }

    /**
     * Adds a collection of items to the algorithm.
     *
     * @param items the items to be added
     * @return {@code true} if the algorithm contents changed as a result of the call
     */
    @Override
    public boolean addItems(Collection<T> items) {
        return mItems.addAll(items);
    }

    @Override
    public void clearItems() {
        mItems.clear();
    }

    /**
     * Removes an item from the algorithm.
     *
     * @param item the item to be removed
     * @return {@code true} if this algorithm contained the specified element (or equivalently,
     * if this algorithm changed as a result of the call).
     */
    @Override
    public boolean removeItem(T item) {
        return mItems.remove(item);
    }

    /**
     * Removes a collection of items from the algorithm.
     *
     * @param items the items to be removed
     * @return {@code true} if this algorithm contents changed as a result of the call
     */
    @Override
    public boolean removeItems(Collection<T> items) {
        return mItems.removeAll(items);
    }

    /**
     * Updates the provided item in the algorithm.
     *
     * @param item the item to be updated
     * @return {@code true} if the item existed in the algorithm and was updated, or {@code false}
     * if the item did not exist in the algorithm and the algorithm contents remain unchanged.
     */
    @Override
    public boolean updateItem(T item) {
        boolean result;
        synchronized (mItems) {
            result = removeItem(item);
            if (result) {
                // Only add the item if it was removed; to help prevent accidental duplicates on map
                result = addItem(item);
            }
        }
        return result;
    }

    @Override
    public void setMaxDistanceBetweenClusteredItems(int maxDistance) {
        mGridSize = maxDistance;
    }

    @Override
    public int getMaxDistanceBetweenClusteredItems() {
        return mGridSize;
    }

    @Override
    public Set<? extends Cluster<T>> getClusters(float zoom) {
        long numCells = (long) Math.ceil(256 * Math.pow(2, zoom) / mGridSize);
        SphericalMercatorProjection proj = new SphericalMercatorProjection(numCells);

        HashSet<Cluster<T>> clusters = new HashSet<>();
        LongSparseArray<StaticCluster<T>> sparseArray = new LongSparseArray<>();

        synchronized (mItems) {
            for (T item : mItems) {
                Point p = proj.toPoint(item.getPosition());

                long coord = getCoord(numCells, p.x, p.y);

                StaticCluster<T> cluster = sparseArray.get(coord);
                if (cluster == null) {
                    cluster = new StaticCluster<>(
                            proj.toLatLng(new Point(Math.floor(p.x) + .5, Math.floor(p.y) + .5)));
                    sparseArray.put(coord, cluster);
                    clusters.add(cluster);
                }
                cluster.add(item);
            }
        }

        return clusters;
    }

    @Override
    public Collection<T> getItems() {
        return mItems;
    }

    private static long getCoord(long numCells, double x, double y) {
        return (long) (numCells * Math.floor(x) + Math.floor(y));
    }
}
