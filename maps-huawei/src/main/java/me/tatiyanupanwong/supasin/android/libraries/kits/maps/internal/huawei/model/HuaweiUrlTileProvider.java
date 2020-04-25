package me.tatiyanupanwong.supasin.android.libraries.kits.maps.internal.huawei.model;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.net.URL;

import me.tatiyanupanwong.supasin.android.libraries.kits.maps.model.UrlTileProvider;

class HuaweiUrlTileProvider extends HuaweiTileProvider implements UrlTileProvider {

    private final com.huawei.hms.maps.model.UrlTileProvider mDelegate;

    private HuaweiUrlTileProvider(com.huawei.hms.maps.model.UrlTileProvider delegate) {
        super(delegate);
        mDelegate = delegate;
    }

    HuaweiUrlTileProvider(int width, int height, @NonNull final UrlTileProvider tileProvider) {
        this(new com.huawei.hms.maps.model.UrlTileProvider(width, height) {
            @Override
            public URL getTileUrl(int x, int y, int zoom) {
                return tileProvider.getTileUrl(x, y, zoom);
            }
        });
    }

    @Override
    public URL getTileUrl(int x, int y, int zoom) {
        return mDelegate.getTileUrl(x, y, zoom);
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        HuaweiUrlTileProvider that = (HuaweiUrlTileProvider) obj;

        return mDelegate.equals(that.mDelegate);
    }

    @Override
    public int hashCode() {
        return mDelegate.hashCode();
    }

    @NonNull
    @Override
    public String toString() {
        return mDelegate.toString();
    }

}
