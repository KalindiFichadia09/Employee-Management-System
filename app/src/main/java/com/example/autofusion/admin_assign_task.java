package com.example.autofusion;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import com.example.autofusion.databinding.FragmentAdminAssignTaskBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class admin_assign_task extends Fragment {

    TextView End_Date;
    Spinner Emp_Email;
    private List<String> empNamesList;
    private ArrayAdapter<String> adapter;
    View view;
    FirebaseFirestore afdb;
    FragmentAdminAssignTaskBinding task;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        task = FragmentAdminAssignTaskBinding.inflate(inflater, container, false);
        view = task.getRoot();

        afdb = FirebaseFirestore.getInstance();

        Emp_Email = view.findViewById(R.id.Emp_Email);
        empNamesList = new ArrayList<>();
        adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, empNamesList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter.add("--Select Employee Email--");
        Emp_Email.setAdapter(adapter);
        fetchDataAndPopulateSpinner();

        //End Date
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

        task.btnAssign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                String Id = task.Id.getText().toString();
                String EmpEmail = task.EmpEmail.getSelectedItem().toString();
                String TaskDescription = task.TaskDescription.getText().toString();
                String EndDate = task.EndDate.getText().toString();

                try {
                    if (EmpEmail.isEmpty() || TaskDescription.isEmpty() || EndDate.isEmpty()) {
                        Toast.makeText(requireContext(), "Enter Values", Toast.LENGTH_SHORT).show();
                    }
                    afdb.collection("Tasks").get().addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            int documentCount = task.getResult().size();
                            String documentName = String.valueOf(documentCount + 1);

                            // Prepare data for the new document
                            Map<String, Object> data = new HashMap<>();
//                            data.put("Id", documentName);
                            data.put("T_Emp_Email", EmpEmail);
                            data.put("T_Description", TaskDescription);
                            data.put("T_End_Date", EndDate);

                            // Add the new document to the collection with the dynamically generated name
                            afdb.collection("Tasks").document(documentName).set(data)
                                    .addOnCompleteListener(task1 -> {
                                        if (task1.isSuccessful()) {
                                            Toast.makeText(getActivity(), "Task Assigned Successfully", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(getActivity(), "Failed to Assign", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        } else {
                            Toast.makeText(getActivity(), "Failed to Get Document Count", Toast.LENGTH_SHORT).show();
                        }
                    });
//                    //Insert
//                    Map<String,Object> data = new HashMap<>();
//                    data.put("Id",Id);
//                    data.put("T_Emp_Email",EmpEmail);
//                    data.put("T_Description",TaskDescription);
//                    data.put("T_End_Date",EndDate);
//
//                    afdb.collection("Tasks").get().addOnCompleteListener(task -> {
//                        if (task.isSuccessful()) {
//                            int documentCount = task.getResult().size();
//                            String documentName = String.valueOf(documentCount + 1);
//
//                                Toast.makeText(requireContext(), "Task assigned successfully", Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                    });
                } catch (Exception e) {
                    Toast.makeText(requireContext(), "Any field is Empty", Toast.LENGTH_SHORT).show();
                }

            }
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