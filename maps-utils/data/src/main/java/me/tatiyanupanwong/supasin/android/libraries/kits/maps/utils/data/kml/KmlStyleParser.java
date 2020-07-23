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

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.HashMap;

import static org.xmlpull.v1.XmlPullParser.END_TAG;
import static org.xmlpull.v1.XmlPullParser.START_TAG;

/**
 * Parses the styles of a given KML file into a KmlStyle object
 */
class KmlStyleParser {

    private final static String STYLE_TAG = "styleUrl";

    private final static String ICON_STYLE_HEADING = "heading";

    private final static String ICON_STYLE_URL = "Icon";

    private final static String ICON_STYLE_SCALE = "scale";

    private final static String ICON_STYLE_HOTSPOT = "hotSpot";

    private final static String COLOR_STYLE_COLOR = "color";

    private final static String COLOR_STYLE_MODE = "colorMode";

    private final static String STYLE_MAP_KEY = "key";

    private final static String STYLE_MAP_NORMAL_STYLE = "normal";

    private final static String LINE_STYLE_WIDTH = "width";

    private final static String POLY_STYLE_OUTLINE = "outline";

    private final static String POLY_STYLE_FILL = "fill";

    /**
     * Parses the IconStyle, LineStyle and PolyStyle tags into a KmlStyle object
     */
    /* package */
    static KmlStyle createStyle(XmlPullParser parser)
            throws IOException, XmlPullParserException {
        KmlStyle styleProperties = new KmlStyle();
        setStyleId(parser.getAttributeValue(null, "id"), styleProperties);
        int eventType = parser.getEventType();
        while (!(eventType == END_TAG && parser.getName().equals("Style"))) {
            if (eventType == START_TAG) {
                switch (parser.getName()) {
                    case "IconStyle":
                        createIconStyle(parser, styleProperties);
                        break;
                    case "LineStyle":
                        createLineStyle(parser, styleProperties);
                        break;
                    case "PolyStyle":
                        createPolyStyle(parser, styleProperties);
                        break;
                    case "BalloonStyle":
                        createBalloonStyle(parser, styleProperties);
                        break;
                }
            }
            eventType = parser.next();
        }
        return styleProperties;
    }

    private static void setStyleId(String id, KmlStyle styleProperties) {
        if (id != null) {
            // Append # to a local styleUrl
            String styleId = "#" + id;
            styleProperties.setStyleId(styleId);
        }
    }

    /**
     * Receives input from an XMLPullParser and assigns relevant properties to a KmlStyle.
     *
     * @param style Style to apply properties to
     */
    private static void createIconStyle(XmlPullParser parser, KmlStyle style)
            throws XmlPullParserException, IOException {
        int eventType = parser.getEventType();
        while (!(eventType == END_TAG && parser.getName().equals("IconStyle"))) {
            if (eventType == START_TAG) {
                switch (parser.getName()) {
                    case ICON_STYLE_HEADING:
                        style.setHeading(Float.parseFloat(parser.nextText()));
                        break;
                    case ICON_STYLE_URL:
                        setIconUrl(parser, style);
                        break;
                    case ICON_STYLE_HOTSPOT:
                        setIconHotSpot(parser, style);
                        break;
                    case ICON_STYLE_SCALE:
                        style.setIconScale(Double.parseDouble(parser.nextText()));
                        break;
                    case COLOR_STYLE_COLOR:
                        style.setMarkerColor(parser.nextText());
                        break;
                    case COLOR_STYLE_MODE:
                        style.setIconColorMode(parser.nextText());
                        break;
                }
            }
            eventType = parser.next();
        }
    }

    /**
     * Parses the StyleMap property and stores the id and the normal style tag
     */
    /* package */
    static HashMap<String, String> createStyleMap(XmlPullParser parser)
            throws XmlPullParserException, IOException {
        HashMap<String, String> styleMaps = new HashMap<>();
        // Indicates if a normal style is to be stored
        boolean isNormalStyleMapValue = false;
        // Append # to style id
        String styleId = "#" + parser.getAttributeValue(null, "id");
        int eventType = parser.getEventType();
        while (!(eventType == END_TAG && parser.getName().equals("StyleMap"))) {
            if (eventType == START_TAG) {
                if (parser.getName().equals(STYLE_MAP_KEY)
                        && parser.nextText().equals(STYLE_MAP_NORMAL_STYLE)) {
                    isNormalStyleMapValue = true;
                } else if (parser.getName().equals(STYLE_TAG) && isNormalStyleMapValue) {
                    styleMaps.put(styleId, parser.nextText());
                    isNormalStyleMapValue = false;
                }
            }
            eventType = parser.next();
        }
        return styleMaps;
    }

    /**
     * Sets relevant styling properties to the KmlStyle object that are found in the IconStyle tag
     * Supported tags include scale, heading, Icon, href, hotSpot
     *
     * @param style Style object to add properties to
     */
    private static void createBalloonStyle(XmlPullParser parser, KmlStyle style)
            throws XmlPullParserException, IOException {
        int eventType = parser.getEventType();
        while (!(eventType == END_TAG && parser.getName().equals("BalloonStyle"))) {
            if (eventType == START_TAG && parser.getName().equals("text")) {
                style.setInfoWindowText(parser.nextText());
            }
            eventType = parser.next();
        }
    }

    /**
     * Sets the icon url for the style
     *
     * @param style Style to set the icon url to
     */
    private static void setIconUrl(XmlPullParser parser, KmlStyle style)
            throws XmlPullParserException, IOException {
        int eventType = parser.getEventType();
        while (!(eventType == END_TAG && parser.getName().equals(ICON_STYLE_URL))) {
            if (eventType == START_TAG && parser.getName().equals("href")) {
                style.setIconUrl(parser.nextText());
            }
            eventType = parser.next();
        }
    }

    /**
     * Sets the hot spot for the icon
     *
     * @param style Style object to apply hotspot properties to
     */
    private static void setIconHotSpot(XmlPullParser parser, KmlStyle style)
            throws XmlPullParserException {
        if (parser.isEmptyElementTag()) {
            return;
        }
        float xValue, yValue;
        String xUnits, yUnits;
        xValue = Float.parseFloat(parser.getAttributeValue(null, "x"));
        yValue = Float.parseFloat(parser.getAttributeValue(null, "y"));
        xUnits = parser.getAttributeValue(null, "xunits");
        yUnits = parser.getAttributeValue(null, "yunits");
        style.setHotSpot(xValue, yValue, xUnits, yUnits);
    }

    /**
     * Sets relevant styling properties to the KmlStyle object that are found in the LineStyle tag
     * Supported tags include color, width
     *
     * @param style Style object to add properties to
     */
    private static void createLineStyle(XmlPullParser parser, KmlStyle style)
            throws XmlPullParserException, IOException {
        int eventType = parser.getEventType();
        while (!(eventType == END_TAG && parser.getName().equals("LineStyle"))) {
            if (eventType == START_TAG) {
                switch (parser.getName()) {
                    case COLOR_STYLE_COLOR:
                        style.setOutlineColor(parser.nextText());
                        break;
                    case LINE_STYLE_WIDTH:
                        style.setWidth(Float.valueOf(parser.nextText()));
                        break;
                    case COLOR_STYLE_MODE:
                        style.setLineColorMode(parser.nextText());
                        break;
                }
            }
            eventType = parser.next();
        }
    }

    /**
     * Sets relevant styling properties to the KmlStyle object that are found in the PolyStyle tag
     * Supported tags include color, outline, fill
     *
     * @param style Style object to add properties to
     */
    private static void createPolyStyle(XmlPullParser parser, KmlStyle style)
            throws XmlPullParserException, IOException {
        int eventType = parser.getEventType();
        while (!(eventType == END_TAG && parser.getName().equals("PolyStyle"))) {
            if (eventType == START_TAG) {
                switch (parser.getName()) {
                    case COLOR_STYLE_COLOR:
                        style.setFillColor(parser.nextText());
                        break;
                    case POLY_STYLE_OUTLINE:
                        style.setOutline(KmlBoolean.parseBoolean(parser.nextText()));
                        break;
                    case POLY_STYLE_FILL:
                        style.setFill(KmlBoolean.parseBoolean(parser.nextText()));
                        break;
                    case COLOR_STYLE_MODE:
                        style.setPolyColorMode(parser.nextText());
                        break;
                }
            }
            eventType = parser.next();
        }
    }

}
