/*
 * Copyright 2022 Supasin Tatiyanupanwong
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

package dev.supasintatiyanupanwong.libraries.android.kits.maps.internal.nil.model;

import static androidx.annotation.RestrictTo.Scope.LIBRARY;

import android.graphics.Bitmap;

import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;

import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.BitmapDescriptor;

@RestrictTo(LIBRARY)
public class NilBitmapDescriptor implements BitmapDescriptor {

    static final @NonNull BitmapDescriptor INSTANCE = new NilBitmapDescriptor();

    public static final @NonNull Factory FACTORY = new Factory() {
        @Override public @NonNull BitmapDescriptor defaultMarker() {
            return NilBitmapDescriptor.INSTANCE; // Not supported, null object for API safe.
        }

        @Override public @NonNull BitmapDescriptor defaultMarker(float hue) {
            return NilBitmapDescriptor.INSTANCE; // Not supported, null object for API safe.
        }

        @Override public @NonNull BitmapDescriptor fromAsset(String assetName) {
            return NilBitmapDescriptor.INSTANCE; // Not supported, null object for API safe.
        }

        @Override public @NonNull BitmapDescriptor fromBitmap(Bitmap image) {
            return NilBitmapDescriptor.INSTANCE; // Not supported, null object for API safe.
        }

        @Override public @NonNull BitmapDescriptor fromFile(String fileName) {
            return NilBitmapDescriptor.INSTANCE; // Not supported, null object for API safe.
        }

        @Override public @NonNull BitmapDescriptor fromPath(String absolutePath) {
            return NilBitmapDescriptor.INSTANCE; // Not supported, null object for API safe.
        }

        @Override public @NonNull BitmapDescriptor fromResource(int resourceId) {
            return NilBitmapDescriptor.INSTANCE; // Not supported, null object for API safe.
        }
    };


    private NilBitmapDescriptor() {}

}
