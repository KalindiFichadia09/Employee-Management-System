package com.example.autofusion;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;

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

import com.example.autofusion.databinding.FragmentSeeLeaveBinding;
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

public class see_leave extends Fragment {
    private TableLayout tableLayout;
    private List<Show_Leave> LeaveList;
    private FirebaseFirestore afdb;
    String unm;
    SharedPreferences sp;
    View view;
    FragmentSeeLeaveBinding slb;

    @SuppressLint("ResourceAsColor")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        slb = FragmentSeeLeaveBinding.inflate(inflater, container, false);
        view = slb.getRoot();

        tableLayout = view.findViewById(R.id.table_layout);
        LeaveList = new ArrayList<>();
        afdb = FirebaseFirestore.getInstance();

        sp = requireContext().getSharedPreferences("AutoFusionLogin", Context.MODE_PRIVATE);
        unm = sp.getString("Username", null);

        loadLeave();
        return view;
    }

    private void loadLeave() {
        afdb.collection("Leaves").whereEqualTo("leaveEmpEmail", unm)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (DocumentSnapshot document : task.getResult()) {
                            String leaveType = document.getString("leaveType");
                            String leaveCategory = document.getString("leaveCategory");
                            String leaveStartDate = document.getString("leaveStartDate");
                            String leaveEndDate = document.getString("leaveEndDate");
                            String leaveRemark = document.getString("leaveRemarks");
                            String leaveStatus = document.getString("leaveStatus");
                            addRowToTable(leaveType, leaveCategory, leaveStartDate, leaveEndDate, leaveRemark, leaveStatus);
                        }
                    } else {
                        Toast.makeText(requireContext(), "Error fetching leaves", Toast.LENGTH_SHORT).show();
                    }
                });
    }


    @SuppressLint("ResourceAsColor")
    private void addRowToTable(String leaveType, String leaveCategory, String LeaveStartDate, String leaveEndDate, String leaveRemark, String leaveStatus) {
        TableRow row = new TableRow(requireContext());

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

        row.addView(tvLT);
        row.addView(tvLC);
        row.addView(tvLSD);
        row.addView(tvLED);
        row.addView(tvLR);
        row.addView(tvLS);

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