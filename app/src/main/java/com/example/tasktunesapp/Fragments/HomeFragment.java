package com.example.tasktunesapp.Fragments;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.Toast;

import com.example.tasktunesapp.Common.WelcomeScreen;
import com.example.tasktunesapp.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import static android.content.Context.INPUT_METHOD_SERVICE;
import static android.content.Context.MODE_PRIVATE;

public class HomeFragment extends Fragment {
    private Button iconButton;
    private MaterialButtonToggleGroup toggleButton;

    public HomeFragment() {
        // Required empty public constructor
    }

    private boolean isFirstTimeLaunch() {
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("APP_PREFERENCES", MODE_PRIVATE);
        return sharedPreferences.getBoolean("IS_FIRST_TIME_LAUNCH", true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        iconButton = rootView.findViewById(R.id.iconCreate);
        toggleButton = rootView.findViewById(R.id.btnToggle);

        if (isFirstTimeLaunch()) {
            Intent welcomeIntent = new Intent(requireContext(), WelcomeScreen.class);
            startActivity(welcomeIntent);
            requireActivity().finish();
        }

        // Set click listener for the button
        iconButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle button click event
                Toast.makeText(requireContext(), "Button is clicked!", Toast.LENGTH_SHORT).show();
            }
        });

        toggleButton.addOnButtonCheckedListener(new MaterialButtonToggleGroup.OnButtonCheckedListener() {
            @Override
            public void onButtonChecked(MaterialButtonToggleGroup group, int checkedId, boolean isChecked) {
                if (isChecked) {
                    for (int i = 0; i < group.getChildCount(); i++) {
                        View view = group.getChildAt(i);
                        if (view instanceof MaterialButton) {
                            MaterialButton button = (MaterialButton) view;
                            if (button.getId() == checkedId) {
                                button.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorButtonSelected)));
                            } else {
                                button.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.bg_color)));
                            }
                        }
                    }
                }
            }
        });
        toggleButton.check(R.id.button1);
        return rootView;
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
