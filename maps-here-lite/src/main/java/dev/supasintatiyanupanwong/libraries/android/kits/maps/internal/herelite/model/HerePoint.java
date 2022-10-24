package dev.supasintatiyanupanwong.libraries.android.kits.maps.internal.herelite.model;

import android.graphics.Point;
import androidx.annotation.Nullable;

import org.jetbrains.annotations.Contract;

final class HerePoint {

    private HerePoint() {}

    @Contract("null -> null; !null -> !null")
    static @Nullable Point wrap(@Nullable com.here.sdk.core.Point2D delegate) {
        return delegate == null
                ? null
                : new Point((int) Math.round(delegate.x), (int) Math.round(delegate.y));
    }

    @Contract("null -> null; !null -> !null")
    static @Nullable com.here.sdk.core.Point2D unwrap(@Nullable Point wrapped) {
        return wrapped == null ? null : new com.here.sdk.core.Point2D(wrapped.x, wrapped.y);
    }

}
