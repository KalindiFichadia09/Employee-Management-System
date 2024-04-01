package com.example.autofusion;

import static android.app.Activity.RESULT_OK;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;

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

import org.w3c.dom.Text;

import java.util.Calendar;

public class apply_leave extends Fragment {
    Spinner leave_type,leave_category;
    String v1,v2;
    View view;
    ImageView leave_attachment;
    Button btn_apply;
    EditText leave_remarks;
    TextView leave_start_date,leave_end_date;
    TextView txt_ans;
    private final int GALLERY_REQUEST_CODE = 1000;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview = inflater.inflate(R.layout.fragment_apply_leave, container, false);

        leave_type = rootview.findViewById(R.id.leave_type);
        leave_category = rootview.findViewById(R.id.leave_category);
        leave_start_date = rootview.findViewById(R.id.leave_start_date);
        leave_end_date = rootview.findViewById(R.id.leave_end_date);
        leave_remarks = rootview.findViewById(R.id.leave_remarks);
        leave_attachment = rootview.findViewById(R.id.leave_attachment);
        btn_apply = rootview.findViewById(R.id.btn_apply);

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


        btn_apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



            }
        });

        String[] leave_type_s = getResources().getStringArray(R.array.leave_type);
        String[] leave_category_S = getResources().getStringArray(R.array.leave_category);

        ArrayAdapter<String> leave_type_ad = new ArrayAdapter<String>(requireContext(), android.R.layout.simple_spinner_item, leave_type_s);
        ArrayAdapter<String> leave_category_ad = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item,leave_category_S);

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

        leave_attachment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent gallery = new Intent(Intent.ACTION_PICK);
                gallery.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(gallery,GALLERY_REQUEST_CODE);


            }
        });
        return rootview;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if(requestCode == GALLERY_REQUEST_CODE) {
                //for gallery
                leave_attachment.setImageURI(data.getData());

                leave_attachment.getLayoutParams().width = 500; // set width to 200 pixels
                leave_attachment.getLayoutParams().height = 500; // set height to 200 pixels
            }
        }
    }
}