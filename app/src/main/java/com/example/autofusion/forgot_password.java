package com.example.autofusion;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.autofusion.databinding.ActivityForgotPasswordBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class forgot_password extends AppCompatActivity {

    AppCompatButton cp;
    FirebaseFirestore afdb;
    ActivityForgotPasswordBinding fpw;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fpw = ActivityForgotPasswordBinding.inflate(getLayoutInflater());
        setContentView(fpw.getRoot());

        afdb = FirebaseFirestore.getInstance();

        fpw.cp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {

                    String eml = fpw.email.getText().toString().trim();
                    String npwd = fpw.newPassword.getText().toString().trim();
                    String cpwd = fpw.confirmPassword.getText().toString().trim();

                    DocumentReference ref = afdb.collection("Employee").document(eml);
                    ref.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                            if (task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();
                                if (document.exists()) {
                                    String Cmp_Email = document.getString("EmpCompanyEmail");
                                    String Password = document.getString("EmpPassword");

                                    if (eml.equals(Cmp_Email)) {

//                                        DocumentReference ref = FirebaseFirestore.getInstance().collection("Registration").document("user1");
                                        ref.update("EmpPassword",cpwd);

                                        startActivity(new Intent(getApplicationContext(), login.class));
                                        Toast.makeText(forgot_password.this, "Password Updated Successfully", Toast.LENGTH_SHORT).show();

                                    } else {
                                        Toast.makeText(forgot_password.this, "Invalid Email", Toast.LENGTH_SHORT).show();
                                    }
                                } else if (eml.isEmpty() || npwd.isEmpty() || cpwd.isEmpty()) {
                                    Toast.makeText(forgot_password.this, "Enter Values", Toast.LENGTH_SHORT).show();
                                }
                            }

                        }
                    });
                }
                catch (Exception e){
                    Toast.makeText(forgot_password.this, "Email and password are empty", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}