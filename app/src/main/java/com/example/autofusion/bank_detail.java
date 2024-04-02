package com.example.autofusion;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.autofusion.databinding.FragmentBankDetailBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class bank_detail extends Fragment {
    FirebaseFirestore afdb;
    SharedPreferences sp;
    View view;
    FragmentBankDetailBinding bib;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        bib =  FragmentBankDetailBinding.inflate(inflater, container, false);
        view = bib.getRoot();

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

                        bib.EmpAccountHolderName.setText(document.getString("EmpAccountHolderName"));
                        bib.EmpBankName.setText(document.getString("EmpBankName"));
                        bib.EmpAccountNo.setText(document.getString("EmpAccountNumber"));
                        bib.EmpIFSCCode.setText(document.getString("EmpIFSCCode"));
                        bib.EmpBankCode.setText(document.getString("EmpBankCode"));
                        bib.EmpBranchName.setText(document.getString("EmpBranchName"));

                    }
                }

            }
        });

        return view;
    }
}