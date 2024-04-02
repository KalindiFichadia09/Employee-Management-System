package com.example.autofusion;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.autofusion.databinding.FragmentPersonalDetailBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class personal_detail extends Fragment {

    FirebaseFirestore afdb;
    SharedPreferences sp;
    View view;
    FragmentPersonalDetailBinding pib;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        pib = FragmentPersonalDetailBinding.inflate(inflater, container, false);
        view = pib.getRoot();

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

                        pib.EmpFullName.setText(document.getString("EmpFullName"));
                        pib.EmpFatherName.setText(document.getString("EmpFatherName"));
                        pib.EmpMotherName.setText(document.getString("EmpMotherName"));
                        pib.EmpGender.setText(document.getString("EmpGender"));
                        pib.EmpDateOfBirth.setText(document.getString("EmpBirthDate"));
                        pib.EmpAdharNo.setText(document.getString("EmpAaddarNo"));
                        pib.EmpBloodGroup.setText(document.getString("EmpBloodGroup"));
                        pib.EmpAge.setText(document.getString("EmpAge"));
                        pib.EmpMobileNo.setText(document.getString("EmpMobileNo"));
                        pib.EmpEmail.setText(document.getString("EmpEmail"));

                        String EmpImage = document.getString("EmpImage");
                        Glide.with(requireContext())
                                .load(EmpImage)
                                .into(pib.EmpImg);

                    }
                }

            }
        });

        return view;
    }
}