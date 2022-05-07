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

import static androidx.annotation.RestrictTo.Scope.LIBRARY;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;

import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.BitmapDescriptor;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.CustomCap;

@RestrictTo(LIBRARY)
public class GoogleCustomCap extends GoogleCap implements CustomCap {

    private final com.google.android.gms.maps.model.CustomCap mDelegate;

    private BitmapDescriptor mBitmapDescriptor;

    private GoogleCustomCap(com.google.android.gms.maps.model.CustomCap delegate) {
        mDelegate = delegate;
    }

    public GoogleCustomCap(@NonNull BitmapDescriptor bitmapDescriptor, float refWidth) {
        this(new com.google.android.gms.maps.model.CustomCap(
                GoogleBitmapDescriptor.unwrap(bitmapDescriptor), refWidth));
    }

    public GoogleCustomCap(@NonNull BitmapDescriptor bitmapDescriptor) {
        this(new com.google.android.gms.maps.model.CustomCap(
                GoogleBitmapDescriptor.unwrap(bitmapDescriptor)));
    }

    @Override
    public @NonNull BitmapDescriptor getBitmapDescriptor() {
        if (mBitmapDescriptor == null) {
            mBitmapDescriptor = GoogleBitmapDescriptor.wrap(mDelegate.bitmapDescriptor);
        }
        return mBitmapDescriptor;
    }

    @Override
    public float getRefWidth() {
        return mDelegate.refWidth;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        GoogleCustomCap that = (GoogleCustomCap) obj;

        return mDelegate.equals(that.mDelegate);
    }

    @Override
    public int hashCode() {
        return mDelegate.hashCode();
    }

    @Override
    public @NonNull String toString() {
        return mDelegate.toString();
    }


    static CustomCap wrap(com.google.android.gms.maps.model.CustomCap delegate) {
        return new GoogleCustomCap(delegate);
    }

    static com.google.android.gms.maps.model.CustomCap unwrap(CustomCap wrapped) {
        return ((GoogleCustomCap) wrapped).mDelegate;
    }

}
