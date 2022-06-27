package com.example.ballotblock.RestAPI;

public class AddWalletModel {
    String uuid;
    String walletAddress;

    public AddWalletModel(String uuid, String walletAddress) {
        this.uuid = uuid;
        this.walletAddress = walletAddress;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getWalletAddress() {
        return walletAddress;
    }

    public void setWalletAddress(String walletAddress) {
        this.walletAddress = walletAddress;
    }
}
