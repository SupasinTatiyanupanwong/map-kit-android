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

import java.util.List;

import me.tatiyanupanwong.supasin.android.libraries.kits.maps.model.IndoorBuilding;
import me.tatiyanupanwong.supasin.android.libraries.kits.maps.model.IndoorLevel;

class GoogleIndoorBuilding implements IndoorBuilding {

    private final com.google.android.gms.maps.model.IndoorBuilding mDelegate;

    private GoogleIndoorBuilding(
            @NonNull com.google.android.gms.maps.model.IndoorBuilding delegate) {
        mDelegate = delegate;
    }

    @Override
    public int getDefaultLevelIndex() {
        return mDelegate.getDefaultLevelIndex();
    }

    @Override
    public int getActiveLevelIndex() {
        return mDelegate.getActiveLevelIndex();
    }

    @Override
    public List<IndoorLevel> getLevels() {
        return GoogleIndoorLevel.wrap(mDelegate.getLevels());
    }

    @Override
    public boolean isUnderground() {
        return mDelegate.isUnderground();
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        GoogleIndoorBuilding that = (GoogleIndoorBuilding) obj;

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


    @Nullable
    static IndoorBuilding wrap(
            @Nullable com.google.android.gms.maps.model.IndoorBuilding delegate) {
        return delegate == null ? null : new GoogleIndoorBuilding(delegate);
    }

}
