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

import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.RoundCap;

class GoogleRoundCap extends GoogleCap implements RoundCap {

    private final com.google.android.gms.maps.model.RoundCap mDelegate;

    private GoogleRoundCap(com.google.android.gms.maps.model.RoundCap delegate) {
        mDelegate = delegate;
    }

    GoogleRoundCap() {
        this(new com.google.android.gms.maps.model.RoundCap());
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        GoogleRoundCap that = (GoogleRoundCap) obj;

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


    static RoundCap wrap(com.google.android.gms.maps.model.RoundCap delegate) {
        return new GoogleRoundCap(delegate);
    }

    static com.google.android.gms.maps.model.RoundCap unwrap(RoundCap wrapped) {
        return ((GoogleRoundCap) wrapped).mDelegate;
    }

}
