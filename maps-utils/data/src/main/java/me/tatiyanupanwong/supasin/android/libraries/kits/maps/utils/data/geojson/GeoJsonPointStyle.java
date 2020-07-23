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

import me.tatiyanupanwong.supasin.android.libraries.kits.maps.MapKit;
import me.tatiyanupanwong.supasin.android.libraries.kits.maps.model.BitmapDescriptor;
import me.tatiyanupanwong.supasin.android.libraries.kits.maps.model.Marker;
import me.tatiyanupanwong.supasin.android.libraries.kits.maps.utils.data.Style;

/**
 * A class that allows for GeoJsonPoint objects to be styled and for these styles to be translated
 * into a MarkerOptions object.
 */
public class GeoJsonPointStyle extends Style implements GeoJsonStyle {

    private final static String[] GEOMETRY_TYPE = {"Point", "MultiPoint", "GeometryCollection"};

    /**
     * Creates a new PointStyle object
     */
    public GeoJsonPointStyle() {
        mMarkerOptions = MapKit.newMarkerOptions();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String[] getGeometryType() {
        return GEOMETRY_TYPE;
    }

    /**
     * Gets the alpha of the GeoJsonPoint. This is a value from 0 to 1, where 0 means the marker is
     * completely transparent and 1 means the marker is completely opaque.
     *
     * @return alpha of the GeoJsonPoint
     */
    public float getAlpha() {
        return mMarkerOptions.getAlpha();
    }

    /**
     * Sets the alpha of the GeoJsonPoint. This is a value from 0 to 1, where 0 means the marker is
     * completely transparent and 1 means the marker is completely opaque.
     *
     * @param alpha alpha value of the GeoJsonPoint
     */
    public void setAlpha(float alpha) {
        mMarkerOptions.alpha(alpha);
        styleChanged();
    }

    /**
     * Gets the Anchor U coordinate of the GeoJsonPoint. Normalized to [0, 1], of the anchor from
     * the left edge. This is equivalent to the same U value used in {@link
     * Marker.Options#getAnchorU()}.
     *
     * @return Anchor U coordinate of the GeoJsonPoint
     */
    public float getAnchorU() {
        return mMarkerOptions.getAnchorU();
    }

    /**
     * Gets the Anchor V coordinate of the GeoJsonPoint. Normalized to [0, 1], of the anchor from
     * the top edge. This is equivalent to the same V value used in {@link
     * Marker.Options#getAnchorV()}.
     *
     * @return Anchor V coordinate of the GeoJsonPoint
     */
    public float getAnchorV() {
        return mMarkerOptions.getAnchorV();
    }

    /**
     * Sets the Anchor U and V coordinates of the GeoJsonPoint. The anchor point is specified in
     * the continuous space [0.0, 1.0] x [0.0, 1.0], where (0, 0) is the top-left corner of the
     * image, and (1, 1) is the bottom-right corner. The U &amp; V values are the same U &amp; V
     * values used in {@link Marker.Options#anchor(float, float)}.
     *
     * @param anchorU Anchor U coordinate of the GeoJsonPoint
     * @param anchorV Anchor V coordinate of the GeoJsonPoint
     */
    public void setAnchor(float anchorU, float anchorV) {
        setMarkerHotSpot(anchorU, anchorV, "fraction", "fraction");
        styleChanged();
    }

    /**
     * Gets whether the GeoJsonPoint is draggable.
     *
     * @return {@code true} if GeoJsonPoint is draggable, {@code false} if not draggable
     */
    public boolean isDraggable() {
        return mMarkerOptions.isDraggable();
    }

    /**
     * Sets the GeoJsonPoint to be draggable.
     *
     * @param draggable {@code true} if GeoJsonPoint is draggable, {@code false} if not draggable
     */
    public void setDraggable(boolean draggable) {
        mMarkerOptions.draggable(draggable);
        styleChanged();
    }

    /**
     * Gets whether the GeoJsonPoint is flat.
     *
     * @return {@code true} if GeoJsonPoint is flat, {@code false} if not flat
     */
    public boolean isFlat() {
        return mMarkerOptions.isFlat();
    }

    /**
     * Sets the GeoJsonPoint to be flat.
     *
     * @param flat {@code true} if GeoJsonPoint is flat, {@code false} if not flat
     */
    public void setFlat(boolean flat) {
        mMarkerOptions.flat(flat);
        styleChanged();
    }

    /**
     * Gets a bitmap image for the GeoJsonPoint
     *
     * @return bitmap descriptor for the GeoJsonPoint
     */
    public BitmapDescriptor getIcon() {
        return mMarkerOptions.getIcon();
    }

    /**
     * Sets a bitmap image for the GeoJsonPoint
     *
     * @param bitmap bitmap descriptor for the GeoJsonPoint
     */
    public void setIcon(BitmapDescriptor bitmap) {
        mMarkerOptions.icon(bitmap);
        styleChanged();
    }

    /**
     * Gets the info window anchor U coordinate of the GeoJsonPoint. Normalized to [0, 1], of the
     * info window anchor from the left edge. This is equivalent to the same U value used in {@link
     * Marker.Options#getInfoWindowAnchorU()}.
     *
     * @return info window anchor U coordinate of the GeoJsonPoint
     */
    public float getInfoWindowAnchorU() {
        return mMarkerOptions.getInfoWindowAnchorU();
    }

    /**
     * Gets the info window anchor V coordinate of the GeoJsonPoint. Normalized to [0, 1], of the
     * info window anchor from the top edge. This is equivalent to the same V value used in {@link
     * Marker.Options#getInfoWindowAnchorV()}.
     *
     * @return info window anchor V coordinate of the GeoJsonPoint
     */
    public float getInfoWindowAnchorV() {
        return mMarkerOptions.getInfoWindowAnchorV();
    }

    /**
     * Sets the info window anchor U and V coordinates of the GeoJsonPoint. This is specified in
     * the same coordinate system as the anchor. The U &amp; V values are the same U &amp; V values
     * used in {@link Marker.Options(float, float)}.
     *
     * @param infoWindowAnchorU info window anchor U coordinate of the GeoJsonPoint
     * @param infoWindowAnchorV info window anchor V coordinate of the GeoJsonPoint
     */
    public void setInfoWindowAnchor(float infoWindowAnchorU, float infoWindowAnchorV) {
        mMarkerOptions.infoWindowAnchor(infoWindowAnchorU, infoWindowAnchorV);
        styleChanged();
    }

    /**
     * Gets the rotation of the GeoJsonPoint in degrees clockwise about the marker's anchor point
     *
     * @return rotation of the GeoJsonPoint
     */
    public float getRotation() {
        return mMarkerOptions.getRotation();
    }


    /**
     * Sets the rotation of the GeoJsonPoint in degrees clockwise about the marker's anchor point
     *
     * @param rotation rotation value of the GeoJsonPoint
     */
    public void setRotation(float rotation) {
        setMarkerRotation(rotation);
        styleChanged();
    }

    /**
     * Gets the snippet of the GeoJsonPoint
     *
     * @return snippet of the GeoJsonPoint
     */
    public String getSnippet() {
        return mMarkerOptions.getSnippet();
    }

    /**
     * Sets the snippet of the GeoJsonPoint
     *
     * @param snippet sets the snippet value of the GeoJsonPoint
     */
    public void setSnippet(String snippet) {
        mMarkerOptions.snippet(snippet);
        styleChanged();
    }

    /**
     * Gets the title of the GeoJsonPoint
     *
     * @return title of the GeoJsonPoint
     */
    public String getTitle() {
        return mMarkerOptions.getTitle();
    }

    /**
     * Sets the title of the GeoJsonPoint
     *
     * @param title title value of the GeoJsonPoint
     */
    public void setTitle(String title) {
        mMarkerOptions.title(title);
        styleChanged();
    }

    /**
     * Gets whether the GeoJsonPoint is visible.
     *
     * @return {@code true} if GeoJsonPoint is visible, {@code false} if not visible
     */
    @Override
    public boolean isVisible() {
        return mMarkerOptions.isVisible();
    }

    /**
     * Sets whether the GeoJsonPoint is visible.
     *
     * @param visible {@code true} if GeoJsonPoint is visible, {@code false} if not visible
     */
    @Override
    public void setVisible(boolean visible) {
        mMarkerOptions.visible(visible);
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
     * Gets a new MarkerOptions object containing styles for the GeoJsonPoint
     *
     * @return new MarkerOptions object
     */
    public Marker.Options toMarkerOptions() {
        Marker.Options markerOptions = MapKit.newMarkerOptions();
        markerOptions.alpha(mMarkerOptions.getAlpha());
        markerOptions.anchor(mMarkerOptions.getAnchorU(), mMarkerOptions.getAnchorV());
        markerOptions.draggable(mMarkerOptions.isDraggable());
        markerOptions.flat(mMarkerOptions.isFlat());
        markerOptions.icon(mMarkerOptions.getIcon());
        markerOptions.infoWindowAnchor(mMarkerOptions.getInfoWindowAnchorU(),
                mMarkerOptions.getInfoWindowAnchorV());
        markerOptions.rotation(mMarkerOptions.getRotation());
        markerOptions.snippet(mMarkerOptions.getSnippet());
        markerOptions.title(mMarkerOptions.getTitle());
        markerOptions.visible(mMarkerOptions.isVisible());
        markerOptions.zIndex(mMarkerOptions.getZIndex());
        return markerOptions;
    }

    @NonNull
    @Override
    public String toString() {
        return "PointStyle{" + "\n geometry type=" + Arrays.toString(GEOMETRY_TYPE) +
                ",\n alpha=" + getAlpha() +
                ",\n anchor U=" + getAnchorU() +
                ",\n anchor V=" + getAnchorV() +
                ",\n draggable=" + isDraggable() +
                ",\n flat=" + isFlat() +
                ",\n info window anchor U=" + getInfoWindowAnchorU() +
                ",\n info window anchor V=" + getInfoWindowAnchorV() +
                ",\n rotation=" + getRotation() +
                ",\n snippet=" + getSnippet() +
                ",\n title=" + getTitle() +
                ",\n visible=" + isVisible() +
                ",\n z index=" + getZIndex() +
                "\n}\n";
    }

    /**
     * Gets the z index of the GeoJsonLineString
     *
     * @return z index of the GeoJsonLineString
     */
    public float getZIndex() {
        return mMarkerOptions.getZIndex();
    }

    /**
     * Sets the z index of the GeoJsonLineString
     *
     * @param zIndex z index value of the GeoJsonPoint
     */
    public void setZIndex(float zIndex) {
        mMarkerOptions.zIndex(zIndex);
        styleChanged();
    }

}
