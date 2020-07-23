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

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.MessageQueue;
import android.util.SparseArray;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import me.tatiyanupanwong.supasin.android.libraries.kits.maps.MapKit;
import me.tatiyanupanwong.supasin.android.libraries.kits.maps.model.BitmapDescriptor;
import me.tatiyanupanwong.supasin.android.libraries.kits.maps.model.LatLng;
import me.tatiyanupanwong.supasin.android.libraries.kits.maps.model.LatLngBounds;
import me.tatiyanupanwong.supasin.android.libraries.kits.maps.model.MapClient;
import me.tatiyanupanwong.supasin.android.libraries.kits.maps.model.Marker;
import me.tatiyanupanwong.supasin.android.libraries.kits.maps.model.Projection;
import me.tatiyanupanwong.supasin.android.libraries.kits.maps.utils.clustering.Cluster;
import me.tatiyanupanwong.supasin.android.libraries.kits.maps.utils.clustering.ClusterItem;
import me.tatiyanupanwong.supasin.android.libraries.kits.maps.utils.clustering.ClusterManager;
import me.tatiyanupanwong.supasin.android.libraries.kits.maps.utils.clustering.R;
import me.tatiyanupanwong.supasin.android.libraries.kits.maps.utils.collections.MarkerManager;
import me.tatiyanupanwong.supasin.android.libraries.kits.maps.utils.geometry.Point;
import me.tatiyanupanwong.supasin.android.libraries.kits.maps.utils.projection.SphericalMercatorProjection;
import me.tatiyanupanwong.supasin.android.libraries.kits.maps.utils.ui.IconGenerator;
import me.tatiyanupanwong.supasin.android.libraries.kits.maps.utils.ui.SquareTextView;

/**
 * The default view for a {@link ClusterManager}. {@link Marker}s are animated in and out of
 * clusters.
 */
public class DefaultClusterRenderer<T extends ClusterItem> implements ClusterRenderer<T> {
    private final MapClient mMap;
    private final IconGenerator mIconGenerator;
    private final ClusterManager<T> mClusterManager;
    private final float mDensity;
    private boolean mAnimate;
    private final Executor mExecutor = Executors.newSingleThreadExecutor();

    private static final int[] BUCKETS = {10, 20, 50, 100, 200, 500, 1000};
    private ShapeDrawable mColoredCircleBackground;

    /**
     * {@link Marker}s that are currently on the map.
     */
    private Set<MarkerWithPosition> mMarkers = Collections.newSetFromMap(
            new ConcurrentHashMap<MarkerWithPosition, Boolean>());

    /**
     * Icons for each bucket.
     */
    private final SparseArray<BitmapDescriptor> mIcons = new SparseArray<>();

    /**
     * {@link Marker}s for single {@link ClusterItem}s.
     */
    private final MarkerCache<T> mMarkerCache = new MarkerCache<>();

    /**
     * If cluster size is less than this size, display individual markers.
     */
    private int mMinClusterSize = 4;

    /**
     * The currently displayed set of clusters.
     */
    private Set<? extends Cluster<T>> mClusters;

    /**
     * {@link Marker}s for {@link Cluster}s.
     */
    private final MarkerCache<Cluster<T>> mClusterMarkerCache = new MarkerCache<>();

    /**
     * The target zoom level for the current set of clusters.
     */
    private float mZoom;

    private final ViewModifier mViewModifier = new ViewModifier();

    private ClusterManager.OnClusterClickListener<T> mClickListener;
    private ClusterManager.OnClusterInfoWindowClickListener<T> mInfoWindowClickListener;
    private ClusterManager.OnClusterInfoWindowLongClickListener<T> mInfoWindowLongClickListener;
    private ClusterManager.OnClusterItemClickListener<T> mItemClickListener;
    private ClusterManager.OnClusterItemInfoWindowClickListener<T> mItemInfoWindowClickListener;
    private ClusterManager.OnClusterItemInfoWindowLongClickListener<T>
            mItemInfoWindowLongClickListener;

    public DefaultClusterRenderer(
            Context context, MapClient map, ClusterManager<T> clusterManager) {
        mMap = map;
        mAnimate = true;
        mDensity = context.getResources().getDisplayMetrics().density;
        mIconGenerator = new IconGenerator(context);
        mIconGenerator.setContentView(makeSquareTextView(context));
        mIconGenerator.setTextAppearance(R.style.mku_ClusterIcon_TextAppearance);
        mIconGenerator.setBackground(makeClusterBackground());
        mClusterManager = clusterManager;
    }

    @Override
    public void onAdd() {
        mClusterManager.getMarkerCollection().setOnMarkerClickListener(
                new MapClient.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(@NonNull Marker marker) {
                        return mItemClickListener != null &&
                                mItemClickListener.onClusterItemClick(mMarkerCache.get(marker));
                    }
                });

        mClusterManager.getMarkerCollection().setOnInfoWindowClickListener(
                new MapClient.OnInfoWindowClickListener() {
                    @Override
                    public void onInfoWindowClick(@NonNull Marker marker) {
                        if (mItemInfoWindowClickListener != null) {
                            mItemInfoWindowClickListener
                                    .onClusterItemInfoWindowClick(mMarkerCache.get(marker));
                        }
                    }
                });

        mClusterManager.getMarkerCollection().setOnInfoWindowLongClickListener(
                new MapClient.OnInfoWindowLongClickListener() {
                    @Override
                    public void onInfoWindowLongClick(@NonNull Marker marker) {
                        if (mItemInfoWindowLongClickListener != null) {
                            mItemInfoWindowLongClickListener
                                    .onClusterItemInfoWindowLongClick(mMarkerCache.get(marker));
                        }
                    }
                });

        mClusterManager.getClusterMarkerCollection().setOnMarkerClickListener(
                new MapClient.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(@NonNull Marker marker) {
                        return mClickListener != null &&
                                mClickListener.onClusterClick(mClusterMarkerCache.get(marker));
                    }
                });

        mClusterManager.getClusterMarkerCollection().setOnInfoWindowClickListener(
                new MapClient.OnInfoWindowClickListener() {
                    @Override
                    public void onInfoWindowClick(@NonNull Marker marker) {
                        if (mInfoWindowClickListener != null) {
                            mInfoWindowClickListener
                                    .onClusterInfoWindowClick(mClusterMarkerCache.get(marker));
                        }
                    }
                });

        mClusterManager.getClusterMarkerCollection().setOnInfoWindowLongClickListener(
                new MapClient.OnInfoWindowLongClickListener() {
                    @Override
                    public void onInfoWindowLongClick(@NonNull Marker marker) {
                        if (mInfoWindowLongClickListener != null) {
                            mInfoWindowLongClickListener
                                    .onClusterInfoWindowLongClick(mClusterMarkerCache.get(marker));
                        }
                    }
                });
    }

    @Override
    public void onRemove() {
        mClusterManager.getMarkerCollection().setOnMarkerClickListener(null);
        mClusterManager.getMarkerCollection().setOnInfoWindowClickListener(null);
        mClusterManager.getMarkerCollection().setOnInfoWindowLongClickListener(null);
        mClusterManager.getClusterMarkerCollection().setOnMarkerClickListener(null);
        mClusterManager.getClusterMarkerCollection().setOnInfoWindowClickListener(null);
        mClusterManager.getClusterMarkerCollection().setOnInfoWindowLongClickListener(null);
    }

    private LayerDrawable makeClusterBackground() {
        mColoredCircleBackground = new ShapeDrawable(new OvalShape());
        ShapeDrawable outline = new ShapeDrawable(new OvalShape());
        outline.getPaint().setColor(0x80ffffff); // Transparent white.
        LayerDrawable background = new LayerDrawable(
                new Drawable[] { outline, mColoredCircleBackground });
        int strokeWidth = (int) (mDensity * 3);
        background.setLayerInset(1, strokeWidth, strokeWidth, strokeWidth, strokeWidth);
        return background;
    }

    private SquareTextView makeSquareTextView(Context context) {
        SquareTextView squareTextView = new SquareTextView(context);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        squareTextView.setLayoutParams(layoutParams);
        squareTextView.setId(R.id.mku_text);
        int twelveDpi = (int) (12 * mDensity);
        squareTextView.setPadding(twelveDpi, twelveDpi, twelveDpi, twelveDpi);
        return squareTextView;
    }

    protected int getColor(int clusterSize) {
        final float hueRange = 220;
        final float sizeRange = 300;
        final float size = Math.min(clusterSize, sizeRange);
        final float hue =
                (sizeRange - size) * (sizeRange - size) / (sizeRange * sizeRange) * hueRange;
        return Color.HSVToColor(new float[] {
                hue, 1f, .6f
        });
    }

    @NonNull
    protected String getClusterText(int bucket) {
        if (bucket < BUCKETS[0]) {
            return String.valueOf(bucket);
        }
        return bucket + "+";
    }

    /**
     * Gets the "bucket" for a particular cluster. By default, uses the number of points within the
     * cluster, bucketed to some set points.
     */
    protected int getBucket(@NonNull Cluster<T> cluster) {
        int size = cluster.getSize();
        if (size <= BUCKETS[0]) {
            return size;
        }
        for (int i = 0; i < BUCKETS.length - 1; i++) {
            if (size < BUCKETS[i + 1]) {
                return BUCKETS[i];
            }
        }
        return BUCKETS[BUCKETS.length - 1];
    }

    /**
     * Gets the minimum cluster size used to render clusters. For example, if "4" is returned,
     * then for any clusters of size 3 or less the items will be rendered as individual markers
     * instead of as a single cluster marker.
     *
     * @return the minimum cluster size used to render clusters. For example, if "4" is returned,
     * then for any clusters of size 3 or less the items will be rendered as individual markers
     * instead of as a single cluster marker.
     */
    public int getMinClusterSize() {
        return mMinClusterSize;
    }

    /**
     * Sets the minimum cluster size used to render clusters. For example, if "4" is provided,
     * then for any clusters of size 3 or less the items will be rendered as individual markers
     * instead of as a single cluster marker.
     *
     * @param minClusterSize the minimum cluster size used to render clusters. For example, if "4"
     *                       is provided, then for any clusters of size 3 or less the items will be
     *                       rendered as individual markers instead of as a single cluster marker.
     */
    public void setMinClusterSize(int minClusterSize) {
        mMinClusterSize = minClusterSize;
    }

    /**
     * ViewModifier ensures only one re-rendering of the view occurs at a time, and schedules
     * re-rendering, which is performed by the RenderTask.
     */
    @SuppressLint("HandlerLeak")
    private class ViewModifier extends Handler {
        private static final int RUN_TASK = 0;
        private static final int TASK_FINISHED = 1;
        private boolean mViewModificationInProgress = false;
        private RenderTask mNextClusters = null;

        @Override
        public void handleMessage(Message msg) {
            if (msg.what == TASK_FINISHED) {
                mViewModificationInProgress = false;
                if (mNextClusters != null) {
                    // Run the task that was queued up.
                    sendEmptyMessage(RUN_TASK);
                }
                return;
            }
            removeMessages(RUN_TASK);

            if (mViewModificationInProgress) {
                // Busy - wait for the callback.
                return;
            }

            if (mNextClusters == null) {
                // Nothing to do.
                return;
            }
            Projection projection = mMap.getProjection();

            RenderTask renderTask;
            synchronized (this) {
                renderTask = mNextClusters;
                mNextClusters = null;
                mViewModificationInProgress = true;
            }

            renderTask.setCallback(new Runnable() {
                @Override
                public void run() {
                    sendEmptyMessage(TASK_FINISHED);
                }
            });
            renderTask.setProjection(projection);
            renderTask.setMapZoom(mMap.getCameraPosition().getZoom());
            mExecutor.execute(renderTask);
        }

        public void queue(@NonNull Set<? extends Cluster<T>> clusters) {
            synchronized (this) {
                // Overwrite any pending cluster tasks - we don't care about intermediate states.
                mNextClusters = new RenderTask(clusters);
            }
            sendEmptyMessage(RUN_TASK);
        }
    }

    /**
     * Determine whether the cluster should be rendered as individual markers or a cluster.
     *
     * @param cluster cluster to examine for rendering
     * @return true if the provided cluster should be rendered as a single marker on the map, false
     * if the items within this cluster should be rendered as individual markers instead.
     */
    protected boolean shouldRenderAsCluster(@NonNull Cluster<T> cluster) {
        return cluster.getSize() >= mMinClusterSize;
    }

    /**
     * Transforms the current view (represented by {@link DefaultClusterRenderer#mClusters} and
     * {@link DefaultClusterRenderer#mZoom}) to a new zoom level and set of clusters.
     * <p/>
     * This must be run off the UI thread. Work is coordinated in the RenderTask, then queued up to
     * be executed by a MarkerModifier.
     * <p/>
     * There are three stages for the render:
     * <p/>
     * 1. Markers are added to the map
     * <p/>
     * 2. Markers are animated to their final position
     * <p/>
     * 3. Any old markers are removed from the map
     * <p/>
     * When zooming in, markers are animated out from the nearest existing cluster. When zooming
     * out, existing clusters are animated to the nearest new cluster.
     */
    private class RenderTask implements Runnable {
        final Set<? extends Cluster<T>> mClusters;
        private Runnable mCallback;
        private Projection mProjection;
        private SphericalMercatorProjection mSphericalMercatorProjection;
        private float mMapZoom;

        private RenderTask(@NonNull Set<? extends Cluster<T>> clusters) {
            mClusters = clusters;
        }

        /**
         * Sets a {@link Runnable} to be invoked when all work has been completed.
         *
         * @param callback a {@link Runnable} to be invoked when all work has been completed.
         */
        public void setCallback(Runnable callback) {
            mCallback = callback;
        }

        public void setProjection(Projection projection) {
            this.mProjection = projection;
        }

        public void setMapZoom(float zoom) {
            mMapZoom = zoom;
            mSphericalMercatorProjection =
                    new SphericalMercatorProjection(256 * Math.pow(2, Math.min(zoom, mZoom)));
        }

        @SuppressLint("NewApi")
        public void run() {
            if (mClusters.equals(DefaultClusterRenderer.this.mClusters)) {
                mCallback.run();
                return;
            }

            final MarkerModifier markerModifier = new MarkerModifier();

            final float zoom = mMapZoom;
            final boolean zoomingIn = zoom > mZoom;
            final float zoomDelta = zoom - mZoom;

            final Set<MarkerWithPosition> markersToRemove = mMarkers;
            // Prevent crashes: https://issuetracker.google.com/issues/35827242
            LatLngBounds visibleBounds;
            try {
                visibleBounds = mProjection.getVisibleRegion().getLatLngBounds();
            } catch (Exception e) {
                e.printStackTrace();
                visibleBounds = MapKit.newLatLngBoundsBuilder()
                        .include(MapKit.newLatLng(0, 0))
                        .build();
            }
            // TODO: Add some padding, so that markers can animate in from off-screen.

            // Find all of the existing clusters that are on-screen. These are candidates for
            // markers to animate from.
            List<Point> existingClustersOnScreen = null;
            if (DefaultClusterRenderer.this.mClusters != null && mAnimate) {
                existingClustersOnScreen = new ArrayList<>();
                for (Cluster<T> c : DefaultClusterRenderer.this.mClusters) {
                    if (shouldRenderAsCluster(c) && visibleBounds.contains(c.getPosition())) {
                        Point point = mSphericalMercatorProjection.toPoint(c.getPosition());
                        existingClustersOnScreen.add(point);
                    }
                }
            }

            // Create the new markers and animate them to their new positions.
            final Set<MarkerWithPosition> newMarkers = Collections.newSetFromMap(
                    new ConcurrentHashMap<MarkerWithPosition, Boolean>());
            for (Cluster<T> cluster : mClusters) {
                boolean onScreen = visibleBounds.contains(cluster.getPosition());
                if (zoomingIn && onScreen && mAnimate) {
                    Point point = mSphericalMercatorProjection.toPoint(cluster.getPosition());
                    Point closest = findClosestCluster(existingClustersOnScreen, point);
                    if (closest != null) {
                        LatLng animateTo = mSphericalMercatorProjection.toLatLng(closest);
                        markerModifier.add(
                                true, new CreateMarkerTask(cluster, newMarkers, animateTo));
                    } else {
                        markerModifier.add(true, new CreateMarkerTask(cluster, newMarkers, null));
                    }
                } else {
                    markerModifier.add(onScreen, new CreateMarkerTask(cluster, newMarkers, null));
                }
            }

            // Wait for all markers to be added.
            markerModifier.waitUntilFree();

            // Don't remove any markers that were just added. This is basically anything that had
            // a hit in the MarkerCache.
            markersToRemove.removeAll(newMarkers);

            // Find all of the new clusters that were added on-screen. These are candidates for
            // markers to animate from.
            List<Point> newClustersOnScreen = null;
            if (mAnimate) {
                newClustersOnScreen = new ArrayList<>();
                for (Cluster<T> cluster : mClusters) {
                    if (shouldRenderAsCluster(cluster)
                            && visibleBounds.contains(cluster.getPosition())) {
                        Point point = mSphericalMercatorProjection.toPoint(cluster.getPosition());
                        newClustersOnScreen.add(point);
                    }
                }
            }

            // Remove the old markers, animating them into clusters if zooming out.
            for (final MarkerWithPosition marker : markersToRemove) {
                boolean onScreen = visibleBounds.contains(marker.mPosition);
                // Don't animate when zooming out more than 3 zoom levels.
                // TODO: drop animation based on speed of device & number of markers to animate.
                if (!zoomingIn && zoomDelta > -3 && onScreen && mAnimate) {
                    final Point point = mSphericalMercatorProjection.toPoint(marker.mPosition);
                    final Point closest = findClosestCluster(newClustersOnScreen, point);
                    if (closest != null) {
                        LatLng animateTo = mSphericalMercatorProjection.toLatLng(closest);
                        markerModifier.animateThenRemove(marker, marker.mPosition, animateTo);
                    } else {
                        markerModifier.remove(true, marker.mMarker);
                    }
                } else {
                    markerModifier.remove(onScreen, marker.mMarker);
                }
            }

            markerModifier.waitUntilFree();

            mMarkers = newMarkers;
            DefaultClusterRenderer.this.mClusters = mClusters;
            mZoom = zoom;

            mCallback.run();
        }
    }

    @Override
    public void onClustersChanged(@NonNull Set<? extends Cluster<T>> clusters) {
        mViewModifier.queue(clusters);
    }

    @Override
    public void setOnClusterClickListener(
            @Nullable ClusterManager.OnClusterClickListener<T> listener) {
        mClickListener = listener;
    }

    @Override
    public void setOnClusterInfoWindowClickListener(
            @Nullable ClusterManager.OnClusterInfoWindowClickListener<T> listener) {
        mInfoWindowClickListener = listener;
    }

    @Override
    public void setOnClusterInfoWindowLongClickListener(
            @Nullable ClusterManager.OnClusterInfoWindowLongClickListener<T> listener) {
        mInfoWindowLongClickListener = listener;
    }

    @Override
    public void setOnClusterItemClickListener(
            @Nullable ClusterManager.OnClusterItemClickListener<T> listener) {
        mItemClickListener = listener;
    }

    @Override
    public void setOnClusterItemInfoWindowClickListener(
            @Nullable ClusterManager.OnClusterItemInfoWindowClickListener<T> listener) {
        mItemInfoWindowClickListener = listener;
    }

    @Override
    public void setOnClusterItemInfoWindowLongClickListener(
            @Nullable ClusterManager.OnClusterItemInfoWindowLongClickListener<T> listener) {
        mItemInfoWindowLongClickListener = listener;
    }

    @Override
    public void setAnimation(boolean animate) {
        mAnimate = animate;
    }

    private static double distanceSquared(Point a, Point b) {
        return (a.x - b.x) * (a.x - b.x) + (a.y - b.y) * (a.y - b.y);
    }

    private Point findClosestCluster(List<Point> markers, Point point) {
        if (markers == null || markers.isEmpty()) return null;

        int maxDistance = mClusterManager.getAlgorithm().getMaxDistanceBetweenClusteredItems();
        double minDistSquared = maxDistance * maxDistance;
        Point closest = null;
        for (Point candidate : markers) {
            double dist = distanceSquared(candidate, point);
            if (dist < minDistSquared) {
                closest = candidate;
                minDistSquared = dist;
            }
        }
        return closest;
    }

    /**
     * Handles all markerWithPosition manipulations on the map. Work (such as adding, removing, or
     * animating a markerWithPosition) is performed while trying not to block the rest of the app's
     * UI.
     */
    @SuppressLint("HandlerLeak")
    private class MarkerModifier extends Handler implements MessageQueue.IdleHandler {
        private static final int BLANK = 0;

        private final Lock mLock = new ReentrantLock();
        private final Condition mBusyCondition = mLock.newCondition();

        private final Queue<CreateMarkerTask> mCreateMarkerTasks = new LinkedList<>();
        private final Queue<CreateMarkerTask> mOnScreenCreateMarkerTasks = new LinkedList<>();
        private final Queue<Marker> mRemoveMarkerTasks = new LinkedList<>();
        private final Queue<Marker> mOnScreenRemoveMarkerTasks = new LinkedList<>();
        private final Queue<AnimationTask> mAnimationTasks = new LinkedList<>();

        /**
         * Whether the idle listener has been added to the UI thread's MessageQueue.
         */
        private boolean mListenerAdded;

        private MarkerModifier() {
            super(Looper.getMainLooper());
        }

        /**
         * Creates markers for a cluster some time in the future.
         *
         * @param priority whether this operation should have priority.
         */
        public void add(boolean priority, CreateMarkerTask c) {
            mLock.lock();
            sendEmptyMessage(BLANK);
            if (priority) {
                mOnScreenCreateMarkerTasks.add(c);
            } else {
                mCreateMarkerTasks.add(c);
            }
            mLock.unlock();
        }

        /**
         * Removes a markerWithPosition some time in the future.
         *
         * @param priority whether this operation should have priority.
         * @param m        the markerWithPosition to remove.
         */
        public void remove(boolean priority, Marker m) {
            mLock.lock();
            sendEmptyMessage(BLANK);
            if (priority) {
                mOnScreenRemoveMarkerTasks.add(m);
            } else {
                mRemoveMarkerTasks.add(m);
            }
            mLock.unlock();
        }

        /**
         * Animates a markerWithPosition some time in the future.
         *
         * @param marker the markerWithPosition to animate.
         * @param from   the position to animate from.
         * @param to     the position to animate to.
         */
        public void animate(MarkerWithPosition marker, LatLng from, LatLng to) {
            mLock.lock();
            mAnimationTasks.add(new AnimationTask(marker, from, to));
            mLock.unlock();
        }

        /**
         * Animates a markerWithPosition some time in the future, and removes it when the animation
         * is complete.
         *
         * @param marker the markerWithPosition to animate.
         * @param from   the position to animate from.
         * @param to     the position to animate to.
         */
        @TargetApi(Build.VERSION_CODES.HONEYCOMB)
        public void animateThenRemove(MarkerWithPosition marker, LatLng from, LatLng to) {
            mLock.lock();
            AnimationTask animationTask = new AnimationTask(marker, from, to);
            animationTask.removeOnAnimationComplete(mClusterManager.getMarkerManager());
            mAnimationTasks.add(animationTask);
            mLock.unlock();
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            if (!mListenerAdded) {
                Looper.myQueue().addIdleHandler(this);
                mListenerAdded = true;
            }
            removeMessages(BLANK);

            mLock.lock();
            try {

                // Perform up to 10 tasks at once.
                // Consider only performing 10 remove tasks, not adds and animations.
                // Removes are relatively slow and are much better when batched.
                for (int i = 0; i < 10; i++) {
                    performNextTask();
                }

                if (!isBusy()) {
                    mListenerAdded = false;
                    Looper.myQueue().removeIdleHandler(this);
                    // Signal any other threads that are waiting.
                    mBusyCondition.signalAll();
                } else {
                    // Sometimes the idle queue may not be called - schedule up some work regardless
                    // of whether the UI thread is busy or not.
                    // TODO: try to remove this.
                    sendEmptyMessageDelayed(BLANK, 10);
                }
            } finally {
                mLock.unlock();
            }
        }

        /**
         * Perform the next task. Prioritise any on-screen work.
         */
        @TargetApi(Build.VERSION_CODES.HONEYCOMB)
        private void performNextTask() {
            if (!mOnScreenRemoveMarkerTasks.isEmpty()) {
                removeMarker(mOnScreenRemoveMarkerTasks.poll());
            } else if (!mAnimationTasks.isEmpty()) {
                mAnimationTasks.poll().perform();
            } else if (!mOnScreenCreateMarkerTasks.isEmpty()) {
                mOnScreenCreateMarkerTasks.poll().perform(this);
            } else if (!mCreateMarkerTasks.isEmpty()) {
                mCreateMarkerTasks.poll().perform(this);
            } else if (!mRemoveMarkerTasks.isEmpty()) {
                removeMarker(mRemoveMarkerTasks.poll());
            }
        }

        private void removeMarker(Marker m) {
            mMarkerCache.remove(m);
            mClusterMarkerCache.remove(m);
            mClusterManager.getMarkerManager().remove(m);
        }

        /**
         * @return {@code true} if there is still work to be processed.
         */
        public boolean isBusy() {
            try {
                mLock.lock();
                return !(mCreateMarkerTasks.isEmpty() && mOnScreenCreateMarkerTasks.isEmpty() &&
                        mOnScreenRemoveMarkerTasks.isEmpty() && mRemoveMarkerTasks.isEmpty() &&
                        mAnimationTasks.isEmpty()
                );
            } finally {
                mLock.unlock();
            }
        }

        /**
         * Blocks the calling thread until all work has been processed.
         */
        public void waitUntilFree() {
            while (isBusy()) {
                // Sometimes the idle queue may not be called - schedule up some work regardless
                // of whether the UI thread is busy or not.
                // TODO: try to remove this.
                sendEmptyMessage(BLANK);
                mLock.lock();
                try {
                    if (isBusy()) {
                        mBusyCondition.await();
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } finally {
                    mLock.unlock();
                }
            }
        }

        @Override
        public boolean queueIdle() {
            // When the UI is not busy, schedule some work.
            sendEmptyMessage(BLANK);
            return true;
        }
    }

    /**
     * A cache of markers representing individual ClusterItems.
     */
    private static class MarkerCache<T> {
        private final Map<T, Marker> mCache = new HashMap<>();
        private final Map<Marker, T> mCacheReverse = new HashMap<>();

        public Marker get(T item) {
            return mCache.get(item);
        }

        public T get(Marker m) {
            return mCacheReverse.get(m);
        }

        public void put(T item, Marker m) {
            mCache.put(item, m);
            mCacheReverse.put(m, item);
        }

        public void remove(Marker m) {
            T item = mCacheReverse.get(m);
            mCacheReverse.remove(m);
            mCache.remove(item);
        }
    }

    /**
     * Called before the marker for a {@link ClusterItem} is added to the map. The default
     * implementation sets the marker and snippet text based on the respective item text if they
     * are both available, otherwise it will set the title if available, and if not it will set
     * the marker title to the item snippet text if that is available.
     *
     * The first time {@link ClusterManager#cluster()} is invoked on a set of items
     * {@link #onBeforeClusterItemRendered(ClusterItem, Marker.Options)} will be called and
     * {@link #onClusterItemUpdated(ClusterItem, Marker)} will not be called.
     * If an item is removed and re-added (or updated) and {@link ClusterManager#cluster()} is
     * invoked again, then {@link #onClusterItemUpdated(ClusterItem, Marker)} will be called and
     * {@link #onBeforeClusterItemRendered(ClusterItem, Marker.Options)} will not be called.
     *
     * @param item item to be rendered
     * @param markerOptions the markerOptions representing the provided item
     */
    protected void onBeforeClusterItemRendered(
            @NonNull T item, @NonNull Marker.Options markerOptions) {
        if (item.getTitle() != null && item.getSnippet() != null) {
            markerOptions.title(item.getTitle());
            markerOptions.snippet(item.getSnippet());
        } else if (item.getTitle() != null) {
            markerOptions.title(item.getTitle());
        } else if (item.getSnippet() != null) {
            markerOptions.title(item.getSnippet());
        }
    }

    /**
     * Called when a cached marker for a {@link ClusterItem} already exists on the map so the marker
     * may be updated to the latest item values. Default implementation updates the title and
     * snippet of the marker if they have changed and refreshes the info window of the marker if it
     * is open. Note that the contents of the item may not have changed since the cached marker was
     * created - implementations of this method are responsible for checking if something changed
     * (if that matters to the implementation).
     * <p/>
     * The first time {@link ClusterManager#cluster()} is invoked on a set of items
     * {@link #onBeforeClusterItemRendered(ClusterItem, Marker.Options)} will be called and
     * {@link #onClusterItemUpdated(ClusterItem, Marker)} will not be called.
     * If an item is removed and re-added (or updated) and {@link ClusterManager#cluster()} is
     * invoked again, then {@link #onClusterItemUpdated(ClusterItem, Marker)} will be called and
     * {@link #onBeforeClusterItemRendered(ClusterItem, Marker.Options)} will not be called.
     *
     * @param item item being updated
     * @param marker cached marker that contains a potentially previous state of the item.
     */
    protected void onClusterItemUpdated(@NonNull T item, @NonNull Marker marker) {
        boolean changed = false;
        // Update marker text if the item text changed - same logic as adding marker in
        // CreateMarkerTask.perform()
        if (item.getTitle() != null && item.getSnippet() != null) {
            if (!item.getTitle().equals(marker.getTitle())) {
                marker.setTitle(item.getTitle());
                changed = true;
            }
            if (!item.getSnippet().equals(marker.getSnippet())) {
                marker.setSnippet(item.getSnippet());
                changed = true;
            }
        } else if (item.getSnippet() != null && !item.getSnippet().equals(marker.getTitle())) {
            marker.setTitle(item.getSnippet());
            changed = true;
        } else if (item.getTitle() != null && !item.getTitle().equals(marker.getTitle())) {
            marker.setTitle(item.getTitle());
            changed = true;
        }
        // Update marker position if the item changed position
        if (!marker.getPosition().equals(item.getPosition())) {
            marker.setPosition(item.getPosition());
            changed = true;
        }
        if (changed && marker.isInfoWindowShown()) {
            // Force a refresh of marker info window contents
            marker.showInfoWindow();
        }
    }

    /**
     * Called before the marker for a {@link Cluster} is added to the map.
     * The default implementation draws a circle with a rough count of the number of items.
     *
     * The first time {@link ClusterManager#cluster()} is invoked on a set of items
     * {@link #onBeforeClusterRendered(Cluster, Marker.Options)} will be called and
     * {@link #onClusterUpdated(Cluster, Marker)} will not be called. If an item is removed and
     * re-added (or updated) and {@link ClusterManager#cluster()} is invoked
     * again, then {@link #onClusterUpdated(Cluster, Marker)} will be called and
     * {@link #onBeforeClusterRendered(Cluster, Marker.Options)} will not be called.
     *
     * @param cluster cluster to be rendered
     * @param markerOptions markerOptions representing the provided cluster
     */
    protected void onBeforeClusterRendered(
            @NonNull Cluster<T> cluster, @NonNull Marker.Options markerOptions) {
        // TODO: consider adding anchor(.5, .5) (Individual markers will overlap more often)
        markerOptions.icon(getDescriptorForCluster(cluster));
    }

    /**
     * Gets a BitmapDescriptor for the given cluster that contains a rough count of the number of
     * items. Used to set the cluster marker icon in the default implementations of
     * {@link #onBeforeClusterRendered(Cluster, Marker.Options)} and
     * {@link #onClusterUpdated(Cluster, Marker)}.
     *
     * @param cluster cluster to get BitmapDescriptor for
     * @return a BitmapDescriptor for the marker icon for the given cluster that contains a rough
     * count of the number of items.
     */
    @NonNull
    protected BitmapDescriptor getDescriptorForCluster(@NonNull Cluster<T> cluster) {
        int bucket = getBucket(cluster);
        BitmapDescriptor descriptor = mIcons.get(bucket);
        if (descriptor == null) {
            mColoredCircleBackground.getPaint().setColor(getColor(bucket));
            descriptor = MapKit.getBitmapDescriptorFactory()
                    .fromBitmap(mIconGenerator.makeIcon(getClusterText(bucket)));
            mIcons.put(bucket, descriptor);
        }
        return descriptor;
    }

    /**
     * Called after the marker for a Cluster has been added to the map.
     *
     * @param cluster the cluster that was just added to the map
     * @param marker the marker representing the cluster that was just added to the map
     */
    protected void onClusterRendered(@NonNull Cluster<T> cluster, @NonNull Marker marker) {}

    /**
     * Called when a cached marker for a {@link Cluster} already exists on the map so the marker may
     * be updated to the latest cluster values. Default implementation updated the icon with a
     * circle with a rough count of the number of items. Note that the contents of the cluster may
     * not have changed since the cached marker was created - implementations of this method are
     * responsible for checking if something changed (if that matters to the implementation).
     *
     * The first time {@link ClusterManager#cluster()} is invoked on a set of items
     * {@link #onBeforeClusterRendered(Cluster, Marker.Options)} will be called and
     * {@link #onClusterUpdated(Cluster, Marker)} will not be called. If an item is removed and
     * re-added (or updated) and {@link ClusterManager#cluster()} is invoked
     * again, then {@link #onClusterUpdated(Cluster, Marker)} will be called and
     * {@link #onBeforeClusterRendered(Cluster, Marker.Options)} will not be called.
     *
     * @param cluster cluster being updated
     * @param marker cached marker that contains a potentially previous state of the cluster
     */
    protected void onClusterUpdated(@NonNull Cluster<T> cluster, @NonNull Marker marker) {
        // TODO: consider adding anchor(.5, .5) (Individual markers will overlap more often)
        marker.setIcon(getDescriptorForCluster(cluster));
    }

    /**
     * Called after the marker for a {@link ClusterItem} has been added to the map.
     *
     * @param clusterItem the item that was just added to the map
     * @param marker the marker representing the item that was just added to the map
     */
    protected void onClusterItemRendered(@NonNull T clusterItem, @NonNull Marker marker) {}

    /**
     * Get the marker from a {@link ClusterItem}.
     *
     * @param clusterItem {@link ClusterItem} which you will obtain its marker
     * @return a marker from a {@link ClusterItem} or null if it does not exists
     */
    public Marker getMarker(T clusterItem) {
        return mMarkerCache.get(clusterItem);
    }

    /**
     * Get the {@link ClusterItem} from a marker.
     *
     * @param marker which you will obtain its ClusterItem
     * @return a ClusterItem from a marker or null if it does not exists
     */
    public T getClusterItem(Marker marker) {
        return mMarkerCache.get(marker);
    }

    /**
     * Get the marker from a {@link Cluster}.
     *
     * @param cluster which you will obtain its marker
     * @return a marker from a cluster or null if it does not exists
     */
    public Marker getMarker(Cluster<T> cluster) {
        return mClusterMarkerCache.get(cluster);
    }

    /**
     * Get the {@link Cluster} from a marker
     *
     * @param marker which you will obtain its Cluster
     * @return a Cluster from a marker or null if it does not exists
     */
    public Cluster<T> getCluster(Marker marker) {
        return mClusterMarkerCache.get(marker);
    }

    /**
     * Creates markerWithPosition(s) for a particular cluster, animating it if necessary.
     */
    private class CreateMarkerTask {
        private final Cluster<T> mCluster;
        private final Set<MarkerWithPosition> mNewMarkers;
        private final LatLng mAnimateFrom;

        /**
         * @param cluster      the cluster to render.
         * @param markersAdded a collection of markers to append any created markers.
         * @param animateFrom  the location to animate the markerWithPosition from, or null if no
         *                     animation is required.
         */
        public CreateMarkerTask(
                Cluster<T> cluster, Set<MarkerWithPosition> markersAdded, LatLng animateFrom) {
            mCluster = cluster;
            mNewMarkers = markersAdded;
            mAnimateFrom = animateFrom;
        }

        private void perform(MarkerModifier markerModifier) {
            // Don't show small clusters. Render the markers inside, instead.
            if (!shouldRenderAsCluster(mCluster)) {
                for (T item : mCluster.getItems()) {
                    Marker marker = mMarkerCache.get(item);
                    MarkerWithPosition markerWithPosition;
                    if (marker == null) {
                        Marker.Options markerOptions = MapKit.newMarkerOptions();
                        if (mAnimateFrom != null) {
                            markerOptions.position(mAnimateFrom);
                        } else {
                            markerOptions.position(item.getPosition());
                        }
                        onBeforeClusterItemRendered(item, markerOptions);
                        marker = mClusterManager.getMarkerCollection().addMarker(markerOptions);
                        markerWithPosition = new MarkerWithPosition(marker);
                        mMarkerCache.put(item, marker);
                        if (mAnimateFrom != null) {
                            markerModifier.animate(
                                    markerWithPosition, mAnimateFrom, item.getPosition());
                        }
                    } else {
                        markerWithPosition = new MarkerWithPosition(marker);
                        onClusterItemUpdated(item, marker);
                    }
                    onClusterItemRendered(item, marker);
                    mNewMarkers.add(markerWithPosition);
                }
                return;
            }

            Marker marker = mClusterMarkerCache.get(mCluster);
            MarkerWithPosition markerWithPosition;
            if (marker == null) {
                Marker.Options markerOptions = MapKit.newMarkerOptions().
                        position(mAnimateFrom == null ? mCluster.getPosition() : mAnimateFrom);
                onBeforeClusterRendered(mCluster, markerOptions);
                marker = mClusterManager.getClusterMarkerCollection().addMarker(markerOptions);
                mClusterMarkerCache.put(mCluster, marker);
                markerWithPosition = new MarkerWithPosition(marker);
                if (mAnimateFrom != null) {
                    markerModifier.animate(markerWithPosition, mAnimateFrom, mCluster.getPosition());
                }
            } else {
                markerWithPosition = new MarkerWithPosition(marker);
                onClusterUpdated(mCluster, marker);
            }
            onClusterRendered(mCluster, marker);
            mNewMarkers.add(markerWithPosition);
        }
    }

    /**
     * A Marker and its position. {@link Marker#getPosition()} must be called from the UI thread,
     * so this object allows lookup from other threads.
     */
    private static class MarkerWithPosition {
        private final Marker mMarker;
        private LatLng mPosition;

        private MarkerWithPosition(Marker marker) {
            mMarker = marker;
            mPosition = marker.getPosition();
        }

        @Override
        public boolean equals(Object other) {
            if (other instanceof MarkerWithPosition) {
                return mMarker.equals(((MarkerWithPosition) other).mMarker);
            }
            return false;
        }

        @Override
        public int hashCode() {
            return mMarker.hashCode();
        }
    }

    private static final TimeInterpolator ANIMATION_INTERP = new DecelerateInterpolator();

    /**
     * Animates a markerWithPosition from one position to another.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
    private class AnimationTask extends AnimatorListenerAdapter implements
            ValueAnimator.AnimatorUpdateListener {
        private final MarkerWithPosition mMarkerWithPosition;
        private final Marker mMarker;
        private final LatLng mFrom;
        private final LatLng mTo;
        private boolean mRemoveOnComplete;
        private MarkerManager mMarkerManager;

        private AnimationTask(MarkerWithPosition markerWithPosition, LatLng from, LatLng to) {
            mMarkerWithPosition = markerWithPosition;
            mMarker = markerWithPosition.mMarker;
            mFrom = from;
            mTo = to;
        }

        public void perform() {
            ValueAnimator valueAnimator = ValueAnimator.ofFloat(0.0f, 1.0f);
            valueAnimator.setInterpolator(ANIMATION_INTERP);
            valueAnimator.addUpdateListener(this);
            valueAnimator.addListener(this);
            valueAnimator.start();
        }

        @Override
        public void onAnimationEnd(Animator animation) {
            if (mRemoveOnComplete) {
                mMarkerCache.remove(mMarker);
                mClusterMarkerCache.remove(mMarker);
                mMarkerManager.remove(mMarker);
            }
            mMarkerWithPosition.mPosition = mTo;
        }

        public void removeOnAnimationComplete(MarkerManager markerManager) {
            mMarkerManager = markerManager;
            mRemoveOnComplete = true;
        }

        @Override
        public void onAnimationUpdate(ValueAnimator valueAnimator) {
            float fraction = valueAnimator.getAnimatedFraction();
            double lat = (mTo.getLatitude() - mFrom.getLatitude()) * fraction + mFrom.getLatitude();
            double lngDelta = mTo.getLongitude() - mFrom.getLongitude();

            // Take the shortest path across the 180th meridian.
            if (Math.abs(lngDelta) > 180) {
                lngDelta -= Math.signum(lngDelta) * 360;
            }
            double lng = lngDelta * fraction + mFrom.getLongitude();
            LatLng position = MapKit.newLatLng(lat, lng);
            mMarker.setPosition(position);
        }
    }
}
