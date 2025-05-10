package com.example.youtuberapplication;

import java.util.List;
import com.example.youtuberapplication.YouTubeResponse.Snippet;
public class VideoDetailsResponse {
    private List<VideoItem> items;

    public List<VideoItem> getItems() {
        return items;
    }

    public static class VideoItem {
        private String id;
        private Snippet snippet;
        private Statistics statistics;

        public String getVideoId()
        {
            return id;
        }

        public Snippet getSnippet() {
            return snippet;
        }


        public Statistics getStatistics() {
            return statistics;
        }


    }
    public static class Statistics {
        private String viewCount;


        public String getViewCount() {
            return viewCount;
        }

    }



}