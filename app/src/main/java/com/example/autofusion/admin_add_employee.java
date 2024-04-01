package com.example.autofusion;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
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
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.autofusion.databinding.FragmentAdminAddDepartmentBinding;
import com.example.autofusion.databinding.FragmentAdminAddEmployeeBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class admin_add_employee extends Fragment {
    Spinner Emp_Blood_Group,Emp_Job_Type,Emp_Department;
    TextView Emp_Birth_Date,Emp_Join_Date;
    RadioGroup Emp_Gender;
    RadioButton gen;
    String bgs,jts,Emp_Gen;
    View view;
    private List<String> departmentNamesList;
    private ArrayAdapter<String> adapter;
    FirebaseFirestore afdb = FirebaseFirestore.getInstance();
    long dob;
    FragmentAdminAddEmployeeBinding emp;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       emp = FragmentAdminAddEmployeeBinding.inflate(inflater, container, false);
       view = emp.getRoot();

       Emp_Gender = view.findViewById(R.id.Emp_Gender);
        Emp_Gender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                gen=view.findViewById(i);
                String sel=gen.getText().toString();
                if(sel.equals("Male"))
                    Emp_Gen = "Male";
                if (sel.equals("Female"))
                    Emp_Gen = "Female";
            }
        });

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
        Emp_Birth_Date = view.findViewById(R.id.Emp_Birth_Date);
        Emp_Birth_Date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();

                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                // on below line we are creating a variable for date picker dialog.
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        requireContext(),
                        new DatePickerDialog.OnDateSetListener() {
                            @SuppressLint("SetTextI18n")
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // on below line we are setting date to our text view.
                                Emp_Birth_Date.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                            }
                        },
                        year, month, day);
                datePickerDialog.show();
            }
        });



        //Date_of_Joining
        Emp_Join_Date=view.findViewById(R.id.Emp_Join_Date);
        Emp_Join_Date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();

                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                // on below line we are creating a variable for date picker dialog.
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        requireContext(),
                        new DatePickerDialog.OnDateSetListener() {
                            @SuppressLint("SetTextI18n")
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // on below line we are setting date to our text view.
                                Emp_Join_Date.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                            }
                        },
                        year, month, day);
                datePickerDialog.show();
            }
        });


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

        //Department Spinner
        Emp_Department = view.findViewById(R.id.Emp_Department_Name);
        departmentNamesList = new ArrayList<>();
        adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, departmentNamesList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter.add("--Select Department--");
        Emp_Department.setAdapter(adapter);
        fetchDataAndPopulateSpinner();


        //fetch values from form and add in firestore
        emp.AddEmp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String EmpFullName = emp.EmpFullName.getText().toString();
                String EmpFatherName = emp.EmpFatherName.getText().toString();
                String EmpMotherName = emp.EmpMotherName.getText().toString();
                String EmpGender = Emp_Gen.toString();
                String EmpBirthDate = Emp_Birth_Date.getText().toString();
                String EmpAaddarNo = emp.EmpAaddarNo.getText().toString();
                String EmpBloodGroup = emp.EmpBloodGroup.getSelectedItem().toString();
                String EmpAge = emp.EmpAge.getText().toString();
                String EmpMobileNo = emp.EmpMobileNo.getText().toString();
                String EmpEmail = emp.EmpEmail.getText().toString();

                String EmpEmployeeId = emp.EmpEmployeeId.getText().toString();
                String EmpDepartmentName = emp.EmpDepartmentName.getSelectedItem().toString();
                String EmpJoinDate = emp.EmpJoinDate.getText().toString();
                String EmpCompanyEmail = emp.EmpCompanyEmail.getText().toString();
                String EmpJobType = emp.EmpJobType.getSelectedItem().toString();
                String EmpPassword = emp.EmpPassword.getText().toString();

                String EmpAccountHolderName = emp.EmpAccountHolderName.getText().toString();
                String EmpBankName = emp.EmpBankName.getText().toString();
                String EmpAccountNumber = emp.EmpAccountNumber.getText().toString();
                String EmpIFSCCode = emp.EmpIFSCCode.getText().toString();
                String EmpBankCode = emp.EmpBankCode.getText().toString();
                String EmpBranchName = emp.EmpBranchName.getText().toString();

                String EmpAddress = emp.EmpAddress.getText().toString();
                String EmpCity = emp.EmpCity.getText().toString();
                String EmpState = emp.EmpState.getText().toString();
                String EmpCountry = emp.EmpCountry.getText().toString();
                String EmpPincode = emp.EmpPincode.getText().toString();
                String EmpEmergencyNo = emp.EmpEmergencyNo.getText().toString();

                //Insert
                Map<String,Object> data = new HashMap<>();
                data.put("EmpFullName",EmpFullName);
                data.put("EmpFatherName",EmpFatherName);
                data.put("EmpMotherName",EmpMotherName);
                data.put("EmpGender",EmpGender);
                data.put("EmpBirthDate",EmpBirthDate);
                data.put("EmpAaddarNo",EmpAaddarNo);
                data.put("EmpBloodGroup",EmpBloodGroup);
                data.put("EmpAge",EmpAge);
                data.put("EmpMobileNo",EmpMobileNo);
                data.put("EmpEmail",EmpEmail);

                data.put("EmpEmployeeId",EmpEmployeeId);
                data.put("EmpDepartmentName",EmpDepartmentName);
                data.put("EmpJoinDate",EmpJoinDate);
                data.put("EmpCompanyEmail",EmpCompanyEmail);
                data.put("EmpJobType",EmpJobType);
                data.put("EmpPassword",EmpPassword);

                data.put("EmpAccountHolderName",EmpAccountHolderName);
                data.put("EmpBankName",EmpBankName);
                data.put("EmpAccountNumber",EmpAccountNumber);
                data.put("EmpIFSCCode",EmpIFSCCode);
                data.put("EmpBankCode",EmpBankCode);
                data.put("EmpBranchName",EmpBranchName);

                data.put("EmpAddress",EmpAddress);
                data.put("EmpCity",EmpCity);
                data.put("EmpState",EmpState);
                data.put("EmpCountry",EmpCountry);
                data.put("EmpPincode",EmpPincode);
                data.put("EmpEmergencyNo",EmpEmergencyNo);

                afdb.collection("Employee").document(EmpCompanyEmail).set(data).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(getActivity(), "Employee Added Successfully", Toast.LENGTH_SHORT).show();
//                                startActivity(new Intent(getActivity(),MainActivity.class));
                        }
                    }
                });
            }
        });
        return view;
    }

    private void fetchDataAndPopulateSpinner() {
        afdb.collection("Department")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                            // Retrieve departmentName field from each document and add it to the list
                            String departmentName = documentSnapshot.getString("Dept_Name");
                            if (departmentName != null) {
                                departmentNamesList.add(departmentName);
                            }
                        }
                        adapter.notifyDataSetChanged(); // Notify the adapter of data change
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Handle any errors
                        Toast.makeText(getContext(), "Error fetching Departments: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}