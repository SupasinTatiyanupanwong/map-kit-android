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

import me.tatiyanupanwong.supasin.android.libraries.kits.maps.model.Dot;

class GoogleDot extends GooglePatternItem implements Dot {

    private final com.google.android.gms.maps.model.Dot mDelegate;

    private GoogleDot(com.google.android.gms.maps.model.Dot delegate) {
        mDelegate = delegate;
    }

    GoogleDot() {
        this(new com.google.android.gms.maps.model.Dot());
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        GoogleDot that = (GoogleDot) obj;

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


    static Dot wrap(com.google.android.gms.maps.model.Dot delegate) {
        return new GoogleDot(delegate);
    }

    static com.google.android.gms.maps.model.Dot unwrap(Dot wrapped) {
        return ((GoogleDot) wrapped).mDelegate;
    }

}
