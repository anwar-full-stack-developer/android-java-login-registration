package com.example.logintest.ui.registration;

import android.util.Patterns;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.logintest.R;
import com.example.logintest.data.AppMainSharedRepository;
import com.example.logintest.data.model.RegisterUser;
import com.example.logintest.data.registration.RegistrationRepository;
import com.example.logintest.data.registration.RegistrationResponse;

public class RegistrationViewModel extends ViewModel {

    private MutableLiveData<RegistrationFormState> registrationFormState = new MutableLiveData<>();
    private MutableLiveData<RegistrationResult> registrationResult = new MutableLiveData<>();
    private RegistrationRepository registrationRepository;

    RegistrationViewModel(RegistrationRepository registrationRepository) {
        this.registrationRepository = registrationRepository;
    }

    LiveData<RegistrationFormState> getRegistrationFormState() {
        return registrationFormState;
    }

    LiveData<RegistrationResult> getRegistrationResult() {
        return registrationResult;
    }

    public void register(String username, String password, String email, String firstName) {
        // can be launched in a separate asynchronous job
        RegistrationResponse<RegisterUser> response = registrationRepository.register(username, password, email, password);

        if (response instanceof RegistrationResponse.Success) {
            RegisterUser data = ((RegistrationResponse.Success<RegisterUser>) response).getData();
//            AppMainSharedRepository.getInstance().setLoggedinUser(data);
            //TODO: required, but removed id necessary
            this.registrationResult.setValue(new RegistrationResult(new RegistrationUserView(data.getFirstName())));
        } else {
            this.registrationResult.setValue(new RegistrationResult("Registration Failed"));
        }
    }

    public void registerDataChanged(String username, String password, String email, String firstName) {
        if (!isFirstNameValid(firstName)) {
            registrationFormState.setValue(new RegistrationFormState(null, null, null, R.string.invalid_first_name));
        }
        else if (!isEmailValid(firstName)) {
            registrationFormState.setValue(new RegistrationFormState(null, null, R.string.invalid_email_address, null));
        }
        else if (!isUserNameValid(username)) {
            registrationFormState.setValue(new RegistrationFormState(R.string.invalid_username, null, null, null));
        }
        else if (!isPasswordValid(password)) {
            registrationFormState.setValue(new RegistrationFormState(null, R.string.not_a_valid_password, null, null));
        } else {
            registrationFormState.setValue(new RegistrationFormState(true));
        }
    }

    // A placeholder username validation check

    private boolean isFirstNameValid(String firstName) {
        if (firstName == null) {
            return false;
        }
        return !firstName.trim().isEmpty() && firstName.trim().length() > 3;
    }

    private boolean isUserNameValid(String username) {
        if (username == null) {
            return false;
        }
        return !username.trim().isEmpty() && username.trim().length() > 5;

//        if (username.contains("@")) {
//            return Patterns.EMAIL_ADDRESS.matcher(username).matches();
//        } else {
//            return !username.trim().isEmpty();
//        }
    }


    // A placeholder username validation check
    private boolean isEmailValid(String username) {
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