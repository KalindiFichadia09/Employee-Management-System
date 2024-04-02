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

public class admin_show_department extends Fragment {

    private FirebaseFirestore afdb;
    View view;
    @SuppressLint("ResourceAsColor")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_admin_show_department, container, false);

        afdb = FirebaseFirestore.getInstance();

        final TableLayout tableLayout = view.findViewById(R.id.tablelayout);

        Typeface typeface = ResourcesCompat.getFont(requireContext(), R.font.font_txt_heading);

        // Add header row
        TableRow rowHeader = new TableRow(requireContext());
        rowHeader.setBackgroundColor(getResources().getColor(R.color.tbl_heading));
        rowHeader.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                TableLayout.LayoutParams.WRAP_CONTENT));
        String[] headerText = {"ID", "NAME"};
        for (String c : headerText) {
            TextView tv = new TextView(requireContext());
            tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
            tv.setTextSize(15);
            tv.setTextColor(R.color.black_heading);
            tv.setTypeface(typeface );
            tv.setGravity(Gravity.CENTER);
            tv.setPadding(8, 8, 8, 8);
            tv.setText(c);
            rowHeader.addView(tv);
        }
        tableLayout.addView(rowHeader);

        // Get data from Firestore and add them to the table
        CollectionReference outletRef = afdb.collection("Department");
        outletRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (DocumentSnapshot document : task.getResult()) {
                        String dept_id = document.getString("Dept_Id");
                        String dept_name = document.getString("Dept_Name");
                        // data rows
                        TableRow row = new TableRow(requireContext());
                        row.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                                TableLayout.LayoutParams.WRAP_CONTENT));
                        String[] colText = {dept_id, dept_name};
                        for (String text : colText) {
                            TextView tv = new TextView(requireContext());
                            tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                                    TableRow.LayoutParams.WRAP_CONTENT));
                            tv.setTextSize(15);
                            tv.setTextColor(R.color.tbl_ans);
                            tv.setGravity(Gravity.CENTER);
                            tv.setPadding(8, 8, 8, 8);
                            tv.setText(text);
                            row.addView(tv);
                        }
                        tableLayout.addView(row);
                    }
                }
            }
        });

        return view;
    }
}