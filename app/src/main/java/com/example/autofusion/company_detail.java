package com.example.autofusion;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.autofusion.databinding.FragmentCompanyDetailBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class company_detail extends Fragment {

    View view;
    SharedPreferences sp;
    FirebaseFirestore afdb;
    FragmentCompanyDetailBinding cib;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        cib =  FragmentCompanyDetailBinding.inflate(inflater, container, false);
        view = cib.getRoot();

        sp = requireContext().getSharedPreferences("AutoFusionLogin", Context.MODE_PRIVATE);

        String unm = sp.getString("Username",null);

        afdb = FirebaseFirestore.getInstance();

        DocumentReference ref = afdb.collection("Employee").document(unm);
        ref.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                if(task.isSuccessful()){
                    DocumentSnapshot document = task.getResult();
                    if(document.exists()){

                        cib.EmpId.setText(document.getString("EmpEmployeeId"));
                        cib.EmpDepartment.setText(document.getString("EmpDepartmentName"));
                        cib.EmpJoinDate.setText(document.getString("EmpJoinDate"));
                        cib.EmpCompanuEmail.setText(document.getString("EmpCompanyEmail"));
                        cib.EmpJobType.setText(document.getString("EmpJobType"));

                    }
                }

            }
        });


        return view;
    }
}