package com.example.tasktunesapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.tasktunesapp.Fragments.HomeFragment;
import com.example.tasktunesapp.Fragments.MusicFragment;
import com.example.tasktunesapp.Model.AudioModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNav;
    Fragment homeFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNav = findViewById(R.id.bottomNavBar);



        bottomNav.setSelectedItemId(R.id.home);
        setColorStateNavBar(bottomNav);
        bottomNav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        replaceFragment(new HomeFragment());
                        return true;
                    case R.id.music_library:
                        // Add code to replace with Music Library Fragment
                        replaceFragment(new MusicFragment());
                        return true;
                    case R.id.settings:
                        // Add code to replace with Settings Fragment
                        return true;
                }
                return false;
            }
        });

        // Set the initial fragment
        homeFragment = new HomeFragment();
        replaceFragment(homeFragment);
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.mainFragment, fragment);
        fragmentTransaction.commit();
    }

    private void setColorStateNavBar(BottomNavigationView bottomNavigationView) {
        int selectedColor = ContextCompat.getColor(this, R.color.bg_color);
        int unselectedColor = ContextCompat.getColor(this, R.color.white);
        int[][] states = new int[][] {
                new int[] { android.R.attr.state_selected },
                new int[] { }
        };
        int[] colors = new int[] {
                selectedColor,
                unselectedColor
        };
        ColorStateList colorStateList = new ColorStateList(states, colors);
        bottomNav.setItemIconTintList(colorStateList);
        bottomNav.setItemTextColor(colorStateList);
    }


    private void searchBarFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.searchFrame, fragment);
        fragmentTransaction.commit();
    }

}
