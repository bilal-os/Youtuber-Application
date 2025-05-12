package com.example.youtuberapplication.adapters;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.youtuberapplication.models.VideoLinkModel;

import java.util.List;

import com.example.youtuberapplication.R;

public class VideoLinkAdapter extends RecyclerView.Adapter<VideoLinkAdapter.ViewHolder> {

    private Context context;
    private List<VideoLinkModel> videoList;

    public VideoLinkAdapter(Context context, List<VideoLinkModel> videoList) {
        this.context = context;
        this.videoList = videoList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, channelName, metaInfo, statusBadge;
        CardView cardView;

        public ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.videoTitle);
            channelName = itemView.findViewById(R.id.channelName);
            metaInfo = itemView.findViewById(R.id.videoMeta);
            statusBadge = itemView.findViewById(R.id.statusBadge);
            cardView = itemView.findViewById(R.id.cardContainer);
        }
    }

    @Override
    public VideoLinkAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_video_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(VideoLinkAdapter.ViewHolder holder, int position) {
        VideoLinkModel item = videoList.get(position);
        holder.title.setText(item.getTitle());
        holder.channelName.setText(item.getChannelName());
        holder.metaInfo.setText(item.getMetaInfo());
        holder.statusBadge.setText(item.getStatus());

        // Example: color based on status
        switch (item.getStatus()) {
            case "APPROVED":
                holder.statusBadge.setBackgroundColor(0xFF12B76A);
                break;
            case "NOT_APPROVED":
                holder.statusBadge.setBackgroundColor(0xFFFFB10B);
                break;
            default:
                holder.statusBadge.setBackgroundColor(0xFFE6BC60);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return videoList.size();
    }
}
