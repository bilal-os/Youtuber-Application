package com.example.youtuberapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class PackagesActivity extends AppCompatActivity {

    private ImageView package1, package2, package3;
    private TextView channelStatus, userName;
    private Button nextButton;
    private ImageButton backButton;

    private FirebaseUser currentUser;
    private DatabaseReference usersRef;
    private String selectedPackage = null;
    private String youtubeChannelLink = null;
    private String youtubeVideoLink = null;
    private boolean isLinksComplete = false;

    // Activity result launcher for adding YouTube channel and video
    private final ActivityResultLauncher<Intent> addChannelLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    // Get the channel and video URLs from the result
                    youtubeChannelLink = result.getData().getStringExtra("channel_url");
                    youtubeVideoLink = result.getData().getStringExtra("video_url");

                    if (youtubeChannelLink != null && !youtubeChannelLink.isEmpty() &&
                            youtubeVideoLink != null && !youtubeVideoLink.isEmpty()) {
                        isLinksComplete = true;
                        updateChannelLinkStatus(true);
                    }
                }
            }
    );

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_packages);

        // === View Binding ===
        package1 = findViewById(R.id.package1);
        package2 = findViewById(R.id.package2);
        package3 = findViewById(R.id.package3);
        channelStatus = findViewById(R.id.channelLinkStatus);
        nextButton = findViewById(R.id.nextButton);
        backButton = findViewById(R.id.backButton);
        userName = findViewById(R.id.tvUsername);

        // Initialize Firebase
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser == null) {
            Toast.makeText(this, "User not authenticated", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Initialize Firebase Database Reference
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://youtubers-application-default-rtdb.firebaseio.com/");
        usersRef = database.getReference("users");

        // Set username
        userName.setText(currentUser.getDisplayName());

        // Set up package selection
        setupPackageSelection();

        // Set up next button
        setupNextButton();

        // Set up back button
        backButton.setOnClickListener(v -> onBackPressed());

        // Set initial channel status
        updateChannelLinkStatus(false);
    }

    private void setupPackageSelection() {
        // Remove any previous selections
        package1.setAlpha(0.5f);
        package2.setAlpha(0.5f);
        package3.setAlpha(0.5f);

        package1.setOnClickListener(v -> selectPackage(package1, "5k", "1K watch hour", "500 subs"));
        package2.setOnClickListener(v -> selectPackage(package2, "10k", "2.5K watch hour", "1000 subs"));
        package3.setOnClickListener(v -> selectPackage(package3, "15k", "4K watch hour", "2,500 subs"));
    }

    private void selectPackage(ImageView selectedPackageView, String price, String hours, String subs) {
        // Reset all package views
        package1.setAlpha(0.5f);
        package2.setAlpha(0.5f);
        package3.setAlpha(0.5f);

        // Highlight selected package
        selectedPackageView.setAlpha(1.0f);

        // Store selected package details
        selectedPackage = price;

        // Check next button status
        checkNextButtonStatus();
    }

    private void updateChannelLinkStatus(boolean isLinked) {
        if (isLinked) {
            channelStatus.setText("YOUR YOUTUBE LINKS ARE LINKED");
            channelStatus.setTextColor(Color.parseColor("#00C853")); // Green
            channelStatus.setBackgroundResource(R.drawable.linked_status_green);
            channelStatus.setOnClickListener(null);
        } else {
            channelStatus.setText("LINK YOUR YOUTUBE CHANNEL AND VIDEO");
            channelStatus.setTextColor(Color.parseColor("#D32F2F")); // Red
            channelStatus.setBackgroundResource(R.drawable.linked_status_red);
            channelStatus.setOnClickListener(v -> {
                // Start AddChannelActivity
                Intent intent = new Intent(PackagesActivity.this, AddChannelActivity.class);
                addChannelLauncher.launch(intent);
            });
        }

        // Check next button status after updating channel link status
        checkNextButtonStatus();
    }

    private void setupNextButton() {
        nextButton.setOnClickListener(v -> {
            if (canProceed()) {
                updateUserPackageAndLinks();
            } else {
                // Show error or prevent navigation
                Toast.makeText(this, "Please select a package and link your YouTube channel and video", Toast.LENGTH_SHORT).show();
            }
        });

        // Initially disable next button
        nextButton.setEnabled(false);
    }

    private void updateUserPackageAndLinks() {
        if (currentUser == null || selectedPackage == null ||
                youtubeChannelLink == null || youtubeVideoLink == null) {
            Toast.makeText(this, "Unable to proceed. Please try again.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Prepare update map
        Map<String, Object> updateMap = new HashMap<>();

        // Update YouTube channel link
        updateMap.put("youtubeChannelLink", youtubeChannelLink);

        // Prepare package details
        Map<String, Object> packageDetails = new HashMap<>();
        packageDetails.put("name", getPackageName(selectedPackage));
        packageDetails.put("views", selectedPackage);
        packageDetails.put("watchHours", getWatchHours(selectedPackage));
        packageDetails.put("subscribers", getSubscribers(selectedPackage));

        // Prepare video details
        Map<String, Object> videoDetails = new HashMap<>();
        videoDetails.put("url", youtubeVideoLink);
        videoDetails.put("title", "Placeholder Video");
        videoDetails.put("status", "pending");
        videoDetails.put("package", packageDetails);

        // Create a unique key for the video
        String videoKey = usersRef.child(currentUser.getUid()).child("videos").push().getKey();

        // Add video to the update map
        updateMap.put("videos/" + videoKey, videoDetails);

        // Update database
        usersRef.child(currentUser.getUid()).updateChildren(updateMap)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(PackagesActivity.this, "Profile updated successfully", Toast.LENGTH_SHORT).show();
                    // Navigate to next screen or finish
                    // Intent intent = new Intent(PackagesActivity.this, NextActivity.class);
                    // startActivity(intent);
                    finish();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(PackagesActivity.this, "Failed to update profile: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    private void checkNextButtonStatus() {
        nextButton.setEnabled(canProceed());
    }

    private boolean canProceed() {
        return selectedPackage != null && isLinksComplete;
    }

    // Helper methods to get package details
    private String getPackageName(String packageType) {
        switch (packageType) {
            case "5k": return "Basic";
            case "10k": return "Standard";
            case "15k": return "Premium";
            default: return "Unknown";
        }
    }

    private String getWatchHours(String packageType) {
        switch (packageType) {
            case "5k": return "1K watch hour";
            case "10k": return "2.5K watch hour";
            case "15k": return "4K watch hour";
            default: return "";
        }
    }

    private String getSubscribers(String packageType) {
        switch (packageType) {
            case "5k": return "500 subs";
            case "10k": return "1000 subs";
            case "15k": return "2,500 subs";
            default: return "";
        }
    }
}