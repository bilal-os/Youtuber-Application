package com.example.youtuberapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class WatcherUploadSubscriptionScreenshotActivity extends AppCompatActivity {

    private ImageButton backButton;
    private ImageView profileIcon, youtubeBanner, horizontalLine;
    private TextView uploadScreenshotText, watcherName;
    private Button uploadScreenshotBtn, submitBtn;
    private BottomNavigationView bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watcher_upload_subscription_screenshot);

        // === View Binding ===
        backButton = findViewById(R.id.backButton);
        profileIcon = findViewById(R.id.profileIcon);
        youtubeBanner = findViewById(R.id.youtubeIcon);
        horizontalLine = findViewById(R.id.horizontalLine);
        uploadScreenshotText = findViewById(R.id.uploadScreenshotText);
        watcherName = findViewById(R.id.watcherName);
        uploadScreenshotBtn = findViewById(R.id.uploadScreenshotBtn);
        submitBtn = findViewById(R.id.submitBtn);
        bottomNavigation = findViewById(R.id.bottomNavigation);

        // === Back Button Navigation ===
        backButton.setOnClickListener(v -> finish());

        // === Upload Button Click Handler ===
        uploadScreenshotBtn.setOnClickListener(v -> {
            // TODO: Implement image picker or screenshot file chooser
            Toast.makeText(this, "Upload screenshot clicked", Toast.LENGTH_SHORT).show();
        });

        // === Submit Button Click Handler ===
        submitBtn.setOnClickListener(v -> {
            // TODO: Validate and submit uploaded screenshot to backend/server
            Toast.makeText(this, "Submit clicked", Toast.LENGTH_SHORT).show();
        });
/*
        // === Bottom Navigation Setup ===
        bottomNavigation.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.nav_home:
                    // TODO: Navigate to home screen
                    return true;
                case R.id.nav_videos:
                    // TODO: Navigate to videos screen
                    return true;
                case R.id.nav_money:
                    // TODO: Navigate to earnings screen
                    return true;
                case R.id.nav_profile:
                    // TODO: Navigate to profile screen
                    return true;
            }
            return false;
        });*/
    }
}
