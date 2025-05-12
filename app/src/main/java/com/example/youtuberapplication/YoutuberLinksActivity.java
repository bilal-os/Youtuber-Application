package com.example.youtuberapplication;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.youtuberapplication.adapters.VideoLinkAdapter;
import com.example.youtuberapplication.models.VideoLinkModel;

import java.util.Arrays;
import java.util.List;

public class YoutuberLinksActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private VideoLinkAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtuber_links);

        recyclerView = findViewById(R.id.videoRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<VideoLinkModel> mockVideos = Arrays.asList(
                new VideoLinkModel("How to grow a YouTube Channel", "Zaki Qasim", "500K views • 2 hours ago", "APPROVED"),
                new VideoLinkModel("Top Shorts Tips", "Bilal Mahmud", "12K views • 4 days ago", "PENDING"),
                new VideoLinkModel("Why I'm NOT uploading daily", "DailyTuber", "3K views • 1 week ago", "NOT_APPROVED")
        );

        adapter = new VideoLinkAdapter(this, mockVideos);
        recyclerView.setAdapter(adapter);
    }
}
