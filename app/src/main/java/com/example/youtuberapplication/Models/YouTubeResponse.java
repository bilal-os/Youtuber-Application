package com.example.youtuberapplication.Models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class YouTubeResponse {

    private List<Item> items;

    public List<Item> getItems() {
        return items;
    }

    public static class Item {
        private String kind;
        private Id id;
        private Snippet snippet;

        public Id getId() {
            return id;
        }

        public Snippet getSnippet() {
            return snippet;
        }

        public static class Id {
            private String kind;
            private String videoId;

            public String getVideoId() {
                return videoId;
            }
        }
    }

    public static class Snippet {
        private String title;
        private String channelTitle;
        private Thumbnails thumbnails;
        private String publishedAt;

        public String getTitle() {
            return title;
        }

        public String getPublishedAt()
        {
            return publishedAt;
        }

        public String getChannelTitle() {
            return channelTitle;
        }

        public Thumbnails getThumbnails() {
            return thumbnails;
        }
    }

        public static class Thumbnails {
            // The 'default' key represents the default thumbnail for the video
            @SerializedName("high")
            private ThumbnailDetail defaultThumbnail;


            // Use 'default' as the field name to match the response structure
            public ThumbnailDetail getDefault() {
                return defaultThumbnail;
            }


            public static class ThumbnailDetail {
                private String url;  // URL of the thumbnail image
                private int width;   // Width of the thumbnail
                private int height;  // Height of the thumbnail

                public String getUrl() {
                    return url;
                }

                public int getWidth() {
                    return width;
                }

                public int getHeight() {
                    return height;
                }
            }
        }
}

