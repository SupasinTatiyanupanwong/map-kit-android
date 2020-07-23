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

package me.tatiyanupanwong.supasin.android.libraries.kits.maps.utils.clustering.view;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Set;

import me.tatiyanupanwong.supasin.android.libraries.kits.maps.utils.clustering.Cluster;
import me.tatiyanupanwong.supasin.android.libraries.kits.maps.utils.clustering.ClusterItem;
import me.tatiyanupanwong.supasin.android.libraries.kits.maps.utils.clustering.ClusterManager;

/**
 * Renders clusters.
 */
public interface ClusterRenderer<T extends ClusterItem> {

    /**
     * Called when the view needs to be updated because new clusters need to be displayed.
     *
     * @param clusters the clusters to be displayed.
     */
    void onClustersChanged(@NonNull Set<? extends Cluster<T>> clusters);

    void setOnClusterClickListener(@Nullable ClusterManager.OnClusterClickListener<T> listener);

    void setOnClusterInfoWindowClickListener(
            @Nullable ClusterManager.OnClusterInfoWindowClickListener<T> listener);

    void setOnClusterInfoWindowLongClickListener(
            @Nullable ClusterManager.OnClusterInfoWindowLongClickListener<T> listener);

    void setOnClusterItemClickListener(
            @Nullable ClusterManager.OnClusterItemClickListener<T> listener);

    void setOnClusterItemInfoWindowClickListener(
            @Nullable ClusterManager.OnClusterItemInfoWindowClickListener<T> listener);

    void setOnClusterItemInfoWindowLongClickListener(
            @Nullable ClusterManager.OnClusterItemInfoWindowLongClickListener<T> listener);

    /**
     * Called to set animation on or off
     */
    void setAnimation(boolean animate);

    /**
     * Called when the view is added.
     */
    void onAdd();

    /**
     * Called when the view is removed.
     */
    void onRemove();
}
