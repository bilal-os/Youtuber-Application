package com.example.youtuberapplication;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class AddChannelActivity extends AppCompatActivity {

    private EditText channelLinkInput;
    private Button submitChannelLink;
    private ImageButton backButton;
    private ImageView profileIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_channel);

        channelLinkInput = findViewById(R.id.channelLinkInput);
        submitChannelLink = findViewById(R.id.submitChannelLink);
        backButton = findViewById(R.id.backButton);
        profileIcon = findViewById(R.id.profileIcon);

        submitChannelLink.setOnClickListener(v -> {
            String link = channelLinkInput.getText().toString().trim();
            if (!link.isEmpty()) {
                Toast.makeText(this, "Submitted: " + link, Toast.LENGTH_SHORT).show();
                // TODO: Handle link submission logic later
            } else {
                Toast.makeText(this, "Please enter a valid link", Toast.LENGTH_SHORT).show();
            }
        });

        backButton.setOnClickListener(v -> finish());

        profileIcon.setOnClickListener(v -> {
            // TODO: Navigate to profile activity (not yet implemented)
            Toast.makeText(this, "Profile clicked", Toast.LENGTH_SHORT).show();
        });

        /*BottomNavigationView bottomNav = findViewById(R.id.bottomNavigationView);*/

        // Commented out navigation logic until destination screens are created
        /*
        bottomNav.setOnNavigationItemSelectedListener(item -> {
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
        });
        */
    }
}
