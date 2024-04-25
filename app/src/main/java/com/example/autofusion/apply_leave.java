package com.example.autofusion;

import static android.app.Activity.RESULT_OK;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.autofusion.databinding.ActivityDashboardBinding;
import com.example.autofusion.databinding.FragmentApplyLeaveBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.w3c.dom.Text;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class apply_leave extends Fragment {
    Spinner leave_type,leave_category;
    String v1,v2;
    View view;
    ImageView leave_attachment;
    Button btn_apply;
    EditText leave_remarks;
    TextView leave_start_date,leave_end_date;
    TextView txt_ans;
    SharedPreferences sp;
    private static final int REQUEST_IMAGE_PICK = 1;
    private StorageReference storageRef;
    Uri imageUri;
    FirebaseFirestore afdb;
    FragmentApplyLeaveBinding alb;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        alb = FragmentApplyLeaveBinding.inflate(inflater, container, false);
        view = alb.getRoot();

        leave_start_date = view.findViewById(R.id.leave_start_date);
        leave_end_date = view.findViewById(R.id.leave_end_date);
        leave_attachment = view.findViewById(R.id.leave_attachment);

        sp = requireContext().getSharedPreferences("AutoFusionLogin", Context.MODE_PRIVATE);
        String unm = sp.getString("Username",null);
        storageRef = FirebaseStorage.getInstance().getReference();
        afdb = FirebaseFirestore.getInstance();

        leave_type = view.findViewById(R.id.leave_type);
        leave_category = view.findViewById(R.id.leave_category);
        String[] leave_type_s = getResources().getStringArray(R.array.leave_type);
        String[] leave_category_S = getResources().getStringArray(R.array.leave_category);

        ArrayAdapter<String> leave_type_ad = new ArrayAdapter<String>(requireContext(), android.R.layout.simple_spinner_dropdown_item, leave_type_s);
        ArrayAdapter<String> leave_category_ad = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_dropdown_item,leave_category_S);

        leave_type.setAdapter(leave_type_ad);
        leave_category.setAdapter(leave_category_ad);
        leave_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                v1 = leave_type_ad.getItem(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        leave_category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                v2 = leave_category_ad.getItem(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        leave_start_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();

                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                // on below line we are creating a variable for date picker dialog.
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        requireContext(),
                        new DatePickerDialog.OnDateSetListener() {
                            @SuppressLint("SetTextI18n")
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                leave_start_date.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                            }
                        },
                        year, month, day);
                datePickerDialog.show();
            }
        });

        leave_end_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();

                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                // on below line we are creating a variable for date picker dialog.
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        requireContext(),
                        new DatePickerDialog.OnDateSetListener() {
                            @SuppressLint("SetTextI18n")
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                leave_end_date.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                            }
                        },
                        year, month, day);
                datePickerDialog.show();
            }
        });

        leave_attachment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImageFromGallery();
            }
        });
        alb.btnApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String leaveType = alb.leaveType.getSelectedItem().toString();
                String leaveCategory = alb.leaveCategory.getSelectedItem().toString();
                String leaveStartDate = alb.leaveStartDate.getText().toString();
                String leaveEndDate = alb.leaveEndDate.getText().toString();
                String leaveRemarks = alb.leaveRemarks.getText().toString();
                String leaveStatus = "Pending";

                if (imageUri != null) {
                    StorageReference imageRef = storageRef.child("images/" + System.currentTimeMillis() + ".jpg");
                    imageRef.putFile(imageUri)
                            .addOnSuccessListener(taskSnapshot -> {
                                imageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                                    String imageUrl = uri.toString();

                                    afdb.collection("Leaves").get().addOnCompleteListener(task -> {
                                        if (task.isSuccessful()) {
                                            int documentCount = task.getResult().size();
                                            String documentName = String.valueOf(documentCount + 1);

                                            // Prepare data for the new document
                                            Map<String, Object> data = new HashMap<>();
                                            data.put("leaveEmpEmail", unm);
                                            data.put("leaveType", leaveType);
                                            data.put("leaveCategory", leaveCategory);
                                            data.put("leaveStartDate", leaveStartDate);
                                            data.put("leaveEndDate", leaveEndDate);
                                            data.put("leaveRemarks", leaveRemarks);
                                            data.put("leaveAttachment", imageUrl);
                                            data.put("leaveStatus", leaveStatus);

                                            // Add the new document to the collection with the dynamically generated name
                                            afdb.collection("Leaves").document(documentName).set(data)
                                                    .addOnCompleteListener(task1 -> {
                                                        if (task1.isSuccessful()) {
                                                            Toast.makeText(getActivity(), "Leave Applied Successfully", Toast.LENGTH_SHORT).show();
                                                        } else {
                                                            Toast.makeText(getActivity(), "Failed to Apply Leave", Toast.LENGTH_SHORT).show();
                                                        }
                                                    });
                                        } else {
                                            Toast.makeText(getActivity(), "Failed to Get Document Count", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                });
                            })
                            .addOnFailureListener(e -> {
                                Toast.makeText(requireContext(), "Failed to upload image", Toast.LENGTH_SHORT).show();
                            });
                } else {
                    Toast.makeText(requireContext(), "No image selected", Toast.LENGTH_SHORT).show();
                }

            }
        });



        return view;
    }
    private void selectImageFromGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQUEST_IMAGE_PICK);
    }
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_PICK && resultCode == RESULT_OK) {
            assert data != null;
            imageUri = data.getData();
        }
    }
}