package com.example.youtuberapplication.models;

public class SubscriberScreenshotModel {
    private String email;
    private int screenshotResId; // drawable resource ID
    private String status;

    public SubscriberScreenshotModel(String email, int screenshotResId, String status) {
        this.email = email;
        this.screenshotResId = screenshotResId;
        this.status = status;
    }

    public String getEmail() {
        return email;
    }

    public int getScreenshotResId() {
        return screenshotResId;
    }

    public String getStatus() {
        return status;
    }
}
