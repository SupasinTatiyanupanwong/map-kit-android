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

package me.tatiyanupanwong.supasin.android.libraries.kits.maps.internal.google.model;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import me.tatiyanupanwong.supasin.android.libraries.kits.maps.model.Gap;

class GoogleGap extends GooglePatternItem implements Gap {

    private final com.google.android.gms.maps.model.Gap mDelegate;

    private GoogleGap(com.google.android.gms.maps.model.Gap delegate) {
        mDelegate = delegate;
    }

    GoogleGap(float length) {
        this(new com.google.android.gms.maps.model.Gap(length));
    }

    @Override
    public float getLength() {
        return mDelegate.length;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        GoogleGap that = (GoogleGap) obj;

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


    static Gap wrap(com.google.android.gms.maps.model.Gap delegate) {
        return new GoogleGap(delegate);
    }

    static com.google.android.gms.maps.model.Gap unwrap(Gap wrapped) {
        return ((GoogleGap) wrapped).mDelegate;
    }

}
