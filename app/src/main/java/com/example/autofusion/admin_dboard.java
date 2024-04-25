package com.example.autofusion;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Text;

public class admin_dboard extends Fragment {

    TextView tot_emp,tot_leave,tot_dept;
    ImageView tot_empi,pd_leave,dept;
    FirebaseFirestore afdb;
    View view;

    @SuppressLint("WrongViewCast")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_admin_dboard, container, false);

        tot_emp = view.findViewById(R.id.employee);
        tot_leave = view.findViewById(R.id.leave);
        tot_dept = view.findViewById(R.id.department);

        afdb = FirebaseFirestore.getInstance();
        CollectionReference collectionRefemp = afdb.collection("Employee");

        // Query for all documents in the collection
        collectionRefemp.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                // Get the number of documents in the result set
                int count = queryDocumentSnapshots.size();

                // Convert count to string before setting to TextView
                String totalCount = String.valueOf(count);

                tot_emp.setText(totalCount);

            }
        });
        String sts = "Pending";
        CollectionReference collectionRefleave = afdb.collection("Leaves");

        collectionRefleave.whereEqualTo("leaveStatus", "Pending").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                // Get the number of documents in the result set
                int count = queryDocumentSnapshots.size();

                // Convert count to string before setting to TextView
                String totalCount = String.valueOf(count);

                tot_leave.setText(totalCount);

            }
        });

        CollectionReference collectionRefdept = afdb.collection("Department");

        // Query for all documents in the collection
        collectionRefdept.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                // Get the number of documents in the result set
                int count = queryDocumentSnapshots.size();

                // Convert count to string before setting to TextView
                String totalCount = String.valueOf(count);

                tot_dept.setText(totalCount);

            }
        });

        tot_empi=view.findViewById(R.id.tot_emp);
        tot_empi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment newFragment = new admin_show_employee();
                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, newFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        pd_leave=view.findViewById(R.id.pd_leave);
        pd_leave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment newFragment = new admin_manage_leave();
                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, newFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        dept=view.findViewById(R.id.dept);
        dept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment newFragment = new admin_show_department();
                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, newFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        return view;
    }
}