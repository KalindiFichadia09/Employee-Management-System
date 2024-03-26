package com.example.autofusion;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

public class admin_assign_task extends Fragment {

    DatePicker At_End_Date;
    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_admin_assign_task, container, false);

        //End Date
        At_End_Date = view.findViewById(R.id.At_End_Date);
        int At_End_Date_day = At_End_Date.getDayOfMonth();
        int At_End_Date_month = At_End_Date.getMonth();
        int At_End_Date_year = At_End_Date.getYear();
        String bdate = String.valueOf(At_End_Date_day+"-"+At_End_Date_month+"-"+At_End_Date_year);

        return view;
    }
}