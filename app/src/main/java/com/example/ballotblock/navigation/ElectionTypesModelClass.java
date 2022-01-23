package com.example.ballotblock.navigation;

public class ElectionTypesModelClass {
    String electionType, electionDate, electionStartTime, electionEndTime, electionDesignation;

    public ElectionTypesModelClass() {
    }

    public ElectionTypesModelClass(String electionType, String electionDate, String electionStartTime, String electionEndTime, String electionDesignation) {
        this.electionType = electionType;
        this.electionDate = electionDate;
        this.electionStartTime = electionStartTime;
        this.electionEndTime = electionEndTime;
        this.electionDesignation = electionDesignation;
    }

    public String getElectionDesignation() {
        return electionDesignation;
    }

    public void setElectionDesignation(String electionDesignation) {
        this.electionDesignation = electionDesignation;
    }

    public String getElectionType() {
        return electionType;
    }

    public void setElectionType(String electionType) {
        this.electionType = electionType;
    }

    public String getElectionDate() {
        return electionDate;
    }

    public void setElectionDate(String electionDate) {
        this.electionDate = electionDate;
    }

    public String getElectionStartTime() {
        return electionStartTime;
    }

    public void setElectionStartTime(String electionStartTime) {
        this.electionStartTime = electionStartTime;
    }

    public String getElectionEndTime() {
        return electionEndTime;
    }

    public void setElectionEndTime(String electionEndTime) {
        this.electionEndTime = electionEndTime;
    }
}
