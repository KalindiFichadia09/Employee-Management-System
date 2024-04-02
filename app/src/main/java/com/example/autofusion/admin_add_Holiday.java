package com.example.autofusion;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class admin_add_Holiday extends Fragment {

    EditText H_Id, H_Name;
    TextView Start_Date, End_Date;
    Button Btn_Add;
    View view;
    FirebaseFirestore afdb;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_admin_add__holiday, container, false);

        H_Id = view.findViewById(R.id.H_Id);
        H_Name = view.findViewById(R.id.H_Name);
        Start_Date = view.findViewById(R.id.Start_Date);
        End_Date = view.findViewById(R.id.End_Date);
        Btn_Add = view.findViewById(R.id.btn_add);

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

        Btn_Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String Id = H_Id.getText().toString().trim();
                String Name = H_Name.getText().toString().trim();
                String StartDate = Start_Date.getText().toString().trim();
                String EndDate = End_Date.getText().toString().trim();

                try {
                    if (Id.isEmpty() || Name.isEmpty() || StartDate.isEmpty() || EndDate.isEmpty()) {
                        Toast.makeText(requireContext(), "Enter Values", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    // Initialize Firestore
                    afdb = FirebaseFirestore.getInstance();

                    // Insert
                    Map<String, Object> data = new HashMap<>();
                    data.put("Holiday_Id", Id);
                    data.put("Holiday_Name", Name);
                    data.put("Start_Date", StartDate);
                    data.put("End_Date", EndDate);

                    afdb.collection("Holidays").document(Id).set(data).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(requireContext(), "Holiday Added successfully", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(requireContext(), "Failed to add holiday", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } catch (Exception e) {
                    Toast.makeText(requireContext(), "Any field is Empty", Toast.LENGTH_SHORT).show();
                }

            }
        });

        return view;
    }
}