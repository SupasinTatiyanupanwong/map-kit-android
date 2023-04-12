package dev.supasintatiyanupanwong.libraries.android.kits.maps.internal.mapbox.model;

import static androidx.annotation.RestrictTo.Scope.LIBRARY;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;

import java.io.FileNotFoundException;
import java.io.IOException;

import dev.supasintatiyanupanwong.libraries.android.kits.maps.MapKit;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.internal.mapbox.R;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.BitmapDescriptor;

@RestrictTo(LIBRARY)
public class MapboxBitmapDescriptorFactory implements BitmapDescriptor.Factory {

    public static final BitmapDescriptor.Factory INSTANCE = new MapboxBitmapDescriptorFactory();

    private MapboxBitmapDescriptorFactory() {}

    @Override public @NonNull BitmapDescriptor defaultMarker() {
        //noinspection ConstantConditions
        return fromBitmap(
                BitmapDescriptor.fromResourceInternal(
                        R.drawable.mapkit_mapbox_ic_location_pin_filled_24dp
                )
        );
    }

    @Override public @NonNull BitmapDescriptor defaultMarker(float hue) {
        //noinspection ConstantConditions
        return fromBitmap(
                BitmapDescriptor.fromResourceInternal(
                        R.drawable.mapkit_mapbox_ic_location_pin_filled_24dp,
                        hue
                )
        );
    }

    @Override public @Nullable BitmapDescriptor fromAsset(@NonNull String assetName) {
        //noinspection ConstantConditions
        if (assetName == null) {
            return null;
        }

        try {
            return fromBitmap(
                    BitmapFactory.decodeStream(
                            MapKit.getApplicationContext().getAssets().open(assetName)
                    )
            );
        } catch (IOException ignored) {
            return null;
        }
    }

    @Override public @Nullable BitmapDescriptor fromBitmap(@Nullable Bitmap image) {
        return image == null ? null : MapboxBitmapDescriptor.wrap(image);
    }

    @Override public @Nullable BitmapDescriptor fromFile(@NonNull String fileName) {
        //noinspection ConstantConditions
        if (fileName == null) {
            return null;
        }

        try {
            return fromBitmap(
                    BitmapFactory.decodeStream(
                            MapKit.getApplicationContext().openFileInput(fileName)
                    )
            );
        } catch (FileNotFoundException e) {
            return null;
        }
    }

    @Override public @Nullable BitmapDescriptor fromPath(@NonNull String absolutePath) {
        //noinspection ConstantConditions
        if (absolutePath == null) {
            return null;
        }

        return fromBitmap(BitmapFactory.decodeFile(absolutePath));
    }

    @Override public @Nullable BitmapDescriptor fromResource(@DrawableRes int resourceId) {
        if (resourceId == 0) {
            return null;
        }

        return fromBitmap(BitmapDescriptor.fromResourceInternal(resourceId));
    }

}
