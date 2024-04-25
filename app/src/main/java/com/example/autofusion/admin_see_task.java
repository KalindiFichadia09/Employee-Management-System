package com.example.autofusion;

import android.annotation.SuppressLint;
import android.content.Context;
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
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;
import java.util.List;

public class admin_see_task extends Fragment {
    private TableLayout tableLayout;
    private List<admin_Show_Task> TaskList;
    FirebaseFirestore afdb;
    View view;

    @SuppressLint("ResourceAsColor")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_admin_see_task, container, false);

        tableLayout = view.findViewById(R.id.table_layout);
        TaskList = new ArrayList<>();
        afdb = FirebaseFirestore.getInstance();

        loadTask();
        return view;
    }

    private void loadTask() {

        afdb.collection("Tasks")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot document : task.getResult()) {
                                String docId = document.getId();
                                String TEmpEmail = document.getString("T_Emp_Email");
                                String TDescription = document.getString("T_Description");
                                String TEndDate = document.getString("T_End_Date");
                                TaskList.add(new admin_Show_Task(TEmpEmail, TDescription, TEndDate));
                                addRowToTable(docId, TEmpEmail, TDescription, TEndDate);
                            }
                        } else {
                            Toast.makeText(requireContext(), "Error fetching Tasks", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @SuppressLint("ResourceAsColor")
    private void addRowToTable(String docId, String TEmpEmail, String TDescription, String TEndDate) {
        TableRow row = new TableRow(requireContext());

        TextView tvDocId = new TextView(requireContext());
        tvDocId.setText(docId);
        applyStyleToTextView(requireContext(), tvDocId);

        TextView tvTEmpEmail = new TextView(requireContext());
        tvTEmpEmail.setText(TEmpEmail);
        applyStyleToTextView(requireContext(), tvTEmpEmail);

        TextView tvTDescription = new TextView(requireContext());
        tvTDescription.setText(TDescription);
        applyStyleToTextView(requireContext(), tvTDescription);

        TextView tvTEndDate = new TextView(requireContext());
        tvTEndDate.setText(TEndDate);
        applyStyleToTextView(requireContext(), tvTEndDate);

        row.addView(tvDocId);
        row.addView(tvTEmpEmail);
        row.addView(tvTDescription);
        row.addView(tvTEndDate);

        tableLayout.addView(row);
    }

    public void applyStyleToTextView(Context context, TextView textView) {
        TypedArray attributes = context.getTheme().obtainStyledAttributes(R.style.txt_tbl_ans, new int[]{android.R.attr.textSize, android.R.attr.textColor, android.R.attr.gravity, android.R.attr.padding});

        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, attributes.getDimensionPixelSize(0, -1));
        textView.setTextColor(attributes.getColor(1, Color.BLACK));
        textView.setGravity(attributes.getInt(2, Gravity.NO_GRAVITY));
        textView.setPadding(attributes.getDimensionPixelSize(3, 0), attributes.getDimensionPixelSize(3, 0), attributes.getDimensionPixelSize(3, 0), attributes.getDimensionPixelSize(3, 0));

        attributes.recycle();
    }
}