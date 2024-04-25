package com.example.autofusion;

import android.annotation.SuppressLint;
import android.content.Context;
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
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;
import java.util.List;

public class salaryslip extends Fragment {
    private TableLayout tableLayout;
    private List<user_Salary_Slip> SalaryList;
    String unm;
    FirebaseFirestore afdb;
    SharedPreferences sp;
    View view;
    @SuppressLint("ResourceAsColor")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_salaryslip, container, false);

        tableLayout = view.findViewById(R.id.table_layout);
        SalaryList = new ArrayList<>();
        afdb = FirebaseFirestore.getInstance();

        sp = requireContext().getSharedPreferences("AutoFusionLogin", Context.MODE_PRIVATE);
        unm = sp.getString("Username", null);

        loadSalary();
        return view;
    }
    private void loadSalary() {

        afdb.collection("Salary").document(unm)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                String StartDate = document.getString("StartDate");
                                String EndDate = document.getString("EndDate");
                                long SBasicPay = document.getLong("SBasicPay");
                                long SAllowance = document.getLong("SAllowance");
                                long SDeduction = document.getLong("SDeduction");
                                long SAdjustment = document.getLong("SAdjustment");
                                long STotalAmount = document.getLong("STotalAmount");
                                SalaryList.add(new user_Salary_Slip(StartDate, EndDate, SBasicPay, SAllowance, SDeduction, SAdjustment,STotalAmount));
                                addRowToTable(StartDate, EndDate, SBasicPay, SAllowance, SDeduction, SAdjustment,STotalAmount);
                            } else {
                                Toast.makeText(requireContext(), "No leaves found", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(requireContext(), "Error fetching leaves", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @SuppressLint("ResourceAsColor")
    private void addRowToTable(String StartDate,String EndDate,long SBasicPay,long SAllowance,long SDeduction,long SAdjustment,long STotalAmount) {
        TableRow row = new TableRow(requireContext());

        TextView tvSD = new TextView(requireContext());
        tvSD.setText(StartDate);
        applyStyleToTextView(requireContext(), tvSD);

        TextView tvEd = new TextView(requireContext());
        tvEd.setText(EndDate);
        applyStyleToTextView(requireContext(), tvEd);

        TextView tvSBP = new TextView(requireContext());
        tvSBP.setText(String.valueOf(SBasicPay));
        applyStyleToTextView(requireContext(), tvSBP);

        TextView tvSA = new TextView(requireContext());
        tvSA.setText(String.valueOf(SAllowance));
        applyStyleToTextView(requireContext(), tvSA);

        TextView tvSDD = new TextView(requireContext());
        tvSDD.setText(String.valueOf(SDeduction));
        applyStyleToTextView(requireContext(), tvSDD);

        TextView tvSAJ = new TextView(requireContext());
        tvSAJ.setText(String.valueOf(SAdjustment));
        applyStyleToTextView(requireContext(), tvSAJ);

        TextView tvSTA = new TextView(requireContext());
        tvSTA.setText(String.valueOf(STotalAmount));
        applyStyleToTextView(requireContext(), tvSTA);

        row.addView(tvSD);
        row.addView(tvEd);
        row.addView(tvSBP);
        row.addView(tvSA);
        row.addView(tvSDD);
        row.addView(tvSAJ);
        row.addView(tvSTA);

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