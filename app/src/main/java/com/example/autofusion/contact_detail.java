package com.example.autofusion;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.autofusion.databinding.FragmentContactDetailBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class contact_detail extends Fragment {

    FirebaseFirestore afdb;
    SharedPreferences sp;
    View view;
    FragmentContactDetailBinding coib;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        coib =  FragmentContactDetailBinding.inflate(inflater, container, false);
        view = coib.getRoot();

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

                        coib.EmpAddress.setText(document.getString("EmpAddress"));
                        coib.EmpCity.setText(document.getString("EmpCity"));
                        coib.EmpState.setText(document.getString("EmpState"));
                        coib.EmpCountry.setText(document.getString("EmpCountry"));
                        coib.EmpPincode.setText(document.getString("EmpPincode"));
                        coib.EmpEmergencyNo.setText(document.getString("EmpEmergencyNo"));

                    }
                }

            }
        });


        return view;
    }
}