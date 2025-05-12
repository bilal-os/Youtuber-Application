package com.example.youtuberapplication;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class PackagesActivity extends AppCompatActivity {

    private ImageView package1, package2, package3;
    private TextView channelStatus;
    private Button nextButton;
    private ImageButton backButton;

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

        // === Handle package clicks (can open detail screen if needed) ===
        package1.setOnClickListener(v -> onPackageSelected("5k", "1K watch hour", "500 subs"));
        package2.setOnClickListener(v -> onPackageSelected("10k", "2.5K watch hour", "1000 subs"));
        package3.setOnClickListener(v -> onPackageSelected("15k", "4K watch hour", "2,500 subs"));

        // === Back Button functionality ===
        backButton.setOnClickListener(v -> onBackPressed());

        // === Next Button click logic ===
        /*nextButton.setOnClickListener(v -> {
            // TODO: Navigate to the next step/screen
            Intent intent = new Intent(PackagesActivity.this, NextStepActivity.class);
            startActivity(intent);
        });
*/
        // === Channel Link Status Logic ===
        boolean isChannelLinked = true; // TODO: Replace with real check
        updateChannelLinkStatus(isChannelLinked);
    }

    private void onPackageSelected(String price, String hours, String subs) {
        /*// TODO: Navigate to package details if needed
        Intent intent = new Intent(PackagesActivity.this, PackageDetailsActivity.class);
        intent.putExtra("price", price);
        intent.putExtra("hours", hours);
        intent.putExtra("subs", subs);
        startActivity(intent);*/
    }

    private void updateChannelLinkStatus(boolean isLinked) {
        if (isLinked) {
            channelStatus.setText("YOUR YOUTUBE CHANNEL IS LINKED");
            channelStatus.setTextColor(Color.parseColor("#00C853")); // Green
            channelStatus.setBackgroundResource(R.drawable.linked_status_green);
        } else {
            channelStatus.setText("YOUR YOUTUBE CHANNEL IS NOT LINKED");
            channelStatus.setTextColor(Color.parseColor("#D32F2F")); // Red
            channelStatus.setBackgroundResource(R.drawable.linked_status_red);
        }
    }
}
