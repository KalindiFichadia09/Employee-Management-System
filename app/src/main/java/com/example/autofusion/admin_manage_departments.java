package com.example.autofusion;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;

import com.google.android.material.tabs.TabLayout;

public class admin_manage_departments extends Fragment {
    View view;
    TabLayout tablayout;
    ViewPager viewpager;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_admin_manage_departments,container,false);

        tablayout=view.findViewById(R.id.tablayout);
        viewpager=view.findViewById(R.id.viewpager);

        tablayoutAdapter_department adapter = new tablayoutAdapter_department(getChildFragmentManager());

        viewpager.setAdapter(adapter);
        tablayout.setupWithViewPager(viewpager);

        return view;
    }
}