package com.example.tasktunesapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.inputmethod.EditorInfo;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class Dashboard extends AppCompatActivity {

    private TextInputLayout searchInputLayout;
    private TextInputEditText searchEditText;

    private boolean isFirstTimeLaunch() {
        SharedPreferences sharedPreferences = getSharedPreferences("APP_PREFERENCES", MODE_PRIVATE);
        return sharedPreferences.getBoolean("IS_FIRST_TIME_LAUNCH", true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        if (isFirstTimeLaunch()) {
            // Start the welcome screen activity
            Intent welcomeIntent = new Intent(this, WelcomeScreen.class);
            startActivity(welcomeIntent);

            // Finish the main activity so the user can't navigate back to it
            finish();
        }

        searchInputLayout = findViewById(R.id.search_input_layout);
        searchEditText = findViewById(R.id.search_edit_text);

        searchEditText.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                performSearch(searchEditText.getText().toString());
                return true;
            }
            return false;
        });

        searchInputLayout.setEndIconOnClickListener(view -> searchEditText.setText(""));
    }

    private void performSearch(String query) {
        // Implement your search functionality here
    }
}