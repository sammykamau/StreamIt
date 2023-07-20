package com.example.streamit;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.ViewHolder> {
    private final List<Video> allVideos;
    private final Context context;


    public VideoAdapter(Context ctx, List<Video> videos){
        this.allVideos = videos;
        this.context = ctx;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.video_view,parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.title.setText(allVideos.get(position).getTitle());
        Picasso.get().load(allVideos.get(position).getImageURL()).into(holder.videoImage);
        holder.author.setText(allVideos.get(position).getAuthor());

        holder.vv.setOnClickListener(view -> {
            Bundle b = new Bundle();
            b.putSerializable("videoData",allVideos.get(position));
            Intent i = new Intent(context, Player.class);
            i.putExtras(b);
            view.getContext().startActivity(i);
        });

    }

    @Override
    public int getItemCount() {
        return allVideos.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView videoImage;
        TextView title;
        TextView author, totalViews, time;

        View vv;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            videoImage = itemView.findViewById(R.id.image);
            title = itemView.findViewById(R.id.title);
            totalViews= itemView.findViewById(R.id.views);
            author = itemView.findViewById(R.id.author);
            time = itemView.findViewById(R.id.time);
            vv = itemView;
        }
    }
}
