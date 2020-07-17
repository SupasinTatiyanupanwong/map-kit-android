package me.tatiyanupanwong.supasin.android.libraries.kits.maps.model;

import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;
import androidx.annotation.UiThread;
import androidx.fragment.app.Fragment;

import me.tatiyanupanwong.supasin.android.libraries.kits.maps.MapKit;

import static androidx.annotation.RestrictTo.Scope.LIBRARY_GROUP;

/**
 * @since 1.2.0
 */
@SuppressWarnings("deprecation")
@RestrictTo(LIBRARY_GROUP)
public interface MapFactory extends Map.Factory {

    @UiThread
    void getMapAsync(@NonNull Fragment fragment, @NonNull MapKit.OnMapReadyCallback callback);

}
