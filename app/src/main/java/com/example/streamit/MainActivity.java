package com.example.streamit;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = null;
    RecyclerView videoList;
    VideoAdapter adapter;
    List<Video> allVideos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        videoList = findViewById(R.id.recyclerView1);
        videoList.setLayoutManager(new LinearLayoutManager(this));
        allVideos = new ArrayList<>();

        adapter = new VideoAdapter(this, allVideos);
        videoList.setAdapter(adapter);


        getJsonData();
    }

    private void getJsonData() {
        String URL = "https://raw.githubusercontent.com/sammykamau/android-videostreaming/main/data.JSON?token=GHSAT0AAAAAACFHKZCOXGKJXFLQ43QLHH2UZFWYTEQ";
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        @SuppressLint("NotifyDataSetChanged") JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, URL, null, response -> {

            try {
                JSONArray categories = response.getJSONArray("categories");
                JSONObject categoryData = categories.getJSONObject(0);
                JSONArray videos = categoryData.getJSONArray("videos");

                for (int i = 0; i < videos.length(); i++){
                    JSONObject video = videos.getJSONObject(i);

                    Video v = new Video();

                    v.setTitle(video.getString("title"));
                    v.setDescription(video.getString("description"));
                    v.setAuthor(video.getString("subtitle"));
                    v.setImageURL(video.getString("thumb"));
                    JSONArray videoUrl = video.getJSONArray("sources");
                    v.setVideoURL(videoUrl.getString(0));

                    Log.d(TAG, "onResponse: " + v.getVideoURL());

                    allVideos.add(v);
                    adapter.notifyDataSetChanged();

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> Log.d(TAG, "onErrorResponse: " + error.getMessage()));

        requestQueue.add(jsonObjectRequest);
    }
}