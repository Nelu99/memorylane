package com.example.memorylane.TabFragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.memorylane.R;

import java.util.Set;

public class BlankFragment3 extends Fragment {

    public BlankFragment3() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_blank3, container, false);

        final EditText userName = view.findViewById(R.id.user_name);
        final SharedPreferences sharedPrefs = getActivity().
                getSharedPreferences("com.example.roadmission", Context.MODE_PRIVATE);
        userName.setText(sharedPrefs.getString("user_name",""),
                TextView.BufferType.EDITABLE);

        userName.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {}
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                SharedPreferences.Editor editor = sharedPrefs.edit();
                editor.putString("user_name", userName.getText().toString());
                editor.apply();
            }
        });

        return view;
    }

}
