package com.world_tech_points.modern_media;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.world_tech_points.modern_media.MediaCategory.MoviesFragment;
import com.world_tech_points.modern_media.MediaCategory.TV_ChannelFragment;
import com.world_tech_points.modern_media.MediaCategory.HomeFragment;
import com.world_tech_points.modern_media.MediaCategory.TrailersFragment;


public class PageViewerAdapter extends FragmentPagerAdapter {

    int position;

    public PageViewerAdapter(@NonNull FragmentManager fm, int position) {
        super(fm, position);
        this.position = position;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        Fragment fm = null;

        switch (position){
            case 0:
                fm = new HomeFragment();
                break;
            case 1:
                fm = new TV_ChannelFragment();
                break;
            case 2:
                fm = new MoviesFragment();
                break;
            case 3:
                fm = new TrailersFragment();
                break;
        }

        return fm;
    }

    @Override
    public int getCount() {
        return position;
    }
}
