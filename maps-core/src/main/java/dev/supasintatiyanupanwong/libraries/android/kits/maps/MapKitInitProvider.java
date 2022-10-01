/*
 * Copyright 2020 Supasin Tatiyanupanwong
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

package dev.supasintatiyanupanwong.libraries.android.kits.maps;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public final class MapKitInitProvider extends ContentProvider {

    private static final @NonNull String[] IMPL_FULLY_QUALIFIED_CLASS_NAMES = new String[] {
            BuildConfig.LIBRARY_PACKAGE_NAME + ".internal.amazon.AmazonMapsBackend",
            BuildConfig.LIBRARY_PACKAGE_NAME + ".internal.google.GoogleMapsBackend",
            BuildConfig.LIBRARY_PACKAGE_NAME + ".internal.huawei.HuaweiMapsBackend",
            BuildConfig.LIBRARY_PACKAGE_NAME + ".internal.nop.NopMapsBackend"
    };

    @Override public boolean onCreate() {
        final Context context = getContext().getApplicationContext();

        for (String className : IMPL_FULLY_QUALIFIED_CLASS_NAMES) {
            try {
                final @Nullable MapKitBackend backend = (MapKitBackend) Class.forName(className)
                        .getMethod("buildIfSupported", Context.class)
                        .invoke(null, context);
                if (backend != null) {
                    MapKit.setBackend(backend);
                    return true;
                }
            } catch (Exception ignored) {
            }
        }

        throw new IllegalStateException("MapKit cannot be initialized");
    }

    @Override public @Nullable Cursor query(
            @NonNull Uri uri,
            @Nullable String[] projection,
            @Nullable String selection,
            @Nullable String[] selectionArgs,
            @Nullable String sortOrder) {
        return null;
    }

    @Override public @Nullable String getType(@NonNull Uri uri) {
        return null;
    }

    @Override public @Nullable Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        return null;
    }

    @Override public int delete(
            @NonNull Uri uri,
            @Nullable String selection,
            @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override public int update(
            @NonNull Uri uri,
            @Nullable ContentValues values,
            @Nullable String selection,
            @Nullable String[] selectionArgs) {
        return 0;
    }

}
