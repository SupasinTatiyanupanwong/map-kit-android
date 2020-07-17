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

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.view.animation.Interpolator;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.graphics.drawable.DrawableCompat;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import me.tatiyanupanwong.supasin.android.libraries.kits.maps.MapFragment;
import me.tatiyanupanwong.supasin.android.libraries.kits.maps.MapKit;
import me.tatiyanupanwong.supasin.android.libraries.kits.maps.model.BitmapDescriptor;
import me.tatiyanupanwong.supasin.android.libraries.kits.maps.model.LatLng;
import me.tatiyanupanwong.supasin.android.libraries.kits.maps.model.LatLngBounds;
import me.tatiyanupanwong.supasin.android.libraries.kits.maps.model.MapClient;
import me.tatiyanupanwong.supasin.android.libraries.kits.maps.model.Marker;

/**
 * This shows how to place markers on a map.
 */
public class MarkerDemoActivity extends AppCompatActivity implements
        SeekBar.OnSeekBarChangeListener,
        MapClient.OnInfoWindowClickListener,
        MapClient.OnInfoWindowCloseListener,
        MapClient.OnInfoWindowLongClickListener,
        MapClient.OnMarkerClickListener,
        MapClient.OnMarkerDragListener,
        OnMapAndViewReadyListener.OnGlobalLayoutAndMapReadyListener {

    private static final LatLng BRISBANE = MapKit.newLatLng(-27.47093, 153.0235);

    private static final LatLng MELBOURNE = MapKit.newLatLng(-37.81319, 144.96298);

    private static final LatLng DARWIN = MapKit.newLatLng(-12.4634, 130.8456);

    private static final LatLng SYDNEY = MapKit.newLatLng(-33.87365, 151.20689);

    private static final LatLng ADELAIDE = MapKit.newLatLng(-34.92873, 138.59995);

    private static final LatLng PERTH = MapKit.newLatLng(-31.952854, 115.857342);

    private static final LatLng ALICE_SPRINGS = MapKit.newLatLng(-24.6980, 133.8807);

    /** Demonstrates customizing the info window and/or its contents. */
    class CustomInfoWindowAdapter implements MapClient.InfoWindowAdapter {

        // These are both viewgroups containing an ImageView with id "badge" and two TextViews with
        // id "title" and "snippet".
        private final View mWindow;

        private final View mContents;

        CustomInfoWindowAdapter() {
            mWindow = getLayoutInflater().inflate(R.layout.custom_info_window, null);
            mContents = getLayoutInflater().inflate(R.layout.custom_info_contents, null);
        }

        @Override
        public View getInfoWindow(@NonNull Marker marker) {
            if (mOptions.getCheckedRadioButtonId() != R.id.custom_info_window) {
                // This means that getInfoContents will be called.
                return null;
            }
            render(marker, mWindow);
            return mWindow;
        }

        @Override
        public View getInfoContents(@NonNull Marker marker) {
            if (mOptions.getCheckedRadioButtonId() != R.id.custom_info_contents) {
                // This means that the default info contents will be used.
                return null;
            }
            render(marker, mContents);
            return mContents;
        }

        private void render(Marker marker, View view) {
            int badge;
            // Use the equals() method on a Marker to check for equals.  Do not use ==.
            if (marker.equals(mBrisbane)) {
                badge = R.drawable.badge_qld;
            } else if (marker.equals(mAdelaide)) {
                badge = R.drawable.badge_sa;
            } else if (marker.equals(mSydney)) {
                badge = R.drawable.badge_nsw;
            } else if (marker.equals(mMelbourne)) {
                badge = R.drawable.badge_victoria;
            } else if (marker.equals(mPerth)) {
                badge = R.drawable.badge_wa;
            } else if (marker.equals(mDarwin1)) {
                badge = R.drawable.badge_nt;
            } else if (marker.equals(mDarwin2)) {
                badge = R.drawable.badge_nt;
            } else if (marker.equals(mDarwin3)) {
                badge = R.drawable.badge_nt;
            } else if (marker.equals(mDarwin4)) {
                badge = R.drawable.badge_nt;
            } else {
                // Passing 0 to setImageResource will clear the image view.
                badge = 0;
            }
            ((ImageView) view.findViewById(R.id.badge)).setImageResource(badge);

            String title = marker.getTitle();
            TextView titleUi = view.findViewById(R.id.title);
            if (title != null) {
                // Spannable string allows us to edit the formatting of the text.
                SpannableString titleText = new SpannableString(title);
                titleText.setSpan(new ForegroundColorSpan(Color.RED), 0, titleText.length(), 0);
                titleUi.setText(titleText);
            } else {
                titleUi.setText("");
            }

            String snippet = marker.getSnippet();
            TextView snippetUi = view.findViewById(R.id.snippet);
            if (snippet != null && snippet.length() > 12) {
                SpannableString snippetText = new SpannableString(snippet);
                snippetText.setSpan(new ForegroundColorSpan(Color.MAGENTA), 0, 10, 0);
                snippetText.setSpan(new ForegroundColorSpan(Color.BLUE), 12, snippet.length(), 0);
                snippetUi.setText(snippetText);
            } else {
                snippetUi.setText("");
            }
        }
    }

    private MapClient mMap;

    private Marker mPerth;

    private Marker mSydney;

    private Marker mBrisbane;

    private Marker mAdelaide;

    private Marker mMelbourne;

    private Marker mDarwin1;
    private Marker mDarwin2;
    private Marker mDarwin3;
    private Marker mDarwin4;


    /**
     * Keeps track of the last selected marker (though it may no longer be selected).  This is
     * useful for refreshing the info window.
     */
    private Marker mLastSelectedMarker;

    private final List<Marker> mMarkerRainbow = new ArrayList<>();

    private TextView mTopText;

    private SeekBar mRotationBar;

    private CheckBox mFlatBox;

    private RadioGroup mOptions;

    private final Random mRandom = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.marker_demo);

        mTopText = findViewById(R.id.top_text);

        mRotationBar = findViewById(R.id.rotationSeekBar);
        mRotationBar.setMax(360);
        mRotationBar.setOnSeekBarChangeListener(this);

        mFlatBox = findViewById(R.id.flat);

        mOptions = findViewById(R.id.custom_info_window_options);
        mOptions.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (mLastSelectedMarker != null && mLastSelectedMarker.isInfoWindowShown()) {
                    // Refresh the info window when the info window's content has changed.
                    mLastSelectedMarker.showInfoWindow();
                }
            }
        });

        MapFragment mapFragment =
                (MapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            new OnMapAndViewReadyListener(mapFragment, this);
        }
    }

    @Override
    public void onMapReady(@NonNull MapClient map) {
        mMap = map;

        // Hide the zoom controls as the button panel will cover it.
        mMap.getUiSettings().setZoomControlsEnabled(false);

        // Add lots of markers to the map.
        addMarkersToMap();

        // Setting an info window adapter allows us to change the both the contents and look of the
        // info window.
        mMap.setInfoWindowAdapter(new CustomInfoWindowAdapter());

        // Set listeners for marker events.  See the bottom of this class for their behavior.
        mMap.setOnMarkerClickListener(this);
        mMap.setOnInfoWindowClickListener(this);
        mMap.setOnMarkerDragListener(this);
        mMap.setOnInfoWindowCloseListener(this);
        mMap.setOnInfoWindowLongClickListener(this);

        // Override the default content description on the view, for accessibility mode.
        // Ideally this string would be localised.
        mMap.setContentDescription("Map with lots of markers.");

        LatLngBounds bounds = MapKit.newLatLngBoundsBuilder()
                .include(PERTH)
                .include(SYDNEY)
                .include(ADELAIDE)
                .include(BRISBANE)
                .include(MELBOURNE)
                .include(DARWIN)
                .build();
        mMap.moveCamera(MapKit.getCameraUpdateFactory().newLatLngBounds(bounds, 50));
    }

    private void addMarkersToMap() {
        // Uses a colored icon.
        mBrisbane = mMap.addMarker(MapKit.newMarkerOptions()
                .position(BRISBANE)
                .title("Brisbane")
                .snippet("Population: 2,074,200")
                .icon(MapKit.getBitmapDescriptorFactory()
                        .defaultMarker(BitmapDescriptor.Factory.HUE_AZURE)));

        // Uses a custom icon with the info window popping out of the center of the icon.
        mSydney = mMap.addMarker(MapKit.newMarkerOptions()
                .position(SYDNEY)
                .title("Sydney")
                .snippet("Population: 4,627,300")
                .icon(MapKit.getBitmapDescriptorFactory()
                        .fromResource(R.drawable.arrow))
                .infoWindowAnchor(0.5f, 0.5f));

        // Creates a draggable marker. Long press to drag.
        mMelbourne = mMap.addMarker(MapKit.newMarkerOptions()
                .position(MELBOURNE)
                .title("Melbourne")
                .snippet("Population: 4,137,400")
                .draggable(true));

        // Place four markers on top of each other with differing z-indexes.
        mDarwin1 = mMap.addMarker(MapKit.newMarkerOptions()
                .position(DARWIN)
                .title("Darwin Marker 1")
                .snippet("z-index 1")
                .zIndex(1));
        mDarwin2 = mMap.addMarker(MapKit.newMarkerOptions()
                .position(DARWIN)
                .title("Darwin Marker 2")
                .snippet("z-index 2")
                .zIndex(2));
        mDarwin3 = mMap.addMarker(MapKit.newMarkerOptions()
                .position(DARWIN)
                .title("Darwin Marker 3")
                .snippet("z-index 3")
                .zIndex(3));
        mDarwin4 = mMap.addMarker(MapKit.newMarkerOptions()
                .position(DARWIN)
                .title("Darwin Marker 4")
                .snippet("z-index 4")
                .zIndex(4));


        // A few more markers for good measure.
        mPerth = mMap.addMarker(MapKit.newMarkerOptions()
                .position(PERTH)
                .title("Perth")
                .snippet("Population: 1,738,800"));
        mAdelaide = mMap.addMarker(MapKit.newMarkerOptions()
                .position(ADELAIDE)
                .title("Adelaide")
                .snippet("Population: 1,213,000"));

        // Vector drawable resource as a marker icon.
        mMap.addMarker(MapKit.newMarkerOptions()
                .position(ALICE_SPRINGS)
                .icon(vectorToBitmap(R.drawable.ic_android, Color.parseColor("#A4C639")))
                .title("Alice Springs"));

        // Creates a marker rainbow demonstrating how to create default marker icons of different
        // hues (colors).
        float rotation = mRotationBar.getProgress();
        boolean flat = mFlatBox.isChecked();

        int numMarkersInRainbow = 12;
        for (int i = 0; i < numMarkersInRainbow; i++) {
            Marker marker = mMap.addMarker(MapKit.newMarkerOptions()
                    .position(MapKit.newLatLng(
                            -30 + 10 * Math.sin(i * Math.PI / (numMarkersInRainbow - 1)),
                            135 - 10 * Math.cos(i * Math.PI / (numMarkersInRainbow - 1))))
                    .title("Marker " + i)
                    .icon(MapKit.getBitmapDescriptorFactory()
                            .defaultMarker(i * 360f / numMarkersInRainbow))
                    .flat(flat)
                    .rotation(rotation));
            mMarkerRainbow.add(marker);
        }
    }

    /**
     * Demonstrates converting a {@link Drawable} to a {@link BitmapDescriptor},
     * for use as a marker icon.
     */
    private BitmapDescriptor vectorToBitmap(@DrawableRes int id, @ColorInt int color) {
        Drawable vectorDrawable =
                Objects.requireNonNull(ResourcesCompat.getDrawable(getResources(), id, null));
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(),
                vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        DrawableCompat.setTint(vectorDrawable, color);
        vectorDrawable.draw(canvas);
        return MapKit.getBitmapDescriptorFactory().fromBitmap(bitmap);
    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    private boolean checkReady() {
        if (mMap == null) {
            Toast.makeText(this, R.string.map_not_ready, Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    /** Called when the Clear button is clicked. */
    public void onClearMap(View view) {
        if (!checkReady()) {
            return;
        }
        mMap.clear();
    }

    /** Called when the Reset button is clicked. */
    public void onResetMap(View view) {
        if (!checkReady()) {
            return;
        }
        // Clear the map because we don't want duplicates of the markers.
        mMap.clear();
        addMarkersToMap();
    }

    /** Called when the Reset button is clicked. */
    public void onToggleFlat(View view) {
        if (!checkReady()) {
            return;
        }
        boolean flat = mFlatBox.isChecked();
        for (Marker marker : mMarkerRainbow) {
            marker.setFlat(flat);
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (!checkReady()) {
            return;
        }
        float rotation = seekBar.getProgress();
        for (Marker marker : mMarkerRainbow) {
            marker.setRotation(rotation);
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        // Do nothing.
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        // Do nothing.
    }

    //
    // Marker related listeners.
    //

    @Override
    public boolean onMarkerClick(@NonNull final Marker marker) {
        if (marker.equals(mPerth)) {
            // This causes the marker at Perth to bounce into position when it is clicked.
            final Handler handler = new Handler();
            final long start = SystemClock.uptimeMillis();
            final long duration = 1500;

            final Interpolator interpolator = new BounceInterpolator();

            handler.post(new Runnable() {
                @Override
                public void run() {
                    long elapsed = SystemClock.uptimeMillis() - start;
                    float t = Math.max(
                            1 - interpolator.getInterpolation((float) elapsed / duration), 0);
                    marker.setAnchor(0.5f, 1.0f + 2 * t);

                    if (t > 0.0) {
                        // Post again 16ms later.
                        handler.postDelayed(this, 16);
                    }
                }
            });
        } else if (marker.equals(mAdelaide)) {
            // This causes the marker at Adelaide to change color and alpha.
            marker.setIcon(MapKit.getBitmapDescriptorFactory()
                    .defaultMarker(mRandom.nextFloat() * 360));
            marker.setAlpha(mRandom.nextFloat());
        }

        // Markers have a z-index that is settable and gettable.
        float zIndex = marker.getZIndex() + 1.0f;
        marker.setZIndex(zIndex);
        Toast.makeText(this, marker.getTitle() + " z-index set to " + zIndex,
                Toast.LENGTH_SHORT).show();

        mLastSelectedMarker = marker;
        // We return false to indicate that we have not consumed the event and that we wish
        // for the default behavior to occur (which is for the camera to move such that the
        // marker is centered and for the marker's info window to open, if it has one).
        return false;
    }

    @Override
    public void onInfoWindowClick(@NonNull Marker marker) {
        Toast.makeText(this, "Click Info Window", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onInfoWindowClose(@NonNull Marker marker) {
        Toast.makeText(this, "Close Info Window", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onInfoWindowLongClick(@NonNull Marker marker) {
        Toast.makeText(this, "Info Window long click", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onMarkerDragStart(@NonNull Marker marker) {
        mTopText.setText("onMarkerDragStart");
    }

    @Override
    public void onMarkerDragEnd(@NonNull Marker marker) {
        mTopText.setText("onMarkerDragEnd");
    }

    @Override
    public void onMarkerDrag(@NonNull Marker marker) {
        mTopText.setText("onMarkerDrag.  Current Position: " + marker.getPosition());
    }

}
