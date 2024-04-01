package com.example.autofusion;

import android.annotation.SuppressLint;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import org.checkerframework.checker.nullness.qual.NonNull;

public class admin_see_task extends Fragment {

    FirebaseFirestore afdb;
    View view;

    @SuppressLint("ResourceAsColor")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_admin_see_task, container, false);

        final TableLayout tablelayout = view.findViewById(R.id.tablelayout);
        afdb = FirebaseFirestore.getInstance();

        Typeface typeface = ResourcesCompat.getFont(requireContext(), R.font.font_txt_heading);

        // Add header row
        TableRow rowHeader = new TableRow(requireContext());
        rowHeader.setBackgroundColor(getResources().getColor(R.color.tbl_heading));
        rowHeader.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                TableLayout.LayoutParams.WRAP_CONTENT));
        String[] headerText = {"ID", "Emp Id","Description","End Date"};
        for (String c : headerText) {
            TextView tv = new TextView(requireContext());
            tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
            tv.setTextSize(15);
            tv.setTextColor(R.color.black_heading);
            tv.setTypeface(typeface );
            tv.setGravity(Gravity.CENTER);
            tv.setPadding(10, 10, 10, 10);
            tv.setText(c);
            rowHeader.addView(tv);
        }
        tablelayout.addView(rowHeader);

        // Get data from Firestore and add them to the table
        CollectionReference outletRef = afdb.collection("Tasks");
        outletRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (DocumentSnapshot document : task.getResult()) {
                        String Id = document.getString("Id");
                        String EmpId = document.getString("T_Emp_Id");
                        String TDescription = document.getString("T_Description");
                        String TEndDate = document.getString("T_End_Date");
                        String ud = "Edit";
                        String dlt = "Remove";
                        // data rows
                        TableRow row = new TableRow(requireContext());
                        row.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT,
                                TableLayout.LayoutParams.WRAP_CONTENT));
                        String[] colText = {Id, EmpId,TDescription,TEndDate};
                        for (String text : colText) {
                            TextView tv = new TextView(requireContext());
                            tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                                    TableRow.LayoutParams.WRAP_CONTENT));
                            tv.setTextSize(15);
                            tv.setTextColor(R.color.tbl_ans);
                            tv.setGravity(Gravity.CENTER);
                            tv.setPadding(10, 10, 10, 10);
                            tv.setText(text);
                            row.addView(tv);
                        }
                        tablelayout.addView(row);
                    }
                }
            }
        });


        return view;
    }
}