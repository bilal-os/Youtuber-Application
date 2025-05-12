package com.example.youtuberapplication.models;

public class VideoLinkModel {
    private String title;
    private String channelName;
    private String metaInfo;
    private String status; // "PENDING", "APPROVED", "NOT_APPROVED"

    public VideoLinkModel(String title, String channelName, String metaInfo, String status) {
        this.title = title;
        this.channelName = channelName;
        this.metaInfo = metaInfo;
        this.status = status;
    }

    public String getTitle() { return title; }
    public String getChannelName() { return channelName; }
    public String getMetaInfo() { return metaInfo; }
    public String getStatus() { return status; }
}
