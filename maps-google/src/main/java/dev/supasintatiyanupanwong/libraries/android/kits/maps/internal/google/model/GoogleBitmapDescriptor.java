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

import android.graphics.Bitmap;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;

import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.BitmapDescriptor;

@RestrictTo(LIBRARY)
public class GoogleBitmapDescriptor implements BitmapDescriptor {

    public static final BitmapDescriptor.Factory FACTORY = new BitmapDescriptor.Factory() {
        @Override
        public @NonNull BitmapDescriptor defaultMarker() {
            return new GoogleBitmapDescriptor(BitmapDescriptorFactory.defaultMarker());
        }

        @Override
        public @NonNull BitmapDescriptor defaultMarker(float hue) {
            return new GoogleBitmapDescriptor(BitmapDescriptorFactory.defaultMarker(hue));
        }

        @Override
        public @NonNull BitmapDescriptor fromAsset(String assetName) {
            return new GoogleBitmapDescriptor(BitmapDescriptorFactory.fromAsset(assetName));
        }

        @Override
        public @NonNull BitmapDescriptor fromBitmap(Bitmap image) {
            return new GoogleBitmapDescriptor(BitmapDescriptorFactory.fromBitmap(image));
        }

        @Override
        public @NonNull BitmapDescriptor fromFile(String fileName) {
            return new GoogleBitmapDescriptor(BitmapDescriptorFactory.fromFile(fileName));
        }

        @Override
        public @NonNull BitmapDescriptor fromPath(String absolutePath) {
            return new GoogleBitmapDescriptor(BitmapDescriptorFactory.fromPath(absolutePath));
        }

        @Override
        public @NonNull BitmapDescriptor fromResource(int resourceId) {
            return new GoogleBitmapDescriptor(BitmapDescriptorFactory.fromResource(resourceId));
        }
    };


    private final com.google.android.gms.maps.model.BitmapDescriptor mDelegate;

    private GoogleBitmapDescriptor(com.google.android.gms.maps.model.BitmapDescriptor delegate) {
        mDelegate = delegate;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        GoogleBitmapDescriptor that = (GoogleBitmapDescriptor) obj;

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


    static BitmapDescriptor wrap(com.google.android.gms.maps.model.BitmapDescriptor delegate) {
        return new GoogleBitmapDescriptor(delegate);
    }

    static com.google.android.gms.maps.model.BitmapDescriptor unwrap(BitmapDescriptor wrapped) {
        return ((GoogleBitmapDescriptor) wrapped).mDelegate;
    }

}
