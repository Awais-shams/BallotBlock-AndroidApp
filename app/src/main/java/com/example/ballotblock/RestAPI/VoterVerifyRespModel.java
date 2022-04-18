package com.example.ballotblock.RestAPI;

public class VoterVerifyRespModel {
    String message;
    boolean registered;
    String status;

    public VoterVerifyRespModel(String message, boolean registered, String status) {
        this.message = message;
        this.registered = registered;
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isRegistered() {
        return registered;
    }

    public void setRegistered(boolean registered) {
        this.registered = registered;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
