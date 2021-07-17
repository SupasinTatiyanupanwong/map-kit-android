/*
 * Copyright 2021 Supasin Tatiyanupanwong
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

package dev.supasintatiyanupanwong.libraries.android.kits.maps.internal.amazon.model;

import android.graphics.Bitmap;

import androidx.annotation.NonNull;

import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.BitmapDescriptor;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.ButtCap;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.CustomCap;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.RoundCap;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.SquareCap;

class AmazonCap implements ButtCap, CustomCap, RoundCap, SquareCap {

    static final @NonNull AmazonCap NULL = new AmazonCap();

    private BitmapDescriptor mBitmapDescriptor;

    private AmazonCap() {}

    @Override
    public @NonNull BitmapDescriptor getBitmapDescriptor() {
        if (mBitmapDescriptor == null) {
            mBitmapDescriptor = AmazonBitmapDescriptor.FACTORY
                    .fromBitmap(Bitmap.createBitmap(1, 1, Bitmap.Config.ALPHA_8));
        }
        return mBitmapDescriptor; // Not supported, fallback to default.
    }

    @Override
    public float getRefWidth() {
        return 0f; // Not supported, fallback to default.
    }

}
