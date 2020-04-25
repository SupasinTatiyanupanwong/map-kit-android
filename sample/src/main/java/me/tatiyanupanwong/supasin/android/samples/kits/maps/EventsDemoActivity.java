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
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import me.tatiyanupanwong.supasin.android.libraries.kits.maps.MapFragment;
import me.tatiyanupanwong.supasin.android.libraries.kits.maps.model.LatLng;
import me.tatiyanupanwong.supasin.android.libraries.kits.maps.model.Map.Factory.OnMapReadyCallback;
import me.tatiyanupanwong.supasin.android.libraries.kits.maps.model.Map;
import me.tatiyanupanwong.supasin.android.libraries.kits.maps.model.Map.OnCameraIdleListener;
import me.tatiyanupanwong.supasin.android.libraries.kits.maps.model.Map.OnMapClickListener;
import me.tatiyanupanwong.supasin.android.libraries.kits.maps.model.Map.OnMapLongClickListener;

/**
 * This shows how to listen to some {@link Map} events.
 */
public class EventsDemoActivity extends AppCompatActivity implements
        OnMapClickListener,
        OnMapLongClickListener,
        OnCameraIdleListener,
        OnMapReadyCallback {

    private TextView mTapTextView;
    private TextView mCameraTextView;
    private Map mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.events_demo);

        mTapTextView = findViewById(R.id.tap_text);
        mCameraTextView = findViewById(R.id.camera_text);

        MapFragment mapFragment =
                (MapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(@NonNull Map map) {
        mMap = map;
        mMap.setOnMapClickListener(this);
        mMap.setOnMapLongClickListener(this);
        mMap.setOnCameraIdleListener(this);
    }

    @Override
    public void onMapClick(@NonNull LatLng point) {
        mTapTextView.setText("tapped, point=" + point);
    }

    @Override
    public void onMapLongClick(@NonNull LatLng point) {
        mTapTextView.setText("long pressed, point=" + point);
    }

    @Override
    public void onCameraIdle() {
        mCameraTextView.setText(mMap.getCameraPosition().toString());
    }

}
