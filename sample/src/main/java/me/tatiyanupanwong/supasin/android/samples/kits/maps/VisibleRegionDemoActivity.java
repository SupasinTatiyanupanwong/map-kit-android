/*
 * Copyright (C) 2020 Supasin Tatiyanupanwong
 * Copyright (C) 2012 The Android Open Source Project
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
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.view.animation.Interpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import me.tatiyanupanwong.supasin.android.libraries.kits.maps.MapFragment;
import me.tatiyanupanwong.supasin.android.libraries.kits.maps.MapKit;
import me.tatiyanupanwong.supasin.android.libraries.kits.maps.model.LatLng;
import me.tatiyanupanwong.supasin.android.libraries.kits.maps.model.LatLngBounds;
import me.tatiyanupanwong.supasin.android.libraries.kits.maps.model.MapClient;

/**
 * This shows how to use setPadding to allow overlays that obscure part of the map without
 * obscuring the map UI or copyright notices.
 */
public class VisibleRegionDemoActivity extends AppCompatActivity implements
        OnMapAndViewReadyListener.OnGlobalLayoutAndMapReadyListener {

    /**
     * Note that this may be null if the Google Play services APK is not available.
     */
    private MapClient mMap;

    private static final LatLng SOH = MapKit.getFactory().newLatLng(-33.85704, 151.21522);

    private static final LatLng SFO = MapKit.getFactory().newLatLng(37.614631, -122.385153);

    private static final LatLngBounds AUS =
            MapKit.getFactory().newLatLngBounds(
                    MapKit.getFactory().newLatLng(-44, 113),
                    MapKit.getFactory().newLatLng(-10, 154));

    private TextView mMessageView;

    /** Keep track of current values for padding, so we can animate from them. */
    private int currentLeft = 150;

    private int currentTop = 0;

    private int currentRight = 0;

    private int currentBottom = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.visible_region_demo);
        mMessageView = findViewById(R.id.message_text);

        MapFragment mapFragment =
                (MapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            new OnMapAndViewReadyListener(mapFragment, this);
        }
    }

    @Override
    public void onMapReady(MapClient map) {
        mMap = map;

        // Move to a place with indoor (SFO airport).
        mMap.setPadding(currentLeft, currentTop, currentRight, currentBottom);
        mMap.moveCamera(MapKit.getFactory().getCameraUpdateFactory().newLatLngZoom(SFO, 18));
        // Add a marker to the Opera House.
        mMap.addMarker(MapKit.getFactory().newMarkerOptions()
                .position(SOH)
                .title("Sydney Opera House"));
        // Add a camera idle listener.
        mMap.setOnCameraIdleListener(new MapClient.OnCameraIdleListener() {
            @Override
            public void onCameraIdle() {
                mMessageView.setText("CameraChangeListener: " + mMap.getCameraPosition());
            }
        });
    }

    /**
     * Checks if the map is ready (which depends on whether the Google Play services APK is
     * available. This should be called prior to calling any methods on GoogleMap.
     */
    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    private boolean checkReady() {
        if (mMap == null) {
            Toast.makeText(this, R.string.map_not_ready, Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public void moveToOperaHouse(View view) {
        if (!checkReady()) {
            return;
        }
        mMap.moveCamera(MapKit.getFactory().getCameraUpdateFactory().newLatLngZoom(SOH, 16));
    }

    public void moveToSFO(View view) {
        if (!checkReady()) {
            return;
        }
        mMap.moveCamera(MapKit.getFactory().getCameraUpdateFactory().newLatLngZoom(SFO, 18));
    }

    public void moveToAUS(View view) {
        if (!checkReady()) {
            return;
        }
        mMap.moveCamera(MapKit.getFactory().getCameraUpdateFactory().newLatLngBounds(AUS, 0));
    }

    public void setNoPadding(View view) {
        if (!checkReady()) {
            return;
        }
        animatePadding(findViewById(R.id.vr_panel).getMeasuredWidth(), 0, 0, 0);
    }

    public void setMorePadding(View view) {
        if (!checkReady()) {
            return;
        }
        View mapView = (getSupportFragmentManager().findFragmentById(R.id.map)).getView();
        int left = findViewById(R.id.vr_panel).getMeasuredWidth();
        int top = 0;
        int right = mapView.getWidth() / 3;
        int bottom = mapView.getHeight() / 4;
        animatePadding(left, top, right, bottom);
    }

    private void animatePadding(
            final int toLeft, final int toTop, final int toRight, final int toBottom) {

        final Handler handler = new Handler();
        final long start = SystemClock.uptimeMillis();
        final long duration = 1000;

        final Interpolator interpolator = new OvershootInterpolator();

        final int startLeft = currentLeft;
        final int startTop = currentTop;
        final int startRight = currentRight;
        final int startBottom = currentBottom;

        currentLeft = toLeft;
        currentTop = toTop;
        currentRight = toRight;
        currentBottom = toBottom;

        handler.post(new Runnable() {
            @Override
            public void run() {
                long elapsed = SystemClock.uptimeMillis() - start;
                float t = interpolator.getInterpolation((float) elapsed / duration);

                int left = (int) (startLeft + ((toLeft - startLeft) * t));
                int top = (int) (startTop + ((toTop - startTop) * t));
                int right = (int) (startRight + ((toRight - startRight) * t));
                int bottom = (int) (startBottom + ((toBottom - startBottom) * t));

                mMap.setPadding(left, top, right, bottom);

                if (elapsed < duration) {
                    // Post again 16ms later.
                    handler.postDelayed(this, 16);
                }
            }
        });
    }

}
