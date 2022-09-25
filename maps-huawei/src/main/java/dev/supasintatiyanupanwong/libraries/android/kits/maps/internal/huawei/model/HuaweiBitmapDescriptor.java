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

package dev.supasintatiyanupanwong.libraries.android.kits.maps.internal.huawei.model;

import static androidx.annotation.RestrictTo.Scope.LIBRARY;

import android.graphics.Bitmap;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;

import com.huawei.hms.maps.model.BitmapDescriptorFactory;

import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.BitmapDescriptor;

@RestrictTo(LIBRARY)
public class HuaweiBitmapDescriptor implements BitmapDescriptor {

    public static final Factory FACTORY = new Factory() {
        @Override public @NonNull BitmapDescriptor defaultMarker() {
            return new HuaweiBitmapDescriptor(BitmapDescriptorFactory.defaultMarker());
        }

        @Override public @NonNull BitmapDescriptor defaultMarker(float hue) {
            return new HuaweiBitmapDescriptor(BitmapDescriptorFactory.defaultMarker(hue));
        }

        @Override public @NonNull BitmapDescriptor fromAsset(String assetName) {
            return new HuaweiBitmapDescriptor(BitmapDescriptorFactory.fromAsset(assetName));
        }

        @Override public @NonNull BitmapDescriptor fromBitmap(Bitmap image) {
            return new HuaweiBitmapDescriptor(BitmapDescriptorFactory.fromBitmap(image));
        }

        @Override public @NonNull BitmapDescriptor fromFile(String fileName) {
            return new HuaweiBitmapDescriptor(BitmapDescriptorFactory.fromFile(fileName));
        }

        @Override public @NonNull BitmapDescriptor fromPath(String absolutePath) {
            return new HuaweiBitmapDescriptor(BitmapDescriptorFactory.fromPath(absolutePath));
        }

        @Override public @NonNull BitmapDescriptor fromResource(int resourceId) {
            return new HuaweiBitmapDescriptor(BitmapDescriptorFactory.fromResource(resourceId));
        }
    };


    private final com.huawei.hms.maps.model.BitmapDescriptor mDelegate;

    private HuaweiBitmapDescriptor(com.huawei.hms.maps.model.BitmapDescriptor delegate) {
        mDelegate = delegate;
    }

    @Override public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        HuaweiBitmapDescriptor that = (HuaweiBitmapDescriptor) obj;

        return mDelegate.equals(that.mDelegate);
    }

    @Override public int hashCode() {
        return mDelegate.hashCode();
    }

    @Override public @NonNull String toString() {
        return mDelegate.toString();
    }


    static BitmapDescriptor wrap(com.huawei.hms.maps.model.BitmapDescriptor delegate) {
        return new HuaweiBitmapDescriptor(delegate);
    }

    static com.huawei.hms.maps.model.BitmapDescriptor unwrap(BitmapDescriptor wrapped) {
        return ((HuaweiBitmapDescriptor) wrapped).mDelegate;
    }

}
