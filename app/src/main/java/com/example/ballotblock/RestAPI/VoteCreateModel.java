package com.example.ballotblock.RestAPI;

public class VoteCreateModel {
    String voterAddress;
    String candidateAddress;
    String txHash;
    String electionId;

    public VoteCreateModel(String voterAddress, String candidateAddress, String txHash, String electionId) {
        this.voterAddress = voterAddress;
        this.candidateAddress = candidateAddress;
        this.txHash = txHash;
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

    public String getTxHash() {
        return txHash;
    }

    public void setTxHash(String txHash) {
        this.txHash = txHash;
    }

    public String getElectionId() {
        return electionId;
    }

    public void setElectionId(String electionId) {
        this.electionId = electionId;
    }
}
