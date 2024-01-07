/*
 * Copyright 2024 Supasin Tatiyanupanwong
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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

final class AmazonMapClient$TagManager {

    private final @NonNull Map<String, Object> mTags = new ConcurrentHashMap<>();

    void setTag(String id, @Nullable Object tag) {
        if (tag == null) {
            mTags.remove(id);
        } else {
            mTags.put(id, tag);
        }
    }

    @Nullable Object getTag(String id) {
        return mTags.get(id);
    }

    void remove(String id) {
        mTags.remove(id);
    }

    void clear() {
        mTags.clear();
    }
}
