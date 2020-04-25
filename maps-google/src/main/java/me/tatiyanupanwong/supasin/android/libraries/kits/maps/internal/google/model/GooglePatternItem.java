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

package me.tatiyanupanwong.supasin.android.libraries.kits.maps.internal.google.model;

import java.util.ArrayList;
import java.util.List;

import me.tatiyanupanwong.supasin.android.libraries.kits.maps.model.Dash;
import me.tatiyanupanwong.supasin.android.libraries.kits.maps.model.Dot;
import me.tatiyanupanwong.supasin.android.libraries.kits.maps.model.Gap;
import me.tatiyanupanwong.supasin.android.libraries.kits.maps.model.PatternItem;

abstract class GooglePatternItem implements PatternItem {

    private static PatternItem wrap(com.google.android.gms.maps.model.PatternItem delegate) {
        if (delegate instanceof com.google.android.gms.maps.model.Dash) {
            return GoogleDash.wrap((com.google.android.gms.maps.model.Dash) delegate);

        } else if (delegate instanceof com.google.android.gms.maps.model.Dot) {
            return GoogleDot.wrap((com.google.android.gms.maps.model.Dot) delegate);

        } else if (delegate instanceof com.google.android.gms.maps.model.Gap) {
            return GoogleGap.wrap((com.google.android.gms.maps.model.Gap) delegate);
        }

        throw new UnsupportedOperationException("Unsupported pattern type " + delegate.toString());
    }

    static List<PatternItem> wrap(List<com.google.android.gms.maps.model.PatternItem> delegates) {
        if (delegates == null) {
            return null;
        }

        List<PatternItem> list = new ArrayList<>();
        for (int iter = 0, size = delegates.size(); iter < size; iter++) {
            list.add(wrap(delegates.get(iter)));
        }
        return list;
    }

    private static com.google.android.gms.maps.model.PatternItem unwrap(PatternItem wrapped) {
        if (wrapped instanceof Dash) {
            return GoogleDash.unwrap((Dash) wrapped);

        } else if (wrapped instanceof Dot) {
            return GoogleDot.unwrap((Dot) wrapped);

        } else if (wrapped instanceof Gap) {
            return GoogleGap.unwrap((Gap) wrapped);
        }

        throw new UnsupportedOperationException("Unsupported pattern type " + wrapped.toString());
    }

    static List<com.google.android.gms.maps.model.PatternItem> unwrap(List<PatternItem> wrappeds) {
        if (wrappeds == null) {
            return null;
        }

        List<com.google.android.gms.maps.model.PatternItem> list = new ArrayList<>();
        for (int iter = 0, size = wrappeds.size(); iter < size; iter++) {
            list.add(unwrap(wrappeds.get(iter)));
        }
        return list;
    }

}
