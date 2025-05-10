package com.example.youtuberapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

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


        Glide.with(holder.ivThumbnail.getContext())
                .load(video.getImageUri().toString()) // Convert Uri back to string for Glide
                .placeholder(R.drawable.thumbnail) // Optional loading placeholder
                .error(R.drawable.thumbnail) // Optional error placeholder
                .into(holder.ivThumbnail);

        holder.itemView.setOnClickListener(v -> {

            String vid = video.getVideoId();

            // Intent to launch in YouTube app
            Intent appIntent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("vnd.youtube:" + vid));
            // Fallback: launch in browser
            Intent webIntent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://www.youtube.com/watch?v=" + vid));

            PackageManager pm = context.getPackageManager();
            if (appIntent.resolveActivity(pm) != null) {
                context.startActivity(appIntent);
            } else {
                context.startActivity(webIntent);
            }


        });
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
