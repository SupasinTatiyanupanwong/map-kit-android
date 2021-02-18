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

package dev.supasintatiyanupanwong.libraries.android.kits.maps.internal.google.model;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.LatLng;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.PointOfInterest;

class GooglePointOfInterest implements PointOfInterest {

    private final com.google.android.gms.maps.model.PointOfInterest mDelegate;

    private LatLng mLatLng;

    private GooglePointOfInterest(com.google.android.gms.maps.model.PointOfInterest delegate) {
        mDelegate = delegate;
    }

    @NonNull
    @Override
    public LatLng getLatLng() {
        if (mLatLng == null) {
            mLatLng = GoogleLatLng.wrap(mDelegate.latLng);
        }
        return mLatLng;
    }

    @Override
    public String getPlaceId() {
        return mDelegate.placeId;
    }

    @Override
    public String getName() {
        return mDelegate.name;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        GooglePointOfInterest that = (GooglePointOfInterest) obj;

        return mDelegate.equals(that.mDelegate);
    }

    @Override
    public int hashCode() {
        return mDelegate.hashCode();
    }

    @NonNull
    @Override
    public String toString() {
        return mDelegate.toString();
    }


    static PointOfInterest wrap(com.google.android.gms.maps.model.PointOfInterest delegate) {
        return new GooglePointOfInterest(delegate);
    }

}
