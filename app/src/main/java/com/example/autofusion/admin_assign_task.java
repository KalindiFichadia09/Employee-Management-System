package com.example.autofusion;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import com.example.autofusion.databinding.FragmentAdminAssignTaskBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class admin_assign_task extends Fragment {

    TextView End_Date;
    View view;
    FirebaseFirestore afdb;
    FragmentAdminAssignTaskBinding task;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        task = FragmentAdminAssignTaskBinding.inflate(inflater, container, false);
        view=task.getRoot();

        afdb = FirebaseFirestore.getInstance();
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

                String Id = task.Id.getText().toString();
                String EmpId = task.EmpId.getText().toString();
                String TaskDescription = task.TaskDescription.getText().toString();
                String EndDate = task.EndDate.getText().toString();

                //Insert
                Map<String,Object> data = new HashMap<>();
                data.put("Id",Id);
                data.put("T_Emp_Id",EmpId);
                data.put("T_Description",TaskDescription);
                data.put("T_End_Date",EndDate);

                afdb.collection("Tasks").document(Id).set(data).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(requireContext(), "Task assigned successfully", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });

        return view;
    }
}