package com.example.logintest.data;

import androidx.lifecycle.MutableLiveData;

import com.example.logintest.data.model.LoggedInUser;

public class AppMainSharedRepository {

    private static volatile AppMainSharedRepository instance;

    // private constructor : singleton access
    private AppMainSharedRepository() {
    }

    // get instance : singleton access
    public static synchronized AppMainSharedRepository getInstance() {
        if (instance == null) {
            instance = new AppMainSharedRepository();
        }
        return instance;

        // Check if the instance is already created
//        if(instance == null) {
//            // synchronize the block to ensure only one thread can execute at a time
//            synchronized (AppMainSharedRepository.class) {
//                // check again if the instance is already created
//                if (instance == null) {
//                    // create the singleton instance
//                    instance = new AppMainSharedRepository();
//                }
//            }
//        }
//        // return the singleton instance
//        return instance;
    }

    private MutableLiveData<String> mCurrentName = new MutableLiveData<>();

    public MutableLiveData<String> getCurrentName() {
//        if (mCurrentName == null) {
//            mCurrentName = new MutableLiveData<>();
//        }
        return mCurrentName;
    }

    public void setCurrentName(String s) {
        mCurrentName.setValue(s);
    }

    private MutableLiveData<Boolean> _isUserLoogedin = new MutableLiveData<>();
    public MutableLiveData<Boolean> isUserLoogedin() {
        if (_isUserLoogedin == null) {
            _isUserLoogedin = new MutableLiveData<>();
        }
        return _isUserLoogedin;
    }

    private MutableLiveData<LoggedInUser> _authLoggedinUser = new MutableLiveData<>();
    public MutableLiveData<LoggedInUser> getLoggedinUser() {
        if (_authLoggedinUser == null) {
            _authLoggedinUser = new MutableLiveData<>();
        }
        return _authLoggedinUser;
    }
    public void setLoggedinUser(LoggedInUser u) {
        _authLoggedinUser.setValue(u);
    }


}
