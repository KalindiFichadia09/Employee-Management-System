package com.example.autofusion;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
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

public class admin_see_salary extends Fragment {
    private TableLayout tableLayout;
    private List<admin_Salaryslip> adminSalaryslips;
    FirebaseFirestore afdb;
    View view;

    @SuppressLint("ResourceAsColor")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view  = inflater.inflate(R.layout.fragment_admin_see_salary, container, false);

        tableLayout = view.findViewById(R.id.table_layout);
        adminSalaryslips = new ArrayList<>();
        afdb = FirebaseFirestore.getInstance();

        loadSalary();

        return view;
    }
    private void loadSalary() {
        afdb.collection("Salary")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot document : task.getResult()) {
                                String empEmail = document.getString("SEmpEmail");
                                String StartDate = document.getString("StartDate");
                                String EndDate = document.getString("EndDate");
                                long SBasicPay = document.getLong("SBasicPay");
                                long SAllowance = document.getLong("SAllowance");
                                long SDeduction = document.getLong("SDeduction");
                                long SAdjustment = document.getLong("SAdjustment");
                                long STotalAmount = document.getLong("STotalAmount");
                                adminSalaryslips.add(new admin_Salaryslip(empEmail,StartDate, EndDate, SBasicPay, SAllowance, SDeduction, SAdjustment,STotalAmount));
                                addRowToTable(empEmail,StartDate, EndDate, SBasicPay, SAllowance, SDeduction, SAdjustment,STotalAmount);
                            }
                        } else {
                            Toast.makeText(requireContext(), "Error fetching leaves", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


    @SuppressLint({"ResourceAsColor", "SetTextI18n"})
    private void addRowToTable(String empEmail,String StartDate,String EndDate,long SBasicPay,long SAllowance,long SDeduction,long SAdjustment,long STotalAmount) {
        TableRow row = new TableRow(requireContext());

        TextView tvEE = new TextView(requireContext());
        tvEE.setText(empEmail);
        applyStyleToTextView(requireContext(), tvEE);

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

        row.addView(tvEE);
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