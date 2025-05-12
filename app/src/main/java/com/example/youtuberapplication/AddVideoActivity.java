package com.example.youtuberapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class AddVideoActivity extends AppCompatActivity {

    private ImageButton backButton;
    private ImageView profileIcon;
    private EditText channelLinkInput;
    private Button submitChannelLink, moreButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_video);

        // === Bind Views ===
        backButton = findViewById(R.id.backButton);
        profileIcon = findViewById(R.id.profileIcon);
        channelLinkInput = findViewById(R.id.channelLinkInput);
        submitChannelLink = findViewById(R.id.submitChannelLink);
        moreButton = findViewById(R.id.moreButton);

        // === Back Button Click ===
        backButton.setOnClickListener(v -> finish());

        // === Profile Icon Click ===
        profileIcon.setOnClickListener(v ->
                Toast.makeText(this, "Profile clicked", Toast.LENGTH_SHORT).show()
        );

        // === Submit Button Click ===
        submitChannelLink.setOnClickListener(v -> {
            String link = channelLinkInput.getText().toString().trim();
            if (!link.isEmpty()) {
                Toast.makeText(this, "Video link submitted: " + link, Toast.LENGTH_SHORT).show();
                // TODO: Handle submission (e.g., save or upload)
            } else {
                Toast.makeText(this, "Please enter a video link", Toast.LENGTH_SHORT).show();
            }
        });

        // === More Button Click ===
        moreButton.setOnClickListener(v ->
                Toast.makeText(this, "More clicked", Toast.LENGTH_SHORT).show()
        );

        /*// === Bottom Navigation ===
        BottomNavigationView bottomNav = findViewById(R.id.bottomNavigation);
        bottomNav.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.nav_home:
                    startActivity(new Intent(this, HomeActivity.class));
                    return true;
                case R.id.nav_videos:
                    startActivity(new Intent(this, VideoListActivity.class));
                    return true;
                case R.id.nav_money:
                    startActivity(new Intent(this, EarningsActivity.class));
                    return true;
                case R.id.nav_more:
                    startActivity(new Intent(this, MoreOptionsActivity.class));
                    return true;
            }
            return false;
        });*/
    }
}
