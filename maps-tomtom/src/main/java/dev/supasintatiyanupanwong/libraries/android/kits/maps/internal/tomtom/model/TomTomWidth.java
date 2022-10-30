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

package dev.supasintatiyanupanwong.libraries.android.kits.maps.internal.tomtom.model;

import androidx.annotation.NonNull;

import com.tomtom.sdk.maps.display.common.WidthByZoom;

import java.util.Collections;
import java.util.List;

final class TomTomWidth {

    private TomTomWidth() {}


    static float wrap(@NonNull List<WidthByZoom> delegate) {
        return delegate.isEmpty() ? 0 : (float) delegate.get(0).getZoom();
    }

    static @NonNull List<WidthByZoom> unwrap(float wrapped) {
        return Collections.singletonList(new WidthByZoom(wrapped, WidthByZoom.DEFAULT_ZOOM_LEVEL));
    }

}
