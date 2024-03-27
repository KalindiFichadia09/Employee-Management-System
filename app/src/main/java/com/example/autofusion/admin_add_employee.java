package com.example.autofusion;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.autofusion.databinding.FragmentAdminAddDepartmentBinding;
import com.example.autofusion.databinding.FragmentAdminAddEmployeeBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class admin_add_employee extends Fragment {
    Spinner Emp_Blood_Group,Emp_Job_Type;
    DatePicker Emp_Birth_Date,Emp_Date_of_Joining;
    String bgs,jts;
    View view;
    String EmpGender;

    FragmentAdminAddEmployeeBinding emp;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       emp = FragmentAdminAddEmployeeBinding.inflate(inflater, container, false);
       view = emp.getRoot();

        int gen;

        gen = emp.EmpGender.getCheckedRadioButtonId();
        RadioButton getgen = getView().findViewById(gen);

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

        //Birth date
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

        FirebaseFirestore afdb = FirebaseFirestore.getInstance();
        emp.AddEmp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String EmpFullName = emp.EmpFullName.getText().toString();
                String EmpFatherName = emp.EmpFatherName.getText().toString();
                String EmpMotherName = emp.EmpMotherName.getText().toString();
                String EmpGenger = getgen.getText().toString();
                String EmpBirthDate = String.valueOf(Emp_Birth_Date_day+"-"+Emp_Birth_Date_month+"-"+Emp_Birth_Date_year);
                String EmpAaddarNo = emp.EmpAaddarNo.getText().toString();
                String EmpBloodGroup = emp.EmpBloodGroup.getSelectedItem().toString();
                String EmpAge = emp.EmpAge.getText().toString();
                String EmpMobileNo = emp.EmpMobileNo.getText().toString();
                String EmpEmail = emp.EmpEmail.getText().toString();

                String EmpEmployeeId = emp.EmpEmployeeId.getText().toString();
                String EmpDepartmentName = emp.EmpDepartmentName.getSelectedItem().toString();
                
//                String EmpEmail = emp.EmpEmail.getText().toString();
//                String EmpEmail = emp.EmpEmail.getText().toString();
//                String EmpEmail = emp.EmpEmail.getText().toString();
//                String EmpEmail = emp.EmpEmail.getText().toString();
//                String EmpEmail = emp.EmpEmail.getText().toString();
//                String EmpEmail = emp.EmpEmail.getText().toString();
//                String EmpEmail = emp.EmpEmail.getText().toString();
//                String EmpEmail = emp.EmpEmail.getText().toString();


                //Insert
                Map<String,Object> data = new HashMap<>();
                data.put("Emp_Full_Name",EmpFullName);
                data.put("Emp_Father_Name",EmpFatherName);
                afdb.collection("Department").document(EmpFullName).set(data).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(getActivity(), "Added Successfully", Toast.LENGTH_SHORT).show();
//                                startActivity(new Intent(getActivity(),MainActivity.class));
                        }
                    }
                });
            }
        });
        return view;
    }
}