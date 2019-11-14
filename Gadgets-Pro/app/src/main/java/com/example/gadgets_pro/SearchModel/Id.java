package com.myapp.ytvideos.SearchModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Id
{
    @SerializedName("kind")
    @Expose
    private String kind;

    @SerializedName("videoId")
    @Expose
    private String videoId;

    public String getKind() {
        return kind;
    }

    public String getVideoId() {
        return videoId;
    }
}
