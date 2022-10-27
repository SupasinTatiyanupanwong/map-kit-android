/*
 * Copyright 2022 Supasin Tatiyanupanwong
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

package dev.supasintatiyanupanwong.libraries.android.kits.maps.internal.herelite.model;

import static androidx.annotation.RestrictTo.Scope.LIBRARY;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.arch.core.util.Function;

import com.here.NativeBase;
import com.here.sdk.core.Anchor2D;
import com.here.sdk.core.GeoCoordinates;
import com.here.sdk.mapviewlite.MapImage;
import com.here.sdk.mapviewlite.MapMarker;
import com.here.sdk.mapviewlite.MapMarkerImageStyle;

import org.jetbrains.annotations.Contract;

import java.lang.reflect.Field;
import java.util.Objects;
import java.util.UUID;

import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.BitmapDescriptor;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.LatLng;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.Marker;

@RestrictTo(LIBRARY)
public class HereMarker implements Marker {

    private final @NonNull com.here.sdk.mapviewlite.MapMarker mDelegate;
    private final @NonNull com.here.sdk.mapviewlite.MapMarkerImageStyle mImageStyle;
    private final @NonNull Function<MapMarker, Void> mRemover;

    private final @NonNull String mId;

    private @Nullable Object mTag;

    HereMarker(
            @NonNull Marker.Options options,
            @NonNull Function<MapMarker, Void> remover
    ) {
        final @NonNull GeoCoordinates coordinates =
                HereLatLng.unwrap(Objects.requireNonNull(options.getPosition()));

        final @NonNull MapMarkerImageStyle imageStyle = new MapMarkerImageStyle();
        imageStyle.setFlat(options.isFlat());
        imageStyle.setAnchorPoint(new Anchor2D(options.getAnchorU(), options.getAnchorV()));
        imageStyle.setAngle(options.getRotation());
        imageStyle.setAlpha(options.getAlpha());
        mImageStyle = imageStyle;

        mDelegate = new MapMarker(coordinates);
        mRemover = remover;

        setIcon(options.getIcon());

        @NonNull String id;
        try {
            final @NonNull Field idField = NativeBase.class.getDeclaredField("nativeHandle");
            idField.setAccessible(true);
            id = String.valueOf(idField.getLong(mDelegate));
        } catch (ReflectiveOperationException ignored) {
            id = UUID.randomUUID().toString();
        }
        mId = id;
    }

    @Override public void remove() {
        mRemover.apply(mDelegate);
    }

    @Override public @NonNull String getId() {
        return mId;
    }

    @Override public void setPosition(@NonNull LatLng latLng) {
        mDelegate.setCoordinates(HereLatLng.unwrap(latLng));
    }

    @Override public @NonNull LatLng getPosition() {
        return HereLatLng.wrap(mDelegate.getCoordinates());
    }

    @Override public void setZIndex(float zIndex) {
        // No-op on HERE Maps (Lite Edition)
    }

    @Override public float getZIndex() {
        return Float.NaN;
    }

    @Override public void setIcon(@Nullable BitmapDescriptor iconDescriptor) {
        final @NonNull MapImage delegate = HereBitmapDescriptor.unwrap(
                iconDescriptor == null
                        ? HereBitmapDescriptor.FACTORY.defaultMarker()
                        : iconDescriptor
        );
        mDelegate.addImage(delegate, null);
        mDelegate.updateImageStyle(mImageStyle);
    }

    @Override public void setAnchor(float anchorU, float anchorV) {
        mImageStyle.setAnchorPoint(new Anchor2D(anchorU, anchorV));
        mDelegate.updateImageStyle(mImageStyle);
    }

    @Override public void setInfoWindowAnchor(float anchorU, float anchorV) {
        // No-op on HERE Maps (Lite Edition)
    }

    @Override public void setTitle(@Nullable String title) {
        // No-op on HERE Maps (Lite Edition)
    }

    @Override public String getTitle() {
        return "";
    }

    @Override public void setSnippet(@Nullable String snippet) {
        // No-op on HERE Maps (Lite Edition)
    }

    @Override public String getSnippet() {
        return "";
    }

    @Override public void setDraggable(boolean draggable) {
        // TODO
    }

    @Override public boolean isDraggable() {
        return false; // TODO
    }

    @Override public void showInfoWindow() {
        // No-op on HERE Maps (Lite Edition)
    }

    @Override public void hideInfoWindow() {
        // No-op on HERE Maps (Lite Edition)
    }

    @Override public boolean isInfoWindowShown() {
        return false;
    }

    @Override public void setVisible(boolean visible) {
        mDelegate.setVisible(visible);
    }

    @Override public boolean isVisible() {
        return mDelegate.isVisible();
    }

    @Override public void setFlat(boolean flat) {
        mImageStyle.setFlat(flat);
        mDelegate.updateImageStyle(mImageStyle);
    }

    @Override public boolean isFlat() {
        return mImageStyle.isFlat();
    }

    @Override public void setRotation(float rotation) {
        mImageStyle.setAngle(rotation);
        mDelegate.updateImageStyle(mImageStyle);
    }

    @Override public float getRotation() {
        return mImageStyle.getAngle();
    }

    @Override public void setAlpha(float alpha) {
        mImageStyle.setAlpha(alpha);
        mDelegate.updateImageStyle(mImageStyle);
    }

    @Override public float getAlpha() {
        return mImageStyle.getAlpha();
    }

    @Override public void setTag(@Nullable Object tag) {
        mTag = tag;
    }

    @Override public @Nullable Object getTag() {
        return mTag;
    }

    @Override public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        HereMarker that = (HereMarker) obj;

        return mDelegate.equals(that.mDelegate);
    }

    @Override public int hashCode() {
        return mDelegate.hashCode();
    }

    @Override public @NonNull String toString() {
        return mDelegate.toString();
    }


    @Contract("null -> null; !null -> !null")
    static @Nullable com.here.sdk.mapviewlite.MapMarker unwrap(@Nullable Marker wrapped) {
        return wrapped == null ? null : ((HereMarker) wrapped).mDelegate;
    }


    public static class Options implements Marker.Options {
        private @Nullable LatLng mPosition;
        private @Nullable BitmapDescriptor mIcon;
        private float mAnchorU = 0.5f;
        private float mAnchorV = 0.5f;
        private boolean mDraggable = true;
        private boolean mVisible = true;
        private boolean mFlat = true;
        private float mRotation;
        private float mAlpha = 1f;

        public Options() {}

        @Override public @NonNull Marker.Options position(@NonNull LatLng latLng) {
            mPosition = Objects.requireNonNull(
                    latLng,
                    "latlng cannot be null - a position is required."
            );
            return this;
        }

        @Override public @NonNull Marker.Options zIndex(float zIndex) {
            // No-op on HERE Maps (Lite Edition)
            return this;
        }

        @Override public @NonNull Marker.Options icon(@Nullable BitmapDescriptor iconDescriptor) {
            mIcon = iconDescriptor;
            return this;
        }

        @Override public @NonNull Marker.Options anchor(float anchorU, float anchorV) {
            mAnchorU = anchorU;
            mAnchorV = anchorV;
            return this;
        }

        @Override public @NonNull Marker.Options infoWindowAnchor(float anchorU, float anchorV) {
            // No-op on HERE Maps (Lite Edition)
            return this;
        }

        @Override public @NonNull Marker.Options title(@Nullable String title) {
            // No-op on HERE Maps (Lite Edition)
            return this;
        }

        @Override public @NonNull Marker.Options snippet(@Nullable String snippet) {
            // No-op on HERE Maps (Lite Edition)
            return this;
        }

        @Override public @NonNull Marker.Options draggable(boolean draggable) {
            mDraggable = draggable;
            return this;
        }

        @Override public @NonNull Marker.Options visible(boolean visible) {
            mVisible = visible;
            return this;
        }

        @Override public @NonNull Marker.Options flat(boolean flat) {
            mFlat = flat;
            return this;
        }

        @Override public @NonNull Marker.Options rotation(float rotation) {
            mRotation = rotation;
            return this;
        }

        @Override public @NonNull Marker.Options alpha(float alpha) {
            mAlpha = alpha;
            return this;
        }

        @Override public @Nullable LatLng getPosition() {
            return mPosition;
        }

        @Override public @Nullable String getTitle() {
            return null;
        }

        @Override public @Nullable String getSnippet() {
            return null;
        }

        @Override public @Nullable BitmapDescriptor getIcon() {
            return mIcon;
        }

        @Override public float getAnchorU() {
            return mAnchorU;
        }

        @Override public float getAnchorV() {
            return mAnchorV;
        }

        @Override public boolean isDraggable() {
            return mDraggable;
        }

        @Override public boolean isVisible() {
            return mVisible;
        }

        @Override public boolean isFlat() {
            return mFlat;
        }

        @Override public float getRotation() {
            return mRotation;
        }

        @Override public float getInfoWindowAnchorU() {
            return Float.NaN;
        }

        @Override public float getInfoWindowAnchorV() {
            return Float.NaN;
        }

        @Override public float getAlpha() {
            return mAlpha;
        }

        @Override public float getZIndex() {
            return Float.NaN;
        }
    }

}
