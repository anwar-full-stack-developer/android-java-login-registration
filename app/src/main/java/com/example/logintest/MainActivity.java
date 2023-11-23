package com.example.logintest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.logintest.data.AppMainSharedRepository;
import com.example.logintest.data.model.LoggedInUser;
import com.example.logintest.databinding.ActivityMainBinding;
import com.example.logintest.ui.login.LoginActivity;
import com.example.logintest.ui.registration.RegistrationActivity;
import com.ok2c.hc.android.http.AndroidHttpClientConnectionManagerBuilder;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;


public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
//    public MainActivity(){
//
//
//    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy gfgPolicy =
                    new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(gfgPolicy);
        }

//        setContentView(R.layout.activity_main);
//        final Button btnLogin = findViewById(R.id.btnNavToLogin);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        AppMainSharedRepository apmsr = AppMainSharedRepository.getInstance();
        apmsr.setContext(getApplicationContext());


        final TextView textViewTempResult = binding.textViewTempResult;

// init from view model
//                mainViewModel.getText().observe(getActivity(), new Observer<String>() {
        //init from activity
//        apmsr.getCurrentName().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(String s) {
//                // When text change
//                // set result text on text view
//                // Update the UI, in this case, a TextView.
//                textViewTempResult.setText("Live data update " + s);
//            }
//        });


        // watch LoggedIn user
        apmsr.getLoggedinUser().observe(this, new Observer<LoggedInUser>() {
            /**
             * @param loggedInUser
             */
            @Override
            public void onChanged(LoggedInUser loggedInUser) {

                // Update the UI, in this case, a TextView.
                textViewTempResult.setText("LiveData: User loggedin success,  ID: "
                        + loggedInUser.getUserId()
                        + "Display Name: "
                        + loggedInUser.getDisplayName()
                );
            }
        });


        final Button btnLogin = binding.btnNavToLogin;
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("MainActivity", "Login Button Clicked");
                Toast.makeText(getApplicationContext(), "Login button Clicked", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(v.getContext(), LoginActivity.class);
                //pass parameter
                intent.putExtra("dispatchFrom", "MainActivity_to_LoginActivity");
                v.getContext().startActivity(intent);
            }
        });

        final Button btnReg = binding.btnNavToRegistration;
        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("MainActivity", "btnNavToPostReq called");
                Toast.makeText(getApplicationContext(),
                        "Registration button Clicked", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(v.getContext(), RegistrationActivity.class);
                //pass parameter
                intent.putExtra("dispatchFrom", "MainActivity_to_RegistrationActivity");
                v.getContext().startActivity(intent);
            }
        });


        apmsr.LoadLoggedinUserFromLocalStorage();

    }




}