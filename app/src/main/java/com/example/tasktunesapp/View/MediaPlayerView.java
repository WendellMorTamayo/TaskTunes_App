package com.example.tasktunesapp.View;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.IdRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.tasktunesapp.R;
import com.example.tasktunesapp.databinding.LayoutMediaPlayerViewBinding;

import org.w3c.dom.Text;

public class MediaPlayerView extends AppCompatActivity {
    LayoutMediaPlayerViewBinding mediaPlayerViewBinding;
    public static final int STATE_NORMAL = 0;
    public static final int STATE_PARTIAL = 1;

    private final View mRootView;

    private int mState;
    private FrameLayout mBottomSheet;
    private ConstraintLayout mControlsContainer;
    TextView mediaTitleTextView, mediaArtistTextView;

    public MediaPlayerView(View rootView) {
        this.mRootView = rootView;

        this.mBottomSheet = findViewByID(R.id.media_player_bottom_sheet_behavior);
        this.mControlsContainer = findViewByID(R.id.media_player_controls_container);

        this.mRootView.setAlpha(0.0F);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mediaTitleTextView = (TextView) findViewByID(R.id.music_title_mediaview);
        this.mediaArtistTextView = (TextView) findViewByID(R.id.music_artist_mediaview);
    }

    public void onSliding(float slideOffset, int state) {
        float fadeStart = 0.25F;
        float alpha = (slideOffset - fadeStart) * (1F / (1F - fadeStart));

        if(state == STATE_NORMAL) {
            this.mRootView.setAlpha(alpha);
            this.mControlsContainer.setAlpha(1F);
        } else {
            this.mControlsContainer.setAlpha(1F - alpha);
        }

        this.mState = state;
    }

    public void updateMusicInfo(String title, String artist) {
        mediaTitleTextView.setText(title);
        mediaArtistTextView.setText(artist);
    }


    public <T extends View> T findViewByID(@IdRes int id) {
        return this.mRootView.findViewById(id);
    }
}
