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
