package com.example.logintest;


import static java.lang.Boolean.TRUE;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.preference.PreferenceManager;

import androidx.annotation.Nullable;
import androidx.datastore.core.DataStore;
import androidx.datastore.preferences.core.Preferences;
import androidx.datastore.preferences.core.PreferencesKeys;
import androidx.datastore.preferences.rxjava3.RxPreferenceDataStoreBuilder;

import java.security.KeyStore;
import java.security.KeyStoreException;
import java.util.Map;

import io.reactivex.rxjava3.core.Flowable;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.*;


public class AuthenticationService extends Service {

    private static final String KEY_TOKEN = "key_token";


    private DataStore<Preferences> dataStore;
    private Context context;
    public AuthenticationService() {
        try {
            KeyStore.getInstance("AndroidKeyStore");
        } catch (KeyStoreException e) {
            throw new RuntimeException(e);
        }

        context = getApplicationContext();

        dataStore =
                new RxPreferenceDataStoreBuilder(context, /*name=*/ "app_main_auth_data").build();

        dataStore.getData();

        SharedPreferences preferences = context.getSharedPreferences("app_pref", Context.MODE_PRIVATE);
        // you can get all Map but be careful you must not modify the collection returned by this
// method, or alter any of its contents.
        Map<String, ?> allPreferences = preferences.getAll();

        SharedPreferences.Editor edit=preferences.edit();

        edit.putString("key","value");
        edit.commit();

        // get values from Map
        preferences.getBoolean("key", TRUE);
        preferences.getString("key", "default val");


////        To retrieve the values use this code
//
//        SharedPreferences preferences = getSharedPreferences(
//                PREF_FILE_NAME, MODE_PRIVATE);
//        Map<String, String> map = (Map<String, String>) preferences.getAll();
//        if(!map.isEmpty()){
//            Iterator<Entry<String, String>> iterator = map.entrySet().iterator();
//            while(iterator.hasNext()){
//                Map.Entry pairs = (Map.Entry)iterator.next();
//                pairs.getKey()+pairs.getValue();
//                //write code here
//            }
//        }

// listener example
        SharedPreferences.OnSharedPreferenceChangeListener mListener
                = new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
            //do rest
            }
        };


//add on Change Listener
        preferences.registerOnSharedPreferenceChangeListener(mListener);

//remove on Change Listener
//        preferences.unregisterOnSharedPreferenceChangeListener(mListener);

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public  void getToken() {

        Preferences.Key<Integer> EXAMPLE_COUNTER = PreferencesKeys.intKey("example_counter");

        Flow<Preferences> data = dataStore.getData();
//getApplicationContext().data
//                        data.map(prefs -> prefs.get(EXAMPLE_COUNTER));

//        Preferences.Key<String> key = PreferencesKeys.stringKey(KEY_TOKEN);
//
//        Flowable<String> token =
//                dataStore.data.map(prefs -> prefs.get(key));
//        return KEY_TOKEN;
    }

    //    @Override
//    public IBinder onBind(Intent intent) {
//        // TODO: Return the communication channel to the service.
//        throw new UnsupportedOperationException("Not yet implemented");
//    }

    // returns a flow of is authenticated state
//    public Flow<Boolean> isAuthenticated()
//    {
//        // flow of token existence from dataStore
//        Preferences.Key<String> k = PreferencesKeys.stringKey(KEY_TOKEN);
//
////                dataStore.getData().collect(prefs -> prefs.get(k));
////        return dataStore.getData().map (
////            it.contains(KEY_TOKEN)
////        );
//    }

}