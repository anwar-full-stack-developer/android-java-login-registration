package com.example.logintest.data.registration;

/**
 * A generic class that holds a result success w/ data or an error exception.
 */
public class RegistrationResponse<T> {
    // hide the private constructor to limit subclass types (Success, Error)
    private RegistrationResponse() {
    }

    @Override
    public String toString() {
        if (this instanceof RegistrationResponse.Success) {
            RegistrationResponse.Success success = (RegistrationResponse.Success) this;
            return "Success[data=" + success.getData().toString() + "]";
        } else if (this instanceof RegistrationResponse.Error) {
            RegistrationResponse.Error error = (RegistrationResponse.Error) this;
            return "Error[exception=" + error.getError().toString() + "]";
        }
        return "";
    }

    // Success sub-class
    public final static class Success<T> extends RegistrationResponse {
        private T data;

        public Success(T data) {
            this.data = data;
        }

        public T getData() {
            return this.data;
        }
    }

    // Error sub-class
    public final static class Error extends RegistrationResponse {
        private Exception error;

        public Error(Exception error) {
            this.error = error;
        }

        public Exception getError() {
            return this.error;
        }
    }
}