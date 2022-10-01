/*
 * Copyright 2020 Supasin Tatiyanupanwong
 * Copyright 2012 The Android Open Source Project
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

package dev.supasintatiyanupanwong.samples.android.kits.maps;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import dev.supasintatiyanupanwong.libraries.android.kits.maps.MapFragment;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.MapKit;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.IndoorBuilding;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.IndoorLevel;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.MapClient;

/**
 * A demo activity showing how to use indoor.
 */
public class IndoorDemoActivity extends AppCompatActivity implements MapKit.OnMapReadyCallback {

    private MapClient mMap;

    private boolean mShowLevelPicker = true;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.indoor_demo);

        MapFragment mapFragment =
                (MapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
    }

    @Override public void onMapReady(@NonNull MapClient map) {
        mMap = map;
        mMap.moveCamera(MapKit.getCameraUpdateFactory()
                .newLatLngZoom(MapKit.newLatLng(37.614631, -122.385153), 18));
    }

    /**
     * Called when the toggle level picker button is clicked.
     */
    public void onToggleLevelPicker(View view) {
        mShowLevelPicker = !mShowLevelPicker;
        mMap.getUiSettings().setIndoorLevelPickerEnabled(mShowLevelPicker);
    }

    /**
     * Called when the focused building info is clicked.
     */
    public void onFocusedBuildingInfo(View view) {
        IndoorBuilding building = mMap.getFocusedBuilding();
        if (building != null) {
            StringBuilder s = new StringBuilder();
            for (IndoorLevel level : building.getLevels()) {
                s.append(level.getName()).append(" ");
            }
            if (building.isUnderground()) {
                s.append("is underground");
            }
            setText(s.toString());
        } else {
            setText("No visible building");
        }
    }

    /**
     * Called when the focused level info is clicked.
     */
    public void onVisibleLevelInfo(View view) {
        IndoorBuilding building = mMap.getFocusedBuilding();
        if (building != null) {
            IndoorLevel level =
                    building.getLevels().get(building.getActiveLevelIndex());
            if (level != null) {
                setText(level.getName());
            } else {
                setText("No visible level");
            }
        } else {
            setText("No visible building");
        }
    }

    /**
     * Called when the activate higher level is clicked.
     */
    public void onHigherLevel(View view) {
        IndoorBuilding building = mMap.getFocusedBuilding();
        if (building != null) {
            List<IndoorLevel> levels = building.getLevels();
            if (!levels.isEmpty()) {
                int currentLevel = building.getActiveLevelIndex();
                // The levels are in 'display order' from top to bottom,
                // i.e. higher levels in the building are lower numbered in the array.
                int newLevel = currentLevel - 1;
                if (newLevel == -1) {
                    newLevel = levels.size() - 1;
                }
                IndoorLevel level = levels.get(newLevel);
                setText("Activating level " + level.getName());
                level.activate();
            } else {
                setText("No levels in building");
            }
        } else {
            setText("No visible building");
        }
    }

    private void setText(String message) {
        TextView text = findViewById(R.id.top_text);
        text.setText(message);
    }

}
