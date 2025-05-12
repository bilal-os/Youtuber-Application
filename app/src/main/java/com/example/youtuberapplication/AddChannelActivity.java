package com.example.youtuberapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.youtuberapplication.models.ChannelResponse;
import com.example.youtuberapplication.models.VideoDetailsResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddChannelActivity extends AppCompatActivity {
    private static final String TAG      = "AddChannelActivity";
    private static final String BASE_URL = "https://www.googleapis.com/youtube/v3/";
    private static final String API_KEY  = "YOUR_API_KEY_HERE";

    private EditText channelLinkInput, videoLinkInput;
    private Button   submitButton, cancelButton;
    private ImageButton backButton;
    private YouTubeApiService api;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle s) {
        super.onCreate(s);
        setContentView(R.layout.activity_add_channel);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api = retrofit.create(YouTubeApiService.class);

        channelLinkInput = findViewById(R.id.channelLinkInput);
        videoLinkInput   = findViewById(R.id.videoLinkInput);
        submitButton     = findViewById(R.id.submitChannelLink);
        backButton       = findViewById(R.id.backButton);
        cancelButton     = findViewById(R.id.cancelButton);

        backButton.setOnClickListener(v -> finish());
        cancelButton.setOnClickListener(v -> {
            setResult(RESULT_CANCELED);
            finish();
        });
        submitButton.setOnClickListener(v -> onSubmit());
    }

    private void onSubmit() {
        String channelUrl = channelLinkInput.getText().toString().trim();
        String videoUrl   = videoLinkInput.getText().toString().trim();
        Log.d(TAG, "onSubmit: raw channelUrl=" + channelUrl);
        Log.d(TAG, "onSubmit: raw videoUrl="   + videoUrl);

        if (channelUrl.isEmpty() || videoUrl.isEmpty()) {
            toast("Please enter both channel and video URLs");
            return;
        }

        String channelKey = parseChannelKey(channelUrl);
        String videoId    = parseVideoId(videoUrl);
        Log.d(TAG, "onSubmit: parsed channelKey=" + channelKey);
        Log.d(TAG, "onSubmit: parsed videoId="    + videoId);

        if (channelKey == null) {
            toast("Invalid channel link");
            return;
        }
        if (videoId == null) {
            toast("Invalid video link");
            return;
        }

        api.getVideoDetails("snippet", videoId, API_KEY)
                .enqueue(new Callback<VideoDetailsResponse>() {
                    @Override
                    public void onResponse(Call<VideoDetailsResponse> call, Response<VideoDetailsResponse> resp) {
                        Log.d(TAG, "videoDetails.onResponse: code=" + resp.code());
                        if (resp.isSuccessful() && resp.body()!=null && !resp.body().getItems().isEmpty()) {
                            resolveChannel(channelKey, videoId);
                        } else {
                            Log.w(TAG, "Video not found or empty response");
                            toast("Video not found");
                        }
                    }
                    @Override
                    public void onFailure(Call<VideoDetailsResponse> call, Throwable t) {
                        Log.e(TAG, "videoDetails onFailure", t);
                        toast("Network error");
                    }
                });
    }

    private void resolveChannel(String channelKey, String videoId) {
        Log.d(TAG, "resolveChannel: channelKey=" + channelKey);
        if (!channelKey.startsWith("UC")) {
            api.searchChannel("snippet", "channel", channelKey, API_KEY)
                    .enqueue(new Callback<ChannelResponse>() {
                        @Override
                        public void onResponse(Call<ChannelResponse> call, Response<ChannelResponse> resp) {
                            Log.d(TAG, "searchChannel.onResponse: code=" + resp.code());
                            if (resp.isSuccessful() && resp.body()!=null) {
                                List<ChannelResponse.ChannelItem> items = resp.body().getItems();
                                Log.d(TAG, "searchChannel: items.size=" + items.size());
                                if (!items.isEmpty()) {
                                    validateChannel(items.get(0).getId(), videoId);
                                } else {
                                    toast("Channel not found");
                                }
                            } else {
                                Log.w(TAG, "searchChannel unsuccessful or empty body");
                                toast("Channel not found");
                            }
                        }
                        @Override
                        public void onFailure(Call<ChannelResponse> call, Throwable t) {
                            Log.e(TAG, "searchChannel onFailure", t);
                            toast("Network error");
                        }
                    });
        } else {
            validateChannel(channelKey, videoId);
        }
    }

    private void validateChannel(String channelId, String videoId) {
        Log.d(TAG, "validateChannel: channelId=" + channelId);
        api.getChannelDetails("snippet", channelId, API_KEY)
                .enqueue(new Callback<ChannelResponse>() {
                    @Override
                    public void onResponse(Call<ChannelResponse> call, Response<ChannelResponse> resp) {
                        Log.d(TAG, "getChannelDetails.onResponse: code=" + resp.code());
                        if (resp.isSuccessful() && resp.body()!=null && !resp.body().getItems().isEmpty()) {
                            Intent result = new Intent();
                            result.putExtra("channel_url", "https://www.youtube.com/channel/" + channelId);
                            result.putExtra("video_url",   "https://www.youtube.com/watch?v=" + videoId);
                            setResult(RESULT_OK, result);
                            toast("Links validated successfully");
                            finish();
                        } else {
                            Log.w(TAG, "Channel not found or empty response");
                            toast("Channel not found");
                        }
                    }
                    @Override
                    public void onFailure(Call<ChannelResponse> call, Throwable t) {
                        Log.e(TAG, "getChannelDetails onFailure", t);
                        toast("Network error");
                    }
                });
    }

    private String parseChannelKey(String url) {
        Uri u = Uri.parse(url);
        List<String> seg = u.getPathSegments();
        Log.d(TAG, "parseChannelKey: pathSegments=" + seg);
        if (seg.size()>=2 && "channel".equals(seg.get(0))) {
            return seg.get(1);
        }
        if (seg.size()>=1 && seg.get(0).startsWith("@")) {
            return seg.get(0).substring(1);
        }
        if (seg.size()>=2 && ("c".equals(seg.get(0)) || "user".equals(seg.get(0)))) {
            return seg.get(1);
        }
        return null;
    }

    private String parseVideoId(String url) {
        Uri u = Uri.parse(url);
        String v = u.getQueryParameter("v");
        Log.d(TAG, "parseVideoId: vParam=" + v);
        if (v!=null && !v.isEmpty()) return v;
        List<String> seg = u.getPathSegments();
        Log.d(TAG, "parseVideoId: pathSegments=" + seg);
        if (!seg.isEmpty()) return seg.get(seg.size()-1);
        return null;
    }

    private void toast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        Log.d(TAG, "toast: " + msg);
    }
}
