package com.example.user.fts;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ServerResponse {

    @SerializedName("response")
    @Expose
    private String response;
    @SerializedName("id")
    @Expose
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}
