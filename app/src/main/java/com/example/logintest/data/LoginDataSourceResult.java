package com.example.logintest.data;

/**
 * A generic class that holds a result success w/ data or an error exception.
 */
public class LoginDataSourceResult<T> {
    // hide the private constructor to limit subclass types (Success, Error)
    private LoginDataSourceResult() {
    }

    @Override
    public String toString() {
        if (this instanceof LoginDataSourceResult.Success) {
            LoginDataSourceResult.Success success = (LoginDataSourceResult.Success) this;
            return "Success[data=" + success.getData().toString() + "]";
        } else if (this instanceof LoginDataSourceResult.Error) {
            LoginDataSourceResult.Error error = (LoginDataSourceResult.Error) this;
            return "Error[exception=" + error.getError().toString() + "]";
        }
        return "";
    }

    // Success sub-class
    public final static class Success<T> extends LoginDataSourceResult {
        private T data;

        public Success(T data) {
            this.data = data;
        }

        public T getData() {
            return this.data;
        }
    }

    // Error sub-class
    public final static class Error extends LoginDataSourceResult {
        private Exception error;

        public Error(Exception error) {
            this.error = error;
        }

        public Exception getError() {
            return this.error;
        }
    }
}