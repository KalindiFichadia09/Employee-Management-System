package com.example.autofusion;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

public class admin_add_holiday extends Fragment {

    DatePicker H_Start_Date,H_End_Date;
    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_admin_add_holiday, container, false);

        //Start Date
        H_Start_Date = view.findViewById(R.id.H_Start_Date);
        int E_Start_Date_day = H_Start_Date.getDayOfMonth();
        int E_Start_Date_month = H_Start_Date.getMonth();
        int E_Start_Date_year = H_Start_Date.getYear();
        String sdate = String.valueOf(E_Start_Date_day+"-"+E_Start_Date_month+"-"+E_Start_Date_year);


        //End Date
        H_End_Date = view.findViewById(R.id.H_End_Date);
        int E_End_Date_day = H_End_Date.getDayOfMonth();
        int E_End_Date_month = H_End_Date.getMonth();
        int E_End_Date_year = H_End_Date.getYear();
        String edate = String.valueOf(E_Start_Date_day+"-"+E_Start_Date_month+"-"+E_Start_Date_year);

        return view;
    }
}