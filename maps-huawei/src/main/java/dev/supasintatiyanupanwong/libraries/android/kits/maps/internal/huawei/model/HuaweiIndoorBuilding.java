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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.IndoorBuilding;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.IndoorLevel;

class HuaweiIndoorBuilding implements IndoorBuilding {

    private final com.huawei.hms.maps.model.IndoorBuilding mDelegate;

    private HuaweiIndoorBuilding(
            @NonNull com.huawei.hms.maps.model.IndoorBuilding delegate) {
        mDelegate = delegate;
    }

    @Override public int getDefaultLevelIndex() {
        return mDelegate.getDefaultLevelIndex();
    }

    @Override public int getActiveLevelIndex() {
        return mDelegate.getActiveLevelIndex();
    }

    @Override public List<IndoorLevel> getLevels() {
        return HuaweiIndoorLevel.wrap(mDelegate.getLevels());
    }

    @Override public boolean isUnderground() {
        return mDelegate.isUnderground();
    }

    @Override public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        HuaweiIndoorBuilding that = (HuaweiIndoorBuilding) obj;

        return mDelegate.equals(that.mDelegate);
    }

    @Override public int hashCode() {
        return mDelegate.hashCode();
    }

    @Override public @NonNull String toString() {
        return mDelegate.toString();
    }


    static @Nullable IndoorBuilding wrap(
            @Nullable com.huawei.hms.maps.model.IndoorBuilding delegate) {
        return delegate == null ? null : new HuaweiIndoorBuilding(delegate);
    }

}
