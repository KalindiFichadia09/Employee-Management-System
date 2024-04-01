package com.example.autofusion;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.autofusion.databinding.ActivityLoginBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class login extends AppCompatActivity {

    TextView fp;
    AppCompatButton login,admin;
    SharedPreferences sp;
    SharedPreferences.Editor spe;
    FirebaseFirestore afdb;
    ActivityLoginBinding bind;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bind = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(bind.getRoot());

        afdb = FirebaseFirestore.getInstance();
        sp =getSharedPreferences("AutoFusionLogin",MODE_PRIVATE);

        bind.login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String unm = bind.Email.getText().toString();
                String pwd = bind.Password.getText().toString();

                DocumentReference ref = afdb.collection("Employee").document(unm);
                ref.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                        if(task.isSuccessful()){
                            DocumentSnapshot document = task.getResult();
                            if(document.exists()){
                                String Cmp_Email = document.getString("EmpCompanyEmail");
                                String Password = document.getString("EmpPassword");

                                if(unm.equals(Cmp_Email) && pwd.equals(Password)){

                                    spe = sp.edit();

                                    spe.putString("Username",unm);
                                    spe.putString("Password",pwd);

                                    spe.commit();

                                    startActivity(new Intent(getApplicationContext(), Dashboard.class));

                                    Toast.makeText(login.this, "Logedin Successfully", Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    Toast.makeText(login.this, "Invalid Username and Password", Toast.LENGTH_SHORT).show();
                                }
                            }
                            else {
                                Toast.makeText(login.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                            }
                        }

                    }
                });
            }
        });

        bind.fp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), forgot_password.class));
            }
        });
        bind.admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), admin_dashboard.class));
            }
        });
    }
}