package com.example.youtuberapplication;

import android.annotation.SuppressLint;
import android.app.Application;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.youtuberapplication.Models.VideoDetailsResponse;
import com.example.youtuberapplication.Models.YouTubeResponse;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class VideosDataClass extends Application {
    private static final String TAG = "VideosDataClass";
    private static final String BASE_URL = "https://www.googleapis.com/youtube/v3/";
    private static final String API_KEY = "AIzaSyCzkceKxCH3qJJ-dWicRSKxwL-znOvPQ7w";
    public static ArrayList<Video> videos;

    public interface OnVideosLoaded {
        void onLoaded(boolean status);
        void onError(Throwable t);
    }

    private OnVideosLoaded listener;

    public void setOnVideosLoadedListener(OnVideosLoaded listener) {
        this.listener = listener;
    }
    @Override
    public void onCreate()
    {
        super.onCreate();
        videos = new ArrayList<>();

       fetchVideos();

    }

    private void fetchVideos() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        YouTubeApiService apiService = retrofit.create(YouTubeApiService.class);
        Call<YouTubeResponse> call = apiService.getVideos("snippet", "video", 20, API_KEY);

        call.enqueue(new Callback<YouTubeResponse>() {
            @Override
            public void onResponse(@NonNull Call<YouTubeResponse> call, @NonNull Response<YouTubeResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<YouTubeResponse.Item> searchResults = response.body().getItems();

                    // collect IDs in one go
                    List<String> ids = new ArrayList<>();
                    for (YouTubeResponse.Item it : searchResults) {
                        ids.add(it.getId().getVideoId());
                    }
                    String videoIds = TextUtils.join(",", ids);


                    // Make a second API call to get video details
                    Call<VideoDetailsResponse> detailsCall = apiService.getVideoDetails(
                            "snippet,statistics,contentDetails",
                            videoIds,
                            API_KEY);

                    detailsCall.enqueue(new Callback<VideoDetailsResponse>() {
                        @Override
                        public void onResponse(@NonNull Call<VideoDetailsResponse> call, @NonNull Response<VideoDetailsResponse> detailsResponse) {
                            if (detailsResponse.isSuccessful() && detailsResponse.body() != null) {
                                videos.clear();

                                for (VideoDetailsResponse.VideoItem vi : detailsResponse.body().getItems()) {
                                    // snippet (title, channel, thumbnails)
                                    String videoId = vi.getVideoId();
                                    String title       = vi.getSnippet().getTitle();
                                    String channel     = vi.getSnippet().getChannelTitle();
                                    Uri    thumbUri    = Uri.parse(
                                            vi.getSnippet()
                                                    .getThumbnails()
                                                    .getDefault()
                                                    .getUrl()
                                    );

                                    // stats
                                    String viewCount   = vi.getStatistics().getViewCount();
                                    String formattedViews = formatViewCount(viewCount);

                                    // contentDetails (duration, publishedAt)

                                    String publishedAt = vi.getSnippet().getPublishedAt();
                                    String timePassed  = getTimePassed(publishedAt);

                                    videos.add(new Video(
                                            title,
                                            channel,
                                            formattedViews,
                                            timePassed,
                                            thumbUri,
                                            videoId
                                    ));
                                }
                            }
                            if (listener != null) listener.onLoaded(true);
                        }

                        @Override
                        public void onFailure(@NonNull Call<VideoDetailsResponse> call, @NonNull Throwable t) {
                            Log.e(TAG, "Error fetching video details: " + t.getMessage());
                        }
                    });
                } else {
                    Log.e(TAG, "Failed to fetch videos");
                }
            }
            @Override
            public void onFailure(@NonNull Call<YouTubeResponse> call, @NonNull Throwable t) {
                System.out.println(TAG + "Error: " + t.getMessage());
            }
        });


    }

    @SuppressLint("DefaultLocale")
    private String formatViewCount(String viewCount) {
        try {
            long views = Long.parseLong(viewCount);
            if (views >= 1_000_000_000) {
                return String.format("%.1fB views", views / 1_000_000_000.0);
            } else if (views >= 1_000_000) {
                return String.format("%.1fM views", views / 1_000_000.0);
            } else if (views >= 1_000) {
                return String.format("%.1fK views", views / 1_000.0);
            } else {
                return views + " views";
            }
        } catch (NumberFormatException e) {
            return "0 views";
        }
    }

    private String getTimePassed(String publishedAt) {

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US);
            sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date publishDate = sdf.parse(publishedAt);
            Date now = new Date();

            long diffInMillis = now.getTime() - publishDate.getTime();
            long diffInSeconds = diffInMillis / 1000;

            if (diffInSeconds < 60) {
                return diffInSeconds + " seconds ago";
            } else if (diffInSeconds < 3600) {
                long minutes = diffInSeconds / 60;
                return minutes + (minutes == 1 ? " minute ago" : " minutes ago");
            } else if (diffInSeconds < 86400) {
                long hours = diffInSeconds / 3600;
                return hours + (hours == 1 ? " hour ago" : " hours ago");
            } else if (diffInSeconds < 604800) {
                long days = diffInSeconds / 86400;
                return days + (days == 1 ? " day ago" : " days ago");
            } else if (diffInSeconds < 2592000) {
                long weeks = diffInSeconds / 604800;
                return weeks + (weeks == 1 ? " week ago" : " weeks ago");
            } else if (diffInSeconds < 31536000) {
                long months = diffInSeconds / 2592000;
                return months + (months == 1 ? " month ago" : " months ago");
            } else {
                long years = diffInSeconds / 31536000;
                return years + (years == 1 ? " year ago" : " years ago");
            }
        } catch (Exception e) {
            return "Unknown time";
        }
    }
}
