package com.example.logintest.data;

import android.util.Log;

import com.example.logintest.data.model.LoggedInUser;
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

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {


    public LoginDataSourceResult<LoggedInUser> login(String username, String password) {


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
        requestPayloadMap.put("username", username);
        requestPayloadMap.put("password", password);
        JSONObject requestPayload = new JSONObject(requestPayloadMap);
        String requestBody = requestPayload.toString();

        System.out.println(requestBody);

        // Set the request entity as a StringEntity with the serialized JSON payload
        StringEntity stringEntity = new StringEntity(requestBody, ContentType.APPLICATION_JSON);
        stringEntity.setContentType(String.valueOf(ContentType.APPLICATION_JSON));
        httpPost.setEntity(stringEntity);


        try {


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
                // TODO: handle loggedInUser authentication
                LoggedInUser realUser =
                        new LoggedInUser(
                                ro.getString("id"),
                                ro.getString("username"),
                                ro.getString("id"));
                return new LoginDataSourceResult.Success<>(realUser);
            } catch (ClientProtocolException e) {
                // Log exception
//                e.printStackTrace();
                return new LoginDataSourceResult.Error(new IOException("Error logging in", e));
            } catch (IOException e) {
                // Log exception
//                e.printStackTrace();
                return new LoginDataSourceResult.Error(new IOException("Error logging in", e));
            }catch (Exception e) {
                return new LoginDataSourceResult.Error(new IOException("Error logging in", e));
//                e.printStackTrace();
            } finally {
                // Close the HttpClient instance
//            httpClient.getConnectionManager().shutdown();
                connectionManager.shutdown();
            }
//            // TODO: handle loggedInUser authentication
//            LoggedInUser fakeUser =
//                    new LoggedInUser(
//                            java.util.UUID.randomUUID().toString(),
//                            "Jane Doe");
//            return new LoginResult.Success<>(fakeUser);
        } catch (Exception e) {
            return new LoginDataSourceResult.Error(new IOException("Error logging in", e));
        }
//        return null;
    }

    public void logout() {
        // TODO: revoke authentication
    }
}