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

import android.graphics.Bitmap;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;

import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.BitmapDescriptor;

class GoogleBitmapDescriptor implements BitmapDescriptor {

    static final BitmapDescriptor.Factory FACTORY = new BitmapDescriptor.Factory() {
        @NonNull
        @Override
        public BitmapDescriptor defaultMarker() {
            return new GoogleBitmapDescriptor(BitmapDescriptorFactory.defaultMarker());
        }

        @NonNull
        @Override
        public BitmapDescriptor defaultMarker(float hue) {
            return new GoogleBitmapDescriptor(BitmapDescriptorFactory.defaultMarker(hue));
        }

        @NonNull
        @Override
        public BitmapDescriptor fromAsset(String assetName) {
            return new GoogleBitmapDescriptor(BitmapDescriptorFactory.fromAsset(assetName));
        }

        @NonNull
        @Override
        public BitmapDescriptor fromBitmap(Bitmap image) {
            return new GoogleBitmapDescriptor(BitmapDescriptorFactory.fromBitmap(image));
        }

        @NonNull
        @Override
        public BitmapDescriptor fromFile(String fileName) {
            return new GoogleBitmapDescriptor(BitmapDescriptorFactory.fromFile(fileName));
        }

        @NonNull
        @Override
        public BitmapDescriptor fromPath(String absolutePath) {
            return new GoogleBitmapDescriptor(BitmapDescriptorFactory.fromPath(absolutePath));
        }

        @NonNull
        @Override
        public BitmapDescriptor fromResource(int resourceId) {
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

    @NonNull
    @Override
    public String toString() {
        return mDelegate.toString();
    }


    static BitmapDescriptor wrap(com.google.android.gms.maps.model.BitmapDescriptor delegate) {
        return new GoogleBitmapDescriptor(delegate);
    }

    static com.google.android.gms.maps.model.BitmapDescriptor unwrap(BitmapDescriptor wrapped) {
        return ((GoogleBitmapDescriptor) wrapped).mDelegate;
    }

}
