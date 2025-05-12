package com.example.youtuberapplication.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.youtuberapplication.R;
import com.example.youtuberapplication.models.SubscriberScreenshotModel;

import java.util.ArrayList;

public class SubscriberScreenshotAdapter extends RecyclerView.Adapter<SubscriberScreenshotAdapter.ViewHolder> {

    private Context context;
    private ArrayList<SubscriberScreenshotModel> list;

    public SubscriberScreenshotAdapter(Context context, ArrayList<SubscriberScreenshotModel> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_subscriber_screenshots, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        SubscriberScreenshotModel model = list.get(position);

        holder.emailText.setText(model.getEmail());
        holder.screenshotImage.setImageResource(model.getScreenshotResId());
        holder.statusBadge.setText(model.getStatus());

        // Optional: Change badge color based on status
        if (model.getStatus().equalsIgnoreCase("NEW")) {
            holder.statusBadgeCard.setCardBackgroundColor(0xFFFFCC00); // Yellow
        } else {
            holder.statusBadgeCard.setCardBackgroundColor(0xFF34C759); // Green
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView emailText, statusBadge;
        ImageView screenshotImage;
        CardView statusBadgeCard;

        public ViewHolder(View itemView) {
            super(itemView);
            emailText = itemView.findViewById(R.id.subscriberEmailTextView);
            screenshotImage = itemView.findViewById(R.id.subscriberScreenshotImage);
            statusBadge = itemView.findViewById(R.id.subscriberStatusBadge);
            statusBadgeCard = itemView.findViewById(R.id.subscriberStatusBadge).getParent() instanceof CardView
                    ? (CardView) itemView.findViewById(R.id.subscriberStatusBadge).getParent()
                    : null;
        }
    }
}
