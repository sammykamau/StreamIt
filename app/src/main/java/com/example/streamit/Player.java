package com.example.streamit;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

public class Player extends AppCompatActivity {

    ProgressBar spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        spinner = findViewById(R.id.progressBar);
        Intent i = getIntent();
        Bundle data = i.getExtras();
        Video v = (Video) data.getSerializable("videoData");

        getSupportActionBar().setTitle(v.getTitle());

        TextView title = findViewById(R.id.videoTitle);
        TextView desc = findViewById(R.id.videoDescription);
        VideoView videoView = findViewById(R.id.videoView);

        title.setText(v.getTitle());
        desc.setText(v.getDescription());

        Uri videoUrl = Uri.parse(v.getVideoURL());
        videoView.setVideoURI(videoUrl);

        MediaController mc = new MediaController(this);
        videoView.setMediaController(mc);
        videoView.setOnPreparedListener(mediaPlayer -> {
            videoView.start();
            spinner.setVisibility(View.GONE);
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}