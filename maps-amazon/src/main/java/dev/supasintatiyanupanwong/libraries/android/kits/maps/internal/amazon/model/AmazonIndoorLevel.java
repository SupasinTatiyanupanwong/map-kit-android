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

import static androidx.annotation.RestrictTo.Scope.LIBRARY;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;

import java.util.ArrayList;
import java.util.List;

import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.IndoorLevel;

@RestrictTo(LIBRARY)
public class AmazonIndoorLevel implements IndoorLevel {

    private final com.amazon.geo.mapsv2.model.IndoorLevel mDelegate;

    private AmazonIndoorLevel(@NonNull com.amazon.geo.mapsv2.model.IndoorLevel delegate) {
        mDelegate = delegate;
    }

    @Override
    public String getName() {
        return mDelegate.getName();
    }

    @Override
    public String getShortName() {
        return mDelegate.getShortName();
    }

    @Override
    public void activate() {
        mDelegate.activate();
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        AmazonIndoorLevel that = (AmazonIndoorLevel) obj;

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


    static List<IndoorLevel> wrap(List<com.amazon.geo.mapsv2.model.IndoorLevel> delegates) {
        if (delegates == null) {
            return null;
        }

        List<IndoorLevel> list = new ArrayList<>();
        for (int iter = 0, size = delegates.size(); iter < size; iter++) {
            list.add(new AmazonIndoorLevel(delegates.get(iter)));
        }
        return list;
    }

}
