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

public class PaymentDetailsActivity extends AppCompatActivity {

    // Views
    private ImageButton backButton;
    private ImageView youtubeIcon, horizontalLine;
    private TextView accountDetailsTitle, bankTitle, accountHolderName, accountNumber;
    private Button uploadScreenshotBtn, submitBtn;
    private BottomNavigationView bottomNavigation;

    // You can add a Uri variable here later for uploaded screenshot

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_details);

        // === View Bindings ===
        backButton = findViewById(R.id.backButton);
        youtubeIcon = findViewById(R.id.youtubeIcon);
        horizontalLine = findViewById(R.id.horizontalLine);
        accountDetailsTitle = findViewById(R.id.accountDetilsTitle);
        bankTitle = findViewById(R.id.bankTitle);
        accountHolderName = findViewById(R.id.accountHolderName);
        accountNumber = findViewById(R.id.accountNumber);
        uploadScreenshotBtn = findViewById(R.id.uploadScreenshotBtn);
        submitBtn = findViewById(R.id.submitBtn);
        bottomNavigation = findViewById(R.id.bottomNavigation);

        // === Back Button Click ===
        backButton.setOnClickListener(v -> finish());

        // === Upload Screenshot Button Click ===
        uploadScreenshotBtn.setOnClickListener(v -> {
            // TODO: Open image picker / file chooser for screenshot
            Toast.makeText(this, "Upload screenshot functionality not implemented yet.", Toast.LENGTH_SHORT).show();
        });

        // === Submit Button Click ===
        submitBtn.setOnClickListener(v -> {
            // TODO: Validate image uploaded & submit form
            Toast.makeText(this, "Submit button clicked.", Toast.LENGTH_SHORT).show();
        });
/*
        // === Bottom Navigation Setup (if needed) ===
        bottomNavigation.setOnNavigationItemSelectedListener(item -> {
            // TODO: Handle navigation actions based on item.getItemId()
            switch (item.getItemId()) {
                case R.id.nav_home:
                    startActivity(new Intent(this, HomeActivity.class));
                    return true;
                case R.id.nav_profile:
                    startActivity(new Intent(this, ProfileActivity.class));
                    return true;
                // Add more cases as per your bottom_nav_menu
                default:
                    return false;
            }
        });*/
    }
}
