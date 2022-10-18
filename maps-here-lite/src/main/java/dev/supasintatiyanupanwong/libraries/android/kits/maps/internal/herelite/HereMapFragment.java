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

package dev.supasintatiyanupanwong.libraries.android.kits.maps.internal.herelite;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.here.sdk.mapviewlite.MapStyle;
import com.here.sdk.mapviewlite.MapViewLite;

public final class HereMapFragment extends Fragment {

    @Override public @NonNull View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState
    ) {
        return new MapViewLite(requireContext());
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        final @NonNull MapViewLite mapView = (MapViewLite) view;
        mapView.onCreate(savedInstanceState);
        mapView.getMapScene().loadScene(MapStyle.NORMAL_DAY, null);
    }

    @Override
    public void onResume() {
        super.onResume();

        final @Nullable View view = getView();
        if (view instanceof MapViewLite) {
            ((MapViewLite) view).onResume();
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        final @Nullable View view = getView();
        if (view instanceof MapViewLite) {
            ((MapViewLite) view).onSaveInstanceState();
        }
    }

    @Override
    public void onPause() {
        final @Nullable View view = getView();
        if (view instanceof MapViewLite) {
            ((MapViewLite) view).onPause();
        }

        super.onPause();
    }

    @Override
    public void onLowMemory() {
        final @Nullable View view = getView();
        if (view instanceof MapViewLite) {
            ((MapViewLite) view).onLowMemory();
        }

        super.onLowMemory();
    }

    @Override
    public void onDestroyView() {
        final @Nullable View view = getView();
        if (view instanceof MapViewLite) {
            ((MapViewLite) view).onDestroy();
        }

        super.onDestroyView();
    }

}
