package com.example.clbootstrap.timeline;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class TimelinePagerAdapter extends FragmentStateAdapter {
    private static final int TAB_COUNT = 3;

    public TimelinePagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        // Create a new fragment based on the tab position
        switch (position) {
            case 0:
                return TimelineFragment.newInstance("following");
            case 1:
                return TimelineFragment.newInstance("my_activities");
            case 2:
                return TimelineFragment.newInstance("featured");
            default:
                return TimelineFragment.newInstance("following");
        }
    }

    @Override
    public int getItemCount() {
        return TAB_COUNT;
    }
}
