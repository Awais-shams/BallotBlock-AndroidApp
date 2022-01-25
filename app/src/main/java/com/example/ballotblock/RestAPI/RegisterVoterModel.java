package com.example.ballotblock.RestAPI;

public class RegisterVoterModel {
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private String dob;
    private String cnic;
    private String permanentAddress;

    public RegisterVoterModel(String firstname, String lastname, String email, String password, String dob, String cnic, String permanentAddress) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.dob = dob;
        this.cnic = cnic;
        this.permanentAddress = permanentAddress;
    }

    // Getter Methods
    public String getFirstname() {
        return firstname;
    }
    public String getLastname() {
        return lastname;
    }
    public String getEmail() {
        return email;
    }
    public String getPassword() {
        return password;
    }
    public String getDob() {
        return dob;
    }
    public String getCnic() {
        return cnic;
    }
    public String getPermanentAddress() {
        return permanentAddress;
    }

    // Setter Methods
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setDob(String dob) {
        this.dob = dob;
    }
    public void setCnic(String cnic) {
        this.cnic = cnic;
    }
    public void setPermanentAddress(String permanentAddress) {
        this.permanentAddress = permanentAddress;
    }
}
