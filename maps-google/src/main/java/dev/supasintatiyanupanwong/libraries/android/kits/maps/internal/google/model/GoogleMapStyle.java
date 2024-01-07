package dev.supasintatiyanupanwong.libraries.android.kits.maps.internal.google.model;

import static androidx.annotation.RestrictTo.Scope.LIBRARY;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RawRes;
import androidx.annotation.RestrictTo;

import org.jetbrains.annotations.Contract;

import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.MapClient;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.MapStyle;

@RestrictTo(LIBRARY)
public class GoogleMapStyle implements MapStyle {

    private GoogleMapStyle() {}

    public static class Options implements MapStyle.Options {
        private final com.google.android.gms.maps.model.MapStyleOptions mDelegate;

        public Options(String json) {
            mDelegate = new com.google.android.gms.maps.model.MapStyleOptions(json);
        }

        public Options(@NonNull Context context, @RawRes int resourceId) {
            mDelegate = com.google.android.gms.maps.model.MapStyleOptions
                    .loadRawResourceStyle(context, resourceId);
        }

        @Override public boolean equals(@Nullable Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }

            GoogleMapStyle.Options that = (GoogleMapStyle.Options) obj;

            return mDelegate.equals(that.mDelegate);
        }

        @Override public int hashCode() {
            return mDelegate.hashCode();
        }

        @Override public @NonNull String toString() {
            return mDelegate.toString();
        }


        @Contract("null -> null; !null -> !null")
        static @Nullable com.google.android.gms.maps.model.MapStyleOptions unwrap(
                @Nullable MapClient.Style.Options wrapped
        ) {
            return wrapped == null ? null : ((GoogleMapStyle.Options) wrapped).mDelegate;
        }
    }
}
