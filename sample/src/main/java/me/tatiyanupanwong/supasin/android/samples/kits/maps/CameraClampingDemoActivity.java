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

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import me.tatiyanupanwong.supasin.android.libraries.kits.maps.MapFragment;
import me.tatiyanupanwong.supasin.android.libraries.kits.maps.MapKit;
import me.tatiyanupanwong.supasin.android.libraries.kits.maps.model.CameraPosition;
import me.tatiyanupanwong.supasin.android.libraries.kits.maps.model.LatLngBounds;
import me.tatiyanupanwong.supasin.android.libraries.kits.maps.model.MapClient;

/**
 * This shows how to constrain the camera to specific boundaries and zoom levels.
 */
public class CameraClampingDemoActivity extends AppCompatActivity implements
        MapKit.OnMapReadyCallback,
        MapClient.OnCameraIdleListener {

    private static final float ZOOM_DELTA = 2.0f;
    private static final float DEFAULT_MIN_ZOOM = 2.0f;
    private static final float DEFAULT_MAX_ZOOM = 22.0f;

    private static final LatLngBounds ADELAIDE =
            MapKit.newLatLngBounds(
                    MapKit.newLatLng(-35.0, 138.58),
                    MapKit.newLatLng(-34.9, 138.61));
    private static final CameraPosition ADELAIDE_CAMERA =
            MapKit.newCameraPositionBuilder()
                    .target(MapKit.newLatLng(-34.92873, 138.59995))
                    .zoom(20.0f)
                    .bearing(0)
                    .tilt(0)
                    .build();

    private static final LatLngBounds PACIFIC =
            MapKit.newLatLngBounds(
                    MapKit.newLatLng(-15.0, 165.0),
                    MapKit.newLatLng(15.0, -165.0));
    private static final CameraPosition PACIFIC_CAMERA =
            MapKit.newCameraPositionBuilder()
                    .target(MapKit.newLatLng(0, -180))
                    .zoom(4.0f)
                    .bearing(0)
                    .tilt(0)
                    .build();

    private MapClient mMap;

    /**
     * Internal min zoom level that can be toggled via the demo.
     */
    private float mMinZoom;

    /**
     * Internal max zoom level that can be toggled via the demo.
     */
    private float mMaxZoom;

    private TextView mCameraTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.camera_clamping_demo);

        resetMinMaxZoom();

        mCameraTextView = findViewById(R.id.camera_text);

        MapFragment mapFragment =
            (MapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(@NonNull MapClient map) {
        mMap = map;
        map.setOnCameraIdleListener(this);
    }

    @Override
    public void onCameraIdle() {
        mCameraTextView.setText(mMap.getCameraPosition().toString());
    }

    /**
     * Before the map is ready many calls will fail.
     * This should be called on all entry points that call methods on the MapClient API.
     */
    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    private boolean checkReady() {
        if (mMap == null) {
            Toast.makeText(this, R.string.map_not_ready, Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void toast(String msg) {
        Toast.makeText(getBaseContext(), msg, Toast.LENGTH_SHORT).show();
    }

    private void resetMinMaxZoom() {
        mMinZoom = DEFAULT_MIN_ZOOM;
        mMaxZoom = DEFAULT_MAX_ZOOM;
    }

    /**
     * Click handler for clamping to Adelaide button.
     */
    public void onClampToAdelaide(View view) {
        if (!checkReady()) {
            return;
        }
        mMap.setLatLngBoundsForCameraTarget(ADELAIDE);
        mMap.animateCamera(
                MapKit.getCameraUpdateFactory().newCameraPosition(ADELAIDE_CAMERA));
    }

    /**
     * Click handler for clamping to Pacific button.
     */
    public void onClampToPacific(View view) {
        if (!checkReady()) {
            return;
        }
        mMap.setLatLngBoundsForCameraTarget(PACIFIC);
        mMap.animateCamera(
                MapKit.getCameraUpdateFactory().newCameraPosition(PACIFIC_CAMERA));
    }

    public void onLatLngClampReset(View view) {
        if (!checkReady()) {
            return;
        }
        // Setting bounds to null removes any previously set bounds.
        mMap.setLatLngBoundsForCameraTarget(null);
        toast("LatLngBounds clamp reset.");
    }

    public void onSetMinZoomClamp(View view) {
        if (!checkReady()) {
            return;
        }
        mMinZoom += ZOOM_DELTA;
        // Constrains the minimum zoom level.
        mMap.setMinZoomPreference(mMinZoom);
        toast("Min zoom preference set to: " + mMinZoom);
    }

    public void onSetMaxZoomClamp(View view) {
        if (!checkReady()) {
            return;
        }
        mMaxZoom -= ZOOM_DELTA;
        // Constrains the maximum zoom level.
        mMap.setMaxZoomPreference(mMaxZoom);
        toast("Max zoom preference set to: " + mMaxZoom);
    }

    public void onMinMaxZoomClampReset(View view) {
        if (!checkReady()) {
            return;
        }
        resetMinMaxZoom();
        mMap.resetMinMaxZoomPreference();
        toast("Min/Max zoom preferences reset.");
    }

}
