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
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

import me.tatiyanupanwong.supasin.android.libraries.kits.maps.MapFragment;
import me.tatiyanupanwong.supasin.android.libraries.kits.maps.MapKit;
import me.tatiyanupanwong.supasin.android.libraries.kits.maps.model.Circle;
import me.tatiyanupanwong.supasin.android.libraries.kits.maps.model.GroundOverlay;
import me.tatiyanupanwong.supasin.android.libraries.kits.maps.model.LatLng;
import me.tatiyanupanwong.supasin.android.libraries.kits.maps.model.LatLngBounds;
import me.tatiyanupanwong.supasin.android.libraries.kits.maps.model.Map;
import me.tatiyanupanwong.supasin.android.libraries.kits.maps.model.Marker;
import me.tatiyanupanwong.supasin.android.libraries.kits.maps.model.Polygon;
import me.tatiyanupanwong.supasin.android.libraries.kits.maps.model.Polyline;

/**
 * This shows how to use setTag/getTag on API objects.
 */
public class TagsDemoActivity extends AppCompatActivity implements
        Map.OnCircleClickListener,
        Map.OnGroundOverlayClickListener,
        Map.OnMarkerClickListener,
        Map.OnPolygonClickListener,
        Map.OnPolylineClickListener,
        OnMapAndViewReadyListener.OnGlobalLayoutAndMapReadyListener {

    private static final LatLng ADELAIDE = MapKit.getFactory().newLatLng(-34.92873, 138.59995);
    private static final LatLng BRISBANE = MapKit.getFactory().newLatLng(-27.47093, 153.0235);
    private static final LatLng DARWIN = MapKit.getFactory().newLatLng(-12.425892, 130.86327);
    private static final LatLng HOBART = MapKit.getFactory().newLatLng(-42.8823388, 147.311042);
    private static final LatLng PERTH = MapKit.getFactory().newLatLng(-31.952854, 115.857342);
    private static final LatLng SYDNEY = MapKit.getFactory().newLatLng(-33.87365, 151.20689);

    private static class CustomTag {
        private final String mDescription;
        private int mClickCount;

        CustomTag(String description) {
            mDescription = description;
            mClickCount = 0;
        }

        void incrementClickCount() {
            mClickCount++;
        }

        @NonNull
        @Override
        public String toString() {
            return "The " + mDescription + " has been clicked " + mClickCount + " times.";
        }
    }

    private Map mMap = null;

    private TextView mTagText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tags_demo);

        mTagText = findViewById(R.id.tag_text);

        MapFragment mapFragment =
                (MapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            new OnMapAndViewReadyListener(mapFragment, this);
        }
    }

    @Override
    public void onMapReady(Map map) {
        mMap = map;

        Map.UiSettings uiSettings = mMap.getUiSettings();

        // Turn off the map toolbar.
        uiSettings.setMapToolbarEnabled(false);

        // Disable interaction with the map - other than clicking.
        uiSettings.setZoomControlsEnabled(false);
        uiSettings.setScrollGesturesEnabled(false);
        uiSettings.setZoomGesturesEnabled(false);
        uiSettings.setTiltGesturesEnabled(false);
        uiSettings.setRotateGesturesEnabled(false);

        // Add a circle, a ground overlay, a marker, a polygon and a polyline to the map.
        addObjectsToMap();

        // Set listeners for click events.  See the bottom of this class for their behavior.
        mMap.setOnCircleClickListener(this);
        mMap.setOnGroundOverlayClickListener(this);
        mMap.setOnMarkerClickListener(this);
        mMap.setOnPolygonClickListener(this);
        mMap.setOnPolylineClickListener(this);

        // Override the default content description on the view, for accessibility mode.
        // Ideally this string would be localised.
        map.setContentDescription(getString(R.string.tags_demo_map_description));

        // Create bounds that include all locations of the map.
        LatLngBounds bounds = MapKit.getFactory().newLatLngBoundsBuilder()
                .include(ADELAIDE)
                .include(BRISBANE)
                .include(DARWIN)
                .include(HOBART)
                .include(PERTH)
                .include(SYDNEY)
                .build();
        mMap.moveCamera(
                MapKit.getFactory().getCameraUpdateFactory().newLatLngBounds(bounds, 100));
    }

    private void addObjectsToMap() {
        // A circle centered on Adelaide.
        Circle adelaideCircle = mMap.addCircle(MapKit.getFactory().newCircleOptions()
                .center(ADELAIDE)
                .radius(500000)
                .fillColor(Color.argb(150, 66, 173, 244))
                .strokeColor(Color.rgb(66, 173, 244))
                .clickable(true));
        adelaideCircle.setTag(new CustomTag("Adelaide circle"));

        // A ground overlay at Sydney.
        GroundOverlay sydneyGroundOverlay = mMap.addGroundOverlay(
                MapKit.getFactory().newGroundOverlayOptions()
                        .image(MapKit.getFactory().getBitmapDescriptorFactory()
                                .fromResource(R.drawable.harbour_bridge))
                        .position(SYDNEY, 700000)
                        .clickable(true));
        sydneyGroundOverlay.setTag(new CustomTag("Sydney ground overlay"));

        // A marker at Hobart.
        Marker hobartMarker = mMap.addMarker(
                MapKit.getFactory().newMarkerOptions().position(HOBART));
        hobartMarker.setTag(new CustomTag("Hobart marker"));

        // A polygon centered at Darwin.
        Polygon darwinPolygon = mMap.addPolygon(MapKit.getFactory().newPolygonOptions()
                .add(
                        MapKit.getFactory()
                                .newLatLng(DARWIN.getLatitude() + 3, DARWIN.getLongitude() - 3),
                        MapKit.getFactory()
                                .newLatLng(DARWIN.getLatitude() + 3, DARWIN.getLongitude() + 3),
                        MapKit.getFactory()
                                .newLatLng(DARWIN.getLatitude() - 3, DARWIN.getLongitude() + 3),
                        MapKit.getFactory()
                                .newLatLng(DARWIN.getLatitude() - 3, DARWIN.getLongitude() - 3))
                .fillColor(Color.argb(150, 34, 173, 24))
                .strokeColor(Color.rgb(34, 173, 24))
                .clickable(true));
        darwinPolygon.setTag(new CustomTag("Darwin polygon"));

        // A polyline from Perth to Brisbane.
        Polyline polyline = mMap.addPolyline(MapKit.getFactory().newPolylineOptions()
                .add(PERTH, BRISBANE)
                .color(Color.rgb(103, 24, 173))
                .width(30)
                .clickable(true));
        polyline.setTag(new CustomTag("Perth to Brisbane polyline"));
    }

    //
    // Click event listeners.
    //

    private void onClick(@NonNull CustomTag tag) {
        tag.incrementClickCount();
        mTagText.setText(tag.toString());
    }

    @Override
    public void onCircleClick(@NonNull Circle circle) {
        onClick((CustomTag) Objects.requireNonNull(circle.getTag()));
    }

    @Override
    public void onGroundOverlayClick(@NonNull GroundOverlay groundOverlay) {
        onClick((CustomTag) groundOverlay.getTag());
    }

    @Override
    public boolean onMarkerClick(@NonNull final Marker marker) {
        onClick((CustomTag) Objects.requireNonNull(marker.getTag()));
        // We return true to indicate that we have consumed the event and that we do not wish
        // for the default behavior to occur (which is for the camera to move such that the
        // marker is centered and for the marker's info window to open, if it has one).
        return true;
    }

    @Override
    public void onPolygonClick(@NonNull Polygon polygon) {
        onClick((CustomTag) Objects.requireNonNull(polygon.getTag()));
    }

    @Override
    public void onPolylineClick(@NonNull Polyline polyline) {
        onClick((CustomTag) Objects.requireNonNull(polyline.getTag()));
    }

}
