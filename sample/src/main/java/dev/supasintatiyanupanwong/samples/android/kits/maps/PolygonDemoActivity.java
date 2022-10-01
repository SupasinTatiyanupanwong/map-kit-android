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

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.SeekBar;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Arrays;
import java.util.List;

import dev.supasintatiyanupanwong.libraries.android.kits.maps.MapFragment;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.MapKit;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.Dash;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.Dot;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.Gap;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.JointType;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.LatLng;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.MapClient;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.PatternItem;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.Polygon;

/**
 * This shows how to draw polygons on a map.
 */
public class PolygonDemoActivity extends AppCompatActivity implements
        AdapterView.OnItemSelectedListener,
        SeekBar.OnSeekBarChangeListener,
        MapKit.OnMapReadyCallback {

    private static final LatLng CENTER = MapKit.newLatLng(-20, 130);
    private static final int MAX_WIDTH_PX = 100;
    private static final int MAX_HUE_DEGREES = 360;
    private static final int MAX_ALPHA = 255;

    private static final int PATTERN_DASH_LENGTH_PX = 50;
    private static final int PATTERN_GAP_LENGTH_PX = 10;
    private static final Dot DOT = MapKit.newDot();
    private static final Dash DASH = MapKit.newDash(PATTERN_DASH_LENGTH_PX);
    private static final Gap GAP = MapKit.newGap(PATTERN_GAP_LENGTH_PX);
    private static final List<PatternItem> PATTERN_DOTTED = Arrays.asList(DOT, GAP);
    private static final List<PatternItem> PATTERN_DASHED = Arrays.asList(DASH, GAP);
    private static final List<PatternItem> PATTERN_MIXED = Arrays.asList(DOT, GAP, DOT, DASH, GAP);

    private Polygon mMutablePolygon;
    private SeekBar mFillHueBar;
    private SeekBar mFillAlphaBar;
    private SeekBar mStrokeWidthBar;
    private SeekBar mStrokeHueBar;
    private SeekBar mStrokeAlphaBar;
    private Spinner mStrokeJointTypeSpinner;
    private Spinner mStrokePatternSpinner;
    private CheckBox mClickabilityCheckbox;

    // These are the options for polygon stroke joints and patterns. We use their
    // string resource IDs as identifiers.

    private static final int[] JOINT_TYPE_NAME_RESOURCE_IDS = {
            R.string.joint_type_default, // Default
            R.string.joint_type_bevel,
            R.string.joint_type_round,
    };

    private static final int[] PATTERN_TYPE_NAME_RESOURCE_IDS = {
            R.string.pattern_solid, // Default
            R.string.pattern_dashed,
            R.string.pattern_dotted,
            R.string.pattern_mixed,
    };

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.polygon_demo);

        mFillHueBar = findViewById(R.id.fillHueSeekBar);
        mFillHueBar.setMax(MAX_HUE_DEGREES);
        mFillHueBar.setProgress(MAX_HUE_DEGREES / 2);

        mFillAlphaBar = findViewById(R.id.fillAlphaSeekBar);
        mFillAlphaBar.setMax(MAX_ALPHA);
        mFillAlphaBar.setProgress(MAX_ALPHA / 2);

        mStrokeWidthBar = findViewById(R.id.strokeWidthSeekBar);
        mStrokeWidthBar.setMax(MAX_WIDTH_PX);
        mStrokeWidthBar.setProgress(MAX_WIDTH_PX / 3);

        mStrokeHueBar = findViewById(R.id.strokeHueSeekBar);
        mStrokeHueBar.setMax(MAX_HUE_DEGREES);
        mStrokeHueBar.setProgress(0);

        mStrokeAlphaBar = findViewById(R.id.strokeAlphaSeekBar);
        mStrokeAlphaBar.setMax(MAX_ALPHA);
        mStrokeAlphaBar.setProgress(MAX_ALPHA);

        mStrokeJointTypeSpinner = findViewById(R.id.strokeJointTypeSpinner);
        mStrokeJointTypeSpinner.setAdapter(new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item,
                getResourceStrings(JOINT_TYPE_NAME_RESOURCE_IDS)));

        mStrokePatternSpinner = findViewById(R.id.strokePatternSpinner);
        mStrokePatternSpinner.setAdapter(new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item,
                getResourceStrings(PATTERN_TYPE_NAME_RESOURCE_IDS)));

        mClickabilityCheckbox = findViewById(R.id.toggleClickability);

        MapFragment mapFragment =
                (MapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
    }

    private String[] getResourceStrings(int[] resourceIds) {
        String[] strings = new String[resourceIds.length];
        for (int i = 0; i < resourceIds.length; i++) {
            strings[i] = getString(resourceIds[i]);
        }
        return strings;
    }

    @Override public void onMapReady(@NonNull MapClient map) {
        // Override the default content description on the view, for accessibility mode.
        map.setContentDescription(getString(R.string.polygon_demo_description));

        int fillColorArgb = Color.HSVToColor(
                mFillAlphaBar.getProgress(), new float[]{mFillHueBar.getProgress(), 1, 1});
        int strokeColorArgb = Color.HSVToColor(
                mStrokeAlphaBar.getProgress(), new float[]{mStrokeHueBar.getProgress(), 1, 1});

        // Create a rectangle with two rectangular holes.
        mMutablePolygon = map.addPolygon(MapKit.newPolygonOptions()
                .addAll(createRectangle(CENTER, 5, 5))
                .addHole(createRectangle(MapKit.newLatLng(-22, 128), 1, 1))
                .addHole(createRectangle(MapKit.newLatLng(-18, 133), 0.5, 1.5))
                .fillColor(fillColorArgb)
                .strokeColor(strokeColorArgb)
                .strokeWidth(mStrokeWidthBar.getProgress())
                .clickable(mClickabilityCheckbox.isChecked()));

        mFillHueBar.setOnSeekBarChangeListener(this);
        mFillAlphaBar.setOnSeekBarChangeListener(this);

        mStrokeWidthBar.setOnSeekBarChangeListener(this);
        mStrokeHueBar.setOnSeekBarChangeListener(this);
        mStrokeAlphaBar.setOnSeekBarChangeListener(this);

        mStrokeJointTypeSpinner.setOnItemSelectedListener(this);
        mStrokePatternSpinner.setOnItemSelectedListener(this);

        mMutablePolygon.setStrokeJointType(
                getSelectedJointType(mStrokeJointTypeSpinner.getSelectedItemPosition()));
        mMutablePolygon.setStrokePattern(
                getSelectedPattern(mStrokePatternSpinner.getSelectedItemPosition()));

        // Move the map so that it is centered on the mutable polygon.
        map.moveCamera(MapKit.getCameraUpdateFactory().newLatLngZoom(CENTER, 4));

        // Add a listener for polygon clicks that changes the clicked polygon's stroke color.
        map.setOnPolygonClickListener(new MapClient.OnPolygonClickListener() {
            @Override public void onPolygonClick(@NonNull Polygon polygon) {
                // Flip the red, green and blue components of the polygon's stroke color.
                polygon.setStrokeColor(polygon.getStrokeColor() ^ 0x00ffffff);
            }
        });
    }

    /**
     * Creates a List of {@link LatLng}s that form a rectangle with the given dimensions.
     */
    private List<LatLng> createRectangle(LatLng center, double halfWidth, double halfHeight) {
        return Arrays.asList(
                MapKit.newLatLng(
                        center.getLatitude() - halfHeight, center.getLongitude() - halfWidth),
                MapKit.newLatLng(
                        center.getLatitude() - halfHeight, center.getLongitude() + halfWidth),
                MapKit.newLatLng(
                        center.getLatitude() + halfHeight, center.getLongitude() + halfWidth),
                MapKit.newLatLng(
                        center.getLatitude() + halfHeight, center.getLongitude() - halfWidth),
                MapKit.newLatLng(
                        center.getLatitude() - halfHeight, center.getLongitude() - halfWidth));
    }

    private int getSelectedJointType(int pos) {
        switch (JOINT_TYPE_NAME_RESOURCE_IDS[pos]) {
            case R.string.joint_type_bevel:
                return JointType.BEVEL;
            case R.string.joint_type_round:
                return JointType.ROUND;
            case R.string.joint_type_default:
                return JointType.DEFAULT;
        }
        return 0;
    }

    private List<PatternItem> getSelectedPattern(int pos) {
        switch (PATTERN_TYPE_NAME_RESOURCE_IDS[pos]) {
            case R.string.pattern_dotted:
                return PATTERN_DOTTED;
            case R.string.pattern_dashed:
                return PATTERN_DASHED;
            case R.string.pattern_mixed:
                return PATTERN_MIXED;
            default:
                return null;
        }
    }

    @Override public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        switch (parent.getId()) {
            case R.id.strokeJointTypeSpinner:
                mMutablePolygon.setStrokeJointType(getSelectedJointType(pos));
                break;
            case R.id.strokePatternSpinner:
                mMutablePolygon.setStrokePattern(getSelectedPattern(pos));
                break;
        }
    }

    @Override public void onNothingSelected(AdapterView<?> parent) {
        // Don't do anything here.
    }

    @Override public void onStopTrackingTouch(SeekBar seekBar) {
        // Don't do anything here.
    }

    @Override public void onStartTrackingTouch(SeekBar seekBar) {
        // Don't do anything here.
    }

    @Override public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (mMutablePolygon == null) {
            return;
        }

        if (seekBar == mFillHueBar) {
            mMutablePolygon.setFillColor(Color.HSVToColor(
                    Color.alpha(mMutablePolygon.getFillColor()), new float[]{progress, 1, 1}));
        } else if (seekBar == mFillAlphaBar) {
            int prevColor = mMutablePolygon.getFillColor();
            mMutablePolygon.setFillColor(Color.argb(
                    progress, Color.red(prevColor), Color.green(prevColor),
                    Color.blue(prevColor)));
        } else if (seekBar == mStrokeHueBar) {
            mMutablePolygon.setStrokeColor(Color.HSVToColor(
                    Color.alpha(mMutablePolygon.getStrokeColor()), new float[]{progress, 1, 1}));
        } else if (seekBar == mStrokeAlphaBar) {
            int prevColorArgb = mMutablePolygon.getStrokeColor();
            mMutablePolygon.setStrokeColor(Color.argb(
                    progress, Color.red(prevColorArgb), Color.green(prevColorArgb),
                    Color.blue(prevColorArgb)));
        } else if (seekBar == mStrokeWidthBar) {
            mMutablePolygon.setStrokeWidth(progress);
        }
    }

    /**
     * Toggles the clickability of the polygon based on the state of the View that triggered this
     * call.
     * This callback is defined on the CheckBox in the layout for this Activity.
     */
    public void toggleClickability(View view) {
        if (mMutablePolygon != null) {
            mMutablePolygon.setClickable(((CheckBox) view).isChecked());
        }
    }

}
