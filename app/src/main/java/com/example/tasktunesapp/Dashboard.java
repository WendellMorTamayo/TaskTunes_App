package com.example.tasktunesapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.view.MotionEvent;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;

import com.example.tasktunesapp.Common.WelcomeScreen;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class Dashboard extends AppCompatActivity {

    private TextInputLayout searchInputLayout;
    private TextInputEditText searchEditText;
    private MaterialButtonToggleGroup toggleButton;

    private boolean isFirstTimeLaunch() {
        SharedPreferences sharedPreferences = getSharedPreferences("APP_PREFERENCES", MODE_PRIVATE);
        return sharedPreferences.getBoolean("IS_FIRST_TIME_LAUNCH", true);
    }

    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (imm != null && searchEditText != null) {
            imm.hideSoftInputFromWindow(searchEditText.getWindowToken(), 0);
        }
    }



    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (searchEditText.isFocused()) {
                int[] screenLocation = new int[2];
                searchEditText.getLocationOnScreen(screenLocation);
                float x = event.getRawX() + searchEditText.getLeft() - screenLocation[0];
                float y = event.getRawY() + searchEditText.getTop() - screenLocation[1];
                if (x < searchEditText.getLeft() || x > searchEditText.getRight() || y < searchEditText.getTop() || y > searchEditText.getBottom()) {
                    searchEditText.clearFocus();
                    hideKeyboard();
                }
            }
        }
        return super.dispatchTouchEvent(event);
    }

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        if (isFirstTimeLaunch()) {
            Intent welcomeIntent = new Intent(this, WelcomeScreen.class);
            startActivity(welcomeIntent);
            finish();
        }

        searchInputLayout = findViewById(R.id.search_input_layout);
        searchEditText = findViewById(R.id.search_edit_text);
        toggleButton = findViewById(R.id.btnToggle);

        searchEditText.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                performSearch(searchEditText.getText().toString());
                return true;
            }
            return false;
        });

        searchInputLayout.setEndIconOnClickListener(view -> searchEditText.setText(""));

        // Add OnFocusChangeListener to close/unselect the search when clicked outside
//        searchEditText.setOnFocusChangeListener((view, hasFocus) -> {
//            if (!hasFocus) {
//                searchEditText.clearFocus();
//            }
//        });


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
    }

    private void performSearch(String query) {
        // Implement your search functionality here
    }
}
