package dev.supasintatiyanupanwong.libraries.android.kits.maps.internal.mapbox.model;

import android.graphics.Point;

import androidx.annotation.Nullable;

import com.mapbox.maps.ScreenCoordinate;

import org.jetbrains.annotations.Contract;

final class MapboxPoint {

    private MapboxPoint() {}

    @Contract("null -> null; !null -> !null")
    static @Nullable Point wrap(@Nullable ScreenCoordinate delegate) {
        return delegate == null
                ? null
                : new Point((int) Math.round(delegate.getX()), (int) Math.round(delegate.getY()));
    }

    @Contract("null -> null; !null -> !null")
    static @Nullable ScreenCoordinate unwrap(@Nullable Point wrapped) {
        return wrapped == null ? null : new ScreenCoordinate(wrapped.x, wrapped.y);
    }

}
