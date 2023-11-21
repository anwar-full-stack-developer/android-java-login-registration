package com.example.logintest.ui.registration;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.logintest.data.registration.RegistrationDataSource;
import com.example.logintest.data.registration.RegistrationRepository;
import com.example.logintest.ui.registration.RegistrationViewModel;

/**
 * ViewModel provider factory to instantiate RegistrationViewModel.
 * Required given RegistrationViewModel has a non-empty constructor
 */
public class RegistrationViewModelFactory implements ViewModelProvider.Factory {

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(RegistrationViewModel.class)) {
            return (T) new RegistrationViewModel(RegistrationRepository.getInstance(new RegistrationDataSource()));
        } else {
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}