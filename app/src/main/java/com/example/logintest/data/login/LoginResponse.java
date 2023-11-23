package com.example.logintest.data.login;

/**
 * A generic class that holds a result success w/ data or an error exception.
 */
public class LoginResponse<T> {
    // hide the private constructor to limit subclass types (Success, Error)
    private LoginResponse() {
    }

    @Override
    public String toString() {
        if (this instanceof LoginResponse.Success) {
            LoginResponse.Success success = (LoginResponse.Success) this;
            return "Success[data=" + success.getData().toString() + "]";
        } else if (this instanceof LoginResponse.Error) {
            LoginResponse.Error error = (LoginResponse.Error) this;
            return "Error[exception=" + error.getError().toString() + "]";
        }
        return "";
    }

    // Success sub-class
    public final static class Success<T> extends LoginResponse {
        private T data;

        public Success(T data) {
            this.data = data;
        }

        public T getData() {
            return this.data;
        }
    }

    // Error sub-class
    public final static class Error extends LoginResponse {
        private Exception error;

        public Error(Exception error) {
            this.error = error;
        }

        public Exception getError() {
            return this.error;
        }
    }
}