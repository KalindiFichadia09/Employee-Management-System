package com.example.autofusion;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.NonNull;
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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class task extends Fragment {
    FirebaseFirestore afdb;
    SharedPreferences sp;
    View view;
    @SuppressLint("ResourceAsColor")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_task, container, false);

        sp= requireContext().getSharedPreferences("AutoFusionLogin", Context.MODE_PRIVATE);
        String unm = sp.getString("Username",null);

        afdb = FirebaseFirestore.getInstance();

        final TableLayout tablelayout = view.findViewById(R.id.tablelayout);
        afdb = FirebaseFirestore.getInstance();

        Typeface typeface = ResourcesCompat.getFont(requireContext(), R.font.font_txt_heading);

        // Add header row
        TableRow rowHeader = new TableRow(requireContext());
        rowHeader.setBackgroundColor(getResources().getColor(R.color.tbl_heading));
        rowHeader.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                TableLayout.LayoutParams.WRAP_CONTENT));
        String[] headerText = {"Description","End Date"};
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

            if (unm != null) {
                afdb.collection("Tasks").document(unm).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                String TEmpEmail = document.getString("T_Emp_Email");
                                String TDescription = document.getString("T_Description");
                                String TEndDate = document.getString("T_End_Date");


                                // data rows
                                TableRow row = new TableRow(requireContext());
                                row.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT,
                                        TableLayout.LayoutParams.WRAP_CONTENT));
                                String[] colText = {TDescription, TEndDate};
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
            }
        return view;
    }
}