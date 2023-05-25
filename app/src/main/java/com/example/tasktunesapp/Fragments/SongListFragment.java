package com.example.tasktunesapp.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tasktunesapp.Adapter.MusicListAdapter;
import com.example.tasktunesapp.Model.AudioModel;
import com.example.tasktunesapp.R;
import com.realgear.multislidinguppanel.MultiSlidingUpPanelLayout;

import java.util.ArrayList;

public class SongListFragment extends Fragment {

    private RecyclerView recyclerView;
    private MusicListAdapter adapter;
    private ArrayList<AudioModel> songsList;
    private MultiSlidingUpPanelLayout panelLayout;

    public SongListFragment(ArrayList<AudioModel> songsList) {
        this.songsList = songsList;
    }

    public SongListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_song__list, container, false);

        recyclerView = view.findViewById(R.id.song_list_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        adapter = new MusicListAdapter(songsList, getActivity());
        recyclerView.setAdapter(adapter);

        return view;
    }
}
