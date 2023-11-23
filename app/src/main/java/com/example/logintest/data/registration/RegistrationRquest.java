package com.example.logintest.data.registration;

public class RegistrationRquest {
    private String username;
    private String password;
    private String firstName;
    private String email;

    public RegistrationRquest(){}

    public RegistrationRquest(String username,
                              String password,
                              String firstName,
                              String email) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getEmail() {
        return email;
    }


    public String toJson(){
        return "";
    }

}
