/*
 * Copyright 2020 Supasin Tatiyanupanwong
 * Copyright 2016 The Android Open Source Project
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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import dev.supasintatiyanupanwong.libraries.android.kits.maps.MapFragment;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.MapKit;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.LatLng;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.LatLngBounds;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.MapClient;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.Marker;

/**
 * This shows how to close the info window when the currently selected marker is re-tapped.
 */
public class MarkerCloseInfoWindowOnRetapDemoActivity extends AppCompatActivity implements
        MapClient.OnMapClickListener,
        MapClient.OnMarkerClickListener,
        MapKit.OnMapAndViewReadyCallback {

    private static final LatLng BRISBANE = MapKit.newLatLng(-27.47093, 153.0235);
    private static final LatLng MELBOURNE = MapKit.newLatLng(-37.81319, 144.96298);
    private static final LatLng SYDNEY = MapKit.newLatLng(-33.87365, 151.20689);
    private static final LatLng ADELAIDE = MapKit.newLatLng(-34.92873, 138.59995);
    private static final LatLng PERTH = MapKit.newLatLng(-31.952854, 115.857342);

    private MapClient mMap = null;

    /**
     * Keeps track of the selected marker.
     */
    private Marker mSelectedMarker;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.marker_close_info_window_on_retap_demo);

        MapFragment mapFragment =
                (MapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
    }

    @Override public void onMapAndViewReady(@NonNull MapClient map) {
        mMap = map;

        // Hide the zoom controls.
        mMap.getUiSettings().setZoomControlsEnabled(false);

        // Add lots of markers to the map.
        addMarkersToMap();

        // Set listener for marker click event.  See the bottom of this class for its behavior.
        mMap.setOnMarkerClickListener(this);

        // Set listener for map click event.  See the bottom of this class for its behavior.
        mMap.setOnMapClickListener(this);

        // Override the default content description on the view, for accessibility mode.
        // Ideally this string would be localized.
        map.setContentDescription("Demo showing how to close the info window when the currently"
            + " selected marker is re-tapped.");

        LatLngBounds bounds = MapKit.newLatLngBoundsBuilder()
                .include(PERTH)
                .include(SYDNEY)
                .include(ADELAIDE)
                .include(BRISBANE)
                .include(MELBOURNE)
                .build();
        mMap.moveCamera(
                MapKit.getCameraUpdateFactory().newLatLngBounds(bounds, 50));
    }

    private void addMarkersToMap() {
       mMap.addMarker(MapKit.newMarkerOptions()
                .position(BRISBANE)
                .title("Brisbane")
                .snippet("Population: 2,074,200"));

        mMap.addMarker(MapKit.newMarkerOptions()
                .position(SYDNEY)
                .title("Sydney")
                .snippet("Population: 4,627,300"));

        mMap.addMarker(MapKit.newMarkerOptions()
                .position(MELBOURNE)
                .title("Melbourne")
                .snippet("Population: 4,137,400"));

        mMap.addMarker(MapKit.newMarkerOptions()
                .position(PERTH)
                .title("Perth")
                .snippet("Population: 1,738,800"));

        mMap.addMarker(MapKit.newMarkerOptions()
                .position(ADELAIDE)
                .title("Adelaide")
                .snippet("Population: 1,213,000"));
    }

    @Override public void onMapClick(final @NonNull LatLng point) {
        // Any showing info window closes when the map is clicked.
        // Clear the currently selected marker.
        mSelectedMarker = null;
    }

    @Override public boolean onMarkerClick(final @NonNull Marker marker) {
        // The user has re-tapped on the marker which was already showing an info window.
        if (marker.equals(mSelectedMarker)) {
            // The showing info window has already been closed - that's the first thing to happen
            // when any marker is clicked.
            // Return true to indicate we have consumed the event and that we do not want the
            // the default behavior to occur (which is for the camera to move such that the
            // marker is centered and for the marker's info window to open, if it has one).
            mSelectedMarker = null;
            return true;
        }

        mSelectedMarker = marker;

        // Return false to indicate that we have not consumed the event and that we wish
        // for the default behavior to occur.
        return false;
    }

}
