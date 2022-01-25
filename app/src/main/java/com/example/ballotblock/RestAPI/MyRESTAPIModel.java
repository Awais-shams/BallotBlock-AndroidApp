package com.example.ballotblock.RestAPI;

public class MyRESTAPIModel {
    private String email;
    private String password;

    public MyRESTAPIModel(String email, String password) {
        this.email = email;
        this.password = password;
    }

    // Getter Methods
    public String getEmail() {
        return email;
    }
    public String getPassword() {
        return password;
    }

    // Setter Methods
    public void setEmail(String email) {
        this.email = email;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}
