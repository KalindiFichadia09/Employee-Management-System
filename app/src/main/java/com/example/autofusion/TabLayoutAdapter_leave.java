package com.example.autofusion;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class TabLayoutAdapter_leave extends FragmentStateAdapter {
    public TabLayoutAdapter_leave(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 0){
            return new apply_leave();
        } else {
            return new see_leave();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
