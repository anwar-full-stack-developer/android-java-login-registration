package com.example.logintest.ui.registration;

import androidx.annotation.Nullable;

/**
 * Authentication result : success (user details) or error message.
 */
class RegistrationResult {
    @Nullable
    private RegistrationUserView success;
    @Nullable
    private String error;

    RegistrationResult(@Nullable String error) {
        this.error = error;
    }

    RegistrationResult(@Nullable RegistrationUserView success) {
        this.success = success;
    }

    @Nullable
    RegistrationUserView getSuccess() {
        return success;
    }

    @Nullable
    String getError() {
        return error;
    }
}