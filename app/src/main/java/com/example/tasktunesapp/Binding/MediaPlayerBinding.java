package com.example.tasktunesapp.Binding;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.tasktunesapp.R;

public class MediaPlayerBinding {
    private View rootView;
    private TextView musicTitleTextView;
    private TextView musicArtistTextView;

    public MediaPlayerBinding(View rootView, TextView musicTitleTextView, TextView musicArtistTextView) {
        this.rootView = rootView;
        this.musicTitleTextView = musicTitleTextView;
        this.musicArtistTextView = musicArtistTextView;
    }

    public static MediaPlayerBinding inflate(LayoutInflater inflater) {
        View rootView = inflater.inflate(R.layout.layout_media_player_view, null);
        TextView musicTitleTextView = rootView.findViewById(R.id.music_title_mediaview);
        TextView musicArtistTextView = rootView.findViewById(R.id.music_artist_mediaview);
        return new MediaPlayerBinding(rootView, musicTitleTextView, musicArtistTextView);
    }

    public void setMusicTitle(String title) {
        musicTitleTextView.setText(title);
    }

    public void setMusicArtist(String artist) {
        musicArtistTextView.setText(artist);
    }

    public View getRootView() {
        return rootView;
    }
}
