package com.example.mixpanelsupportandroidsampleapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        helper = new StorageHelper(context);
        setupUI();
        //TODO: initialize analytics
    }//end of onCreate

    @Override
    protected void onResume() {
        super.onResume();
        //TODO: track when the app is opened
    }//end of onResume

    /*
    * Void function; executed in the authentication screen
    * Creates/Updates user data via the StorageHelper class
    * @param emailValue email used for authentication
    */
    private void doLogin(String emailValue){
        boolean result = Utils.validateEmail(emailValue);
        if(!result) return;
        JSONObject user = helper.getUserData(emailValue);
        emailInput.setText("");

        //TODO: track user authenticated

        Intent intent = new Intent(context, ProfileData.class);
        intent.putExtra("userEmail",emailValue);
        startActivity(intent);
    }//end of doLogin

    private void setupUI(){
        emailInput = findViewById(R.id.inputEmail);
        loginBtn = findViewById(R.id.btnLogIn);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doLogin(emailInput.getText().toString());
            }
        });
        emailInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    loginBtn.performClick();
                    return true;
                }
                return false;
            }
        });
    }//end of setupUI

    // Class scoped variables
    private StorageHelper helper;
    private EditText emailInput;
    private Button loginBtn;
    private Context context;
}//end of MainActivity