package com.example.logintest.ui.registration;

import androidx.annotation.Nullable;

/**
 * Data validation state of the login form.
 */
class RegistrationFormState {
    @Nullable
    private Integer usernameError;
    @Nullable
    private Integer passwordError;

    @Nullable
    private Integer firstNameError;

    @Nullable
    private Integer emailError;

    private boolean isDataValid;

    RegistrationFormState(@Nullable Integer usernameError,
                          @Nullable Integer passwordError,
                          @Nullable Integer emailError,
                          @Nullable Integer firstNameError
                          ) {
        this.usernameError = usernameError;
        this.passwordError = passwordError;
        this.isDataValid = false;
    }

    RegistrationFormState(boolean isDataValid) {
        this.usernameError = null;
        this.passwordError = null;
        this.firstNameError = null;
        this.emailError = null;
        this.isDataValid = isDataValid;
    }


    @Nullable
    Integer getUsernameError() {
        return usernameError;
    }

    @Nullable
    Integer getPasswordError() {
        return passwordError;
    }

    @Nullable
    Integer getFirstNameError() {
        return firstNameError;
    }

    @Nullable
    Integer getEmailError() {
        return emailError;
    }

    boolean isDataValid() {
        return isDataValid;
    }
}