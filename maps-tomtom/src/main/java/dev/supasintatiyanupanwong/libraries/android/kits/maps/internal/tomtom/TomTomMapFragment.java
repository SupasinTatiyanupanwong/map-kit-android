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

package dev.supasintatiyanupanwong.libraries.android.kits.maps.internal.tomtom;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.tomtom.sdk.maps.display.MapOptions;
import com.tomtom.sdk.maps.display.ui.MapView;

public final class TomTomMapFragment extends Fragment {

    @Override public @NonNull View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState
    ) {
        return new MapView(requireContext(), new MapOptions("TODO"));
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        ((MapView) view).onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();

        final @Nullable View view = getView();
        if (view instanceof MapView) {
            ((MapView) view).onStart();
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        final @Nullable View view = getView();
        if (view instanceof MapView) {
            ((MapView) view).onResume();
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        final @Nullable View view = getView();
        if (view instanceof MapView) {
            ((MapView) view).onSaveInstanceState(outState);
        }
    }

    @Override
    public void onPause() {
        final @Nullable View view = getView();
        if (view instanceof MapView) {
            ((MapView) view).onPause();
        }

        super.onPause();
    }

    @Override
    public void onStop() {
        final @Nullable View view = getView();
        if (view instanceof MapView) {
            ((MapView) view).onStop();
        }

        super.onStop();
    }

    @Override
    public void onDestroyView() {
        final @Nullable View view = getView();
        if (view instanceof MapView) {
            ((MapView) view).onDestroy();
        }

        super.onDestroyView();
    }

}
