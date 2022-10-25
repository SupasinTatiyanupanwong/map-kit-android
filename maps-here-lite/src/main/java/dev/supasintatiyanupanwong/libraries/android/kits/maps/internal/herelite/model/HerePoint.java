/*
 * Copyright 2022 Supasin Tatiyanupanwong
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
