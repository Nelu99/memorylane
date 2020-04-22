package com.example.memorylane.TabFragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;

import com.example.memorylane.R;

public class BlankFragment2 extends Fragment {

    public BlankFragment2() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_blank2, container, false);
        final ImageButton imgbt = (ImageButton) view.findViewById(R.id.blank2_button);
        Animation anim = (Animation) AnimationUtils.loadAnimation(getContext(), R.anim.pop);
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}
            @Override
            public void onAnimationRepeat(Animation animation) {}
            @Override
            public void onAnimationEnd(Animation arg0) {
                Animation anim = (Animation) AnimationUtils.loadAnimation(getContext(), R.anim.pop);
                anim.setAnimationListener(this);
                imgbt.startAnimation(anim);
            }
            });
        imgbt.startAnimation(anim);

        return view;
    }
}
