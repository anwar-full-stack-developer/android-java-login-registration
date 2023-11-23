package com.example.logintest.ui.login;

import android.app.Activity;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.example.logintest.data.model.LoggedInUser;
import com.example.logintest.ui.login.LoginViewModel;
import com.example.logintest.ui.login.LoginViewModelFactory;
import com.example.logintest.databinding.ActivityLoginBinding;
import com.example.logintest.ui.registration.RegistrationActivity;

public class LoginActivity extends AppCompatActivity  {

    private LoginViewModel loginViewModel;
    private ActivityLoginBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        loginViewModel = new ViewModelProvider(this, new LoginViewModelFactory())
                .get(LoginViewModel.class);

        final EditText usernameEditText = binding.username;
        final EditText passwordEditText = binding.password;
        final Button loginButton = binding.login;
        final Button signupButton = binding.signup;
        final ProgressBar loadingProgressBar = binding.loading;

        // reload login user from local storage
//        LoadLoggedinUserFromLocalStorage();

        loginViewModel.getLoginFormState().observe(this, new Observer<LoginFormState>() {
            @Override
            public void onChanged(@Nullable LoginFormState loginFormState) {
                if (loginFormState == null) {
                    return;
                }
                loginButton.setEnabled(loginFormState.isDataValid());
                if (loginFormState.getUsernameError() != null) {
                    usernameEditText.setError(getString(loginFormState.getUsernameError()));
                }
                if (loginFormState.getPasswordError() != null) {
                    passwordEditText.setError(getString(loginFormState.getPasswordError()));
                }
            }
        });

        loginViewModel.getLoginResult().observe(this, new Observer<LoginResult>() {
            @Override
            public void onChanged(@Nullable LoginResult loginResult) {
                if (loginResult == null) {
                    return;
                }
                loadingProgressBar.setVisibility(View.GONE);
                if (loginResult.getError() != null) {
                    showLoginFailed(loginResult.getError());
                }
                if (loginResult.getSuccess() != null) {
                    //TODO: save loggedin user to local storage, remove line of necessery
//                    saveLoggedinUserToSharedPreferences(loginResult.getSuccess());
                    updateUiWithUser(loginResult.getSuccess());
                }
                setResult(Activity.RESULT_OK);

                //Complete and destroy login activity once successful
                if (loginResult.getSuccess() != null) {
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

                loginViewModel.loginDataChanged(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }
        };
        usernameEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    loginViewModel.login(usernameEditText.getText().toString(),
                            passwordEditText.getText().toString());
                }
                return false;
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingProgressBar.setVisibility(View.VISIBLE);
                loginViewModel.login(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }
        });

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("LoginActivity", "SignUp Button Clicked");
                Intent intent = new Intent(v.getContext(), RegistrationActivity.class);
                //pass parameter
                intent.putExtra("dispatchFrom", "LoginActivity_to_RegistrationActivity");
                v.getContext().startActivity(intent);

            }
        });
    }

    /**
     * Save user info to local storage
     *
     * @param model LoggedInUserView
     */
    private void saveLoggedinUserToSharedPreferences(LoggedInUserView model) {
        //save login info to local shared storage
        LoggedInUser u = AppMainSharedRepository.getInstance().getLoggedinUser().getValue();

        AppMainSharedRepository Amsr = AppMainSharedRepository.getInstance();
        SharedPreferences preferences = getApplicationContext().getSharedPreferences(Amsr.getPreferencesStorageName(), Context.MODE_PRIVATE);

        SharedPreferences.Editor edit = preferences.edit();
        edit.putString("loggedin_user_token", u.getLoginToken());
        edit.putString("loggedin_user_id", u.getUserId());
        edit.putString("loggedin_user_displayName", u.getDisplayName());
        edit.commit();

        AppMainSharedRepository.getInstance().setAllPreferences(preferences.getAll());

    }

    private void LoadLoggedinUserFromLocalStorage(){
        //Reload loggedin user from storage
        AppMainSharedRepository Amsr = AppMainSharedRepository.getInstance();
        SharedPreferences preferences = getApplicationContext().getSharedPreferences(Amsr.getPreferencesStorageName(), Context.MODE_PRIVATE);

        Amsr.setAllPreferences(preferences.getAll());

        String token = preferences.getString("loggedin_user_token", null);
        String uid = preferences.getString("loggedin_user_id", null);
        String displayName =  preferences.getString("loggedin_user_displayName", null);

        if (token != null && token.trim().isEmpty() == false )
            Amsr.setLoggedinUser( new LoggedInUser(uid, displayName, token) );

    }

    private void updateUiWithUser(LoggedInUserView model) {
        String welcome = "Login Success, " + getString(R.string.welcome) + model.getDisplayName();
        // TODO : initiate successful logged in experience

        final TextView textViewLoginActionCbMsg = binding.textViewLoginActionCbMsg;
        textViewLoginActionCbMsg.setText(welcome);
        textViewLoginActionCbMsg.setTextColor(Color.parseColor("green"));


        Toast.makeText(getApplicationContext(), welcome, Toast.LENGTH_LONG).show();
    }

    private void showLoginFailed(@StringRes Integer errorString) {
        final TextView textViewLoginActionCbMsg = binding.textViewLoginActionCbMsg;
        textViewLoginActionCbMsg.setText(errorString);
        textViewLoginActionCbMsg.setTextColor(Color.parseColor("red"));
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
    }
}