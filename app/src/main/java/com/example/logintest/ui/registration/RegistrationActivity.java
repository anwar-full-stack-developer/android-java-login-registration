package com.example.logintest.ui.registration;

import android.app.Activity;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.logintest.R;
import com.example.logintest.data.AppMainSharedRepository;
import com.example.logintest.databinding.ActivityRegistrationBinding;
import com.example.logintest.ui.login.LoginActivity;
import com.example.logintest.ui.registration.RegistrationUserView;
import com.example.logintest.ui.registration.RegistrationResult;
import com.example.logintest.ui.registration.RegistrationViewModel;
import com.example.logintest.ui.registration.RegistrationViewModelFactory;

public class RegistrationActivity extends AppCompatActivity {

    private RegistrationViewModel registrationViewModel;
    private ActivityRegistrationBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityRegistrationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        registrationViewModel = new ViewModelProvider(this, new RegistrationViewModelFactory())
                .get(RegistrationViewModel.class);

        final EditText firstNameEditText = binding.firstName;
        final EditText emailEditText = binding.email;
        final EditText usernameEditText = binding.username;
        final EditText passwordEditText = binding.password;
        final Button registrationButton = binding.registration;
        final Button signinButton = binding.signin;
        final ProgressBar loadingProgressBar = binding.loading;


        registrationViewModel.getRegistrationFormState().observe(this, new Observer<RegistrationFormState>() {
            @Override
            public void onChanged(@Nullable RegistrationFormState registrationFormState) {
                if (registrationFormState == null) {
                    return;
                }
                registrationButton.setEnabled(registrationFormState.isDataValid());

                if (registrationFormState.getFirstNameError() != null) {
                    usernameEditText.setError(getString(registrationFormState.getFirstNameError()));
                }

                if (registrationFormState.getEmailError() != null) {
                    usernameEditText.setError(getString(registrationFormState.getEmailError()));
                }

                if (registrationFormState.getUsernameError() != null) {
                    usernameEditText.setError(getString(registrationFormState.getUsernameError()));
                }

                if (registrationFormState.getPasswordError() != null) {
                    passwordEditText.setError(getString(registrationFormState.getPasswordError()));
                }
            }
        });

        registrationViewModel.getRegistrationResult().observe(this, new Observer<RegistrationResult>() {
            @Override
            public void onChanged(@Nullable RegistrationResult registrationResult) {
                if (registrationResult == null) {
                    return;
                }
                loadingProgressBar.setVisibility(View.GONE);
                if (registrationResult.getError() != null) {
                    showRegistrationFailed(registrationResult.getError());
                }
                if (registrationResult.getSuccess() != null) {
                    updateUiWithUser(registrationResult.getSuccess());
                }
                setResult(Activity.RESULT_OK);

                //Complete and destroy registration activity once successful
                if (registrationResult.getSuccess() != null) {
                    finish();
                }
//                finish();
            }
        });

        //TODO: remove line
        AppMainSharedRepository apmsr = AppMainSharedRepository.getInstance();

        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }

            @Override
            public void afterTextChanged(Editable s) {
                ///TODO: remove line, update username to main activity as demo
                apmsr.setCurrentName(usernameEditText.getText().toString());

                registrationViewModel.registerDataChanged(
                        usernameEditText.getText().toString(),
                        passwordEditText.getText().toString(),
                        emailEditText.getText().toString(),
                        firstNameEditText.getText().toString());
            }
        };
        usernameEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {

                    registrationViewModel.register(
                            usernameEditText.getText().toString(),
                            passwordEditText.getText().toString(),
                            emailEditText.getText().toString(),
                            firstNameEditText.getText().toString());
                }
                return false;
            }
        });

        registrationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingProgressBar.setVisibility(View.VISIBLE);
                Log.d("RegistrationActivity", "Register Button Clicked");
                registrationViewModel.register(
                        usernameEditText.getText().toString(),
                        passwordEditText.getText().toString(),
                        emailEditText.getText().toString(),
                        firstNameEditText.getText().toString());
            }
        });

        signinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("RegistrationActivity", "Signin Button Clicked");
                Intent intent = new Intent(v.getContext(), LoginActivity.class);
                //pass parameter
                intent.putExtra("dispatchFrom", "RegistrationActivity_to_LoginActivity");
                v.getContext().startActivity(intent);

            }
        });
    }

    private void updateUiWithUser(RegistrationUserView model) {
        String welcome = "Registration Success, " + getString(R.string.welcome) + model.getDisplayName();
        // TODO : initiate successful logged in experience

        final TextView textViewRegistrationActionCbMsg = binding.textViewRegistrationActionCbMsg;
        textViewRegistrationActionCbMsg.setText(welcome);
        textViewRegistrationActionCbMsg.setTextColor(Color.parseColor("green"));

        Toast.makeText(getApplicationContext(), welcome, Toast.LENGTH_LONG).show();
    }

    private void showRegistrationFailed( String errorString) {
        final TextView btn = binding.textViewRegistrationActionCbMsg;
        btn.setText(errorString);
        btn.setTextColor(Color.parseColor("red"));
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
    }
}