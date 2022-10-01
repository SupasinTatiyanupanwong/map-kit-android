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

package dev.supasintatiyanupanwong.samples.android.kits.maps;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import dev.supasintatiyanupanwong.libraries.android.kits.maps.MapFragment;

/**
 * This shows how to add a map to a ViewPager. Note the use of
 * {@link ViewGroup#requestTransparentRegion(View)} to reduce jankiness.
 */
public class MapInPagerDemoActivity extends AppCompatActivity {

    /** Called when the activity is first created. */
    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_in_pager_demo);

        ViewPager pager = findViewById(R.id.pager);
        pager.setAdapter(new MyAdapter(getSupportFragmentManager()));

        // This is required to avoid a black flash when the map is loaded.  The flash is due
        // to the use of a SurfaceView as the underlying view of the map.
        pager.requestTransparentRegion(pager);
    }


    /** A simple fragment that displays a TextView. */
    public static class TextFragment extends Fragment {

        @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
            return inflater.inflate(R.layout.text_fragment, container, false);
        }
    }

    /** A simple FragmentPagerAdapter that returns two TextFragment and a MapFragment. */
    static class MyAdapter extends FragmentPagerAdapter {

        MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override public int getCount() {
            return 3;
        }

        @Override public @NonNull Fragment getItem(int position) {
            switch (position) {
                case 0:
                case 1:
                    return new TextFragment();
                case 2:
                    return MapFragment.newInstance();
                default:
                    throw new IllegalArgumentException();
            }
        }
    }

}
