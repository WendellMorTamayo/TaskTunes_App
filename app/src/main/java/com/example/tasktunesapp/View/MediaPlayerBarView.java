package com.example.tasktunesapp.View;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.IdRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.tasktunesapp.R;
import com.google.android.material.progressindicator.LinearProgressIndicator;

public class MediaPlayerBarView extends AppCompatActivity {
    public static final int STATE_NORMAL = 0;
    public static final int STATE_PARTIAL = 1;

    private final View mRootView;

    private int mState;
    private FrameLayout mBackgroundView;
    private FrameLayout mBottomSheet;
    private LinearProgressIndicator mProgressIndicator;
    private ConstraintLayout mControlsContainer;
    TextView mediaTitleTextView, mediaArtistTextView;

    public MediaPlayerBarView(View rootView) {
        this.mRootView = rootView;
        this.mBackgroundView = findViewByID(R.id.media_player_bar_bg);
        this.mControlsContainer = findViewByID(R.id.media_player_bar_control_container);
        this.mProgressIndicator = findViewByID(R.id.media_player_bar_progress_indicator);
        this.mRootView.setAlpha(1.0F);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mediaTitleTextView = (TextView) findViewByID(R.id.media_player_bar_title);
        this.mediaArtistTextView = (TextView) findViewByID(R.id.media_player_bar_artist);
    }

    public void onSliding(float slideOffset, int state) {
        float fadeStart = 0.25F;
        float alpha = (slideOffset / fadeStart);
        if(state == STATE_NORMAL) {
            this.mRootView.setAlpha(1F - alpha);
            this.mBackgroundView.setAlpha(1F);
            this.mProgressIndicator.setAlpha(1F);
            this.mControlsContainer.setAlpha(1F);
        } else {
            this.mRootView.setAlpha(alpha);
            this.mBackgroundView.setAlpha(0F);
            this.mProgressIndicator.setAlpha(0F);
            this.mControlsContainer.setAlpha(1F);
        }
        this.mState = state;
    }

    public <T extends View> T findViewByID(@IdRes int id) {
        return this.mRootView.findViewById(id);
    }


    public void updateMediaInfo(String title, String artist) {
        mediaTitleTextView.setText(title);
        mediaArtistTextView.setText(artist);
    }
}
