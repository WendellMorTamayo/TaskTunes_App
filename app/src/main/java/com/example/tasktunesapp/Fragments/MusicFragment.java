package com.example.tasktunesapp.Fragments;

import android.content.Context;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.tasktunesapp.Adapter.MusicListAdapter;
import com.example.tasktunesapp.Model.AudioModel;
import com.example.tasktunesapp.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.button.MaterialButtonToggleGroup;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MusicFragment extends Fragment {

    View rootView;

    TextView noMusicTextView;
    FrameLayout musicLibraryFrame;
    private MaterialButtonToggleGroup toggleButton;
    private ArrayList<AudioModel> songsList = new ArrayList<>();

    public MusicFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_music, container, false);
        toggleButton = rootView.findViewById(R.id.btnMusicToggle);
        noMusicTextView = rootView.findViewById(R.id.no_songs_text);
        musicLibraryFrame = rootView.findViewById(R.id.music_library_frame);

        loadSongsList();
        setupRecyclerView();

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

    private void loadSongsList() {
        String[] projection = {
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.ARTIST,
                MediaStore.Audio.Media.ALBUM,
                MediaStore.Audio.Media.DATA,
                MediaStore.Audio.Media.DURATION
        };

        Context context = requireContext().getApplicationContext();
        Cursor cursor = context.getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, projection, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                AudioModel songData = new AudioModel(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getLong(4));
                if (new File(songData.getPath()).exists())
                    songsList.add(songData);
            }
            cursor.close();
        }

        Collections.sort(songsList, new Comparator<AudioModel>() {
            @Override
            public int compare(AudioModel song1, AudioModel song2) {
                return song1.getTitle().compareToIgnoreCase(song2.getTitle());
            }
        });
    }

    private void setupRecyclerView() {
        if (songsList.size() == 0) {
            noMusicTextView.setVisibility(View.VISIBLE);
        } else {
            SongListFragment songListFragment = new SongListFragment(songsList);
            getChildFragmentManager()
                    .beginTransaction()
                    .replace(R.id.music_library_frame, songListFragment)
                    .commit();
        }
    }

    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        Animation animation;
        if (enter) {
            animation = AnimationUtils.loadAnimation(getContext(), R.anim.h_fragment_enter);
        } else {
            animation = AnimationUtils.loadAnimation(getContext(), R.anim.h_fragment_exit);
        }
        return animation;
    }

    @Override
    public void onResume() {
        super.onResume();
        setupRecyclerView();
    }
}
