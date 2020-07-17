/*
 * Copyright (C) 2020 Supasin Tatiyanupanwong
 * Copyright (C) 2016 The Android Open Source Project
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

package me.tatiyanupanwong.supasin.android.samples.kits.maps;

import android.annotation.SuppressLint;
import android.view.View;
import android.view.ViewTreeObserver;

import androidx.annotation.NonNull;

import me.tatiyanupanwong.supasin.android.libraries.kits.maps.MapFragment;
import me.tatiyanupanwong.supasin.android.libraries.kits.maps.model.Map;
import me.tatiyanupanwong.supasin.android.libraries.kits.maps.model.Map.Factory.OnMapReadyCallback;

/**
 * Helper class that will delay triggering the OnMapReady callback until both the Map and the
 * View having completed initialization. This is only necessary if a developer wishes to immediately
 * invoke any method on the Map that also requires the View to have finished layout
 * (ie. anything that needs to know the View's true size like snapshotting).
 */
class OnMapAndViewReadyListener implements
        ViewTreeObserver.OnGlobalLayoutListener,
        OnMapReadyCallback {

    /** A listener that needs to wait for both the Map and the View to be initialized. */
    interface OnGlobalLayoutAndMapReadyListener {
        void onMapReady(Map map);
    }

    private final MapFragment mMapFragment;
    private final View mMapView;
    private final OnGlobalLayoutAndMapReadyListener mCallback;

    private boolean mIsViewReady;
    private boolean mIsMapReady;
    private Map mMap;

    OnMapAndViewReadyListener(MapFragment mapFragment, OnGlobalLayoutAndMapReadyListener callback) {
        mMapFragment = mapFragment;
        mMapView = mapFragment.getView();
        mCallback = callback;
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

        // Note if the Map is already ready it will still fire the callback later.
        mMapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(@NonNull Map map) {
        mMap = map;
        mIsMapReady = true;
        fireCallbackIfReady();
    }

    @SuppressLint("NewApi")  // We check which build version we are using.
    @Override
    public void onGlobalLayout() {
        // Remove our listener.
        mMapView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
        mIsViewReady = true;
        fireCallbackIfReady();
    }

    private void fireCallbackIfReady() {
        if (mIsViewReady && mIsMapReady) {
            mCallback.onMapReady(mMap);
        }
    }

}
