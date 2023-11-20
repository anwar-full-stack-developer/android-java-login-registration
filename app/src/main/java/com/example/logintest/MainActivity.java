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

import com.example.logintest.R;
import com.example.logintest.data.AppMainSharedRepository;
import com.example.logintest.data.model.LoggedInUser;
import com.example.logintest.databinding.ActivityMainBinding;
import com.example.logintest.ui.login.LoginActivity;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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



        final TextView textViewTempResult = binding.textViewTempResult;

        AppMainSharedRepository apmsr = AppMainSharedRepository.getInstance();
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

        final Button btnNavToPostReq = binding.btnNavToPostReq;
        btnNavToPostReq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("MainActivity", "btnNavToPostReq called");
                makePostRequest();
                Toast.makeText(getApplicationContext(), "Post Request Clicked", Toast.LENGTH_SHORT).show();
            }
        });

    }


    private void makePostRequest()  {
//        https://hc.apache.org/httpcomponents-client-4.5.x/quickstart.html
//        https://hayageek.com/android-http-post-get/
//https://gist.github.com/mingliangguo/c86e05a0f8a9019b281a63d151965ac7

//https://www.javasavvy.com/post-json-request-using-apache-httpclient/
//https://www.javasavvy.com/java-http-post-request-example/
// https://www.javasavvy.com/apache-httpclient-post-request-with-jackson-object-mapper/

        CloseableHttpClient httpClient = null;
        String LOG_TAG = "HttpClientActivity";
        HttpClientConnectionManager connectionManager;


        connectionManager = AndroidHttpClientConnectionManagerBuilder.create()

                .setConnectionTimeToLive(1, TimeUnit.MINUTES)
                .setDefaultSocketConfig(SocketConfig.custom()
                        .setSoTimeout(5000)
                        .build())
                .build();


        httpClient = HttpClients.custom()
                .setConnectionManager(connectionManager)
                .setDefaultRequestConfig(RequestConfig.custom()
                        .setConnectTimeout(5000)
                        .setSocketTimeout(5000)
                        .setCookieSpec(CookieSpecs.STANDARD_STRICT)
                        .build())
                .build();

        // replace with your url
        HttpPost httpPost = new HttpPost("http://192.168.4.100:8000/api/account/login");
        //            httpPost.setHeader("Authorization", "Basic (with a username and password)");
        httpPost.setHeader("Content-type", "application/json");

        // preparing post data
        Map<String, String> requestPayloadMap = new HashMap<>();
        requestPayloadMap.put("username", "user1");
        requestPayloadMap.put("password", "11111111");
        JSONObject requestPayload = new JSONObject(requestPayloadMap);
        String requestBody = requestPayload.toString();

        System.out.println(requestBody);

        // Set the request entity as a StringEntity with the serialized JSON payload
        StringEntity stringEntity = new StringEntity(requestBody, ContentType.APPLICATION_JSON);
        stringEntity.setContentType(String.valueOf(ContentType.APPLICATION_JSON));
        httpPost.setEntity(stringEntity);


        //making POST request.
        try {
             HttpResponse response = httpClient.execute(httpPost);
            // write response to log/ response header
            Log.d("HttpPostResponseLogin:", response.toString());

            // Get the response status code
            int statusCode = response.getStatusLine().getStatusCode();
            // Get the response body
            HttpEntity responseEntity = response.getEntity();
            String responseBody = EntityUtils.toString(responseEntity);
            // Close the response entity
            EntityUtils.consume(responseEntity);
            Header encodingHeader = responseEntity.getContentEncoding();

//            / Handle the response
            System.out.println("Response Code: " + statusCode);
            System.out.println("Response Body: " + responseBody);
            Log.d("PostJsonResponse", responseBody);
            JSONObject ro = new JSONObject(responseBody);
            System.out.println("ResponseObject: username: " + ro.getString("username"));

        } catch (ClientProtocolException e) {
            // Log exception
            e.printStackTrace();
        } catch (IOException e) {
            // Log exception
            e.printStackTrace();
        }catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Close the HttpClient instance
//            httpClient.getConnectionManager().shutdown();
            connectionManager.shutdown();
        }

    }


}