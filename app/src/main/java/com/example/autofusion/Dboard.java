package com.example.autofusion;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.autofusion.databinding.FragmentDboardBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class Dboard extends Fragment {
    TextView username;
    View view;
    ImageView profile;
    SharedPreferences  sp;
    FirebaseFirestore afdb;
    FragmentDboardBinding dboard;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        dboard =  FragmentDboardBinding.inflate(inflater, container, false);
        view = dboard.getRoot();

        username = view.findViewById(R.id.username);

        sp = requireContext().getSharedPreferences("AutoFusionLogin", Context.MODE_PRIVATE);

        String unm = sp.getString("Username",null);
//        dboard.username.setText(""+unm);

        afdb = FirebaseFirestore.getInstance();

        DocumentReference ref = afdb.collection("Employee").document(unm);
        ref.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                if(task.isSuccessful()){
                    DocumentSnapshot document = task.getResult();
                    if(document.exists()){

                        username.setText(document.getString("EmpFullName"));

                    }
                }

            }
        });

        profile = view.findViewById(R.id.profile);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment newFragment = new profile();
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