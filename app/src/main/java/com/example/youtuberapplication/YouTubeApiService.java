package com.example.youtuberapplication;

import com.example.youtuberapplication.models.ChannelResponse;
import com.example.youtuberapplication.models.VideoDetailsResponse;
import com.example.youtuberapplication.models.YouTubeResponse;

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

    @GET("channels")
    Call<ChannelResponse> getChannelDetails(
            @Query("part") String part,
            @Query("id") String channelId,
            @Query("key") String apiKey
    );

    @GET("search")
    Call<ChannelResponse> searchChannel(
            @Query("part") String part,
            @Query("type") String type,
            @Query("q") String query,
            @Query("key") String apiKey
    );

}
