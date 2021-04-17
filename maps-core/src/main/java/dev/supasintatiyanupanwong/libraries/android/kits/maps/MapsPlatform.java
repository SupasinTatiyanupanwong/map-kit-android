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

import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Objects;

import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.MapFactory;

abstract class MapsPlatform {

    private static MapsPlatform sPlatform;

    static synchronized @NonNull MapsPlatform get() {
        return sPlatform;
    }

    static synchronized void init(@NonNull Context context) {
        sPlatform = findPlatform(context);
    }


    abstract @NonNull MapFactory getFactory();

    @LayoutRes
    abstract int getFragmentLayoutId();

    @IdRes
    abstract int getFragmentDelegateId();


    private static MapsPlatform findPlatform(@NonNull Context context) {
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
                "dev.supasintatiyanupanwong.libraries.android.kits.maps.internal.amazon";

        private static final String CLASS_NAME_LAYOUT_RES = LIBRARY_PACKAGE_NAME + ".R$layout";

        private static final String CLASS_NAME_ID_RES = LIBRARY_PACKAGE_NAME + ".R$id";

        private static final String FIELD_NAME_LAYOUT_ID = "kits_maps_internal_amazon_map_view";

        private static final String FIELD_NAME_FRAGMENT_ID = "kits_maps_internal_map_fragment";

        private static MapFactory sFactory;
        private static int sFragmentLayoutId;
        private static int sFragmentDelegateId;

        private AmazonMapsPlatform() {}

        @Override
        @NonNull MapFactory getFactory() {
            return sFactory;
        }

        @LayoutRes
        @Override
        int getFragmentLayoutId() {
            return sFragmentLayoutId;
        }

        @IdRes
        @Override
        int getFragmentDelegateId() {
            return sFragmentDelegateId;
        }


        static @Nullable AmazonMapsPlatform buildIfSupported(@NonNull Context context) {
            try {
                sFactory = Objects.requireNonNull(
                        (MapFactory) Class
                                .forName(LIBRARY_PACKAGE_NAME + ".model.AmazonMapFactory")
                                .getMethod("buildIfSupported", Context.class)
                                .invoke(null, context));

                sFragmentLayoutId = Class.forName(CLASS_NAME_LAYOUT_RES)
                        .getField(FIELD_NAME_LAYOUT_ID).getInt(null);

                sFragmentDelegateId = Class.forName(CLASS_NAME_ID_RES)
                        .getField(FIELD_NAME_FRAGMENT_ID).getInt(null);

                return new AmazonMapsPlatform();
            } catch (Exception ignored) {
                return null;
            }
        }
    }

    private static final class GoogleMapsPlatform extends MapsPlatform {
        private static final String LIBRARY_PACKAGE_NAME =
                "dev.supasintatiyanupanwong.libraries.android.kits.maps.internal.google";

        private static final String CLASS_NAME_LAYOUT_RES = LIBRARY_PACKAGE_NAME + ".R$layout";

        private static final String CLASS_NAME_ID_RES = LIBRARY_PACKAGE_NAME + ".R$id";

        private static final String FIELD_NAME_LAYOUT_ID = "kits_maps_internal_google_map_view";

        private static final String FIELD_NAME_FRAGMENT_ID = "kits_maps_internal_map_fragment";

        private static MapFactory sFactory;
        private static int sFragmentLayoutId;
        private static int sFragmentDelegateId;

        private GoogleMapsPlatform() {}

        @Override
        @NonNull MapFactory getFactory() {
            return sFactory;
        }

        @LayoutRes
        @Override
        int getFragmentLayoutId() {
            return sFragmentLayoutId;
        }

        @IdRes
        @Override
        int getFragmentDelegateId() {
            return sFragmentDelegateId;
        }


        static @Nullable GoogleMapsPlatform buildIfSupported(@NonNull Context context) {
            try {
                sFactory = Objects.requireNonNull(
                        (MapFactory) Class
                                .forName(LIBRARY_PACKAGE_NAME + ".model.GoogleMapFactory")
                                .getMethod("buildIfSupported", Context.class)
                                .invoke(null, context));

                sFragmentLayoutId = Class.forName(CLASS_NAME_LAYOUT_RES)
                        .getField(FIELD_NAME_LAYOUT_ID).getInt(null);

                sFragmentDelegateId = Class.forName(CLASS_NAME_ID_RES)
                        .getField(FIELD_NAME_FRAGMENT_ID).getInt(null);

                return new GoogleMapsPlatform();
            } catch (Exception ignored) {
                return null;
            }
        }
    }

    private static final class HuaweiMapsPlatform extends MapsPlatform {
        private static final String LIBRARY_PACKAGE_NAME =
                "dev.supasintatiyanupanwong.libraries.android.kits.maps.internal.huawei";

        private static final String CLASS_NAME_LAYOUT_RES = LIBRARY_PACKAGE_NAME + ".R$layout";

        private static final String CLASS_NAME_ID_RES = LIBRARY_PACKAGE_NAME + ".R$id";

        private static final String FIELD_NAME_LAYOUT_ID = "kits_maps_internal_huawei_map_view";

        private static final String FIELD_NAME_FRAGMENT_ID = "kits_maps_internal_map_fragment";

        private static MapFactory sFactory;
        private static int sFragmentLayoutId;
        private static int sFragmentDelegateId;

        private HuaweiMapsPlatform() {}

        @Override
        @NonNull MapFactory getFactory() {
            return sFactory;
        }

        @LayoutRes
        @Override
        int getFragmentLayoutId() {
            return sFragmentLayoutId;
        }

        @IdRes
        @Override
        int getFragmentDelegateId() {
            return sFragmentDelegateId;
        }


        static @Nullable HuaweiMapsPlatform buildIfSupported(@NonNull Context context) {
            try {
                sFactory = Objects.requireNonNull(
                        (MapFactory) Class
                                .forName(LIBRARY_PACKAGE_NAME + ".model.HuaweiMapFactory")
                                .getMethod("buildIfSupported", Context.class)
                                .invoke(null, context));

                sFragmentLayoutId = Class.forName(CLASS_NAME_LAYOUT_RES)
                        .getField(FIELD_NAME_LAYOUT_ID).getInt(null);

                sFragmentDelegateId = Class.forName(CLASS_NAME_ID_RES)
                        .getField(FIELD_NAME_FRAGMENT_ID).getInt(null);

                return new HuaweiMapsPlatform();
            } catch (Exception ignored) {
                return null;
            }
        }
    }

}
