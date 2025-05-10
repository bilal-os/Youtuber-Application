package com.example.youtuberapplication;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface YouTubeApiService {
    @GET("search")
    Call<YouTubeResponse> getVideos(
            @Query("part") String part,
            @Query("type") String type,
            @Query("maxResults") int maxResults,
            @Query("key") String apiKey
    );

    @GET("videos")
    Call<VideoDetailsResponse> getVideoDetails(
            @Query("part") String part,
            @Query("id") String videoIds,
            @Query("key") String apiKey
    );
}
