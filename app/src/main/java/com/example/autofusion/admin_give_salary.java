package com.example.autofusion;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.autofusion.databinding.FragmentAdminGiveSalaryBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class admin_give_salary extends Fragment {

    TextView Start_Date,End_Date;
    Spinner S_Emp_Email;
    private List<String> empNamesList;
    private ArrayAdapter<String> adapter;
    FirebaseFirestore afdb;
    View view;
    SharedPreferences sp;
    FragmentAdminGiveSalaryBinding gsb;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        gsb = FragmentAdminGiveSalaryBinding.inflate(inflater, container, false);
        view = gsb.getRoot();

        afdb = FirebaseFirestore.getInstance();

        S_Emp_Email = view.findViewById(R.id.S_Emp_Email);
        empNamesList = new ArrayList<>();
        adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, empNamesList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter.add("--Select Employee Email--");
        S_Emp_Email.setAdapter(adapter);
        fetchDataAndPopulateSpinner();

        Start_Date = view.findViewById(R.id.Start_Date);
        Start_Date.setOnClickListener(new View.OnClickListener() {
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
                                Start_Date.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                            }
                        },
                        year, month, day);
                datePickerDialog.show();
            }
        });

        End_Date = view.findViewById(R.id.End_Date);
        End_Date.setOnClickListener(new View.OnClickListener() {
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
                                End_Date.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                            }
                        },
                        year, month, day);
                datePickerDialog.show();
            }
        });

        gsb.btnGive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String SEmpEmail = gsb.SEmpEmail.getSelectedItem().toString();
                String StartDate = gsb.StartDate.getText().toString();
                String EndDate = gsb.EndDate.getText().toString();
                long SBasicPay = Long.parseLong(gsb.SBasicPay.getText().toString());
                long SAllowance = Long.parseLong(gsb.SAllowance.getText().toString());
                long SDeduction = Long.parseLong(gsb.SDeduction.getText().toString());
                long SAdjustment = Long.parseLong(gsb.SAdjustment.getText().toString());
                long STotalAmount = (SBasicPay+SAllowance+SAdjustment)-SDeduction;


                Map<String,Object> data = new HashMap<>();
                data.put("SEmpEmail",SEmpEmail);
                data.put("StartDate",StartDate);
                data.put("EndDate",EndDate);
                data.put("SBasicPay",SBasicPay);
                data.put("SAllowance",SAllowance);
                data.put("SDeduction",SDeduction);
                data.put("SAdjustment",SAdjustment);
                data.put("STotalAmount",STotalAmount);

                afdb.collection("Salary").document(SEmpEmail).set(data).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(getActivity(), "Leave Apply Successfully", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            };
        });

        return view;
    }
    private void fetchDataAndPopulateSpinner() {
        afdb.collection("Employee")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                            // Retrieve departmentName field from each document and add it to the list
                            String departmentName = documentSnapshot.getString("EmpCompanyEmail");
                            if (departmentName != null) {
                                empNamesList.add(departmentName);
                            }
                        }
                        adapter.notifyDataSetChanged(); // Notify the adapter of data change
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Handle any errors
                        Toast.makeText(getContext(), "Error fetching Employee: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}