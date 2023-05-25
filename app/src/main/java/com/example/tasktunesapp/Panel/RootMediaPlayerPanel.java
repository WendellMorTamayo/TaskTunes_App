package com.example.tasktunesapp.Panel;

import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import com.example.tasktunesapp.Adapter.MusicListAdapter;
import com.example.tasktunesapp.Adapter.StateFragmentAdapter;
import com.example.tasktunesapp.Behavior.bottomsheet.CustomBottomSheetBehavior;
import com.example.tasktunesapp.Fragments.HomeFragment;
import com.example.tasktunesapp.Fragments.MusicFragment;
import com.example.tasktunesapp.Model.AudioModel;
import com.example.tasktunesapp.R;
import com.example.tasktunesapp.View.MediaPlayerBarView;
import com.example.tasktunesapp.View.MediaPlayerView;
import com.realgear.multislidinguppanel.BasePanelView;
import com.realgear.multislidinguppanel.IPanel;
import com.realgear.multislidinguppanel.MultiSlidingUpPanelLayout;
import com.realgear.readable_bottom_bar.ReadableBottomBar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RootMediaPlayerPanel extends BasePanelView {

    private MediaPlayerBarView mMediaPlayerBarView;
    private MediaPlayerView mMediaPlayerView;
    private MultiSlidingUpPanelLayout panelLayout;

    private TextView titleTextView, artistTextView, titleTextBarView, artistTextBarView;

    private Map<Class<? extends BasePanelView>, BasePanelView> panelViewMap;
    private ArrayList<AudioModel> songsList;

    public RootMediaPlayerPanel(@NonNull Context context, MultiSlidingUpPanelLayout panelLayout) {
        super(context, panelLayout);
        this.panelLayout = panelLayout;
        panelViewMap = new HashMap<>();
        getContext().setTheme(R.style.Theme_App);
        LayoutInflater.from(getContext()).inflate(R.layout.layout_root_mediaplayer, this, true);
    }

    public RootMediaPlayerPanel(@NonNull Context context, MultiSlidingUpPanelLayout panelLayout, ArrayList<AudioModel> songsList) {
        super(context, panelLayout);
        this.panelLayout = panelLayout;
        this.songsList = songsList;
        panelViewMap = new HashMap<>();
        getContext().setTheme(R.style.Theme_App);
        LayoutInflater.from(getContext()).inflate(R.layout.layout_root_mediaplayer, this, true);
    }

    @Override
    public void onCreateView() {
        this.setPanelState(MultiSlidingUpPanelLayout.COLLAPSED);
        this.setSlideDirection(MultiSlidingUpPanelLayout.SLIDE_VERTICAL);

        this.setPeakHeight(getResources().getDimensionPixelSize(R.dimen.mediaplayerbar_height));

    }

    @Override
    public void onBindView() {

        mMediaPlayerView = new MediaPlayerView(findViewById(R.id.media_player_view));
        mMediaPlayerBarView = new MediaPlayerBarView(findViewById(R.id.media_player_bar_view));

        MusicListAdapter musicListAdapter = new MusicListAdapter(songsList, getContext(), mMediaPlayerBarView, mMediaPlayerView);
        DisplayMetrics dm = getResources().getDisplayMetrics();
        FrameLayout layout = findViewById(R.id.media_player_bottom_sheet_behavior);

        ViewGroup.LayoutParams params = layout.getLayoutParams();
        params.height = dm.heightPixels - (mPeakHeight);
        layout.setLayoutParams(params);

        CustomBottomSheetBehavior<FrameLayout> bottomSheetBehavior = CustomBottomSheetBehavior.from(layout);
        bottomSheetBehavior.setSkipAnchored(false);
        bottomSheetBehavior.setAllowUserDragging(true);

        bottomSheetBehavior.setAnchorOffset((int)(dm.heightPixels * 0.75F));
        bottomSheetBehavior.setPeekHeight(getPeakHeight());
        bottomSheetBehavior.setState(CustomBottomSheetBehavior.STATE_COLLAPSED);

        bottomSheetBehavior.addBottomSheetCallback(new CustomBottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int oldState, int newState) {
                switch (newState) {
                    case CustomBottomSheetBehavior.STATE_ANCHORED:
                    case CustomBottomSheetBehavior.STATE_EXPANDED:
                    case CustomBottomSheetBehavior.STATE_DRAGGING:
                        getMultiSlidingUpPanel().setSlidingEnabled(false);
                        break;
                    default:
                        getMultiSlidingUpPanel().setSlidingEnabled(true);
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                mMediaPlayerView.onSliding(slideOffset, MediaPlayerView.STATE_PARTIAL);
                mMediaPlayerBarView.onSliding(slideOffset, MediaPlayerBarView.STATE_PARTIAL);
            }
        });

    }

    @Override
    public void onPanelStateChanged(int panelSate) {

    }

    public <T extends BasePanelView> T getPanelView(Class<T> panelViewClass) {
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            if (panelViewClass.isInstance(child)) {
                return panelViewClass.cast(child);
            }
        }
        throw new IllegalArgumentException("Panel view not found in MultiSlidingUpPanelLayout");
    }

    @Override
    public void onSliding(@NonNull IPanel<View> panel, int top, int dy, float slidingOffset) {
        super.onSliding(panel, top, dy, slidingOffset);

        mMediaPlayerView.onSliding(slidingOffset, MediaPlayerView.STATE_NORMAL);
        mMediaPlayerBarView.onSliding(slidingOffset, MediaPlayerBarView.STATE_NORMAL);
    }

}
