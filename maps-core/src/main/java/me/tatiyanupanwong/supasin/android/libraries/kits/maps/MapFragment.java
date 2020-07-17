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

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.UiThread;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import me.tatiyanupanwong.supasin.android.libraries.kits.maps.model.Map;
import me.tatiyanupanwong.supasin.android.libraries.kits.maps.model.MapClient;
import me.tatiyanupanwong.supasin.android.libraries.kits.maps.model.Marker;

/**
 * A Map component in an app which displays a map (with data obtained from the Maps service). When
 * focused, it will capture keypresses and touch gestures to move the map. It's a wrapper around a
 * view of a map to automatically handle the necessary life cycle needs. Being a fragment, this
 * component can be added to an activity's layout file simply with the XML below.
 *
 * <pre>
 * &lt;fragment
 *     class="me.tatiyanupanwong.supasin.android.libraries.kits.maps.MapFragment"
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

    @Nullable
    @Override
    public final View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        return inflater.inflate(MapsPlatform.get().getFragmentLayoutId(), container, false);
    }

    /**
     * Sets a callback object which will be triggered when the {@link Map} instance is ready
     * to be used.
     *
     * @param callback The callback object that will be triggered when the map is ready to be used.
     * @see #getMapAsync(MapKit.OnMapReadyCallback)
     * @deprecated As of 1.2.0, use {@link #getMapAsync(MapKit.OnMapReadyCallback)} instead.
     */
    @SuppressWarnings("deprecation")
    @Deprecated
    @UiThread
    public final void getMapAsync(final Map.Factory.OnMapReadyCallback callback) {
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
     * @see #getMapAsyncInternal(MapKit.OnMapReadyCallback)
     * @deprecated As of 1.2.0, use {@link #getMapAsyncInternal(MapKit.OnMapReadyCallback)} instead.
     */
    @SuppressWarnings("deprecation")
    @Deprecated
    private void getMapAsyncInternal(Map.Factory.OnMapReadyCallback callback) {
        Fragment fragment = getChildFragmentManager()
                .findFragmentById(MapsPlatform.get().getFragmentDelegateId());

        if (fragment == null) {
            throw new NullPointerException();
        }

        MapsPlatform.get().getFactory().getMapAsync(fragment, callback);
    }

    /**
     * @since 1.2.0
     */
    private void getMapAsyncInternal(MapKit.OnMapReadyCallback callback) {
        Fragment fragment = getChildFragmentManager()
                .findFragmentById(MapsPlatform.get().getFragmentDelegateId());

        if (fragment == null) {
            throw new NullPointerException();
        }

        MapsPlatform.get().getFactory().getMapAsync(fragment, callback);
    }

}
