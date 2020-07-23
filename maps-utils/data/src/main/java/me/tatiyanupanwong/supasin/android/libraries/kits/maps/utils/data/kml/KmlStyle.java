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

import android.graphics.Color;

import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

import me.tatiyanupanwong.supasin.android.libraries.kits.maps.MapKit;
import me.tatiyanupanwong.supasin.android.libraries.kits.maps.model.Marker;
import me.tatiyanupanwong.supasin.android.libraries.kits.maps.model.Polygon;
import me.tatiyanupanwong.supasin.android.libraries.kits.maps.model.Polyline;
import me.tatiyanupanwong.supasin.android.libraries.kits.maps.utils.data.Style;

/**
 * Represents the defined styles in the KML document
 */
public class KmlStyle extends Style {

    private final static int HSV_VALUES = 3;

    private final static int HUE_VALUE = 0;

    private final static int INITIAL_SCALE = 1;

    private final HashMap<String, String> mBalloonOptions;

    private final HashSet<String> mStylesSet;

    private boolean mFill = true;

    private boolean mOutline = true;

    private String mIconUrl;

    private double mScale;

    private String mStyleId;

    private boolean mIconRandomColorMode;

    private boolean mLineRandomColorMode;

    private boolean mPolyRandomColorMode;

    @VisibleForTesting
    float mMarkerColor;

    /**
     * Creates a new KmlStyle object
     */
    KmlStyle() {
        super();
        mStyleId = null;
        mBalloonOptions = new HashMap<>();
        mStylesSet = new HashSet<>();
        mScale = INITIAL_SCALE;
        mMarkerColor = 0;
        mIconRandomColorMode = false;
        mLineRandomColorMode = false;
        mPolyRandomColorMode = false;
    }

    /**
     * Sets text found for an info window
     *
     * @param text Text for an info window
     */
    void setInfoWindowText(String text) {
        mBalloonOptions.put("text", text);
    }

    /**
     * Gets the id for the style
     *
     * @return Style Id, null otherwise
     */
    String getStyleId() {
        return mStyleId;
    }

    /**
     * Sets id for a style
     *
     * @param styleId Id for the style
     */
    void setStyleId(String styleId) {
        mStyleId = styleId;
    }

    /**
     * Checks if a given style (for a marker, linestring or polygon) has been set.
     *
     * @param style style to check if set
     * @return {@code true} if style was set, {@code false} otherwise
     */
    public boolean isStyleSet(String style) {
        return mStylesSet.contains(style);
    }

    /**
     * Gets whether the Polygon fill is set.
     *
     * @return {@code true} if there is a fill for the polygon, {@code false} otherwise
     */
    public boolean hasFill() {
        return mFill;
    }

    /**
     * Sets whether the Polygon has a fill.
     *
     * @param fill {@code true} if the polygon fill is set, {@code false} otherwise
     */
    public void setFill(boolean fill) {
        mFill = fill;
    }

    /**
     * Gets the scale for a marker icon
     *
     * @return scale value
     */
    public double getIconScale() {
        return mScale;
    }

    /**
     * Sets the scale for a marker icon
     *
     * @param scale scale value
     */
    void setIconScale(double scale) {
        mScale = scale;
        mStylesSet.add("iconScale");
    }

    /**
     * Gets whether the Polygon outline is set.
     *
     * @return {@code true} if the polygon outline is set, {@code false} otherwise
     */
    public boolean hasOutline() {
        return mOutline;
    }

    /**
     * Gets whether a BalloonStyle has been set.
     *
     * @return {@code true} if a BalloonStyle has been set, {@code false} otherwise
     */
    public boolean hasBalloonStyle() {
        return mBalloonOptions.size() > 0;
    }

    /**
     * Sets whether the Polygon has an outline.
     *
     * @param outline {@code true} if the polygon outline is set, {@code false} otherwise
     */
    void setOutline(boolean outline) {
        mOutline = outline;
        mStylesSet.add("outline");
    }

    /**
     * Gets the url for the marker icon
     *
     * @return Url for the marker icon, null otherwise
     */
    public String getIconUrl() {
        return mIconUrl;
    }

    /**
     * Sets the url for the marker icon
     *
     * @param iconUrl Url for the marker icon
     */
    void setIconUrl(String iconUrl) {
        mIconUrl = iconUrl;
        mStylesSet.add("iconUrl");
    }

    /**
     * Sets the fill color for a KML Polygon using a String
     *
     * @param color Fill color for a KML Polygon as a String
     */
    void setFillColor(String color) {
        // Add # to allow for mOutline color to be parsed correctly
        int polygonColorNum = (Color.parseColor("#" + convertColor(color)));
        setPolygonFillColor(polygonColorNum);
        mStylesSet.add("fillColor");
    }

    /**
     * Sets the color for a marker
     *
     * @param color Color for a marker
     */
    void setMarkerColor(String color) {
        int integerColor = Color.parseColor("#" + convertColor(color));
        mMarkerColor = getHueValue(integerColor);
        mMarkerOptions.icon(MapKit
                .getBitmapDescriptorFactory().defaultMarker(mMarkerColor));
        mStylesSet.add("markerColor");
    }

    /**
     * Gets the hue value from a color
     *
     * @param integerColor Integer representation of a color
     * @return Hue value from a color
     */
    private static float getHueValue(int integerColor) {
        float[] hsvValues = new float[HSV_VALUES];
        Color.colorToHSV(integerColor, hsvValues);
        return hsvValues[HUE_VALUE];
    }

    /**
     * Converts a color format of the form AABBGGRR to AARRGGBB. Any leading or trailing spaces
     * in the provided string will be trimmed prior to conversion.
     *
     * @param color Color of the form AABBGGRR
     * @return Color of the form AARRGGBB
     */
    private static String convertColor(String color) {
        // Tolerate KML with leading or trailing whitespace in colors
        color = color.trim();
        String newColor;
        if (color.length() > 6) {
            newColor = color.substring(0, 2) + color.substring(6, 8)
                    + color.substring(4, 6) + color.substring(2, 4);
        } else {
            newColor = color.substring(4, 6) + color.substring(2, 4) +
                    color.substring(0, 2);
        }
        return newColor;
    }

    /**
     * Sets the rotation / heading for a marker
     *
     * @param heading Decimal representation of a rotation value
     */
    void setHeading(float heading) {
        setMarkerRotation(heading);
        mStylesSet.add("heading");
    }

    /**
     * Sets the hotspot / anchor point of a marker
     *
     * @param x      x point of a marker position
     * @param y      y point of a marker position
     * @param xUnits units in which the x value is specified
     * @param yUnits units in which the y value is specified
     */
    void setHotSpot(float x, float y, String xUnits, String yUnits) {
        setMarkerHotSpot(x, y, xUnits, yUnits);
        mStylesSet.add("hotSpot");
    }

    /**
     * Sets the color mode for a marker. A "random" color mode sets the color mode to true,
     * a "normal" colormode sets the color mode to false.
     *
     * @param colorMode A "random" or "normal" color mode
     */
    void setIconColorMode(String colorMode) {
        mIconRandomColorMode = colorMode.equals("random");
        mStylesSet.add("iconColorMode");
    }

    /**
     * Checks whether the color mode for a marker is true / random.
     *
     * @return {@code true} if the color mode is true, {@code false} otherwise
     */
    boolean isIconRandomColorMode() {
        return mIconRandomColorMode;
    }

    /**
     * Sets the color mode for a polyline. A "random" color mode sets the color mode to true,
     * a "normal" colormode sets the color mode to false.
     *
     * @param colorMode A "random" or "normal" color mode
     */
    void setLineColorMode(String colorMode) {
        mLineRandomColorMode = colorMode.equals("random");
        mStylesSet.add("lineColorMode");
    }

    /**
     * Checks whether the color mode for a polyline is true / random.
     *
     * @return {@code true} if the color mode is true, {@code false} otherwise
     */
    public boolean isLineRandomColorMode() {
        return mLineRandomColorMode;
    }

    /**
     * Sets the color mode for a polygon. A "random" color mode sets the color mode to true,
     * a "normal" colormode sets the color mode to false.
     *
     * @param colorMode A "random" or "normal" color mode
     */
    void setPolyColorMode(String colorMode) {
        mPolyRandomColorMode = colorMode.equals("random");
        mStylesSet.add("polyColorMode");
    }

    /**
     * Checks whether the color mode for a polygon is true / random.
     *
     * @return {@code true} if the color mode is true, {@code false} otherwise
     */
    /* package */
    public boolean isPolyRandomColorMode() {
        return mPolyRandomColorMode;
    }

    /**
     * Sets the outline color for a Polyline and a Polygon using a String
     *
     * @param color Outline color for a Polyline and a Polygon represented as a String
     */
    void setOutlineColor(String color) {
        // Add # to allow for mOutline color to be parsed correctly
        mPolylineOptions.color(Color.parseColor("#" + convertColor(color)));
        mPolygonOptions.strokeColor(Color.parseColor("#" + convertColor(color)));
        mStylesSet.add("outlineColor");
    }

    /**
     * Sets the line width for a Polyline and a Polygon
     *
     * @param width Line width for a Polyline and a Polygon
     */
    void setWidth(Float width) {
        setLineStringWidth(width);
        setPolygonStrokeWidth(width);
        mStylesSet.add("width");
    }

    /**
     * Gets the balloon options
     *
     * @return Balloon Options
     */
    public HashMap<String, String> getBalloonOptions() {
        return mBalloonOptions;
    }

    /**
     * Creates a new marker option from given properties of an existing marker option.
     *
     * @param originalMarkerOption An existing MarkerOption instance
     * @param iconRandomColorMode  {@code true} if marker color mode is random, {@code false} otherwise
     * @param markerColor          Color of the marker
     * @return A new MarkerOption
     */
    private static Marker.Options createMarkerOptions(
            Marker.Options originalMarkerOption,
            boolean iconRandomColorMode,
            float markerColor) {
        Marker.Options newMarkerOption = MapKit.newMarkerOptions();
        newMarkerOption.rotation(originalMarkerOption.getRotation());
        newMarkerOption.anchor(originalMarkerOption.getAnchorU(), originalMarkerOption.getAnchorV());
        if (iconRandomColorMode) {
            float hue = getHueValue(computeRandomColor((int) markerColor));
            originalMarkerOption.icon(MapKit
                    .getBitmapDescriptorFactory().defaultMarker(hue));
        }
        newMarkerOption.icon(originalMarkerOption.getIcon());
        return newMarkerOption;
    }

    /**
     * Creates a new PolylineOption from given properties of an existing PolylineOption
     *
     * @param originalPolylineOption An existing PolylineOption instance
     * @return A new PolylineOption
     */
    private static Polyline.Options createPolylineOptions(Polyline.Options originalPolylineOption) {
        Polyline.Options polylineOptions = MapKit.newPolylineOptions();
        polylineOptions.color(originalPolylineOption.getColor());
        polylineOptions.width(originalPolylineOption.getWidth());
        polylineOptions.clickable(originalPolylineOption.isClickable());
        return polylineOptions;
    }

    /**
     * Creates a new PolygonOption from given properties of an existing PolygonOption
     *
     * @param originalPolygonOption An existing PolygonOption instance
     * @param isFill                Whether the fill for a polygon is set
     * @param isOutline             Whether the outline for a polygon is set
     * @return A new PolygonOption
     */
    private static Polygon.Options createPolygonOptions(
            Polygon.Options originalPolygonOption,
            boolean isFill,
            boolean isOutline) {
        Polygon.Options polygonOptions = MapKit.newPolygonOptions();
        if (isFill) {
            polygonOptions.fillColor(originalPolygonOption.getFillColor());
        }
        if (isOutline) {
            polygonOptions.strokeColor(originalPolygonOption.getStrokeColor());
            polygonOptions.strokeWidth(originalPolygonOption.getStrokeWidth());
        }
        polygonOptions.clickable(originalPolygonOption.isClickable());
        return polygonOptions;
    }

    /**
     * Gets a MarkerOption
     *
     * @return A new MarkerOption
     */
    public Marker.Options getMarkerOptions() {
        return createMarkerOptions(mMarkerOptions, isIconRandomColorMode(), mMarkerColor);
    }

    /**
     * Gets a PolylineOption
     *
     * @return new PolylineOptions
     */
    public Polyline.Options getPolylineOptions() {
        return createPolylineOptions(mPolylineOptions);
    }

    /**
     * Gets a PolygonOption
     *
     * @return new PolygonOptions
     */
    public Polygon.Options getPolygonOptions() {
        return createPolygonOptions(mPolygonOptions, mFill, mOutline);
    }

    /**
     * Computes a random color given an integer. Algorithm to compute the random color can be
     * found in https://developers.google.com/kml/documentation/kmlreference#colormode
     *
     * @param color Color represented as an integer
     * @return Integer representing a random color
     */
    public static int computeRandomColor(int color) {
        Random random = new Random();
        int red = Color.red(color);
        int green = Color.green(color);
        int blue = Color.blue(color);
        //Random number can only be computed in range [0, n)
        if (red != 0) {
            red = random.nextInt(red);
        }
        if (blue != 0) {
            blue = random.nextInt(blue);
        }
        if (green != 0) {
            green = random.nextInt(green);
        }
        return Color.rgb(red, green, blue);
    }

    @NonNull
    @Override
    public String toString() {
        return "Style" + "{" +
                "\n balloon options=" + mBalloonOptions +
                ",\n fill=" + mFill +
                ",\n outline=" + mOutline +
                ",\n icon url=" + mIconUrl +
                ",\n scale=" + mScale +
                ",\n style id=" + mStyleId +
                "\n}\n";
    }
}
