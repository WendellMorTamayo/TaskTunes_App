package com.example.tasktunesapp.Fragments;

import android.content.ContentUris;
import android.content.Context;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.tasktunesapp.Adapter.MusicListAdapter;
import com.example.tasktunesapp.Model.AudioModel;
import com.example.tasktunesapp.Panel.RootMediaPlayerPanel;
import com.example.tasktunesapp.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.android.material.card.MaterialCardView;
import com.realgear.multislidinguppanel.MultiSlidingUpPanelLayout;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MusicFragment extends Fragment implements MusicListAdapter.OnItemClickListener{

    View rootView;

    TextView noMusicTextView;
    ImageView albumCover;
    FrameLayout musicLibraryFrame;
    MaterialCardView songCard;

    private MultiSlidingUpPanelLayout panelLayout;
    private RootMediaPlayerPanel mediaPlayerPanel;
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
        albumCover = rootView.findViewById(R.id.icon_view);
        songCard = rootView.findViewById(R.id.card_view);
        panelLayout = rootView.findViewById(R.id.multiSlidingUpPanel);
        mediaPlayerPanel = new RootMediaPlayerPanel(requireContext(), panelLayout);


        loadSongsList();
        setupRecyclerView();

        mediaPlayerPanel = new RootMediaPlayerPanel(requireContext(), panelLayout, songsList);

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
                MediaStore.Audio.Media.DURATION,
                MediaStore.Audio.Media.ALBUM_ID // Add album ID column to retrieve album cover information
        };

        Context context = requireContext().getApplicationContext();
        Cursor cursor = context.getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, projection, null, null, null);
        if (cursor != null) {
            int albumIdColumnIndex = cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID);
            while (cursor.moveToNext()) {
                AudioModel songData = new AudioModel(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getLong(4));
                if (new File(songData.getPath()).exists()) {
                    // Retrieve album cover using album ID
                    if (albumIdColumnIndex != -1) {
                        long albumId = cursor.getLong(albumIdColumnIndex);
                        Bitmap albumCover = getAlbumCoverFromAlbumId(context, albumId);
                        songData.setAlbumCover(albumCover);
                    }
                    songsList.add(songData);
                }
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

    private Bitmap getAlbumCoverFromAlbumId(Context context, long albumId) {
        Bitmap albumCover = null;
        try {
            Uri albumArtUri = Uri.parse("content://media/external/audio/albumart");
            Uri albumCoverUri = ContentUris.withAppendedId(albumArtUri, albumId);
            ParcelFileDescriptor parcelFileDescriptor = context.getContentResolver().openFileDescriptor(albumCoverUri, "r");
            if (parcelFileDescriptor != null) {
                FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
                albumCover = BitmapFactory.decodeFileDescriptor(fileDescriptor);
                parcelFileDescriptor.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return albumCover;
    }


    @Override
    public void onItemClick(AudioModel songData) {
        String title = songData.getTitle();
        String artist = songData.getArtist();
        Toast.makeText(requireContext(), "Title: " + title + ", Artist: " + artist, Toast.LENGTH_SHORT).show();

        // Display the album cover
        Bitmap albumCoverBitmap = songData.getAlbumCover();
        if (albumCoverBitmap != null) {
            albumCover.setImageBitmap(albumCoverBitmap);
        } else {
            // Handle the case where no album cover is available
            albumCover.setImageResource(R.drawable.ic_album);
        }

        // Expand the RootMediaPlayerPanel here
        mediaPlayerPanel.setPanelState(MultiSlidingUpPanelLayout.EXPANDED);
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

