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

/**
 * A list of all the demos we have available.
 */
final class DemoDetailsList {

    /** This class should not be instantiated. */
    private DemoDetailsList() {}

    static final DemoDetails[] DEMOS = {
            new DemoDetails(R.string.basic_map_demo_label,
                    R.string.basic_map_demo_description,
                    BasicMapDemoActivity.class),
            new DemoDetails(R.string.camera_demo_label,
                    R.string.camera_demo_description,
                    CameraDemoActivity.class),
            new DemoDetails(R.string.camera_clamping_demo_label,
                    R.string.camera_clamping_demo_description,
                    CameraClampingDemoActivity.class),
            new DemoDetails(R.string.circle_demo_label,
                    R.string.circle_demo_description,
                    CircleDemoActivity.class),
            new DemoDetails(R.string.events_demo_label,
                    R.string.events_demo_description,
                    EventsDemoActivity.class),
            new DemoDetails(R.string.ground_overlay_demo_label,
                    R.string.ground_overlay_demo_description,
                    GroundOverlayDemoActivity.class),
            new DemoDetails(R.string.indoor_demo_label,
                    R.string.indoor_demo_description,
                    IndoorDemoActivity.class),
            new DemoDetails(R.string.layers_demo_label,
                    R.string.layers_demo_description,
                    LayersDemoActivity.class),
            new DemoDetails(R.string.location_source_demo_label,
                    R.string.location_source_demo_description,
                    LocationSourceDemoActivity.class),
            new DemoDetails(R.string.map_in_pager_demo_label,
                    R.string.map_in_pager_demo_description,
                    MapInPagerDemoActivity.class),
            new DemoDetails(R.string.marker_demo_label,
                    R.string.marker_demo_description,
                    MarkerDemoActivity.class),
            new DemoDetails(R.string.marker_close_info_window_on_retap_demo_label,
                    R.string.marker_close_info_window_on_retap_demo_description,
                    MarkerCloseInfoWindowOnRetapDemoActivity.class),
            new DemoDetails(R.string.multi_map_demo_label,
                    R.string.multi_map_demo_description,
                    MultiMapDemoActivity.class),
            new DemoDetails(R.string.my_location_demo_label,
                    R.string.my_location_demo_description,
                    MyLocationDemoActivity.class),
            new DemoDetails(R.string.polygon_demo_label,
                    R.string.polygon_demo_description,
                    PolygonDemoActivity.class),
            new DemoDetails(R.string.polyline_demo_label,
                    R.string.polyline_demo_description,
                    PolylineDemoActivity.class),
            new DemoDetails(R.string.programmatic_demo_label,
                    R.string.programmatic_demo_description,
                    ProgrammaticDemoActivity.class),
            new DemoDetails(R.string.retain_map_demo_label,
                    R.string.retain_map_demo_description,
                    RetainMapDemoActivity.class),
            new DemoDetails(R.string.snapshot_demo_label,
                    R.string.snapshot_demo_description,
                    SnapshotDemoActivity.class),
            new DemoDetails(R.string.styled_map_demo_label,
                    R.string.styled_map_demo_description,
                    StyledMapDemoActivity.class),
            new DemoDetails(R.string.tags_demo_label,
                    R.string.tags_demo_description,
                    TagsDemoActivity.class),
            new DemoDetails(R.string.tile_coordinate_demo_label,
                    R.string.tile_coordinate_demo_description,
                    TileCoordinateDemoActivity.class),
            new DemoDetails(R.string.tile_overlay_demo_label,
                    R.string.tile_overlay_demo_description,
                    TileOverlayDemoActivity.class),
            new DemoDetails(R.string.ui_settings_demo_label,
                    R.string.ui_settings_demo_description,
                    UiSettingsDemoActivity.class),
            new DemoDetails(R.string.visible_region_demo_label,
                    R.string.visible_region_demo_description,
                    VisibleRegionDemoActivity.class),
    };

}
