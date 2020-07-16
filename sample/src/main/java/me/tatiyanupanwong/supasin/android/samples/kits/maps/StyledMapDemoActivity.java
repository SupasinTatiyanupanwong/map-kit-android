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

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import me.tatiyanupanwong.supasin.android.libraries.kits.maps.MapFragment;
import me.tatiyanupanwong.supasin.android.libraries.kits.maps.MapKit;
import me.tatiyanupanwong.supasin.android.libraries.kits.maps.model.LatLng;
import me.tatiyanupanwong.supasin.android.libraries.kits.maps.model.MapClient;

/**
 * This shows how to style a map with JSON.
 */
public class StyledMapDemoActivity extends AppCompatActivity implements
        MapClient.Factory.OnMapReadyCallback {

    private MapClient mMap = null;

    private static final String TAG = StyledMapDemoActivity.class.getSimpleName();

    private static final String SELECTED_STYLE = "selected_style";

    // Stores the ID of the currently selected style, so that we can re-apply it when
    // the activity restores state, for example when the device changes orientation.
    private int mSelectedStyleId = R.string.style_label_default;

    // These are simply the string resource IDs for each of the style names. We use them
    // as identifiers when choosing which style to apply.
    private final int[] mStyleIds = {
            R.string.style_label_retro,
            R.string.style_label_night,
            R.string.style_label_grayscale,
            R.string.style_label_no_pois_no_transit,
            R.string.style_label_default,
    };

    private static final LatLng SYDNEY = MapKit.getFactory().newLatLng(-33.8688, 151.2093);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            mSelectedStyleId = savedInstanceState.getInt(SELECTED_STYLE);
        }
        setContentView(R.layout.styled_map_demo);

        MapFragment mapFragment =
                (MapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        // Store the selected map style, so we can assign it when the activity resumes.
        outState.putInt(SELECTED_STYLE, mSelectedStyleId);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onMapReady(@NonNull MapClient map) {
        mMap = map;
        mMap.moveCamera(MapKit.getFactory().getCameraUpdateFactory().newLatLngZoom(SYDNEY, 14));
        setSelectedStyle();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.styled_map, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_style_choose) {
            showStylesDialog();
        }
        return true;
    }

    /**
     * Shows a dialog listing the styles to choose from, and applies the selected
     * style when chosen.
     */
    private void showStylesDialog() {
        // mStyleIds stores each style's resource ID, and we extract the names here, rather
        // than using an XML array resource which AlertDialog.Builder.setItems() can also
        // accept. We do this since using an array resource would mean we would not have
        // constant values we can switch/case on, when choosing which style to apply.
        List<String> styleNames = new ArrayList<>();
        for (int style : mStyleIds) {
            styleNames.add(getString(style));
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.style_choose));
        builder.setItems(styleNames.toArray(new CharSequence[0]),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mSelectedStyleId = mStyleIds[which];
                        String msg = getString(R.string.style_set_to, getString(mSelectedStyleId));
                        Toast.makeText(getBaseContext(), msg, Toast.LENGTH_SHORT).show();
                        Log.d(TAG, msg);
                        setSelectedStyle();
                    }
                });
        builder.show();
    }

    /**
     * Creates a {@link MapClient.Style.Options} object, then sets it on the {@link MapClient}
     * instance, via the {@link MapClient#setMapStyle)} method.
     */
    private void setSelectedStyle() {
        MapClient.Style.Options style;
        switch (mSelectedStyleId) {
            case R.string.style_label_retro:
                // Sets the retro style via raw resource JSON.
                style = MapKit.getFactory().newMapStyleOptions(this, R.raw.mapstyle_retro);
                break;
            case R.string.style_label_night:
                // Sets the night style via raw resource JSON.
                style =  MapKit.getFactory().newMapStyleOptions(this, R.raw.mapstyle_night);
                break;
            case R.string.style_label_grayscale:
                // Sets the grayscale style via raw resource JSON.
                style = MapKit.getFactory().newMapStyleOptions(this, R.raw.mapstyle_grayscale);
                break;
            case R.string.style_label_no_pois_no_transit:
                // Sets the no POIs or transit style via JSON string.
                style = MapKit.getFactory().newMapStyleOptions(""
                        + "["
                        + "  {"
                        + "    \"featureType\":\"poi.business\","
                        + "    \"elementType\":\"all\","
                        + "    \"stylers\":["
                        + "      {"
                        + "        \"visibility\":\"off\""
                        + "      }"
                        + "    ]"
                        + "  },"
                        + "  {"
                        + "    \"featureType\":\"transit\","
                        + "    \"elementType\":\"all\","
                        + "    \"stylers\":["
                        + "      {"
                        + "        \"visibility\":\"off\""
                        + "      }"
                        + "    ]"
                        + "  }"
                        + "]");
                break;
            case R.string.style_label_default:
                // Removes previously set style, by setting it to null.
                style = null;
                break;
            default:
                return;
        }
        mMap.setMapStyle(style);
    }

}
