package com.example.autofusion;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.autofusion.databinding.FragmentDboardBinding;

public class Dboard extends Fragment {
    View view;
    SharedPreferences  sp;
    SharedPreferences.Editor spe;
    FragmentDboardBinding dboard;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        dboard =  FragmentDboardBinding.inflate(inflater, container, false);
        view = dboard.getRoot();

        sp = requireContext().getSharedPreferences("AutoFusionLogin", Context.MODE_PRIVATE);

        String unm = sp.getString("Username",null);

        dboard.username.setText(""+unm);

        return view;
    }
}