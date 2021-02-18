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

package me.tatiyanupanwong.supasin.android.samples.kits.maps;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.SeekBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Locale;

import me.tatiyanupanwong.supasin.android.libraries.kits.maps.MapFragment;
import me.tatiyanupanwong.supasin.android.libraries.kits.maps.MapKit;
import me.tatiyanupanwong.supasin.android.libraries.kits.maps.model.MapClient;
import me.tatiyanupanwong.supasin.android.libraries.kits.maps.model.TileOverlay;
import me.tatiyanupanwong.supasin.android.libraries.kits.maps.model.TileProvider;
import me.tatiyanupanwong.supasin.android.libraries.kits.maps.model.UrlTileProvider;

/**
 * This demonstrates how to add a tile overlay to a map.
 */
public class TileOverlayDemoActivity extends AppCompatActivity implements
        SeekBar.OnSeekBarChangeListener,
        MapKit.OnMapReadyCallback {

    private static final int TRANSPARENCY_MAX = 100;

    /** This returns moon tiles. */
    private static final String MOON_MAP_URL_FORMAT =
            "https://mw1.google.com/mw-planetary/lunar/lunarmaps_v1/clem_bw/%d/%d/%d.jpg";

    private TileOverlay mMoonTiles;
    private SeekBar mTransparencyBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tile_overlay_demo);

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
        map.setMapType(MapClient.MAP_TYPE_NONE);

        TileProvider tileProvider = MapKit
                .newUrlTileProvider(256, 256, new UrlTileProvider() {
                    @Override
                    public synchronized URL getTileUrl(int x, int y, int zoom) {
                        // The moon tile coordinate system is reversed.  This is not normal.
                        int reversedY = (1 << zoom) - y - 1;
                        String urlSpec =
                                String.format(Locale.US, MOON_MAP_URL_FORMAT, zoom, x, reversedY);
                        try {
                            return new URL(urlSpec);
                        } catch (MalformedURLException e) {
                            throw new AssertionError(e);
                        }
                    }
                });

        mMoonTiles = map.addTileOverlay(
                MapKit.newTileOverlayOptions().tileProvider(tileProvider));
        mTransparencyBar.setOnSeekBarChangeListener(this);
    }

    public void setFadeIn(View v) {
        if (mMoonTiles == null) {
            return;
        }
        mMoonTiles.setFadeIn(((CheckBox) v).isChecked());
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {}

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {}

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (mMoonTiles != null) {
            mMoonTiles.setTransparency((float) progress / (float) TRANSPARENCY_MAX);
        }
    }

}
