package com.example.autofusion;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;
import java.util.List;

public class admin_show_employee extends Fragment {
    private TableLayout tableLayout;
    private List<Show_Employee> EmployeeList;
    FirebaseFirestore afdb;
    View view;

    @SuppressLint("ResourceAsColor")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_admin_show_employee, container, false);

        tableLayout = view.findViewById(R.id.table_layout);
        EmployeeList = new ArrayList<Show_Employee>();
        afdb = FirebaseFirestore.getInstance();

        loadEmployee();

        return view;
    }

    private void loadEmployee() {
        afdb.collection("Employee")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot document : task.getResult()) {
                                String EmpName = document.getString("EmpFullName");
                                String EmpCEmail = document.getString("EmpCompanyEmail");
                                String EmpMobile = document.getString("EmpMobileNo");
                                String EmpDepartment = document.getString("EmpDepartmentName");
                                EmployeeList.add(new Show_Employee(EmpName, EmpCEmail,EmpMobile,EmpDepartment));
                                addRowToTable(EmpName, EmpCEmail,EmpMobile,EmpDepartment);
                            }
                        } else {
                            Toast.makeText(requireContext(), "Error fetching departments", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @SuppressLint("ResourceAsColor")
    private void addRowToTable(String EmpName, String EmpCEmail,String EmpMobile,String EmpDept) {
        TableRow row = new TableRow(requireContext());

        TextView tvEmpName = new TextView(requireContext());
        tvEmpName.setText(EmpName);
        applyStyleToTextView(requireContext(), tvEmpName);

        TextView tvEmpCEmail = new TextView(requireContext());
        tvEmpCEmail.setText(EmpCEmail);
        applyStyleToTextView(requireContext(), tvEmpCEmail);

        TextView tvEmpMobile = new TextView(requireContext());
        tvEmpMobile.setText(EmpMobile);
        applyStyleToTextView(requireContext(), tvEmpMobile);

        TextView tvEmpDept = new TextView(requireContext());
        tvEmpDept.setText(EmpDept);
        applyStyleToTextView(requireContext(), tvEmpDept);

        Button editButton = new Button(requireContext());
        editButton.setText("Edit");
        editButton.setGravity(Gravity.CENTER);
        editButton.setTextColor(R.color.update);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle edit button click
//                editDepartment(id, name);
            }
        });

        Button deleteButton = new Button(requireContext());
        deleteButton.setText("Delete");
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle delete button click
//                deleteDepartment(id);
            }
        });

        row.addView(tvEmpName);
        row.addView(tvEmpCEmail);
        row.addView(tvEmpMobile);
        row.addView(tvEmpDept);
//        row.addView(editButton);
//        row.addView(deleteButton);

        tableLayout.addView(row);
    }

    /*private void editDepartment(String id, String name) {
        // Pass department id and name to another Java file or method
        Intent intent = new Intent(getContext(), admin_update_department.class);
        intent.putExtra("departmentId", id);
        intent.putExtra("departmentName", name);
        startActivity(intent);
    }*/

    /*private void deleteDepartment(String id) {
        // Get reference to the document and delete it
        DocumentReference docRef = afdb.collection("Department").document(id);
        docRef.delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(requireContext(), "Department deleted successfully", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(requireContext(), "Failed to delete department", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }*/

    public void applyStyleToTextView(Context context, TextView textView) {
        TypedArray attributes = context.getTheme().obtainStyledAttributes(R.style.txt_tbl_ans, new int[]{
                android.R.attr.textSize,
                android.R.attr.textColor,
                android.R.attr.gravity,
                android.R.attr.padding
        });

        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, attributes.getDimensionPixelSize(0, -1));
        textView.setTextColor(attributes.getColor(1, Color.BLACK));
        textView.setGravity(attributes.getInt(2, Gravity.NO_GRAVITY));
        textView.setPadding(attributes.getDimensionPixelSize(3, 0), attributes.getDimensionPixelSize(3, 0), attributes.getDimensionPixelSize(3, 0), attributes.getDimensionPixelSize(3, 0));

        attributes.recycle();
    }
}