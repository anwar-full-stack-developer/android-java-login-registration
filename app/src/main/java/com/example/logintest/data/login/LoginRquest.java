package com.example.logintest.data.login;

public class LoginRquest {
    private String username;
    private String password;

    public LoginRquest() {

    }
    public LoginRquest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String toJson(){
        return "";
    }
}
