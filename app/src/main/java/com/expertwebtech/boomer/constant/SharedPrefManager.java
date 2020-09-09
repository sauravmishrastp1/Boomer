package com.expertwebtech.boomer.constant;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import com.expertwebtech.boomer.activity.LoginActivity;
import com.expertwebtech.boomer.pojo.User;



/**
 * Created by Sharad
 */

//here for this class we are using a singleton pattern

public class SharedPrefManager {

    //the constants
    public static final String SHARED_PREF_NAME = "simplifiedcodingsharedpref";
    private static final String KEY_USERNAME = "keyusername";
    private static final String KEY_EMAIL = "keyemail";
    private static final String KEY_PHONE = "keyphone";
    private static final String KEY_USERID="keyUserId";
    private static final String KEY_USER_TYPE="keyUserType";
    private static final String KEY_LOCATION="keyLocation";
    private static final String KEY_IMAGE="key_IMAGE";




    private static SharedPrefManager mInstance;
    private static Context mCtx;

    private SharedPrefManager(Context context) {
        mCtx = context;
    }

    public static synchronized SharedPrefManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPrefManager(context);
        }
        return mInstance;
    }

    //method to let the user login
    //this method will store the user data in shared preferences
    public void userLogin(User user) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_PHONE, user.getPhone());
        editor.putString(KEY_USERNAME, user.getName());
        editor.putString(KEY_EMAIL, user.getEmail());
        editor.putString(KEY_USERID, user.getId());
        editor.putString(KEY_IMAGE, user.getImage());
        editor.putString(KEY_USER_TYPE, user.getUsertype());
        editor.putString(KEY_LOCATION, user.getLocation());
        editor.commit();
    }



    public void updateUserProfile(User user)
    { SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_PHONE, user.getPhone());
        editor.putString(KEY_USERNAME, user.getName());
        editor.putString(KEY_EMAIL, user.getEmail());
        editor.putString(KEY_USERID, user.getId());


        editor.commit();
    }

    //this method will checker whether user is already logged in or not
    public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USERID, null) != null;
    }

    //this method will give the logged in user
    public User getUser() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new User(
                sharedPreferences.getString(KEY_USERID, null),
                sharedPreferences.getString(KEY_USERNAME, null),
                sharedPreferences.getString(KEY_EMAIL, String.valueOf(-1)),
                sharedPreferences.getString(KEY_USER_TYPE,null),
                sharedPreferences.getString(KEY_LOCATION,null),
                sharedPreferences.getString(KEY_IMAGE,null),
                sharedPreferences.getString(KEY_PHONE,null));
    }

    //this method will logout the user
    public void logout() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        Intent intent=new Intent(mCtx, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mCtx.startActivity(intent);
    }
}