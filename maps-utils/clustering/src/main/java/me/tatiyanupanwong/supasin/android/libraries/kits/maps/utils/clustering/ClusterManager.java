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

package me.tatiyanupanwong.supasin.android.libraries.kits.maps.utils.clustering;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;

import java.util.Collection;
import java.util.Set;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import me.tatiyanupanwong.supasin.android.libraries.kits.maps.model.CameraPosition;
import me.tatiyanupanwong.supasin.android.libraries.kits.maps.model.MapClient;
import me.tatiyanupanwong.supasin.android.libraries.kits.maps.model.Marker;
import me.tatiyanupanwong.supasin.android.libraries.kits.maps.utils.clustering.algo.Algorithm;
import me.tatiyanupanwong.supasin.android.libraries.kits.maps.utils.clustering.algo.NonHierarchicalDistanceBasedAlgorithm;
import me.tatiyanupanwong.supasin.android.libraries.kits.maps.utils.clustering.algo.PreCachingAlgorithmDecorator;
import me.tatiyanupanwong.supasin.android.libraries.kits.maps.utils.clustering.algo.ScreenBasedAlgorithm;
import me.tatiyanupanwong.supasin.android.libraries.kits.maps.utils.clustering.algo.ScreenBasedAlgorithmAdapter;
import me.tatiyanupanwong.supasin.android.libraries.kits.maps.utils.clustering.view.ClusterRenderer;
import me.tatiyanupanwong.supasin.android.libraries.kits.maps.utils.clustering.view.DefaultClusterRenderer;
import me.tatiyanupanwong.supasin.android.libraries.kits.maps.utils.collections.MarkerManager;

/**
 * Groups many items on a map based on zoom level.
 * <p/>
 * ClusterManager should be added to the map as an:
 * <ul>
 *     <li>
 *         {@link
 *         MapClient.OnCameraIdleListener}
 *     </li>
 *     <li>
 *         {@link
 *         MapClient.OnMarkerClickListener}
 *     </li>
 * </ul>
 */
public class ClusterManager<T extends ClusterItem> implements
        MapClient.OnCameraIdleListener,
        MapClient.OnMarkerClickListener,
        MapClient.OnInfoWindowClickListener {

    private final MarkerManager mMarkerManager;
    private final MarkerManager.Collection mMarkers;
    private final MarkerManager.Collection mClusterMarkers;

    private ScreenBasedAlgorithm<T> mAlgorithm;
    private ClusterRenderer<T> mRenderer;

    private final MapClient mMap;
    private CameraPosition mPreviousCameraPosition;
    private ClusterTask mClusterTask;
    private final ReadWriteLock mClusterTaskLock = new ReentrantReadWriteLock();

    private OnClusterItemClickListener<T> mOnClusterItemClickListener;
    private OnClusterInfoWindowClickListener<T> mOnClusterInfoWindowClickListener;
    private OnClusterInfoWindowLongClickListener<T> mOnClusterInfoWindowLongClickListener;
    private OnClusterItemInfoWindowClickListener<T> mOnClusterItemInfoWindowClickListener;
    private OnClusterItemInfoWindowLongClickListener<T> mOnClusterItemInfoWindowLongClickListener;
    private OnClusterClickListener<T> mOnClusterClickListener;

    public ClusterManager(Context context, MapClient map) {
        this(context, map, new MarkerManager(map));
    }

    public ClusterManager(Context context, MapClient map, MarkerManager markerManager) {
        mMap = map;
        mMarkerManager = markerManager;
        mClusterMarkers = markerManager.newCollection();
        mMarkers = markerManager.newCollection();
        mRenderer = new DefaultClusterRenderer<>(context, map, this);
        mAlgorithm = new ScreenBasedAlgorithmAdapter<>(new PreCachingAlgorithmDecorator<>(
                new NonHierarchicalDistanceBasedAlgorithm<T>()));

        mClusterTask = new ClusterTask();
        mRenderer.onAdd();
    }

    public MarkerManager.Collection getMarkerCollection() {
        return mMarkers;
    }

    public MarkerManager.Collection getClusterMarkerCollection() {
        return mClusterMarkers;
    }

    public MarkerManager getMarkerManager() {
        return mMarkerManager;
    }

    public void setRenderer(ClusterRenderer<T> renderer) {
        mRenderer.setOnClusterClickListener(null);
        mRenderer.setOnClusterItemClickListener(null);
        mClusterMarkers.clear();
        mMarkers.clear();
        mRenderer.onRemove();
        mRenderer = renderer;
        mRenderer.onAdd();
        mRenderer.setOnClusterClickListener(mOnClusterClickListener);
        mRenderer.setOnClusterInfoWindowClickListener(mOnClusterInfoWindowClickListener);
        mRenderer.setOnClusterInfoWindowLongClickListener(mOnClusterInfoWindowLongClickListener);
        mRenderer.setOnClusterItemClickListener(mOnClusterItemClickListener);
        mRenderer.setOnClusterItemInfoWindowClickListener(mOnClusterItemInfoWindowClickListener);
        mRenderer.setOnClusterItemInfoWindowLongClickListener(
                mOnClusterItemInfoWindowLongClickListener);
        cluster();
    }

    public void setAlgorithm(Algorithm<T> algorithm) {
        if (algorithm instanceof ScreenBasedAlgorithm) {
            setAlgorithm((ScreenBasedAlgorithm<T>) algorithm);
        } else {
            setAlgorithm(new ScreenBasedAlgorithmAdapter<>(algorithm));
        }
    }

    public void setAlgorithm(ScreenBasedAlgorithm<T> algorithm) {
        algorithm.lock();
        try {
            final Algorithm<T> oldAlgorithm = getAlgorithm();
            mAlgorithm = algorithm;

            if (oldAlgorithm != null) {
                oldAlgorithm.lock();
                try {
                    algorithm.addItems(oldAlgorithm.getItems());
                } finally {
                    oldAlgorithm.unlock();
                }
            }
        } finally {
            algorithm.unlock();
        }

        if (mAlgorithm.shouldReclusterOnMapMovement()) {
            mAlgorithm.onCameraChange(mMap.getCameraPosition());
        }

        cluster();
    }

    public void setAnimation(boolean animate) {
        mRenderer.setAnimation(animate);
    }

    public ClusterRenderer<T> getRenderer() {
        return mRenderer;
    }

    public Algorithm<T> getAlgorithm() {
        return mAlgorithm;
    }

    /**
     * Removes all items from the cluster manager. After calling this method you must invoke
     * {@link #cluster()} for the map to be cleared.
     */
    public void clearItems() {
        final Algorithm<T> algorithm = getAlgorithm();
        algorithm.lock();
        try {
            algorithm.clearItems();
        } finally {
            algorithm.unlock();
        }
    }

    /**
     * Adds items to clusters. After calling this method you must invoke {@link #cluster()} for the
     * state of the clusters to be updated on the map.
     *
     * @param items items to add to clusters
     * @return {@code true} if the cluster manager contents changed as a result of the call
     */
    public boolean addItems(Collection<T> items) {
        final Algorithm<T> algorithm = getAlgorithm();
        algorithm.lock();
        try {
            return algorithm.addItems(items);
        } finally {
            algorithm.unlock();
        }
    }

    /**
     * Adds an item to a cluster. After calling this method you must invoke {@link #cluster()} for
     * the state of the clusters to be updated on the map.
     *
     * @param myItem item to add to clusters
     * @return {@code true} if the cluster manager contents changed as a result of the call
     */
    public boolean addItem(T myItem) {
        final Algorithm<T> algorithm = getAlgorithm();
        algorithm.lock();
        try {
            return algorithm.addItem(myItem);
        } finally {
            algorithm.unlock();
        }
    }

    /**
     * Removes items from clusters. After calling this method you must invoke {@link #cluster()} for
     * the state of the clusters to be updated on the map.
     *
     * @param items items to remove from clusters
     * @return {@code true} if the cluster manager contents changed as a result of the call
     */
    public boolean removeItems(Collection<T> items) {
        final Algorithm<T> algorithm = getAlgorithm();
        algorithm.lock();
        try {
            return algorithm.removeItems(items);
        } finally {
            algorithm.unlock();
        }
    }

    /**
     * Removes an item from clusters. After calling this method you must invoke {@link #cluster()}
     * for the state of the clusters to be updated on the map.
     *
     * @param item item to remove from clusters
     * @return {@code true} if the item was removed from the cluster manager as a result of this
     * call
     */
    public boolean removeItem(T item) {
        final Algorithm<T> algorithm = getAlgorithm();
        algorithm.lock();
        try {
            return algorithm.removeItem(item);
        } finally {
            algorithm.unlock();
        }
    }

    /**
     * Updates an item in clusters. After calling this method you must invoke {@link #cluster()} for
     * the state of the clusters to be updated on the map.
     *
     * @param item item to update in clusters
     * @return {@code true} if the item was updated in the cluster manager, {@code false} if the
     * item is not contained within the cluster manager and the cluster manager contents are
     * unchanged
     */
    public boolean updateItem(T item) {
        final Algorithm<T> algorithm = getAlgorithm();
        algorithm.lock();
        try {
            return algorithm.updateItem(item);
        } finally {
            algorithm.unlock();
        }
    }

    /**
     * Force a re-cluster on the map. You should call this after adding, removing, updating,
     * or clearing item(s).
     */
    public void cluster() {
        mClusterTaskLock.writeLock().lock();
        try {
            // Attempt to cancel the in-flight request.
            mClusterTask.cancel(true);
            mClusterTask = new ClusterTask();
            mClusterTask.executeOnExecutor(
                    AsyncTask.THREAD_POOL_EXECUTOR, mMap.getCameraPosition().getZoom());
        } finally {
            mClusterTaskLock.writeLock().unlock();
        }
    }

    /**
     * Might re-cluster.
     */
    @Override
    public void onCameraIdle() {
        if (mRenderer instanceof MapClient.OnCameraIdleListener) {
            ((MapClient.OnCameraIdleListener) mRenderer).onCameraIdle();
        }

        mAlgorithm.onCameraChange(mMap.getCameraPosition());

        // delegate clustering to the algorithm
        if (mAlgorithm.shouldReclusterOnMapMovement()) {
            cluster();

            // Don't re-compute clusters if the map has just been panned/tilted/rotated.
        } else if (mPreviousCameraPosition == null || mPreviousCameraPosition.getZoom()
                != mMap.getCameraPosition().getZoom()) {
            mPreviousCameraPosition = mMap.getCameraPosition();
            cluster();
        }
    }

    @Override
    public boolean onMarkerClick(@NonNull Marker marker) {
        return getMarkerManager().onMarkerClick(marker);
    }

    @Override
    public void onInfoWindowClick(@NonNull Marker marker) {
        getMarkerManager().onInfoWindowClick(marker);
    }

    /**
     * Runs the clustering algorithm in a background thread, then re-paints when results come back.
     */
    private class ClusterTask extends AsyncTask<Float, Void, Set<? extends Cluster<T>>> {
        @Override
        protected Set<? extends Cluster<T>> doInBackground(Float... zoom) {
            final Algorithm<T> algorithm = getAlgorithm();
            algorithm.lock();
            try {
                return algorithm.getClusters(zoom[0]);
            } finally {
                algorithm.unlock();
            }
        }

        @Override
        protected void onPostExecute(Set<? extends Cluster<T>> clusters) {
            mRenderer.onClustersChanged(clusters);
        }
    }

    /**
     * Sets a callback that's invoked when a {@link Cluster} is tapped. Note: For this listener to
     * function, the ClusterManager must be added as a click listener to the map.
     */
    public void setOnClusterClickListener(OnClusterClickListener<T> listener) {
        mOnClusterClickListener = listener;
        mRenderer.setOnClusterClickListener(listener);
    }

    /**
     * Sets a callback that's invoked when a {@link Cluster} info window is tapped. Note: For this
     * listener to function, the ClusterManager must be added as a info window click listener to
     * the map.
     */
    public void setOnClusterInfoWindowClickListener(
            OnClusterInfoWindowClickListener<T> listener) {
        mOnClusterInfoWindowClickListener = listener;
        mRenderer.setOnClusterInfoWindowClickListener(listener);
    }

    /**
     * Sets a callback that's invoked when a {@link Cluster} info window is long-pressed. Note: For
     * this listener to function, the ClusterManager must be added as a info window click listener
     * to the map.
     */
    public void setOnClusterInfoWindowLongClickListener(
            OnClusterInfoWindowLongClickListener<T> listener) {
        mOnClusterInfoWindowLongClickListener = listener;
        mRenderer.setOnClusterInfoWindowLongClickListener(listener);
    }

    /**
     * Sets a callback that's invoked when an individual {@link ClusterItem} is tapped. Note: For
     * this listener to function, the ClusterManager must be added as a click listener to the map.
     */
    public void setOnClusterItemClickListener(OnClusterItemClickListener<T> listener) {
        mOnClusterItemClickListener = listener;
        mRenderer.setOnClusterItemClickListener(listener);
    }

    /**
     * Sets a callback that's invoked when an individual {@link ClusterItem}'s Info Window is
     * tapped. Note: For this listener to function, the ClusterManager must be added as a info
     * window click listener to the map.
     */
    public void setOnClusterItemInfoWindowClickListener(
            OnClusterItemInfoWindowClickListener<T> listener) {
        mOnClusterItemInfoWindowClickListener = listener;
        mRenderer.setOnClusterItemInfoWindowClickListener(listener);
    }

    /**
     * Sets a callback that's invoked when an individual {@link ClusterItem}'s Info Window is
     * long-pressed. Note: For this listener to function, the ClusterManager must be added as a
     * info window click listener to the map.
     */
    public void setOnClusterItemInfoWindowLongClickListener(
            OnClusterItemInfoWindowLongClickListener<T> listener) {
        mOnClusterItemInfoWindowLongClickListener = listener;
        mRenderer.setOnClusterItemInfoWindowLongClickListener(listener);
    }

    /**
     * Interface definition for a callback to be invoked when a Cluster is clicked.
     */
    public interface OnClusterClickListener<T extends ClusterItem> {
        /**
         * Called when cluster is clicked.
         * Return {@code true} if click has been handled
         * Return false and the click will dispatched to the next listener
         */
        boolean onClusterClick(Cluster<T> cluster);
    }

    /**
     * Interface definition for a callback to be invoked when a Cluster's Info Window is clicked.
     */
    public interface OnClusterInfoWindowClickListener<T extends ClusterItem> {
        void onClusterInfoWindowClick(Cluster<T> cluster);
    }

    /**
     * Interface definition for a callback to be invoked when a Cluster's Info Window is long clicked.
     */
    public interface OnClusterInfoWindowLongClickListener<T extends ClusterItem> {
        void onClusterInfoWindowLongClick(Cluster<T> cluster);
    }

    /**
     * Interface definition for a callback to be invoked when an individual ClusterItem is clicked.
     */
    public interface OnClusterItemClickListener<T extends ClusterItem> {

        /**
         * Called when {@code item} is clicked.
         *
         * @param item the item clicked
         *
         * @return {@code true} if the listener consumed the event (i.e. the default behavior should not
         * occur), {@code false} otherwise (i.e. the default behavior should occur).  The default behavior
         * is for the camera to move to the marker and an info window to appear.
         */
        boolean onClusterItemClick(T item);
    }

    /**
     * Called when an individual ClusterItem's Info Window is clicked.
     */
    public interface OnClusterItemInfoWindowClickListener<T extends ClusterItem> {
        void onClusterItemInfoWindowClick(T item);
    }

    /**
     * Called when an individual ClusterItem's Info Window is long clicked.
     */
    public interface OnClusterItemInfoWindowLongClickListener<T extends ClusterItem> {
        void onClusterItemInfoWindowLongClick(T item);
    }
}
