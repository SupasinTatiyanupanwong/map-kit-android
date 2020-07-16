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

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import me.tatiyanupanwong.supasin.android.libraries.kits.maps.MapFragment;
import me.tatiyanupanwong.supasin.android.libraries.kits.maps.MapKit;
import me.tatiyanupanwong.supasin.android.libraries.kits.maps.model.CameraPosition;
import me.tatiyanupanwong.supasin.android.libraries.kits.maps.model.CameraUpdate;
import me.tatiyanupanwong.supasin.android.libraries.kits.maps.model.LatLng;
import me.tatiyanupanwong.supasin.android.libraries.kits.maps.model.MapClient;
import me.tatiyanupanwong.supasin.android.libraries.kits.maps.model.Polyline;

/**
 * This shows how to change the camera position for the map.
 */
public class CameraDemoActivity extends AppCompatActivity implements
        MapClient.OnCameraIdleListener,
        MapClient.OnCameraMoveCanceledListener,
        MapClient.OnCameraMoveStartedListener,
        MapClient.OnCameraMoveListener,
        MapClient.Factory.OnMapReadyCallback {

    private static final String TAG = CameraDemoActivity.class.getName();

    /**
     * The amount by which to scroll the camera. Note that this amount is in raw pixels, not dp
     * (density-independent pixels).
     */
    private static final int SCROLL_BY_PX = 100;

    private static final CameraPosition BONDI =
            MapKit.getFactory().newCameraPositionBuilder()
                    .target(MapKit.getFactory().newLatLng(-33.891614, 151.276417))
                    .zoom(15.5f)
                    .bearing(300)
                    .tilt(50)
                    .build();

    private static final CameraPosition SYDNEY =
            MapKit.getFactory().newCameraPositionBuilder()
                    .target(MapKit.getFactory().newLatLng(-33.87365, 151.20689))
                    .zoom(15.5f)
                    .bearing(0)
                    .tilt(25)
                    .build();

    private MapClient mMap;

    private CompoundButton mAnimateToggle;
    private CompoundButton mCustomDurationToggle;
    private SeekBar mCustomDurationBar;
    private Polyline.Options mCurrPolylineOptions;
    private boolean mIsCanceled = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camera_demo);

        mAnimateToggle = findViewById(R.id.animate);
        mCustomDurationToggle = findViewById(R.id.duration_toggle);
        mCustomDurationBar = findViewById(R.id.duration_bar);

        updateEnabledState();

        MapFragment mapFragment =
                (MapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateEnabledState();
    }

    @Override
    public void onMapReady(@NonNull MapClient map) {
        mMap = map;

        mMap.setOnCameraIdleListener(this);
        mMap.setOnCameraMoveStartedListener(this);
        mMap.setOnCameraMoveListener(this);
        mMap.setOnCameraMoveCanceledListener(this);

        // We will provide our own zoom controls.
        mMap.getUiSettings().setZoomControlsEnabled(false);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);

        // Show Sydney
        mMap.moveCamera(
                MapKit.getFactory().getCameraUpdateFactory()
                        .newLatLngZoom(MapKit.getFactory().newLatLng(-33.87365, 151.20689), 10));
    }

    /**
     * When the map is not ready the CameraUpdateFactory cannot be used. This should be called on
     * all entry points that call methods on the Google Maps API.
     */
    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    private boolean checkReady() {
        if (mMap == null) {
            Toast.makeText(this, R.string.map_not_ready, Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    /**
     * Called when the Go To Bondi button is clicked.
     */
    public void onGoToBondi(View view) {
        if (!checkReady()) {
            return;
        }

        changeCamera(MapKit.getFactory().getCameraUpdateFactory().newCameraPosition(BONDI));
    }

    /**
     * Called when the Animate To Sydney button is clicked.
     */
    public void onGoToSydney(View view) {
        if (!checkReady()) {
            return;
        }

        changeCamera(
                MapKit.getFactory().getCameraUpdateFactory().newCameraPosition(SYDNEY),
                new MapClient.CancelableCallback() {
                    @Override
                    public void onFinish() {
                        Toast.makeText(
                                getBaseContext(), "Animation to Sydney complete", Toast.LENGTH_SHORT
                        ).show();
                    }

                    @Override
                    public void onCancel() {
                        Toast.makeText(
                                getBaseContext(), "Animation to Sydney canceled", Toast.LENGTH_SHORT
                        ).show();
                    }
                });
    }

    /**
     * Called when the stop button is clicked.
     */
    public void onStopAnimation(View view) {
        if (!checkReady()) {
            return;
        }

        mMap.stopAnimation();
    }

    /**
     * Called when the zoom in button (the one with the +) is clicked.
     */
    public void onZoomIn(View view) {
        if (!checkReady()) {
            return;
        }

        changeCamera(MapKit.getFactory().getCameraUpdateFactory().zoomIn());
    }

    /**
     * Called when the zoom out button (the one with the -) is clicked.
     */
    public void onZoomOut(View view) {
        if (!checkReady()) {
            return;
        }

        changeCamera(MapKit.getFactory().getCameraUpdateFactory().zoomOut());
    }

    /**
     * Called when the tilt more button (the one with the /) is clicked.
     */
    public void onTiltMore(View view) {
        if (!checkReady()) {
            return;
        }

        CameraPosition currentCameraPosition = mMap.getCameraPosition();
        float currentTilt = currentCameraPosition.getTilt();
        float newTilt = currentTilt + 10;

        newTilt = (newTilt > 90) ? 90 : newTilt;

        CameraPosition cameraPosition =
                MapKit.getFactory().newCameraPositionBuilder(currentCameraPosition)
                        .tilt(newTilt)
                        .build();

        changeCamera(
                MapKit.getFactory().getCameraUpdateFactory().newCameraPosition(cameraPosition));
    }

    /**
     * Called when the tilt less button (the one with the \) is clicked.
     */
    public void onTiltLess(View view) {
        if (!checkReady()) {
            return;
        }

        CameraPosition currentCameraPosition = mMap.getCameraPosition();

        float currentTilt = currentCameraPosition.getTilt();

        float newTilt = currentTilt - 10;
        newTilt = (newTilt > 0) ? newTilt : 0;

        CameraPosition cameraPosition =
                MapKit.getFactory().newCameraPositionBuilder(currentCameraPosition)
                        .tilt(newTilt)
                        .build();

        changeCamera(
                MapKit.getFactory().getCameraUpdateFactory().newCameraPosition(cameraPosition));
    }

    /**
     * Called when the left arrow button is clicked. This causes the camera to move to the left
     */
    public void onScrollLeft(View view) {
        if (!checkReady()) {
            return;
        }

        changeCamera(MapKit.getFactory().getCameraUpdateFactory().scrollBy(-SCROLL_BY_PX, 0));
    }

    /**
     * Called when the right arrow button is clicked. This causes the camera to move to the right.
     */
    public void onScrollRight(View view) {
        if (!checkReady()) {
            return;
        }

        changeCamera(MapKit.getFactory().getCameraUpdateFactory().scrollBy(SCROLL_BY_PX, 0));
    }

    /**
     * Called when the up arrow button is clicked. The causes the camera to move up.
     */
    public void onScrollUp(View view) {
        if (!checkReady()) {
            return;
        }

        changeCamera(MapKit.getFactory().getCameraUpdateFactory().scrollBy(0, -SCROLL_BY_PX));
    }

    /**
     * Called when the down arrow button is clicked. This causes the camera to move down.
     */
    public void onScrollDown(View view) {
        if (!checkReady()) {
            return;
        }

        changeCamera(MapKit.getFactory().getCameraUpdateFactory().scrollBy(0, SCROLL_BY_PX));
    }

    /**
     * Called when the animate button is toggled
     */
    public void onToggleAnimate(View view) {
        updateEnabledState();
    }

    /**
     * Called when the custom duration checkbox is toggled
     */
    public void onToggleCustomDuration(View view) {
        updateEnabledState();
    }

    /**
     * Update the enabled state of the custom duration controls.
     */
    private void updateEnabledState() {
        mCustomDurationToggle.setEnabled(mAnimateToggle.isChecked());
        mCustomDurationBar
                .setEnabled(mAnimateToggle.isChecked() && mCustomDurationToggle.isChecked());
    }

    private void changeCamera(CameraUpdate update) {
        changeCamera(update, null);
    }

    /**
     * Change the camera position by moving or animating the camera depending on the state of the
     * animate toggle button.
     */
    private void changeCamera(CameraUpdate update, MapClient.CancelableCallback callback) {
        if (mAnimateToggle.isChecked()) {
            if (mCustomDurationToggle.isChecked()) {
                int duration = mCustomDurationBar.getProgress();
                // The duration must be strictly positive so we make it at least 1.
                mMap.animateCamera(update, Math.max(duration, 1), callback);
            } else {
                mMap.animateCamera(update, callback);
            }
        } else {
            mMap.moveCamera(update);
        }
    }

    @Override
    public void onCameraMoveStarted(int reason) {
        if (!mIsCanceled) {
            mMap.clear();
        }

        String reasonText = "UNKNOWN_REASON";
        mCurrPolylineOptions = MapKit.getFactory().newPolylineOptions().width(5);
        switch (reason) {
            case MapClient.OnCameraMoveStartedListener.REASON_GESTURE:
                mCurrPolylineOptions.color(Color.BLUE);
                reasonText = "GESTURE";
                break;
            case MapClient.OnCameraMoveStartedListener.REASON_API_ANIMATION:
                mCurrPolylineOptions.color(Color.RED);
                reasonText = "API_ANIMATION";
                break;
            case MapClient.OnCameraMoveStartedListener.REASON_DEVELOPER_ANIMATION:
                mCurrPolylineOptions.color(Color.GREEN);
                reasonText = "DEVELOPER_ANIMATION";
                break;
        }
        Log.i(TAG, "onCameraMoveStarted(" + reasonText + ")");
        addCameraTargetToPath();
    }

    @Override
    public void onCameraMove() {
        // When the camera is moving, add its target to the current path we'll draw on the map.
        if (mCurrPolylineOptions != null) {
            addCameraTargetToPath();
        }
        Log.i(TAG, "onCameraMove");
    }

    @Override
    public void onCameraMoveCanceled() {
        // When the camera stops moving, add its target to the current path, and draw it on the map.
        if (mCurrPolylineOptions != null) {
            addCameraTargetToPath();
            mMap.addPolyline(mCurrPolylineOptions);
        }
        mIsCanceled = true;  // Set to clear the map when dragging starts again.
        mCurrPolylineOptions = null;
        Log.i(TAG, "onCameraMoveCancelled");
    }

    @Override
    public void onCameraIdle() {
        if (mCurrPolylineOptions != null) {
            addCameraTargetToPath();
            mMap.addPolyline(mCurrPolylineOptions);
        }
        mCurrPolylineOptions = null;
        mIsCanceled = false;  // Set to *not* clear the map when dragging starts again.
        Log.i(TAG, "onCameraIdle");
    }

    private void addCameraTargetToPath() {
        LatLng target = mMap.getCameraPosition().getTarget();
        mCurrPolylineOptions.add(target);
    }

}
