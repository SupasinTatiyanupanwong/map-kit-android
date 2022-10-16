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

package dev.supasintatiyanupanwong.libraries.android.kits.maps.internal.tomtom.model;

import static androidx.annotation.RestrictTo.Scope.LIBRARY;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.VectorDrawable;
import android.net.Uri;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.core.content.ContextCompat;

import com.tomtom.sdk.maps.display.image.ImageFactory;

import java.io.File;

import dev.supasintatiyanupanwong.libraries.android.kits.maps.MapKit;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.internal.tomtom.R;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.BitmapDescriptor;

@RestrictTo(LIBRARY)
public class TomTomBitmapDescriptor implements BitmapDescriptor {

    public static final Factory FACTORY = new Factory() {
        @Override public @NonNull BitmapDescriptor defaultMarker() {
            return fromBitmap(getBitmapFromResourceId(R.drawable.ic_location_pin_filled_24dp));
        }

        @Override public @NonNull BitmapDescriptor defaultMarker(float hue) {
            return fromBitmap(getBitmapFromResourceId(R.drawable.ic_location_pin_filled_24dp));
        }

        @Override public @NonNull BitmapDescriptor fromAsset(String assetName) {
            return wrap(ImageFactory.INSTANCE.fromAssets(assetName));
        }

        @Override public @NonNull BitmapDescriptor fromBitmap(Bitmap image) {
            return wrap(ImageFactory.INSTANCE.fromBitmap(image));
        }

        @Override public @NonNull BitmapDescriptor fromFile(String fileName) {
            return wrap(ImageFactory.INSTANCE.fromUri(Uri.fromFile(new File(fileName))));
        }

        @Override public @NonNull BitmapDescriptor fromPath(String absolutePath) {
            return wrap(ImageFactory.INSTANCE.fromUri(Uri.fromFile(new File(absolutePath))));
        }

        @Override public @NonNull BitmapDescriptor fromResource(int resourceId) {
            return wrap(ImageFactory.INSTANCE.fromResource(resourceId));
        }


        private @NonNull Bitmap getBitmapFromResourceId(@DrawableRes int resourceId) {
            Drawable drawable = ContextCompat.getDrawable(MapKit.getContext(), resourceId);
            if (drawable instanceof BitmapDrawable) {
                return ((BitmapDrawable) drawable).getBitmap();
            } else if (drawable instanceof VectorDrawable) {
                return getBitmap((VectorDrawable) drawable);
            } else {
                throw new IllegalArgumentException("unsupported drawable type");
            }
        }

        private @NonNull Bitmap getBitmap(@NonNull VectorDrawable drawable) {
            Bitmap bitmap = Bitmap.createBitmap(
                    drawable.getIntrinsicWidth(),
                    drawable.getIntrinsicHeight(),
                    Bitmap.Config.ARGB_8888
            );
            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            drawable.draw(canvas);
            return bitmap;
        }
    };


    private final com.tomtom.sdk.maps.display.image.Image mDelegate;

    private TomTomBitmapDescriptor(com.tomtom.sdk.maps.display.image.Image delegate) {
        mDelegate = delegate;
    }

    @Override public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        TomTomBitmapDescriptor that = (TomTomBitmapDescriptor) obj;

        return mDelegate.equals(that.mDelegate);
    }

    @Override public int hashCode() {
        return mDelegate.hashCode();
    }

    @Override public @NonNull String toString() {
        return mDelegate.toString();
    }


    static BitmapDescriptor wrap(com.tomtom.sdk.maps.display.image.Image delegate) {
        return new TomTomBitmapDescriptor(delegate);
    }

    static @NonNull com.tomtom.sdk.maps.display.image.Image unwrap(
            @Nullable BitmapDescriptor wrapped
    ) {
        if (wrapped == null) {
            return ((TomTomBitmapDescriptor) FACTORY.defaultMarker()).mDelegate;
        } else {
            return ((TomTomBitmapDescriptor) wrapped).mDelegate;
        }
    }

}
