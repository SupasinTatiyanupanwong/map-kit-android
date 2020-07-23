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

package me.tatiyanupanwong.supasin.android.libraries.kits.maps.utils.data.geojson;

import androidx.annotation.NonNull;

import java.util.Arrays;
import java.util.List;

import me.tatiyanupanwong.supasin.android.libraries.kits.maps.MapKit;
import me.tatiyanupanwong.supasin.android.libraries.kits.maps.model.PatternItem;
import me.tatiyanupanwong.supasin.android.libraries.kits.maps.model.Polyline;
import me.tatiyanupanwong.supasin.android.libraries.kits.maps.utils.data.Style;

/**
 * A class that allows for GeoJsonLineString objects to be styled and for these styles to be
 * translated into a PolylineOptions object.
 */
public class GeoJsonLineStringStyle extends Style implements GeoJsonStyle {

    private final static String[] GEOMETRY_TYPE = {"LineString", "MultiLineString",
            "GeometryCollection"};

    /**
     * Creates a new LineStringStyle object
     */
    public GeoJsonLineStringStyle() {
        mPolylineOptions = MapKit.newPolylineOptions();
        mPolylineOptions.clickable(true);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String[] getGeometryType() {
        return GEOMETRY_TYPE;
    }

    /**
     * Gets the color of the GeoJsonLineString as a 32-bit ARGB color
     *
     * @return color of the GeoJsonLineString
     */
    public int getColor() {
        return mPolylineOptions.getColor();
    }

    /**
     * Sets the color of the GeoJsonLineString as a 32-bit ARGB color
     *
     * @param color color value of the GeoJsonLineString
     */
    public void setColor(int color) {
        mPolylineOptions.color(color);
        styleChanged();
    }

    /**
     * Gets the clickability setting for this Options object.
     *
     * @return {@code true} if the GeoJsonLineString is clickable; {@code false} if it is not
     */
    public boolean isClickable() {
        return mPolylineOptions.isClickable();
    }

    /**
     * Specifies whether this GeoJsonLineString is clickable
     *
     * @param clickable - new clickability setting for the GeoJsonLineString
     */
    public void setClickable(boolean clickable) {
        mPolylineOptions.clickable(clickable);
        styleChanged();
    }

    /**
     * Gets whether the GeoJsonLineString is geodesic.
     *
     * @return {@code true} if GeoJsonLineString is geodesic, {@code false} otherwise
     */
    public boolean isGeodesic() {
        return mPolylineOptions.isGeodesic();
    }

    /**
     * Sets whether the GeoJsonLineString is geodesic.
     *
     * @param geodesic {@code true} if GeoJsonLineString is geodesic, {@code false} otherwise
     */
    public void setGeodesic(boolean geodesic) {
        mPolylineOptions.geodesic(geodesic);
        styleChanged();
    }

    /**
     * Gets the width of the GeoJsonLineString in screen pixels
     *
     * @return width of the GeoJsonLineString
     */
    public float getWidth() {
        return mPolylineOptions.getWidth();
    }

    /**
     * Sets the width of the GeoJsonLineString in screen pixels
     *
     * @param width width value of the GeoJsonLineString
     */
    public void setWidth(float width) {
        setLineStringWidth(width);
        styleChanged();
    }

    /**
     * Gets the z index of the GeoJsonLineString
     *
     * @return z index of the GeoJsonLineString
     */
    public float getZIndex() {
        return mPolylineOptions.getZIndex();
    }

    /**
     * Sets the z index of the GeoJsonLineString
     *
     * @param zIndex z index value of the GeoJsonLineString
     */
    public void setZIndex(float zIndex) {
        mPolylineOptions.zIndex(zIndex);
        styleChanged();
    }

    /**
     * Gets whether the GeoJsonLineString is visible.
     *
     * @return {@code true} if the GeoJsonLineString visible, {@code false} if not visible
     */
    @Override
    public boolean isVisible() {
        return mPolylineOptions.isVisible();
    }

    /**
     * Sets whether the GeoJsonLineString is visible.
     *
     * @param visible {@code true} if the GeoJsonLineString is visible, {@code false} if not visible
     */
    @Override
    public void setVisible(boolean visible) {
        mPolylineOptions.visible(visible);
        styleChanged();
    }

    /**
     * Notifies the observers, GeoJsonFeature objects, that the style has changed. Indicates to the
     * GeoJsonFeature that it should check whether a redraw is needed for the feature.
     */
    private void styleChanged() {
        setChanged();
        notifyObservers();
    }

    /**
     * Gets a new PolylineOptions object containing styles for the GeoJsonLineString
     *
     * @return new PolylineOptions object
     */
    public Polyline.Options toPolylineOptions() {
        Polyline.Options polylineOptions = MapKit.newPolylineOptions();
        polylineOptions.color(mPolylineOptions.getColor());
        polylineOptions.clickable(mPolylineOptions.isClickable());
        polylineOptions.geodesic(mPolylineOptions.isGeodesic());
        polylineOptions.visible(mPolylineOptions.isVisible());
        polylineOptions.width(mPolylineOptions.getWidth());
        polylineOptions.zIndex(mPolylineOptions.getZIndex());
        polylineOptions.pattern(getPattern());
        return polylineOptions;
    }

    @NonNull
    @Override
    public String toString() {
        return "LineStringStyle{" + "\n geometry type=" + Arrays.toString(GEOMETRY_TYPE) +
                ",\n color=" + getColor() +
                ",\n clickable=" + isClickable() +
                ",\n geodesic=" + isGeodesic() +
                ",\n visible=" + isVisible() +
                ",\n width=" + getWidth() +
                ",\n z index=" + getZIndex() +
                ",\n pattern=" + getPattern() +
                "\n}\n";
    }

    /**
     * Gets the pattern of the GeoJsonLineString
     *
     * @return line style of GeoJsonLineString
     */
    public List<PatternItem> getPattern() {
        return mPolylineOptions.getPattern();
    }

    /**
     * Sets the pattern of the GeoJsonLineString
     *
     * @param pattern line style of GeoJsonLineString
     */
    public void setPattern(List<PatternItem> pattern) {
        mPolylineOptions.pattern(pattern);
        styleChanged();
    }

}
