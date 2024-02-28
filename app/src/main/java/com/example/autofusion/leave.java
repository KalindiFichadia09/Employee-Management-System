package com.example.autofusion;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;

public class leave extends Fragment {
    TabLayout tabLayout;
    ViewPager2 viewPager2;
    TabLayoutAdapter_leave adapter;
    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootview =  inflater.inflate(R.layout.fragment_profile, container, false);


        tabLayout = rootview.findViewById(R.id.tab_layout);
        viewPager2 = rootview.findViewById(R.id.view_pager);

        tabLayout.addTab(tabLayout.newTab().setText("Apply Leave"));
        tabLayout.addTab(tabLayout.newTab().setText("See Leave"));

        FragmentManager fragmentManager = getChildFragmentManager();
        adapter = new TabLayoutAdapter_leave(fragmentManager, getLifecycle());
        viewPager2.setAdapter(adapter);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager2.setCurrentItem(tab.getPosition());
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                tabLayout.selectTab(tabLayout.getTabAt(position));
            }
        });
        // Inflate the layout for this fragment
        return rootview;
    }
}