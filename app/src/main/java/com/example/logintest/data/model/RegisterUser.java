package com.example.logintest.data.model;

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */

public class RegisterUser {

    private String id;
    private String _id;
    private String firstName;
    private String username;
    private String email;
    private String password;

    public RegisterUser(){

    }
    public RegisterUser(String id, String firstName, String username, String email, String password) {
        this.id = id;
        this._id = id;
        this.firstName = firstName;
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this._id = id;
        this.id = id;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this.id = _id;
        this._id = _id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}