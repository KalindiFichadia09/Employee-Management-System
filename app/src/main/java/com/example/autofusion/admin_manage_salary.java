package com.example.autofusion;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;

public class admin_manage_salary extends Fragment {

    TabLayout tablayout;
    ViewPager viewpager;
    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_admin_manage_salary, container, false);

        tablayout = view.findViewById(R.id.tablayout);
        viewpager = view.findViewById(R.id.viewpager);

        tablayoutAdapter_salary adapter = new tablayoutAdapter_salary(getChildFragmentManager());

        viewpager.setAdapter(adapter);
        tablayout.setupWithViewPager(viewpager);

        return view;
    }
}