package com.example.ballotblock.RestAPI;

public class VoterVerificationMode {
    String electionId, voterId, email;

    public VoterVerificationMode(String electionId, String voterId, String email) {
        this.electionId = electionId;
        this.voterId = voterId;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
