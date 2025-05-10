package com.example.youtuberapplication;

import android.app.Application;
import android.net.Uri;

import java.util.ArrayList;

public class VideosDataClass extends Application {
    public static ArrayList<Video> videos;

    @Override
    public void onCreate()
    {
        super.onCreate();
        videos = new ArrayList<>();

        // Add dummy videos
        for (int i = 0; i < 10; i++) {
            Video dummyVideo = new Video(
                    getString(R.string.videoTitle),
                    getString(R.string.channel_name),
                    "5M Views",
                    "2 Hours Ago",
                    Uri.parse("android.resource://" + getPackageName() + "/" + R.drawable.thumbnail)
            );
            videos.add(dummyVideo);
        }

    }

}
