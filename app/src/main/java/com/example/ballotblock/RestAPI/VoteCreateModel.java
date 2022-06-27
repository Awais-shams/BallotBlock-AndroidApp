package com.example.ballotblock.RestAPI;

public class VoteCreateModel {
    String voterAddress;
    String candidateAddress;
    String txhash;
    String electionId;

    public VoteCreateModel(String voterAddress, String candidateAddress, String txhash, String electionId) {
        this.voterAddress = voterAddress;
        this.candidateAddress = candidateAddress;
        this.txhash = txhash;
        this.electionId = electionId;
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

    public String getTxhash() {
        return txhash;
    }

    public void setTxhash(String txhash) {
        this.txhash = txhash;
    }

    public String getElectionId() {
        return electionId;
    }

    public void setElectionId(String electionId) {
        this.electionId = electionId;
    }
}
