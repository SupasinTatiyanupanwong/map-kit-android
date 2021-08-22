/*
 * Copyright 2021 Supasin Tatiyanupanwong
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

package dev.supasintatiyanupanwong.libraries.android.kits.maps.internal.amazon.model;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.net.URL;

import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.UrlTileProvider;

class AmazonUrlTileProvider extends AmazonTileProvider implements UrlTileProvider {

    private final com.amazon.geo.mapsv2.model.UrlTileProvider mDelegate;

    private AmazonUrlTileProvider(com.amazon.geo.mapsv2.model.UrlTileProvider delegate) {
        super(delegate);
        mDelegate = delegate;
    }

    AmazonUrlTileProvider(int width, int height, @NonNull final UrlTileProvider tileProvider) {
        this(new com.amazon.geo.mapsv2.model.UrlTileProvider(width, height) {
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

        AmazonUrlTileProvider that = (AmazonUrlTileProvider) obj;

        return mDelegate.equals(that.mDelegate);
    }

    @Override
    public int hashCode() {
        return mDelegate.hashCode();
    }

    @Override
    public @NonNull String toString() {
        return mDelegate.toString();
    }

}