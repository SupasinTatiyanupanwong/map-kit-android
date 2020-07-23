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

import me.tatiyanupanwong.supasin.android.libraries.kits.maps.model.MapClient;
import me.tatiyanupanwong.supasin.android.libraries.kits.maps.model.Polyline;

/**
 * Keeps track of collections of polylines on the map. Delegates all {@link Polyline}-related
 * events to each collection's individually managed listeners.
 * <p/>
 * All polyline operations (adds and removes) should occur via its {@linkplain Collection
 * collection} class. That is, don't add a polyline via a collection, then remove it via {@link
 * Polyline#remove()}
 */
public class PolylineManager extends
        MapObjectManager<Polyline, PolylineManager.Collection> implements
        MapClient.OnPolylineClickListener {

    public PolylineManager(@NonNull MapClient map) {
        super(map);
    }

    @Override
    void setListenersOnUiThread() {
        if (mMap != null) {
            mMap.setOnPolylineClickListener(this);
        }
    }

    @Override
    public Collection newCollection() {
        return new Collection();
    }

    @Override
    protected void removeObjectFromMap(@NonNull Polyline object) {
        object.remove();
    }

    @Override
    public void onPolylineClick(@NonNull Polyline polyline) {
        Collection collection = mAllObjects.get(polyline);
        if (collection != null && collection.mPolylineClickListener != null) {
            collection.mPolylineClickListener.onPolylineClick(polyline);
        }
    }


    public class Collection extends MapObjectManager.Collection {
        private MapClient.OnPolylineClickListener mPolylineClickListener;

        public Collection() {}

        public Polyline addPolyline(Polyline.Options opts) {
            Polyline polyline = mMap.addPolyline(opts);
            super.add(polyline);
            return polyline;
        }

        public void addAll(java.util.Collection<Polyline.Options> opts) {
            for (Polyline.Options opt : opts) {
                addPolyline(opt);
            }
        }

        public void addAll(java.util.Collection<Polyline.Options> opts, boolean defaultVisible) {
            for (Polyline.Options opt : opts) {
                addPolyline(opt).setVisible(defaultVisible);
            }
        }

        public void showAll() {
            for (Polyline polyline : getPolylines()) {
                polyline.setVisible(true);
            }
        }

        public void hideAll() {
            for (Polyline polyline : getPolylines()) {
                polyline.setVisible(false);
            }
        }

        public boolean remove(Polyline polyline) {
            return super.remove(polyline);
        }

        public java.util.Collection<Polyline> getPolylines() {
            return getObjects();
        }

        public void setOnPolylineClickListener(
                @Nullable MapClient.OnPolylineClickListener polylineClickListener) {
            mPolylineClickListener = polylineClickListener;
        }
    }

}
