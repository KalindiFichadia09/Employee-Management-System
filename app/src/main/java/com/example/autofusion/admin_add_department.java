package com.example.autofusion;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.autofusion.databinding.FragmentAdminAddDepartmentBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class admin_add_department extends Fragment {

    View view;

    FragmentAdminAddDepartmentBinding b;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        b = FragmentAdminAddDepartmentBinding.inflate(inflater, container, false);
        view=b.getRoot();
        FirebaseFirestore afdb = FirebaseFirestore.getInstance();
        b.AddDept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String id,name;
                id = b.DeptId.getText().toString();
                name = b.DeptName.getText().toString();

                if(TextUtils.isEmpty(id) || TextUtils.isEmpty(name)){
                    Toast.makeText(getActivity(), "Id or Name is Empty", Toast.LENGTH_SHORT).show();
                }
                else {
                    //Insert
                    Map<String,Object> data = new HashMap<>();
                    data.put("Dept_Id",id);
                    data.put("Dept_Name",name);
                    afdb.collection("Department").document(id).set(data).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(getActivity(), "Added Successfully", Toast.LENGTH_SHORT).show();
//                                startActivity(new Intent(getActivity(),MainActivity.class));
                            }
                        }
                    });
                }

            }
        });

        return view;
    }
}