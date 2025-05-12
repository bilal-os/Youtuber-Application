package com.example.youtuberapplication;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.youtuberapplication.adapters.SubscriberScreenshotAdapter;
import com.example.youtuberapplication.models.SubscriberScreenshotModel;

import java.util.ArrayList;

public class AdminSubscriberScreenshotsActivity extends AppCompatActivity {

    private RecyclerView subscriberRecyclerView;
    private SubscriberScreenshotAdapter adapter;
    private ArrayList<SubscriberScreenshotModel> screenshotList;

    private LinearLayout checkboxNewContainer, checkboxViewedContainer;
    private ImageView checkboxNewIcon, checkboxViewedIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_subscriber_screenshots);

        // === View Binding ===
        subscriberRecyclerView = findViewById(R.id.subscriberRecyclerView);
        checkboxNewContainer = findViewById(R.id.checkbox_new_container);
        checkboxViewedContainer = findViewById(R.id.checkbox_viewed_container);
        checkboxNewIcon = findViewById(R.id.checkbox_new_icon);
        checkboxViewedIcon = findViewById(R.id.checkbox_viewed_icon);

        // === RecyclerView Setup ===
        subscriberRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        screenshotList = new ArrayList<>();

        // === Dummy Data for Now ===
        screenshotList.add(new SubscriberScreenshotModel("user1@example.com", R.drawable.thumbnail_screenshot_subscriber, "NEW"));
        screenshotList.add(new SubscriberScreenshotModel("user2@example.com", R.drawable.thumbnail_screenshot_subscriber, "VIEWED"));
        screenshotList.add(new SubscriberScreenshotModel("user3@example.com", R.drawable.thumbnail_screenshot_subscriber, "NEW"));

        adapter = new SubscriberScreenshotAdapter(this, screenshotList);
        subscriberRecyclerView.setAdapter(adapter);

        // === Checkbox Filters (not implemented yet) ===
        checkboxNewContainer.setOnClickListener(v -> {
            checkboxNewIcon.setImageResource(R.drawable.ic_checkbox_checked);
            checkboxViewedIcon.setImageResource(R.drawable.ic_checkbox_unchecked);

            // TODO: filter screenshotList to only show "NEW"
        });

        checkboxViewedContainer.setOnClickListener(v -> {
            checkboxViewedIcon.setImageResource(R.drawable.ic_checkbox_checked);
            checkboxNewIcon.setImageResource(R.drawable.ic_checkbox_unchecked);

            // TODO: filter screenshotList to only show "VIEWED"
        });

        // TODO: Implement actual data loading from backend (e.g., Firebase, API, etc.)
    }
}
