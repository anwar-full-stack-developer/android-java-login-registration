package com.example.logintest.data.registration;

import com.example.logintest.data.model.RegisterUser;

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */
public class RegistrationRepository {

    private static volatile RegistrationRepository instance;

    private RegistrationDataSource dataSource;

    // If user credentials will be cached in local storage, it is recommended it be encrypted
    // @see https://developer.android.com/training/articles/keystore
    private RegisterUser user = null;

    // private constructor : singleton access
    private RegistrationRepository(RegistrationDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public static RegistrationRepository getInstance(RegistrationDataSource dataSource) {
        if (instance == null) {
            instance = new RegistrationRepository(dataSource);
        }
        return instance;
    }


    // If user credentials will be cached in local storage, it is recommended it be encrypted
    // @see https://developer.android.com/training/articles/keystore


    public RegistrationResponse<RegisterUser> register(String username, String password, String email, String firstName) {
        // handle login upon complete register
        RegistrationResponse<RegisterUser> response = dataSource.register(username, password, email, password);
//        if (response instanceof RegistrationResponse.Success) {
//
//        }
        return response;
    }
}