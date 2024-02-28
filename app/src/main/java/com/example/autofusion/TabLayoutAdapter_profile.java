package com.example.autofusion;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class TabLayoutAdapter_profile extends FragmentStateAdapter {
    public TabLayoutAdapter_profile(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 0){
            return new personal_detail();
        } else if (position==1) {
            return new company_detail();
        } else if (position==2) {
            return new bank_detail();
        }else {
            return new contact_detail();
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}