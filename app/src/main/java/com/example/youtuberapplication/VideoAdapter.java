package com.example.youtuberapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoHolder> {

    Context context;

    public VideoAdapter(Context context)
    {
        this.context=context;
    }

    @NonNull
    @Override
    public VideoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate singleVideo-List-item layout
        View view = LayoutInflater.from(context).inflate(R.layout.single_videolist_item,parent,false);
        return new VideoHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull VideoHolder holder, int position) {

        Video video = VideosDataClass.videos.get(position);

        // Set data to the views
        holder.tvVideoTitle.setText(video.getTitleName());
        holder.tvChannelName.setText(video.getChannelName());
        holder.tvViewsHours.setText(video.getViews() + " • " + video.getHours());
        holder.ivThumbnail.setImageURI(video.getImageUri());

    }

    @Override
    public int getItemCount() {
        return VideosDataClass.videos.size()-1;
    }

    public static class VideoHolder extends RecyclerView.ViewHolder{
        ImageView ivThumbnail;
        TextView tvVideoTitle,tvChannelName,tvViewsHours;
        public VideoHolder(@NonNull View itemView) {
            super(itemView);
            ivThumbnail=itemView.findViewById(R.id.ivThumbnail);
            tvVideoTitle=itemView.findViewById(R.id.tvVideoTitle);
            tvChannelName=itemView.findViewById(R.id.tvChannelName);
            tvViewsHours=itemView.findViewById(R.id.tvViewsHours);
        }
    }

}
