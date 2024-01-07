/*
 * Copyright 2024 Supasin Tatiyanupanwong
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

/**
 * @since 2.3.0
 */
public interface MapStyle extends MapClient.Style {

    /**
     * Defines styling options for a {@link MapClient}.
     *
     * <p>With style options you can customize the presentation of the standard map styles,
     * changing the visual display of features like roads, parks, and other points of interest.
     * As well as changing the style of these features, you can also hide features entirely.
     * This means that you can emphasize particular components of the map or make the map
     * complement the content of your app.
     */
    interface Options extends MapClient.Style.Options {}
}
