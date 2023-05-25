package com.example.tasktunesapp.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import com.example.tasktunesapp.Binding.MediaPlayerBinding;
import com.example.tasktunesapp.Common.MediaPlayer;
import com.example.tasktunesapp.Common.MyMediaPlayer;
import com.example.tasktunesapp.Model.AudioModel;
import com.example.tasktunesapp.databinding.LayoutMediaPlayerBarBinding;
import com.example.tasktunesapp.databinding.LayoutMediaPlayerViewBinding;
import com.example.tasktunesapp.R;
import com.example.tasktunesapp.View.MediaPlayerBarView;
import com.example.tasktunesapp.View.MediaPlayerView;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;

public class MusicListAdapter extends RecyclerView.Adapter<MusicListAdapter.ViewHolder> {

    ArrayList<AudioModel> songsList;
    Context context;
    MaterialCardView cardView;
    MediaPlayer mediaPlayer;
    LayoutInflater inflater;
    LayoutMediaPlayerBarBinding mediaPlayerBarBinding;
    LayoutMediaPlayerViewBinding mediaPlayerViewBinding;
    private MediaPlayerBarView mediaPlayerBarView;
    MediaPlayerView mediaPlayerView;

    public MusicListAdapter(ArrayList<AudioModel> songsList, Context context) {
        this.songsList = songsList;
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        mediaPlayer = new MediaPlayer(context);
    }

    public MusicListAdapter(ArrayList<AudioModel> songsList, Context context, MediaPlayerBarView mediaPlayerBarView, MediaPlayerView mediaPlayerView) {
        this.songsList = songsList;
        this.context = context;
        this.mediaPlayerBarView = mediaPlayerBarView;
        this.mediaPlayerView = mediaPlayerView;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
        cardView = view.findViewById(R.id.card_view);
        mediaPlayerViewBinding = LayoutMediaPlayerViewBinding.inflate(inflater, (ViewGroup) view, false);
        mediaPlayerBarBinding = LayoutMediaPlayerBarBinding.inflate(inflater, (ViewGroup) view, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        AudioModel songData = songsList.get(position);
        holder.titleTextView.setText(songData.getTitle());
        holder.artistTextView.setText(songData.getArtist());


        if (songData.getAlbumCover() != null) {
            holder.iconImageView.setImageBitmap(songData.getAlbumCover());
        } else {
            holder.iconImageView.setImageResource(R.drawable.ic_album);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.play(songData.getPath());
            }
        });

        if (MyMediaPlayer.currentIndex == position) {
            holder.titleTextView.setTextColor(Color.parseColor("#FFFFFFFF"));
            holder.artistTextView.setTextColor(Color.parseColor("#19FFFFFF"));
        } else {
            holder.titleTextView.setTextColor(Color.parseColor("#FFFFFF"));
            holder.artistTextView.setTextColor(Color.parseColor("#19FFFFFF"));
        }

    }

    @Override
    public int getItemCount() {
        return songsList.size();
    }

    public interface OnItemClickListener {
        void onItemClick(AudioModel songData);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView titleTextView;
        ImageView iconImageView;
        TextView artistTextView;



        public ViewHolder(View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.music_title_text);
            iconImageView = itemView.findViewById(R.id.icon_view);
            artistTextView = itemView.findViewById(R.id.music_artist_text);

        }

    }

}
