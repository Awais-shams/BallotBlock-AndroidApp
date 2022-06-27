package com.example.ballotblock.RestAPI;

public class VoteCreateRespModel {
    String uuid;
    int id;
    String voterAddress;
    String candidateAddress;
    String txHash;
    int ElectionId;
    String updatedAt;
    String createdAt;

    public VoteCreateRespModel(String uuid, int id, String voterAddress, String candidateAddress, String txHash, int electionId, String updatedAt, String createdAt) {
        this.uuid = uuid;
        this.id = id;
        this.voterAddress = voterAddress;
        this.candidateAddress = candidateAddress;
        this.txHash = txHash;
        ElectionId = electionId;
        this.updatedAt = updatedAt;
        this.createdAt = createdAt;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public float getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVoterAddress() {
        return voterAddress;
    }

    public void setVoterAddress(String voterAddress) {
        this.voterAddress = voterAddress;
    }

    public String getCandidateAddress() {
        return candidateAddress;
    }

    public void setCandidateAddress(String candidateAddress) {
        this.candidateAddress = candidateAddress;
    }

    public String getTxHash() {
        return txHash;
    }

    public void setTxHash(String txHash) {
        this.txHash = txHash;
    }

    public float getElectionId() {
        return ElectionId;
    }

    public void setElectionId(int electionId) {
        ElectionId = electionId;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
