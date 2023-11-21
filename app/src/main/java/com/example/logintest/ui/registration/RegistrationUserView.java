package com.example.logintest.ui.registration;

/**
 * Class exposing registered user details to the UI.
 */
class RegistrationUserView {
    private String displayName;
    //... other data fields that may be accessible to the UI

    RegistrationUserView(String displayName) {
        this.displayName = displayName;
    }

    String getDisplayName() {
        return displayName;
    }
}