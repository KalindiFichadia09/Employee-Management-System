package com.example.autofusion;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
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

import com.example.autofusion.databinding.FragmentSeeLeaveBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import org.checkerframework.checker.nullness.qual.NonNull;

public class see_leave extends Fragment {

    private FirebaseFirestore afdb;
    SharedPreferences sp;
    View view;

    FragmentSeeLeaveBinding slb;

    @SuppressLint("ResourceAsColor")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        slb =  FragmentSeeLeaveBinding.inflate(inflater, container, false);
        view = slb.getRoot();

        afdb = FirebaseFirestore.getInstance();
        sp = requireContext().getSharedPreferences("AutoFusionLogin", Context.MODE_PRIVATE);
        String unm = sp.getString("Username",null);

        final TableLayout tableLayout = view.findViewById(R.id.tablelayout);

        Typeface typeface = ResourcesCompat.getFont(requireContext(), R.font.font_txt_heading);

        // Add header row
        TableRow rowHeader = new TableRow(requireContext());
        rowHeader.setBackgroundColor(getResources().getColor(R.color.tbl_heading));
        rowHeader.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                TableLayout.LayoutParams.WRAP_CONTENT));
        String[] headerText = {"Leave Type", "Leave Category","Leave Start Date","Leave End Date","Leave Remarks"};
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

        if (unm != null) {
            afdb.collection("Leaves").document(unm).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@androidx.annotation.NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {

                            String leaveType = document.getString("leaveType");
                            String leaveCategory = document.getString("leaveCategory");
                            String LeaveStartDate = document.getString("leaveStartDate");
                            String leaveEndDate = document.getString("leaveEndDate");
                            String leaveRemark = document.getString("leaveRemarks");

                            // data rows
                            TableRow row = new TableRow(requireContext());
                            row.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT,
                                    TableLayout.LayoutParams.WRAP_CONTENT));
                            String[] colText = {leaveType, leaveCategory,LeaveStartDate,leaveEndDate,leaveRemark};
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
                            tableLayout.addView(row);
                        }
                    }
                }
            });
        }

        return view;
    }
}