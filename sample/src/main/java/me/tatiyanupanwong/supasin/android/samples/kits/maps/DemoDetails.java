/*
 * Copyright 2020 Supasin Tatiyanupanwong
 * Copyright 2012 The Android Open Source Project
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

package me.tatiyanupanwong.supasin.android.samples.kits.maps;

import android.app.Activity;

/**
 * A simple POJO that holds the details about the demo that are used by the List Adapter.
 */
class DemoDetails {

    private final int mTitleId;

    private final int mDescriptionId;

    private final Class<? extends Activity> mActivityClass;

    DemoDetails(int titleId, int descriptionId, Class<? extends Activity> activityClass) {
        mTitleId = titleId;
        mDescriptionId = descriptionId;
        mActivityClass = activityClass;
    }

    /**
     * The resource id of the title of the demo.
     */
    public int getTitleId() {
        return mTitleId;
    }

    /**
     * The resources id of the description of the demo.
     */
    public int getDescriptionId() {
        return mDescriptionId;
    }

    /**
     * The demo activity's class.
     */
    public Class<? extends Activity> getActivityClass() {
        return mActivityClass;
    }
}
