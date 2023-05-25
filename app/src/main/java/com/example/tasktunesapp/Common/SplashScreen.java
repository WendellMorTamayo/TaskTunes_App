package com.example.tasktunesapp.Common;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tasktunesapp.MainActivity;
import com.example.tasktunesapp.R;

public class SplashScreen extends AppCompatActivity {
    private static int SPLASH_SCREEN = 2000;
    private static final String PERMISSION_READ_MEDIA_AUDIO = Manifest.permission.READ_EXTERNAL_STORAGE;
    private static final int PERMISSION_REQ_CODE = 100;
    ImageView imageView;
    TextView txtTaskTunes, txtTagline;
    Button btnLetsGo;
    Fragment fragment_home;
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
                requestRuntimePermission();
            }
        }, SPLASH_SCREEN);




    }

    private void requestRuntimePermission() {
        if(ActivityCompat.checkSelfPermission(this, PERMISSION_READ_MEDIA_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            if(ActivityCompat.shouldShowRequestPermissionRationale(this, PERMISSION_READ_MEDIA_AUDIO)) {
                showRationaleForAudio();
            } else {
                ActivityCompat.requestPermissions(this, new String[]{PERMISSION_READ_MEDIA_AUDIO}, PERMISSION_REQ_CODE);
            }
        } else {
            if (isFirstTimeLaunch()) {
                startWelcomeScreen();
            } else {
                startMainActivity();
            }
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQ_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission Granted.", Toast.LENGTH_SHORT).show();
                startMainActivity();
            } else {
                Toast.makeText(this, "Permission Denied.", Toast.LENGTH_SHORT).show();
                startMainActivity(); // start MainActivity even if permission is denied
            }
        }
    }

    private void showRationaleForAudio() {
        new AlertDialog.Builder(this)
                .setMessage("This app requires permission to access audio files.")
                .setTitle("Permission Required")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.requestPermissions(SplashScreen.this, new String[]{PERMISSION_READ_MEDIA_AUDIO}, PERMISSION_REQ_CODE);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startMainActivity(); // start MainActivity if the user declines the permission
                    }
                })
                .show();
    }


    private void startMainActivity() {
        Intent intent = new Intent(SplashScreen.this, MainActivity.class);
        startActivity(intent);
    }

    private void startWelcomeScreen() {
        Intent intent = new Intent(SplashScreen.this, WelcomeScreen.class);
        startActivity(intent);
        finish();
    }

}