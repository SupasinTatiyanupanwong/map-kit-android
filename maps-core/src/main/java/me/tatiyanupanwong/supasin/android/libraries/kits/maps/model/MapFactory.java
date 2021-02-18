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

package me.tatiyanupanwong.supasin.android.libraries.kits.maps.model;

import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;
import androidx.annotation.UiThread;
import androidx.fragment.app.Fragment;

import me.tatiyanupanwong.supasin.android.libraries.kits.maps.MapKit;

import static androidx.annotation.RestrictTo.Scope.LIBRARY_GROUP;

/**
 * @since 1.2.0
 */
@SuppressWarnings("deprecation")
@RestrictTo(LIBRARY_GROUP)
public interface MapFactory extends Map.Factory {

    @UiThread
    void getMapAsync(@NonNull Fragment fragment, @NonNull MapKit.OnMapReadyCallback callback);

}
