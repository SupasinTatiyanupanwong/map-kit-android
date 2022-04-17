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

package dev.supasintatiyanupanwong.libraries.android.kits.maps;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.UiThread;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.MapClient;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.Marker;

/**
 * A Map component in an app which displays a map (with data obtained from the Maps service). When
 * focused, it will capture keypresses and touch gestures to move the map. It's a wrapper around a
 * view of a map to automatically handle the necessary life cycle needs. Being a fragment, this
 * component can be added to an activity's layout file simply with the XML below.
 *
 * <pre>
 * &lt;fragment
 *     class="dev.supasintatiyanupanwong.libraries.android.kits.maps.MapFragment"
 *     android:layout_width="match_parent"
 *     android:layout_height="match_parent" /&gt;
 * </pre>
 *
 * <p>A {@link MapClient} must be acquired using {@link #getMapAsync(MapKit.OnMapReadyCallback)}.
 * This class automatically initializes the maps system and the view.
 *
 * <p>Any objects obtained from the {@link MapClient} are associated with the view. It's important
 * to not hold on to objects (e.g. {@link Marker}) beyond the view's life. Otherwise it will cause
 * a memory leak as the view cannot be released.
 *
 * @since 1.0.0
 */
public class MapFragment extends Fragment {

    public static MapFragment newInstance() {
        return new MapFragment();
    }

    // Used to verify that subclasses call through to super.onCreateView().
    private boolean mOnCreateViewCalled = false;

    @CallSuper
    @Override
    public @NonNull View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        mOnCreateViewCalled = true;
        return inflater.inflate(MapKit.getBackend().getMapFragmentLayoutRes(), container, false);
    }

    @CallSuper
    @Override
    public void onStart() {
        // FM enforced super.onStart() to be called if overridden, checking our view first.
        ensureViewCreated();
        super.onStart();
    }

    /**
     * Sets a callback object which will be triggered when the {@link MapClient} instance is ready
     * to be used.
     *
     * @param callback The callback object that will be triggered when the map is ready to be used.
     * @since 1.2.0
     */
    @UiThread
    public final void getMapAsync(final MapKit.OnMapReadyCallback callback) {
        if (getView() != null) {
            getMapAsyncInternal(callback);
        } else {
            requireFragmentManager().registerFragmentLifecycleCallbacks(
                    new FragmentManager.FragmentLifecycleCallbacks() {
                        @Override
                        public void onFragmentViewCreated(
                                @NonNull FragmentManager fragmentManager,
                                @NonNull Fragment fragment,
                                @NonNull View view,
                                @Nullable Bundle savedInstanceState) {
                            fragmentManager.unregisterFragmentLifecycleCallbacks(this);
                            getMapAsyncInternal(callback);
                        }
                    }, false);
        }
    }

    /**
     * Sets a callback object which will be triggered when the map has undergone layout and the
     * {@link MapClient} instance is ready to be used.
     *
     * @param callback The callback object that will be triggered when the map has undergone layout
     * and ready to be used.
     * @since 2.0.0
     */
    public final void getMapAsync(final MapKit.OnMapAndViewReadyCallback callback) {
        getMapAsync(new OnMapAndViewReadyCallbackImpl(this, callback));
    }


    private void ensureViewCreated() {
        if (!mOnCreateViewCalled) {
            throw new IllegalStateException("MapFragment " + this
                    + " did not call through to super.onCreateView()");
        }
    }

    private void getMapAsyncInternal(MapKit.OnMapReadyCallback callback) {
        Fragment fragment = getChildFragmentManager()
                .findFragmentById(MapKit.getBackend().getMapFragmentIdRes());

        if (fragment == null) {
            throw new NullPointerException();
        }

        MapKit.getBackend().getMapAsync(fragment, callback);
    }


    private static class OnMapAndViewReadyCallbackImpl implements
            ViewTreeObserver.OnGlobalLayoutListener,
            MapKit.OnMapReadyCallback {
        private final MapFragment mMapFragment;
        private final View mMapView;
        private final MapKit.OnMapAndViewReadyCallback mDelegate;

        private boolean mIsViewReady;
        private boolean mIsMapReady;
        private MapClient mMap;

        public OnMapAndViewReadyCallbackImpl(
                @NonNull MapFragment mapFragment,
                @NonNull MapKit.OnMapAndViewReadyCallback delegate) {
            mMapFragment = mapFragment;
            mMapView = mapFragment.getView();
            mDelegate = delegate;
            mIsViewReady = false;
            mIsMapReady = false;
            mMap = null;

            registerListeners();
        }


        private void registerListeners() {
            // View layout.
            if ((mMapView.getWidth() != 0) && (mMapView.getHeight() != 0)) {
                // View has already completed layout.
                mIsViewReady = true;
            } else {
                // Map has not undergone layout, register a View observer.
                mMapView.getViewTreeObserver().addOnGlobalLayoutListener(this);
            }

            // Note if the MapClient is already ready it will still fire the callback later.
            mMapFragment.getMapAsync(this);
        }

        @Override
        public final void onMapReady(@NonNull MapClient map) {
            mMap = map;
            mIsMapReady = true;
            fireCallbackIfReady();
        }

        @Override
        public final void onGlobalLayout() {
            // Remove our listener.
            mMapView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            mIsViewReady = true;
            fireCallbackIfReady();
        }

        private void fireCallbackIfReady() {
            if (mIsViewReady && mIsMapReady) {
                mDelegate.onMapAndViewReady(mMap);
            }
        }
    }

}
