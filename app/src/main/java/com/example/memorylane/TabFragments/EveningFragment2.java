package com.example.memorylane.TabFragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.example.memorylane.R;
import com.example.memorylane.WritingActivity;

public class EveningFragment2 extends Fragment {

    public static EditText editText;
    public static Spinner spinner;

    public EveningFragment2() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_evening2, container, false);
        editText = view.findViewById(R.id.editText_evening);
        spinner = view.findViewById(R.id.spinner_evening);
        ArrayAdapter<String> adapter =  new ArrayAdapter<String>(getContext(),
                R.layout.spinner_item, getResources().getStringArray(R.array.moods));
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinner.setAdapter(adapter);

        return view;
    }
}
