package com.example.tasktunesapp.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.example.tasktunesapp.R;

public class MusicFragment extends Fragment {



    public MusicFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_music, container, false);
    }

    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        Animation animation;
        if (enter) {
            // Fragment enter animation
            animation = AnimationUtils.loadAnimation(getContext(), R.anim.h_fragment_enter);
        } else {
            // Fragment exit animation
            animation = AnimationUtils.loadAnimation(getContext(), R.anim.h_fragment_exit);
        }
        return animation;
    }
}