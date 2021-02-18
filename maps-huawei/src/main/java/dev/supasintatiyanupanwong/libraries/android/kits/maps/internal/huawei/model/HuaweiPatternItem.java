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

package dev.supasintatiyanupanwong.libraries.android.kits.maps.internal.huawei.model;

import java.util.ArrayList;
import java.util.List;

import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.Dash;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.Dot;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.Gap;
import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.PatternItem;

abstract class HuaweiPatternItem implements PatternItem {

    private static PatternItem wrap(com.huawei.hms.maps.model.PatternItem delegate) {
        if (delegate instanceof com.huawei.hms.maps.model.Dash) {
            return HuaweiDash.wrap((com.huawei.hms.maps.model.Dash) delegate);

        } else if (delegate instanceof com.huawei.hms.maps.model.Dot) {
            return HuaweiDot.wrap((com.huawei.hms.maps.model.Dot) delegate);

        } else if (delegate instanceof com.huawei.hms.maps.model.Gap) {
            return HuaweiGap.wrap((com.huawei.hms.maps.model.Gap) delegate);
        }

        throw new UnsupportedOperationException("Unsupported pattern type " + delegate.toString());
    }

    static List<PatternItem> wrap(List<com.huawei.hms.maps.model.PatternItem> delegates) {
        if (delegates == null) {
            return null;
        }

        List<PatternItem> list = new ArrayList<>();
        for (int iter = 0, size = delegates.size(); iter < size; iter++) {
            list.add(wrap(delegates.get(iter)));
        }
        return list;
    }

    private static com.huawei.hms.maps.model.PatternItem unwrap(PatternItem wrapped) {
        if (wrapped instanceof Dash) {
            return HuaweiDash.unwrap((Dash) wrapped);

        } else if (wrapped instanceof Dot) {
            return HuaweiDot.unwrap((Dot) wrapped);

        } else if (wrapped instanceof Gap) {
            return HuaweiGap.unwrap((Gap) wrapped);
        }

        throw new UnsupportedOperationException("Unsupported pattern type " + wrapped.toString());
    }

    static List<com.huawei.hms.maps.model.PatternItem> unwrap(List<PatternItem> wrappeds) {
        if (wrappeds == null) {
            return null;
        }

        List<com.huawei.hms.maps.model.PatternItem> list = new ArrayList<>();
        for (int iter = 0, size = wrappeds.size(); iter < size; iter++) {
            list.add(unwrap(wrappeds.get(iter)));
        }
        return list;
    }

}
