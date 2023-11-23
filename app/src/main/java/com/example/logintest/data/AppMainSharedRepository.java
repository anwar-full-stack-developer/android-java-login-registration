package com.example.logintest.data;

import static java.lang.Boolean.TRUE;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.lifecycle.MutableLiveData;

import com.example.logintest.ApplicationContext;
import com.example.logintest.data.model.LoggedInUser;

import java.util.Map;
import java.util.Set;

public class AppMainSharedRepository {

    private static volatile AppMainSharedRepository instance;

    private String preferencesStorageName = "AppMainSharedPreferences";
    private SharedPreferences preferences;
    private Map<String, ?> allPreferences;

    /**
     * get application context, Must be set context from a activity
     */
    private Context context;
    // private constructor : singleton access
    public AppMainSharedRepository() {
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
        if (mCurrentName == null) {
            mCurrentName = new MutableLiveData<>();
        }
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

    private void _saveLoggedinUser(LoggedInUser u) {
//        preferences = context.getSharedPreferences(getPreferencesStorageName(), Context.MODE_PRIVATE);
        // you can get all Map but be careful you must not modify the collection returned by this
        // method, or alter any of its contents.
        setAllPreferences(preferences.getAll());

        SharedPreferences.Editor edit = preferences.edit();
        edit.putString("loggedin_user_token", u.getLoginToken());
        edit.putString("loggedin_user_id", u.getUserId());
        edit.putString("loggedin_user_displayName", u.getDisplayName());
        edit.commit();
    }

    public void LoadLoggedinUserFromLocalStorage(){
        //Reload loggedin user from storage
//        preferences = context.getSharedPreferences(getPreferencesStorageName(), Context.MODE_PRIVATE);
        setAllPreferences(preferences.getAll());

        String token = preferences.getString("loggedin_user_token", null);
        String uid = preferences.getString("loggedin_user_id", null);
        String displayName =  preferences.getString("loggedin_user_displayName", null);

        if (token != null && token.trim().isEmpty() == false )
            setLoggedinUser( new LoggedInUser(uid, displayName, token) );

    }

    public void setLoggedinUser(LoggedInUser u) {
        _authLoggedinUser.setValue(u);
        _isUserLoogedin.setValue(true);
        _saveLoggedinUser(u);
    }

    public void setAllPreferences(Map<String, ?> p) {
        this.allPreferences = p;
    }
    public String getPreferencesStorageName() {
        return this.preferencesStorageName;
    }

    public void setContext(Context context){
        this.context = context;
        preferences = context.getSharedPreferences(getPreferencesStorageName(), Context.MODE_PRIVATE);
    }
}
