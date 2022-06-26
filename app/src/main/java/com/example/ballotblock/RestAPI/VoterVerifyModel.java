package com.example.ballotblock.RestAPI;

public class VoterVerifyModel {
    String electionId, voterId, VoterAddress, email;

    public VoterVerifyModel(String electionId, String voterId, String voterAddress, String email) {
        this.electionId = electionId;
        this.voterId = voterId;
        this.VoterAddress = voterAddress;
        this.email = email;
    }

    public String getElectionId() {
        return electionId;
    }

    public void setElectionId(String electionId) {
        this.electionId = electionId;
    }

    public String getVoterId() {
        return voterId;
    }

    public void setVoterId(String voterId) {
        this.voterId = voterId;
    }

    public String getVoterAddress() {
        return VoterAddress;
    }

    public void setVoterAddress(String voterAddress) {
        this.VoterAddress = voterAddress;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
