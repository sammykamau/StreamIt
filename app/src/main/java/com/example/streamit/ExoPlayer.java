package com.example.streamit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.VideoView;

import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.ui.StyledPlayerView;

import java.util.Objects;

public class ExoPlayer extends AppCompatActivity {
    com.google.android.exoplayer2.ExoPlayer player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exo_player);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        Intent i = getIntent();
        Bundle data = i.getExtras();
        assert data != null;
        Video v = (Video) data.getSerializable("videoData");

        assert v != null;
        getSupportActionBar().setTitle(v.getTitle());

        Uri videoUrl = Uri.parse(v.getVideoURL());

        StyledPlayerView playerView = findViewById(R.id.playerView);
        player = new com.google.android.exoplayer2.ExoPlayer.Builder(ExoPlayer.this).build();
        playerView.setPlayer(player);

        // Build the media item.
        //MediaItem mediaItem =
        MediaItem mediaItem = MediaItem.fromUri(videoUrl);
        // Set the media item to be played.
        player.setMediaItem(mediaItem);
        // Prepare the player.
        player.prepare();
        // Start the playback.
        player.play();

    }

    @Override
    protected void onStop() {
        super.onStop();
        player.setPlayWhenReady(false);
        player = null;
    }
}