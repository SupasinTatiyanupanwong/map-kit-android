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
import android.graphics.Point;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.SeekBar;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import dev.supasintatiyanupanwong.libraries.android.kits.maps.MapFragment;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.MapKit;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.BitmapDescriptor;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.Circle;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.Dash;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.Dot;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.Gap;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.LatLng;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.MapClient;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.Marker;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.PatternItem;

/**
 * This shows how to draw circles on a map.
 */
public class CircleDemoActivity extends AppCompatActivity implements
        AdapterView.OnItemSelectedListener,
        SeekBar.OnSeekBarChangeListener,
        MapKit.OnMapReadyCallback,
        MapClient.OnMarkerDragListener,
        MapClient.OnMapLongClickListener {

    private static final LatLng SYDNEY = MapKit.newLatLng(-33.87365, 151.20689);
    private static final double DEFAULT_RADIUS_METERS = 1000000;
    private static final double RADIUS_OF_EARTH_METERS = 6371009;

    private static final int MAX_WIDTH_PX = 50;
    private static final int MAX_HUE_DEGREES = 360;
    private static final int MAX_ALPHA = 255;

    private static final int PATTERN_DASH_LENGTH_PX = 100;
    private static final int PATTERN_GAP_LENGTH_PX = 200;
    private static final Dot DOT = MapKit.newDot();
    private static final Dash DASH = MapKit.newDash(PATTERN_DASH_LENGTH_PX);
    private static final Gap GAP = MapKit.newGap(PATTERN_GAP_LENGTH_PX);
    private static final List<PatternItem> PATTERN_DOTTED = Arrays.asList(DOT, GAP);
    private static final List<PatternItem> PATTERN_DASHED = Arrays.asList(DASH, GAP);
    private static final List<PatternItem> PATTERN_MIXED = Arrays.asList(DOT, GAP, DOT, DASH, GAP);

    private MapClient mMap;

    private final List<DraggableCircle> mCircles = new ArrayList<>(1);

    private int mFillColorArgb;
    private int mStrokeColorArgb;

    private SeekBar mFillHueBar;
    private SeekBar mFillAlphaBar;
    private SeekBar mStrokeWidthBar;
    private SeekBar mStrokeHueBar;
    private SeekBar mStrokeAlphaBar;
    private Spinner mStrokePatternSpinner;
    private CheckBox mClickabilityCheckbox;

    // These are the options for stroke patterns. We use their
    // string resource IDs as identifiers.

    private static final int[] PATTERN_TYPE_NAME_RESOURCE_IDS = {
            R.string.pattern_solid, // Default
            R.string.pattern_dashed,
            R.string.pattern_dotted,
            R.string.pattern_mixed,
    };

    private class DraggableCircle {
        private final Marker mCenterMarker;
        private final Marker mRadiusMarker;
        private final Circle mCircle;
        private double mRadiusMeters;

        DraggableCircle(LatLng center, double radiusMeters) {
            mRadiusMeters = radiusMeters;
            mCenterMarker = mMap.addMarker(MapKit.newMarkerOptions()
                    .position(center)
                    .draggable(true));
            mRadiusMarker = mMap.addMarker(MapKit.newMarkerOptions()
                    .position(toRadiusLatLng(center, radiusMeters))
                    .draggable(true)
                    .icon(MapKit.getBitmapDescriptorFactory()
                            .defaultMarker(BitmapDescriptor.Factory.HUE_AZURE)));
            mCircle = mMap.addCircle(MapKit.newCircleOptions()
                    .center(center)
                    .radius(radiusMeters)
                    .strokeWidth(mStrokeWidthBar.getProgress())
                    .strokeColor(mStrokeColorArgb)
                    .fillColor(mFillColorArgb)
                    .clickable(mClickabilityCheckbox.isChecked()));
        }

        boolean onMarkerMoved(Marker marker) {
            if (marker.equals(mCenterMarker)) {
                mCircle.setCenter(marker.getPosition());
                mRadiusMarker.setPosition(toRadiusLatLng(marker.getPosition(), mRadiusMeters));
                return true;
            }
            if (marker.equals(mRadiusMarker)) {
                mRadiusMeters =
                        toRadiusMeters(mCenterMarker.getPosition(), mRadiusMarker.getPosition());
                mCircle.setRadius(mRadiusMeters);
                return true;
            }
            return false;
        }

        void onStyleChange() {
            mCircle.setStrokeWidth(mStrokeWidthBar.getProgress());
            mCircle.setStrokeColor(mStrokeColorArgb);
            mCircle.setFillColor(mFillColorArgb);
        }

        void setStrokePattern(List<PatternItem> pattern) {
            mCircle.setStrokePattern(pattern);
        }

        void setClickable(boolean clickable) {
            mCircle.setClickable(clickable);
        }
    }

    /** Generate LatLng of radius marker */
    private static LatLng toRadiusLatLng(LatLng center, double radiusMeters) {
        double radiusAngle = Math.toDegrees(radiusMeters / RADIUS_OF_EARTH_METERS) /
                Math.cos(Math.toRadians(center.getLatitude()));
        return MapKit.newLatLng(
                center.getLatitude(), center.getLongitude() + radiusAngle);
    }

    private static double toRadiusMeters(LatLng center, LatLng radius) {
        float[] result = new float[1];
        Location.distanceBetween(center.getLatitude(), center.getLongitude(),
                radius.getLatitude(), radius.getLongitude(), result);
        return result[0];
    }

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.circle_demo);

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
        map.setContentDescription(getString(R.string.map_circle_description));

        mMap = map;
        mMap.setOnMarkerDragListener(this);
        mMap.setOnMapLongClickListener(this);

        mFillColorArgb = Color.HSVToColor(
                mFillAlphaBar.getProgress(), new float[]{mFillHueBar.getProgress(), 1, 1});
        mStrokeColorArgb = Color.HSVToColor(
                mStrokeAlphaBar.getProgress(), new float[]{mStrokeHueBar.getProgress(), 1, 1});

        mFillHueBar.setOnSeekBarChangeListener(this);
        mFillAlphaBar.setOnSeekBarChangeListener(this);

        mStrokeWidthBar.setOnSeekBarChangeListener(this);
        mStrokeHueBar.setOnSeekBarChangeListener(this);
        mStrokeAlphaBar.setOnSeekBarChangeListener(this);

        mStrokePatternSpinner.setOnItemSelectedListener(this);

        DraggableCircle circle = new DraggableCircle(SYDNEY, DEFAULT_RADIUS_METERS);
        mCircles.add(circle);

        // Move the map so that it is centered on the initial circle
        mMap.moveCamera(MapKit.getCameraUpdateFactory().newLatLngZoom(SYDNEY, 4.0f));

        // Set up the click listener for the circle.
        map.setOnCircleClickListener(new MapClient.OnCircleClickListener() {
            @Override public void onCircleClick(@NonNull Circle circle) {
                // Flip the red, green and blue components of the circle's stroke color.
                circle.setStrokeColor(circle.getStrokeColor() ^ 0x00ffffff);
            }
        });

        List<PatternItem> pattern =
                getSelectedPattern(mStrokePatternSpinner.getSelectedItemPosition());
        for (DraggableCircle draggableCircle : mCircles) {
            draggableCircle.setStrokePattern(pattern);
        }
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
        if (parent.getId() == R.id.strokePatternSpinner) {
            for (DraggableCircle draggableCircle : mCircles) {
                draggableCircle.setStrokePattern(getSelectedPattern(pos));
            }
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
        if (seekBar == mFillHueBar) {
            mFillColorArgb =
                    Color.HSVToColor(Color.alpha(mFillColorArgb), new float[]{progress, 1, 1});
        } else if (seekBar == mFillAlphaBar) {
            mFillColorArgb = Color.argb(progress, Color.red(mFillColorArgb),
                    Color.green(mFillColorArgb), Color.blue(mFillColorArgb));
        } else if (seekBar == mStrokeHueBar) {
            mStrokeColorArgb =
                    Color.HSVToColor(Color.alpha(mStrokeColorArgb), new float[]{progress, 1, 1});
        } else if (seekBar == mStrokeAlphaBar) {
            mStrokeColorArgb = Color.argb(progress, Color.red(mStrokeColorArgb),
                    Color.green(mStrokeColorArgb), Color.blue(mStrokeColorArgb));
        }

        for (DraggableCircle draggableCircle : mCircles) {
            draggableCircle.onStyleChange();
        }
    }

    @Override public void onMarkerDragStart(@NonNull Marker marker) {
        onMarkerMoved(marker);
    }

    @Override public void onMarkerDragEnd(@NonNull Marker marker) {
        onMarkerMoved(marker);
    }

    @Override public void onMarkerDrag(@NonNull Marker marker) {
        onMarkerMoved(marker);
    }

    private void onMarkerMoved(Marker marker) {
        for (DraggableCircle draggableCircle : mCircles) {
            if (draggableCircle.onMarkerMoved(marker)) {
                break;
            }
        }
    }

    @Override public void onMapLongClick(@NonNull LatLng point) {
        // We know the center, let's place the outline at a point 3/4 along the view.
        Fragment mapFragment =
                Objects.requireNonNull(getSupportFragmentManager().findFragmentById(R.id.map));
        View mapView = Objects.requireNonNull(mapFragment.getView());
        LatLng radiusLatLng = Objects.requireNonNull(mMap.getProjection().fromScreenLocation(
                new Point(mapView.getHeight() * 3 / 4, mapView.getWidth() * 3 / 4)));

        // Create the circle.
        DraggableCircle circle = new DraggableCircle(point, toRadiusMeters(point, radiusLatLng));
        mCircles.add(circle);
    }

    public void toggleClickability(View view) {
        boolean clickable = ((CheckBox) view).isChecked();
        // Set each of the circles to be clickable or not, based on the
        // state of the checkbox.
        for (DraggableCircle draggableCircle : mCircles) {
            draggableCircle.setClickable(clickable);
        }
    }

}
