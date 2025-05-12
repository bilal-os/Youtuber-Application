package com.example.youtuberapplication.models;

import com.google.gson.annotations.SerializedName;
import java.util.Collections;
import java.util.List;

public class ChannelResponse {
    @SerializedName("items")
    private List<ChannelItem> items;

    public List<ChannelItem> getItems() {
        return items != null ? items : Collections.emptyList();
    }

    public static class ChannelItem {
        @SerializedName("id")
        private String id;

        @SerializedName("snippet")
        private Snippet snippet;

        public String getId() {
            return id;
        }

        public Snippet getSnippet() {
            return snippet;
        }
    }

    public static class Snippet {
        @SerializedName("title")
        private String title;

        @SerializedName("description")
        private String description;

        public String getTitle() {
            return title;
        }

        public String getDescription() {
            return description;
        }
    }
}