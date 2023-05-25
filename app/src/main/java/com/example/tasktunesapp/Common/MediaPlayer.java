package com.example.tasktunesapp.Common;

import android.app.PendingIntent;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.tasktunesapp.R;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.audio.AudioAttributes;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.PlayerNotificationManager;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;

public class MediaPlayer {
    private Context context;
    private ExoPlayer player;
    private PlayerNotificationManager notificationManager;
    private boolean isPlaying;

    private TextView titleBar;

    public TextView getTitleBar() {
        return titleBar;
    }

    public void setTitleBar(TextView titleBar) {
        this.titleBar = titleBar;
    }

    public TextView getArtistBar() {
        return artistBar;
    }

    public void setArtistBar(TextView artistBar) {
        this.artistBar = artistBar;
    }

    public TextView getTitleView() {
        return titleView;
    }

    public void setTitleView(TextView titleView) {
        this.titleView = titleView;
    }

    public TextView getArtistView() {
        return artistView;
    }

    public void setArtistView(TextView artistView) {
        this.artistView = artistView;
    }

    private TextView artistBar;
    private TextView titleView;
    private TextView artistView;

    public MediaPlayer(Context context) {
        this.context = context;
        initializePlayer();
    }

    public MediaPlayer(Context context, TextView titleBar, TextView artistBar, TextView titleView, TextView artistView) {
        this.context = context;
        this.titleBar = titleBar;
        this.artistBar = artistBar;
        this.titleView = titleView;
        this.artistView = artistView;
        initializePlayer();
    }

    private void initializePlayer() {
        player = new ExoPlayer.Builder(context)
                .setAudioAttributes(AudioAttributes.DEFAULT, true)
                .setHandleAudioBecomingNoisy(true)
                .setWakeMode(C.WAKE_MODE_LOCAL)
                .build();
    }

    public void releasePlayer() {
        if (player != null) {
            player.release();
            player = null;
        }
        if (notificationManager != null) {
            notificationManager.setPlayer(null);
        }
    }

    public void play(String audioPath) {
        Uri audioUri = Uri.parse(audioPath);
        MediaItem mediaItem = MediaItem.fromUri(audioUri);
        player.setMediaItem(mediaItem);
        player.prepare();
        player.play();
        isPlaying = true;
    }

    public void pause() {
        player.pause();
        isPlaying = false;
    }

    public void resume() {
        player.play();
        isPlaying = true;
    }

    public boolean isPlaying() {
        return isPlaying;
    }
}
