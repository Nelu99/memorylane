package com.example.memorylane.TabFragments;

import android.os.Bundle;

import androidx.preference.PreferenceFragmentCompat;

import com.example.memorylane.R;

public class BlankFragment extends PreferenceFragmentCompat {
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.fragment_blank, rootKey);
    }
}
