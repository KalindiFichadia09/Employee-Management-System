package com.example.autofusion;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;

public class admin_add_employee extends Fragment {
    Spinner Emp_Blood_Group,Emp_Job_Type;
    DatePicker Emp_Birth_Date,Emp_Date_of_Joining;
    String bgs,jts;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_admin_add_employee, container, false);

        //Blood Group...
        Emp_Blood_Group=view.findViewById(R.id.Emp_Blood_Group);
        String[] bg_s = getResources().getStringArray(R.array.blood_group);
        ArrayAdapter<String> bg_ad=new ArrayAdapter<>(requireContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,bg_s);
        Emp_Blood_Group.setAdapter(bg_ad);
        Emp_Blood_Group.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                bgs=bg_ad.getItem(i);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        Emp_Birth_Date=view.findViewById(R.id.Emp_Birth_Date);
        int Emp_Birth_Date_day = Emp_Birth_Date.getDayOfMonth();
        int Emp_Birth_Date_month = Emp_Birth_Date.getMonth();
        int Emp_Birth_Date_year = Emp_Birth_Date.getYear();
        String bdate = String.valueOf(Emp_Birth_Date_day+"-"+Emp_Birth_Date_month+"-"+Emp_Birth_Date_year);

        //Date_of_Joining
        Emp_Date_of_Joining=view.findViewById(R.id.Emp_Date_of_Joining);
        int Emp_Date_of_Joining_day = Emp_Date_of_Joining.getDayOfMonth();
        int Emp_Date_of_Joining_month = Emp_Date_of_Joining.getMonth();
        int Emp_Date_of_Joining_year = Emp_Date_of_Joining.getYear();
        String join_date = String.valueOf(Emp_Date_of_Joining_day+"-"+Emp_Date_of_Joining_month+"-"+Emp_Date_of_Joining_year);

        //Job Type
        Emp_Job_Type=view.findViewById(R.id.Emp_Job_Type);
        String[] jt_s = getResources().getStringArray(R.array.job_type);
        ArrayAdapter<String> jt_ad = new ArrayAdapter<>(requireContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,jt_s);
        Emp_Job_Type.setAdapter(jt_ad);
        Emp_Job_Type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                jts=jt_ad.getItem(i);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        return view;
    }
}