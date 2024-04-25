    package com.example.autofusion;

    import androidx.annotation.NonNull;
    import androidx.appcompat.app.AppCompatActivity;

    import android.content.Intent;
    import android.os.Bundle;
    import android.view.View;
    import android.widget.Toast;

    import com.example.autofusion.databinding.ActivityAdminUpdateDepartmentBinding;
    import com.google.android.gms.tasks.OnCompleteListener;
    import com.google.android.gms.tasks.Task;
    import com.google.firebase.firestore.DocumentReference;
    import com.google.firebase.firestore.DocumentSnapshot;
    import com.google.firebase.firestore.FirebaseFirestore;

    public class admin_update_department extends AppCompatActivity {

        FirebaseFirestore afdb;
        ActivityAdminUpdateDepartmentBinding ud;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            ud = ActivityAdminUpdateDepartmentBinding.inflate(getLayoutInflater());
            setContentView(ud.getRoot());

            afdb = FirebaseFirestore.getInstance();

            // Retrieve data from intent
            String departmentId = getIntent().getStringExtra("departmentId");
            String departmentName = getIntent().getStringExtra("departmentName");

            ud.DeptId.setText(departmentId);
            ud.DeptName.setText(departmentName);

            ud.EditDept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    try {

                        String uid = ud.DeptId.getText().toString().trim();
                        String uname = ud.DeptName.getText().toString().trim();

                        DocumentReference ref = afdb.collection("Department").document(uid);
                        ref.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                                if (task.isSuccessful()) {
                                    DocumentSnapshot document = task.getResult();
                                    if (document.exists()) {
                                        String Dept_Id = document.getString("Dept_Id");
                                        String Dept_Name = document.getString("Dept_Name");

                                        if (uid.equals(Dept_Id)) {

                                            ref.update("Dept_Id",uid);
                                            ref.update("Dept_Name",uname);

                                            Toast.makeText(admin_update_department.this, "Department Updated Successfully", Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(getApplicationContext(), admin_show_department.class));

                                        } else {
                                            Toast.makeText(admin_update_department.this, "Invalid Email", Toast.LENGTH_SHORT).show();
                                        }
                                    } else if (uid.isEmpty() || uname.isEmpty()) {
                                        Toast.makeText(admin_update_department.this, "Enter Values", Toast.LENGTH_SHORT).show();
                                    }
                                }

                            }
                        });
                    }
                    catch (Exception e){
                        Toast.makeText(admin_update_department.this, "Email and password are empty", Toast.LENGTH_SHORT).show();
                    }

                }
            });


        }
    }