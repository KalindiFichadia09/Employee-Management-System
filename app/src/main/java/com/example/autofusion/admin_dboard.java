package com.example.autofusion;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Text;

public class admin_dboard extends Fragment {

    TextView tot_emp,tot_leave,tot_dept;
    FirebaseFirestore afdb;
    View view;

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

        CollectionReference collectionRefleave = afdb.collection("Leaves");

        // Query for all documents in the collection
        collectionRefleave.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
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

        return view;
    }
}