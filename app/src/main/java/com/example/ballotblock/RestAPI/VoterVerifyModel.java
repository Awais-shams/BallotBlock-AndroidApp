package com.example.ballotblock.RestAPI;

public class VoterVerifyModel {
    String electionId, voterId, givenId;

    public VoterVerifyModel(String electionId, String voterId, String givenId) {
        this.electionId = electionId;
        this.voterId = voterId;
        this.givenId = givenId;
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

    public String getGivenId() {
        return givenId;
    }

    public void setGivenId(String givenId) {
        this.givenId = givenId;
    }
}
