/*
 * Copyright (C) 2020 Supasin Tatiyanupanwong
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

package me.tatiyanupanwong.supasin.android.libraries.kits.maps.model;

/**
 * Joint types for {@link Polyline} and outline of {@link Polygon}.
 */
public final class JointType {

    /**
     * Default: Mitered joint, with fixed pointed extrusion equal to half the stroke width on
     * the outside of the joint.
     */
    public static final int DEFAULT = 0;

    /**
     * Flat bevel on the outside of the joint.
     */
    public static final int BEVEL = 1;

    /**
     * Rounded on the outside of the joint by an arc of radius equal to half the stroke width,
     * centered at the vertex.
     */
    public static final int ROUND = 2;


    private JointType() {}

}
