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
import android.view.View;
import android.widget.CheckBox;
import android.widget.SeekBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import me.tatiyanupanwong.supasin.android.libraries.kits.maps.MapFragment;
import me.tatiyanupanwong.supasin.android.libraries.kits.maps.MapKit;
import me.tatiyanupanwong.supasin.android.libraries.kits.maps.model.BitmapDescriptor;
import me.tatiyanupanwong.supasin.android.libraries.kits.maps.model.GroundOverlay;
import me.tatiyanupanwong.supasin.android.libraries.kits.maps.model.LatLng;
import me.tatiyanupanwong.supasin.android.libraries.kits.maps.model.MapClient;

/**
 * This shows how to add a ground overlay to a map.
 */
public class GroundOverlayDemoActivity extends AppCompatActivity implements
        SeekBar.OnSeekBarChangeListener,
        MapClient.OnGroundOverlayClickListener,
        MapClient.Factory.OnMapReadyCallback {

    private static final int TRANSPARENCY_MAX = 100;

    private static final LatLng NEWARK = MapKit.getFactory().newLatLng(40.714086, -74.228697);

    private static final LatLng NEAR_NEWARK =
            MapKit.getFactory()
                    .newLatLng(NEWARK.getLatitude() - 0.001, NEWARK.getLongitude() - 0.025);

    private final List<BitmapDescriptor> mImages = new ArrayList<>();

    private GroundOverlay mGroundOverlay;

    private GroundOverlay mGroundOverlayRotated;

    private SeekBar mTransparencyBar;

    private int mCurrentEntry = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ground_overlay_demo);

        mTransparencyBar = findViewById(R.id.transparencySeekBar);
        mTransparencyBar.setMax(TRANSPARENCY_MAX);
        mTransparencyBar.setProgress(0);

        MapFragment mapFragment =
                (MapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(@NonNull MapClient map) {
        // Register a listener to respond to clicks on GroundOverlays.
        map.setOnGroundOverlayClickListener(this);

        map.moveCamera(MapKit.getFactory().getCameraUpdateFactory().newLatLngZoom(NEWARK, 11));

        mImages.clear();
        mImages.add(MapKit.getFactory().getBitmapDescriptorFactory()
                .fromResource(R.drawable.newark_nj_1922));
        mImages.add(MapKit.getFactory().getBitmapDescriptorFactory()
                .fromResource(R.drawable.newark_prudential_sunny));

        // Add a small, rotated overlay that is clickable by default
        // (set by the initial state of the checkbox.)
        mGroundOverlayRotated = map.addGroundOverlay(
                MapKit.getFactory().newGroundOverlayOptions()
                        .image(mImages.get(1))
                        .anchor(0, 1)
                        .position(NEAR_NEWARK, 4300f, 3025f)
                        .bearing(30)
                        .clickable(((CheckBox) findViewById(R.id.toggleClickability)).isChecked()));

        // Add a large overlay at Newark on top of the smaller overlay.
        mGroundOverlay = map.addGroundOverlay(
                MapKit.getFactory().newGroundOverlayOptions()
                        .image(mImages.get(mCurrentEntry))
                        .anchor(0, 1)
                        .position(NEWARK, 8600f, 6500f));

        mTransparencyBar.setOnSeekBarChangeListener(this);

        // Override the default content description on the view, for accessibility mode.
        // Ideally this string would be localised.
        map.setContentDescription("Map with ground overlay.");
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (mGroundOverlay != null) {
            mGroundOverlay.setTransparency((float) progress / (float) TRANSPARENCY_MAX);
        }
    }

    public void switchImage(View view) {
        mCurrentEntry = (mCurrentEntry + 1) % mImages.size();
        mGroundOverlay.setImage(mImages.get(mCurrentEntry));
    }

    /**
     * Toggles the visibility between 100% and 50% when a {@link GroundOverlay} is clicked.
     */
    @Override
    public void onGroundOverlayClick(@NonNull GroundOverlay groundOverlay) {
        // Toggle transparency value between 0.0f and 0.5f. Initial default value is 0.0f.
        mGroundOverlayRotated.setTransparency(0.5f - mGroundOverlayRotated.getTransparency());
    }

    /**
     * Toggles the clickability of the smaller, rotated overlay based on the state of the View that
     * triggered this call.
     * This callback is defined on the CheckBox in the layout for this Activity.
     */
    public void toggleClickability(View view) {
        if (mGroundOverlayRotated != null) {
            mGroundOverlayRotated.setClickable(((CheckBox) view).isChecked());
        }
    }

}
