package com.example.tasktunesapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashScreen extends AppCompatActivity {
    private static int SPLASH_SCREEN = 4000;
    ImageView imageView;
    TextView txtTaskTunes, txtTagline;
    Button btnLetsGo;
    private boolean isFirstTimeLaunch() {
        SharedPreferences sharedPreferences = getSharedPreferences("APP_PREFERENCES", MODE_PRIVATE);
        return sharedPreferences.getBoolean("IS_FIRST_TIME_LAUNCH", true);
    }

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);

        imageView = findViewById(R.id.imageView);
        txtTaskTunes = findViewById(R.id.txtTaskTunes);
        txtTagline = findViewById(R.id.txtTagline);
        btnLetsGo = findViewById(R.id.btnLetsGo);

        Animation topAnimation = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        Animation bottomAnimation = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);

        imageView.startAnimation(topAnimation);
        txtTaskTunes.startAnimation(bottomAnimation);
        txtTagline.startAnimation(bottomAnimation);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (isFirstTimeLaunch()) {
                    Intent intent = new Intent(SplashScreen.this, WelcomeScreen.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(SplashScreen.this, Dashboard.class);
                    startActivity(intent);
                }
                finish();
            }
        }, SPLASH_SCREEN);


    }
}