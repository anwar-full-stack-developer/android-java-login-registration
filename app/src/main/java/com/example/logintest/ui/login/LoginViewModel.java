package com.example.logintest.ui.login;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import android.content.Context;
import android.util.Patterns;

import com.example.logintest.data.AppMainSharedRepository;
import com.example.logintest.data.LoginRepository;
import com.example.logintest.data.LoginDataSourceResult;
import com.example.logintest.data.model.LoggedInUser;

import com.example.logintest.R;

public class LoginViewModel extends ViewModel {

    private MutableLiveData<LoginFormState> loginFormState = new MutableLiveData<>();
    private MutableLiveData<LoginResult> loginResult = new MutableLiveData<>();
    private LoginRepository loginRepository;

    LoginViewModel(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    LiveData<LoginFormState> getLoginFormState() {
        return loginFormState;
    }

    LiveData<LoginResult> getLoginResult() {
        return loginResult;
    }

    public void login(String username, String password) {

        // can be launched in a separate asynchronous job
        LoginDataSourceResult<LoggedInUser> loginDataSourceResult = loginRepository.login(username, password);

        if (loginDataSourceResult instanceof LoginDataSourceResult.Success) {
            LoggedInUser data = ((LoginDataSourceResult.Success<LoggedInUser>) loginDataSourceResult).getData();
            //TODO: required, but removed id necessary
            AppMainSharedRepository.getInstance().setLoggedinUser(data);

            this.loginResult.setValue(new LoginResult(new LoggedInUserView(data.getDisplayName())));
        } else {
            this.loginResult.setValue(new LoginResult(R.string.login_failed));
        }
    }

    public void loginDataChanged(String username, String password) {
        if (!isUserNameValid(username)) {
            loginFormState.setValue(new LoginFormState(R.string.invalid_username, null));
        } else if (!isPasswordValid(password)) {
            loginFormState.setValue(new LoginFormState(null, R.string.invalid_password));
        } else {
            loginFormState.setValue(new LoginFormState(true));
        }
    }

    // A placeholder username validation check
    private boolean isUserNameValid(String username) {
        if (username == null) {
            return false;
        }
        if (username.contains("@")) {
            return Patterns.EMAIL_ADDRESS.matcher(username).matches();
        } else {
            return !username.trim().isEmpty();
        }
    }

    // A placeholder password validation check
    private boolean isPasswordValid(String password) {
        return password != null && password.trim().length() > 5;
    }
}