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

import androidx.annotation.NonNull;

/**
 * Bitmap overlay centered at the start or end vertex of a {@link Polyline}, orientated according
 * to the direction of the line's first or last edge and scaled with respect to the line's stroke
 * width. {@code CustomCap} can be applied to {@code Polyline} with any stroke pattern.
 */
public interface CustomCap extends Cap {

    /**
     * Descriptor of the bitmap to be overlaid at the start or end vertex.
     */
    @NonNull
    BitmapDescriptor getBitmapDescriptor();

    /**
     * Reference stroke width (in pixels) - the stroke width for which the cap bitmap at its native
     * dimension is designed. The default reference stroke width is 10 pixels.
     */
    float getRefWidth();

}
