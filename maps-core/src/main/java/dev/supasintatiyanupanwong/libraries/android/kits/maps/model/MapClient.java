/*
 * Copyright 2020 Supasin Tatiyanupanwong
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

package dev.supasintatiyanupanwong.libraries.android.kits.maps.model;

import android.view.View;

import androidx.annotation.UiThread;

import dev.supasintatiyanupanwong.libraries.android.kits.maps.MapFragment;

/**
 * This is the main class of the Map Kit and is the entry point for all methods related to the map.
 * You cannot instantiate a {@link MapClient} object directly, rather, you must obtain one from
 * {@link MapFragment#getMapAsync} that you have added to your application.
 *
 * <p>Note: Similar to a {@link View} object, a {@link MapClient} can only be read and modified from
 * the Android UI thread. Calling {@link MapClient} methods from another thread will result in an
 * exception.
 *
 * <p>You can adjust the viewpoint of a map by changing the position of the camera (as opposed to
 * moving the map). You can use the map's camera to set parameters such as location, zoom level,
 * tilt angle, and bearing.
 *
 * @since 1.2.0
 */
@SuppressWarnings("deprecation")
@UiThread
public interface MapClient extends Map {}
