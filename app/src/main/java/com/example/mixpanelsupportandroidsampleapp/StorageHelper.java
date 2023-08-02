package com.example.mixpanelsupportandroidsampleapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

public class StorageHelper {
    Context context;
    private final String SHARED_PREFS_FILE = "com.example.mixpanelsupportandroidsampleapp.data";
    public StorageHelper(Context ctx){
        context = ctx;
    }

    public JSONObject getUserData(String email){
        JSONObject user;
        String stored_object = getStringValue(email);
        try {
            return new JSONObject(stored_object);
        } catch (JSONException e) {
            user = new JSONObject();
            try {
                user.put("email",email);
                user.put("name","");
                storeUserData(user);
            } catch (JSONException ex) {
                Log.v("StorageHelper","Issues loading data into user object");
            }
        }

        return user;
    }// end of getUserData

    public void storeUserData(JSONObject user_object){
        try {
            storeStringValue(user_object.getString("email"),user_object.toString());
        } catch (JSONException e) {
            Log.v("StorageHelper","Issues loading data into user object");
        }
    }//end of storeUserData


    public void storeStringValue(String keyName, String value){
        SharedPreferences sharedPref = context.getSharedPreferences(SHARED_PREFS_FILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(keyName, value);
        editor.apply();
    }//end of storeStringValue
    public void storeIntValue(String keyName, Integer value){
        SharedPreferences sharedPref = context.getSharedPreferences(SHARED_PREFS_FILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(keyName, value);
        editor.apply();
    }//end of storeIntValue

    public String getStringValue(String keyName){
        SharedPreferences sharedPref = context.getSharedPreferences(SHARED_PREFS_FILE, Context.MODE_PRIVATE);
        return sharedPref.getString(keyName,"");
    }//end of getStringValue
    public Integer getIntValue(String keyName){
        SharedPreferences sharedPref = context.getSharedPreferences(SHARED_PREFS_FILE, Context.MODE_PRIVATE);
        return sharedPref.getInt(keyName,0);
    }//end of getStringValue

}//end of StorageHelper class
