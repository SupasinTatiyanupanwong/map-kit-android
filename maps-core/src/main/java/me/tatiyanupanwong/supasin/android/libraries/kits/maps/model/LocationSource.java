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

import android.location.Location;

import androidx.annotation.NonNull;

/**
 * Defines an interface for providing location data, typically to a {@link Map} object.
 *
 * <p>A {@link Map} object has a built-in location provider for its my-location layer,
 * but it can be {@link Map#setLocationSource replaced} with another one that implements
 * this interface.
 *
 * <p>A {@link Map} object activates its location provider using {@link
 * #activate(OnLocationChangedListener)}. While active (between {@link
 * #activate(OnLocationChangedListener)} and {@link #deactivate()}), a location provider should
 * push periodic location updates to the listener registered in {@link
 * #activate(OnLocationChangedListener)}. It is the provider's responsibility to use location
 * services wisely according to the map's lifecycle state. For example, it should only using
 * battery-intensive services (like GPS) occasionally, or only while an activity is in the
 * foreground.
 */
public interface LocationSource {

    /**
     * Activates this provider. This provider will notify the supplied listener periodically,
     * until you call {@link #deactivate()}. Notifications will be broadcast on the main thread.
     *
     * @param listener A listener that's called when a new location is available
     */
    void activate(@NonNull LocationSource.OnLocationChangedListener listener);

    /**
     * Deactivates this provider. The previously-registered callback is not notified of any
     * further updates.
     */
    void deactivate();


    /**
     * Handles a location update.
     */
    interface OnLocationChangedListener {
        /**
         * Called when a new user location is known.
         */
        void onLocationChanged(@NonNull Location location);
    }

}
