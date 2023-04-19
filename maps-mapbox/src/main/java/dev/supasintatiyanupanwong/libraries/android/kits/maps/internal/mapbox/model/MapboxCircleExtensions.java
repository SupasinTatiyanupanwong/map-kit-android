package dev.supasintatiyanupanwong.libraries.android.kits.maps.internal.mapbox.model;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.mapbox.maps.plugin.annotation.generated.CircleAnnotation;
import com.mapbox.maps.plugin.annotation.generated.CircleAnnotationManager;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

class MapboxCircleExtensions {

    private final @NonNull CircleAnnotationManager mManager;

    private final @NonNull Map<Long, Boolean> mClickabilities = new ConcurrentHashMap<>();
    private final @NonNull Map<Long, Object> mTags = new ConcurrentHashMap<>();

    MapboxCircleExtensions(@NonNull CircleAnnotationManager manager) {
        mManager = manager;
    }

    void remove(@NonNull CircleAnnotation annotation) {
        mManager.delete(annotation);
        mTags.remove(annotation.getId());
    }

    void setVisible(@NonNull CircleAnnotation annotation, boolean visible) {
        annotation.setCircleOpacity(visible ? 1.0 : 0);
        annotation.setCircleStrokeOpacity(visible ? 1.0 : 0);
    }

    boolean isVisible(@NonNull CircleAnnotation annotation) {
        return annotation.getCircleOpacity() == null || annotation.getCircleOpacity() != 0;
    }

    void setClickable(@NonNull CircleAnnotation annotation, boolean clickable) {
        mClickabilities.put(annotation.getId(), clickable);
    }

    boolean isClickable(@NonNull CircleAnnotation annotation) {
        return Boolean.TRUE.equals(mClickabilities.get(annotation.getId()));
    }

    void setTag(@NonNull CircleAnnotation annotation, @Nullable Object tag) {
        mTags.put(annotation.getId(), tag);
    }

    @Nullable Object getTag(@NonNull CircleAnnotation annotation) {
        return mTags.get(annotation.getId());
    }

    void invalidate(@NonNull CircleAnnotation annotation) {
        mManager.update(annotation);
    }

}
