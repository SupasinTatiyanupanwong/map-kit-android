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

import static androidx.annotation.RestrictTo.Scope.LIBRARY;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;

import dev.supasintatiyanupanwong.libraries.android.kits.maps.model.Dot;

@RestrictTo(LIBRARY)
public class HuaweiDot extends HuaweiPatternItem implements Dot {

    private final com.huawei.hms.maps.model.Dot mDelegate;

    private HuaweiDot(com.huawei.hms.maps.model.Dot delegate) {
        mDelegate = delegate;
    }

    public HuaweiDot() {
        this(new com.huawei.hms.maps.model.Dot());
    }

    @Override public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        HuaweiDot that = (HuaweiDot) obj;

        return mDelegate.equals(that.mDelegate);
    }

    @Override public int hashCode() {
        return mDelegate.hashCode();
    }

    @Override public @NonNull String toString() {
        return mDelegate.toString();
    }


    static Dot wrap(com.huawei.hms.maps.model.Dot delegate) {
        return new HuaweiDot(delegate);
    }

    static com.huawei.hms.maps.model.Dot unwrap(Dot wrapped) {
        return ((HuaweiDot) wrapped).mDelegate;
    }

}
