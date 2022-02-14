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

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Objects;

abstract class MapsPlatform {

    abstract @NonNull MapKitBackend getMapKitBackend();


    static @NonNull MapsPlatform findPlatform(@NonNull Context context) {
        MapsPlatform amazon = AmazonMapsPlatform.buildIfSupported(context);
        if (amazon != null) {
            return amazon;
        }

        MapsPlatform google = GoogleMapsPlatform.buildIfSupported(context);
        if (google != null) {
            return google;
        }

        MapsPlatform huawei = HuaweiMapsPlatform.buildIfSupported(context);
        if (huawei != null) {
            return huawei;
        }

        throw new IllegalStateException(
                "Can't find supported platform, make sure to include one of the next artifacts:"
                        + " ':maps-amazon', ':maps-google', or ':maps-huawei'");
    }


    private static final class AmazonMapsPlatform extends MapsPlatform {
        private static final String LIBRARY_PACKAGE_NAME =
                BuildConfig.LIBRARY_PACKAGE_NAME + ".internal.amazon";

        private static MapKitBackend sBackend;

        private AmazonMapsPlatform() {}

        @Override @NonNull MapKitBackend getMapKitBackend() {
            return sBackend;
        }

        static @Nullable AmazonMapsPlatform buildIfSupported(@NonNull Context context) {
            try {
                sBackend = Objects.requireNonNull(
                        (MapKitBackend) Class
                                .forName(LIBRARY_PACKAGE_NAME + ".AmazonMapsBackend")
                                .getMethod("buildIfSupported", Context.class)
                                .invoke(null, context));

                return new AmazonMapsPlatform();
            } catch (Exception ignored) {
                return null;
            }
        }
    }

    private static final class GoogleMapsPlatform extends MapsPlatform {
        private static final String LIBRARY_PACKAGE_NAME =
                BuildConfig.LIBRARY_PACKAGE_NAME + ".internal.google";

        private static MapKitBackend sBackend;

        private GoogleMapsPlatform() {}

        @Override @NonNull MapKitBackend getMapKitBackend() {
            return sBackend;
        }

        static @Nullable GoogleMapsPlatform buildIfSupported(@NonNull Context context) {
            try {
                sBackend = Objects.requireNonNull(
                        (MapKitBackend) Class
                                .forName(LIBRARY_PACKAGE_NAME + ".GoogleMapsBackend")
                                .getMethod("buildIfSupported", Context.class)
                                .invoke(null, context));

                return new GoogleMapsPlatform();
            } catch (Exception ignored) {
                return null;
            }
        }
    }

    private static final class HuaweiMapsPlatform extends MapsPlatform {
        private static final String LIBRARY_PACKAGE_NAME =
                BuildConfig.LIBRARY_PACKAGE_NAME + ".internal.huawei";

        private static MapKitBackend sBackend;

        private HuaweiMapsPlatform() {}

        @Override @NonNull MapKitBackend getMapKitBackend() {
            return sBackend;
        }

        static @Nullable HuaweiMapsPlatform buildIfSupported(@NonNull Context context) {
            try {
                sBackend = Objects.requireNonNull(
                        (MapKitBackend) Class
                                .forName(LIBRARY_PACKAGE_NAME + ".HuaweiMapsBackend")
                                .getMethod("buildIfSupported", Context.class)
                                .invoke(null, context));

                return new HuaweiMapsPlatform();
            } catch (Exception ignored) {
                return null;
            }
        }
    }

}
