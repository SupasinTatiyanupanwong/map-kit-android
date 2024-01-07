/*
 * Copyright 2024 Supasin Tatiyanupanwong
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

package dev.supasintatiyanupanwong.libraries.android.kits.maps.internal.huawei.model;

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
public class HuaweiMapStyle implements MapStyle {

    private HuaweiMapStyle() {}

    public static class Options implements MapStyle.Options {
        private final com.huawei.hms.maps.model.MapStyleOptions mDelegate;

        public Options(String json) {
            mDelegate = new com.huawei.hms.maps.model.MapStyleOptions(json);
        }

        public Options(@NonNull Context context, @RawRes int resourceId) {
            mDelegate = com.huawei.hms.maps.model.MapStyleOptions
                    .loadRawResourceStyle(context, resourceId);
        }

        @Override public boolean equals(@Nullable Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }

            HuaweiMapStyle.Options that = (HuaweiMapStyle.Options) obj;

            return mDelegate.equals(that.mDelegate);
        }

        @Override public int hashCode() {
            return mDelegate.hashCode();
        }

        @Override public @NonNull String toString() {
            return mDelegate.toString();
        }


        @Contract("null -> null; !null -> !null")
        static @Nullable com.huawei.hms.maps.model.MapStyleOptions unwrap(
                @Nullable MapClient.Style.Options wrapped
        ) {
            return wrapped == null ? null : ((HuaweiMapStyle.Options) wrapped).mDelegate;
        }
    }
}
