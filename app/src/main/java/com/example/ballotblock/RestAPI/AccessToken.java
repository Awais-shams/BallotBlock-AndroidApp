package com.example.ballotblock.RestAPI;

public class AccessToken {
    private String message;
    private String accessToken;
    private String uuid;

    public AccessToken(String message, String accessToken, String uuid) {
        this.message = message;
        this.accessToken = accessToken;
        this.uuid = uuid;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
