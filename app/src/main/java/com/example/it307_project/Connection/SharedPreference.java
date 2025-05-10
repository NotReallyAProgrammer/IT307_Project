package com.example.it307_project.Connection;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreference {

    private static SharedPreference instance;
    private static final String SHARED_PREF_NAME = "mysharedpref12";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_EMAIL = "useremail";
    private static final String KEY_USER_ID = "userid";

    private static Context c;

    private SharedPreference(Context context) {
        c = context.getApplicationContext(); // safer to use application context
    }

    public static synchronized SharedPreference getInstance(Context context) {
        if (instance == null) {
            instance = new SharedPreference(context);
        }
        return instance;
    }

    public boolean userLogin(int id, String username, String email) {
        SharedPreferences sharedPreferences = c.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt(KEY_USER_ID, id);
        editor.putString(KEY_USERNAME, username);
        editor.putString(KEY_EMAIL, email);
        editor.apply(); // or commit()

        return true;
    }

    public boolean isLoggedIn(){
        SharedPreferences sharedPreferences = c.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        if(sharedPreferences.getString(KEY_EMAIL,null) != null){
            return true;
        }
        return false;
    }

    public boolean logOut(){
        SharedPreferences sharedPreferences = c.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        return true;
    }
}
