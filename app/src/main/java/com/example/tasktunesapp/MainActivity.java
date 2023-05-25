package com.example.tasktunesapp;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tasktunesapp.Adapter.MusicListAdapter;
import com.example.tasktunesapp.Adapter.StateFragmentAdapter;
import com.example.tasktunesapp.Panel.RootMediaPlayerPanel;
import com.example.tasktunesapp.Panel.RootNavigationBarPanel;

import com.example.tasktunesapp.View.MediaPlayerBarView;
import com.example.tasktunesapp.View.MediaPlayerView;
import com.realgear.multislidinguppanel.Adapter;
import com.realgear.multislidinguppanel.MultiSlidingUpPanelLayout;
import com.realgear.multislidinguppanel.PanelStateListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MultiSlidingUpPanelLayout panelLayout = findViewById(R.id.root_sliding_up_panel);
        List<Class<?>> items = new ArrayList<>();


        items.add(RootMediaPlayerPanel.class);
        items.add(RootNavigationBarPanel.class);

        panelLayout.setPanelStateListener(new PanelStateListener(panelLayout) {});


        // The adapter handles the items you can also extend it but I don't recommend for
        // beginners
        panelLayout.setAdapter(new Adapter(this, items) {});
    }
}
