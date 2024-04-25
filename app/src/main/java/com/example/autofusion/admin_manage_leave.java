package com.example.autofusion;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.core.content.res.ResourcesCompat;
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
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;
import java.util.List;

public class admin_manage_leave extends Fragment {
    private TableLayout tableLayout;
    private List<admin_Show_Leave> ALeaveList;
    private FirebaseFirestore afdb;
    View view;

    @SuppressLint("ResourceAsColor")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_admin_manage_leave, container, false);

        tableLayout = view.findViewById(R.id.table_layout);
        ALeaveList = new ArrayList<>();
        afdb = FirebaseFirestore.getInstance();

        loadLeave();

        return view;
    }

    private void loadLeave() {
        afdb.collection("Leaves")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot document : task.getResult()) {
                                String docId = document.getId(); // Fetch the document ID
                                String empEmail = document.getString("leaveEmpEmail");
                                String leaveType = document.getString("leaveType");
                                String leaveCategory = document.getString("leaveCategory");
                                String LeaveStartDate = document.getString("leaveStartDate");
                                String leaveEndDate = document.getString("leaveEndDate");
                                String leaveRemark = document.getString("leaveRemarks");
                                String leaveStatus = document.getString("leaveStatus");
                                ALeaveList.add(new admin_Show_Leave(empEmail, leaveType, leaveCategory, LeaveStartDate, leaveEndDate, leaveRemark, leaveStatus));
                                addRowToTable(docId, empEmail, leaveType, leaveCategory, LeaveStartDate, leaveEndDate, leaveRemark, leaveStatus);
                            }
                        } else {
                            Toast.makeText(requireContext(), "Error fetching leaves", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


    @SuppressLint({"ResourceAsColor", "SetTextI18n"})
    private void addRowToTable(String docId, String empEmail, String leaveType, String leaveCategory, String LeaveStartDate, String leaveEndDate, String leaveRemark, String leaveStatus) {
        TableRow row = new TableRow(requireContext());

        TextView tvDocId = new TextView(requireContext());
        tvDocId.setText(docId);
        applyStyleToTextView(requireContext(), tvDocId);

        TextView tvEE = new TextView(requireContext());
        tvEE.setText(empEmail);
        applyStyleToTextView(requireContext(), tvEE);

        TextView tvLT = new TextView(requireContext());
        tvLT.setText(leaveType);
        applyStyleToTextView(requireContext(), tvLT);

        TextView tvLC = new TextView(requireContext());
        tvLC.setText(leaveCategory);
        applyStyleToTextView(requireContext(), tvLC);

        TextView tvLSD = new TextView(requireContext());
        tvLSD.setText(LeaveStartDate);
        applyStyleToTextView(requireContext(), tvLSD);

        TextView tvLED = new TextView(requireContext());
        tvLED.setText(leaveEndDate);
        applyStyleToTextView(requireContext(), tvLED);

        TextView tvLR = new TextView(requireContext());
        tvLR.setText(leaveRemark);
        applyStyleToTextView(requireContext(), tvLR);

        TextView tvLS = new TextView(requireContext());
        tvLS.setText(leaveStatus);
        applyStyleToTextView(requireContext(), tvLS);

        Button actionButton = new Button(requireContext());
        actionButton.setText("Action");
        actionButton.setGravity(Gravity.CENTER);
        actionButton.setTextColor(R.color.update);
        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TableRow rowToEdit = (TableRow) v.getParent();
                String rowunm = ((TextView) rowToEdit.getChildAt(0)).getText().toString();

                AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
                builder.setMessage("Approve or Decline ??");
                builder.setTitle("Alert !!");
                builder.setCancelable(false);
                builder.setPositiveButton("Approve", (dialog, which) -> {

                    DocumentReference ref = afdb.collection("Leaves").document(rowunm);
                    ref.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@androidx.annotation.NonNull Task<DocumentSnapshot> task) {

                            if (task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();
                                if (document.exists()) {
                                    String leaveEmpEmail = document.getString("leaveEmpEmail");
                                    String leaveStatus = document.getString("leaveStatus");
                                    String ls = "Approved";
                                    if (leaveEmpEmail.equals(empEmail)) { // Compare with employee's email
                                        ref.update("leaveStatus", ls)
                                                .addOnSuccessListener(aVoid -> {
                                                    Toast.makeText(requireContext(), "Leave Approved !!", Toast.LENGTH_SHORT).show();
                                                    dialog.dismiss();
                                                })
                                                .addOnFailureListener(e -> {
                                                    Toast.makeText(requireContext(), "Error updating leave status", Toast.LENGTH_SHORT).show();
                                                    dialog.dismiss();
                                                });
                                    } else {
                                        Toast.makeText(requireContext(), "Invalid Email", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            } else {
                                Toast.makeText(requireContext(), "Document does not exist", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                });
                builder.setNegativeButton("Decline", (dialog, which) -> {
                    DocumentReference ref = afdb.collection("Leaves").document(rowunm);
                    ref.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@androidx.annotation.NonNull Task<DocumentSnapshot> task) {

                            if (task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();
                                if (document.exists()) {
                                    String leaveEmpEmail = document.getString("leaveEmpEmail");
                                    String leaveStatus = document.getString("leaveStatus");
                                    String ls = "Decline";
                                    if (leaveEmpEmail.equals(empEmail)) { // Compare with employee's email
                                        ref.update("leaveStatus", ls)
                                                .addOnSuccessListener(aVoid -> {
                                                    Toast.makeText(requireContext(), "Leave Declined !!", Toast.LENGTH_SHORT).show();
                                                    dialog.dismiss();
                                                })
                                                .addOnFailureListener(e -> {
                                                    Toast.makeText(requireContext(), "Error updating leave status", Toast.LENGTH_SHORT).show();
                                                    dialog.dismiss();
                                                });
                                    } else {
                                        Toast.makeText(requireContext(), "Invalid Email", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            } else {
                                Toast.makeText(requireContext(), "Document does not exist", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                });
                builder.setNeutralButton("Cancel", (dialog, which) -> dialog.cancel());
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

        row.addView(tvDocId);
        row.addView(tvEE);
        row.addView(tvLT);
        row.addView(tvLC);
        row.addView(tvLSD);
        row.addView(tvLED);
        row.addView(tvLR);
        row.addView(tvLS);
        row.addView(actionButton);

        tableLayout.addView(row);
    }

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