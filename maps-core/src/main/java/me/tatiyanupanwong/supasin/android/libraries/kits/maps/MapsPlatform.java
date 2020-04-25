/*
 * Copyright (C) 2020 Supasin Tatiyanupanwong
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package me.tatiyanupanwong.supasin.android.libraries.kits.maps;

import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;

import java.lang.reflect.Constructor;

import me.tatiyanupanwong.supasin.android.libraries.kits.maps.model.Map;

abstract class MapsPlatform {
    private static final MapsPlatform PLATFORM = findPlatform();

    static MapsPlatform get() {
        return PLATFORM;
    }


    abstract Map.Factory getMapFactory();

    @LayoutRes
    abstract int getMapFragmentLayoutResId();

    @IdRes
    abstract int getDelegateMapFragmentId();


    private static MapsPlatform findPlatform() {
        MapsPlatform google = GoogleMapsPlatform.buildIfSupported();
        if (google != null) {
            return google;
        }

        MapsPlatform huawei = HuaweiMapsPlatform.buildIfSupported();
        if (huawei != null) {
            return huawei;
        }

        throw new IllegalStateException(
                "Can't find supported maps platform, make sure to include one of the next artifacts:"
                        + " \':maps-google\', or \':maps-huawei\'");
    }


    private static final class GoogleMapsPlatform extends MapsPlatform {
        private static final String LIBRARY_PACKAGE_NAME =
                "me.tatiyanupanwong.supasin.android.libraries.kits.maps.internal.google";

        private static final String CLASS_NAME_FACTORY =
                LIBRARY_PACKAGE_NAME + ".model.GoogleMap$Factory";

        private static final String CLASS_NAME_LAYOUT_RES = LIBRARY_PACKAGE_NAME + ".R$layout";

        private static final String CLASS_NAME_ID_RES = LIBRARY_PACKAGE_NAME + ".R$id";

        private static final String FIELD_NAME_LAYOUT_ID = "kits_maps_internal_google_map_view";

        private static final String FIELD_NAME_FRAGMENT_ID = "kits_maps_internal_map_fragment";

        private static Map.Factory sMapFactory;
        private static int sMapFragmentLayoutResId;
        private static int sDelegateMapFragmentId;

        @Override
        int getMapFragmentLayoutResId() {
            return sMapFragmentLayoutResId;
        }

        @Override
        int getDelegateMapFragmentId() {
            return sDelegateMapFragmentId;
        }

        @Override
        Map.Factory getMapFactory() {
            return sMapFactory;
        }


        @Nullable
        static MapsPlatform buildIfSupported() {
            try {
                //noinspection unchecked
                Constructor<Map.Factory> ctor =
                        ((Class<Map.Factory>) Class.forName(CLASS_NAME_FACTORY))
                                .getDeclaredConstructor();
                ctor.setAccessible(true);
                sMapFactory = ctor.newInstance();

                sMapFragmentLayoutResId = Class.forName(CLASS_NAME_LAYOUT_RES)
                        .getField(FIELD_NAME_LAYOUT_ID).getInt(null);

                sDelegateMapFragmentId = Class.forName(CLASS_NAME_ID_RES)
                        .getField(FIELD_NAME_FRAGMENT_ID).getInt(null);

                return new GoogleMapsPlatform();
            } catch (Exception ex) {
                return null;
            }
        }
    }

    private static final class HuaweiMapsPlatform extends MapsPlatform {
        private static final String LIBRARY_PACKAGE_NAME =
                "me.tatiyanupanwong.supasin.android.libraries.kits.maps.internal.huawei";

        private static final String CLASS_NAME_FACTORY =
                LIBRARY_PACKAGE_NAME + ".model.HuaweiMap$Factory";

        private static final String CLASS_NAME_LAYOUT_RES = LIBRARY_PACKAGE_NAME + ".R$layout";

        private static final String CLASS_NAME_ID_RES = LIBRARY_PACKAGE_NAME + ".R$id";

        private static final String FIELD_NAME_LAYOUT_ID = "kits_maps_internal_huawei_map_view";

        private static final String FIELD_NAME_FRAGMENT_ID = "kits_maps_internal_map_fragment";

        private static Map.Factory sMapFactory;
        private static int sMapFragmentLayoutResId;
        private static int sDelegateMapFragmentId;

        @Override
        int getMapFragmentLayoutResId() {
            return sMapFragmentLayoutResId;
        }

        @Override
        int getDelegateMapFragmentId() {
            return sDelegateMapFragmentId;
        }

        @Override
        Map.Factory getMapFactory() {
            return sMapFactory;
        }


        @Nullable
        static MapsPlatform buildIfSupported() {
            try {
                //noinspection unchecked
                Constructor<Map.Factory> ctor =
                        ((Class<Map.Factory>) Class.forName(CLASS_NAME_FACTORY))
                                .getDeclaredConstructor();
                ctor.setAccessible(true);
                sMapFactory = ctor.newInstance();

                sMapFragmentLayoutResId = Class.forName(CLASS_NAME_LAYOUT_RES)
                        .getField(FIELD_NAME_LAYOUT_ID).getInt(null);

                sDelegateMapFragmentId = Class.forName(CLASS_NAME_ID_RES)
                        .getField(FIELD_NAME_FRAGMENT_ID).getInt(null);

                return new HuaweiMapsPlatform();
            } catch (Exception ignored) {
                return null;
            }
        }
    }

}
