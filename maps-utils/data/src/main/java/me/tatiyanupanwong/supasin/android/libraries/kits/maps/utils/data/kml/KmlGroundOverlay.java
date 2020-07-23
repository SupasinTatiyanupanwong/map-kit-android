/*
 * Copyright (C) 2020 Supasin Tatiyanupanwong
 * Copyright (C) 2020 Google Inc.
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

package me.tatiyanupanwong.supasin.android.libraries.kits.maps.utils.data.kml;

import androidx.annotation.NonNull;

import java.util.HashMap;
import java.util.Map;

import me.tatiyanupanwong.supasin.android.libraries.kits.maps.MapKit;
import me.tatiyanupanwong.supasin.android.libraries.kits.maps.model.GroundOverlay;
import me.tatiyanupanwong.supasin.android.libraries.kits.maps.model.LatLngBounds;

/**
 * Represents a KML Ground Overlay
 */
public class KmlGroundOverlay {

    private final Map<String, String> mProperties;

    private final GroundOverlay.Options mGroundOverlayOptions;

    private final String mImageUrl;

    private LatLngBounds mLatLngBox;

    /**
     * Creates a new Ground Overlay.
     *
     * @param imageUrl   url of the ground overlay image
     * @param latLonBox  bounds of the image
     * @param drawOrder  z index of the image
     * @param visibility {@code true} if visible, {@code false} otherwise
     * @param properties properties hashmap
     * @param rotation   rotation of image
     */
    KmlGroundOverlay(String imageUrl, LatLngBounds latLonBox, float drawOrder, int visibility,
            HashMap<String, String> properties, float rotation) {
        mGroundOverlayOptions = MapKit.newGroundOverlayOptions();
        mImageUrl = imageUrl;
        mProperties = properties;
        if (latLonBox == null) {
            throw new IllegalArgumentException("No LatLonBox given");
        }
        mLatLngBox = latLonBox;
        mGroundOverlayOptions.positionFromBounds(latLonBox);
        mGroundOverlayOptions.bearing(rotation);
        mGroundOverlayOptions.zIndex(drawOrder);
        mGroundOverlayOptions.visible(visibility != 0);
    }

    /**
     * Gets an image url
     *
     * @return An image url
     */
    public String getImageUrl() {
        return mImageUrl;
    }

    /**
     * Returns boundaries of the ground overlay
     *
     * @return Boundaries of the ground overlay
     */
    public LatLngBounds getLatLngBox() {
        return mLatLngBox;
    }

    /**
     * Gets an iterable of the properties
     *
     * @return Iterable of the properties
     */
    public Iterable<String> getProperties() {
        return mProperties.keySet();
    }

    /**
     * Gets a property value
     *
     * @param keyValue key value of the property
     * @return Value of property
     */
    public String getProperty(String keyValue) {
        return mProperties.get(keyValue);
    }

    /**
     * Returns a boolean value determining whether the ground overlay has a property.
     *
     * @param keyValue Value to retrieve
     * @return {@code true} if the property exists, {@code false} otherwise
     */
    public boolean hasProperty(String keyValue) {
        return mProperties.get(keyValue) != null;
    }

    /**
     * Gets the ground overlay option of the ground overlay on the map
     *
     * @return GroundOverlayOptions
     */
    GroundOverlay.Options getGroundOverlayOptions() {
        return mGroundOverlayOptions;
    }

    @NonNull
    @Override
    public String toString() {
        return "GroundOverlay" + "{" +
                "\n properties=" + mProperties +
                ",\n image url=" + mImageUrl +
                ",\n LatLngBox=" + mLatLngBox +
                "\n}\n";
    }
}
