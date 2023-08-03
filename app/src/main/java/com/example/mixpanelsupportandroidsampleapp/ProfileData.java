package com.example.mixpanelsupportandroidsampleapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mixpanel.android.mpmetrics.MixpanelAPI;

import org.json.JSONException;
import org.json.JSONObject;

public class ProfileData extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_data);
        context = this;
        helper = new StorageHelper(context);
        Intent intent = getIntent();
        user = helper.getUserData(intent.getStringExtra("userEmail"));
        setupUI(user);

        mixpanel = MixpanelAPI.getInstance(context,MainActivity.MP_PROJECT_TOKEN, false);
    }//end of onCreate

    /*
     * Void function; executed in the profile data screen
     * Updates the user's name into local storage via the StorageHelper class
     * @param name User's name
     */
    private void doNameUpdate(String name){
        if(name == "") return;
        try {
            user.put("name",name);
            helper.storeUserData(user);

            mixpanel.track("user_updated_name");
            mixpanel.getPeople().set("$name",name);
            mixpanel.flush();

        } catch (JSONException e) {
            Log.v("ProfileData","Issues updating user object");
        }
        Toast.makeText(context,"User's name updated", Toast.LENGTH_SHORT).show();
    }//end of doNameUpdate

    /*
     * Void function; logs the user out and returns to the launcher screen
     */
    private void doLogOut(){
        mixpanel.track("log_out");
        mixpanel.flush();
        //reset to generate a new ID for the potential next user
        mixpanel.reset();
        finish();
    }//end of doLogOut

    private void setupUI(JSONObject user){
        emailLabel = (TextView) findViewById(R.id.labelEmailValue);
        nameInput = (EditText) findViewById(R.id.inputName);
        btnLogOut = (Button) findViewById(R.id.btnLogOut);
        btnSave = (Button) findViewById(R.id.btnSave);

        try {
            emailLabel.setText(user.getString("email"));
            nameInput.setText(user.getString("name"));
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doLogOut();
            }
        });
        nameInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    btnSave.performClick();
                    return true;
                }
                return false;
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doNameUpdate(nameInput.getText().toString());
            }
        });
    }//end of setupUI

    EditText nameInput;
    TextView emailLabel;
    Button btnLogOut;
    Button btnSave;
    Context context;
    JSONObject user;
    StorageHelper helper;
    MixpanelAPI mixpanel;
}//end of activity