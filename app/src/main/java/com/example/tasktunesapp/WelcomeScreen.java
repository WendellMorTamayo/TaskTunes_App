package com.example.tasktunesapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class WelcomeScreen extends AppCompatActivity {

    Button btnLetsGo;

    private void setFirstTimeLaunchFlag() {
        SharedPreferences sharedPreferences = getSharedPreferences("APP_PREFERENCES", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("IS_FIRST_TIME_LAUNCH", false);
        editor.apply();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);

        btnLetsGo = findViewById(R.id.btnLetsGo);

        btnLetsGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFirstTimeLaunchFlag();

                Intent intent = new Intent(WelcomeScreen.this, Dashboard.class);
                startActivity(intent);

                finish();
            }
        });

    }
}