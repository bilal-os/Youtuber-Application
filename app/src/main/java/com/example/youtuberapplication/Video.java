    package com.example.youtuberapplication;

    import android.app.Application;
    import android.net.Uri;

    public class Video extends Application {
        String titleName, channelName, views, hours;
            Uri imageUri;
        // Constructor
        public Video(String titleName, String channelName, String views, String hours, Uri imageUri) {
            this.titleName = titleName;
            this.channelName = channelName;
            this.views = views;
            this.hours = hours;
            this.imageUri = imageUri;
        }

        // Getters
        public String getTitleName() {
            return titleName;
        }

        public String getChannelName() {
            return channelName;
        }

        public String getViews() {
            return views;
        }

        public String getHours() {
            return hours;
        }

        public Uri getImageUri() {
            return imageUri;
        }

        // Setters
        public void setTitleName(String titleName) {
            this.titleName = titleName;
        }

        public void setChannelName(String channelName) {
            this.channelName = channelName;
        }

        public void setViews(String views) {
            this.views = views;
        }

        public void setHours(String hours) {
            this.hours = hours;
        }

        public void setImageUri(Uri imageUri) {
            this.imageUri = imageUri;
        }

    }
