/*
 * Copyright 2023 Supasin Tatiyanupanwong
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

package dev.supasintatiyanupanwong.libraries.android.kits.maps.internal.mapbox.model;

import android.graphics.Bitmap;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.mapbox.maps.extension.style.layers.properties.generated.IconAnchor;
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationOptions;

import java.util.Arrays;

import dev.supasintatiyanupanwong.libraries.android.kits.maps.MapKit;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.BitmapDescriptor;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.LatLng;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.Marker;

public class MapboxMarkerOptions implements Marker.Options {

    private static final boolean USE_OFFSET_INTERPOLATION_ANCHOR = false;

    private final @NonNull PointAnnotationOptions mDelegate = new PointAnnotationOptions();

    private final float mDensity =
            MapKit.getApplicationContext().getResources().getDisplayMetrics().density;

    private @Nullable BitmapDescriptor mIconDescriptorCache;
    private float mIconWidth;
    private float mIconHeight;

    private float mAlpha;
    private boolean mVisible;

    public MapboxMarkerOptions() {
        icon(null);
        anchor(0.5f, 0.5f);
        alpha(1);
        visible(true);
    }

    @Override public @NonNull Marker.Options position(@NonNull LatLng latLng) {
        //noinspection ConstantValue
        if (latLng != null) {
            mDelegate.withGeometry(MapboxLatLng.unwrap(latLng));
        }
        return this;
    }

    @Override public @NonNull Marker.Options zIndex(float zIndex) {
        mDelegate.withSymbolSortKey(zIndex);
        return this;
    }

    @Override public @NonNull Marker.Options icon(@Nullable BitmapDescriptor iconDescriptor) {
        mIconDescriptorCache = iconDescriptor;
        final @NonNull Bitmap bitmap = MapboxBitmapDescriptor.unwrap(
                iconDescriptor == null
                        ? MapboxBitmapDescriptorFactory.INSTANCE.defaultMarker()
                        : iconDescriptor
        );
        mIconWidth = bitmap.getWidth() / mDensity;
        mIconHeight = bitmap.getHeight() / mDensity;
        mDelegate.withIconImage(bitmap);
        return this;
    }

    @Override public @NonNull Marker.Options anchor(float anchorU, float anchorV) {
        if (!USE_OFFSET_INTERPOLATION_ANCHOR) {
            // Finding nearest possible anchor point
            if (anchorU > 0.75f) {
                if (anchorV > 0.75f) {
                    mDelegate.withIconAnchor(IconAnchor.BOTTOM_RIGHT);
                } else if (anchorV < 0.25f) {
                    mDelegate.withIconAnchor(IconAnchor.TOP_RIGHT);
                } else { // anchorV in [0.25f..0.75f]
                    mDelegate.withIconAnchor(IconAnchor.RIGHT);
                }
            } else if (anchorU < 0.25f) {
                if (anchorV > 0.75f) {
                    mDelegate.withIconAnchor(IconAnchor.BOTTOM_LEFT);
                } else if (anchorV < 0.25f) {
                    mDelegate.withIconAnchor(IconAnchor.TOP_LEFT);
                } else { // anchorV in [0.25f..0.75f]
                    mDelegate.withIconAnchor(IconAnchor.LEFT);
                }
            } else {
                if (anchorV > 0.75f) {
                    mDelegate.withIconAnchor(IconAnchor.BOTTOM);
                } else if (anchorV < 0.25f) {
                    mDelegate.withIconAnchor(IconAnchor.TOP);
                } else { // anchorV in [0.25f..0.75f]
                    mDelegate.withIconAnchor(IconAnchor.CENTER);
                }
            }

            // No offset interpolation offset
            mDelegate.withIconOffset(Arrays.asList(0.0, 0.0));
        } else {
            // TODO needs fixes in interpolation logic
            // Without offset
            if (anchorU == 0.5f && anchorV == 0.5f) {
                mDelegate.withIconAnchor(IconAnchor.CENTER);
                mDelegate.withIconOffset(Arrays.asList(0.0, 0.0));
                return this;
            }

            if (anchorU == 0f && anchorV == 0.5f) {
                mDelegate.withIconAnchor(IconAnchor.LEFT);
                mDelegate.withIconOffset(Arrays.asList(0.0, 0.0));
                return this;
            }

            if (anchorU == 1f && anchorV == 0.5f) {
                mDelegate.withIconAnchor(IconAnchor.RIGHT);
                mDelegate.withIconOffset(Arrays.asList(0.0, 0.0));
                return this;
            }

            if (anchorU == 0.5f && anchorV == 0f) {
                mDelegate.withIconAnchor(IconAnchor.TOP);
                mDelegate.withIconOffset(Arrays.asList(0.0, 0.0));
                return this;
            }

            if (anchorU == 0.5f && anchorV == 1f) {
                mDelegate.withIconAnchor(IconAnchor.BOTTOM);
                mDelegate.withIconOffset(Arrays.asList(0.0, 0.0));
                return this;
            }

            if (anchorU == 0f && anchorV == 0f) {
                mDelegate.withIconAnchor(IconAnchor.TOP_LEFT);
                mDelegate.withIconOffset(Arrays.asList(0.0, 0.0));
                return this;
            }

            if (anchorU == 0f && anchorV == 1f) {
                mDelegate.withIconAnchor(IconAnchor.BOTTOM_LEFT);
                mDelegate.withIconOffset(Arrays.asList(0.0, 0.0));
                return this;
            }

            if (anchorU == 1f && anchorV == 0f) {
                mDelegate.withIconAnchor(IconAnchor.TOP_RIGHT);
                mDelegate.withIconOffset(Arrays.asList(0.0, 0.0));
                return this;
            }

            if (anchorU == 1f && anchorV == 1f) {
                mDelegate.withIconAnchor(IconAnchor.BOTTOM_RIGHT);
                mDelegate.withIconOffset(Arrays.asList(0.0, 0.0));
                return this;
            }

            // Interpolated offset. There will be some distortion, but it is somewhat acceptable.
            final double interpolatedOffsetX;
            final double interpolatedOffsetY;
            if (anchorU > 0.75f) { // (-, ?)
                interpolatedOffsetX = anchorU - 1;

                if (anchorV > 0.75f) {
                    mDelegate.withIconAnchor(IconAnchor.BOTTOM_RIGHT);
                    interpolatedOffsetY = anchorV - 1;
                } else if (anchorV < 0.25f) {
                    mDelegate.withIconAnchor(IconAnchor.TOP_RIGHT);
                    interpolatedOffsetY = 1 - anchorV;
                } else { // anchorV in [0.25f..0.75f]
                    mDelegate.withIconAnchor(IconAnchor.RIGHT);
                    interpolatedOffsetY = anchorV - 0.5;
                }
            } else if (anchorU < 0.25f) { // (+, ?)
                interpolatedOffsetX = 1 - anchorU;

                if (anchorV > 0.75f) {
                    mDelegate.withIconAnchor(IconAnchor.BOTTOM_LEFT);
                    interpolatedOffsetY = anchorV - 1;
                } else if (anchorV < 0.25f) {
                    mDelegate.withIconAnchor(IconAnchor.TOP_LEFT);
                    interpolatedOffsetY = 1 - anchorV;
                } else { // anchorV in [0.25f..0.75f]
                    mDelegate.withIconAnchor(IconAnchor.LEFT);
                    interpolatedOffsetY = anchorV - 0.5;
                }
            } else { // anchorU in [0.25f..0.75f]
                interpolatedOffsetX = anchorU - 0.5;

                if (anchorV > 0.75f) {
                    mDelegate.withIconAnchor(IconAnchor.BOTTOM);
                    interpolatedOffsetY = anchorV - 1;
                } else if (anchorV < 0.25f) {
                    mDelegate.withIconAnchor(IconAnchor.TOP);
                    interpolatedOffsetY = 1 - anchorV;
                } else { // anchorV in [0.25f..0.75f]
                    mDelegate.withIconAnchor(IconAnchor.CENTER);
                    interpolatedOffsetY = anchorV - 0.5;
                }
            }

            mDelegate.withIconOffset(
                    Arrays.asList(
                            0.25f * mIconWidth * interpolatedOffsetX,
                            0.25f * mIconHeight * interpolatedOffsetY
                    )
            );
        }
        return this;
    }

    @Override public @NonNull Marker.Options infoWindowAnchor(float anchorU, float anchorV) {
        return this;
    }

    @Override public @NonNull Marker.Options title(@Nullable String title) {
        return this;
    }

    @Override public @NonNull Marker.Options snippet(@Nullable String snippet) {
        return this;
    }

    @Override public @NonNull Marker.Options draggable(boolean draggable) {
        mDelegate.withDraggable(draggable);
        return this;
    }

    @Override public @NonNull Marker.Options visible(boolean visible) {
        mVisible = visible;
        invalidateOpacity();
        return this;
    }

    @Override public @NonNull Marker.Options flat(boolean flat) {
        return this;
    }

    @Override public @NonNull Marker.Options rotation(float rotation) {
        mDelegate.withIconRotate(rotation);
        return this;
    }

    @Override public @NonNull Marker.Options alpha(float alpha) {
        mAlpha = alpha;
        invalidateOpacity();
        return this;
    }

    @Override public LatLng getPosition() {
        return MapboxLatLng.wrap(mDelegate.getGeometry());
    }

    @Override public String getTitle() {
        return null;
    }

    @Override public String getSnippet() {
        return null;
    }

    @Override public @Nullable BitmapDescriptor getIcon() {
        return mIconDescriptorCache;
    }

    @Override public float getAnchorU() {
        //noinspection DataFlowIssue
        return mDelegate.getIconOffset().get(0).floatValue();
    }

    @Override public float getAnchorV() {
        //noinspection DataFlowIssue
        return mDelegate.getIconOffset().get(1).floatValue();
    }

    @Override public boolean isDraggable() {
        return mDelegate.getDraggable();
    }

    @Override public boolean isVisible() {
        return mVisible;
    }

    @Override public boolean isFlat() {
        return false;
    }

    @Override public float getRotation() {
        final @Nullable Double rotate = mDelegate.getIconRotate();
        return rotate == null ? 0f : rotate.floatValue();
    }

    @Override public float getInfoWindowAnchorU() {
        return 0f;
    }

    @Override public float getInfoWindowAnchorV() {
        return 0f;
    }

    @Override public float getAlpha() {
        return mAlpha;
    }

    @Override public float getZIndex() {
        final @Nullable Double sortKey = mDelegate.getSymbolSortKey();
        return sortKey == null ? 0f : sortKey.floatValue();
    }


    private void invalidateOpacity() {
        mDelegate.withIconOpacity(mVisible ? mAlpha : 0);
    }


    static PointAnnotationOptions unwrap(Marker.Options wrapped) {
        return ((MapboxMarkerOptions) wrapped).mDelegate;
    }

}
