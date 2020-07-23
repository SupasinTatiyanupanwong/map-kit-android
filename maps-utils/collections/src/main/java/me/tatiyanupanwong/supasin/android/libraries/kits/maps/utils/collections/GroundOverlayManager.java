/*
 * Copyright (C) 2020 Supasin Tatiyanupanwong
 * Copyright (C) 2017 Google Inc.
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

package me.tatiyanupanwong.supasin.android.libraries.kits.maps.utils.collections;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import me.tatiyanupanwong.supasin.android.libraries.kits.maps.model.GroundOverlay;
import me.tatiyanupanwong.supasin.android.libraries.kits.maps.model.MapClient;

/**
 * Keeps track of collections of ground overlays on the map. Delegates all {@link
 * GroundOverlay}-related events to each collection's individually managed listeners.
 * <p/>
 * All ground overlay operations (adds and removes) should occur via its {@linkplain Collection
 * collection} class. That is, don't add a ground overlay via a collection, then remove it via
 * {@link GroundOverlay#remove()}
 */
public class GroundOverlayManager extends
        MapObjectManager<GroundOverlay, GroundOverlayManager.Collection> implements
        MapClient.OnGroundOverlayClickListener {

    public GroundOverlayManager(@NonNull MapClient map) {
        super(map);
    }

    @Override
    void setListenersOnUiThread() {
        if (mMap != null) {
            mMap.setOnGroundOverlayClickListener(this);
        }
    }

    @Override
    public Collection newCollection() {
        return new Collection();
    }

    @Override
    protected void removeObjectFromMap(@NonNull GroundOverlay object) {
        object.remove();
    }

    @Override
    public void onGroundOverlayClick(@NonNull GroundOverlay groundOverlay) {
        Collection collection = mAllObjects.get(groundOverlay);
        if (collection != null && collection.mGroundOverlayClickListener != null) {
            collection.mGroundOverlayClickListener.onGroundOverlayClick(groundOverlay);
        }
    }


    public class Collection extends MapObjectManager.Collection {
        private MapClient.OnGroundOverlayClickListener mGroundOverlayClickListener;

        public Collection() {}

        public GroundOverlay addGroundOverlay(GroundOverlay.Options opts) {
            GroundOverlay groundOverlay = mMap.addGroundOverlay(opts);
            super.add(groundOverlay);
            return groundOverlay;
        }

        public void addAll(java.util.Collection<GroundOverlay.Options> opts) {
            for (GroundOverlay.Options opt : opts) {
                addGroundOverlay(opt);
            }
        }

        public void addAll(
                java.util.Collection<GroundOverlay.Options> opts, boolean defaultVisible) {
            for (GroundOverlay.Options opt : opts) {
                addGroundOverlay(opt).setVisible(defaultVisible);
            }
        }

        public void showAll() {
            for (GroundOverlay groundOverlay : getGroundOverlays()) {
                groundOverlay.setVisible(true);
            }
        }

        public void hideAll() {
            for (GroundOverlay groundOverlay : getGroundOverlays()) {
                groundOverlay.setVisible(false);
            }
        }

        public boolean remove(GroundOverlay groundOverlay) {
            return super.remove(groundOverlay);
        }

        public java.util.Collection<GroundOverlay> getGroundOverlays() {
            return getObjects();
        }

        public void setOnGroundOverlayClickListener(
                @Nullable MapClient.OnGroundOverlayClickListener groundOverlayClickListener) {
            mGroundOverlayClickListener = groundOverlayClickListener;
        }
    }

}
