package com.example.youtuberapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.DrawableRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

public class AdminPaymentScreenshotsActivity extends AppCompatActivity {

    private LinearLayout checkboxNewContainer, checkboxViewedContainer;
    private ImageView checkboxNewIcon, checkboxViewedIcon;
    private boolean isNewSelected = true;
    private RecyclerView paymentRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_payment_screenshots);

        // Back button
        ImageButton backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> finish());

        // Init views
        checkboxNewContainer = findViewById(R.id.checkbox_new_container);
        checkboxViewedContainer = findViewById(R.id.checkbox_viewed_container);
        checkboxNewIcon = findViewById(R.id.checkbox_new_icon);
        checkboxViewedIcon = findViewById(R.id.checkbox_viewed_icon);
        paymentRecyclerView = findViewById(R.id.paymentRecyclerView);

        // Set initial state
        updateFilterUI(true);

        // Toggle NEW
        checkboxNewContainer.setOnClickListener(v -> {
            if (!isNewSelected) {
                isNewSelected = true;
                updateFilterUI(true);
                loadNewPayments(); // Implement this
            }
        });

        // Toggle VIEWED
        checkboxViewedContainer.setOnClickListener(v -> {
            if (isNewSelected) {
                isNewSelected = false;
                updateFilterUI(false);
                loadViewedPayments(); // Implement this
            }
        });

        // TODO: Setup RecyclerView adapter and data source
    }

    // Visually update the icons based on which is selected
    private void updateFilterUI(boolean newSelected) {
        checkboxNewIcon.setImageResource(newSelected ? R.drawable.ic_checkbox_checked : R.drawable.ic_checkbox_unchecked);
        checkboxViewedIcon.setImageResource(!newSelected ? R.drawable.ic_checkbox_checked : R.drawable.ic_checkbox_unchecked);
    }

    // Placeholder: Load only NEW payments into RecyclerView
    private void loadNewPayments() {
        // TODO: Filter and load NEW transactions (e.g., from Firebase or local data)
    }

    // Placeholder: Load only VIEWED payments into RecyclerView
    private void loadViewedPayments() {
        // TODO: Filter and load VIEWED transactions
    }
}
