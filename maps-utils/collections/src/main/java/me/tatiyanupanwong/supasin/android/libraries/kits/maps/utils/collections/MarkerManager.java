/*
 * Copyright (C) 2020 Supasin Tatiyanupanwong
 * Copyright (C) 2013 Google Inc.
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

import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import me.tatiyanupanwong.supasin.android.libraries.kits.maps.model.MapClient;
import me.tatiyanupanwong.supasin.android.libraries.kits.maps.model.Marker;

/**
 * Keeps track of collections of markers on the map. Delegates all {@link Marker}-related events
 * to each collection's individually managed listeners.
 * <p/>
 * All marker operations (adds and removes) should occur via its {@linkplain Collection collection}
 * class. That is, don't add a marker via a collection, then remove it via {@link Marker#remove()}
 */
public class MarkerManager extends
        MapObjectManager<Marker, MarkerManager.Collection> implements
        MapClient.OnInfoWindowClickListener,
        MapClient.OnInfoWindowLongClickListener,
        MapClient.OnMarkerClickListener,
        MapClient.OnMarkerDragListener,
        MapClient.InfoWindowAdapter {

    public MarkerManager(MapClient map) {
        super(map);
    }

    @Override
    void setListenersOnUiThread() {
        if (mMap != null) {
            mMap.setOnInfoWindowClickListener(this);
            mMap.setOnInfoWindowLongClickListener(this);
            mMap.setOnMarkerClickListener(this);
            mMap.setOnMarkerDragListener(this);
            mMap.setInfoWindowAdapter(this);
        }
    }

    public Collection newCollection() {
        return new Collection();
    }

    @Override
    public View getInfoWindow(@NonNull Marker marker) {
        Collection collection = mAllObjects.get(marker);
        if (collection != null && collection.mInfoWindowAdapter != null) {
            return collection.mInfoWindowAdapter.getInfoWindow(marker);
        }
        return null;
    }

    @Override
    public View getInfoContents(@NonNull Marker marker) {
        Collection collection = mAllObjects.get(marker);
        if (collection != null && collection.mInfoWindowAdapter != null) {
            return collection.mInfoWindowAdapter.getInfoContents(marker);
        }
        return null;
    }

    @Override
    public void onInfoWindowClick(@NonNull Marker marker) {
        Collection collection = mAllObjects.get(marker);
        if (collection != null && collection.mInfoWindowClickListener != null) {
            collection.mInfoWindowClickListener.onInfoWindowClick(marker);
        }
    }

    @Override
    public void onInfoWindowLongClick(@NonNull Marker marker) {
        Collection collection = mAllObjects.get(marker);
        if (collection != null && collection.mInfoWindowLongClickListener != null) {
            collection.mInfoWindowLongClickListener.onInfoWindowLongClick(marker);
        }
    }

    @Override
    public boolean onMarkerClick(@NonNull Marker marker) {
        Collection collection = mAllObjects.get(marker);
        if (collection != null && collection.mMarkerClickListener != null) {
            return collection.mMarkerClickListener.onMarkerClick(marker);
        }
        return false;
    }

    @Override
    public void onMarkerDragStart(@NonNull Marker marker) {
        Collection collection = mAllObjects.get(marker);
        if (collection != null && collection.mMarkerDragListener != null) {
            collection.mMarkerDragListener.onMarkerDragStart(marker);
        }
    }

    @Override
    public void onMarkerDrag(@NonNull Marker marker) {
        Collection collection = mAllObjects.get(marker);
        if (collection != null && collection.mMarkerDragListener != null) {
            collection.mMarkerDragListener.onMarkerDrag(marker);
        }
    }

    @Override
    public void onMarkerDragEnd(@NonNull Marker marker) {
        Collection collection = mAllObjects.get(marker);
        if (collection != null && collection.mMarkerDragListener != null) {
            collection.mMarkerDragListener.onMarkerDragEnd(marker);
        }
    }

    @Override
    protected void removeObjectFromMap(@NonNull Marker object) {
        object.remove();
    }


    public class Collection extends MapObjectManager.Collection {
        private MapClient.OnInfoWindowClickListener mInfoWindowClickListener;
        private MapClient.OnInfoWindowLongClickListener mInfoWindowLongClickListener;
        private MapClient.OnMarkerClickListener mMarkerClickListener;
        private MapClient.OnMarkerDragListener mMarkerDragListener;
        private MapClient.InfoWindowAdapter mInfoWindowAdapter;

        public Collection() {}

        public Marker addMarker(Marker.Options opts) {
            Marker marker = mMap.addMarker(opts);
            super.add(marker);
            return marker;
        }

        public void addAll(java.util.Collection<Marker.Options> opts) {
            for (Marker.Options opt : opts) {
                addMarker(opt);
            }
        }

        public void addAll(java.util.Collection<Marker.Options> opts, boolean defaultVisible) {
            for (Marker.Options opt : opts) {
                addMarker(opt).setVisible(defaultVisible);
            }
        }

        public void showAll() {
            for (Marker marker : getMarkers()) {
                marker.setVisible(true);
            }
        }

        public void hideAll() {
            for (Marker marker : getMarkers()) {
                marker.setVisible(false);
            }
        }

        public boolean remove(Marker marker) {
            return super.remove(marker);
        }

        public java.util.Collection<Marker> getMarkers() {
            return getObjects();
        }

        public void setOnInfoWindowClickListener(
                @Nullable MapClient.OnInfoWindowClickListener infoWindowClickListener) {
            mInfoWindowClickListener = infoWindowClickListener;
        }

        public void setOnInfoWindowLongClickListener(
                @Nullable MapClient.OnInfoWindowLongClickListener infoWindowLongClickListener) {
            mInfoWindowLongClickListener = infoWindowLongClickListener;
        }

        public void setOnMarkerClickListener(
                @Nullable MapClient.OnMarkerClickListener markerClickListener) {
            mMarkerClickListener = markerClickListener;
        }

        public void setOnMarkerDragListener(
                @Nullable MapClient.OnMarkerDragListener markerDragListener) {
            mMarkerDragListener = markerDragListener;
        }

        public void setInfoWindowAdapter(
                @Nullable MapClient.InfoWindowAdapter infoWindowAdapter) {
            mInfoWindowAdapter = infoWindowAdapter;
        }
    }

}
