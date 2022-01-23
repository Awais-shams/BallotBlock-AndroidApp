package com.example.ballotblock.navigation;

public class ElectionTypesModelClass {
    String electionType, electionDate, electionStartTime, electionEndTime;

    public ElectionTypesModelClass() {
    }

    public ElectionTypesModelClass(String electionType, String electionDate, String electionStartTime, String electionEndTime) {
        this.electionType = electionType;
        this.electionDate = electionDate;
        this.electionStartTime = electionStartTime;
        this.electionEndTime = electionEndTime;
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
