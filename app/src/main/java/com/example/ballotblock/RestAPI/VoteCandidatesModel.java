package com.example.ballotblock.RestAPI;

public class VoteCandidatesModel {
    private int id;
    private String uuid, firstname, lastname, email, cnic, publicAddress, dob, permanentAddress, ElectionId;

    public VoteCandidatesModel(int id, String uuid, String firstname, String lastname, String email, String cnic, String publicAddress, String dob, String permanentAddress, String electionId) {
        this.id = id;
        this.uuid = uuid;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.cnic = cnic;
        this.publicAddress = publicAddress;
        this.dob = dob;
        this.permanentAddress = permanentAddress;
        ElectionId = electionId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCnic() {
        return cnic;
    }

    public void setCnic(String cnic) {
        this.cnic = cnic;
    }

    public String getPublicAddress() {
        return publicAddress;
    }

    public void setPublicAddress(String publicAddress) {
        this.publicAddress = publicAddress;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getPermanentAddress() {
        return permanentAddress;
    }

    public void setPermanentAddress(String permanentAddress) {
        this.permanentAddress = permanentAddress;
    }

    public String getElectionId() {
        return ElectionId;
    }

    public void setElectionId(String electionId) {
        ElectionId = electionId;
    }
}
