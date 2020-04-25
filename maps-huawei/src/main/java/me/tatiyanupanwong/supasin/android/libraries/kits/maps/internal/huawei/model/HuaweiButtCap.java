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

package me.tatiyanupanwong.supasin.android.libraries.kits.maps.internal.huawei.model;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import me.tatiyanupanwong.supasin.android.libraries.kits.maps.model.ButtCap;

class HuaweiButtCap extends HuaweiCap implements ButtCap {

    private final com.huawei.hms.maps.model.ButtCap mDelegate;

    private HuaweiButtCap(com.huawei.hms.maps.model.ButtCap delegate) {
        mDelegate = delegate;
    }

    HuaweiButtCap() {
        this(new com.huawei.hms.maps.model.ButtCap());
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        HuaweiButtCap that = (HuaweiButtCap) obj;

        return mDelegate.equals(that.mDelegate);
    }

    @Override
    public int hashCode() {
        return mDelegate.hashCode();
    }

    @NonNull
    @Override
    public String toString() {
        return mDelegate.toString();
    }


    static ButtCap wrap(com.huawei.hms.maps.model.ButtCap delegate) {
        return new HuaweiButtCap(delegate);
    }

    static com.huawei.hms.maps.model.ButtCap unwrap(ButtCap wrapped) {
        return ((HuaweiButtCap) wrapped).mDelegate;
    }

}
