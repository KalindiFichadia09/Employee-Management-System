package com.example.autofusion;

import static android.app.Activity.RESULT_OK;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

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
    private static final int REQUEST_IMAGE_PICK = 1;
    private StorageReference storageRef = FirebaseStorage.getInstance().getReference();
    Uri imageUri;
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

        //Image
        emp.EmpImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                selectImageFromGallery();

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

                String EmpFullName = emp.EmpFullName.getText().toString().trim();
                String EmpFatherName = emp.EmpFatherName.getText().toString().trim();
                String EmpMotherName = emp.EmpMotherName.getText().toString().trim();
                String EmpGender = Emp_Gen.toString();
                String EmpBirthDate = Emp_Birth_Date.getText().toString();
                String EmpAaddarNo = emp.EmpAaddarNo.getText().toString().trim();
                String EmpBloodGroup = emp.EmpBloodGroup.getSelectedItem().toString();
                String EmpAge = emp.EmpAge.getText().toString().trim();
                String EmpMobileNo = emp.EmpMobileNo.getText().toString().trim();
                String EmpEmail = emp.EmpEmail.getText().toString().trim();

                String EmpEmployeeId = emp.EmpEmployeeId.getText().toString().trim();
                String EmpDepartmentName = emp.EmpDepartmentName.getSelectedItem().toString();
                String EmpJoinDate = emp.EmpJoinDate.getText().toString();
                String EmpCompanyEmail = emp.EmpCompanyEmail.getText().toString().trim();
                String EmpJobType = emp.EmpJobType.getSelectedItem().toString();
                String EmpPassword = emp.EmpPassword.getText().toString().trim();

                String EmpAccountHolderName = emp.EmpAccountHolderName.getText().toString().trim();
                String EmpBankName = emp.EmpBankName.getText().toString().trim();
                String EmpAccountNumber = emp.EmpAccountNumber.getText().toString().trim();
                String EmpIFSCCode = emp.EmpIFSCCode.getText().toString().trim();
                String EmpBankCode = emp.EmpBankCode.getText().toString().trim();
                String EmpBranchName = emp.EmpBranchName.getText().toString().trim();

                String EmpAddress = emp.EmpAddress.getText().toString().trim();
                String EmpCity = emp.EmpCity.getText().toString().trim();
                String EmpState = emp.EmpState.getText().toString().trim();
                String EmpCountry = emp.EmpCountry.getText().toString().trim();
                String EmpPincode = emp.EmpPincode.getText().toString().trim();
                String EmpEmergencyNo = emp.EmpEmergencyNo.getText().toString().trim();

                if (imageUri != null) {
                     StorageReference imageRef = storageRef.child("images/" + System.currentTimeMillis() + ".jpg");
                    imageRef.putFile(imageUri)
                            .addOnSuccessListener(taskSnapshot -> {
                                imageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                                    String imageUrl = uri.toString();

                                    //Insert
                                    Map<String, Object> data = new HashMap<>();
                                    data.put("EmpFullName", EmpFullName);
                                    data.put("EmpFatherName", EmpFatherName);
                                    data.put("EmpMotherName", EmpMotherName);
                                    data.put("EmpGender", EmpGender);
                                    data.put("EmpBirthDate", EmpBirthDate);
                                    data.put("EmpAaddarNo", EmpAaddarNo);
                                    data.put("EmpBloodGroup", EmpBloodGroup);
                                    data.put("EmpAge", EmpAge);
                                    data.put("EmpMobileNo", EmpMobileNo);
                                    data.put("EmpEmail", EmpEmail);
                                    data.put("EmpImage",imageUrl);

                                    data.put("EmpEmployeeId", EmpEmployeeId);
                                    data.put("EmpDepartmentName", EmpDepartmentName);
                                    data.put("EmpJoinDate", EmpJoinDate);
                                    data.put("EmpCompanyEmail", EmpCompanyEmail);
                                    data.put("EmpJobType", EmpJobType);
                                    data.put("EmpPassword", EmpPassword);

                                    data.put("EmpAccountHolderName", EmpAccountHolderName);
                                    data.put("EmpBankName", EmpBankName);
                                    data.put("EmpAccountNumber", EmpAccountNumber);
                                    data.put("EmpIFSCCode", EmpIFSCCode);
                                    data.put("EmpBankCode", EmpBankCode);
                                    data.put("EmpBranchName", EmpBranchName);

                                    data.put("EmpAddress", EmpAddress);
                                    data.put("EmpCity", EmpCity);
                                    data.put("EmpState", EmpState);
                                    data.put("EmpCountry", EmpCountry);
                                    data.put("EmpPincode", EmpPincode);
                                    data.put("EmpEmergencyNo", EmpEmergencyNo);

                                    afdb.collection("Employee").document(EmpCompanyEmail).set(data).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(getActivity(), "Employee Added Successfully", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                });
                            })
                            .addOnFailureListener(e -> {
                                Toast.makeText(requireContext(), "Failed to upload image", Toast.LENGTH_SHORT).show();
                            });
                }
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
    private void selectImageFromGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQUEST_IMAGE_PICK);
    }
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_PICK && resultCode == RESULT_OK) {
            assert data != null;
            imageUri = data.getData();
        }
    }
}