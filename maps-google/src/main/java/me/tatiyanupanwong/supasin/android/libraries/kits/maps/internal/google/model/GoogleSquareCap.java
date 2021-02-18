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

package me.tatiyanupanwong.supasin.android.libraries.kits.maps.internal.google.model;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import me.tatiyanupanwong.supasin.android.libraries.kits.maps.model.SquareCap;

class GoogleSquareCap extends GoogleCap implements SquareCap {

    private final com.google.android.gms.maps.model.SquareCap mDelegate;

    private GoogleSquareCap(com.google.android.gms.maps.model.SquareCap delegate) {
        mDelegate = delegate;
    }

    GoogleSquareCap() {
        this(new com.google.android.gms.maps.model.SquareCap());
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        GoogleSquareCap that = (GoogleSquareCap) obj;

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


    static SquareCap wrap(com.google.android.gms.maps.model.SquareCap delegate) {
        return new GoogleSquareCap(delegate);
    }

    static com.google.android.gms.maps.model.SquareCap unwrap(SquareCap wrapped) {
        return ((GoogleSquareCap) wrapped).mDelegate;
    }

}
